package com.webingate.paysmartcustomerapp.customerapp.Deo;

import com.google.gson.annotations.SerializedName;

public class CustomerFlightResponce {

	@SerializedName("Id") private int Id;
	@SerializedName("name") private String name;
	@SerializedName("active") private String active;
	@SerializedName("desce") private String desce;
	@SerializedName("latitude") private String latitude;
	@SerializedName("longitude") private String longitude;
	@SerializedName("code") private String code;
	@SerializedName("countryid") private String countryid;

	public int getId(){
		return Id;
	}

	public String getActive(){
		return active;
	}
	public String getDesce(){
		return desce;
	}
	public String getLatitude(){
		return latitude;
	}
	public String getLongitude(){
		return longitude;
	}
	public String getCode(){
		return code;
	}
	public String getCountryid(){
		return countryid;
	}
	public String getName(){
		return name;
	}

	
}