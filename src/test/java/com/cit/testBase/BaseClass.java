package com.cit.testBase;

import com.cit.pages.CDCalculatorPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;


public class BaseClass {
    protected WebDriver driver;
    protected SoftAssert softAssert;
    protected CDCalculatorPage page;
    protected Logger logger;
    protected Properties properties;

    @BeforeClass(groups = {"Smoke","Sanity","Regression","Master"})
    public void setup(){
        logger = LogManager.getLogger(this.getClass()); // Create a logger for this specific class, so that logs show the class name automatically
        softAssert = new SoftAssert();
    }

    @BeforeMethod(groups = {"Smoke","Sanity","Regression","Master"})
    @Parameters({"browser","OS"})
    public void launchApplication(String br,String os) throws IOException {
        switch (br.toLowerCase()){
            case "edge": driver = new EdgeDriver(); break;
            case "chrome": driver = new ChromeDriver();break;
            case "firefox": driver = new FirefoxDriver(); break;
            default: logger.info("************ INVALID BROWSER ************");return;
        }

        logger.info("-------------------- Launching Application ---------------------");
        FileInputStream fi = new FileInputStream("./src//test//resources//config.properties");
        properties = new Properties();
        properties.load(fi);

        driver.get(properties.getProperty("aut_url"));
        page = new CDCalculatorPage(driver);
        page.maximizeWindow();
        page.acceptCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod(groups = {"Smoke","Sanity","Regression","Master"})
    public void tearDown(){
        if (driver != null) {
            logger.info("------------------- Browser closed -------------------");
            driver.quit();
        }
    }
}
