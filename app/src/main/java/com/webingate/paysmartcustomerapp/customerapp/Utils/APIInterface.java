package com.webingate.paysmartcustomerapp.customerapp.Utils;
import com.google.gson.JsonObject;
import com.webingate.paysmartcustomerapp.customerapp.Deo.ActiveCountries;
import com.webingate.paysmartcustomerapp.customerapp.Deo.AddCardResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.AppUsersResponce;
import com.webingate.paysmartcustomerapp.customerapp.Deo.AvailableVehiclesResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CalculatePriceResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.ConfigData;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerBookingStatusResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerChangePwdResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerEOTPVerificationResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerFlightResponce;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerGetstopsResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerPayResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerPwdVerificationResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerRateTheRideResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerResendOTPResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerRideDetailsResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerforgotPwdResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.DefaultResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.GetAvailableServicesResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.GetBookingHistoryResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.GetCurrentBalanceResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.GetCustomerAccountResponce;
import com.webingate.paysmartcustomerapp.customerapp.Deo.GetCustomerBookingListResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.GetWalletTransDetailsResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.MOTPVerificationResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.MakepaymentResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.RegisterUserResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.SaveBookingDetailsResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.SaveSOSNumberResponce;
import com.webingate.paysmartcustomerapp.customerapp.Deo.SavebankingDetailsResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.UpdateBookingstatusResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.UpdateUserResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.ValidateCredentialsResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.VehiclePositionResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.WalletBalanceResponse;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface APIInterface  {
    @GET("/api/AppUsers/CustomersCardsList")
    public Observable<List<AddCardResponse>> GetCardList(@Query("UserId") int UserId);//

    @POST("/api/AppUsers/SaveCards")
    public Observable<List<AddCardResponse>> SaveAddCard(@Body JsonObject jsonObject);//

    @GET("/api/AppUsers/AppUserDetailsUseracountno")
    public Observable<List<AppUsersResponce>> getAppUserDetails(@Query("UserAccountNo") String UserAccountNo);

    @GET("/api/Driverlogin/GetCustomertrips")
    public Observable<List<GetCustomerBookingListResponse>> GetCustomertrips(@Query("custNo") String driverNo,@Query("status") int status);

    @GET("/api/Driverlogin/GetdrivertripsBookingno")
    public Observable<List<GetCustomerBookingListResponse>> Getdrivertripsbookingno(@Query("DriverNo") String driverNo, @Query("bno") String bno);
    @GET("/api/Driverlogin/Getdrivertrips")
    public Observable<List<GetCustomerBookingListResponse>> Getdrivertrips(@Query("DriverNo") String driverNo,@Query("status") int status);

    @POST("/api/UserAccount/UpdateAppUser")
    public Observable<List<UpdateUserResponse>> UpdateAppUser(@Body JsonObject jsonObject);

    @GET("/api/WalletBalance/Getcurrentbalance")
    public Observable<List<GetCurrentBalanceResponse>> Getcurrentbalance(@Query("mobileno") String mobileNo);
    @GET("/api/WalletBalance/Getcurrentbalance")
    public Observable<List<WalletBalanceResponse>> Getcurrentbalance1(@Query("mobileno") String mobileNo);
    @POST("/api/WalletBalance/WalletBalance")
    public Observable<List<WalletBalanceResponse>> WalletBalance(@Body JsonObject jsonObject);

    @POST("/api/VehicleBooking/VehiclePosition")
    public Observable<List<VehiclePositionResponse>> VehiclePosition(@Body JsonObject jsonObject);

    @GET("/api/stops/getstops")
    public Observable<List<CustomerGetstopsResponse>> getstops();

    @GET("/api/MeteredTaxi/TaxiStops")
    public Observable<List<CustomerGetstopsResponse>> TaxiStops();//i

    @POST("/api/VehicleBooking/CalculatePrice")
    public Observable<List<CalculatePriceResponse>> CalculatePrice(@Body JsonObject jsonObject);

    @POST("/api/VehicleBooking/SaveBookingDetails")
    public Observable<List<SaveBookingDetailsResponse>> SaveBookingDetails(@Body JsonObject jsonObject);

    @POST("/api/VehicleBooking/BookingStatus")
    public Observable<List<CustomerBookingStatusResponse>> BookingStatus(@Body JsonObject jsonObject);

    @POST("/api/VehicleBooking/UpdateBookingStatus")
    public Observable<List<UpdateBookingstatusResponse>> UpdateBookingStatus(@Body JsonObject jsonObject);

    @POST("/api/VehicleBooking/AdvanceBookingDetails")
    public Observable<List<SaveBookingDetailsResponse>> AdvanceBookingDetails(@Body JsonObject jsonObject);//i

    @POST("/api/login/ValidateCredentials")
    public Observable<List<ValidateCredentialsResponse>> ValidateCredentials(@Body JsonObject jsonObject);

    @POST("/api/Payment/Pay")
    public Observable<List<CustomerPayResponse>> Pay(@Body JsonObject jsonObject);

    @POST("/api/UserAccount/RegisterUser")
    public Observable<List<RegisterUserResponse>> RegisterUser(@Body JsonObject jsonObject);

    @POST("/api/UserAccount/UpdateRegisterUser")
    public Observable<List<RegisterUserResponse>> RegisterUserUpdate(@Body JsonObject jsonObject);


    @GET("/api/TicketBooking/GetAvailableServices")
    public Observable<List<GetAvailableServicesResponse>> GetAvailableServices(@Query("srcId") String srcId, @Query("destId") String destId);//i

    @POST("/api/UserAccount/EOTPVerification")
    public Observable<List<CustomerEOTPVerificationResponse>> CustomerEOTPVerification(@Body JsonObject jsonObject);//i

    @POST("/api/UserAccount/MOTPVerifications")
    public Observable<List<MOTPVerificationResponse>> MOTPVerifications(@Body JsonObject jsonObject);

    @POST("/api/UserAccount/EwalletOTPSending")
    public Observable<List<MOTPVerificationResponse>> EwalletSendOTP(@Body JsonObject jsonObject);//

    @POST("/api/UserAccount/EwalletOTPVerification")
    public Observable<List<MOTPVerificationResponse>> EwalletMOTPVerifications(@Body JsonObject jsonObject);

    @GET("/api/AppUsers/GetEwalletStatus")
    public Observable<List<MOTPVerificationResponse>> GetEwalletStatus(@Query("acct") String acct);

    @POST("/api/ChangePwd/change")
    public Observable<List<CustomerChangePwdResponse>> ChangePassword(@Body JsonObject jsonObject);


    @GET("/api/WalletTransDetails/GetWalletTransDetails")
    public Observable<List<GetWalletTransDetailsResponse>> GetWalletTransDetails(@Query("MobileNo") String mobileNo);//i

    @POST("/api/DriverMaster/SaveBankingdetails")
    public Observable<List<SavebankingDetailsResponse>> SaveBankingdetails(@Body JsonObject jsonObject);

    @GET("/api/BookingHistory/GetBookingHistory")
    public Observable<List<GetBookingHistoryResponse>> GetBookingHistory(@Query("PhoneNo") String phoneNo);

    @POST("/api/VehicleBooking/RideDetails")
    public Observable<List<CustomerRideDetailsResponse>> RideDetails(@Body JsonObject jsonObject);

    @POST("/api/VehicleBooking/RateTheRide")
    public Observable<List<CustomerRateTheRideResponse>> RateTheRide(@Body JsonObject jsonObject);

    @POST("/api/VehicleBooking/AvailableVehicles")
    public Observable<List<AvailableVehiclesResponse>> AvailableVehicles(@Body JsonObject jsonObject);

    @POST("/api/Forgotpassword/Forgotpassword")
    public Observable<List<CustomerforgotPwdResponse>> Forgotpassword(@Body JsonObject jsonObject);

    @POST("/api/UserAccount/ResendOtp")
    public Observable<List<CustomerResendOTPResponse>> ResendOTP(@Body JsonObject jsonObject);

    @POST("/api/UserAccount/Passwordverification")
    public Observable<List<CustomerPwdVerificationResponse>> Passwordverification(@Body JsonObject jsonObject);

    @POST("/api/CustomerAccountDetails/MakePayment")
    public Observable<List<MakepaymentResponse>> MakePayment(@Body JsonObject jsonObject);

    @POST("/api/SaveSOSNumber")
    public Observable<List<SaveSOSNumberResponce>> SaveSOSNumber(@Body JsonObject jsonObject);//i

    @GET("/api/CustomerAccountDetails/GetCustomerAccount")
    public Observable<List<GetCustomerAccountResponce>> GetCustomerAccount(@Query("userId") String userId);//i

    @GET("/api/Common/GetCountry")
    public Observable<List<ActiveCountries>> GetActiveCountry(@Query("active") int active);//i

    @POST("/api/CustomerAccountDetails/CustomerAccount")
    public Observable<List<DefaultResponse>> CustomerAccount(@Body JsonObject jsonObject);//i

    @POST("api/Common/ConfigData")
    public Observable<List<ConfigData>> GetConfigData(@Body JsonObject jsonObject);

    @POST("api/TicketBooking/SaveBookingDetails")
    public Observable<List<GetCustomerBookingListResponse>> SaveBookingDetails1(@Body JsonObject jsonObject);

    /*


    @POST("gallery/uploadImage")
    public Observable<SuccessMsg> UpdateImage(@Body RequestBody jsonObject);

    @GET("gallery/imagesList")
    public Observable<List<ImagesList>> GetImagesList();

    @POST("gallery/tagsList")
    public Observable<List<SuggestionsList>>  GetSuggestions(@Body JsonObject jsonObject);

    @POST("gallery/imagesearchbyTag")
    public Observable<List<ImagesList>> GetSortedImages(@Body JsonObject jsonObject);


    @POST("gallery/deleteImages")
    public Observable<SuccessMsg> DeleteImages(@Body JsonObject jsonObject);


    */
    @GET("/api/airport/getairport")
    public Observable<List<CustomerFlightResponce>> GetAirPortList();

    @POST("/api/flightschedule/getflightschedule")
    public Observable<List<CustomerFlightResponce>> getflightschedule(@Body JsonObject jsonObject);//i

    @POST("/api/flightbooking/saveflightbooking")
    public Observable<List<CustomerFlightResponce>> saveflightbooking(@Body List<CustomerFlightResponce> jsonObject);//i


    @POST("/api/passengerFlight/savepassenger")
    public Observable<List<CustomerFlightResponce>> savepassenger(@Body List<CustomerFlightResponce> jsonObject);//i
    @POST("/api/FBTransactionMaster/saveFBTransactionMaster")
    public Observable<List<CustomerFlightResponce>> saveFBTransactionMaster(@Body JsonObject jsonObject);//i


}
