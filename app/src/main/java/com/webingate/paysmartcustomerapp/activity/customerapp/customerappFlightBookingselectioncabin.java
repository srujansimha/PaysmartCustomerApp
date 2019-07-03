package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.utils.Tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.ButterKnife;

public class customerappFlightBookingselectioncabin extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    EditText destination,orgin;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;
    public static final int TYPE_AIRPORT=2;
    int dest = 0;
    Toast toast;
    String selectioncabin=null;
    Place getdestinationname, source;
    TextView journeyDate,adults,child,infants,selectcabin;
    Calendar dateTime = Calendar.getInstance();
    Button done;
    EditText adultqty,childqty,infantqty;
    ImageView minusImageView,plusImageView,minusImageView1,plusImageView1,minusImageView2,plusImageView2;
    private Button[] btn = new Button[3];
    private Button btn_unfocus;
    private int[] btn_id = {R.id.btn0, R.id.btn1, R.id.btn2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_custmer_selection);
        ButterKnife.bind(this);
        initUI();
        initToolbar();
        initActions();
    }

    private void initUI() {
        for(int i = 0; i < btn.length; i++){
            btn[i] = (Button) findViewById(btn_id[i]);
            btn[i].setBackgroundColor(Color.rgb(207, 207, 207));
            btn[i].setOnClickListener(customerappFlightBookingselectioncabin.this);
        }

        btn_unfocus = btn[0];

        done=findViewById(R.id.done);
        adultqty=findViewById(R.id.qtyEditText);
        childqty=findViewById(R.id.qtyEditTextchildren);
        infantqty=findViewById(R.id.qtyEditTextinfants);

        minusImageView=findViewById(R.id.minusImageView);
        plusImageView=findViewById(R.id.plusImageView);
        minusImageView1=findViewById(R.id.minusImageView1);
        plusImageView1=findViewById(R.id.plusImageView1);
        minusImageView2=findViewById(R.id.minusImageView2);
        plusImageView2=findViewById(R.id.plusImageView2);



    }


    private void initActions() {

        if(!(ApplicationConstants.adultquantity==null)){
            adultqty.setText(ApplicationConstants.adultquantity);
        }
        if(!(ApplicationConstants.childquantity==null)){
            childqty.setText(ApplicationConstants.childquantity);
        }
        if(!(ApplicationConstants.infantquantity==null)){
            infantqty.setText(ApplicationConstants.infantquantity);
        }
        if(!(ApplicationConstants.CabinName==null)){
            int a=0;
            if(ApplicationConstants.CabinName=="Economy"){
                a=1;

            }else if(ApplicationConstants.CabinName=="PremiumEconomy"){
                a=2;
            }else if(ApplicationConstants.CabinName=="Business"){
                a=3;
            }




            switch (a){
                case 1 :
                    selectioncabin="Economy";
                    ApplicationConstants.CabinName=selectioncabin;
                    setFocus(btn_unfocus, btn[0]);
                    break;

                case 2:
                    selectioncabin="PremiumEconomy";
                    ApplicationConstants.CabinName=selectioncabin;
                    setFocus(btn_unfocus, btn[1]);
                    break;

                case 3:
                    selectioncabin="Business";
                    ApplicationConstants.CabinName=selectioncabin;
                    setFocus(btn_unfocus, btn[2]);
                    break;



            }
        }


        //Adults Quanity
        minusImageView.setOnClickListener(v -> {

            try {
                int value = Integer.parseInt(adultqty.getText().toString());

                if (value > 0) {
                    value -= 1;
                }

                adultqty.setText(String.valueOf(value));
                ApplicationConstants.adultquantity=String.valueOf(value);

            } catch (Exception ignored) {
            }
        });

        plusImageView.setOnClickListener(v -> {

            try {
                int value = Integer.parseInt(adultqty.getText().toString());

                value += 1;

                adultqty.setText(String.valueOf(value));
                ApplicationConstants.adultquantity=String.valueOf(value);

            } catch (Exception ignored) {
            }
        });


        //Child Quanity
        minusImageView1.setOnClickListener(v -> {

            try {
                int value = Integer.parseInt(childqty.getText().toString());

                if (value > 0) {
                    value -= 1;
                }

                childqty.setText(String.valueOf(value));
                ApplicationConstants.childquantity=String.valueOf(value);

            } catch (Exception ignored) {
            }
        });

        plusImageView1.setOnClickListener(v -> {

            try {
                int value = Integer.parseInt(childqty.getText().toString());

                value += 1;

                childqty.setText(String.valueOf(value));
                ApplicationConstants.childquantity=String.valueOf(value);

            } catch (Exception ignored) {
            }
        });
        //Child Quanity
        minusImageView2.setOnClickListener(v -> {

            try {
                int value = Integer.parseInt(infantqty.getText().toString());

                if (value > 0) {
                    value -= 1;
                }

                infantqty.setText(String.valueOf(value));
                ApplicationConstants.infantquantity=String.valueOf(value);
            } catch (Exception ignored) {
            }
        });

        plusImageView2.setOnClickListener(v -> {

            try {
                int value = Integer.parseInt(infantqty.getText().toString());

                value += 1;

                infantqty.setText(String.valueOf(value));
                ApplicationConstants.infantquantity=String.valueOf(value);
            } catch (Exception ignored) {
            }
        });


        done.setOnClickListener(view -> {





            startActivity(new Intent(this,customerappFlightBookingSearchActivity.class));
        });
    }
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Flight Booking");
        Tools.setSystemBarColor(this, R.color.md_blue_800);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_setting, menu);
        //Tools.changeMenuIconColor(menu, getResources().getColor(android.R.color.white));
        //Tools.changeOverflowMenuIconColor(toolbar, getResources().getColor(android.R.color.white));
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


    public void DisplayToast(String text){
        if(toast!=null){
            toast.cancel();
            toast=null;

        }
        toast=Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
        toast.show();

    }
    @Override
    public void onClick(View v) {
        //setForcus(btn_unfocus, (Button) findViewById(v.getId()));
        //Or use switch
        switch (v.getId()){
            case R.id.btn0 :
                selectioncabin="Economy";
                ApplicationConstants.CabinName=selectioncabin;
                setFocus(btn_unfocus, btn[0]);
                break;

            case R.id.btn1 :
                selectioncabin="PremiumEconomy";
                ApplicationConstants.CabinName=selectioncabin;
                setFocus(btn_unfocus, btn[1]);
                break;

            case R.id.btn2 :
                selectioncabin="Business";
                ApplicationConstants.CabinName=selectioncabin;
                setFocus(btn_unfocus, btn[2]);
                break;


        }
    }

    private void setFocus(Button btn_unfocus, Button btn_focus){
        btn_unfocus.setTextColor(Color.rgb(49, 50, 51));
        btn_unfocus.setBackgroundColor(Color.rgb(207, 207, 207));
        btn_focus.setTextColor(Color.rgb(255, 255, 255));
        btn_focus.setBackgroundColor(Color.rgb(3, 106, 150));
        this.btn_unfocus = btn_focus;
    }

}
