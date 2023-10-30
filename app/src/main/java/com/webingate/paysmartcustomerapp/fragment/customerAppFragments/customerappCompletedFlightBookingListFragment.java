package com.webingate.paysmartcustomerapp.fragment.customerAppFragments;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.activity.customerapp.customerappBookingDetails;
import com.webingate.paysmartcustomerapp.adapter.customerappFlightBookingAdapter;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerFlightResponce;
import com.webingate.paysmartcustomerapp.customerapp.Deo.GetCustomerBookingListResponse;
import com.webingate.paysmartcustomerapp.customerapp.Dialog.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.webingate.paysmartcustomerapp.adapter.customerappFlightBookingAdapter.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class customerappCompletedFlightBookingListFragment extends Fragment {
    Toast toast;
    private Context context;
    private String bookingno;
    @BindView(R.id.listView1)
    ListView listView2;
    String mb;
    RecyclerView recyclerView;
   // MyCustomAdapter dataAdapter = null;
    List<GetCustomerBookingListResponse> list;
    ArrayList <CustomerFlightResponce> completetripList;
    ProgressDialog dialog ;

    customerappFlightBookingAdapter adapter;
    private SwipeRefreshLayout swipe_refresh;
    public customerappCompletedFlightBookingListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.flight_fragement, container, false);

        initData();

        initUI(view);

        //  initDataBindings();

        initActions();

        return view;
    }


    private void initData() {
        mb=ApplicationConstants.userAccountNo;//com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.ApplicationConstants.mobileNo;
        int tt=2;
        GetCustomercompleteTrips();
    }

    private void initUI(View view) {
        swipe_refresh = view.findViewById(R.id.swipe_refresh_layout);
        adapter=new customerappFlightBookingAdapter(null);
        recyclerView = view.findViewById(R.id.RecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initDataBinding() {
    }

    private void initActions() {
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //GamesList(Status);
                pullAndRefresh();
            }
        });
    }
    private void pullAndRefresh() {
        swipeProgress(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GetCustomercompleteTrips();
                swipeProgress(false);
            }
        }, 4000);
    }

    private void swipeProgress(final boolean show) {
        if (!show) {
            swipe_refresh.setRefreshing(show);
            return;
        }
        swipe_refresh.post(new Runnable() {
            @Override
            public void run() {
                swipe_refresh.setRefreshing(show);
            }
        });
    }

//    private void displayListView1() {
//
//
//        //create an ArrayAdaptar from the String Array
//
//        dataAdapter = new MyCustomAdapter(getActivity(),R.layout.flight_tripscustom, completetripList);
//
//        // Assign adapter to ListView
//        dataAdapter.notifyDataSetChanged();
//        ListView listView = (ListView) getActivity().findViewById(R.id.listView1);
//        listView.setAdapter(dataAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://124.123.41.203:8088/UI/EmailTemplates/BoardingPass.pdf"));
//                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "BoardingPass.pdf");
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); // to notify when download is complete
//                request.allowScanningByMediaScanner();// if you want to be available from media players
//                DownloadManager manager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
//                manager.enqueue(request);
//                //dataAdapter.getPosition(GetdriverTripsResponse tripModel);
//
//               // GetdriverTripsResponse tripModel = (GetdriverTripsResponse)list.get(position);
//                //bookingno =  dataAdapter.getItem(position).getBNo();
//                //bookingno = tripModel.getId();
//                //JsonObject object = new JsonObject();
//                //object.addProperty("BNo", bookingno);
//               /// GetDriverTripsDetails(mb,bookingno);
//                //RideDetails(object);
//
//               /* TripRequest tripRequest = new TripRequest();
//                tripRequest.execute();*/
//            }
//
//        });
//
//    }

