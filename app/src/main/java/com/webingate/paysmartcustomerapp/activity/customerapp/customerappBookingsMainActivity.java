package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.adapter.customerapp_AdapterBookingType;
import com.webingate.paysmartcustomerapp.data.DataGenerator;
import com.webingate.paysmartcustomerapp.model.BookingType;
import com.webingate.paysmartcustomerapp.utils.Tools;
import com.webingate.paysmartcustomerapp.widget.LineItemDecoration;

import java.util.List;

import static com.webingate.paysmartcustomerapp.fragment.customerAppFragments.customerappUserInfoFragment.UserAccountNumber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class customerappBookingsMainActivity extends AppCompatActivity {
    private customerapp_AdapterBookingType mAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerapp_bookingsmain_activity);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking Types");
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

        List<BookingType> items = DataGenerator.getBookingTypes(this);

        //set data and list adapter
        mAdapter = new customerapp_AdapterBookingType(this, items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new customerapp_AdapterBookingType.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BookingType obj, int position) {
                GoToBookingsList(position);
            }
        });

    }
    private void GoToBookingsList(int pos){

        switch (pos){
            case 0:
                Toast.makeText(getApplicationContext(), "Clicked option 0.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,customerappGetaLyftBookingsListsActivity.class));
                break;
            case 1:
                Toast.makeText(getApplicationContext(), "Clicked option 1.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,customerappFlightBookingsListsActivity.class));
                break;
            default:
                Toast.makeText(getApplicationContext(), "default.", Toast.LENGTH_SHORT).show();
                break;
        }
//        Intent intent;
//        intent = new Intent(this, customerappGetaLyftBookingsListActivity.class);
//        startActivity(intent);
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
