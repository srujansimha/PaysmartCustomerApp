package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.adapter.customerapp_AdapterBookingType;
import com.webingate.paysmartcustomerapp.adapter.customerapp_AdapterFAQItem;
import com.webingate.paysmartcustomerapp.data.DataGenerator;
import com.webingate.paysmartcustomerapp.model.BookingType;
import com.webingate.paysmartcustomerapp.model.People;

import com.webingate.paysmartcustomerapp.model.Social;
import com.webingate.paysmartcustomerapp.utils.Tools;
import com.webingate.paysmartcustomerapp.widget.LineItemDecoration;

import java.util.List;

public class customerappFAQMainMenuActivity extends AppCompatActivity {
   // private customerapp_AdapterFAQItem mAdapter;
    private customerapp_AdapterBookingType mAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerapp_faqmain_activity);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAQ categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_search, menu);
//        return true;
//    }
    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setHasFixedSize(true);

        List<BookingType> items = DataGenerator.getFAQCategoryData(this);

        //set data and list adapter
       // mAdapter = new customerapp_AdapterFAQItem(this, items);
        mAdapter = new customerapp_AdapterBookingType(this, items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new customerapp_AdapterBookingType.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BookingType obj, int position) {
                GoToFAQList(position);
            }
        });

    }
    private void GoToFAQList(int pos){
        Intent intent;
        intent = new Intent(this, customerappFAQListDetailActivity.class);
        startActivity(intent);
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
}
