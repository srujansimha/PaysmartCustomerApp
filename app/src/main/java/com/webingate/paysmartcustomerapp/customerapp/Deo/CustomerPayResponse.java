package com.webingate.paysmartcustomerapp.customerapp.Deo;

import com.google.gson.annotations.SerializedName;

public class CustomerPayResponse{

	@SerializedName("status")
	private int status;

	@SerializedName("PaymentDate") private String PaymentDate;
	@SerializedName("PaymentTime") private String PaymentTime;
	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
	public String getPaymentDate(){
		return PaymentDate;
	}
	public String getPaymentTime(){
		return PaymentTime;
	}

	@Override
 	public String toString(){
		return 
			"CustomerPayResponse{" + 
			"status = '" + status + '\'' + 
			"}";
		}
}