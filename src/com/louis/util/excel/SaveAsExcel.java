package com.louis.util.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class SaveAsExcel {
	/*
	 * @@fileName:文件路径
	 * @@nameA:第一列的名字
	 * @@nameB:第二列的名字
	 * @@listA：第一列的数据
	 * @@listB:第二列的数据
	 * */
	public static void save(String fileName,String nameA,String nameB,ArrayList<Integer> listA,ArrayList<Integer> listB) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("table");  //创建table工作薄
		HSSFRow row;
		HSSFCell cell;
		row = sheet.createRow(0);//创建表格行
		cell = row.createCell(0);// 根据表格行创建单元格
		cell.setCellValue(nameA);
		cell = row.createCell(1);
		cell.setCellValue(nameB);
		for(int i = 1; i <= listA.size(); i++) {
		    row = sheet.createRow(i);//创建表格行
			cell = row.createCell(0);// 根据表格行创建单元格
			cell.setCellValue((double)listA.get(i-1));
			cell = row.createCell(1);// 根据表格行创建单元格
			cell.setCellValue((double)listB.get(i-1));
		}
		try {
			File file = new File(fileName);
			if(file.exists()) {
				file.delete();
			}
			wb.write(new FileOutputStream(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void save2(String fileName,String nameA,String nameB,ArrayList<Integer> listA, ArrayList<Float> listB) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("table");  //创建table工作薄
		HSSFRow row;
		HSSFCell cell;
		row = sheet.createRow(0);//创建表格行
		cell = row.createCell(0);// 根据表格行创建单元格
		cell.setCellValue(nameA);
		cell = row.createCell(1);
		cell.setCellValue(nameB);
		for(int i = 1; i < listA.size(); i++) {
		    row = sheet.createRow(i);//创建表格行
			cell = row.createCell(0);// 根据表格行创建单元格
			cell.setCellValue((double)listA.get(i));
			cell = row.createCell(1);// 根据表格行创建单元格
			cell.setCellValue((double)listB.get(i));
		}
		try {
			File file = new File(fileName);
			if(file.exists()) {
				file.delete();
			}
			wb.write(new FileOutputStream(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void save3(String fileName,String nameB,ArrayList<Float> listB) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("table");  //创建table工作薄
		HSSFRow row;
		HSSFCell cell;
		row = sheet.createRow(0);//创建表格行
		cell = row.createCell(0);// 根据表格行创建单元
		cell.setCellValue(nameB);
		for(int i = 1; i <= listB.size(); i++) {
		    row = sheet.createRow(i);//创建表格行
			cell = row.createCell(0);// 根据表格行创建单元格
			cell.setCellValue((double)listB.get(i-1));
		}
		try {
			File file = new File(fileName);
			if(file.exists()) {
				file.delete();
			}
			wb.write(new FileOutputStream(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @name: 多个列名
	 * @list:多个列的列表
	 * */
	public static void save4(String fileName, ArrayList<String> name, ArrayList<ArrayList<Float>> list) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("table");  //创建table工作薄
		HSSFRow row;
		HSSFCell cell;
		for(int i = 0; i < name.size();i++) {
			row = sheet.createRow(0);//创建表格行
			cell = row.createCell(i);// 根据表格行创建单元
			cell.setCellValue(name.get(i));
			
			ArrayList<Float> tempList = list.get(i);
			
			for(int j= 1; j <= tempList.size(); j++) {
			    row = sheet.createRow(j);//创建表格行
				cell = row.createCell(i);// 根据表格行创建单元格
				cell.setCellValue((double)tempList.get(j-1));
			}
		}
		
		try {
			File file = new File(fileName);
			if(file.exists()) {
				file.delete();
			}
			wb.write(new FileOutputStream(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
