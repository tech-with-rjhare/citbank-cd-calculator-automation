package com.cit.tests;


import com.cit.pages.CDCalculatorPage;
import com.cit.utils.ExcelUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.time.Duration;



public class TestCDCalculator {

    WebDriver driver;
    SoftAssert softAssert;
    private String depositAmount = null;
    private String lengthOfCD = null; // In months
    private String interestRate = null;
    private CDCalculatorPage page;

    @BeforeClass
    void setup(){
        softAssert = new SoftAssert();

    }

    @BeforeMethod
    @Parameters({"browser","aut_url"})
    void launchApplication(@Optional("chrome") String br,@Optional("https://www.cit.com/cit-bank/resources/calculators/certificate-of-deposit-calculator") String url){
        switch (br.toLowerCase()){
            case "edge": driver = new EdgeDriver(); break;
            case "chrome": driver = new ChromeDriver();break;
            case "firefox": driver = new FirefoxDriver(); break;
            default: System.out.println("Invalid browser");return;
        }
        driver.get(url);
        page = new CDCalculatorPage(driver);
        page.maximizeWindow();
        page.acceptCookies();
    }

    @Test()
     void testTitleOfPage(){
        String titleOfPage = "CD Calculator | Certificate of Deposit Calculator | CIT Bank";
        softAssert.assertEquals(titleOfPage,page.expectedTitleOfPage());
        System.out.println("Title of page : "+driver.getTitle());
        softAssert.assertAll();
    }

    @Test(priority = 1,dependsOnMethods = {"testTitleOfPage"})
    void testCIDaily() throws Exception {
        String xlFilePath = System.getProperty("user.dir") + "\\test-data\\testdata_cdcalculator.xlsx";
        int rowCount = ExcelUtils.getRowCount(xlFilePath, "Compounded_Daily");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        for(int row=1; row<rowCount;row++){

        depositAmount = ExcelUtils.getCellData(xlFilePath, "Compounded_Daily", row, 0);
        lengthOfCD = ExcelUtils.getCellData(xlFilePath, "Compounded_Daily", row, 1);
        interestRate = ExcelUtils.getCellData(xlFilePath, "Compounded_Daily", row, 2);
        //System.out.println(depositAmount +" | "+lengthOfCD+" | "+interestRate);

        page.enterDepositAmount(depositAmount);
        page.enterTenureInMonths(lengthOfCD);
        page.enterROI(interestRate);
        page.selectOptionCompoundingDropdown("Compounded Daily");
        page.clickSubmitBtn();

        String expectedRes = ExcelUtils.getCellData(xlFilePath, "Compounded_Daily", row, 4);
        String actualRes = page.getResult();
        //System.out.println("Actual result : " + actualRes + " " + "expectedRes : " + expectedRes);
            if (actualRes.equals(expectedRes)) {
                ExcelUtils.setCellData(xlFilePath, "Compounded_Daily", row, 6, "Passed");
                ExcelUtils.fillGreenColor(xlFilePath, "Compounded_Daily", row, 6);
            } else {
                ExcelUtils.setCellData(xlFilePath, "Compounded_Daily", row, 6, "Failed");
                ExcelUtils.fillRedColor(xlFilePath, "Compounded_Daily", row, 6);
            }

    }
            //Assert.assertEquals(expectedRes, actualRes,"Calculation logic doesn't match with requirement");

    }

    @Test(priority = 2,dependsOnMethods = {"testTitleOfPage"})
    void testCIMonthly() throws Exception {
        String xlFilePath = System.getProperty("user.dir") + "\\test-data\\testdata_cdcalculator.xlsx";
        int rowCount = ExcelUtils.getRowCount(xlFilePath, "Compounded_Monthly");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        for(int row=1; row<rowCount;row++){

            depositAmount = ExcelUtils.getCellData(xlFilePath, "Compounded_Monthly", row, 0);
            lengthOfCD = ExcelUtils.getCellData(xlFilePath, "Compounded_Monthly", row, 1);
            interestRate = ExcelUtils.getCellData(xlFilePath, "Compounded_Monthly", row, 2);
            //System.out.println(depositAmount +" | "+lengthOfCD+" | "+interestRate);

            page.enterDepositAmount(depositAmount);
            page.enterTenureInMonths(lengthOfCD);
            page.enterROI(interestRate);
            page.selectOptionCompoundingDropdown("Compounded Monthly");
            page.clickSubmitBtn();

            String expectedRes = ExcelUtils.getCellData(xlFilePath, "Compounded_Monthly", row, 4);
            String actualRes = page.getResult();
            //System.out.println("Actual result : " + actualRes + " " + "expectedRes : " + expectedRes);
            if (actualRes.equals(expectedRes)) {
                ExcelUtils.setCellData(xlFilePath, "Compounded_Monthly", row, 7, "Passed");
                ExcelUtils.fillGreenColor(xlFilePath, "Compounded_Monthly", row, 7);
            } else {
                ExcelUtils.setCellData(xlFilePath, "Compounded_Monthly", row, 7, "Failed");
                ExcelUtils.fillRedColor(xlFilePath, "Compounded_Monthly", row, 7);
            }

        }
        //Assert.assertEquals(expectedRes, actualRes,"Calculation logic doesn't match with requirement");

    }

    @AfterMethod
    void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }

}
