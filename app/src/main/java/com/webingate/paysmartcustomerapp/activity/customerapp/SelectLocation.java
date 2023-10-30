package com.webingate.paysmartcustomerapp.activity.customerapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Status;

import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.ui.PlaceAutocomplete;
import com.google.android.libraries.places.compat.ui.PlaceAutocompleteFragment;
import com.google.android.libraries.places.compat.ui.PlacePicker;
import com.google.android.libraries.places.compat.ui.PlaceSelectionListener;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;

public class SelectLocation extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String ID ="idKey";
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    public static final String Password = "passwordkey";
    public static final String UserAccountNo = "UserAccountNokey";

    public static final String CURRENTLOC="currentloc";
    final int PLACE_PICKER_REQUEST = 1;
    final int AUTOCOMPLETE_REQUEST = 2;
    TextView placeName,currentlocation;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_location);

        placeName= findViewById(R.id.placeName);
        placeName.setVisibility(View.GONE);
        currentlocation=findViewById(R.id.currentlocation);
        setupAutoCompleteFragment();

        placeName.setOnClickListener(view -> {

            showAutocomplete();
        });
//        currentlocation.setOnClickListener(view -> {
//            ApplicationConstants.currentloc=0;
//            ApplicationConstants.cslat=0.0;
//            ApplicationConstants.cslog=0.0;
//            SharedPreferences sharedPref = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putInt(CURRENTLOC,0);
//            editor.putString(Customerlat,"0.0");
//            editor.putString(Customerlog,"0.0");
//            editor.commit();
//
//            startActivity(new Intent(this,SelectCustomerLocation.class));
//            finish();
//        });

    }

    @Override
    public void onBackPressed(){
        finish();
    }

    private void setupAutoCompleteFragment() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                showPlace(place);
            }

            @Override
            public void onError(Status status) {

                showResponse("An error occurred: " + status);
            }
        });
    }

    private void showResponse(String response) {

    }
    private void showPlace(Place place) {
//        showResponse(String.format("%s: '%s'", source, place.getName()));
//        showPhotoForPlace(place);
         //Toast.makeText(this, "Show Data: " + place.getAddress(), Toast.LENGTH_SHORT).show();
        //destination = PlaceAutocomplete.getPlace(this, place);
        ApplicationConstants.cdlat = String.valueOf(place.getLatLng().latitude);
        ApplicationConstants.cdlog = String.valueOf(place.getLatLng().longitude);
//        ApplicationConstants.cslat = 17.4171;
//        ApplicationConstants.cslog = 78.4535;

        ApplicationConstants.customeraddress = String.valueOf(place.getAddress());
        ApplicationConstants.currentloc = 1;
        Toast.makeText(this, "Destination: " + ApplicationConstants.customeraddress, Toast.LENGTH_SHORT).show();

        //startActivity(new Intent(this,customerappGetaLyftActivity.class));
        finish();

    }

    private void showAutocomplete() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
           // startActivityForResult(intent, AUTOCOMPLETE_REQUEST);
            ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                            Intent data = result.getData();
                            // ...
                        }
                    }
            );

            Intent intent1 = new Intent();
            startActivityForResult.launch(intent1);
        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil.getErrorDialog(e.getConnectionStatusCode(), this, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            showResponse(getString(R.string.google_play_services_error));
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Place place;
        String source;
        if (resultCode == RESULT_OK) {
            if (requestCode == PLACE_PICKER_REQUEST) {
                place = PlacePicker.getPlace(this, data);
                source = getString(R.string.place_picker);
            } else if (requestCode == AUTOCOMPLETE_REQUEST) {
                place = PlaceAutocomplete.getPlace(this, data);
                source = getString(R.string.autocomplete);
            } else {
                return;
            }
            showPlace(place);
        }
    }

}
