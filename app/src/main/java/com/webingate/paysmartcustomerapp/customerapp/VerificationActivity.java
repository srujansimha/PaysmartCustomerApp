package com.webingate.paysmartcustomerapp.customerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;

import com.google.gson.JsonObject;
import com.webingate.paysmartcustomerapp.activity.customerapp.customerMOTPVerificationActivity;
import com.webingate.paysmartcustomerapp.activity.customerapp.login_activity;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerEOTPVerificationResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.DefaultResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.MOTPVerificationResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.ValidateCredentialsResponse;
import com.webingate.paysmartcustomerapp.customerapp.Dialog.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/*import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;*/import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import com.webingate.paysmartcustomerapp.R;
public class VerificationActivity extends FragmentActivity {

    String response = "", serverurl = "", email, phoneNo;

    @BindView(R.id.otp_hint)
    TextView otp;
    @BindView(R.id.input_otp)
    EditText otpHint;
    @BindView(R.id.btn_confirm)
    AppCompatButton confirm;

    private int otpflag;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    public static final String Mobileotp = "mobileotpkey";
    public static final String Emailotp = "emailotpkey";
    Toast toast;
    ProgressDialog dialog ;
    String emailOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);
        dialog =  new ProgressDialog.Builder(VerificationActivity.this)
                .setTitle("Loading...")
                .setTitleColorRes(R.color.gray)
                .build();
        otp = (EditText) findViewById(R.id.input_otp);

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        emailOtp = prefs.getString(Emailotp, null);
        String mobileOtp = prefs.getString(Mobileotp, null);
        email = prefs.getString(Email, null);
        phoneNo = prefs.getString(Phone, null);
        if (emailOtp != null) {
            otpflag = 0;
            otpHint.setText(getString(R.string.email_otp));
            otp.setHint("Enter email address OTP ");
            serverurl = getResources().getString(R.string.url_server) + getResources().getString(R.string.url_eotpverification);
            Toast.makeText(getApplicationContext(), emailOtp, Toast.LENGTH_SHORT).show();
        } else {
            otpflag = 1;
            otpHint.setText(getString(R.string.mobile_otp));
            otp.setHint("Enter Mobile Number OTP ");
            serverurl = getResources().getString(R.string.url_server) + getResources().getString(R.string.url_motpverification);
            Toast.makeText(getApplicationContext(), mobileOtp, Toast.LENGTH_SHORT).show();
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter details", Toast.LENGTH_SHORT).show();
                } else {
                    JsonObject object=new JsonObject();
                    if (emailOtp != null) {
                        object.addProperty("Email", email);
                        object.addProperty("EVerificationCode", otp.getText().toString());
                        object.addProperty("Mobilenumber", phoneNo);
                        EOTPVerification(object);
                    }else {
                        object.addProperty("Mobilenumber", phoneNo);
                        object.addProperty("MVerificationCode", otp.getText().toString());
                        MOTPVerifications(object);
                    }


                    /*
                    VerificationTask verificationTask = new VerificationTask();
                    verificationTask.execute();*/
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void EOTPVerification(JsonObject jsonObject){

        StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(VerificationActivity.this).getrestadapter()
                .CustomerEOTPVerification(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CustomerEOTPVerificationResponse>>() {
                    @Override
                    public void onCompleted() {
                        //  DisplayToast("Successfully Registered");
                        StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Log.d("OnError ", e.getMessage());
                            DisplayToast("Error");
                            StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<CustomerEOTPVerificationResponse> responselist) {
                        CustomerEOTPVerificationResponse response=responselist.get(0);
                        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Emailotp, response.getEmail());
                        editor.commit();//
                        startActivity(new Intent(VerificationActivity.this, customerMOTPVerificationActivity.class));
                            finish();
                    }
                });
    }


    public void MOTPVerifications(JsonObject jsonObject){

        StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(VerificationActivity.this).getrestadapter()
                .MOTPVerifications(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MOTPVerificationResponse>>() {
                    @Override
                    public void onCompleted() {
                        //  DisplayToast("Successfully Registered");
                        StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Log.d("OnError ", e.getMessage());
                            DisplayToast("Error");
                            StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<MOTPVerificationResponse> responselist) {
                        MOTPVerificationResponse response=responselist.get(0);
                        Intent intent = new Intent(VerificationActivity.this, login_activity.class);
                        intent.putExtra("Mobilenumber",response.getMobilenumber());
                        //intent.putExtra("Uid",E_uid);
                        startActivity(intent);
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

   /* class VerificationTask extends AsyncTask<String, Void, String[]> {
        ProgressDialog dialog = new ProgressDialog(VerificationActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setTitle("Verification in process");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String[] doInBackground(String... params) {
            try {
                verficationRequest();
                return new String[]{"Success"};
            } catch (Exception e) {
                return new String[]{"error"};
            }
        }

        @Override
        protected void onPostExecute(String... result) {
            try {
                //   Toast.makeText(getApplicationContext(), "Responce "+response, Toast.LENGTH_LONG).show();
                if (response.contains("1")) {
                    if (otpflag == 0) {
                        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Emailotp, null);
                        editor.commit();
                        startActivity(new Intent(VerificationActivity.this, VerificationActivity.class));
                        finish();
                    } else {
                        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Mobileotp, null);
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Registration Successfull", Toast.LENGTH_SHORT).show();
                        ApplicationConstants.verify_email = true;
                        startActivity(new Intent(VerificationActivity.this, LoginActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Verification Failed", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public void verficationRequest() {
        BufferedReader reader = null;
        response = "";
        try {
            JSONObject object = new JSONObject();
            if (otpflag == 0) {
                object.put("Email", email);
                object.put("EVerificationCode", otp.getText().toString());
                object.put("Mobilenumber", phoneNo);
            } else {
                object.put("Mobilenumber", phoneNo);
                object.put("MVerificationCode", otp.getText().toString());
            }
            //  String data = URLEncoder.encode(requestkey, "UTF-8") + "=" + URLEncoder.encode(requestvalue, "UTF-8");
            // Defined URL  where to send data
            URL url = new URL(serverurl);
            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Host", "android.schoolportal.gr");
            conn.connect();
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(object.toString());
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }
            response = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // response = response + e.getMessage();
            //e.printStackTrace();
        } catch (IOException e) {
            //  response = response + e.getMessage();
        } catch (JSONException e) {
            //  e.printStackTrace();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

    }*/

}
