package com.cit.tests;

import com.cit.pages.CDCalculatorPageFactory;
import com.cit.testBase.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC008_Test_CD_Calculator_yearly extends BaseClass {

    @Test(groups = {"Regression","Master"})
    public void verify_CD_yearly(){
        CDCalculatorPageFactory cd = new CDCalculatorPageFactory(driver);
        cd.enterDepositAmount(properties.getProperty("DepositAmount"));
        cd.enterTenureInMonths(properties.getProperty("tenure"));
        cd.enterROI(properties.getProperty("roi"));
        String option = "Compounded Annually";
        cd.selectOptionCompoundingDropdown(option);
        cd.clickSubmitBtn();
        if(properties.getProperty("yearlyExpected").equals(cd.getResult())){
            logger.info("✅ Validation Passed - Expected = Actual = {}", cd.getResult());
        }
        else {
            logger.error("❌ Validation Failed - Expected: {} | Actual: {}", properties.getProperty("yearlyExpected"), cd.getResult());
        }
        Assert.assertEquals(properties.getProperty("yearlyExpected"),cd.getResult(),"Result mismatch check logs");
    }
}
