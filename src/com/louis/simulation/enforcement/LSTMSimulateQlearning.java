package com.louis.simulation.enforcement;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import com.louis.Vm.Vm;
import com.louis.Calculation.cpuCalculation;
import com.louis.Markov.StateDependOnUtilization;
import com.louis.cloudlet.Cloudlet;
import com.louis.staticVariable.Constants;
import com.louis.util.excel.SaveAsExcel;
import com.louis.utils.file.ArrayListToFile;
import com.louis.utils.file.NumberOfRequest;
import com.louis.utils.file.NumberOfRequest2;
import com.louis.utils.file.PredictToExcel;

public class LSTMSimulateQlearning {
	public static int time =0;     //记录时间，以s为单位
	public static ArrayList<Integer> sla = new ArrayList<>();  //记录每秒的延迟时间
	public static ArrayList<Integer> sla300 = new ArrayList<>(); //记录每300秒的延迟时间
	public static boolean isAddVM = false;
	public static int timeTemp = 0;  //增加vm时需要考虑开机时间，这个变量是保存执行execute的时刻
	public static ArrayList<Float> nextIntervalRequestlist5 = PredictToExcel.predictValue();//从预测文件中读取
	public static int nextPredictTime = 1;//用于获取下一个预测值
	public static int executeVmCount = 0;//调度虚拟机的次数
	public static void main(String[] args) {
		System.out.println("开始仿真");
		PrintStream out=null;
		PrintStream mylog;
		try {
			mylog = new PrintStream(Constants.logPath);
			out=System.out;
			System.setOut(mylog);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < Constants.numberOfRequestTen.size(); i++) {
			Constants.numberOfRequest.add(Constants.numberOfRequestTen.get(i)*Constants.multiple);  //numberOfRequest=在日志的请求记录上*multiple
		}
        
		Constants.numberOfRequest5 = NumberOfRequest2.getNumberOfRequest5(Constants.numberOfRequest);  //5分钟的请求量
		
		ArrayListToFile.arrayListToFile(Constants.numberOfRequestTen, "F:/VmExperiment/numberOfRequest/numberOfRequestTen.txt");
		ArrayListToFile.arrayListToFile(Constants.numberOfRequest, "F:/VmExperiment/numberOfRequest/numberOfRequest.txt");
		ArrayListToFile.arrayListToFile(Constants.numberOfRequest5, "F:/VmExperiment/numberOfRequest/numberOfRequest5.txt");
	
		
		
//-------------------------------------------start:仿真MAPE----------------------------------------------------------------//	
	
        /*********处理业务逻辑**********************/
        while(time<=Constants.terminateTime) {
        	if(time==0) {
        		System.out.println("~~~~~~~~~~~~第"+time+"时刻的处理~~~~~~~~~~~~");
        		createVm(time, Constants.initVmNumber);    //初始化虚拟机     
        		createCloudlet(time, Constants.numberOfRequest.get(time));  //创建任务
        		cloudletToVm();   //将任务绑定到vm上
        		Constants.vmNumAll.add(Constants.vmList.size());  //将此时的虚拟机个数添加到vmNumAll
        	}else {
        		System.out.println("~~~~~~~~~~~~第"+time+"时刻的处理~~~~~~~~~~~~");
            	createCloudlet(time, Constants.numberOfRequest.get(time));   //**需要修改成按秒创建任务**
       		    cloudletToVm();
        	}
        	
        	//模拟vm处理任务
        	process();    
        	time =time+1;
        	
    		
			if(time%Constants.schedulingInterval==0&&time!=0) {
				timeTemp = time;
				//计算cpu的利用率（300秒）计算一次的
				cpuCalculation.calculateCpuUtilization300();
				float cpuU =  Constants.cpuUtilizationList300.get(Constants.time300-1);
				//提出的方案：使用预测值*/
				//2、获取下一个预测值
				if(nextPredictTime>=287) {
					nextPredictTime = 287;
				}
				float predictValue1 = Constants.numberOfRequest5.get(nextPredictTime);
				nextPredictTime++;
				//虚拟机调度
				execute(cpuU,predictValue1); 
				
				Constants.cpuUtilizationListAll.addAll(Constants.cpuUtilizationList);
				Constants.cpuUtilizationList = new ArrayList<Float>();
				Constants.vmNumAll.add(Constants.vmList.size());
				
			}
			//vm开机时间是2分钟
//			if(time-timeTemp==120&&isAddVM) {
//				createVm(time, 1);
//			}
			
        }
     
//-------------------------------------------end:结束仿真MAPE----------------------------------------------------------------//	           
//--------------------------------------------start:开始打印---------------------------------------------------------------//
        //原始的用户的请求量
        System.out.println("****原始的用户请求量****");
        for (int i = 0; i < Constants.numberOfRequestTen.size(); i++) {
        	System.out.print(Constants.numberOfRequestTen.get(i)+"  ");
		}
        //用户的请求量
        System.out.println();
        System.out.println("****乘以mutiple倍的用户请求量****");
        for (int i = 0; i < Constants.numberOfRequest.size(); i++) {
        	System.out.print(Constants.numberOfRequest.get(i)+"  ");
		}
        //计算总延迟时间
        System.out.println();
        System.out.println("****总延迟时间****");
        System.out.println(Constants.totalDelayTime);
        //全部的每隔5秒的cpu利用率
        System.out.println("****cpu利用率*****"+Constants.cpuUtilizationListAll.size());
        for (int i = 0; i < Constants.cpuUtilizationListAll.size(); i++) {
			System.out.print(Constants.cpuUtilizationListAll.get(i)+"  ");
		}
        //全部的每隔300秒的cpu利用率
        System.out.println();
        System.out.println("****每隔300秒cpu利用率*****"+Constants.cpuUtilizationList300.size());
        for (int i = 0; i < Constants.cpuUtilizationList300.size(); i++) {
			System.out.print(Constants.cpuUtilizationList300.get(i)+"  ");
		}
        //虚拟机个数
        System.out.println();
        System.out.println("****虚拟机个数****");
        for (int i = 0; i <Constants.vmNumAll.size(); i++) {
			System.out.print(Constants.vmNumAll.get(i)+" ");
		}
        //每隔5分钟的延迟时间
        System.out.println();
        int sum = 0;
		for (int i = 0; i <sla.size(); i++) {
			sum = sum +sla.get(i);
			if((i+1)%300==0) {
				sla300.add(sum);
				sum = 0;
			}
		}
        System.out.println("****sla****"+sla300.size());
        for (int i = 0; i <sla300.size(); i++) {
			System.out.print(sla300.get(i)+" ");
		}
        System.setOut(out);

//--------------------------------------------end:结束打印---------------------------------------------------------------//
       /*excel中的time时间列*/ 
        ArrayList<Integer> time288 = new ArrayList<>();   //288个5分钟
		for (int i = 0; i < 288; i++) {
			time288.add(i);
		}
		ArrayList<Integer> time17280 = new ArrayList<>();   //17280个5秒钟
		for (int i = 0; i < 17280; i++) {
			time17280.add(i);
		}
		System.out.println("虚拟机更新次数："+executeVmCount);
		/*每隔五分钟请求量*/
		SaveAsExcel.save("F:/numberOfRequest5.xls", "time", "5分钟的请求量", time288, Constants.numberOfRequest5);
		/*每隔五分钟的vm数量*/
		SaveAsExcel.save("F:/vmNumAll.xls", "time", "虚拟机的个数", time288, Constants.vmNumAll);
		/*每隔五分钟的cpu利用率*/
		SaveAsExcel.save2("F:/cpuUtilizationList300.xls", "time", "每隔5分钟的cpu利用率", time288, Constants.cpuUtilizationList300);
		/*每隔五分钟的延迟时间*/
		SaveAsExcel.save("F:/sla.xls", "time", "每隔5分钟的延迟时间", time288, Constants.slaTotalTime);
		ArrayListToFile.arrayListToFile(Constants.vmNumAll, "F:/VmExperiment/vmNumAllTxt/lstmQL.txt");
		ArrayListToFile.arrayListToFile3(Constants.cpuUtilizationList300, "F:/VmExperiment/CPUTxt/lstmQL.txt");
		ArrayListToFile.arrayListToFile(Constants.slaTotalTime, "F:/VmExperiment/SLATxt/lstmQL.txt");
		System.out.println("仿真结束");
	}
	
