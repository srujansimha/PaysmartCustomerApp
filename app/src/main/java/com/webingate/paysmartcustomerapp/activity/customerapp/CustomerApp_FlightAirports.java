package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.adapter.FlightlistAdapter;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerFlightResponce;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerGetstopsResponse;
import com.webingate.paysmartcustomerapp.customerapp.ListViewAdapter;
import com.webingate.paysmartcustomerapp.customerapp.Ticket_Source_Destination_Date;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CustomerApp_FlightAirports extends AppCompatActivity implements SearchView.OnQueryTextListener {
    // Declare Variables
    FlightlistAdapter adapter;
    //String[] stopsList;
    private static final String ARG_SECTION_NUMBER = "section_number";

    @BindView(R.id.search)
    SearchView editsearch;
    @BindView(R.id.listview)
    ListView list;
    @BindView(R.id.layout)
    LinearLayout layout;
    Unbinder unbinder;
    Spinner spinner;
    List<CustomerFlightResponce> stopslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerapp_flight_ariport);
        //ButterKnife.bind(this);

        initUI();

        initActions();
    }

    private void initActions() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                //   Toast.makeText(getContext(), stopsList[position], Toast.LENGTH_SHORT).show();
                if (ApplicationConstants.fstatus==1)  {
                    ApplicationConstants.fsource =stopslist.get(position).getName();
                    ApplicationConstants.fsourceid = stopslist.get(position).getId();

                    startActivity(new Intent(CustomerApp_FlightAirports.this,customerappFlightBookingSearchActivity.class));
                    // goPage(ApplicationConstants.FRAGMENT);
                } else if(ApplicationConstants.fstatus==2) {
                    ApplicationConstants.fdestination = stopslist.get(position).getName();
                    ApplicationConstants.fdestinationid = stopslist.get(position).getId();

                    startActivity(new Intent(CustomerApp_FlightAirports.this,customerappFlightBookingSearchActivity.class));
                    // goPage(ApplicationConstants.FRAGMENT);
                }
            }
        });
        editsearch.setOnQueryTextListener(this);
    }

    private void initUI() {
        list = (ListView)findViewById(R.id.listview);
        editsearch=findViewById(R.id.search);
        GetAirPortList();
        //list.setAdapter(adapter);

    }


//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.stops, container, false);
//
//      //  stopslist= (List<CustomerGetstopsResponse>) savedInstanceState.getSerializable("details");
//
//        unbinder = ButterKnife.bind(this, v);
//        super.onCreate(savedInstanceState);
//
//        // Generate sample data
//        //initDataBinding();
//
//        // Locate the ListView in listview_main.xml
//        list = (ListView) v.findViewById(R.id.listview);
//
//
//        // Pass results to ListViewAdapter Class
//        //adapter = new ListViewAdapter(getContext(),stopslist);
//
//        // Binds the Adapter to the ListView
//
//
//        // Locate the EditText in listview_main.xml
//        editsearch.setOnQueryTextListener(this);
//
//        return v;
//    }



    public void GetAirPortList(){
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(this).getrestadapter()
                .GetAirPortList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CustomerFlightResponce>>() {
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
                    public void onNext(List<CustomerFlightResponce> responselist) {
                       // Bundle bundle = new Bundle();
                       // bundle.putSerializable("details", (Serializable) responselist);
                        stopslist = responselist;
                        adapter = new FlightlistAdapter(CustomerApp_FlightAirports.this,stopslist);
                        list.setAdapter(adapter);

                        //  ApplicationConstants.FRAGMENT = ApplicationConstants.TICKET_SOURCE_DESTINATION;
                        //  goPage(ApplicationConstants.FRAGMENT,bundle);
                    }
                });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }




    public void onDestroyView() {
        super.onDestroy();
        unbinder.unbind();
    }
}