package com.webingate.paysmartcustomerapp.customerapp.Deo;

import com.google.gson.annotations.SerializedName;

public class AppUsersResponce {
    @SerializedName("flag")
    private String flag;

    @SerializedName("Username")
    private String Username;

    @SerializedName("AuthTypeId")
    private String AuthTypeId;

    @SerializedName("Password")
    private String Password;

    @SerializedName("Mobilenumber")
    private String Mobilenumber;

    @SerializedName("Email")
    private String Email;

    @SerializedName("CountryId")
    private String CountryId;

    @SerializedName("CCode")
    private String CCode;

    @SerializedName("UserAccountNo")
    private String UserAccountNo;

    @SerializedName("UserTypeId")
    private String UserTypeId;

    @SerializedName("Emailotp")
    private String Emailotp;

    @SerializedName("Id")
    private String Id;
    @SerializedName("Mobileotp")
    private String Mobileotp;

    @SerializedName("Code")
    private String Code;
    @SerializedName("CreatedOn")
    private String CreatedOn;

    @SerializedName("description")
    private String description;


    public String getemail(){
        return Email;
    }

    public String getemailotp(){
        return Emailotp;
    }
    public String getmotp(){return Mobileotp;}
    public String getmnumber(){return Mobilenumber;}
    public String getCode() {
        return Code;
    }
    public String getDescription() {
        return description;
    }
    public void setId(String Id){this.Id = Id;}
    public String getId(){return Id;}
    public String getUsername(){return  Username;}
    public String getCreatedOn(){return  CreatedOn;}
    public void setUserAccountNo(String UserAccountNo){this.UserAccountNo = UserAccountNo;}
    public String getUserAccountNo() {
        return UserAccountNo;
    }

    @Override
    public String toString(){
        return
                "RegisterBusinessAppusersResponse{" +
                        "Id = '" + Id + '\'' +
                        "Email = '" + Email + '\'' +
                        "Emailotp = '" + Emailotp + '\'' +
                        "Username = '" + Username + '\'' +
                        "Mobileotp = '" + Mobileotp + '\'' +
                        "Mobilenumber = '" + Mobilenumber + '\'' +
                        "UserAccountNo = '" + UserAccountNo + '\'' +
                          "CreatedOn = '" + CreatedOn + '\'' +
                        "}";
    }
}