	/*
	 * 创建vm
	 * @id id
	 * @time vm创建时间
	 * @num 一次性创建数目
	 * */
	private static void createVm(int time,int num) {
		System.out.println("-----创建虚拟机-----");
		if (num==0) {
			return;
		}
		for (int i = 0; i < num; i++) {
			System.out.println("虚拟机id:"+Constants.VmId+"     创建时间:"+time);
			Vm vm  = new Vm(Constants.VmId, time);
			Constants.VmId++;
			Constants.vmList.add(vm);
		}

	}

	/*
	 * 创建cloudlet，单个方法
	 * @id id
	 * @time cloudlet创建时间
	 * @num 一次性创建数目
	 * */
	private static void createCloudlet(int time,int num) {
		System.out.println("-----创建服务-----");
		for (int i = 0; i < num; i++) {
			System.out.println("服务id:"+Constants.CloudletId+"     创建时间:"+time);
			Cloudlet cloudlet = new Cloudlet(Constants.CloudletId,time);
			Constants.CloudletId++;
			Constants.cloudletList.add(cloudlet);
		}
	}
	
	/*
	 * 将cloudlet部署到vm上，此处采用的是最小资源利用
	 * */
	private static void cloudletToVm() {
		int round = 0;
		//根据vm中未处理的任务大小进行排序
		Constants.vmList = sortVm();
		for (int j = 0; j < Constants.cloudletList.size(); j++) {
			int vmI = (round++)%Constants.vmList.size(); 
			Vm vm = Constants.vmList.get(vmI);// 得到第几个vm
		    ArrayList<Cloudlet> temp = vm.getCloudletsList();
		    temp.add(Constants.cloudletList.get(j));
		    vm.setCloudletsList(temp);
		}
		
		
		Constants.cloudletList = new ArrayList<Cloudlet>();  //将任务分配好后，这个任务链表就可以设置为空了
		
		System.out.println("-----服务绑定-----");
		for (int i = 0; i < Constants.vmList.size(); i++) {
			Vm vm = Constants.vmList.get(i);
			System.out.println("虚拟机"+vm.getId());
			for (int j = 0; j < vm.getCloudletsList().size(); j++) {
				System.out.println(vm.getCloudletsList().get(j).getId());
			}
		}

	}
	
	
	/*
	 * 返回按未处理任务处理排序的vm
	 * */
	private static ArrayList<Vm> sortVm() {
		ArrayList<Vm> minVmList = new ArrayList<>();
		minVmList.add(Constants.vmList.get(0));
		for (int i = 1; i < Constants.vmList.size(); i++) {
			Vm vm = Constants.vmList.get(i);
			int pos = 0;
			for (int j = 0; j < minVmList.size(); j++) {
				Vm vmJ = minVmList.get(j);
				if(vmJ.getUnProcessedCloudletsList().size()<=vm.getUnProcessedCloudletsList().size()) {
					pos++;
				}
			}
			minVmList.add(pos, vm);
		}
		System.out.println("排序后：");
		for (int i = 0; i < minVmList.size(); i++) {
			System.out.print(minVmList.get(i).getId());
		}
		System.out.println();
		return minVmList;
		
	}

