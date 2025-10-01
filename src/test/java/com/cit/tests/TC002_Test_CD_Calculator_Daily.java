package com.cit.tests;

import com.cit.pages.CDCalculatorPageFactory;
import com.cit.testBase.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC002_Test_CD_Calculator_Daily extends BaseClass {

    @Test(groups = {"Smoke","Regression","Master"})
    public void verify_CD_Daily(){
        CDCalculatorPageFactory cd = new CDCalculatorPageFactory(driver);
        cd.enterDepositAmount(properties.getProperty("DepositAmount"));
        cd.enterTenureInMonths(properties.getProperty("tenure"));
        cd.enterROI(properties.getProperty("roi"));
        String option = "Compounded Daily";
        cd.selectOptionCompoundingDropdown(option);
        cd.clickSubmitBtn();

        if(properties.getProperty("dailyExpected").equals(cd.getResult())){
            logger.info("✅ Validation Passed - Expected = Actual = {}", cd.getResult());
        }
        else {
            logger.error("❌ Validation Failed - Expected: {} | Actual: {}", properties.getProperty("dailyExpected"), cd.getResult());
        }
        Assert.assertEquals(properties.getProperty("dailyExpected"),cd.getResult(),"Result mismatch check logs");

    }
}
