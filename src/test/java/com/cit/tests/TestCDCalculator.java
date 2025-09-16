package com.cit.tests;


import net.bytebuddy.build.Plugin;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;


public class TestCDCalculator {

    WebDriver driver;
    WebDriverWait expWait;
    SoftAssert softAssert;
    private String depositAmount = null;
    private String lengthOfCD = null; // In months
    private String interestRate = null;
    private WebElement depAmount;
    private WebElement months;
    private WebElement interest;
    private String url = "https://www.cit.com/cit-bank/resources/calculators/certificate-of-deposit-calculator";

    @BeforeClass
    void setup(){
        driver = new ChromeDriver();
        expWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        softAssert = new SoftAssert();
        driver.get(url);
        driver.manage().window().maximize();
    }

    void acceptCookies(){
            By acceptCookiesBtn_locator = By.xpath("//button[contains(text(),'Accept All Cookies')]");
            WebElement acceptCookieBtn = expWait.until(ExpectedConditions.elementToBeClickable(acceptCookiesBtn_locator));
            acceptCookieBtn.click();
            System.out.println("Cookies accepted.");
    }

    @Test(priority = 0)
    void testTitleOfPage(){
        acceptCookies();
        String titleOfPage = "CD Calculator | Certificate of Deposit Calculator | CIT Bank";
        softAssert.assertEquals(titleOfPage,driver.getTitle());
        System.out.println("Title of page : "+driver.getTitle());
        softAssert.assertAll();
    }

    @Test(priority = 1)
    void testCIMonthly() throws InterruptedException {
        //acceptCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        depAmount = driver.findElement(By.xpath("//input[@id='mat-input-0']"));
        months = driver.findElement(By.xpath("//input[@id='mat-input-1']"));
        interest = driver.findElement(By.xpath("//input[@id='mat-input-2']"));

        depositAmount = "3000";
        lengthOfCD = "24";
        interestRate = "08.22";

        depAmount.clear();
        months.clear();
        interest.clear();

        depAmount.sendKeys(depositAmount);
        months.sendKeys(lengthOfCD);
        interest.sendKeys(interestRate);


        WebElement dropdownCompound = driver.findElement(By.xpath("//mat-select[@id='mat-select-0']"));
        dropdownCompound.click();

        List<WebElement> options=driver.findElements(By.xpath("//div[@id='mat-select-0-panel']//mat-option"));

        for(WebElement option:options)
        {
           // System.out.println(option.getText());
            if(option.getText().equals("Compounded Daily")) {
                option.click();

            }
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@id='CIT-chart-submit']")).click();

        String actualRes = "$3,535.99";
        String expectedRes = driver.findElement(By.xpath("//span[@id='displayTotalValue']")).getText();
        //Assert.assertEquals(actualRes, expectedRes,"Calculation logic doesn't match with requirement");
        System.out.println("Actual result : "+actualRes+"\n"+"expectedRes : "+expectedRes);
        Assert.assertEquals(expectedRes, actualRes,"Calculation logic failed");
    }

    @AfterClass
    void tearDown(){
        driver.quit();
    }
}
