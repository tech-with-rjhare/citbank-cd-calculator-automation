package com.cit.tests;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class BaseClass {
    protected WebDriver driver;
    protected SoftAssert softAssert;
    protected CDCalculatorPage page;
    protected Logger logger;
    protected Properties properties;

    @BeforeClass
    void setup(){
        logger = LogManager.getLogger(this.getClass()); // Create a logger for this specific class, so that logs show the class name automatically
        softAssert = new SoftAssert();
    }

    @BeforeMethod
    @Parameters({"browser","OS"})
    void launchApplication(String br,String os) throws IOException {
        switch (br.toLowerCase()){
            case "edge": driver = new EdgeDriver(); break;
            case "chrome": driver = new ChromeDriver();break;
            case "firefox": driver = new FirefoxDriver(); break;
            default: logger.info("************ INVALID BROWSER ************");return;
        }

        FileInputStream fi = new FileInputStream("./src//test//resources//config.properties");
        properties = new Properties();
        properties.load(fi);

        driver.get(properties.getProperty("aut_url"));
        page = new CDCalculatorPage(driver);
        page.maximizeWindow();
        page.acceptCookies();
    }

    @AfterMethod
    void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }
}
