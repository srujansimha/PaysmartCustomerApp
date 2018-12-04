package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.customerapp.Deo.AddCardResponse;
import com.webingate.paysmartcustomerapp.utils.Tools;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//import com.webingate.commontemplate.R;
//import com.webingate.commontemplate.utils.Tools;

public class customerappPaymentCardDetails extends AppCompatActivity {

    private TextView card_number;
    private TextView card_expire;
    private TextView card_cvv;
    private TextView card_name;

    private TextInputEditText et_card_number;
    private TextInputEditText et_expire;
    private TextInputEditText et_cvv;
    private TextInputEditText et_name;
    private Button Addcard;
    String cardno,cardname,update;
    Toast toast;
    String cno,cname,cexp,ccv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_card_details);

        card_number = (TextView) findViewById(R.id.card_number);
        card_expire = (TextView) findViewById(R.id.card_expire);
        card_cvv = (TextView) findViewById(R.id.card_cvv);
        card_name = (TextView) findViewById(R.id.card_name);
        Intent intent = getIntent();
         cardno=intent.getStringExtra("carno");
         cardname=intent.getStringExtra("Name");
        update=intent.getStringExtra("fl");
        et_card_number = (TextInputEditText) findViewById(R.id.et_card_number);
        et_expire = (TextInputEditText) findViewById(R.id.et_expire);
        et_cvv = (TextInputEditText) findViewById(R.id.et_cvv);
        et_name = (TextInputEditText) findViewById(R.id.et_name);
        Addcard=(Button) findViewById(R.id.Addcard);
        if(cardno!=null && card_name!=null){
            et_card_number.setText(cardno.toString().replaceAll("\\s+","") );
            et_name.setText(cardname);
            card_number.setText(cardno.toString().trim());
            card_name.setText(cardname);

        }
        et_card_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_number.setText("**** **** **** ****");
                } else {
                    String number = Tools.insertPeriodically(charSequence.toString().trim(), " ", 4);
                    card_number.setText(number);
                    cno=number;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_expire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_expire.setText("MM/YY");
                } else {
                    String exp = Tools.insertPeriodically(charSequence.toString().trim(), "/", 2);
                    card_expire.setText(exp);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_cvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_cvv.setText("***");
                } else {
                    card_cvv.setText(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_name.setText("Your Name");
                } else {
                    card_name.setText(charSequence.toString().trim());
                    cname=charSequence.toString().trim();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        initToolbar();
        initActions();
    }

    private void initActions() {
        Addcard.setOnClickListener(V->{
            Toast.makeText(this, "Click on Add Card.", Toast.LENGTH_LONG).show();
            JsonObject object = new JsonObject();

            object.addProperty("CardNumber",(cno!=null?cno:cardno));
            object.addProperty("CardModel","Master");
            object.addProperty("CardType","Master");
            object.addProperty("CardCategory","debit");
            object.addProperty("Status","Active");
            object.addProperty("UserId",ApplicationConstants.userid);
            object.addProperty("Customer",(cname!=null?cname:cardname));
            object.addProperty("EffectiveFrom","");
            object.addProperty("EffectiveTo","");
            object.addProperty("insupdflag",(update!=null?'U':'I'));
            addCard(object);
        });
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Card");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_1000);
    }
    public void addCard(JsonObject jsonObject){
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(this).getrestadapter()
                .SaveAddCard(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<AddCardResponse>>() {
                    @Override
                    public void onCompleted() {
                        DisplayToast("Successfully onCompleted");
                        //StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            DisplayToast("Successfully onError");
                            //DisplayToast("Unable to Register");
                            //StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    @Override
                    public void onNext(List<AddCardResponse> responseList) {
//                        DisplayToast("Successfully onNext");
                        if(responseList!=null){
                        AddCardResponse response=responseList.get(0);
                        DisplayToast("Customer Name"+response.getCustomer());
                        startActivity(new Intent(customerappPaymentCardDetails.this,customerappPaymentModeActivity.class));
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
