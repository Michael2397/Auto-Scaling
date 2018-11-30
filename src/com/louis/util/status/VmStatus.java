package com.louis.util.status;

import java.util.ArrayList;
import java.util.HashMap;

import com.louis.util.regression.LinearRegression;
import com.louis.utils.file.NumberOfRequest;

public class VmStatus {
 
	public static ArrayList<String> vmNextStatus300 = new ArrayList<>();   //预测vm下一阶段的状态
	
	/*
	 * 功能：返回下一个状态是什么
	 * 传入参数list:300秒的请求数
	 * */
	public static ArrayList<String> getVmNextStatus300(ArrayList<Integer> list){
		int time = 5;  //从第五个计算
		vmNextStatus300.add(null);
		vmNextStatus300.add(null);
		vmNextStatus300.add(null);
		vmNextStatus300.add(null);
		HashMap<Integer, Integer> hashMap = new HashMap<>();
		for ( ; time<=list.size(); time++) {
			hashMap.put(time, list.get(time-1));
			hashMap.put(time-1, list.get(time-2));
			hashMap.put(time-2, list.get(time-3));
			hashMap.put(time-3, list.get(time-4));
			hashMap.put(time-4, list.get(time-5));
			
			float predictedValue = LinearRegression.getPredictedValue(hashMap, time+1);
			String nextStatus = "";
			if(predictedValue>list.get(time-1)) {
				nextStatus = "Over";
			}else if (predictedValue<list.get(time-1)) {
				nextStatus = "Under";
			}else {
				nextStatus = "Normal";
			}
			
			vmNextStatus300.add(time-1, nextStatus);
		}
		
		
		return vmNextStatus300;
	}
	
}
