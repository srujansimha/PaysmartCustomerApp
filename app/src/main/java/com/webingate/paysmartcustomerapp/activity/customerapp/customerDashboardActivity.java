package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.customerapp.Deo.AppUsersResponce;
import com.webingate.paysmartcustomerapp.fragment.customerAppFragments.customerAppDashboardFragment;
import com.webingate.paysmartcustomerapp.utils.Utils;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class customerDashboardActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String ID ="idKey";
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    public static final String Password = "passwordkey";
    public static final String UserAccountNo = "UserAccountNokey";

    String useracc;
    Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerapp_dashboard_activity);
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        ApplicationConstants.userAccountNo= prefs.getString(UserAccountNo, null);

        initData();

        initUI();

        initDataBinding();

        initAction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_more, menu);
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

        BottomNavigationView bottomNavigationView = findViewById(R.id.home9BottomNavigation);
        Utils.removeShiftMode(bottomNavigationView);

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

//        View badge = LayoutInflater.from(this)
//                .inflate(R.layout.customerapp_notificationcount_item, bottomNavigationMenuView, false);
//        TextView tv = badge.findViewById(R.id.notification_badge);
//        tv.setText("8+");
//        itemView.addView(badge);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
//                case R.id.searchMenu:
//                    loadFragment(new customerAppDashboardFragment());
//                    break;
                case R.id.orderMenu:
                    startActivity(new Intent(customerDashboardActivity.this, customerappCouponsListActivity.class));
                    break;
                case R.id.inboxMenu:
                    startActivity(new Intent(customerDashboardActivity.this, customerappNotificationListActivity.class));
                    break;
                case R.id.profileMenu:
                    startActivity(new Intent(customerDashboardActivity.this, customerappUserprofileActivity.class));

                    //loadFragment(new AppDirectoryHome4Fragment());
                    //GetAppUserDetails( ApplicationConstants.userAccountNo);
//                    Intent intent = new Intent(this, customerappUserprofileActivity.class);
//                    startActivity(intent);
                    break;
                default:
                    loadFragment(new customerAppDashboardFragment());
                    break;
            }

            //Toast.makeText(getApplicationContext(), "Clicked " + item.getTitle(), Toast.LENGTH_SHORT).show();

            return false;
        });

        loadFragment(new customerAppDashboardFragment());

    }

    private void initDataBinding() {
    }

    private void initAction() {
    }

    public void GetAppUserDetails(String userAccountNo){
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(this).getrestadapter()
                .getAppUserDetails(userAccountNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<AppUsersResponce>>() {
                    @Override
                    public void onCompleted() {
                       // DisplayToast("Successfully Registered");
                        //StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            //Log.d("OnError ", e.getMessage());
                            DisplayToast("Error"+e.getMessage());
                            //StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<AppUsersResponce> responselist) {
                        AppUsersResponce response = responselist.get(0);
                        if(response.getCode()!=null){
                            DisplayToast(response.getDescription());
                        }else {
                            DrawerLayout drawer = findViewById(R.id.drawer_layout);
                            SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Phone, response.getmnumber());
                            editor.putString(Email, response.getemail());
                            editor.putString(Name, response.getUsername());
                            editor.commit();
                            startActivity(new Intent(customerDashboardActivity.this, customerappUserprofileActivity.class));
                            finish();
                        }
                    }
                });
    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.baseline_menu_black_24);

        if (toolbar.getNavigationIcon() != null) {
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP);
        }

        toolbar.setTitle("Customer dashboard");

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
                .replace(R.id.home9Frame, fragment)
                .commitAllowingStateLoss();
    }
    public void DisplayToast(String text){
        if(toast!=null){
            toast.cancel();
            toast=null;

        }
        toast= Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
        toast.show();

    }
}
