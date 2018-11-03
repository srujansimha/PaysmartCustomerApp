package com.webingate.paysmartcustomerapp.activity.customerapp;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rilixtech.CountryCodePicker;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.adapter.uicollection.GeneralItemSpinnerAdapter;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.customerapp.Deo.ValidateCredentialsResponse;
import com.webingate.paysmartcustomerapp.customerapp.WelcomeActivity;
import com.webingate.paysmartcustomerapp.object.GeneralList;
import com.webingate.paysmartcustomerapp.repository.customerapp.customerappRepository;
import com.webingate.paysmartcustomerapp.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//public class login_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
public class login_activity extends AppCompatActivity{

    public static final String MyPREFERENCES = "MyPrefs";
   // public static final String Phone = "phoneKey";
    public static final String ID = "idKey";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String Password = "passwordkey";
    public static final String Mobileotp = "mobileotpkey";
    public static final String Emailotp = "emailotpkey";
    public static final String Dateofbirth = "dateofbirth";
    public static final String Gender = "gender";
    public static final String Paymenttype = "paymenttype";
    public static final String Profilepic = "profilepic";

    private String response;

   ArrayList<GeneralList> countriesList;
    TextView forgotTextView, signUpTextView;
    @BindView(R.id.loginButton)
    Button loginButton;

    @BindView(R.id.s_mobileno)
    EditText mno;
    @BindView(R.id.s_password)
    EditText pwd;

    Toast toast;
    com.webingate.paysmartcustomerapp.customerapp.Dialog.ProgressDialog dialog ;


    int loginasOption = -1;
    CardView facebookCardView, twitterCardView;
   // Spinner spinner;
    ImageView bgImageView;
    ImageView countryImage;
    ArrayList<String> list;
    ArrayAdapter<GeneralList> adapter;
    CountryCodePicker ccp;


    public final static int REQUEST_CODE = 10101;
    private boolean isServerOn;
    String mobNo, id, emailOTP, mobileOTP;
    private static final int PERMISSIONS_ALL = 7;
    String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermissions();
        login_activity.CheckServerTask checkServerTask = new login_activity.CheckServerTask();
        checkServerTask.execute();
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //mobNo = prefs.getString(Phone, null);
        id = prefs.getString(ID, null);
        emailOTP = prefs.getString(Emailotp, null);
        mobileOTP = prefs.getString(Mobileotp, null);
        ApplicationConstants.username = prefs.getString(Name, null);
        ApplicationConstants.email = prefs.getString(Email, null);
        ApplicationConstants.dateofbirth = prefs.getString(Dateofbirth, null);
        ApplicationConstants.profilepic = prefs.getString(Profilepic, null);

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.customerapp_login_activity);
        ButterKnife.bind(this);


        initUI();

        initDataBindings();

        initActions();

        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.setCustomMasterCountries("IN,ZW,AF");
    }

    //region Init Functions
    private void initUI() {
        forgotTextView = findViewById(R.id.forgotTextView);
        signUpTextView = findViewById(R.id.signuptTextView);
        mno = findViewById(R.id.s_mobileno);
        pwd = findViewById(R.id.s_password);

        loginButton = findViewById(R.id.loginButton);
//        facebookCardView = findViewById(R.id.facebookCardView);
//        twitterCardView = findViewById(R.id.twitterCardView);
        bgImageView = findViewById(R.id.bgImageView);
      //  countryImage = findViewById(R.id.countryImg);
    }

    private void initDataBindings() {
        int id = R.drawable.login_background;
        Utils.setImageToImageView(getApplicationContext(), bgImageView, id);


    }

    private void initActions() {
        forgotTextView.setOnClickListener(view -> {
            //Toast.makeText(getApplicationContext(), "Clicked Forgot Password.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, customerForgotPasswordActivity.class);
            startActivity(intent);
        });

        signUpTextView.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "Clicked Sign Up.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, customerSignUpActivity.class);
            startActivity(intent);


        });

        loginButton.setOnClickListener(view -> {
            //Toast.makeText(getApplicationContext(), "Clicked Login.", Toast.LENGTH_SHORT).show();
            if (mno.getText().toString().matches("") || pwd.getText().toString().matches("")) {
                Toast.makeText(getApplicationContext(), "Please Enter details", Toast.LENGTH_SHORT).show();
            } else {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("CountryId", ccp.getSelectedCountryCode());
                jsonObject.addProperty("Password", pwd.getText().toString());
                jsonObject.addProperty("UserAccountNo", ccp.getSelectedCountryCode()+mno.getText().toString());
                CustomerLogin(jsonObject);
            }

        });

