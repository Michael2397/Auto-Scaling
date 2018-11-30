package com.louis.staticVariable;

import java.util.ArrayList;
import java.util.List;

import com.louis.Vm.Vm;
import com.louis.cloudlet.Cloudlet;
import com.louis.util.status.VmStatus;
import com.louis.utils.file.NumberOfRequest;

public class Constants {
	
	/*********时间************/
	public static int terminateTime = 86400-1;  //总共时间24*60*60=86400
	public static int schedulingInterval = 300;  //定义多少秒执行一次update()操作（即虚拟机调度操作）
	public static int cpuInterval = 5; //定义间隔多少秒计算cpu利用率
	public static int time =0;     //记录时间，以s为单位
	
	/*********任务************/
	public static int multiple = 2;//请求数据扩大的倍数
	public static int CloudletId = 0;  //cloudlet的id
	public static ArrayList<Integer> numberOfRequestTen = NumberOfRequest.getNumberOfRequest(); //原始的用户请求数量序列，基于日志得到的
	public static ArrayList<Integer> numberOfRequest = new ArrayList<>(); //真实请求数量序列，在原有日志的基础上乘以multiple
	public static ArrayList<Integer> numberOfRequest5 = new ArrayList<>();  //每五分钟的请求序列，在numberOfRequest基础上统计出5分钟的请求量（用在预测模型与强化学习模型中）
	public static List<Cloudlet>  cloudletList = new ArrayList<Cloudlet>();//cloudlet链表,每秒任务首先是加入到这个链表中，待任务分配后cloudletToVm（）就设置为空了
	public static List<Cloudlet>  deleteCloudletList = new ArrayList<Cloudlet>();//在关掉vm时，需要保存关掉vm中尚未处理的任务，执行migrate()后，尚未处理任务就会设置未空了
	
	/*********虚拟机************/
	public static int VmId = 0;  //vm的id
	public static int initVmNumber = 4;//vm初始化的数目 
	public static ArrayList<Vm> vmList = new ArrayList<>();//记录vm链表，增加删除vm都需要操作这个链表
	public static ArrayList<Integer> vmNumAll = new ArrayList<>();//记录虚拟机个数，因为是每隔5分钟更新一次虚拟机，所以链表长度为288
	public static int processCapacity = 1;   //vm每秒处理cloudlet的数目，现在默认是1，这个变量没怎么考虑
	
	/*********服务处理总延迟时间************/
	public static int totalDelayTime = 0;  //记录一天总的延迟时间
	public static ArrayList<Integer> slaTotalTime = new ArrayList<>();
	
	/*********轮询vm的id全局变量************/
	public static int roundRobinId = 0;    //在轮询调度中会使用，后来使用最小资源利用调度，则没有使用了 
	
	/*********第几个5分钟（300秒）的辅助变量************/
	public static int time300 = 0;   //第几个5分钟（300秒），用来读取5分钟的用户请求量和5分钟cpu利用率
	public static int temp300 = 0;   //记录该时间段内所有vm还尚未处理的任务总和，这个阶段没有处理的任务自然会推迟到下一阶段
	
	/*********保存cpu利用率************/
	public static List<Float> cpuUtilizationList = new ArrayList<Float>();  //5分钟（300秒）内每隔5秒钟的cpu利用率
	public static List<Float> cpuUtilizationListAll = new ArrayList<Float>();//记录一天所有的每隔5秒的cpu利用率
	public static ArrayList<Float> cpuUtilizationList300 = new ArrayList<Float>();  //记录一天中每隔5分钟（300秒）计算一次的cpu利用率
	
	/*********保存vm运行状态************/
	public static List<String> vmNextStatus = VmStatus.getVmNextStatus300(numberOfRequest5);//记录下一5分钟（300秒）的预测状态，在资源调度中会使用到
	
	/*********打印日志存放的路径************/
	public static String  logPath = "./log0507.txt";
	
	/*********最终的q矩阵************/
	public static float q[][]={{0.723f,0.217f,0.06f},{0.385f,0.205f,0.41f},{0.115f,0.131f,0.754f}}; 
}
