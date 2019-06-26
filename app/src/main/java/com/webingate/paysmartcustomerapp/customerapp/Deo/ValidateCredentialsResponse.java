package com.webingate.paysmartcustomerapp.customerapp.Deo;

import com.google.gson.annotations.SerializedName;

public class ValidateCredentialsResponse{

	@SerializedName("Firstname")
	private String firstname;

	@SerializedName("Email")
	private String email;

	@SerializedName("Username")
	private String username;

	@SerializedName("StatusId")
	private String statusId;

	@SerializedName("Id")
	private int id;

	@SerializedName("AuthTypeId")
	private String authTypeId;

	@SerializedName("Mobilenumber")
	private String mobilenumber;

	@SerializedName("lastname")
	private String lastname;

	@SerializedName("UserAccountNo")
	private String UserAccountNo;

	@SerializedName("Code")
	private String code;
	@SerializedName("UserPhoto")
	private String UserPhoto;

	@SerializedName("description")
	private String description;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public void setFirstname(String firstname){
		this.firstname = firstname;
	}

	public String getFirstname(){
		return firstname;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setStatusId(String statusId){
		this.statusId = statusId;
	}

	public String getStatusId(){
		return statusId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
	public String getUserPhoto(){
		return UserPhoto;
	}

	public void setAuthTypeId(String authTypeId){
		this.authTypeId = authTypeId;
	}

	public String getAuthTypeId(){
		return authTypeId;
	}

	public void setMobilenumber(String mobilenumber){
		this.mobilenumber = mobilenumber;
	}

	public String getMobilenumber(){
		return mobilenumber;
	}

	public void setLastname(String lastname){
		this.lastname = lastname;
	}

	public String getLastname(){
		return lastname;
	}
	public void setUserAccountNo(String UserAccountNo){
		this.UserAccountNo = UserAccountNo;
	}

	public String getUserAccountNo(){
		return UserAccountNo;
	}

	@Override
 	public String toString(){
		return 
			"ValidateCredentialsResponse{" + 
			"firstname = '" + firstname + '\'' + 
			",email = '" + email + '\'' + 
			",username = '" + username + '\'' + 
			",statusId = '" + statusId + '\'' + 
			",id = '" + id + '\'' + 
			",authTypeId = '" + authTypeId + '\'' + 
			",mobilenumber = '" + mobilenumber + '\'' + 
			",lastname = '" + lastname + '\'' +
			",UserAccountNo = '" + UserAccountNo + '\''+
			",UserPhoto = '" + UserPhoto + '\''+
			"}";
		}
}