package com.cit.tests;

import com.cit.testBase.BaseClass;
import com.cit.utils.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC002_Test_CD_Calculator_Daily_DDT extends BaseClass {

    @Test(dataProvider = "CompoundDailyTestData",dataProviderClass = DataProviders.class,priority = 1,dependsOnMethods = {"TC001_testTitleOfPage"})
    public void verify_CD_calculator_Daily(
            String depositAmount,
            String lengthOfCD,
            String interestRate,
            String compoundingDropdown,
            String expectedRes) throws Exception {
        try {
            logger.info("************** START: TC002_Test_CD_Calculator_Daily ******************");
            page.enterDepositAmount(depositAmount);
            page.enterTenureInMonths(lengthOfCD);
            page.enterROI(interestRate);
            page.selectOptionCompoundingDropdown(compoundingDropdown);
            page.clickSubmitBtn();
            logger.info("✅ Successfully entered all inputs & clicked Submit");

            String actualRes = page.getResult();
            logger.info("Application Result -> {}", actualRes);

            if (actualRes.equals(expectedRes)) {
                logger.info("✅ Validation Passed - Expected = Actual = {}", actualRes);
            } else {
                logger.error("❌ Validation Failed - Expected: {} | Actual: {}", expectedRes, actualRes);
            }
            Assert.assertEquals(actualRes, expectedRes, "Result mismatch! Check logs for details.");

        } catch (Exception e) {
            logger.error("⚠ Exception occurred in TC002_testCIDaily:", e);
            Assert.fail("An exception occurred: " + e.getMessage());
        }finally {
            logger.info("********** END: TC002_Test_CD_Calculator_Daily **********");
        }

    }
}
