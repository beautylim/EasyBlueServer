package com.EasyBlue.entity;

public class HealthData {
    private int uuid,userId,memberId,step;
    private String updateTime;
    public HealthData(int userId,int memberId,int step,String updateTime){
    	this.userId = userId;
    	this.memberId = memberId;
    	this.step = step;
    	this.updateTime = updateTime;
    }
    
	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
