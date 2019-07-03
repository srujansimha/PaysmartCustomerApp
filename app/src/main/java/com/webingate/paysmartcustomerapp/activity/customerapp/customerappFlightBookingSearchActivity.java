package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.utils.Tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.ButterKnife;

public class customerappFlightBookingSearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    EditText destination,orgin;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;
    public static final int TYPE_AIRPORT=2;
    int dest = 0;
    Toast toast;
    Place getdestinationname, source;
    TextView journeyDate,adults,child,infants,selectcabin;
    Calendar dateTime = Calendar.getInstance();
    Button confirmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerapp_flightbookingsearch);
        ButterKnife.bind(this);
        initUI();
        initToolbar();
        initActions();
    }

    private void initUI() {
        destination=findViewById(R.id.destination);
        orgin=findViewById(R.id.orgin);
        journeyDate = findViewById(R.id.journeyDate);
        confirmButton=findViewById(R.id.confirmButton);
        adults = findViewById(R.id.adults);
        child = findViewById(R.id.child);
        infants = findViewById(R.id.infants);
        selectcabin = findViewById(R.id.selectcabin);




    }


    private void initActions() {

        if(!(ApplicationConstants.fsource==null)){
            orgin.setText(ApplicationConstants.fsource);
        }
        if(!(ApplicationConstants.fdestination==null)){
            destination.setText(ApplicationConstants.fdestination);
        }
        if(!(ApplicationConstants.adultquantity==null)){
            adults.setText(ApplicationConstants.adultquantity+" Adult");
        }
        if(!(ApplicationConstants.childquantity==null)){
            child.setText(ApplicationConstants.childquantity+" Child");
        }
        if(!(ApplicationConstants.infantquantity==null)){
            infants.setText(ApplicationConstants.infantquantity+" Infant");
        }
        if(!(ApplicationConstants.CabinName==null)) {
            selectcabin.setText(ApplicationConstants.CabinName);
        }
        if(!(ApplicationConstants.fdate==null)){
            journeyDate.setText(ApplicationConstants.fdate);
        }
        adults.setOnClickListener(view -> {
            startActivity(new Intent(this,customerappFlightBookingselectioncabin.class));
        });
        child.setOnClickListener(view -> {
            startActivity(new Intent(this,customerappFlightBookingselectioncabin.class));
        });
        infants.setOnClickListener(view -> {
            startActivity(new Intent(this,customerappFlightBookingselectioncabin.class));
        });
        selectcabin.setOnClickListener(view -> {
            startActivity(new Intent(this,customerappFlightBookingselectioncabin.class));
        });

        orgin.setOnClickListener(view -> {
            ApplicationConstants.fstatus=1;
            startActivity(new Intent(this,CustomerApp_FlightAirports.class));
            if(!(ApplicationConstants.fsource==null)){
                orgin.setText(ApplicationConstants.fsource);
            }



//            try {
//                dest = 1;
//                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//
//                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS).setTypeFilter(1007).setTypeFilter(34)
//                        .build();
//
//                Intent intent =
//                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
//                                .setFilter(typeFilter)
//
//                                .build(customerappFlightBookingSearchActivity.this);
//                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
//            } catch (GooglePlayServicesRepairableException e) {
//                // TODO: Handle the error.
//            } catch (GooglePlayServicesNotAvailableException e) {
//                // TODO: Handle the error.
//            }
        });
        destination.setOnClickListener(view -> {
            ApplicationConstants.fstatus=2;
            startActivity(new Intent(this,CustomerApp_FlightAirports.class));
            if(!(ApplicationConstants.fdestination==null)){
                destination.setText(ApplicationConstants.fdestination);
            }
//            try {
//                dest = 2;
//                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//
//                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS).setTypeFilter(1007).setTypeFilter(34)
//                        .build();
//                Intent intent =
//                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
//                                .setFilter(typeFilter)
//                                .build(customerappFlightBookingSearchActivity.this);
//                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
//            } catch (GooglePlayServicesRepairableException e) {
//                // TODO: Handle the error.
//            } catch (GooglePlayServicesNotAvailableException e) {
//                // TODO: Handle the error.
//            }
        });
       journeyDate.setOnClickListener(view -> {
           new DatePickerDialog(this, datePickerDialog, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
       });

        confirmButton.setOnClickListener(view -> {
            DisplayToast("Clicked on Confirm");
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
    @SuppressLint("MissingPermission")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PLACE_AUTOCOMPLETE_REQUEST_CODE:
                if (dest == 1) {
                    source = PlaceAutocomplete.getPlace(this, data);
                    if (source != null) {
                        DisplayToast(source.getName().toString());
                        orgin.setText(source.getName().toString());

                        dest = 0;

                    }
                } else if (dest == 2) {
                    getdestinationname = PlaceAutocomplete.getPlace(this, data);
                    if (getdestinationname != null) {
                        DisplayToast(getdestinationname.getName().toString());
                        destination.setText(getdestinationname.getName().toString());
                        dest = 0;

                    }
                }

                break;

        }
    }
    DatePickerDialog.OnDateSetListener datePickerDialog = (view, year, monthOfYear, dayOfMonth) -> {
        dateTime.set(Calendar.YEAR, year);
        dateTime.set(Calendar.MONTH, monthOfYear);
        dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDate();
    };
    private void updateDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String shortTimeStr = sdf.format(dateTime.getTime());
        ApplicationConstants.fdate = shortTimeStr;
        journeyDate.setText( ApplicationConstants.fdate);
    }

    public void DisplayToast(String text){
        if(toast!=null){
            toast.cancel();
            toast=null;

        }
        toast=Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
        toast.show();

    }

}
