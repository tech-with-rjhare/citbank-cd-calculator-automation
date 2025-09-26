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

public class BaseClass {
    protected WebDriver driver;
    protected SoftAssert softAssert;
    protected CDCalculatorPage page;
    protected Logger logger;

    @BeforeClass
    void setup(){
        logger = LogManager.getLogger(this.getClass()); // Create a logger for this specific class, so that logs show the class name automatically
        softAssert = new SoftAssert();
    }

    @BeforeMethod
    @Parameters({"browser","aut_url"})
    void launchApplication(@Optional("chrome") String br, @Optional("https://www.cit.com/cit-bank/resources/calculators/certificate-of-deposit-calculator") String url){
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

    @AfterMethod
    void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }
}
