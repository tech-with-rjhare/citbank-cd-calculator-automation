package com.cit.tests;

import com.cit.testBase.BaseClass;
import com.cit.utils.DataProviders;
import com.cit.utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC005_Test_CD_Calculator_Monthly_DDT extends BaseClass {
     @Test(dataProvider = "CompoundMonthlyTestData", dataProviderClass = DataProviders.class, priority = 2,groups = {"Sanity","Regression","Master"})
    public void verify_CD_calculator_Monthly(
            String row,
            String depositAmount,
            String lengthOfCD,
            String interestRate,
            String compounded,
            String expectedRes
     ) throws Exception {
         try{
             logger.info("************** START: TC003_Test_CD_Calculator_Monthly ******************");
            ExcelUtils xlfile = new ExcelUtils(".//test-data//testdata_cdcalculator.xlsx");
            page.enterDepositAmount(depositAmount);
            page.enterTenureInMonths(lengthOfCD);
            page.enterROI(interestRate);
            page.selectOptionCompoundingDropdown(compounded);
            page.clickSubmitBtn();
            logger.info("✅ Successfully entered all inputs & clicked Submit");
            String actualRes = page.getResult();
            logger.info("Application Result -> {}", actualRes);
            int intRow = Integer.parseInt(row);

         if (actualRes.equals(expectedRes)) {
             logger.info("✅ Validation Passed - Expected = Actual = {}", actualRes);
             xlfile.setCellData("Compounded_Monthly",intRow,7,"Passed");
             xlfile.fillGreenColor("Compounded_Monthly",intRow,7);
         } else {
             logger.error("❌ Validation Failed - Expected: {} | Actual: {}", expectedRes, actualRes);
             xlfile.setCellData("Compounded_Monthly",intRow,7,"Fail");
             xlfile.fillRedColor("Compounded_Monthly",intRow,7);
         }
         Assert.assertEquals(actualRes, expectedRes, "Result mismatch! Check logs for details.");

        } catch (Exception e) {
            logger.error("⚠ Exception occurred in TC003_Test_CD_Calculator_Monthly:", e);
            Assert.fail("An exception occurred: " + e.getMessage());
        }finally {
        logger.info("********** END: TC003_Test_CD_Calculator_Monthly **********");
    }

    }
}
