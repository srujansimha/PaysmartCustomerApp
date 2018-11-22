package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rilixtech.CountryCodePicker;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.Deo.ActiveCountries;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerEOTPVerificationResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerforgotPwdResponse;
import com.webingate.paysmartcustomerapp.customerapp.ForgotPasswordActivity;
import com.webingate.paysmartcustomerapp.utils.Utils;

import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class customerForgotPasswordActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Phone = "phoneKey";
    public static final String UserAccountNo = "UserAccountNoKey";

    Button resetButton;
    TextView signInTextView;
    ImageView bgImageView;

    Toast toast;
    CountryCodePicker ccp;

    @BindView(R.id.s_mobileno)
    EditText mno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerapp_forgotpassword_activity);

        initUI();

        initDataBindings();

        initActions();
        initData();
        ccp = (CountryCodePicker) findViewById(R.id.ccp);


    }

    //region Init Functions
    private void initUI() {
        signInTextView = findViewById(R.id.signinTextView);
        resetButton = findViewById(R.id.resetButton);
        bgImageView = findViewById(R.id.bgImageView);
        mno = findViewById(R.id.s_mobileno);
    }

    private void initDataBindings() {
        int id = R.drawable.login_background_3;
        Utils.setImageToImageView(getApplicationContext(), bgImageView, id);
    }

    private void initData(){
        GetActiveCountries(1);
    }


    private void initActions() {

        signInTextView.setOnClickListener(view -> {
           // Toast.makeText(getApplicationContext(), "Clicked Sign In", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, login_activity.class);
            startActivity(intent);
        });


        resetButton.setOnClickListener(view -> {
            //Toast.makeText(getApplicationContext(), "Clicked Reset.", Toast.LENGTH_SHORT).show();
            if(mno.getText().toString().matches("")){
                Toast.makeText(getApplicationContext(),"Please Enter Mobile Number",Toast.LENGTH_SHORT).show();
            }
            else
            {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("UserAccountNo",ccp.getSelectedCountryCode()+mno.getText().toString());
                ForgotPassword(jsonObject);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void ForgotPassword(JsonObject jsonObject){
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(this).getrestadapter()
                .Forgotpassword(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CustomerforgotPwdResponse>>() {
                    @Override
                    public void onCompleted() {
                        DisplayToast("Successfully Registered");
                        //StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            //Log.d("OnError ", e.getMessage());
                            DisplayToast("onError"+e.getMessage());
                            //StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<CustomerforgotPwdResponse> responselist) {
                        CustomerforgotPwdResponse response = responselist.get(0);
                        if (response.getCode() != null) {
                            DisplayToast(response.getDescription());
                        } else {
                            SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            Intent intent = new Intent(customerForgotPasswordActivity.this, customerpwdOTPVerificationActivity.class);
                            //intent.putExtra("passwordotp", response.getPasswordotp());
                            editor.putString(UserAccountNo, response.getUserAccountNo());
                            //intent.putExtra("Uid",E_uid);
                            startActivity(intent);
                            editor.commit();
                            //startActivity(new Intent(customerEOTPVerificationActivity.this, login_activity.class));
//                       Intent intent = new Intent(customerEOTPVerificationActivity.this, businessappMOTPVerificationActivity.class);
//                        intent.putExtra("eotp","");
                            finish();
                        }
                    }
                });
    }

    public void GetActiveCountries(int active){
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(customerForgotPasswordActivity.this).getrestadapter()
                .GetActiveCountry(active)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ActiveCountries>>() {
                    @Override
                    public void onCompleted() {
                        //DisplayToast("Successfully Registered");
                        //StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Log.d("OnError ", e.getMessage());
                            DisplayToast("Error");
                            //StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<ActiveCountries> list) {

                        List<ActiveCountries> response= list ;
                        int countrycount = response.size();
                        if (countrycount == 0) {
                            DisplayToast("Please configure countries of operation.");
                        } else {

                            String countriesList = "";
                            for(int i=0; i < countrycount ; i++){
                                if(i == countrycount-1)
                                    countriesList += response.get(i).getISOCode();
                                else
                                    countriesList += response.get(i).getISOCode()+ ",";
                            }

                            ccp.setCustomMasterCountries(countriesList);

//                            ccp = (CountryCodePicker) findViewById(R.id.ccp);
//                            ccp.setCustomMasterCountries(response.getISOCode());
//                            SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedpreferences.edit();
//                            editor.putString(Isocode, response.getISOCode());
//                            editor.commit();
                        }
                    }
                });
    }

    public void DisplayToast(String text){
        if(toast!=null){
            toast.cancel();
            toast=null;

        }
        toast= Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
        toast.show();

    }
    //endregion
}
