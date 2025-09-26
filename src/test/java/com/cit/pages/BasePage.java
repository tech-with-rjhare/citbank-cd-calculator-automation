package com.cit.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    WebDriver driver;
    WebDriverWait wait;
    public BasePage(WebDriver driver){

        this.driver=driver;
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver,this);
    }
}
