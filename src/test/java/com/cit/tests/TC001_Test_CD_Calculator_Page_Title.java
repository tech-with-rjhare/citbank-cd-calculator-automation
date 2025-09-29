package com.cit.tests;


import com.cit.testBase.BaseClass;
import org.testng.annotations.Test;


public class TC001_Test_CD_Calculator_Page_Title extends BaseClass {

    @Test()
     public void TC001_testTitleOfPage(){
        logger.info("***** Start : TC001_Test_CD_Calculator_Page_Title  ****");
        String titleOfPage = "CD Calculator | Certificate of Deposit Calculator | CIT Bank";
        softAssert.assertEquals(titleOfPage,page.expectedTitleOfPage());
        logger.info("Application Result -> {}", titleOfPage);
        logger.info("***** End : TC001_Test_CD_Calculator_Page_Title  ****");
        softAssert.assertAll();
    }

}
