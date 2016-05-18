package com.EasyBlue.entity;

public class FamilyMember {
    private int uuid,userId,gender;
    private String bornDate, nickName,portraitUrl,tel;
    public FamilyMember(int userId,int gender,String bornDate,String nickName,String portraitUrl,String tel){
    	this.userId = userId;
    	this.gender = gender;
    	this.bornDate = bornDate;
    	this.nickName = nickName;
    	this.portraitUrl = portraitUrl;
    	this.tel = tel;
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

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getBornDate() {
		return bornDate;
	}

	public void setBornDate(String bornDate) {
		this.bornDate = bornDate;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPortraitUrl() {
		return portraitUrl;
	}

	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
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
