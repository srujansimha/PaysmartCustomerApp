package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;

public class DialogFlightPaymentTransactionsFragment extends DialogFragment {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Phone = "phoneKey";
    public static final String ID = "idKey";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String fsourceid = "sourceid";
    public static final String fdestinationid= "fdestinationid";
    public static final String fsourcename = "fsourcename";
    public static final String fdestinationname= "fdestinationname";
    public static final String fjourneydate = "fjourneydate";
    public static final String fcabinename = "fcabinename";
    public static final String fadults = "fadults";
    public static final String fchild = "fchild";
    public static final String finfant = "finfant";
    public static final String fstatusno = "fstatusno";

    private View root_view;
    Toast toast;
    String uname,uemail;
    TextView username,useremail,amount,trasdate,trastype,statusid,transactionno,transactionstatus,transactionpurpose;
    ImageView card_logo,userphoto;
   // com.mikhaellopez.circularimageview.CircularImageView circularImageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.dialog_payment_transactions, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uname=prefs.getString(Name,null);
        uemail=prefs.getString(Email,null);

        amount=(TextView)root_view.findViewById(R.id.amount);
        trasdate=(TextView)root_view.findViewById(R.id.trasdate);
        trastype=(TextView)root_view.findViewById(R.id.trastype);
        //statusid=(TextView)root_view.findViewById(R.id.statusid);
        username=(TextView)root_view.findViewById(R.id.username);
        useremail=(TextView)root_view.findViewById(R.id.useremail);
        transactionno=(TextView)root_view.findViewById(R.id.transactionno);
        transactionstatus=(TextView)root_view.findViewById(R.id.transactionstatus);
        transactionpurpose=(TextView)root_view.findViewById(R.id.transactionpurpose);
        username.setText(uname);
        useremail.setText(uemail);
        trasdate.setText(ApplicationConstants.pdate+ " " +ApplicationConstants.ptime);
        trastype.setText( ApplicationConstants.pmode);

       // amount.setText( ApplicationConstants.amount);
//        trasdate.setText(ApplicationConstants.TransactionDate+ " " +ApplicationConstants.TransactionTime);
//        trastype.setText( ApplicationConstants.PaymentMode);
//        statusid.setText(ApplicationConstants.PaymentDesc);
//        if(ApplicationConstants.UserPhoto!=null){
//            byte[] decodedString= Base64.decode( ApplicationConstants.UserPhoto.substring( ApplicationConstants.UserPhoto.indexOf(",")+1), Base64.DEFAULT);
//            Bitmap image1 = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            userphoto.setImageBitmap(image1);
//            //userImageView.setImageBitmap(Utils.getCircularBitmapWithBorder(image1,0,0));
//        }
       // circularImageView
        //amount,trasdate,trastype
        ((FloatingActionButton) root_view.findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//               Intent intent= new Intent(getContext(),EwalletMainActivity.class);
//               startActivity(intent);
//               getActivity().finish();
                ApplicationConstants.source="";
                ApplicationConstants.destination="";

                SharedPreferences sharedPref = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(fadults,null);
                editor.putString(fchild,null);
                editor.putString(finfant,null);
                editor.putString(fsourcename,null);
                editor.putInt(fsourceid,0);
                editor.putString(fdestinationname,null);
                editor.putInt(fdestinationid,0);
                editor.putString(fcabinename,null);
                editor.putString(fjourneydate,null);
                editor.commit();
                startActivity(new Intent(getContext(), customerappFlightBookingSearchActivity.class));
                getActivity().finish();
                dismiss();
            }
        });
        //GetEwalletHistorybyId();
        return root_view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
         Dialog dialog = super.onCreateDialog(savedInstanceState);
         dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
//    public void GetEwalletHistorybyId(int Id, View root_view){
//        com.webingate.GameWinR_Admin.utils.DataPrepare.get(getContext()).getrestadapter()
//                .GetEwalletHistorybyId(Id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<List<WalletBalanceResponse>>() {
//
//                    @Override
//                    public void onCompleted() {
//                       // DisplayToast("Successfully Get Game list");
//                        //StopDialogue();
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        try {
//                            //Log.d("OnError ", e.getMessage());
//                            DisplayToast("Error");
//                            //StopDialogue();
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onNext(List<WalletBalanceResponse> responselist) {
//                        if(responselist.size()!=0){
//                        WalletBalanceResponse respons = responselist.get(0);
//                        amount.setText(String.valueOf(respons.getAmount()));
//                        transactionpurpose.setText(respons.getComments());
//                        transactionno.setText(respons.getTransactionId());
//                        trasdate.setText(respons.getDate()+ " " +respons.getTime());
//                        trastype.setText(respons.getTransactionMode());
//                        transactionstatus.setText(respons.getStatusId());
//                          if(respons.getAppuser()!=null){
//                              userphoto=(ImageView)root_view.findViewById(R.id.userphoto);
//                              username=(TextView)root_view.findViewById(R.id.username);
//                              useremail=(TextView)root_view.findViewById(R.id.useremail);
//                            username.setText(respons.getAppuser());
//                            useremail.setText(respons.getEmail());
//                             if(respons.getUserPhoto()==null){
//
//                                //userImageView.setImageBitmap(Utils.getCircularBitmapWithBorder(image1,0,0));
//                            }
//                            else{
//                                 byte[] decodedString= Base64.decode(respons.getUserPhoto().substring(respons.getUserPhoto().indexOf(",")+1), Base64.DEFAULT);
//                                 Bitmap image1 = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                                 userphoto.setImageBitmap(image1);
//                             }
//
//                          }
//                          else{
//                              LinearLayout tohide=(LinearLayout)root_view.findViewById(R.id.tohide);
//                              tohide.setVisibility(View.GONE);
//                          }
//
//                        }
//                    }
//                });
//
//
//    }
    public void DisplayToast(String text){
        if(toast!=null){
            toast.cancel();
            toast=null;
        }
        toast= Toast.makeText(getContext(),text, Toast.LENGTH_SHORT);
        toast.show();
    }
}