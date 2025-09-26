package com.cit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CDCalculatorPage extends BasePage{

    //Constructor
    public CDCalculatorPage(WebDriver driver){

        super(driver);
    }

    //Locators
    private By depositAmountField = By.xpath("//input[@id='mat-input-0']");
    private By tenureInMonthField = By.xpath("//input[@id='mat-input-1']");
    private By interestRateField = By.xpath("//input[@id='mat-input-2']");
    private By dropdownCompounding = By.xpath("//mat-select[@id='mat-select-0']");
    private By submitBtn = By.xpath("//button[@id='CIT-chart-submit']");
    private By acceptCookiesBtn_locator = By.xpath("//button[contains(text(),'Accept All Cookies')]");


    //Actions
    public void enterDepositAmount(String depAmount){
        driver.findElement(depositAmountField).clear();
        driver.findElement(depositAmountField).sendKeys(depAmount);
    }

    public void enterTenureInMonths(String tenure){
        driver.findElement(tenureInMonthField).clear();
        driver.findElement(tenureInMonthField).sendKeys(tenure);
    }

    public void enterROI(String roi){
        driver.findElement(interestRateField).clear();
        driver.findElement(interestRateField).sendKeys(roi);
    }

    public void selectOptionCompoundingDropdown(String optionText){
        driver.findElement(dropdownCompounding).click();
        By optionLocator = By.xpath("//mat-option//span[contains(text(),'"+optionText+"')]");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        option.click();
    }

    public void clickSubmitBtn(){
        driver.findElement(submitBtn).click();
    }

    public String getResult(){
        By result = By.xpath("//span[@id='displayTotalValue']");
        WebElement getResult = wait.until(ExpectedConditions.visibilityOfElementLocated(result));
        return getResult.getText();
    }

    public void acceptCookies(){
        WebElement acceptCookieBtn = wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesBtn_locator));
        acceptCookieBtn.click();
    }

    public void maximizeWindow(){
        driver.manage().window().maximize();
    }

    public String expectedTitleOfPage(){
        wait.until(ExpectedConditions.titleContains("CD Calculator"));
        return driver.getTitle();
    }
}
