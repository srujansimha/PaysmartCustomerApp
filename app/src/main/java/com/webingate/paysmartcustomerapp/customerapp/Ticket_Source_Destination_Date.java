package com.webingate.paysmartcustomerapp.customerapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerGetstopsResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.GetAvailableServicesResponse;
import com.webingate.paysmartcustomerapp.customerapp.Deo.ValidateCredentialsResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.object.DirectoryHome9ProductsVO;
import com.webingate.paysmartcustomerapp.repository.directory.DirectoryHome9Repository;

import static com.webingate.paysmartcustomerapp.data.DataGenerator.getAvailableServices;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

@SuppressLint("NewApi")
public class Ticket_Source_Destination_Date extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    Calendar dateTime = Calendar.getInstance();
    @BindView(R.id.source)
    TextView source;
    @BindView(R.id.destination)
    TextView destination;
    @BindView(R.id.returnjourneyDate)
    TextView rdate;
    @BindView(R.id.journeyDate)
    TextView date;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.scrollView1)
    ScrollView scrollView1;
    Toast toast;
    ProgressDialog dialog ;
    Unbinder unbinder;
    private String response;
    List<CustomerGetstopsResponse> stops;

    public static Ticket_Source_Destination_Date newInstance(int SectionNumber) {
        Ticket_Source_Destination_Date ticket_source_destination_date = new Ticket_Source_Destination_Date();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, SectionNumber);
        ticket_source_destination_date.setArguments(args);
        return ticket_source_destination_date;
    }

    @SuppressLint("NewApi")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tkt_src_dst_date, container, false);
        unbinder = ButterKnife.bind(this, v);
       /* dialog =  new ProgressDialog.Builder(getActivity())
                .setTitle("Loading...")
                .setTitleColorRes(R.color.gray)
                .build();*/
      //  stops= (List<CustomerGetstopsResponse>) savedInstanceState.getSerializable("details");
        source = (TextView) v.findViewById(R.id.source);
        destination = (TextView) v.findViewById(R.id.destination);
        date = (TextView) v.findViewById(R.id.journeyDate);
        rdate=(TextView)v.findViewById(R.id.returnjourneyDate);
        next = (Button) v.findViewById(R.id.next);
        if (!(ApplicationConstants.source==null)) {
            source.setText("  " + ApplicationConstants.source);
        }
        if (!(ApplicationConstants.destination==null)) {
            destination.setText("  " + ApplicationConstants.destination);
        }
        View.OnClickListener citySelection = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.source:
                        ApplicationConstants.source = "";
                        ApplicationConstants.FRAGMENT = ApplicationConstants.STOPS;
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("details", (Serializable) stops);
                        goPage(ApplicationConstants.FRAGMENT,bundle);
                        break;
                    case R.id.destination:
                        if (ApplicationConstants.source.matches("")) {
                            Toast.makeText(getContext(), "Please Selcet Source", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        ApplicationConstants.destination = "";
                        ApplicationConstants.FRAGMENT = ApplicationConstants.STOPS;
                        bundle=new Bundle();
                        bundle.putSerializable("details", (Serializable) stops);
                        goPage(ApplicationConstants.FRAGMENT,bundle);
                        break;
                    case R.id.journeyDate:
                       // DialogFragment newFragment = new DatePickerFragment();
                        // DatePickerDialog newFragment = new DatePickerDialog(getContext(),R.style.AppTheme,this,2017,5,9);
                        //newFragment.show(this.getFragmentManager(), "datePicker");

                        new DatePickerDialog(v.getContext(), datePickerDialog, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
                         ApplicationConstants.passengerdate = date.getText().toString();
                        break;
                    case R.id.returnjourneyDate:
                        // DialogFragment newFragment = new DatePickerFragment();
                        // DatePickerDialog newFragment = new DatePickerDialog(getContext(),R.style.AppTheme,this,2017,5,9);
                        //newFragment.show(this.getFragmentManager(), "datePicker");

                        new DatePickerDialog(v.getContext(), datePickerDialog, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();

                        break;
                    case R.id.next:
                        if (ApplicationConstants.source.matches("") || ApplicationConstants.destination.matches("") || ApplicationConstants.date.matches("")) {
                            Toast.makeText(getContext(), "Please Selcet ", Toast.LENGTH_SHORT).show();
                        } else {
//                            TravelsRequest travelsRequest = new TravelsRequest();
//                            travelsRequest.execute();

                            // dummy data set up
                            //JSONArray jsonObj = new JSONArray();
                            ApplicationConstants.travelsArraylist.clear();
                            for (int i = 0; i < 5; i++) {
                                try{
                               // JSONObject c = jsonObj.getJSONObject(i);
                                //List<DirectoryHome9ProductsVO> productsList;
                                ApplicationConstants.travelsArraylist = getAvailableServices();
                                }
                                catch (Exception e){

                                }
                            }
                            ApplicationConstants.FRAGMENT = ApplicationConstants.TRAVELS;
                            goPage(ApplicationConstants.FRAGMENT);
                        }
                        break;
                }
            }
        };

        source.setOnClickListener(citySelection);
        destination.setOnClickListener(citySelection);
        date.setOnClickListener(citySelection);
        next.setOnClickListener(citySelection);

        return v;
    }

    private void goPage(int page,Bundle bundle) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (page) {
            case ApplicationConstants.STOPS:
                fragmentClass = Stops.class;
                break;
            case ApplicationConstants.TRAVELS:
                fragmentClass = Travels.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       /* fragmentTransaction.setCustomAnimations(
                R.anim.rotate_forward,
                R.anim.rotate_backward);*/
        fragmentTransaction.replace(R.id.srcdestdatetimeFrame, fragment);
        fragmentTransaction.commit();
    }
    private void goPage(int page) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (page) {
            case ApplicationConstants.STOPS:
                fragmentClass = Stops.class;
                break;
            case ApplicationConstants.TRAVELS:
                fragmentClass = Travels.class;
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
        fragmentTransaction.replace(R.id.srcdestdatetimeFrame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    DatePickerDialog.OnDateSetListener datePickerDialog = (view, year, monthOfYear, dayOfMonth) -> {
        dateTime.set(Calendar.YEAR, year);
        dateTime.set(Calendar.MONTH, monthOfYear);
        dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDate();
    };

    private void openDatePicker(){
        new DatePickerDialog(this.getContext(), datePickerDialog, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String shortTimeStr = sdf.format(dateTime.getTime());
        ApplicationConstants.date = shortTimeStr;
        date.setText( ApplicationConstants.date);
    }

//    @SuppressLint("ValidFragment")
//    public class DatePickerFragment extends DialogFragment implements
//            DatePickerDialog.OnDateSetListener {
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceSateate) {
//
//            final Calendar c = Calendar.getInstance();
//            int year = c.get(Calendar.YEAR);
//            int month = c.get(Calendar.MONTH);
//            int day = c.get(Calendar.DAY_OF_MONTH);
//
//            return new DatePickerDialog(getActivity(), R.style.Dialog_Theme, this, year, month, day);
//        }
//
//        public void onDateSet(DatePicker view, final int year, final int month, final int day) {
//            // Do something with the date chosen
//            Handler mainHandler = new Handler(getContext().getMainLooper());
//
//            Runnable myRunnable = new Runnable() {
//                @Override
//                public void run() {
//                    date.setText("  " + day + "/" + month + "/" + year);
//                    ApplicationConstants.date = day + "/" + month + "/" + year;
//                    // Toast.makeText(getContext(),day+"/"+month+"/"+year,Toast.LENGTH_SHORT).show();
//                } // This is your code
//            };
//            mainHandler.post(myRunnable);
//
//
//        }
//    }


    /*public void GetAvailableServices(String srcId,String destId){

        StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(getActivity()).getrestadapter()
                .GetAvailableServices(srcId, destId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<GetAvailableServicesResponse>>() {
                    @Override
                    public void onCompleted() {
                        //  DisplayToast("Successfully Registered");
                        StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Log.d("OnError ", e.getMessage());
                            DisplayToast("Error");
                            StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<GetAvailableServicesResponse> responselist) {



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

    }*/

    class TravelsRequest extends AsyncTask<String, Void, String[]> {
        ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setTitle("Getting Travels list....");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String[] doInBackground(String... params) {
            try {
                Travelsrequest();
                return new String[]{"Success"};
            } catch (Exception e) {
                return new String[]{"error"};
            }
        }

        @Override
        protected void onPostExecute(String... result) {
            try {
                if (response.matches("")) {
                    Toast.makeText(getContext(), "No Travels Found ", Toast.LENGTH_LONG).show();
                } else {
                    // Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    JSONArray jsonObj = new JSONArray(response);
                    if (jsonObj.length() < 1) {
                        Toast.makeText(getContext(), "No Travels Found ", Toast.LENGTH_LONG).show();

                    } else {
                        // Getting JSON Array node
                        // JSONArray values = jsonObj.getJSONArray("");

                        // looping through All Contacts
                        ApplicationConstants.travelsArraylist.clear();
                        for (int i = 0; i < jsonObj.length(); i++) {
                            JSONObject c = jsonObj.getJSONObject(i);
                            List<DirectoryHome9ProductsVO> productsList;
                            //DirectoryHome9Repository travelModel=new DirectoryHome9Repository();
                            ApplicationConstants.travelsArraylist = getAvailableServices();

                            //ApplicationConstants.travelsArraylist.add(travelModel);
                            //ApplicationConstants.travelsArraylist.add(productsList);
                            // Toast.makeText(getContext(), "Stop  " + c.getString("Name"), Toast.LENGTH_LONG).show();
                            //  startActivity(new Intent(LoginActivity.this, HomeActivity1.class));
                            //  finish();

                        }
                        ApplicationConstants.FRAGMENT = ApplicationConstants.TRAVELS;
                        goPage(ApplicationConstants.FRAGMENT);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public void Travelsrequest() {
        BufferedReader reader = null;
        response = "";
        try {
            // JSONObject object = new JSONObject();
            URL url = new URL(getResources().getString(R.string.url_server) + getResources().getString(R.string.url_get_available_services) + ApplicationConstants.sourceid + "&destId=" + ApplicationConstants.destinationid);
            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoInput(true);
            // conn.setDoOutput (true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Host", "android.schoolportal.gr");
            conn.connect();
            // OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            // wr.write(object.toString());
            // wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }
            response = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // response = response + e.getMessage();
            //e.printStackTrace();
        } catch (IOException e) {
            //  response = response + e.getMessage();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

    }
}
