package com.webingate.paysmartcustomerapp.fragment.customerAppFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.customerapp.Home;
import com.webingate.paysmartcustomerapp.customerapp.Payments;
import com.webingate.paysmartcustomerapp.customerapp.Travels;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class FlightPassengerDetails extends Fragment {
    //TextView name,time,subtitle,price,seatsselected;
    private static final String ARG_SECTION_NUMBER = "section_number";
    EditText input_mobile,input_email;
    TableRow tableRow[]=new TableRow[5];
    Toast toast;
    EditText names[]=new EditText[5];
    EditText age[]=new EditText[5];
    ToggleButton gender[]=new ToggleButton[5];
    ArrayList<String> namelist=new ArrayList();
    ArrayList<String> agelist=new ArrayList();
    ArrayList<String> genderlist=new ArrayList();

    Button payment;
    ArrayList selected=new ArrayList();
    public static FlightPassengerDetails newInstance(int SectionNumber) {
        FlightPassengerDetails home = new FlightPassengerDetails();
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
        View v = inflater.inflate(R.layout.flight_passengers_details, container, false);
        tableRow[0]= (TableRow) v.findViewById(R.id.tabrow1);
        tableRow[1]= (TableRow) v.findViewById(R.id.tabrow2);
        tableRow[2]= (TableRow) v.findViewById(R.id.tabrow3);
        tableRow[3]= (TableRow) v.findViewById(R.id.tabrow4);
        tableRow[4]= (TableRow) v.findViewById(R.id.tabrow5);
        names[0]= (EditText) v.findViewById(R.id.name1);
        names[1]= (EditText) v.findViewById(R.id.name2);
        names[2]= (EditText) v.findViewById(R.id.name3);
        names[3]= (EditText) v.findViewById(R.id.name4);
        names[4]= (EditText) v.findViewById(R.id.name5);
        age[0]= (EditText) v.findViewById(R.id.age1);
        age[1]= (EditText) v.findViewById(R.id.age2);
        age[2]= (EditText) v.findViewById(R.id.age3);
        age[3]= (EditText) v.findViewById(R.id.age4);
        age[4]= (EditText) v.findViewById(R.id.age5);
        gender[0]= (ToggleButton) v.findViewById(R.id.gender1);
        gender[1]= (ToggleButton) v.findViewById(R.id.gender2);
        gender[2]= (ToggleButton) v.findViewById(R.id.gender3);
        gender[3]= (ToggleButton) v.findViewById(R.id.gender4);
        gender[4]= (ToggleButton) v.findViewById(R.id.gender5);
        payment= (Button) v.findViewById(R.id.btn_payments);
        input_mobile=(EditText)v.findViewById(R.id.input_mobile);
        input_email=(EditText)v.findViewById(R.id.input_email);


        for(int i=5;i>ApplicationConstants.seatsSelected.size();i--){
            tableRow[i-1].setVisibility(View.GONE);
        }
        payment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(input_mobile.getText().toString()==null &&input_email.getText().toString()==null){
                    DisplayToast("Enter Mobile No and Email");
                }
                else{
                ApplicationConstants.PassengerMobileno=input_mobile.getText().toString();
                ApplicationConstants.PassengerEmailid=input_email.getText().toString();
                for(int i=0;i<ApplicationConstants.seatsSelected.size();i++){

                    if(names[i].getText().toString()!=null){
                    namelist.add(names[i].getText().toString());
                    ApplicationConstants.passengerlist=namelist;
                    }

                    if(age[i].getText().toString()!=null){
                        agelist.add(age[i].getText().toString());
                        ApplicationConstants.passengerage=agelist;
                    }
                    genderlist.add(gender[i].getText().toString());
                    ApplicationConstants.passengergender=genderlist;
                }
                ApplicationConstants.FRAGMENT=ApplicationConstants.PAYMENTS;
                goPage(ApplicationConstants.FRAGMENT);
                }
            }
        });
        return v;
    }

    private void goPage(int page) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        Class fragmentClass = null;
        switch(page){
            case ApplicationConstants.HOME:
                fragmentClass = Home.class;
                break;
            case ApplicationConstants.TRAVELS:
                fragmentClass = Travels.class;
                break;
            case ApplicationConstants.PAYMENTS:
                fragmentClass = FlightPayments.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       /* fragmentTransaction.setCustomAnimations(
                R.anim.rotate_forward,
                R.anim.rotate_backward);*/
        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.commit();
    }
    public void DisplayToast(String text){
        if(toast!=null){
            toast.cancel();
            toast=null;

        }
        toast= Toast.makeText(getContext(),text,Toast.LENGTH_SHORT);
        toast.show();

    }
}
