package com.webingate.paysmartcustomerapp.customerapp.Deo;

import com.google.gson.annotations.SerializedName;

public class CustomerEOTPVerificationResponse {

    @SerializedName("Email")
    private String Email;

    @SerializedName("EVerificationCode")
    private String EVerificationCode;

    @SerializedName("userId")
    private String userId;

    @SerializedName("Code")
    private String Code;

    @SerializedName("description")
    private String description;



    public String getEmail(){
        return Email;
    }
    public String getCode() {
        return Code;
    }
    public String getDescription() {
        return description;
    }
    @Override
    public String toString(){
        return
                "CustomerEOTPResponse{" +
                        "Email = '" + Email + '\'' +
                        "}";
    }
}