	/*
	 * 虚拟机处理用户请求的逻辑
	 * */
	private static void process() {
		int delayTime = 0;   //记录本次所有vm上任务的延迟时间
		for (int i = 0; i < Constants.vmList.size(); i++) {
			Vm vm = Constants.vmList.get(i);
			ArrayList<Cloudlet> cloudletsList = vm.getCloudletsList();   
			ArrayList<Cloudlet> processedCloudletsList = vm.getProcessedCloudletsList();
			ArrayList<Cloudlet> unProcessedCloudletsList = vm.getUnProcessedCloudletsList();
			ArrayList<Cloudlet> tempCloudletsList = new ArrayList<>();//临时变量
			
			
			//先处理上一步余下的任务,然后再处理本次任务
			tempCloudletsList.addAll(unProcessedCloudletsList); 
			tempCloudletsList.addAll(cloudletsList);
			//将上一秒未处理的任务和这次任务赋值给tempCloudletsList后就可以清空了
			cloudletsList = new ArrayList<>();    
			unProcessedCloudletsList = new ArrayList<>();
			
			System.out.println("虚拟机"+vm.getId()+"需要处理的任务数："+tempCloudletsList.size());   //打印本次需要执行的操作数目
			
			for (int j = 0; j < Constants.processCapacity; j++) {
				if(tempCloudletsList.size()!=0) {
					Cloudlet cloudlet = tempCloudletsList.remove(0);
					processedCloudletsList.add(cloudlet);
				}
			}
		
			int tempSize = tempCloudletsList.size();
			for (int j = 0; j < tempSize; j++) {  //如果还有剩余的任务没有处理，则加入到未处理队列中
				if(tempCloudletsList.size()!=0) {
					Cloudlet cloudlet = tempCloudletsList.remove(0);
					unProcessedCloudletsList.add(cloudlet);	
				}

			}
			
//			//记录延迟时间，延迟一个就是一秒
//			totalDelayTime = totalDelayTime+unProcessedCloudletsList.size();   
//			delayTime = delayTime +unProcessedCloudletsList.size();
			
			System.out.println("虚拟机"+vm.getId()+"已经处理的任务数："+processedCloudletsList.size());
			System.out.println("虚拟机"+vm.getId()+"尚未处理的任务数："+unProcessedCloudletsList.size());
			System.out.println();
			
			//将上述三个链表信息更新到vm对象中
			vm.setCloudletsList(cloudletsList);
			vm.setProcessedCloudletsList(processedCloudletsList);
			vm.setUnProcessedCloudletsList(unProcessedCloudletsList);
		
		}
		
		for (int i = 0; i < Constants.vmList.size(); i++) {
			delayTime = delayTime +Constants.vmList.get(i).getUnProcessedCloudletsList().size();
			Constants.totalDelayTime = Constants.totalDelayTime +Constants.vmList.get(i).getUnProcessedCloudletsList().size();
		}
		System.out.println("目前总延迟时间为："+delayTime);
		sla.add(delayTime);
	}

	
	

//--------------------------------------------start:vm调度部分---------------------------------------------------------------//
	
