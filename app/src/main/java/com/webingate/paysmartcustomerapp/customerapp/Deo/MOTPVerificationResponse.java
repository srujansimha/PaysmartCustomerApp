package com.webingate.paysmartcustomerapp.customerapp.Deo;

import com.google.gson.annotations.SerializedName;

public class MOTPVerificationResponse {
    @SerializedName("Mobilenumber")
    private String Mobilenumber;

    @SerializedName("MVerificationCode")
    private String MVerificationCode;

    @SerializedName("userId")
    private String userId;

    @SerializedName("Code")
    private String Code;

    @SerializedName("description")
    private String description;


    public String getMobilenumber(){
        return Mobilenumber;
    }
    public String getCode() {
        return Code;
    }
    public String getDescription() {
        return description;
    }
    public String toString(){
        return
                "CustomerMOTPResponse{" +
                        "Mobilenumber = '" + Mobilenumber + '\'' +
                        "}";
    }
}
