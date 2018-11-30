package com.louis.Vm;

import java.util.ArrayList;
import java.util.HashSet;

import com.louis.cloudlet.Cloudlet;

public class Vm {
	//编号
	private int id;
	//创建时间
	private int startTime;
	//vm关机时间
	private int finishTime;
	//新到来的任务序列
	ArrayList<Cloudlet> cloudletsList = new ArrayList<>();
	//已经处理的任务序列
	ArrayList<Cloudlet> processedCloudletsList = new ArrayList<>();
	//剩余未处理的任务序列
	ArrayList<Cloudlet> unProcessedCloudletsList = new ArrayList<>();
    
	public Vm(int id, int startTime) {
		super();
		this.id = id;
		this.startTime = startTime;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getStartTime() {
		return startTime;
	}


	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getFinishTime() {
		return finishTime;
	}


	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}


	public ArrayList<Cloudlet> getCloudletsList() {
		return cloudletsList;
	}


	public void setCloudletsList(ArrayList<Cloudlet> cloudletsList) {
		this.cloudletsList = cloudletsList;
	}


	public ArrayList<Cloudlet> getProcessedCloudletsList() {
		return processedCloudletsList;
	}


	public void setProcessedCloudletsList(ArrayList<Cloudlet> processedCloudletsList) {
		this.processedCloudletsList = processedCloudletsList;
	}


	public ArrayList<Cloudlet> getUnProcessedCloudletsList() {
		return unProcessedCloudletsList;
	}


	public void setUnProcessedCloudletsList(ArrayList<Cloudlet> unProcessedCloudletsList) {
		this.unProcessedCloudletsList = unProcessedCloudletsList;
	}
	

	
}
