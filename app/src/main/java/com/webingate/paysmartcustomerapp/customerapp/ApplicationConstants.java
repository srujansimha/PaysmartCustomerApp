package com.webingate.paysmartcustomerapp.customerapp;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Seshu on 4/25/2017.
 */

public class ApplicationConstants {

    public static  String fdate;
    public static  int fstatus;
    public static  String adultquantity;
    public static  String childquantity;
    public static  String infantquantity;
    public static  String CabinName;
    public static  String estPrice;
    public static String userAccountNo;
    public static int tripFlag=0;
    public static int userid=0;
    public static String mobileNo;
    public static int id;
    public static String username;
    public static String password;
    public static String email;
    public static String photo;
    public static boolean verify_email=true;
    public static boolean isResetPasswordfirstWondow=true;
    public static String source;
    public static String destination;
    public static String fsource;
    public static String fdestination;
    public static String date="";
    public static String rdate="";
    public static int sourceid=0;
    public static int destinationid=0;
    public static int fsourceid=0;
    public static int fdestinationid=0;
    public static TravelModel travel;
    public static String bookingDate="";
    public static String bookingTime="";
    public static String walletBalance="";
    public static String pic_format;
    public static String pic_data;


    //public static ArrayList confirmedTrips=new ArrayList();
    public static String rating,comments;
    public static String address;

    public static int vehicleType=0;
    public static final int HOME=0;
    public static final int TICKET_SOURCE_DESTINATION=1;
    public static final int STOPS=2;
    public static final int TRAVELS=3;
    public static final int BUSLAYOUT=4;
    public static final int TICKETS=5;
    public static final int EWALLET=6;
    public static final int FEEDBACK=7;
    public static final int PAYMENTS=8;
    public static final int PASSENGERDETAILS=9;

    //public static ArrayList<StopsModel> stopsArraylist = new ArrayList<StopsModel>();
    public static ArrayList<TravelModel> travelsArraylist = new ArrayList<TravelModel>();

    //public static ArrayList<Payment_Method_model> paymentMethods= new ArrayList<Payment_Method_model>();

    public static int FRAGMENT=0;
    public static ArrayList seatsSelected=new ArrayList();

    public static String bookingNo;
    public static String booKingOTP="";
    public static String driverName;
    public static String driverId="";
    public static String mobNo;
    public static String registrationNo;
    public static String vModel;
    public static String driverimage;
    public static String vehicleimage;
    public static ArrayList<TransactionModel> walletTransactions;
    public static int marker;
    public static String dateofbirth="";
    public static String gender="";
    public static String paymenttype="";
    public static String profilepic="";
    public static String csource;
    public static String cdestination;
    public static String cslat;
    public static String cslog;
    public static String cdlat;
    public static String cdlog;
    public static String pdate;
    public static String ptime;
    public static String pmode;
    //Registration Details
    /*public static String Username="";
    public static String Email="";
    public static String Mobilenumber="";
    public static String Password="";
    public static String Mobileotp="";
    public static String Emailotp="";
    public static String Passwordotp="";
    public static String StatusId="";
    public static String CreatedOn="";
    public static String Mobileotpsenton="";
    public static String emailotpsenton="";
    public static String noofattempts="";
    public static String Firstname="";
    public static String lastname="";
    public static String AuthTypeId="";
    public static String AltPhonenumber="";
    public static String Altemail="";
    public static String AccountNo="";*/

}
