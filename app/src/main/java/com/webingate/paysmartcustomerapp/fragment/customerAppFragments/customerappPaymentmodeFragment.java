package com.webingate.paysmartcustomerapp.fragment.customerAppFragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.activity.customerapp.customerappPaymentCardDetails;
import com.webingate.paysmartcustomerapp.activity.customerapp.customerappPaymentModeActivity;
import com.webingate.paysmartcustomerapp.adapter.customerappCardListAdapter;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.utils.Utils;

import butterknife.BindView;
import cropper.CropImage;
import cropper.CropImageView;


public class customerappPaymentmodeFragment extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Username = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    public static final String UserAccountNumber = "UserAccountNo";
    public static final String gender = "GenderKey";
    public static final String Address = "AddressKey";


    private LinearLayout addcards;
    RecyclerView recyclerView;
    customerappCardListAdapter madpter;
    Toast toast;
    @BindView(R.id.clistcount)
    TextView clist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.customerapp_paymentmode_fragment,container,false);
        addcards=(LinearLayout) view.findViewById(R.id.addcards);
        clist=(TextView)view.findViewById(R.id.clistcount);
        recyclerView = view.findViewById(R.id.placeList1RecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        initData();
//
        initUI(view);
//
//        initDataBindings();
//
         initActions();
//
        return view;
    }

    private void initActions() {


        addcards.setOnClickListener(v ->{
            Toast.makeText(getActivity(), "clicked one Add Card", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(),customerappPaymentCardDetails.class));

        });


    }

   //
//    private void initData() {
//        productsList = DirectoryHome9Repository.getfleetownerList();
//        categoryList = DirectoryHome9Repository.getCategoryList();
//        promotionsList = DirectoryHome9Repository.getPromotionsList();
//        popularList = DirectoryHome9Repository.getPopularList();
//        flightsList = DirectoryHome9Repository.getFlightsList();
//    }
//
    private void initUI(View view) {

    }





 //   public void onClick(View view) {
//        Intent intent =new Intent(view.getContext(),CropingMainActivity.class);
//        startActivity(intent);
//    }
}
