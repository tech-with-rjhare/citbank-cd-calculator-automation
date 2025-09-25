package com.cit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CDCalculatorPageFactory {

    private WebDriver driver;
    private WebDriverWait wait;

    public CDCalculatorPageFactory(WebDriver driver){
        this.driver=driver;
        wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//input[@id='mat-input-0']")
    private WebElement depositAmountField;

    @FindBy(xpath = "//input[@id='mat-input-1']")
    private WebElement tenureInMonthField;

    @FindBy(xpath = "//input[@id='mat-input-2']")
    private WebElement interestRateField;

    @FindBy(xpath = "//mat-select[@id='mat-select-0']")
    private WebElement dropdownCompounding;

    @FindBy(xpath = "//button[@id='CIT-chart-submit']")
    private WebElement submitBtn;

    @FindBy(xpath = "//button[contains(text(),'Accept All Cookies')]")
    private WebElement acceptCookiesBtn;

    @FindBy(xpath = "//span[@id='displayTotalValue']")
    private WebElement result;

    public void enterDepositAmount(String depAmount){
        depositAmountField.clear();
        depositAmountField.sendKeys(depAmount);
    }

    public void enterTenureInMonths(String tenure){
        tenureInMonthField.clear();
        tenureInMonthField.sendKeys(tenure);
    }

    public void enterROI(String roi){
        interestRateField.clear();
        interestRateField.sendKeys(roi);
    }

    public void selectOptionCompoundingDropdown(String optionText){
        dropdownCompounding.click();
        By optionLocator = By.xpath("//mat-option//span[contains(text(),'"+optionText+"')]");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        option.click();
    }

    public void clickSubmitBtn(){
        submitBtn.click();
    }

    public String getResult(){

       wait.until(ExpectedConditions.visibilityOf(result));
        return result.getText();
    }

    public void acceptCookies(){
        wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesBtn));
        acceptCookiesBtn.click();
    }

    public void maximizeWindow(){
        driver.manage().window().maximize();
    }

    public String expectedTitleOfPage(){
        wait.until(ExpectedConditions.titleContains("CD Calculator"));
        return driver.getTitle();
    }



}
