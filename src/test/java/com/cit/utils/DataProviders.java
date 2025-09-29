package com.cit.utils;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    @DataProvider(name = "CompoundDailyTestData")
    public String[][] getData() throws IOException {
        String xlPath = System.getProperty("user.dir") + "\\test-data\\testdata_cdcalculator.xlsx";
        String sheet = "Compounded_Daily";
        ExcelUtils xlFile = new ExcelUtils(xlPath);
        int row = xlFile.getRowCount(sheet);
        //int col = xlFile.getCellCount(sheet);
        int col = 5;

        String[][] data = new String[row][col];

        for(int i = 1;i < row; i++){
            for(int j = 0;j < col; j++){
                data[i-1][j] = xlFile.getCellData(sheet,i,j);
            }
        }
        return data;
    }


    @DataProvider(name="CompoundMonthlyTestData")
    public String[][] getDataMonthly() throws IOException{
        String xlPath = System.getProperty("user.dir") + "\\test-data\\testdata_cdcalculator.xlsx";
        String sheet = "Compounded_Monthly";
        ExcelUtils xlfile = new ExcelUtils(xlPath);
        int rowCount = xlfile.getRowCount(sheet);

        String[][] data = new String[rowCount][6];
        for(int i = 1; i <  rowCount; i++){
            for (int j =0 ; j < 6; j++ ){
                data[i-1][j] = xlfile.getCellData(sheet,i,j);
            }

        }

        return data;
    }
    // Can Add multiple DataProvider methods for different test Data to test different modules

}