	private static void execute(float cpuU,float predictValue) {
		boolean isAdd = false;
		boolean isDel = false;
		float nextCpuUtilization = cpuCalculation.calculateCpuUtilization300(predictValue);
		String nextState = StateDependOnUtilization.stateDependOnUtilization(nextCpuUtilization);
		int x = 0;
		if (nextState.equals("over")) {
			x = 0;
		} else if (nextState.equals("normal")) {
			x = 1;
		} else if (nextState.equals("under")) {
			x = 2;
		}
		int y = 0;
		float q[][] = Constants.q;
		float max = q[x][0];
		for (int i = 1; i < q[x].length; i++) {
			if(q[x][i]>max) {
				max = q[x][i];
				y = i;
			}
		}
		if(y==0) {//增加虚拟机
			isAdd = true;
			
		}else if(y==1){
			System.out.println("不变");
		}else {
			isDel = true;
			
		}
		
		
		if(isAdd==true) {
			int addNum = 0;
			addNum=1;
			if(predictValue>Constants.vmList.size()*300*0.7) {
				addNum=addNum+1;
				if(predictValue>Constants.vmList.size()*300*0.7) {
					int mul = (int) (predictValue/(300*0.7)); //预测值是当前处理的多少倍
					if(mul-Constants.vmList.size()>=3) {
						addNum=3;
					}
				}
			}
			createVm(time, addNum);
			executeVmCount++;
		   
		}
		if(isDel==true) {
			if(cpuU<0.3) {
				deleteVm(1);
				executeVmCount++;
			}
			
		}

	}
	
	
	/*
	 * 创建vm
	 * @id id
	 * @time vm创建时间
	 * @num 一次性创建数目
	 * */
	private static void deleteVm(int deleteVmNum) {
		if(deleteVmNum==0) {
			return;
		}
		if (Constants.vmList.size()==1) {
			System.out.println("虚拟机还剩下一个，不能删除");
			return ;
		}
		if(Constants.vmList.size()>deleteVmNum&&Constants.vmList.size()>1) {
			for (int i = 0; i < deleteVmNum; i++) {
				//记录下这个vm中尚未处理完的
				Constants.deleteCloudletList = Constants.vmList.get(0).getUnProcessedCloudletsList();
				
				//删除最开始的那个虚拟机
				Constants.vmList.remove(0);
			}
		}else {
			System.out.println("删除不合法");
			return;
		}
		
		if(Constants.deleteCloudletList.size()>0) {
			System.out.println("vm中任务需要迁移");
			migrate();
		}
		

	}
	
	private static void migrate() {
		Constants.vmList=sortVm();
		int round = 0;
		for (int j = 0; j < Constants.deleteCloudletList.size(); j++) {
			int vmI = (round++)%Constants.vmList.size(); 
			Vm vm = Constants.vmList.get(vmI);// 得到第几个vm
		    ArrayList<Cloudlet> temp = vm.getCloudletsList();
		    temp.add(Constants.deleteCloudletList.get(j));
		    vm.setCloudletsList(temp);
		}
		
		
/*轮询方式*/
//		for (int j = 0; j < deleteCloudletList.size(); j++) {
//			int vmI = (roundRobinId++)%vmList.size(); 
//			Vm vm = vmList.get(vmI);// 得到第几个vm
//		    ArrayList<Cloudlet> temp = vm.getCloudletsList();
//		    temp.add(deleteCloudletList.get(j));
//		    vm.setCloudletsList(temp);
//		}
		
		Constants.deleteCloudletList = new ArrayList<Cloudlet>();  
		
		
		/*打印重新绑定的结果*/
		System.out.println("-----服务重新绑定-----");
		for (int i = 0; i < Constants.vmList.size(); i++) {
			Vm vm = Constants.vmList.get(i);
			System.out.println("虚拟机"+vm.getId());
			for (int j = 0; j < vm.getCloudletsList().size(); j++) {
				System.out.println(vm.getCloudletsList().get(j).getId());
			}
		}

	}
	
//--------------------------------------------end:vm调度部分---------------------------------------------------------------//	

}
