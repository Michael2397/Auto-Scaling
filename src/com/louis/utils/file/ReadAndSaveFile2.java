package com.louis.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.Count;

/*
 * 提取一个月里所有的数据,按月存放成文件
 * */
public class ReadAndSaveFile2 {
	public static void main(String[] args) throws IOException {
		String string = System.getProperty("user.dir");
	    String fileName = string+"\\data\\source\\access";
	    ArrayList<Integer> listRow = CountRow.getRow();
	   
	    for (int i = 1; i <= 28; i++) {
	    	File file = new File(fileName);
	 	    BufferedReader  reader = new BufferedReader(new FileReader(file));  
	 	    String tempString = null;  
	    	String strFilename =string+"\\data\\source\\access"+(i);
	    	
	    	try  
		    {      
		      // 创建文件对象  
		      File fileText = new File(strFilename);  
		      // 向文件写入对象写入信息  
		      FileWriter fileWriter = new FileWriter(fileText);  
		      
		     
		      while ((tempString = reader.readLine()) != null) {
		    	  if(i<10&&tempString.contains("0"+i+"/Jul/")) {
		    		  tempString = tempString.replaceAll("\"","");
			    	  tempString = tempString.replaceAll("HTTP/1.0","");
			    	  tempString = tempString.replaceAll("0"+i+"/Jul/","0"+i+"-Jul");
			    	  tempString = tempString.replaceAll(":","-");
			    	  // 写文件        
				      fileWriter.write(tempString+"\n");
		    	  }
		    	  if(i>=10&&tempString.contains(i+"/Jul/")) {
		    		  tempString = tempString.replaceAll("\"","");
			    	  tempString = tempString.replaceAll("HTTP/1.0","");
			    	  tempString = tempString.replaceAll(i+"/Jul/",i+"-Jul");
			    	  tempString = tempString.replaceAll(":","-");
			    	  // 写文件        
				      fileWriter.write(tempString+"\n");
		    	  }
		    	
			      
		      }
		      
		       System.out.println("结束");
		      // 关闭  
		      fileWriter.close();  
		    }  
		    catch (IOException e)  
		    {  
		      //  
		      e.printStackTrace();  
		    }  
		}
		 
	}
}
