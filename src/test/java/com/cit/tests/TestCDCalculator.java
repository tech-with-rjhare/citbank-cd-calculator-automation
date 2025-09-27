package com.cit.tests;


import com.cit.utils.ExcelUtils;
import org.testng.annotations.Test;

import java.time.Duration;



public class TestCDCalculator extends BaseClass{

    private String depositAmount = null;
    private String lengthOfCD = null; // In months
    private String interestRate = null;


    @Test()
     void TC001_testTitleOfPage(){
        logger.info("***** Starting TC001_testTitleOfPage  ****");
        logger.debug("This is a debug log message");
        String titleOfPage = "CD Calculator | Certificate of Deposit Calculator | CIT Bank";
        softAssert.assertEquals(titleOfPage,page.expectedTitleOfPage());
        softAssert.assertAll();
    }

    @Test(priority = 1,dependsOnMethods = {"TC001_testTitleOfPage"})
    void TC002_testCIDaily() throws Exception {
        String xlFilePath = System.getProperty("user.dir") + "\\test-data\\testdata_cdcalculator.xlsx";
        int rowCount = ExcelUtils.getRowCount(xlFilePath, "Compounded_Daily");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        for(int row=1; row<rowCount;row++){

        depositAmount = ExcelUtils.getCellData(xlFilePath, "Compounded_Daily", row, 0);
        lengthOfCD = ExcelUtils.getCellData(xlFilePath, "Compounded_Daily", row, 1);
        interestRate = ExcelUtils.getCellData(xlFilePath, "Compounded_Daily", row, 2);
        //System.out.println(depositAmount +" | "+lengthOfCD+" | "+interestRate);

        page.enterDepositAmount(depositAmount);
        page.enterTenureInMonths(lengthOfCD);
        page.enterROI(interestRate);
        page.selectOptionCompoundingDropdown("Compounded Daily");
        page.clickSubmitBtn();

        String expectedRes = ExcelUtils.getCellData(xlFilePath, "Compounded_Daily", row, 4);
        String actualRes = page.getResult();
        //System.out.println("Actual result : " + actualRes + " " + "expectedRes : " + expectedRes);
/*            if (actualRes.equals(expectedRes)) {
                ExcelUtils.setCellData(xlFilePath, "Compounded_Daily", row, 6, "Passed");
                ExcelUtils.fillGreenColor(xlFilePath, "Compounded_Daily", row, 6);
            } else {
                ExcelUtils.setCellData(xlFilePath, "Compounded_Daily", row, 6, "Failed");
                ExcelUtils.fillRedColor(xlFilePath, "Compounded_Daily", row, 6);
            }*/

    }
            //Assert.assertEquals(expectedRes, actualRes,"Calculation logic doesn't match with requirement");

    }

    @Test(priority = 2,dependsOnMethods = {"TC001_testTitleOfPage"})
    void TC003_testCIMonthly() throws Exception {
        String xlFilePath = System.getProperty("user.dir") + "\\test-data\\testdata_cdcalculator.xlsx";
        int rowCount = ExcelUtils.getRowCount(xlFilePath, "Compounded_Monthly");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        for(int row=1; row<rowCount;row++){

            depositAmount = ExcelUtils.getCellData(xlFilePath, "Compounded_Monthly", row, 0);
            lengthOfCD = ExcelUtils.getCellData(xlFilePath, "Compounded_Monthly", row, 1);
            interestRate = ExcelUtils.getCellData(xlFilePath, "Compounded_Monthly", row, 2);
            //System.out.println(depositAmount +" | "+lengthOfCD+" | "+interestRate);

            page.enterDepositAmount(depositAmount);
            page.enterTenureInMonths(lengthOfCD);
            page.enterROI(interestRate);
            page.selectOptionCompoundingDropdown("Compounded Monthly");
            page.clickSubmitBtn();

            String expectedRes = ExcelUtils.getCellData(xlFilePath, "Compounded_Monthly", row, 4);
            String actualRes = page.getResult();
            //System.out.println("Actual result : " + actualRes + " " + "expectedRes : " + expectedRes);
            /*if (actualRes.equals(expectedRes)) {
                ExcelUtils.setCellData(xlFilePath, "Compounded_Monthly", row, 7, "Passed");
                ExcelUtils.fillGreenColor(xlFilePath, "Compounded_Monthly", row, 7);
            } else {
                ExcelUtils.setCellData(xlFilePath, "Compounded_Monthly", row, 7, "Failed");
                ExcelUtils.fillRedColor(xlFilePath, "Compounded_Monthly", row, 7);
            }
*/
        }
        //Assert.assertEquals(expectedRes, actualRes,"Calculation logic doesn't match with requirement");

    }


}
