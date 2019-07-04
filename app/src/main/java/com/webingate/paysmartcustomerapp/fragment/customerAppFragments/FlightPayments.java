package com.webingate.paysmartcustomerapp.fragment.customerAppFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.activity.customerapp.DialogFlightPaymentTransactionsFragment;
import com.webingate.paysmartcustomerapp.activity.customerapp.DialogPaymentTransactionsFragment;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.customerapp.BusLayout;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerPayResponse;
import com.webingate.paysmartcustomerapp.customerapp.Dialog.ProgressDialog;
import com.webingate.paysmartcustomerapp.customerapp.Mytickets;
import com.webingate.paysmartcustomerapp.customerapp.Stops;
import com.webingate.paysmartcustomerapp.customerapp.Ticket_Source_Destination_Date;
import com.webingate.paysmartcustomerapp.customerapp.Travels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@SuppressLint("NewApi")
public class FlightPayments extends Fragment {
    Button bookTicket, myTickets, eWallet;
    private static final String ARG_SECTION_NUMBER = "section_number";

    @BindView(R.id.btn_ewallet)
    AppCompatButton ewallet;
    @BindView(R.id.btn_preferences)
    AppCompatButton btnPreferences;
    @BindView(R.id.btn_feedback_enquiry)
    AppCompatButton btnFeedbackEnquiry;

    Unbinder unbinder;
    private String response;

    Toast toast;
    ProgressDialog dialog ;
    public static FlightPayments newInstance(int SectionNumber) {
        FlightPayments home = new FlightPayments();
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
        View v = inflater.inflate(R.layout.flightpaymentmethods, container, false);
        dialog =  new ProgressDialog.Builder(getActivity())
                .setTitle("Loading...")
                .setTitleColorRes(R.color.gray)
                .build();
        unbinder = ButterKnife.bind(this, v);

        ewallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationConstants.pmode="E-Wallet";
                JsonObject object = new JsonObject();
                object.addProperty("Transactionid", "1256");
                object.addProperty("Transaction_Number", "ts1258967");
                object.addProperty("Amount", "150");
                object.addProperty("Paymentmode", "1");
                object.addProperty("TransactionStatus", "1");
                object.addProperty("Gateway_transId", "wb123");
                object.addProperty("flag","I");
                Pay(object);
                /*PaymentRequest paymentRequest = new PaymentRequest();
                paymentRequest.execute();*/
            }
        });
        btnPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationConstants.pmode="Net Banking";
                JsonObject object = new JsonObject();
                object.addProperty("Transactionid", "1256");
                object.addProperty("Transaction_Number", "ts1258967");
                object.addProperty("Amount", "150");
                object.addProperty("Paymentmode", "1");
                object.addProperty("TransactionStatus", "1");
                object.addProperty("Gateway_transId", "wb123");
                object.addProperty("flag","I");
                Pay(object);
                /*PaymentRequest paymentRequest = new PaymentRequest();
                paymentRequest.execute();*/
            }
        });
        btnFeedbackEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationConstants.pmode="Credit & Debit";

                JsonObject object = new JsonObject();
                object.addProperty("Transactionid", "1256");
                object.addProperty("Transaction_Number", "ts1258967");
                object.addProperty("Amount", "150");
                object.addProperty("Paymentmode", "1");
                object.addProperty("TransactionStatus", "1");
                object.addProperty("Gateway_transId", "wb123");
                object.addProperty("flag","I");
                Pay(object);
                /*PaymentRequest paymentRequest = new PaymentRequest();
                paymentRequest.execute();*/
            }
        });
        return v;
    }

    private void goPage(int page) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (page) {
            case ApplicationConstants.HOME:
                fragmentClass = FlightPayments.class;
                break;
            case ApplicationConstants.TICKET_SOURCE_DESTINATION:
                fragmentClass = Ticket_Source_Destination_Date.class;
                break;
            case ApplicationConstants.TICKETS:
                fragmentClass = Mytickets.class;
                break;
            case ApplicationConstants.STOPS:
                fragmentClass = Stops.class;
                break;
            case ApplicationConstants.TRAVELS:
                fragmentClass = Travels.class;
                break;
            case ApplicationConstants.BUSLAYOUT:
                fragmentClass = BusLayout.class;
                break;
            case ApplicationConstants.EWALLET:
                fragmentClass = BusLayout.class;
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

    private void showDialogPaymentTransactions() {
        //ApplicationConstants.transactionsId=id;
        FragmentManager fragmentManager =getActivity().getSupportFragmentManager();

        DialogFlightPaymentTransactionsFragment dptf = new DialogFlightPaymentTransactionsFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        transaction.add(android.R.id.content, dptf).addToBackStack(null).commit();

    }



    public void Pay(JsonObject jsonObject){

        StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(getActivity()).getrestadapter()
                .Pay(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CustomerPayResponse>>() {
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
                    public void onNext(List<CustomerPayResponse> responselist) {
                        List<CustomerPayResponse> res=responselist;
                        ApplicationConstants.pdate=res.get(0).getPaymentDate();
                        ApplicationConstants.ptime=res.get(0).getPaymentTime();
                        showDialogPaymentTransactions();

                    }
                });
    }
    public void DisplayToast(String text){
        if(toast!=null){
            toast.cancel();
            toast=null;

        }
        toast=Toast.makeText(getContext(),text,Toast.LENGTH_SHORT);
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

   /* class PaymentRequest extends AsyncTask<String, Void, String[]> {
        ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setTitle("Processing....");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String[] doInBackground(String... params) {
            try {
                RegisterRequest();
                return new String[]{"Success"};
            } catch (Exception e) {
                return new String[]{"error"};
            }
        }

        @Override
        protected void onPostExecute(String... result) {
            try {
                if (response.matches("")) {
                    Toast.makeText(getContext(), "An error has occurred ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public void RegisterRequest() {
        BufferedReader reader = null;
        response = "";
        try {
            JSONObject object = new JSONObject();
            object.put("Transactionid", "1256");
            object.put("Transaction_Number", "ts1258967");
            object.put("Amount", "150");
            object.put("Paymentmode", "1");
            object.put("TransactionStatus", "1");
            object.put("Gateway_transId", "wb123");
            // JSONObject object = new JSONObject();
            URL url = new URL(getResources().getString(R.string.url_server) + getResources().getString(R.string.url_pay));
            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoInput(true);
            // conn.setDoOutput (true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Host", "android.schoolportal.gr");
            conn.connect();
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(object.toString());
            wr.flush();

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
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

    }*/
}
