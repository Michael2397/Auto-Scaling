package com.louis.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * 提取前64714行的数据，将前64714行的数据单独存放在一个文件中
 * */
public class ReadAndSaveFile {
	public static void main(String[] args) {
		String string = System.getProperty("user.dir");
	    String fileName = string+"\\data\\source\\access";
	    String strFilename =string+"\\data\\source\\access2";
		 try  
		    {      
		      // 创建文件对象  
		      File fileText = new File(strFilename);  
		      // 向文件写入对象写入信息  
		      FileWriter fileWriter = new FileWriter(fileText);  
		      
		      File file = new File(fileName);
		      BufferedReader  reader = new BufferedReader(new FileReader(file));  
		      String tempString = null;  
		      int line=0;
		      while ((tempString = reader.readLine()) != null) {
		    	  tempString = tempString.replaceAll("\"","");
		    	  tempString = tempString.replaceAll("HTTP/1.0","");
		    	  tempString = tempString.replaceAll("01/Jul/","");
		    	  tempString = tempString.replaceAll(":","-");
		    	  line++;
		    	  if(line == 64715) {
	            		break;     //只记录7月1日的情况
		    	  }
		    	  // 写文件        
			      fileWriter.write(tempString+"\n"); 
			      
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
