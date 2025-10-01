package com.cit.tests;

import com.cit.pages.CDCalculatorPageFactory;
import com.cit.testBase.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC004_Test_CD_Calculator_Monthly extends BaseClass {

    @Test(groups = {"Sanity","Regression","Master"})
    public void verify_CD_Monthly(){
        CDCalculatorPageFactory cd = new CDCalculatorPageFactory(driver);
        cd.enterDepositAmount(properties.getProperty("DepositAmount"));
        cd.enterTenureInMonths(properties.getProperty("tenure"));
        cd.enterROI(properties.getProperty("roi"));
        String option = "Compounded Monthly";
        cd.selectOptionCompoundingDropdown(option);
        cd.clickSubmitBtn();
        if(properties.getProperty("monthlyExpected").equals(cd.getResult())){
            logger.info("✅ Validation Passed - Expected = Actual = {}", cd.getResult());
        }
        else {
            logger.error("❌ Validation Failed - Expected: {} | Actual: {}", properties.getProperty("monthlyExpected"), cd.getResult());
        }
        Assert.assertEquals(properties.getProperty("monthlyExpected"),cd.getResult(),"Result mismatch check logs");
    }
}
