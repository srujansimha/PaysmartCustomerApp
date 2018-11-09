package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import android.location.Criteria;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonObject;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.SphericalUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.adapter.customerapp_VehicleTypesAdapter;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.customerapp.CheckingCabsDialogue;
import com.webingate.paysmartcustomerapp.customerapp.CurrentTrip;
import com.webingate.paysmartcustomerapp.customerapp.Deo.AvailableVehiclesResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CalculatePriceResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerBookingStatusResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.SaveBookingDetailsResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.UpdateBookingstatusResponse;
import com.webingate.paysmartcustomerapp.customerapp.Dialog.ProgressDialog;
import com.webingate.paysmartcustomerapp.customerapp.GetaLyft;
import com.webingate.paysmartcustomerapp.customerapp.LatLngInterpolator;
import com.webingate.paysmartcustomerapp.customerapp.Payments_Dialoguebox;
import com.webingate.paysmartcustomerapp.customerapp.RideLater_Dialoguebox;
import com.webingate.paysmartcustomerapp.object.DirectoryHome9ProductsVO;
import com.webingate.paysmartcustomerapp.repository.directory.DirectoryHome9Repository;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class customerappGetaLyftActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,Payments_Dialoguebox.PaymentDetails,RideLater_Dialoguebox.RideLater,CheckingCabsDialogue.checkingcabsDialogue {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.map_source)
    TextView selectsource;
    @BindView(R.id.source_gps_location)
    Button sourceGpsLocation;
    @BindView(R.id.table_row)
    TableRow tableRow;
    @BindView(R.id.map_destination)
    TextView selectDestination;
    //    @BindView(R.id.taxi)
//    AppCompatButton taxi;
//    @BindView(R.id.meteredtaxi)
//    AppCompatButton meteredtaxi;
//    @BindView(R.id.bus)
    AppCompatButton bus;
    @BindView(R.id.ridenow)
    AppCompatButton rideNow;
    @BindView(R.id.ridelater)
    AppCompatButton rideLater;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    String serverUrl = "";
    int bookingId = 0;
    int dest = 0;
    Place destination, source;
    static GoogleMap mMap;
    static Marker marker, markerDesst, markerCar1, markerCar2, markerBus, markerAmbulance;
    private Marker cabs[] = new Marker[5];
    double sourceLatitude = 0.0, sourceLongitude = 0.0, destLatitude = 0.0, destLongitude = 0.0;
    private static final int CHECKPRICE = 1;
    private static final int BOOKCAB = 2;
    private static final int GETBOOKINGSTATUS = 3;
    private static final int CANCELBOOKING = 4;
    private static final int RIDELATER = 5;
    private static final int GETDIRECTIONS = 6;
    private static final int GETNEARESTVEHICLES = 7;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;
    private static final int CURRENT_TRIP_ACTIVITY = 1;

    private static GoogleApiClient mGoogleApiClient;
    private static final int ACCESS_FINE_LOCATION_INTENT_ID = 3;
    private static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";
    final Handler handler = new Handler();
    // HirevehicleRequest hirevehicleRequest;
    CheckingCabsDialogue checkingCabsDialogue;
    DirectionsResult result;
    boolean isBookingStarted=true;
    Toast toast;
    ProgressDialog dialog;

    //TODO: this is to test then scroll view navigation
    List<DirectoryHome9ProductsVO> productsList;
    customerapp_VehicleTypesAdapter productsAdapter;
    RecyclerView rvProduct;
