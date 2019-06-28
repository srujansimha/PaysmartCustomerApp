package com.webingate.paysmartcustomerapp.customerapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import com.google.gson.JsonObject;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.activity.customerapp.customerDashboardActivity;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerRateTheRideResponse;

import java.util.List;

public class RatingBarDialogue extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;
    private static final int GETFEEDBACK = 3;

    @BindView(R.id.ratingid)
    TextView ratingid;
    @BindView(R.id.dialog_ratingbar)
    RatingBar feedback;
    @BindView(R.id.input_comments)
    EditText comments;
    @BindView(R.id.submit)
    Button submit;
    Ratingfinished ratingfinished;
    public interface Ratingfinished{
        public void Rating(String rating,String comments);
    }
    public RatingBarDialogue(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        ratingfinished= (Ratingfinished) a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ratingbar);
        ButterKnife.bind(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (comments.getText().toString().matches("")) {
            Toast.makeText(getContext(), "Please Provide Your Comments", Toast.LENGTH_SHORT).show();
            //System.exit(0);
        } else {
            ApplicationConstants.rating = feedback.getRating() + "";
            ApplicationConstants.comments = comments.getText().toString();
            ratingfinished.Rating(comments.getText().toString(),feedback.getRating() + "");
          //  CurrentTrip.tripFlag = GETFEEDBACK;
            Rating();
            getOwnerActivity().startActivity(new Intent(getContext(), customerDashboardActivity.class));
            getOwnerActivity().finish();
            dismiss();

        }
    }

    public void Rating() {
        JsonObject object = new JsonObject();
        object.addProperty("CustomerPhoneNo", ApplicationConstants.mobileNo);
        object.addProperty("BNo", ApplicationConstants.bookingNo);
        object.addProperty("Rating", ApplicationConstants.rating );
        object.addProperty("RatedBy", 0);
        object.addProperty("Comments", ApplicationConstants.comments);
        RateTheRide1(object);
    }
    public void RateTheRide1(JsonObject jsonObject){

       // StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(getContext()).getrestadapter()
                .RateTheRide(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CustomerRateTheRideResponse>>() {
                    @Override
                    public void onCompleted() {
                        //  DisplayToast("Successfully Registered");
                        //StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Log.d("OnError ", e.getMessage());
                            //DisplayToast("Error");
                            //StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<CustomerRateTheRideResponse> responselist) {
                        //   CustomerRateTheRideResponse response=responselist.get(0);
                        //finish();
                    }
                });
    }
}