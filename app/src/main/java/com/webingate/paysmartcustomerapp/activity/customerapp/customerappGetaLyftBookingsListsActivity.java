package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.utils.common_adapter.ViewPagerAdapter;

public class customerappGetaLyftBookingsListsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerapp_currenttrips_activity);

        initToolbar();

        ViewPager viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }
    private void setupViewPager(ViewPager viewPager) {


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        customerappCurrentBookingListFragment GameListLiveFragmentobj=new customerappCurrentBookingListFragment();
        customerappCompletedBookingListFragment GameListUpcomingFragmentobj=new customerappCompletedBookingListFragment();
        customerappCancelBookingListFragment GameListCompletedFragmentobj=new customerappCancelBookingListFragment();


        viewPagerAdapter.addFragment(GameListLiveFragmentobj, "Current & in progress");
        viewPagerAdapter.addFragment(GameListUpcomingFragmentobj, "Completed");
        viewPagerAdapter.addFragment(GameListCompletedFragmentobj, "Cancelled");

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(1);

//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new customerappCurrentBookingListFragment(), "Current & in progress");
//        adapter.addFragment(new customerappCompletedBookingListFragment(), "Completed");
//        adapter.addFragment(new customerappCancelBookingListFragment(), "Cancelled");
//       //adapter.addFragment(new UiContainerTabLayoutTab3Fragment(), "Tab 3");
//        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(1);
    }

    //region Init Toolbar

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_black_24);

        if (toolbar.getNavigationIcon() != null) {
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP);
        }

        toolbar.setTitle("Booking List");

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, item.getItemId(), Toast.LENGTH_SHORT).show();
        int iid = item.getItemId();
        if (iid == android.R.id.home) {
            finish();
        }
        else if(iid == R.menu.menu_edit)
        {
           Intent i = new Intent(this, customerappTroubleTicketCreation.class);
           startActivity(i);
        }


        return super.onOptionsItemSelected(item);
    }
}