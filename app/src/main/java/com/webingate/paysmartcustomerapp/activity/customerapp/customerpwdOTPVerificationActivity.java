package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.JsonObject;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerPwdVerificationResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerResendOTPResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerforgotPwdResponse;
import com.webingate.paysmartcustomerapp.utils.Utils;

import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class customerpwdOTPVerificationActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Password = "passwordkey";
    public static final String Phone = "phoneKey";
    public static final String UserAccountNo = "UserAccountNoKey";
String useracntno;
    private ProgressDialog pd;
    Toast toast;
    @BindView(R.id.s_passwordotp)
    EditText potp;
    @BindView(R.id.s_newpassword)
    EditText newpwd;



    ImageView bgImageView;
    Button changeButton, resendButton, submitOTPButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerapp_pwdotpverification_activity);
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //mobNo = prefs.getString(Phone, null);
        //mobno = prefs.getString(Phone, null);
        useracntno = prefs.getString(UserAccountNo,null);

        initUI();

        initActions();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_true, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        initToolbar();

        bgImageView = findViewById(R.id.bgImageView);
        int id = Utils.getDrawableInt(getApplicationContext(), "verification3");
        Utils.setImageToImageView(getApplicationContext(), bgImageView, id);

        changeButton = findViewById(R.id.changeButton);

        resendButton = findViewById(R.id.resendButton);

        submitOTPButton = findViewById(R.id.submitOTPButton);
        potp = findViewById(R.id.s_passwordotp);
        newpwd = findViewById(R.id.s_newpassword);
    }

    private void initActions(){
        changeButton.setOnClickListener((View v) ->{
           Toast.makeText(getApplicationContext(),"Clicked Change Email.",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"+"email_to"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "email_subject");
            intent.putExtra(Intent.EXTRA_TEXT, "email_body");
            startActivity(intent);

        });

        resendButton.setOnClickListener((View v) ->{
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("UserAccountNo",useracntno);
            jsonObject.addProperty("change","3");
            ResendOTP(jsonObject);
            //Toast.makeText(getApplicationContext(),"OTP is Resent.",Toast.LENGTH_SHORT).show();
        });

        submitOTPButton.setOnClickListener((View v) ->{
            //Toast.makeText(getApplicationContext(),"OTP is Resent.",Toast.LENGTH_SHORT).show();
            if(potp.getText().toString().matches("")){
                Toast.makeText(getApplicationContext(),"Please Enter OTP",Toast.LENGTH_SHORT).show();
            }
            else
            {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("UserAccountNo",useracntno);
                jsonObject.addProperty("Password",newpwd.getText().toString());
                jsonObject.addProperty("Passwordotp",potp.getText().toString());
                ForgotPassword(jsonObject);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void ForgotPassword(JsonObject jsonObject){
        StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(this).getrestadapter()
                .Passwordverification(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CustomerPwdVerificationResponse>>() {
                    @Override
                    public void onCompleted() {
                        //DisplayToast("Password Updated Successfully");
                        StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            //Log.d("OnError ", e.getMessage());
                           // DisplayToast("onError"+e.getMessage());
                            StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<CustomerPwdVerificationResponse> responselist) {
                        CustomerPwdVerificationResponse response = responselist.get(0);
                        if (response.getCode() != null) {
                            DisplayToast(response.getDescription());
                        } else {
                            SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            Intent intent = new Intent(customerpwdOTPVerificationActivity.this, login_activity.class);
                            intent.putExtra("mobilenumber", response.getMobilenumber());
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

    public void ResendOTP(JsonObject jsonObject){
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(this).getrestadapter()
                .ResendOTP(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CustomerResendOTPResponse>>() {
                    @Override
                    public void onCompleted() {
                        DisplayToast("OTP has been Resent");
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
                    public void onNext(List<CustomerResendOTPResponse> responselist) {
                        CustomerResendOTPResponse response = responselist.get(0);
                        if (response.getCode() != null) {
                            DisplayToast(response.getDescription());
                        } else {
                            SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            //Intent intent = new Intent(customerpwdOTPVerificationActivity.this, customerpwdOTPVerificationActivity.class);
                            editor.putString(UserAccountNo, response.getUserAccountNo());
                            //intent.putExtra("Uid",E_uid);
                            //startActivity(intent);
                            editor.commit();
                            //startActivity(new Intent(customerEOTPVerificationActivity.this, login_activity.class));
//                       Intent intent = new Intent(customerEOTPVerificationActivity.this, businessappMOTPVerificationActivity.class);
//                        intent.putExtra("eotp","");
                            //finish();
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

    public void StartDialogue(){
        pd=new android.app.ProgressDialog(this);
        pd.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Please wait.....");
        pd.setCancelable(false);
        pd.incrementProgressBy(50);
        pd.show();
    }
    public void StopDialogue(){

        pd.dismiss();
    }
    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.baseline_menu_black_24);

        if (toolbar.getNavigationIcon() != null) {
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP);
        }

        toolbar.setTitle("Forgot Password Verify");

        try {
            toolbar.setTitleTextColor(getResources().getColor(R.color.md_white_1000));
        } catch (Exception e) {
            Log.e("TEAMPS", "Can't set color.");
        }

        try {
            setSupportActionBar(toolbar);
        } catch (Exception e) {
            Log.e("TEAMPS", "Error in set support action bar.");
        }

        try {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        } catch (Exception e) {
            Log.e("TEAMPS", "Error in set display home as up enabled.");
        }

    }
}
