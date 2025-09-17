package com.cit.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelUtils {
    private static FileInputStream fi;
    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    private static XSSFRow row;
    private static XSSFCell cell;
    private static DataFormatter formatter;

    public static int getRowCount(String xlfilepath, String xlSheet) throws Exception{
    fi = new FileInputStream(xlfilepath);
    workbook = new XSSFWorkbook(fi);
    sheet = workbook.getSheet(xlSheet);
    int rowCount = sheet.getLastRowNum();
    workbook.close();
    fi.close();
    return (rowCount+1);
    }

    public static String getCellData(String xlFilePath, String xlSheet, int rowNum, int colNum) throws Exception{
    fi = new FileInputStream(xlFilePath);
    workbook = new XSSFWorkbook(fi);
    sheet = workbook.getSheet(xlSheet);
    row = sheet.getRow(rowNum);
    cell = row.getCell(colNum);
    String data;
        try {
            //data =cell.toString();
            DataFormatter formatter = new DataFormatter();
            data = formatter.formatCellValue(cell);
        } catch (Exception e) {
            data = "";
        }
        workbook.close();
        fi.close();
        return data;
    }

    public static void getrowdata(String xlFilePath, String xlSheet) throws Exception{

        formatter = new DataFormatter();
        fi = new FileInputStream(xlFilePath);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(xlSheet);
        int rowCount = getRowCount(xlFilePath,xlSheet);
        int cellCount = 0;
        for(int i =0;i< rowCount;i++){
            row = sheet.getRow(i);
            cellCount = row.getLastCellNum();
            for(int j = 0; j < cellCount; j++ ) {
                cell = row.getCell(j);
                System.out.print(cell.toString()+"\t");
                //String cellData = formatter.formatCellValue(cell);
                //System.out.print(cellData + " | ");
            }

            System.out.println();
        }
    }

}
