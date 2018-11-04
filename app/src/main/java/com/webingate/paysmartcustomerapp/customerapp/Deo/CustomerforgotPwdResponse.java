package com.webingate.paysmartcustomerapp.customerapp.Deo;

import com.google.gson.annotations.SerializedName;

public class CustomerforgotPwdResponse{

	@SerializedName("UserAccountNo")
	private String UserAccountNo;

	@SerializedName("Email")
	private String Email;

	@SerializedName("Passwordotp")
	private String passwordotp;

	@SerializedName("Code")
	private String code;

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

	public void setPasswordotp(String passwordotp){
		this.passwordotp = passwordotp;
	}

	public String getPasswordotp(){
		return passwordotp;
	}

	public void setUserAccountNo(String UserAccountNo){
		this.UserAccountNo = UserAccountNo;
	}
	public String getUserAccountNo(){
		return UserAccountNo;
	}

	public void setEmail(String UserAccountNo){
		this.Email = Email;
	}
	public String getEmail(){
		return Email;
	}

	@Override
 	public String toString(){
		return 
			"CustomerforgotPwdResponse{" + 
			"passwordotp = '" + passwordotp + '\'' +
			",UserAccountNo = '" + UserAccountNo + '\''	+
			",Email = '" + Email + '\''+

			"}";
		}
}