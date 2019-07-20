package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.customerapp.Deo.AppUsersResponce;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerEOTPVerificationResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.MOTPVerificationResponse;
import com.webingate.paysmartcustomerapp.customerapp.EWallet;
import com.webingate.paysmartcustomerapp.utils.Utils;

import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class customerappUserprofileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String ID ="idKey";
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    public static final String Password = "passwordkey";
    public static final String UserAccountNo = "UserAccountNokey";
    public static final String UserPhoto = "UserPhoto";

    Toolbar toolbar;
    Toast toast;

    @BindView(R.id.editFAB)
    Button edit;
//    @BindView(R.id.textView228)
//    TextView hphone;
    @BindView(R.id.phoneTextView)
    TextView pphone;
    @BindView(R.id.emailTextView)
    TextView email;
    @BindView(R.id.UsernameTextView)
    TextView uname;
    @BindView(R.id.FirstnameTextView)
    TextView fname;

    @BindView(R.id.textView31)
    TextView joindate;

    @BindView(R.id.phoneno)
    TextView phoneno;

    @BindView(R.id.puphone)
    TextView puphone;

    @BindView(R.id.userImageView)
    ImageView userimage;

    String useracc,usrname,emailid,mobno,profilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerapp_userprofile_activity);
//        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putString(UserAccountNo,Email);
//
//        editor.commit();

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        useracc= prefs.getString(UserAccountNo, null);
        usrname = prefs.getString(Name, null);
        emailid = prefs.getString(Email, null);
        mobno = prefs.getString(Phone, null);
        profilepic = prefs.getString(UserPhoto,null);
        ApplicationConstants.photo = profilepic;

        //puphone.setText("7883890asdf");
        //phoneno.setText(mobno);

        initUI();
        initData();
        FloatingActionButton edit = findViewById(R.id.editFAB);

        edit.setOnClickListener(
                v ->{
                Intent intent = new Intent (customerappUserprofileActivity.this,customerappUserDetailsActivity.class);
                startActivity(intent);
                finish();
        });
    }

    private void initData() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
