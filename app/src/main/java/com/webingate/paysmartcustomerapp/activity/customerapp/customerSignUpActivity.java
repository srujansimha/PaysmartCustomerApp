package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.customerapp.Deo.RegisterUserResponse;
import com.webingate.paysmartcustomerapp.customerapp.RegisterActivity;
import com.webingate.paysmartcustomerapp.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import com.webingate.paysmartcustomerapp.customerapp.Dialog.ProgressDialog;
public class customerSignUpActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    public static final String Password = "passwordkey";
    public static final String Mobileotp = "mobileotpkey";
    public static final String Emailotp = "emailotpkey";
    public static final String Profilepic = "profilepic";
    private boolean isprofilePic = false;
    Toast toast;

    @BindView(R.id.mobileNo)
    EditText S_mobileNo;
    @BindView(R.id.s_password)
    EditText S_password;
    @BindView(R.id.s_username)
    EditText S_username;
    @BindView(R.id.s_email)
    EditText S_email;

    TextView forgotTextView, signUpTextView;
    Button registerButton;
    ImageView bgImageView;

    ProgressDialog dialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerapp_signup_activity);

        initUI();

        initDataBindings();

        initActions();

    }

    //region Init Functions
    private void initUI() {
        forgotTextView = findViewById(R.id.forgotTextView);
        signUpTextView = findViewById(R.id.signuptTextView);

        S_username=findViewById(R.id.s_username);
        S_email=findViewById(R.id.s_email);
        S_mobileNo=findViewById(R.id.mobileNo);
        S_password=findViewById(R.id.s_password);
        registerButton = findViewById(R.id.registerButton);
        bgImageView = findViewById(R.id.bgImageView);
        dialog =  new ProgressDialog.Builder(this)
                .setTitle("Loading...")
                .setTitleColorRes(R.color.gray)
                .build();
    }

    private void initDataBindings() {
        int id = R.drawable.login_background_3;
        Utils.setImageToImageView(getApplicationContext(), bgImageView, id);
    }

    private void initActions() {
        forgotTextView.setOnClickListener(view -> {
           // Toast.makeText(getApplicationContext(), "Clicked Forgot Password.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, customerForgotPasswordActivity.class);
            startActivity(intent);
        });

        signUpTextView.setOnClickListener(view -> {
          //  Toast.makeText(getApplicationContext(), "Clicked Sign In.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, login_activity.class);
            startActivity(intent);

        });

        registerButton.setOnClickListener(view -> {
            //Toast.makeText(getApplicationContext(), "Clicked Register.", Toast.LENGTH_SHORT).show();

            JsonObject object = new JsonObject();
            object.addProperty("flag", "I");
            object.addProperty("Email", S_email.getText().toString());
            object.addProperty("Mobilenumber",S_mobileNo.getText().toString());
            object.addProperty("Password", S_password.getText().toString());
            //object.addProperty("Usename",S_username.getText().toString());
            object.addProperty("CountryId","1");
            object.addProperty("CCode","91");
            object.addProperty("CurrentStateId","1");
            object.addProperty("UserAccountNo","10991"+S_mobileNo.getText().toString());
            //object.addProperty("UserTypeId","109");
            RegisterUser(object);
//            Intent intent = new Intent(this, customerEOTPVerificationActivity.class);
//            startActivity(intent);
        });

//        registerButton.setOnClickListener(new View.OnClickListener(){
//           // Toast.makeText(getApplicationContext(), "Clicked Register.", Toast.LENGTH_SHORT).show();
//
//            @Override
//            public void onClick(View v) {
////                if (firstname.getText().toString().matches("") || lastname.getText().toString().matches("")
////                        || email.getText().toString().matches("")
////                        || mobileno.getText().toString().matches("") || password.getText().toString().matches("")
////                        || repassword.getText().toString().matches("")) {
////                    Toast.makeText(getApplicationContext(), "Please Enter details", Toast.LENGTH_SHORT).show();
////                } else if (!password.getText().toString().matches(repassword.getText().toString())) {
////                    Toast.makeText(getApplicationContext(), "Password Not Matched", Toast.LENGTH_SHORT).show();
////                } else {
////                    if (!isprofilePic) {
////                        Toast.makeText(getApplicationContext(), "Upload Profile Picture", Toast.LENGTH_SHORT).show();
////                        return;
////                    }
//                    JsonObject object = new JsonObject();
//                    object.addProperty("flag", "I");
//
//                object.addProperty("Username", "srujan");
//                object.addProperty("Email", "webingateteam@gmail.com");
//                object.addProperty("Mobilenumber", "12235567890");
//                object.addProperty("Password", "123");
//                object.addProperty("Firstname", "srujan");
//                object.addProperty("lastname", "simha");
//                object.addProperty("AuthTypeId", "2");
//                object.addProperty("AltPhonenumber", "");
//                object.addProperty("Altemail", "");
//                object.addProperty("AccountNo", "");
//                object.addProperty("CountryId", 1);
//
////                    object.addProperty("Username", firstname.getText().toString());
////                    object.addProperty("Email", email.getText().toString());
////                    object.addProperty("Mobilenumber", mobileno.getText().toString());
////                    object.addProperty("Password", password.getText().toString());
////                    object.addProperty("Firstname", firstname.getText().toString());
////                    object.addProperty("lastname", lastname.getText().toString());
////                    object.addProperty("AuthTypeId", "2");
////                    object.addProperty("AltPhonenumber", mobileno.getText().toString());
////                    object.addProperty("Altemail", email.getText().toString());
////                    object.addProperty("AccountNo", "");
////                    object.addProperty("CountryId", getResources().getStringArray(R.array.country_type_id)[country.getSelectedItemPosition()]);
////                    Bitmap bitmap = ((BitmapDrawable) profilepic.getDrawable()).getBitmap();
////                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
////                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
////                    byte[] imageBytes = baos.toByteArray();
////                    String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//                    //ApplicationConstants.driverimage = encodedImage;
//                   // object.addProperty("UserPhoto", "data:image/png;base64," + encodedImage);
//                    RegisterUser(object);
//
//                  /*  serverurl = getResources().getString(R.string.url_server) + getResources().getString(R.string.url_registeruser);
//                    RegisterUserTask registerUserTask = new RegisterUserTask();
//                    registerUserTask.execute();*/
//
//                }
//            });
        }



    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void RegisterUser(JsonObject jsonObject){

      //  StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(customerSignUpActivity.this).getrestadapter()
                .RegisterUser(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RegisterUserResponse>>() {
                    @Override
                    public void onCompleted() {
                        DisplayToast("Successfully Registered");
                        StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            DisplayToast("Error"+e.getMessage());
                        //    StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<RegisterUserResponse> responselist) {
                        RegisterUserResponse response=responselist.get(0);
                        if (response.getCode()!=null) {
                            DisplayToast(response.getDescription());
                        } else {
                            SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Name, response.getUsername());
                            editor.putString(Phone, response.getMobilenumber());
                            editor.putString(Email,response.getEmail());
                            editor.putString(Password, response.getPassword());
                            editor.putString(Emailotp, response.getEmailotp());
                            editor.putString(Mobileotp, response.getMobileotp());
                            editor.putString(Profilepic, ApplicationConstants.driverimage);
                            editor.commit();
                            ApplicationConstants.driverimage = null;
                            startActivity(new Intent(customerSignUpActivity.this, customerEOTPVerificationActivity.class));
                            finish();
                        }

                    }
                });
    }
    public void DisplayToast(String text){
        if(toast!=null){
            toast.cancel();
            toast=null;

        }
        toast=Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
        toast.show();

    }
    public void StartDialogue(){

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    public void StopDialogue(){
        if(dialog.isShowing()){
            dialog.cancel();
        }

    }
    //endregion
}
