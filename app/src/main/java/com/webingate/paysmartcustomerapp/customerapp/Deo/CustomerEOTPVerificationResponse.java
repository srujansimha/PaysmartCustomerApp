package com.webingate.paysmartcustomerapp.customerapp.Deo;

import com.google.gson.annotations.SerializedName;

public class CustomerEOTPVerificationResponse {

    @SerializedName("Email")
    private String Email;

    @SerializedName("EVerificationCode")
    private String EVerificationCode;

    @SerializedName("userId")
    private String userId;


    public String getEmail(){
        return Email;
    }
    @Override
    public String toString(){
        return
                "CustomerEOTPResponse{" +
                        "Email = '" + Email + '\'' +
                        "}";
    }
}