private boolean mLocationPermissionGranted =false;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* if (timer != null)
            ApplicationConstants.tripFlag = 0;
        timer.cancel();*/

    }

    /*Handler handler = new Handler();
     Runnable updateMarker = new Runnable() {
         @Override
         public void run() {

             Toast.makeText(getApplicationContext(),latitude+" "+longitude,Toast.LENGTH_SHORT).show();
                startPosition=new LatLng(latitude,longitude);
                 mMap.moveCamera(CameraUpdateFactory.newLatLng(startPosition));
                 marker.setPosition(startPosition);

                 // Repeat till progress is complete.
         }
     };*/
    private LocationRequest mLocationRequest;
    ActionBarDrawerToggle toggle;
    private LatLng latLng, latlngnew;
    private String response;
    private Polyline line;
    // private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerapp_getalyft_activity);
        ButterKnife.bind(this);
        dialog = new ProgressDialog.Builder(customerappGetaLyftActivity.this)
                .setTitle("Loading...")
                .setTitleColorRes(R.color.gray)
                .build();
        initGoogleAPIClient();//Init Google API Client
        checkPermissions();//Check Permission
        //  configureCameraIdle();//cofigure drag destionatio selection
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        taxi.setVisibility(View.GONE);
//        meteredtaxi.setVisibility(View.GONE);
//        bus.setVisibility(View.GONE);
        selectsource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dest = 1;
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(customerappGetaLyftActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });
        sourceGpsLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(customerappGetaLyftActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(customerappGetaLyftActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, customerappGetaLyftActivity.this);
               /* if (marker != null) {
                    latlngnew = new LatLng(latitude, longitude);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latlngnew);
                    markerOptions.title("Current Position");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    marker.remove();
                    marker = mMap.addMarker(markerOptions);
                    selectsource.setText("Source :" + GetAddress(latitude, longitude));
                    sourceLatitude = latitude;
                    sourceLongitude = longitude;
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latlngnew));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(16.5f));
                    if (!selectDestination.getText().toString().matches("")) {
                        String url = getDirectionsUrl(new LatLng(sourceLatitude, sourceLongitude), destination.getLatLng());
                        DownloadTask downloadTask = new DownloadTask();
                        //Start downloading json data from Google Directions API
                        downloadTask.execute(url);
                    }
                }*/
            }
        });
        selectDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dest = 2;
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(customerappGetaLyftActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });
        rideNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectDestination.getText().toString().matches("")) {
                    DisplayToast("Please Select Destination");
                } else {
//                    JsonObject object=new JsonObject();
//                    object.addProperty("BNo", ApplicationConstants.bookingNo);
//                    object.addProperty("PackageId", "3");
//                    CalculatePrice(object);
                    Toast.makeText(getApplicationContext(), "Clicked : get a lyft details", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(customerappGetaLyftActivity.this, customerappGetaLyftDetailsActivity.class);
//                    intent.putExtra("source", source.getName() + "," + source.getAddress());
//                    intent.putExtra("destination", destination.getName() + "," + destination.getAddress());
                    startActivity(intent);
                }
            }
        });
        rideLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectDestination.getText().toString().matches("")) {
                    DisplayToast("Please Select Destination");
                } else {

                    RideLater_Dialoguebox rideLater_dialoguebox = new RideLater_Dialoguebox(customerappGetaLyftActivity.this);
                    rideLater_dialoguebox.setCanceledOnTouchOutside(false);
                    rideLater_dialoguebox.show();
                }
            }
        });
        //   AvailableVehicles();
        productsList = DirectoryHome9Repository.getVehicleTypes();
        productsAdapter = new customerapp_VehicleTypesAdapter(productsList);


        rvProduct = findViewById(R.id.rvProducts);

        RecyclerView.LayoutManager productLayoutManager =  new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvProduct.setLayoutManager(productLayoutManager);
        rvProduct.setAdapter(productsAdapter);
       // productsAdapter.getItem(0).itemView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        productsAdapter.setOnItemClickListener((view, promotion, position) -> {

            switch(position){
                case 0:
                    Toast.makeText(getApplicationContext(), "Clicked : get a lyft", Toast.LENGTH_SHORT).show();

//                    Intent intent = new Intent(this, customerappGetaLyftActivity.class);
//                    startActivity(intent);
                    AvailableVehiclesTest(0);
                    break;
                case 1:
                    AvailableVehiclesTest(1);
//                        AppDirectoryHome1Fragment af1 = new AppDirectoryHome1Fragment();
//
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.home9Frame, af1)
//                                .commitAllowingStateLoss();
                    break;
                case 2:
                    AvailableVehiclesTest(2);

                    break;
                case 3:
                    AvailableVehiclesTest(3);
                    break;
                case 4:
                    AvailableVehiclesTest(4);
                    break;
                case 5:
                    AvailableVehiclesTest(5);
                    break;
                default:
                    break;
            }
            //Toast.makeText(getContext(), "Clicked : " + promotion.getName(), Toast.LENGTH_SHORT).show();
        });

    }

    public  void AvailableVehiclesTest(int vehicleType){


        switch (vehicleType){
            case 0:
                ApplicationConstants.marker = R.mipmap.marker_car;
                break;
            case 1:
                ApplicationConstants.marker = R.mipmap.marker_taxi;
                break;
            case 2:
                ApplicationConstants.marker = R.mipmap.marker_car;
                break;
            case 3:
                ApplicationConstants.marker = R.mipmap.marker_taxi;
                break;
            case 4:
                ApplicationConstants.marker = R.mipmap.marker_car;
                break;
                default:
                    break;
        }

        for (int i = 0; i < 5; i++) {
            AvailableVehiclesResponse response= new AvailableVehiclesResponse();
            double lat = 17.459 + (0.001 * (i+1)); //response.getLatitude();
            double lon = 78.423+ (0.001 * (i+1));//response.getLongitude();
            LatLng lng = new LatLng(lat, lon);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(lng);
            markerOptions.title(response.getRegistrationNo());
            markerOptions.icon(BitmapDescriptorFactory.fromResource(ApplicationConstants.marker));
            cabs[i] = mMap.addMarker(markerOptions);
        }

    }

    //toolbar button click
    @Override
    public boolean onSupportNavigateUp() {
        //Toast.makeText(getApplicationContext(),"Back button Pressed",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
        return true;
    }

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3)
                .setApiKey(getString(R.string.google_maps_key))
                .setConnectTimeout(10, TimeUnit.SECONDS)
                .setReadTimeout(10, TimeUnit.SECONDS)
                .setWriteTimeout(10, TimeUnit.SECONDS);
    }

    // this method will provide distance and time between two places
    private String getEndLocationTitle(DirectionsResult results) {
        return "Time :" + results.routes[0].legs[0].duration.humanReadable + " Distance :" + results.routes[0].legs[0].distance.humanReadable;
    }

    // This task will provide directions and path
    private class DirectionsTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                DateTime now = new DateTime();
                // Fetching the data from web service
                Log.i("Directions", "Waiting For directions");
                result = DirectionsApi.newRequest(getGeoContext())
                        .mode(TravelMode.DRIVING).origin(new com.google.maps.model.LatLng(sourceLatitude, sourceLongitude))
                        .destination(new com.google.maps.model.LatLng(destLatitude, destLongitude)).departureTime(now).await();
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String results) {
            super.onPostExecute(results);
            Log.i("Directions", "Got directions");
            if (result != null) {
                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(result.routes[0]
                        .legs[0].startLocation.lat, result.routes[0]
                        .legs[0].startLocation.lng))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                        .title("Source" + result.routes[0].legs[0].startAddress);
                if (marker != null)
                    marker.remove();
                marker = mMap.addMarker(markerOptions);
                markerOptions = new MarkerOptions().position(new LatLng(result.routes[0]
                        .legs[0].endLocation.lat, result.routes[0]
                        .legs[0].endLocation.lng))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                        .title("Destination" + result.routes[0].legs[0].endAddress);
                if (markerDesst != null)
                    markerDesst.remove();
                markerDesst = mMap.addMarker(markerOptions);
                selectsource.setText("Source :" + result.routes[0].legs[0].startAddress);
                selectDestination.setText("Destination :" + result.routes[0].legs[0].endAddress);
                List<LatLng> decodedPath = PolyUtil.decode(result.routes[0].overviewPolyline.getEncodedPath());
                PolylineOptions polylineOptions = new PolylineOptions().addAll(decodedPath);
                if (line != null)
                    line.remove();
                line = mMap.addPolyline(polylineOptions);
            }
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //   mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                requestLocationPermission();
            }
        } else {
            showSettingDialog();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //User has previously accepted this permission
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            //Not in api-23, no need to prompt
            mMap.setMyLocationEnabled(true);
        }
        // mMap.getUiSettings().setZoomControlsEnabled(true);
        // mMap.getUiSettings().setZoomGesturesEnabled(true);

        // mMap.setOnCameraIdleListener(onCameraIdleListener);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        latlngnew = new LatLng(0.0, 0.0);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latlngnew);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(ApplicationConstants.marker));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                destLatitude = latLng.latitude;
                destLongitude = latLng.longitude;
                selectDestination.setText("Destination : " + (destLatitude + "").substring(0, 10) + " , " + (destLongitude + "").substring(0, 10));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(destLatitude, destLongitude));
                markerOptions.title("Destination");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                if (markerDesst != null)
                    markerDesst.remove();
                markerDesst = mMap.addMarker(markerOptions);
                // Getting Directions from source to destination
                customerappGetaLyftActivity.DirectionsTask downloadTask2 = new customerappGetaLyftActivity.DirectionsTask();
                downloadTask2.execute();
                dest = 0;
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, customerappGetaLyftActivity.this);
            }
        });

    }


    /* Initiate Google API Client  */
    private void initGoogleAPIClient() {
        //Without Google API Client Auto Location Dialog will not work
        mGoogleApiClient = new GoogleApiClient.Builder(customerappGetaLyftActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();
    }

    /* Check Location Permission for Marshmallow Devices */
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(customerappGetaLyftActivity.this,
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED)
                requestcallPermission();
            if (ContextCompat.checkSelfPermission(customerappGetaLyftActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else
                showSettingDialog();
        } else
            showSettingDialog();

    }

    /*  Show Popup to access User Permission  */
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(customerappGetaLyftActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(customerappGetaLyftActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);

        } else {
            ActivityCompat.requestPermissions(customerappGetaLyftActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }

    private void requestcallPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(customerappGetaLyftActivity.this, Manifest.permission.CALL_PHONE)) {
            ActivityCompat.requestPermissions(customerappGetaLyftActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    ACCESS_FINE_LOCATION_INTENT_ID);

        } else {
            ActivityCompat.requestPermissions(customerappGetaLyftActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }

    /* Show Location Access Dialog */
    private void showSettingDialog() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//Setting priotity of Location request to high
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);//5 sec Time interval for location update
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient to show dialog always when GPS is off

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            mMap.setMyLocationEnabled(true);
                        }
                        updateGPSStatus("GPS is Enabled in your device");
                            try {
                                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, customerappGetaLyftActivity.this);
                                AvailableVehiclesTest(0);
                            }catch (Exception ex){
                              Log.println(Log.ASSERT,"error",ex.getMessage().toString());
                            }
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(customerappGetaLyftActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
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
                        sourceLatitude = source.getLatLng().latitude;
                        sourceLongitude = source.getLatLng().longitude;
                        selectsource.setText("Source :" + source.getName() + "," + source.getAddress());
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(source.getLatLng());
                        markerOptions.title("Source");
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                        if (marker != null)
                            marker.remove();
                        marker = mMap.addMarker(markerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(source.getLatLng()));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.5f));
                        // Getting Directions from source to destination
                        if (!selectDestination.getText().toString().matches("")) {
                            customerappGetaLyftActivity.DirectionsTask downloadTask = new customerappGetaLyftActivity.DirectionsTask();
                            downloadTask.execute();
                        }
                        dest = 0;
                        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

                        AvailableVehicles();
                    }
                } else if (dest == 2) {
                    destination = PlaceAutocomplete.getPlace(this, data);
                    if (destination != null) {
                        DisplayToast(destination.getName().toString());
                        destLatitude = destination.getLatLng().latitude;
                        destLongitude = destination.getLatLng().longitude;
                        selectDestination.setText("Destination :" + destination.getName() + "," + destination.getAddress());
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(destination.getLatLng());
                        markerOptions.title("Destination");
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                        if (markerDesst != null)
                            markerDesst.remove();
                        markerDesst = mMap.addMarker(markerOptions);
                        // Getting Directions from source to destination
                        customerappGetaLyftActivity.DirectionsTask downloadTask = new customerappGetaLyftActivity.DirectionsTask();
                        downloadTask.execute();
                        dest = 0;
                        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                    }
                }

                break;
            case CURRENT_TRIP_ACTIVITY:
                selectsource.setText("");
                selectDestination.setText("");
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                mMap.clear();
                onMapReady(mMap);
                finish();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(gpsLocationReceiver, new IntentFilter(BROADCAST_ACTION));//Register broadcast receiver to check the status of GPS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && mGoogleApiClient.isConnected()) {
            //  LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }



    //Run on UI
    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            showSettingDialog();
        }
    };

    /* Broadcast receiver to check status of GPS */
    private BroadcastReceiver gpsLocationReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //If Action is Location
            if (intent.getAction().matches(BROADCAST_ACTION)) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                //Check if GPS is turned ON or OFF
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Log.e("About GPS", "GPS is Enabled in your device");
                    updateGPSStatus("GPS is Enabled in your device");
                } else {
                    //If GPS turned OFF show Location Dialog
                    new Handler().postDelayed(sendUpdatesToUI, 10);
                    // showSettingDialog();
                    updateGPSStatus("GPS is Disabled in your device");
                    Log.e("About GPS", "GPS is Disabled in your device");
                }

            }
        }
    };

    //Method to update GPS status text
    private void updateGPSStatus(String status) {
        //  GetLocationTask getLocationTask=new GetLocationTask();
        //  getLocationTask.execute();
        //  handler.postDelayed(updateMarker,2000);
        //  updateMyLocation(toLatLng(oldLocation), toLatLng(mCurrentLocation));
    }


    /* On Request permission method to check the permisison is granted or not for Marshmallow+ Devices  */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        initGoogleAPIClient();
        showSettingDialog();

