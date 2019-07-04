package com.webingate.paysmartcustomerapp.fragment.customerAppFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.google.gson.JsonObject;
import com.webingate.paysmartcustomerapp.R;

import com.webingate.paysmartcustomerapp.activity.customerapp.login_activity;
import com.webingate.paysmartcustomerapp.adapter.FlightSchedulelist;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.customerapp.BusLayout;
import com.webingate.paysmartcustomerapp.customerapp.Deo.ConfigData;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerFlightResponce;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Flightslist extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String fsourceid = "sourceid";
    public static final String fdestinationid= "fdestinationid";
    public static final String fsourcename = "fsourcename";
    public static final String fdestinationname= "fdestinationname";
    public static final String fjourneydate = "fjourneydate";
    public static final String fcabinename = "fcabinename";
    public static final String fadults = "fadults";
    public static final String fchild = "fchild";
    public static final String finfant = "finfant";
    // Declare Variables

    FlightSchedulelist adapter;
    ArrayList<CustomerFlightResponce> flist;


    private static final String ARG_SECTION_NUMBER = "section_number";

    @BindView(R.id.search)
    SearchView editsearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.layout)
    LinearLayout layout;
    Unbinder unbinder;
    //ImageView imageView6;


    public static Flightslist newInstance(int SectionNumber) {
        Flightslist home = new Flightslist();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, SectionNumber);
        home.setArguments(args);

        return home;
    }

    @SuppressLint("NewApi")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.customerapp_flight_list, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        ApplicationConstants.fsourceid=prefs.getInt(fsourceid,0);
        ApplicationConstants.fdestinationid=prefs.getInt(fdestinationid,0);
        // Generate sample data

        // Locate the ListView in listview_main.xml
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        adapter = new FlightSchedulelist(null);
        // get recycler view
        //recyclerView =(RecyclerView)v.findViewById(R.id.placeList1RecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        editsearch=(SearchView)v.findViewById(R.id.search);
        editsearch.setVisibility(View.GONE);

//        adapter = new TravelsAdapter(getContext(), ApplicationConstants.travelsArraylist);
//
//        // Binds the Adapter to the ListView
//        recyclerView.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        //editsearch.setVisibility(View.GONE);
       // .setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // TODO Auto-generated method stub
//                ApplicationConstants.travel = ApplicationConstants.travelsArraylist.get(position);
//                ApplicationConstants.FRAGMENT = ApplicationConstants.BUSLAYOUT;
//                goPage(ApplicationConstants.FRAGMENT);
//            }
//        });
        unbinder = ButterKnife.bind(this, v);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("srcid", ApplicationConstants.fsourceid);
        jsonObject.addProperty("destid", ApplicationConstants.fdestinationid);
        Getflightlist(jsonObject);
        return v;
    }

    private void goPage(int page) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (page) {

            case ApplicationConstants.BUSLAYOUT:
                fragmentClass = FlightLayout.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       /* fragmentTransaction.setCustomAnimations(
                R.anim.rotate_forward,
                R.anim.rotate_backward);*/

        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.commit();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    public void Getflightlist(JsonObject jsonObject){
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(getContext()).getrestadapter()
                .getflightschedule(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CustomerFlightResponce>>() {
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
                    public void onNext(List<CustomerFlightResponce> responselist) {
                        // Bundle bundle = new Bundle();
                        // bundle.putSerializable("details", (Serializable) responselist);

                        flist= (ArrayList <CustomerFlightResponce>) responselist;
                        adapter = new FlightSchedulelist(flist);
                        recyclerView.setAdapter(adapter);

                        adapter.setOnItemClickListener((view, obj, position) ->
                                {
                                    //ApplicationConstants.flightslist = responselist.get(position);
                                    ApplicationConstants.FRAGMENT = ApplicationConstants.BUSLAYOUT;
                                    goPage(ApplicationConstants.FRAGMENT);
                                }
                        );

                        //  ApplicationConstants.FRAGMENT = ApplicationConstants.TICKET_SOURCE_DESTINATION;
                        //  goPage(ApplicationConstants.FRAGMENT,bundle);
                    }
                    //}
                });

    }

}