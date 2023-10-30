package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerGetstopsResponse;
import com.webingate.paysmartcustomerapp.customerapp.Ticket_Source_Destination_Date;

import java.io.Serializable;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class customerappBusBookingMainActivity extends AppCompatActivity {
    List<CustomerGetstopsResponse> stops;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerapp_busbookingmain_activity);

        initData();

        initUI();

        initDataBinding();

        initAction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
    }

    private void initUI() {
        initToolbar();
        if(ApplicationConstants.seatsSelected!=null){
            ApplicationConstants.seatsSelected.clear();
        }
        if(ApplicationConstants.passengerlist!=null){
            ApplicationConstants.passengerlist.clear();
        }
        if(ApplicationConstants.passengerage!=null){
            ApplicationConstants.passengerage.clear();
        }
        if(ApplicationConstants.passengergender!=null){
            ApplicationConstants.passengergender.clear();
        }


        loadFragment(new Ticket_Source_Destination_Date());

    }

    private void initDataBinding() {

      //  GetStops();
    }
    public void GetStops(){

      //  StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(this).getrestadapter()
                .getstops()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CustomerGetstopsResponse>>() {
                    @Override
                    public void onCompleted() {
                        //  DisplayToast("Successfully Registered");
                       // StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Log.d("OnError ", e.getMessage());
                          //  DisplayToast("Error");
                           // StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<CustomerGetstopsResponse> responselist) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("details", (Serializable) responselist);
                      //  ApplicationConstants.FRAGMENT = ApplicationConstants.TICKET_SOURCE_DESTINATION;
                      //  goPage(ApplicationConstants.FRAGMENT,bundle);
                    }
                });
    }

    private void initAction() {
    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.baseline_menu_black_24);

        if (toolbar.getNavigationIcon() != null) {
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP);
        }

        toolbar.setTitle("Bus booking");

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

    private void loadFragment(Fragment fragment) {
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.srcdestdatetimeFrame, fragment)
                .commitAllowingStateLoss();
    }
}