//        switch (requestCode) {
//            case ACCESS_FINE_LOCATION_INTENT_ID: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    //If permission granted show location dialog if APIClient is not null
//                    if (mGoogleApiClient == null) {
//                        initGoogleAPIClient();
//                        showSettingDialog();
//                    } else
//                        showSettingDialog();
//
//
//                } else {
//                    updateGPSStatus("Location Permission denied.");
//                    Toast.makeText(customerappGetaLyftActivity.this, "Location Permission denied.", Toast.LENGTH_SHORT).show();
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//        }
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000 * 30);
        mLocationRequest.setFastestInterval(1000 * 5);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
         //mMap.animateCamera(CameraUpdateFactory.zoomTo(18.5f));
        //AvailableVehicles();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        //  mLastLocation = location;
       /* if (marker != null) {
            marker.remove();
        }*/
        sourceLatitude = location.getLatitude();
        sourceLongitude = location.getLongitude();

        //  DisplayToast(sourceLatitude + " " + sourceLongitude);
        latLng = latlngnew;
        //Place current location marker
        //below statement commenting to test for zimbzbwe location
        latlngnew = new LatLng(sourceLatitude, sourceLongitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latlngnew);
        markerOptions.title("Source");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        if (marker != null)
            marker.remove();
        marker = mMap.addMarker(markerOptions);
        selectsource.setText("Source : " + sourceLatitude + " , " + sourceLongitude);
        if (GetAddress(sourceLatitude, sourceLongitude) != null)
            selectsource.setText("Source :" + GetAddress(sourceLatitude, sourceLongitude));


        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlngnew));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.5f));
        //stop location updates
        //  if (mGoogleApiClient != null)
        if (!selectDestination.getText().toString().matches("")) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            customerappGetaLyftActivity.DirectionsTask downloadTask = new customerappGetaLyftActivity.DirectionsTask();
            downloadTask.execute();
        }

