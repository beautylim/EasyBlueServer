package com.EasyBlue.entity;

public class UserInfo {
    private int uuid,gender;
    private String name,bornDate,portraitUrl,currentPlace,tel;
    public UserInfo(int gender,String name,String bornDate,String portraitUrl,String currentPlace,String tel){
    	this.gender = gender;
    	this.name = name;
    	this.bornDate = bornDate;
    	this.portraitUrl = portraitUrl;
    	this.currentPlace = currentPlace;
    	this.tel = tel;
    }
    
	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBornDate() {
		return bornDate;
	}

	public void setBornDate(String bornDate) {
		this.bornDate = bornDate;
	}

	public String getPortraitUrl() {
		return portraitUrl;
	}

	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}

	public String getCurrentPlace() {
		return currentPlace;
	}

	public void setCurrentPlace(String currentPlace) {
		this.currentPlace = currentPlace;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
