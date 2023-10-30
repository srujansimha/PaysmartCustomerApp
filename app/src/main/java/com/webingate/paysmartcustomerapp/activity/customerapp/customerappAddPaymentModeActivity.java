package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.adapter.customerappCardListAdapter;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.customerapp.Deo.AddCardResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class customerappAddPaymentModeActivity extends AppCompatActivity {


    private LinearLayout addcards;
    RecyclerView recyclerView;
    customerappCardListAdapter madpter;
    Toast toast;
    @BindView(R.id.clistcount)
    TextView clist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerapp_paymentmode_list);
        addcards=(LinearLayout)findViewById(R.id.addcards);
        clist=(TextView)findViewById(R.id.clistcount);
        recyclerView = findViewById(R.id.placeList1RecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        initData();

        initUI();

        initDataBinding();

        initActions();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
     GetCardList(ApplicationConstants.userid);
    }

    private void initUI() {

        // Init Toolbar
        initToolbar();

    }

    private void initDataBinding() {

    }

    private void initActions() {


        addcards.setOnClickListener(v ->{
            Toast.makeText(this, "clicked one Add Card", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(customerappAddPaymentModeActivity.this,customerappPaymentCardDetails.class));

        });
//        madpter.setOnItemClickListener((view, obj, position) ->
//                {
//                    Toast.makeText(customerappPaymentModeActivity.this, "Selected : "+obj.getCustomer(), Toast.LENGTH_LONG).show();
//                }
//        );
    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_black_24);

        if (toolbar.getNavigationIcon() != null) {
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP);
        }

        toolbar.setTitle("Select Card");

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

//   public void onItemClick(View view) {
//        Toast.makeText(this, "list item clicked.", Toast.LENGTH_SHORT).show();
//    }
    ArrayList<AddCardResponse>  response;
    public void GetCardList(int UserId){
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(this).getrestadapter()
                .GetCardList(UserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<AddCardResponse>>() {
                    @Override
                    public void onCompleted() {
                        DisplayToast("Successfully Registered");
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
                    public void onNext(List<AddCardResponse> responselist) {
                        response = (ArrayList<AddCardResponse>)responselist;
                        clist.setText((responselist.size()) +" Linked card(s)");
                        madpter = new customerappCardListAdapter(response);
                        recyclerView.setAdapter(madpter);
                        madpter.setOnItemClickListener((view, obj, position) ->
                                {
                                    DisplayToast("Clicked Confirm");
                                   // Intent intent=new Intent(customerappAddPaymentModeActivity.this,customerappPaymentModeDetails.class);
                                    Intent intent = new Intent(customerappAddPaymentModeActivity.this, customerappGetaLyftConfirmActivity.class);
                                    intent.putExtra("source", ApplicationConstants.csource);
                                    intent.putExtra("destination", ApplicationConstants.cdestination);
                                    intent.putExtra("slat",ApplicationConstants.cslat);
                                    intent.putExtra("slog",ApplicationConstants.cslog);
                                    intent.putExtra("dlat",ApplicationConstants.cdlat);
                                    intent.putExtra("dlog",ApplicationConstants.cdlog);
                                    intent.putExtra("cardselected","Debit/Credit Card");
                                    startActivity(intent);
                                }
                        );
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
}
