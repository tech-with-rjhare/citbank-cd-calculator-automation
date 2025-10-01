package com.cit.tests;

import com.cit.pages.CDCalculatorPageFactory;
import com.cit.testBase.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC007_Test_CD_Calculator_halfYearly extends BaseClass {

    @Test(groups = {"Regression","Master"})
    public void verify_CD_halfYearly(){
        CDCalculatorPageFactory cd = new CDCalculatorPageFactory(driver);
        cd.enterDepositAmount(properties.getProperty("DepositAmount"));
        cd.enterTenureInMonths(properties.getProperty("tenure"));
        cd.enterROI(properties.getProperty("roi"));
        String option = "Compounded Semi-Annually";
        cd.selectOptionCompoundingDropdown(option);
        cd.clickSubmitBtn();
        if(properties.getProperty("halfYearlyExpected").equals(cd.getResult())){
            logger.info("✅ Validation Passed - Expected = Actual = {}", cd.getResult());
        }
        else {
            logger.error("❌ Validation Failed - Expected: {} | Actual: {}", properties.getProperty("halfYearlyExpected"), cd.getResult());
        }
        Assert.assertEquals(properties.getProperty("halfYearlyExpected"),cd.getResult(),"Result mismatch check logs");
    }
}
