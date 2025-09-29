package com.cit.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {
    private FileInputStream fi;
    private FileOutputStream fo;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row;
    private XSSFCell cell;
    private DataFormatter formatter;
    private XSSFCellStyle style;
    private String xlfilepath;

    public ExcelUtils(String xlfilepath){
        this.xlfilepath = xlfilepath;
    }

    public int getRowCount(String xlSheet) throws IOException{
    fi = new FileInputStream(xlfilepath);
    workbook = new XSSFWorkbook(fi);
    sheet = workbook.getSheet(xlSheet);
    int rowCount = sheet.getPhysicalNumberOfRows();
    workbook.close();
    fi.close();
    return (rowCount);
    }

    public int getCellCount(String xlSheet) throws IOException {
        int cellCount = 0;
        fi = new FileInputStream(xlfilepath);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(xlSheet);
        row = sheet.getRow(1);
        cellCount=row.getPhysicalNumberOfCells();
        workbook.close();
        fi.close();
        return cellCount;
    }

    public String getCellData(String xlSheet, int rowNum, int colNum) throws IOException{
    fi = new FileInputStream(xlfilepath);
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

     /*// Just check that datas of rows
    public void getrowdata(String xlSheet) throws Exception{

        formatter = new DataFormatter();
        fi = new FileInputStream(xlfilepath);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(xlSheet);
        int rowCount = getRowCount(xlSheet);
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
    }*/
    public void setCellData(String xlSheet, int rowNum, int colNum, String data) throws IOException{

        fi = new FileInputStream(xlfilepath);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(xlSheet);

        row = sheet.getRow(rowNum);
        if(row == null) row = workbook.createSheet().getRow(rowNum);

        cell = row.getCell(colNum);
        if(cell == null) cell = row.createCell(colNum);

        cell.setCellValue(data);
        fi.close();

        fo = new FileOutputStream(xlfilepath);
        workbook.write(fo);

        workbook.close();
        fo.close();
    }

    public void fillGreenColor(String xlSheet, int rowNum, int colNum) throws IOException {
        fi = new FileInputStream(xlfilepath);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(xlSheet);
        row = sheet.getRow(rowNum);
        cell = row.getCell(colNum);

        style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);
        fi.close();

        fo = new FileOutputStream(xlfilepath);
        workbook.write(fo);
        workbook.close();
        fo.close();
    }

    public void fillRedColor(String xlSheet, int rowNum, int colNum) throws IOException {
        fi = new FileInputStream(xlfilepath);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(xlSheet);
        row = sheet.getRow(rowNum);
        cell = row.getCell(colNum);

        style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);
        fi.close();

        fo = new FileOutputStream(xlfilepath);
        workbook.write(fo);
        workbook.close();
        fo.close();
    }


    /*public static void main(String[] args) throws Exception {
        String xlFilePath = System.getProperty("user.dir") + "\\test-data\\testdata_cdcalculator.xlsx";
        ExcelUtils xlfile = new ExcelUtils(xlFilePath);
        System.out.println("Row " + xlfile.getRowCount("Compounded_Daily"));
        System.out.println("Cell " + xlfile.getCellCount("Compounded_Daily"));
        xlfile.getrowdata("Compounded_Daily");
    }*/
}