//        TextView puphone=findViewById(R.id.puphone);
//        TextView phoneno=findViewById(R.id.phoneno);
//        phoneno.setText(mobno);
//        puphone.setText(mobno);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_profile) {
            Toast.makeText(this, "Clicked nav_profile.", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_paymentmode) {
          //  Toast.makeText(this, "Clicked nav_paymentmode.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, customerappPaymentModeActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_bookings) {
            //Toast.makeText(this, "Clicked nav_bookings.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, customerappBookingsMainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_ewallet) {
            Toast.makeText(this, "Clicked nav_ewallet.", Toast.LENGTH_SHORT).show();
            //startActivity( new Intent(customerappUserprofileActivity.this, DashboardEWallet.class));
           GetEwalletStatus(useracc);
        } else if (id == R.id.nav_notification) {
            Toast.makeText(this, "Clicked nav_notification.", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_alerts) {
            Toast.makeText(this, "Clicked nav_alerts.", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_preferences) {
            Toast.makeText(this, "Clicked nav_preferences.", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_coupons) {
//            startActivity(new Intent(this,ImageCroppedTesting.class));
        Toast.makeText(this, "Clicked nav_coupons.", Toast.LENGTH_SHORT).show();

    } else if (id == R.id.nav_sos) {
       // Toast.makeText(this, "Clicked nav_sos.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, customerappSOSListActivity.class);
            startActivity(intent);

    }
        else if (id == R.id.nav_faq) {
            //Toast.makeText(this, "Clicked nav_faq.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, customerappFAQMainMenuActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            SharedPreferences sharedPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
//                            SharedPreferences pref = getApplicationContext().getSharedPreferences(MyPREFERENCES, 0);
//                            Editor editor = pref.edit();
            //editor.putString(ID, null);
            editor.putString(mobno, null);

            editor.putString(UserAccountNo,null);

            editor.commit();
            Intent i = new Intent(customerappUserprofileActivity.this, login_activity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            //i.putExtra("logout",ApplicationConstants.Loginby);
            startActivity(i);
            finish();
//            Intent intent = new Intent(this, login_activity.class);
//
//            startActivity(intent);

        } else if (id == R.id.nav_about_us) {
           // Toast.makeText(this, "Clicked About Us.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, customerappaboutusactivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_contact_us) {
            //Toast.makeText(this, "Clicked Contact Us.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, customerAppContactUs.class);
            startActivity(intent);
        } else if (id == R.id.nav_tticket) {
            //Toast.makeText(this, "Clicked nav_tticket.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, customerappTroubleTicketListActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void initUI() {
        initToolbar();

        TextView tt = findViewById(R.id.emailTextView);
        TextView pht =(TextView) findViewById(R.id.phoneTextView);
        TextView username = findViewById(R.id.UsernameTextView);
        TextView fname = findViewById(R.id.FirstnameTextView);
        TextView puphone=findViewById(R.id.puphone);
        userimage = findViewById(R.id.userImageView);

        //puphone=findViewById(R.id.puphone);

        // Main User Profile Screen
        tt.setText(emailid);
        pht.setText(mobno);
        username.setText(usrname);
        fname.setText(usrname);
        puphone.setText(mobno);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);
        ImageView userImageView = headerLayout.findViewById(R.id.userImageView);
        TextView phoneno = headerLayout.findViewById(R.id.phoneno);
       // Utils.setCircleImageToImageView(this, userImageView, R.drawable.profile1, 0, 0);

        phoneno.setText(mobno);
        ImageView userImageView1 = findViewById(R.id.userImageView1);
        //Utils.setCircleImageToImageView(this, userImageView1, R.drawable.profile1, 0, 0);


        if(ApplicationConstants.photo==null){
            Utils.setCircleImageToImageView(getApplicationContext(), userImageView, R.drawable.profile1, 0, 0);
            Utils.setCircleImageToImageView(getApplicationContext(), userImageView1, R.drawable.profile1, 0, 0);

        }
        else
        {
            //Utils.setCircleImageToImageView(getApplicationContext(), userImageView1, R.drawable.profile1, 0, 0);
            byte[] decodedString= Base64.decode( ApplicationConstants.photo.substring( ApplicationConstants.photo.indexOf(",")+1), Base64.DEFAULT);
            Bitmap image1 = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            userImageView1.setImageBitmap(image1);
            userImageView.setImageBitmap(Utils.getCircularBitmapWithBorder(image1,0,0));
        }



//        if(Utils.isRTL()) {
//            navigationView.setTextDirection(View.TEXT_DIRECTION_RTL);
//        }else {
//            navigationView.setTextDirection(View.TEXT_DIRECTION_LTR);
//        }

//        View headerLayout = navigationView.getHeaderView(0);
//        ImageView userImageView = headerLayout.findViewById(R.id.userImageView);
//        Utils.setCircleImageToImageView(this, userImageView, R.drawable.profile1, 0, 0);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         //getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
    private void initToolbar() {

        toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.baseline_menu_black_24);

        if (toolbar.getNavigationIcon() != null) {
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.md_white_1000), PorterDuff.Mode.OVERLAY);
        }

        toolbar.setTitle("User profile");

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
    public void GetEwalletStatus(String acct){
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(this).getrestadapter()
                .GetEwalletStatus(acct)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MOTPVerificationResponse>>() {
                    @Override
                    public void onCompleted() {
                        //DisplayToast("Successfully Registered");
                        //StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Log.d("OnError ", e.getMessage());
                            //DisplayToast("Error"+e.getMessage());
                            //StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<MOTPVerificationResponse> responselist) {
                        if(responselist.size()!=0 ){
                        MOTPVerificationResponse response = responselist.get(0);

                        if (response.getCode() != null) {
                            DisplayToast(response.getDescription());
                        }
                        else{
                                startActivity( new Intent(customerappUserprofileActivity.this, customerewalletActivity.class));
                            //editor.commit();
                            //startActivity(new Intent(customerEOTPVerificationActivity.this, login_activity.class));
//                       Intent intent = new Intent(customerEOTPVerificationActivity.this, businessappMOTPVerificationActivity.class);
//                        intent.putExtra("eotp","");
                            finish();
                        }
                        }
                         else {
                            startActivity( new Intent(customerappUserprofileActivity.this, customerewalletActivity.class));
                        }
                    }
                });
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
