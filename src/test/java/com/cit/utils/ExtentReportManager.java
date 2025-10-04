package com.cit.utils;

import java.awt.*;
import java.io.File;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.cit.testBase.BaseClass;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener{

    public ExtentSparkReporter sparkReporter;  // UI of the report
    public ExtentReports extent;  //populate common info on the report
    public ExtentTest test; // creating test case entries in the report and update status of the test methods
    public String reportName;
    public String timeStamp = new SimpleDateFormat("dd-MMM-yyyy.HH.mm.ss").format(new Date());

    public void onStart(ITestContext context) {

        /*
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy.HH.mm.ss");
        Date date = new Date();
        String currentTimeStamp = dateFormat.format(date);
         */

        // timeStamp capturing realtime time
        String reportPath = System.getProperty("user.dir")+ "/reports/";
        reportName = "Automation-Test-Report" + timeStamp + ".html";

        sparkReporter=new ExtentSparkReporter(reportPath+reportName); //specify location of the report + name of report dynamically

        sparkReporter.config().setDocumentTitle("Automation Test Execution Report"); // TiTle of report
        sparkReporter.config().setReportName("Functional & Regression Suite Results"); // name of the report
        sparkReporter.config().setTheme(Theme.DARK);

        extent=new ExtentReports();
        String os = context.getCurrentXmlTest().getParameter("OS"); // fetching OS from current running XML parameter
        String browser = context.getCurrentXmlTest().getParameter("browser"); //fetching browser from current running XML parameter
        String userName =context.getCurrentXmlTest().getParameter("username");
        List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups(); // Capturing all the groups included in current xml file

        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application","CitBank");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name",userName); //System.getProperty("user.name")
        extent.setSystemInfo("Execution Date", timeStamp);
        extent.setSystemInfo("Operating System",os);
        extent.setSystemInfo("Browser",browser);
        if(!includedGroups.isEmpty()){
            extent.setSystemInfo("Groups", includedGroups.toString());
        }

    }


    public void onTestSuccess(ITestResult result) {

        test = extent.createTest(result.getTestClass().getName()); // create a new entry in the report which is copying the Test class name
        test.assignCategory(result.getMethod().getGroups()); // Map TestNG groups to ExtentReport categories
        test.log(Status.PASS, "✅ Test Passed: " + result.getName()); // update status on report with message

    }

    public void onTestFailure(ITestResult result) {

        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, "❌ Test Failed: " + result.getName());
        test.log(Status.INFO, result.getThrowable().getMessage());
        try{
            BaseClass baseClass = new BaseClass();
            String imgPath = baseClass.captureScreenShot(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void onTestSkipped(ITestResult result) {

        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, "⚠️ Test Skipped: " + result.getName());
        test.log(Status.INFO, result.getThrowable().getMessage());

    }


    public void onFinish(ITestContext context) {
        extent.flush();
        String ExtentReportFilePath = System.getProperty("user.dir")+"\\reports\\"+reportName;

        File extentReport = new File(ExtentReportFilePath);
        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        try {
			  URL url = new  URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+reportName);

		  // Create the email message
		  ImageHtmlEmail email = new ImageHtmlEmail();
		  email.setDataSourceResolver(new DataSourceUrlResolver(url));
		  email.setHostName("smtp.googlemail.com");
		  email.setSmtpPort(465);
		  email.setAuthenticator(new DefaultAuthenticator("rjhare0@gmail.com","Password_of_email"));
          email.setSSLOnConnect(true);
		  email.setFrom("rjhare0@gmail.com"); //Sender
		  email.setSubject("Automated Test Execution Report – CIT Bank CD Calculator – " + timeStamp);
		  String message = "Dear Team,\n\n" +
                  "Please find attached the latest automated test execution report for the CIT Bank CD Calculator project.\n\n" +
                  "The report provides a detailed summary of the test cases executed, including any failed or skipped test cases. Kindly review the results at your earliest convenience.\n\n" +
                  "Best regards,\n" +
                  "Rakesh Jhare\n" +
                  "QA Automation Engineer\n";
          email.setMsg(message);
          String recipientEmail = "jhare171065@gmail.com";
		  email.addTo(recipientEmail); //Receiver
		  email.attach(url, "Extent Test Report", "Please review the attached report for details of executed test cases.");
		  email.send(); // send the email
          System.out.println("Test report email sent successfully to " + recipientEmail);
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  */
    }

}
