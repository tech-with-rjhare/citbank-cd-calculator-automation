package com.cit.tests;

import com.cit.pages.CDCalculatorPageFactory;
import com.cit.testBase.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC006_Test_CD_Calculator_quarterly extends BaseClass {

    @Test(groups = {"Regression","Master"})
    public void verify_CD_quarterly(){
        CDCalculatorPageFactory cd = new CDCalculatorPageFactory(driver);
        cd.enterDepositAmount(properties.getProperty("DepositAmount"));
        cd.enterTenureInMonths(properties.getProperty("tenure"));
        cd.enterROI(properties.getProperty("roi"));
        String option = "Compounded Quarterly";
        cd.selectOptionCompoundingDropdown(option);
        cd.clickSubmitBtn();
        if(properties.getProperty("quarterlyExpected").equals(cd.getResult())){
            logger.info("✅ Validation Passed - Expected = Actual = {}", cd.getResult());
        }
        else {
            logger.error("❌ Validation Failed - Expected: {} | Actual: {}", properties.getProperty("quarterlyExpected"), cd.getResult());
        }
        Assert.assertEquals(properties.getProperty("quarterlyExpected"),cd.getResult(),"Result mismatch check logs");
    }
}
