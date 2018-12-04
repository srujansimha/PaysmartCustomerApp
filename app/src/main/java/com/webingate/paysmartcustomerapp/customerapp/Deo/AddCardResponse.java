package com.webingate.paysmartcustomerapp.customerapp.Deo;

import com.google.gson.annotations.SerializedName;

public class AddCardResponse {

	@SerializedName("Customer") private String Customer;
	@SerializedName("Id") private int Id;
	@SerializedName("CardNumber") private String CardNumber;
	@SerializedName("CardModel") private String CardModel;
	@SerializedName("CardCategory") private String CardCategory;
	@SerializedName("StatusId") private String StatusId;
	@SerializedName("UserId") private String UserId;
	@SerializedName("EffectiveFrom") private String EffectiveFrom;
	@SerializedName("EffectiveTo") private String EffectiveTo;

	public String getCustomer(){return Customer;}
	public String getCardNumber(){return CardNumber;}
	public int getId(){return Id;}

	@Override
 	public String toString(){
		return 
			"AddCardResponse{" +
					",Id = '" + Id + '\'' +
					",Customer = '" + Customer + '\'' +
					",CardNumber = '" + CardNumber + '\'' +
					",CardModel = '" + CardModel + '\'' +
					",CardCategory = '" + CardCategory + '\'' +
					",StatusId = '" + StatusId + '\'' +
					",UserId = '" + UserId + '\'' +
					",EffectiveFrom = '" + EffectiveFrom + '\'' +
					",EffectiveTo = '" + EffectiveTo + '\'' +
					"}";
		}

}