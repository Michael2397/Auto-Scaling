package com.louis.cloudlet;

public class Cloudlet {
	//编号
	private int id;
	//创建时间
	int startTime;
	//任务结束时间
	int finishTime;
	//该cloudlet延迟时间
	int slaTime;
		
	public Cloudlet(int id, int startTime) {
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

	public int getSlaTime() {
		return slaTime;
	}

	public void setSlaTime(int slaTime) {
		this.slaTime = slaTime;
	}
	
	
}
