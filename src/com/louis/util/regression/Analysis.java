package com.louis.util.regression;

import java.util.ArrayList;

public class Analysis {
	public static ArrayList<String> predictedStateList = new ArrayList<>();   //通过对比预测值和真实值得到的状态链表
	public static ArrayList<Float> predictedValueList = new ArrayList<>();//预测值链表，是从第三个开始预测
	
	/*
	 * 获取预测值
	 * */
	public static void getLine(ArrayList<Integer> countCloudlet5min) {
	    RegressionLine line = new RegressionLine(); 
	    
		for (int predictedIndex = 2; predictedIndex < countCloudlet5min.size(); predictedIndex++) {
			if(predictedIndex<=5) {   //如果是预测前五个数的话，利用前面所有数据
				for (int j = 0; j < predictedIndex; j++) {
					line.addDataPoint(new DataPoint(j,countCloudlet5min.get(j)));
				}
			}
			else {  //如果预测的位置是大于5的话，则只利用此位置的前面五个数据
				for (int j = predictedIndex-5; j < predictedIndex; j++) {
					line.addDataPoint(new DataPoint(j,countCloudlet5min.get(j)));
				}
			}
			
			//预测值
			LinearRegression.printSums(line);  
	        LinearRegression.printLine(line);
	        float predictedValue = line.getA1()*predictedIndex+line.getA0();
	        predictedValueList.add(predictedValue);
		} 
   
	}
	
	/*
	 * 获得状态链表,是第三个开始预测。乘以1.2，0.8的原因：如果不设置比率，则normal状态几乎为0
	 * */
	public static void getPredictedState(ArrayList<Integer> countCloudlet5min) {
		for (int i = 0; i < predictedValueList.size(); i++) {
			if(predictedValueList.get(i)>1.2*countCloudlet5min.get(i+2)) {
				predictedStateList.add("over");
			}else if(predictedValueList.get(i)<0.8*countCloudlet5min.get(i+2)) {
				predictedStateList.add("under");
			}else {
				predictedStateList.add("normal");
			}
		}
	}
}
