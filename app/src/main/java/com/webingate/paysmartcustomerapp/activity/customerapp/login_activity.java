package com.webingate.paysmartcustomerapp.activity.customerapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rilixtech.CountryCodePicker;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.adapter.uicollection.GeneralItemSpinnerAdapter;
import com.webingate.paysmartcustomerapp.object.GeneralList;
import com.webingate.paysmartcustomerapp.repository.customerapp.customerappRepository;
import com.webingate.paysmartcustomerapp.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//public class login_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
public class login_activity extends AppCompatActivity{

   // ArrayList<GeneralList> countriesList;
    TextView forgotTextView, signUpTextView;
    @BindView(R.id.loginButton)
    Button loginButton;
    CardView facebookCardView, twitterCardView;
   // Spinner spinner;
    ImageView bgImageView;
   // ImageView countryImage;
   // ArrayList<String> list;
    ArrayAdapter<GeneralList> adapter;
    CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerapp_login_activity);

        initUI();

        initDataBindings();

        initActions();
    }

    //region Init Functions
    private void initUI() {
        forgotTextView = findViewById(R.id.forgotTextView);
        signUpTextView = findViewById(R.id.signuptTextView);

        loginButton = findViewById(R.id.loginButton);
//        facebookCardView = findViewById(R.id.facebookCardView);
//        twitterCardView = findViewById(R.id.twitterCardView);
        bgImageView = findViewById(R.id.bgImageView);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.setCustomMasterCountries("IN,ZW,AF");
//      //  countryImage = findViewById(R.id.countryImg);
//        spinner = findViewById(R.id.spinner);
//        spinner.setOnItemSelectedListener(this);
//
//       // list = new ArrayList<GeneralList>(countriesList);
//        countriesList = customerappRepository.getcountriesListJson();
//        adapter = new ArrayAdapter<GeneralList>(this, android.R.layout.simple_spinner_item, countriesList);
//
//        GeneralItemSpinnerAdapter uiloginasCustomSpinnerAdapter =new GeneralItemSpinnerAdapter(getApplicationContext(),countriesList);
//        spinner.setAdapter(uiloginasCustomSpinnerAdapter);
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

            Intent intent = new Intent(this, customerDashboardActivity.class);
           // EditText editText = (EditText) findViewById(R.id.editText);
           // String message = editText.getText().toString();
          //  intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);

        });

//        facebookCardView.setOnClickListener(view -> {
//            Toast.makeText(getApplicationContext(), "Clicked Facebook.", Toast.LENGTH_SHORT).show();
//        });
//
//        twitterCardView.setOnClickListener(view -> {
//            Toast.makeText(getApplicationContext(), "Clicked Twitter.", Toast.LENGTH_SHORT).show();
//        });
    }


}
