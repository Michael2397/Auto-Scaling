package com.louis.Calculation;

import java.util.ArrayList;

import com.louis.Vm.Vm;
import com.louis.staticVariable.Constants;

public class cpuCalculation {
	// -------------------------------------------start:计算cpu部分---------------------------------------------------------------//

	/*
	 * 300秒计算一次cpu利用率
	 */
	public static void calculateCpuUtilization300() {

		int requestNum = Constants.temp300 + Constants.numberOfRequest5.get(Constants.time300);
		// 遍历虚拟机，查看还有多少未被处理的
		int unRequestNum = 0;
		for (int i = 0; i < Constants.vmList.size(); i++) {
			unRequestNum = unRequestNum + Constants.vmList.get(i).getUnProcessedCloudletsList().size();
		}
		//requestNum = requestNum - unRequestNum;

		int totalPro = Constants.vmList.size() * 300; // cpu在300秒内可以处理多少个
		Constants.cpuUtilizationList300.add((float) (requestNum * 100 / totalPro) / 100);
        
		//超过0.75的都算是有延迟
		if((int) (requestNum-totalPro*0.8)>0) {
			Constants.slaTotalTime.add((int) (requestNum-totalPro*0.8));
		}else {
			Constants.slaTotalTime.add(0);
		}
		
		
		
		Constants.temp300 = unRequestNum;
		Constants.time300++; 
	}
	/*
	 * 计算下一阶段的cpu利用率
	 * @nextInterval300：下一段的请求的预测值
	 * @totalPro：当前的的vm个数
	 */
	public static float calculateCpuUtilization300(int nextInterval300) {
		int totalPro = Constants.vmList.size() * 300; // cpu在300秒内可以处理多少个
		return ((float) (nextInterval300 * 100 / totalPro) / 100);

	}
	public static float calculateCpuUtilization300(float nextInterval300) {
		int totalPro = Constants.vmList.size() * 300; // cpu在300秒内可以处理多少个
		return ((float) (nextInterval300 * 100 / totalPro) / 100);

	}

	/*
	 * 每隔五秒计算一次所有vm的cpu利用率，并保存到cpuUtilizationList中
	 */
	public static void calculateCpuUtilization() {
		int totalCpu5Sec = 0;// 记录五秒内，所有vm处理任务的总数
		for (int i = 0; i < Constants.vmList.size(); i++) {
			Vm vm = Constants.vmList.get(i);
			totalCpu5Sec = totalCpu5Sec + vm.getProcessedCloudletsList().size();
		}

		float cpuUtilizationPer5Sec = cpuUtilizationPer5Sec(totalCpu5Sec); // 每隔5秒计算一次cpu利用率
		System.out.println("cpu利用率：" + cpuUtilizationPer5Sec);
		Constants.cpuUtilizationList.add(cpuUtilizationPer5Sec);
	}

	/*
	 * 计算过cpu利用率后，将虚拟机中记录已经处理过的信息置0
	 */
	public static void setProcessedZero() {
		for (int i = 0; i < Constants.vmList.size(); i++) {
			Vm vm = Constants.vmList.get(i);
			vm.setProcessedCloudletsList(new ArrayList<>());
		}
	}

	/*
	 * cpu计算公式，五秒内所有vm已经处理的任务总数/五秒内所有vm提供的处理能力
	 */
	public static float cpuUtilizationPer5Sec(int totalCpu5Sec) {
		float cpuUtilizationPer5Sec = ((float) totalCpu5Sec)
				/ (Constants.processCapacity * Constants.vmList.size() * Constants.cpuInterval);
		return cpuUtilizationPer5Sec;
	}

	// --------------------------------------------end:计算cpu部分---------------------------------------------------------------//
}
