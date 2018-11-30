//任务结束时间
package com.louis.utils.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.tribes.util.StringManager;

import jdk.management.resource.internal.TotalResourceContext;
import jdk.nashorn.internal.ir.ReturnNode;

/*
 * 复制NumberofRequest
 * 需要对数据格式处理：1、删除双引号2、删除HTTP/1.0  3、删除01/Jul/ 4、将：替换成-
 * */

public class NumberOfRequest {
	public static List<Integer> list = new ArrayList<>();
	public static HashMap<Integer, Integer>  timeMap = new HashMap<>();   //记录<时刻，次数>
	public static ArrayList<Integer> timeList = new ArrayList<>();   //时间序列有多少个请求,即为每秒所对应的 请求是多少
	public static ArrayList<Integer> getNumberOfRequest(){
		extractData();
		return timeList;
	}
	
	public static void extractData() {
		
		//String fileName = "C:\\Users\\michael\\Desktop\\accessCopy";
		//String fileName =System.getProperty("user.dir")+"\\data\\source\\access7";
		String fileName =System.getProperty("user.dir")+"\\data\\sourceClarkNet\\access6";
		String str = "1995-";
		int line = 0;//记录行数
		
		File file = new File(fileName);
		
		BufferedReader reader = null;  
        try {  
            //System.out.println("以行为单位读取文件内容，一次读一整行：");  
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            
           
            // 一次读入一行，直到读入null为文件结束  
            while ((tempString = reader.readLine()) != null) { 
            	line++;

                int index = tempString.indexOf(str);  //查看str的位置
                int tempTime = getTime(tempString, index);  //得到时间戳

                saveAsMap(tempTime);
            }  
            reader.close();  
            
            for(int startTime = 0;startTime<86400;startTime++) {
            	if(timeMap.containsKey(startTime)) {
            		timeList.add(timeMap.get(startTime));
            	}else {
            		timeList.add(0);
            	}
            }
//            System.out.println("打印");
//            for (int i = 0; i < 50; i++) {
//				System.out.println(timeList.get(i));
//			}

 
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) { 
            	 try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }  
        }  
	}
	public static void main(String[] args) throws IOException {
		int min = 0;
		ArrayList<Integer> arrayList = getNumberOfRequest();
		
		ArrayList<Integer> list = new ArrayList<>();
		
		int count = 0;
		for (int i = 0; i < arrayList.size(); i++) {
            
			min = min+arrayList.get(i);
			count++;
			if(count%300==0) {
				list.add(min);
				min = 0;
			}
			
		}
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum = sum+list.get(i);
		}
		System.out.println(sum);
		
	 	ArrayListToFile.arrayListToFile(list, "d:/2.txt");
		
		int totalnum= 0;
		

//		 int totalNum = 0;    //总共的请求数：64714 /86400 = 0.7
//		 ArrayList<Integer> arrayList = getNumberOfRequest();
//		 for (int i = 0; i < 86400; i++) {
//			totalNum = totalNum+arrayList.get(i);
//		}
//		 System.out.println(totalNum);
	}


	/*
	 * 通过日志文件获得时刻戳
	 * */
	public static int getTime(String  tempString,int index) {
		index = index+4;
		//小时
		String hour = tempString.substring(index+1,index+3);
	   ////System.out.println(hour);
		//分钟
		String min = tempString.substring(index+4,index+6);
		////System.out.println(min);
		//秒
		String sec = tempString.substring(index+7,index+9);
		////System.out.println(sec);
		//时刻
		int time = 0;
		time = Integer.parseInt(hour)*3600+Integer.parseInt(min)*60+Integer.parseInt(sec);
		return time;
	}
	
	/*
	 * 将获得的时间保存为map<时刻，次数>
	 * */
	public static void saveAsMap(int tempTime) {
		if(timeMap.containsKey(tempTime)) {
			int c = timeMap.get(tempTime);
		    timeMap.put(tempTime, c+1);    //如果在map中则加1
		}else {
			timeMap.put(tempTime, 1);
		}
	}
	
	
	/*
	 * 得到5分钟的请求量
	 * */
	public static ArrayList<Integer>  getNumberOfRequest5(ArrayList<Integer> arrayList ) {
		int min = 0;
		ArrayList<Integer> list = new ArrayList<>();
		
		int count = 0;
		for (int i = 0; i < arrayList.size(); i++) {
            
			min = min+arrayList.get(i);
			count++;
			if(count%300==0) {
				list.add(min);
				min = 0;
			}		
		}
		return list;
	}
	
}
