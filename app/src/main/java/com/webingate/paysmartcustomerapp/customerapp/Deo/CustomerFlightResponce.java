package com.webingate.paysmartcustomerapp.customerapp.Deo;

import com.google.gson.annotations.SerializedName;

public class CustomerFlightResponce {

	@SerializedName("age") private int age;
	@SerializedName("Id") private int Id;
	@SerializedName("name") private String name;
	@SerializedName("gender") private String gender;
	@SerializedName("appuserid") private int appuserid;
	@SerializedName("flag") private String flag;
	@SerializedName("Mobileno") private String Mobileno;
	@SerializedName("emailid") private String emailid;
	@SerializedName("seatno") private String seatno;

	@SerializedName("active") private String active;
	@SerializedName("desce") private String desce;
	@SerializedName("latitude") private String latitude;
	@SerializedName("longitude") private String longitude;
	@SerializedName("code") private String code;
	@SerializedName("countryid") private String countryid;

	@SerializedName("Sourceterminal") private String Sourceterminal;
	@SerializedName("destinationterminal") private String destinationterminal;
	@SerializedName("amount") private String amount;
	@SerializedName("description") private String description;
	@SerializedName("TransactionDate") private String TransactionDate;
	@SerializedName("StatusName") private String StatusName;
	@SerializedName("TransGatewayId") private String TransGatewayId;
	@SerializedName("PasssengerId") private String PasssengerId;
	@SerializedName("Emaild") private String Emaild;
	@SerializedName("ContactNo") private String ContactNo;
	@SerializedName("TicketNo") private String TicketNo;
	@SerializedName("Source") private String Source;
	@SerializedName("Destination") private String Destination;

	public int getId(){
		return Id;
	}
	public String getPasssengerId(){return PasssengerId;}
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
	public String getSourceterminal(){
		return Sourceterminal;
	}
	public String getDestinationterminal(){
		return destinationterminal;
	}
	public String getAmount(){return amount;}
	public String getStatusName(){return StatusName;}
	public String getDescription(){
		return description;
	}
	public String getTransactionDate(){
		return TransactionDate;
	}
	public String getTransGatewayId(){
		return TransGatewayId;
	}
	public String getEmailid(){
		return Emaild;
	}
	public String getContactNo(){
		return ContactNo;
	}
	public String getTicketNo(){
		return TicketNo;
	}

	public void setName(String name){this.name=name;}
	public void setAge(int age){this.age=age;}
	public void setgender(String gender){this.gender=gender;}
	public void setappuserid(int appuserid){this.appuserid=appuserid;}
    public void setFlag(String flag){this.flag=flag;}
	public void setMobileno(String Mobileno){this.Mobileno=Mobileno;}
	public void setEmailid(String emailid){this.emailid=emailid;}
	public void setSeatno(String seatno){this.seatno=seatno;}
	public void setSource(String Source){this.Source=Source;}
	public void setDestination(String Destination){this.Destination=Destination;}

}