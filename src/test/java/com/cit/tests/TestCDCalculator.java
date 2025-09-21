package com.cit.tests;


import com.cit.utils.ExcelUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.time.Duration;
import java.util.List;



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

    @BeforeClass
    void setup(){
        softAssert = new SoftAssert();
    }

    void acceptCookies(){
            By acceptCookiesBtn_locator = By.xpath("//button[contains(text(),'Accept All Cookies')]");
            WebElement acceptCookieBtn = expWait.until(ExpectedConditions.elementToBeClickable(acceptCookiesBtn_locator));
            acceptCookieBtn.click();
            System.out.println("Cookies accepted.");
    }

    @BeforeMethod
    @Parameters({"browser","aut_url"})
    void launchApplication(@Optional("chrome") String br,@Optional("https://www.cit.com/cit-bank/resources/calculators/certificate-of-deposit-calculator") String url){

        switch (br.toLowerCase()){
            case "edge": driver = new EdgeDriver(); break;
            case "chrome": driver = new ChromeDriver();break;
            default: System.out.println("Invalid browser");return;
        }
        expWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(url);
        driver.manage().window().maximize();
        acceptCookies();
    }

    @Test()
     void testTitleOfPage(){
        String titleOfPage = "CD Calculator | Certificate of Deposit Calculator | CIT Bank";
        expWait.until(ExpectedConditions.titleContains("CD Calculator"));
        softAssert.assertEquals(titleOfPage,driver.getTitle());
        System.out.println("Title of page : "+driver.getTitle());
        softAssert.assertAll();
    }

    @Test(priority = 1,dependsOnMethods = {"testTitleOfPage"})
    void testCIDaily() throws Exception {
        String xlFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata_cdcalculator.xlsx";
        int rowCount = ExcelUtils.getRowCount(xlFilePath, "Compounded_Daily");
        //System.out.println("Row : " + rowCount);
        //ExcelUtils.getrowdata(xlFilePath,"Compounded_Daily");
        //acceptCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        depAmount = driver.findElement(By.xpath("//input[@id='mat-input-0']"));
        months = driver.findElement(By.xpath("//input[@id='mat-input-1']"));
        interest = driver.findElement(By.xpath("//input[@id='mat-input-2']"));

        for(int row=1; row<rowCount;row++){

        depositAmount = ExcelUtils.getCellData(xlFilePath, "Compounded_Daily", row, 0);
        lengthOfCD = ExcelUtils.getCellData(xlFilePath, "Compounded_Daily", row, 1);
        interestRate = ExcelUtils.getCellData(xlFilePath, "Compounded_Daily", row, 2);
        //System.out.println(depositAmount +" | "+lengthOfCD+" | "+interestRate);


        depAmount.clear();
        months.clear();
        interest.clear();

        depAmount.sendKeys(depositAmount);
        months.sendKeys(lengthOfCD);
        interest.sendKeys(interestRate);

        WebElement dropdownCompound = driver.findElement(By.xpath("//mat-select[@id='mat-select-0']"));
        dropdownCompound.click();

/*       List<WebElement> options = expWait.until(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By.xpath("//div[@id='mat-select-0-panel']//mat-option")));
        for (WebElement option : options) {
            if (option.getText().trim().equals("Compounded Daily")) {
                option.click();
                break;
            }
        }
*/

        WebElement daily = expWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option//span[contains(text(),'Compounded Daily')]")));
        daily.click();

        driver.findElement(By.xpath("//button[@id='CIT-chart-submit']")).click();
        String expectedRes = ExcelUtils.getCellData(xlFilePath, "Compounded_Daily", row, 4);
        expWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='displayTotalValue']")));
        String actualRes = driver.findElement(By.xpath("//span[@id='displayTotalValue']")).getText();
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
        String xlFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata_cdcalculator.xlsx";
        int rowCount = ExcelUtils.getRowCount(xlFilePath, "Compounded_Monthly");
        System.out.println("Row : " + rowCount);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        depAmount = driver.findElement(By.xpath("//input[@id='mat-input-0']"));
        months = driver.findElement(By.xpath("//input[@id='mat-input-1']"));
        interest = driver.findElement(By.xpath("//input[@id='mat-input-2']"));

        for(int row=1; row<rowCount;row++){

            depositAmount = ExcelUtils.getCellData(xlFilePath, "Compounded_Monthly", row, 0);
            lengthOfCD = ExcelUtils.getCellData(xlFilePath, "Compounded_Monthly", row, 1);
            interestRate = ExcelUtils.getCellData(xlFilePath, "Compounded_Monthly", row, 2);
            //System.out.println(depositAmount +" | "+lengthOfCD+" | "+interestRate);


            depAmount.clear();
            months.clear();
            interest.clear();

            depAmount.sendKeys(depositAmount);
            months.sendKeys(lengthOfCD);
            interest.sendKeys(interestRate);

            WebElement dropdownCompound = driver.findElement(By.xpath("//mat-select[@id='mat-select-0']"));
            dropdownCompound.click();

            WebElement daily = expWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option//span[contains(text(),'Compounded Monthly')]")));
            daily.click();

            driver.findElement(By.xpath("//button[@id='CIT-chart-submit']")).click();
            String expectedRes = ExcelUtils.getCellData(xlFilePath, "Compounded_Monthly", row, 4);
            expWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='displayTotalValue']")));
            String actualRes = driver.findElement(By.xpath("//span[@id='displayTotalValue']")).getText();
            //System.out.println("Actual result : " + actualRes + "\n" + "expectedRes : " + expectedRes);
            /*if (actualRes.equals(expectedRes)) {
                ExcelUtils.setCellData(xlFilePath, "Compounded_Monthly", row, 6, "Passed");
                ExcelUtils.fillGreenColor(xlFilePath, "Compounded_Monthly", row, 6);
            }else
            {
                ExcelUtils.setCellData(xlFilePath, "Compounded_Monthly", row, 6, "Failed");
                ExcelUtils.fillRedColor(xlFilePath, "Compounded_Monthly", row, 6);
            }*/

        }

    }

    @AfterMethod
    void tearDown(){
        if (driver != null) {
            driver.quit();   // ✅ Har test ke baad browser close
        }
    }

}