//        facebookCardView.setOnClickListener(view -> {
//            Toast.makeText(getApplicationContext(), "Clicked Facebook.", Toast.LENGTH_SHORT).show();
//        });
//
//        twitterCardView.setOnClickListener(view -> {
//            Toast.makeText(getApplicationContext(), "Clicked Twitter.", Toast.LENGTH_SHORT).show();
//        });
    }

    public void CustomerLogin(JsonObject jsonObject){

        // StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(this).getrestadapter()
                .ValidateCredentials(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ValidateCredentialsResponse>>() {
                    @Override
                    public void onCompleted() {
                        //    DisplayToast("Successfully LoggedIn");
                        //   StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Log.d("OnError ", e.getLocalizedMessage());
                            DisplayToast("Unable to Login");
                            StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<ValidateCredentialsResponse> responce) {
                        ValidateCredentialsResponse credentialsResponse=responce.get(0);
                        if(credentialsResponse.getCode()!=null){
                            DisplayToast(credentialsResponse.getDescription());
                        }else {
                            SharedPreferences sharedPref = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
//                            editor.putString(DRIVERID, credentialsResponse.getDid());
//                            editor.putString(VEHICLEID, credentialsResponse.getVehicleId());
//                            editor.putString(Phone, mobileNo.getText().toString());
//                            editor.putString(Emailotp, null);
//                            editor.putString(Mobileotp, null);
                            Intent intent = new Intent(login_activity.this, customerDashboardActivity.class);
                            intent.putExtra("mobilenumber", credentialsResponse.getMobilenumber());
                            //intent.putExtra("Uid",E_uid);
                            startActivity(intent);
                            editor.commit();
                            finish();
                        }

                    }
                });
    }



        private void checkPermissions() {


            if (hasPermissions(this, PERMISSIONS).size() > 0) {
                //ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
                ActivityCompat.requestPermissions(this, hasPermissions(this, PERMISSIONS).toArray(new String[0]), PERMISSIONS_ALL);

            } else if (hasPermissions(this, PERMISSIONS).size() == 0) {
                // DisplaySplashscreen();
            }
//            if (Build.VERSION.SDK_INT >= 23) {
//                if (ContextCompat.checkSelfPermission(login_activity.this,
//                        Manifest.permission.SYSTEM_ALERT_WINDOW)
//                        != PackageManager.PERMISSION_GRANTED)
//                    checkDrawOverlayPermission();
//            }
        }


        public static ArrayList<String> hasPermissions(Context context, String... permissions) {

            ArrayList<String> perms = new ArrayList<>();
            //if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            if (Build.VERSION.SDK_INT >= 23 && context != null && permissions != null) {
                for (String permission : permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        perms.add(permission);
                    }
                }
            }
            return perms;
        }

        public boolean checkDrawOverlayPermission() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return true;
            }
            if (!Settings.canDrawOverlays(login_activity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + login_activity.this.getPackageName()));
                startActivityForResult(intent, REQUEST_CODE);
                return false;
            } else {
                return true;
            }
        }

        class CheckServerTask extends AsyncTask<String, Void, String[]> {
            ProgressDialog dialog = new ProgressDialog(login_activity.this);

            protected void onPreExecute() {
                super.onPreExecute();
                dialog.setMessage("Please Wait...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

            }

            @Override
            protected String[] doInBackground(String... strings) {
                CheckConnection();
                return new String[0];
            }

            @Override
            protected void onPostExecute(String... result) {
                if (dialog.isShowing())
                    dialog.dismiss();
                if (isServerOn) {
                    if (emailOTP == null && mobileOTP != null) {
                        ApplicationConstants.mobileNo = mobNo;
                        ApplicationConstants.id = id;
                        startActivity(new Intent(login_activity.this,customerMOTPVerificationActivity.class));
                        finish();
                    } else if (emailOTP != null || mobileOTP != null) {
                        startActivity(new Intent(login_activity.this, customerEOTPVerificationActivity.class));
                        finish();
                    }
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(login_activity.this, R.style.Theme_AppCompat_DayNight_Dialog);
                    alertDialog.setTitle("Please Check Server or Please check Your Interenet Connection");
                    alertDialog.setMessage("Try Again");
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    CheckServerTask checkServerTask = new CheckServerTask();
                                    checkServerTask.execute();
                                }
                            });
                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });

                    alertDialog.show();
                }

            }
        }

    private void CheckConnection() {
        /*isServerOn=false;
         String ipAddress;
        if(getResources().getString(R.string.url_server).matches("http://124.123.41.203:8088")){
            ipAddress=getResources().getString(R.string.url_server);
        }else{
            ipAddress="196.27.119.219";
        }
        Log.i("Checking Server", "Address "+ipAddress);
        SocketAddress sockaddr = new InetSocketAddress(ipAddress, 8088);
        // Create an unbound socket
        Socket sock = new Socket();
        int timeoutMs = 5000;   // 2 seconds
        try {
            Log.i("Checking Server", "Connecting  "+ipAddress);
            sock.connect(sockaddr, timeoutMs);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("Checking Server", ipAddress+" is Connected "+sock.isConnected());

         isServerOn=sock.isConnected();*/
        isServerOn = true;
           /* try
            {
                InetAddress inet = InetAddress.getByName(ipAddress);
                System.out.println("Sending Ping Request to " + ipAddress);

                boolean status = inet.isReachable(5000); //Timeout = 5000 milli seconds

                if (status)
                {
                    Log.i("Checking Server","Status : Host is reachable");
                }
                else
                {
                    Log.i("Checking Server","Status : Host is not reachable");
                }
            }
            catch (UnknownHostException e)
            {
                Log.i("Checking Server","Host does not exists");
            }
            catch (IOException e)
            {
                Log.i("Checking Server","Error in reaching the Host");
            }*/

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
    }


