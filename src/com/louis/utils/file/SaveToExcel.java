package com.louis.utils.file;

import java.util.ArrayList;

import com.louis.util.excel.SaveAsExcel;

/***
 * 
 * 将提取的特征保存到excel中
 * */
public class SaveToExcel {
    public static void main(String[] args) {
    	System.out.println("开始");
	
		
		ArrayList<String> name  =  new ArrayList<>();
		name.add("year");
		name.add("month");
		name.add("day");
		name.add("week");
		name.add("hour");
		name.add("minute");
//		name.add("second");
		name.add("request");
		name.add("maxReq20minutes");  //当前时刻的前20分钟的请求量最大值
		name.add("minReq20minutes");  //当前时刻的前20分钟的请求量最小值
		name.add("meanReq20minutes");  //当前时刻的前20分钟的请求量平均值
		
		
		ArrayList<ArrayList<Float>> list =  new ArrayList<>();
		ArrayList<Float> year =  new ArrayList<>();
		ArrayList<Float> month =  new ArrayList<>();
		ArrayList<Float> day =  new ArrayList<>();
		ArrayList<Float> week =  new ArrayList<>();
		ArrayList<Float> hour =  new ArrayList<>();
		ArrayList<Float> minute =  new ArrayList<>();
		ArrayList<Float> second =  new ArrayList<>();
		ArrayList<Float> request =  new ArrayList<>();
		ArrayList<Float> maxReq20minutes =  new ArrayList<>();
		ArrayList<Float> minReq20minutes =  new ArrayList<>();
		ArrayList<Float> meanReq20minutes =  new ArrayList<>();

		//年
		for(int i = 0; i<28*288; i++) {
			year.add((float) 1995);
		}
		list.add(year);
		
		//月
		for(int i = 0; i<28*288; i++) {
			month.add((float) 7);
		}
		list.add(month);
		
		//日
		for(int i = 0; i<28*288; i++) {
			day.add((float) (i/(24*12))+1);
		}
		list.add(day);
		
		//星期
		week.add((float) 6);
		week.add((float) 7);
		int w = 0;
		for(int i = 3; i<=28; i++) {
			if(3<=i && i<=9) {
				w = 0;
			} else if (10<=i && i<=16) {
				w = 1;
			} else if (17<=i && i<=23) {
				w = 2;
			}else if (24<=i && i<=28) {
				w = 3;
			}
			week.add((float) (i-2-(7*w)));
		}
		ArrayList<Float> weekTemp = new ArrayList<>();
		int temp = 0;
		for(int i = 0; i<28*288; i++) {
			weekTemp.add(week.get(temp));
			if((i+1)%288 == 0) {
				temp++;
			}
		}
		
		list.add(week);
		
		//时
		int count = 0;
		int h = 0;
		while(true) {
			if(count == 28*288) {
				break;
			}
			hour.add((float)(h%24));
			count++;
			if(count % 12 == 0) {
				h++;
			}
		}
		list.add(hour);
		
		//分
        count = 0;
        int m = 0;
        while(true) {
        	if(count == 28*24*12) {
        		break;
        	}
        	minute.add((float)m*5);
        	count++;
        	m++;
        	if(m%12==0) {
        		m = 0;
        	}
        }
        list.add(minute);
        
        //秒
      	for(int i = 0; i<28*288; i++) {
      		second.add((float) 0);
      	}
      	list.add(second);
        
      	
      	//request
      	request = getRequestNumber();
      	list.add(request);
      	
      	//max
      	maxReq20minutes =  getMaxRequestNumber(request);
      	list.add(maxReq20minutes);
      	
      	
      	//min
      	minReq20minutes = getMinRequestNumber(request);
      	list.add(minReq20minutes);
      	
      	//mean
      	meanReq20minutes = getMeanRequestNumber(request);
      	list.add(meanReq20minutes);
      	
    	String fileName = "F:\\autoScaling\\request\\NASA\\meanReq20minutes.xls";
      	
      	SaveAsExcel.save4(fileName, name, list);
        SaveAsExcel.save3(fileName, "year", year);
      	SaveAsExcel.save3(fileName, "month", month);
      	SaveAsExcel.save3(fileName, "day", day);
      	SaveAsExcel.save3(fileName, "week", weekTemp);
      	SaveAsExcel.save3(fileName, "hour", hour);
      	SaveAsExcel.save3(fileName, "minute", minute);
    	SaveAsExcel.save3(fileName, "request", request);
    	SaveAsExcel.save3(fileName, "maxReq20minutes", maxReq20minutes);
     	SaveAsExcel.save3(fileName, "minReq20minutes", minReq20minutes);
      	SaveAsExcel.save3(fileName, "meanReq20minutes", meanReq20minutes);
      	System.out.println("结束");
	}
    
    
    private static ArrayList<Float> getRequestNumber() {
    	ArrayList<Float> request = new ArrayList<>();
    	int multiple = 3;//请求量扩大的倍数
		for (int j = 1; j <= 28; j++) {
			int min = 0;
			String fileName =System.getProperty("user.dir")+"\\data\\source\\access"+j;
//			String fileName =System.getProperty("user.dir")+"\\data\\sourceClarkNet\\access"+j;
			ArrayList<Integer> arrayList  = NumberOfRequest2.getNumberOfRequest(fileName);  //获取的是每一秒的请求量
			ArrayList<Float> list = new ArrayList<>();  //保存的是每5分钟的请求量
			int count = 0;
			for (int i = 0; i < arrayList.size(); i++) {
	            
				min = min+arrayList.get(i)*multiple;
				count++;
				if(count%300==0) {
					list.add((float)min);
					min = 0;
				}
				
			}
			request.addAll(list);
			
		}
    	return request;
    }
    
    private static ArrayList<Float> getMaxRequestNumber(ArrayList<Float> request){
    	ArrayList<Float> maxRequest = new ArrayList<>();
    	int total = 28*24*12;
    	for(int i =0 ;i<total; i++) {
    		if(i == 0 || i==1 || i==2 || i == 3) {
    			maxRequest.add(request.get(i));
    		} else {
    			float max = request.get(i-1);
    			max = Math.max(max, request.get(i-2));
    			max = Math.max(max, request.get(i-3));
    			max = Math.max(max, request.get(i-4));
    			maxRequest.add(max);
    		}
    	}
    	return maxRequest;
    }
    
    private static ArrayList<Float> getMinRequestNumber(ArrayList<Float> request){
    	ArrayList<Float> minRequest = new ArrayList<>();
    	int total = 28*24*12;
    	for(int i =0 ;i<total; i++) {
    		if(i == 0 || i==1 || i==2 || i == 3) {
    			minRequest.add(request.get(i));
    		} else {
    			float min = request.get(i-1);
    			min = Math.min(min, request.get(i-2));
    			min = Math.min(min, request.get(i-3));
    			min = Math.min(min, request.get(i-4));
    			minRequest.add(min);
    		}
    	}
    	return minRequest;
    }
    
    private static ArrayList<Float> getMeanRequestNumber(ArrayList<Float> request){
    	ArrayList<Float> meanRequest = new ArrayList<>();
    	int total = 28*24*12;
    	for(int i =0 ;i<total; i++) {
    		if(i == 0 || i==1 || i==2 || i==3) {
    			meanRequest.add(request.get(i));
    		} else {
    			float sum = request.get(i-1) + request.get(i-2) + request.get(i-3) + request.get(i-4);
    			
    			meanRequest.add(sum/4);
    		}
    	}
    	return meanRequest;
    }
    
    
}	