//    private class MyCustomAdapter extends ArrayAdapter<CustomerFlightResponce> {
//
//        private ArrayList<CustomerFlightResponce> logsselected;
//
//        public MyCustomAdapter(Context context, int textViewResourceId,
//                               ArrayList<CustomerFlightResponce> logsSelected) {
//            super(context, textViewResourceId, logsSelected);
//            this.logsselected = completetripList;
//            this.logsselected.addAll(logsSelected);
//        }
//
//        private class ViewHolder {
//            TextView time, booking_no, source, destination, price,download;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            ViewHolder holder = null;
//            Log.v("ConvertView", String.valueOf(position));
//
//            if (convertView == null) {
//                //sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
//                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(
//                        Context.LAYOUT_INFLATER_SERVICE);
//                convertView = vi.inflate(R.layout.layout_tripscustom, null);
//
//                holder = new ViewHolder();
//                holder.download=(TextView)convertView.findViewById(R.id.download);
//                holder.time = (TextView) convertView.findViewById(R.id.time);
//                holder.price = (TextView) convertView.findViewById(R.id.price);
//                holder.booking_no = (TextView) convertView.findViewById(R.id.booking_no);
//                holder.source = (TextView) convertView.findViewById(R.id.source);
//                holder.destination = (TextView) convertView.findViewById(R.id.destination);
//                convertView.setTag(holder);
//
//	   /* holder.code.setOnClickListener( new View.OnClickListener() {
//		     public void onClick(View v) {
//		      TextView tb = (TextView) v ;
//		      LogModel log =new LogModel();
//		    		  log= (LogModel) tb.getTag();
//		      Toast.makeText(getApplicationContext(),"Clicked On Log" + log.getName()+" "+log.getDate()+"/"+log.getMonth()+"/"+log.getYear(),
//		      Toast.LENGTH_LONG).show();
//		      //log.setSelected(cb.isChecked());
//		     }
//		    }); */
//            } else {
//                holder = (MyCustomAdapter.ViewHolder) convertView.getTag();
//            }
//
//            CustomerFlightResponce logs = logsselected.get(position);
//            // holder.code.setText(" (" +  logs.getCode() + ")");
//            holder.time.setText(logs.getTransactionDate());
//            //holder.price.setText("$ " + logs.getAmount());
//            //holder.booking_no.setText("Booking No " + logs.getId());
//            //holder.source.setText(logs.getSrc());
//
//            //holder.destination.setText(logs.getDest());
//            return convertView;
//
//        }
//
//    }

    public void GetCustomercompleteTrips( ){

        //StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(getActivity()).getrestadapter()
                .getFBTransactionMaster()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CustomerFlightResponce>>() {
                    @Override
                    public void onCompleted() {
                        //DisplayToast("Successfully get Drivers Trip list");
                        //   StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Log.d("OnError ", e.getMessage());
                           // DisplayToast(e.getMessage());
//                            StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<CustomerFlightResponce> trips) {
                        completetripList= (ArrayList<CustomerFlightResponce>) trips;
                        adapter = new customerappFlightBookingAdapter(completetripList);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new OnItemClickListener() {


                            @Override
                            public void onItemClick(View view, CustomerFlightResponce flight, int position) {
                                DisplayToast("Clicked On Item");

                            }

                            @Override
                            public void onDownloadClick(View view, CustomerFlightResponce flight, int position) {

                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://124.123.41.203:8088/UI/EmailTemplates/BoardingPass.pdf"));
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "BoardingPass.pdf");
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); // to notify when download is complete
                                request.allowScanningByMediaScanner();// if you want to be available from media players
                                DownloadManager manager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                                manager.enqueue(request);
                                DisplayToast("E Ticket is downloaded");

                            }


                        });
                    }
                });
    }
    public void DisplayToast(String text){
        if(toast!=null){
            toast.cancel();
            toast=null;

        }
        toast=Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT);
        toast.show();

    }
    public void StartDialogue(){

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    public void StopDialogue(){
        if(dialog.isShowing()){
            dialog.cancel();
        }

    }


    public void GetDriverTripsDetails(String driverNo,String bno){

        //StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(getActivity()).getrestadapter()
                .Getdrivertripsbookingno(driverNo,bno)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<GetCustomerBookingListResponse>>() {
                    @Override
                    public void onCompleted() {
                        DisplayToast("Successfully get Drivers Trip list");
                        //   StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Log.d("OnError ", e.getMessage());
                           // DisplayToast("Unable to Register");
//                            StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<GetCustomerBookingListResponse> trips) {
                        GetCustomerBookingListResponse response=trips.get(0);
                        Intent intent = new Intent(getActivity(), customerappBookingDetails.class);
                        intent.putExtra("driverdetails",response.getDriverId());
                        intent.putExtra("comment",response.getComments());
                        intent.putExtra("startTime",response.getBookedDate());
                        intent.putExtra("endTime",response.getDepartureTime());
                        intent.putExtra("source",response.getSrc());
                        intent.putExtra("destination",response.getDest());
                        intent.putExtra("amount",response.getAmount());
                        startActivity(intent);



                    }
                });
    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.customerapp_closedtickets_tabfragment, container, false);
//    }

}