//        latLng = new LatLng(location.getLatitude(), location.getLongitude() - 0.001225);
//        markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Seshu");
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_car));
//        markerCar2 = mMap.addMarker(markerOptions);
//
//        latLng = new LatLng(location.getLatitude() + 0.002225, location.getLongitude());
//        markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Seshu");
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_bus));
//        markerBus = mMap.addMarker(markerOptions);
//
//        latLng = new LatLng(location.getLatitude() + 0.001225, location.getLongitude());
//        markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Ambulance");
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_ambulance));
//        markerAmbulance = mMap.addMarker(markerOptions);
//
//        float rotation = (float) SphericalUtil.computeHeading(latLng, latlngnew);
//        rotateMarker(markerCar1, latlngnew, rotation);
        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlngnew));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.5f));
    }


    private void rotateMarker(final Marker marker, final LatLng destination, final float rotation) {

        if (marker != null) {

            final LatLng startPosition = marker.getPosition();
            final float startRotation = marker.getRotation();

            final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.Spherical();
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(3000); // duration 3 second
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    try {
                        float v = animation.getAnimatedFraction();
                        LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, destination);
                        float bearing = computeRotation(v, startRotation, rotation);

                        marker.setRotation(bearing);
                        marker.setPosition(newPosition);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            valueAnimator.start();
        }
    }

    private static float computeRotation(float fraction, float start, float end) {
        float normalizeEnd = end - start; // rotate start to 0
        float normalizedEndAbs = (normalizeEnd + 360) % 360;

        float direction = (normalizedEndAbs > 180) ? -1 : 1; // -1 = anticlockwise, 1 = clockwise
        float rotation;
        if (direction > 0) {
            rotation = normalizedEndAbs;
        } else {
            rotation = normalizedEndAbs - 360;
        }

        float result = fraction * rotation + start;
        return (result + 360) % 360;
    }

    private String GetAddress(double latitude, double longitude) {
        String address = "";
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            for (int i = 0; i <= addresses.get(0).getMaxAddressLineIndex(); i++) {
                address = address + addresses.get(0).getAddressLine(i) + ",";
            }
            // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            return address;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

   /* public void GetBookingStatus() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (ApplicationConstants.tripFlag != 0) {
                                if (hirevehicleRequest == null) {
                                    hirevehicleRequest = new HirevehicleRequest();
                                    hirevehicleRequest.execute();
                                } else if (hirevehicleRequest.getStatus() == AsyncTask.Status.FINISHED) {
                                    Log.i("BookingStatus ", "" + ApplicationConstants.tripFlag);
                                    hirevehicleRequest = new HirevehicleRequest();
                                    // PerformBackgroundTask this class is the class that extends AsynchTask
                                    hirevehicleRequest.execute();
                                }
                                Log.i("Request Status", "" + hirevehicleRequest.getStatus());

                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };

        timer.schedule(timerTask, 0, 5000); //execute in every 50000 ms
    }*/

    @Override
    public void DialogueCancelled() {
        isBookingStarted=false;
        JsonObject object=new JsonObject();
        object.addProperty("BNo", ApplicationConstants.bookingNo);
        object.addProperty("BookingStatus", "Cancelled");
        object.addProperty("UpdatedBy", "1");
        object.addProperty("UpdatedUserId", "21");
        UpdateBookingStatus(object);
        AvailableVehicles();
    }
    public void SaveBookingDetails(JsonObject jsonObject) {
        StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(customerappGetaLyftActivity.this).getrestadapter()
                .SaveBookingDetails(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SaveBookingDetailsResponse>>() {
                    @Override
                    public void onCompleted() {
                        //  DisplayToast("Successfully Registered");
                        StopDialogue();
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            e.printStackTrace();
                            Log.d("OnError ", e.getMessage());
                            DisplayToast("Error");
                            StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<SaveBookingDetailsResponse> responselist) {
                        SaveBookingDetailsResponse response = responselist.get(0);
                        if(response.getBookingNumber()!=null) {
                            ApplicationConstants.bookingNo = response.getBookingNumber();
                            isBookingStarted = true;
                            checkingCabsDialogue = new CheckingCabsDialogue(customerappGetaLyftActivity.this);
                            checkingCabsDialogue.setCanceledOnTouchOutside(false);
                            checkingCabsDialogue.show();
                            BookingStatus();
                        }else {
                            DisplayToast("Booking Failed");
                        }

                    }
                });
    }

    public void CalculatePrice(JsonObject jsonObject) {
        StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(customerappGetaLyftActivity.this).getrestadapter()
                .CalculatePrice(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CalculatePriceResponse>>() {
                    @Override
                    public void onCompleted() {
                        //  DisplayToast("Successfully Registered");
                        StopDialogue();
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            StopDialogue();
                            e.printStackTrace();
                            Log.d("OnError ", e.getMessage());
                            DisplayToast("Error");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<CalculatePriceResponse> responselist) {
                        CalculatePriceResponse response = responselist.get(0);
                        Intent intent = new Intent(customerappGetaLyftActivity.this, customerappGetaLyftDetailsActivity.class);
                        intent.putExtra("source", source.getLatLng());
                        intent.putExtra("destination", destination.getLatLng());
                        //Payments_Dialoguebox payments_dialoguebox = new Payments_Dialoguebox(customerappGetaLyftActivity.this, response.getPrice()+"");
                        //payments_dialoguebox.setCanceledOnTouchOutside(false);
                        //payments_dialoguebox.show();
                        startActivity(intent);
                        finish();
                    }
                });
    }
    public void BookingStatus(){
        if(isBookingStarted) {
            JsonObject object = new JsonObject();
            object.addProperty("BNo", ApplicationConstants.bookingNo);
            BookingStatus(object);
        }
    }
    public void BookingStatus(JsonObject jsonObject) {
        // StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(customerappGetaLyftActivity.this).getrestadapter()
                .BookingStatus(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CustomerBookingStatusResponse>>() {
                    @Override
                    public void onCompleted() {
                        //  DisplayToast("Successfully Registered");
                        //   StopDialogue();
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            BookingStatus();
                            Log.d("OnError ", e.getMessage());
                            //  DisplayToast("Error");
                            //    StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<CustomerBookingStatusResponse> responselist) {
                        CustomerBookingStatusResponse response = responselist.get(0);
                        if (response.getBooKingOTP() != null) {
                            if (checkingCabsDialogue != null)
                                checkingCabsDialogue.dismiss();
                            isBookingStarted=false;
                            Intent intent = new Intent(customerappGetaLyftActivity.this, CurrentTrip.class);
                            intent.putExtra("lat", sourceLatitude);
                            intent.putExtra("lon", sourceLongitude);
                            intent.putExtra("destlat", destLatitude);
                            intent.putExtra("destlon", destLongitude);
                            intent.putExtra("details", response);
                            startActivity(intent);
                            finish();
                        }else {
                            BookingStatus();
                        }
                    }
                });
    }

    public void UpdateBookingStatus(JsonObject jsonObject) {
        StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(customerappGetaLyftActivity.this).getrestadapter()
                .UpdateBookingStatus(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UpdateBookingstatusResponse>>() {
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
                    public void onNext(List<UpdateBookingstatusResponse> responselist) {
                        AlertDialog alertDialog = new AlertDialog.Builder(customerappGetaLyftActivity.this, R.style.Dialog_Theme).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("All Cabs Were Busy Please Try Again");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                });
    }

    public void AdvanceBookingDetails(JsonObject jsonObject) {
        StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(customerappGetaLyftActivity.this).getrestadapter()
                .AdvanceBookingDetails(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SaveBookingDetailsResponse>>() {
                    @Override
                    public void onCompleted() {
                        DisplayToast("Booking Completed");
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
                    public void onNext(List<SaveBookingDetailsResponse> responselist) {

                    }
                });
    }

    public void AvailableVehicles() {
        if (!isBookingStarted){
            JsonObject object = new JsonObject();
            object.addProperty("CustomerPhoneNo", ApplicationConstants.mobileNo);
            object.addProperty("SrcLatitude", sourceLongitude);
            object.addProperty("SrcLongitude", sourceLongitude);
            object.addProperty("VehicleGroupId", "69");
            AvailableVehicles(object);
        }
    }
    public void AvailableVehicles(JsonObject jsonObject){
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(customerappGetaLyftActivity.this).getrestadapter()
                .AvailableVehicles(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<AvailableVehiclesResponse>>() {
                    @Override
                    public void onCompleted() {
                        //  DisplayToast("Booking Completed");
                        AvailableVehicles();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            AvailableVehicles();
                            Log.d("OnError ", e.getMessage());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<AvailableVehiclesResponse> responselist) {

                        if (responselist==null) {
                            for (int i = 0; i < 5; i++) {
                                if (cabs[i] != null)
                                    cabs[i].remove();
                            }
                            Log.i("Booking status", "No Cabs Found : ");
                            DisplayToast("No Cabs Found , Checking For cabs.....");
                        } else {
                            for (int i = 0; i < 5; i++) {
                                if (cabs[i] != null)
                                    cabs[i].remove();
                            }
                            Log.i("Booking status", "Cabs Found ");
                            for (int i = 0; i < responselist.size(); i++) {
                                AvailableVehiclesResponse response=responselist.get(i);
                                double lat = response.getLatitude();
                                double lon = response.getLongitude();
                                LatLng lng = new LatLng(lat, lon);
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(lng);
                                markerOptions.title(response.getRegistrationNo());
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(ApplicationConstants.marker));
                                cabs[i] = mMap.addMarker(markerOptions);
                            }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void PaymentDetails(String paymentType) {
        DateFormat dateFormatter = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
        dateFormatter.setLenient(false);
        Date today = new Date();
        String datetime = dateFormatter.format(today);
        JsonObject object=new JsonObject();
        object.addProperty("flag", "i");
        object.addProperty("Id", "");
        object.addProperty("CompanyId", "2");
        object.addProperty("BNo", ApplicationConstants.bookingNo);
        object.addProperty("BookedDate", datetime.substring(0, 11));
        object.addProperty("BookedTime", datetime.substring(11));
        object.addProperty("DepartueDate", datetime.substring(0, 11));
        object.addProperty("DepartureTime", datetime.substring(11));
        object.addProperty("BookingType", "currentbooking");
        object.addProperty("Src", selectsource.getText().toString());
        object.addProperty("Dest", selectDestination.getText().toString());
        object.addProperty("SrcId", "15");
        object.addProperty("DestId", "35");
        object.addProperty("SrcLatitude", sourceLatitude + "");
        object.addProperty("SrcLongitude", sourceLongitude + "");
        object.addProperty("DestLatitude", destLatitude + "");
        object.addProperty("DestLongitude", destLongitude + "");
        object.addProperty("VechId", "12");
        object.addProperty("PackageId", "101");
        object.addProperty("Pricing", "300");
        object.addProperty("DriverId", "");
        object.addProperty("DriverPhoneNo", "");
        object.addProperty("CustomerPhoneNo", ApplicationConstants.mobileNo);
        object.addProperty("CustomerId", "568");
        object.addProperty("BookingStatus", "New");
        object.addProperty("NoofVehicles", "1");
        object.addProperty("NoofSeats", "1");
        object.addProperty("ClosingDate", "");
        object.addProperty("ClosingTime", "");
        object.addProperty("CancelledOn", "");
        object.addProperty("CancelledBy", "");
        object.addProperty("BookingChannel", "app");
        object.addProperty("Reasons", "");
        object.addProperty("PaymentTypeId", paymentType);
        SaveBookingDetails(object);
    }

    @Override
    public void RideLater(String date, String time) {
        Log.i("Booking status", "Ride later calling");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
        dateFormatter.setLenient(false);
        Date today = new Date();
        String datetime = dateFormatter.format(today);
        JsonObject object=new JsonObject();
        object.addProperty("flag", "U");
        object.addProperty("Id", "");
        object.addProperty("CompanyId", "1");
        object.addProperty("BNo", "00");
        object.addProperty("BookedDate", datetime.substring(0, 11));
        object.addProperty("BookedTime", datetime.substring(11));
        object.addProperty("DepartueDate", ApplicationConstants.bookingDate);
        object.addProperty("DepartureTime", ApplicationConstants.bookingTime);
        object.addProperty("BookingType", "Advancebooking");
        object.addProperty("Src", selectsource.getText().toString());
        object.addProperty("Dest", selectDestination.getText().toString());
        object.addProperty("SrcId", "15");
        object.addProperty("DestId", "35");
        object.addProperty("SrcLatitude", sourceLatitude + "");
        object.addProperty("SrcLongitude", sourceLongitude + "");
        object.addProperty("DestLatitude", destination.getLatLng().latitude + "");
        object.addProperty("DestLongitude", destination.getLatLng().longitude + "");
        object.addProperty("VechId", "");
        object.addProperty("PackageId", "250");
        object.addProperty("Pricing", "300");
        object.addProperty("DriverId", "");
        object.addProperty("DriverPhoneNo", "");
        object.addProperty("CustomerPhoneNo", ApplicationConstants.mobileNo);
        object.addProperty("CustomerId", "568");
        object.addProperty("BookingStatus", "New");
        object.addProperty("NoofVehicles", "1");
        object.addProperty("NoofSeats", "1");
        object.addProperty("ClosingDate", "");
        object.addProperty("ClosingTime", "");
        object.addProperty("CancelledOn", "");
        object.addProperty("CancelledBy", "");
        object.addProperty("BookingChannel", "app");
        object.addProperty("Reasons", "");
        AdvanceBookingDetails(object);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Unregister receiver on destroy
        if (gpsLocationReceiver != null)
            unregisterReceiver(gpsLocationReceiver);
    }
}


