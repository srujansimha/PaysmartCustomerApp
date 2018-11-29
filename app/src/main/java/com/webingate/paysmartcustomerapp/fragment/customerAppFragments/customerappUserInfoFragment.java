package com.webingate.paysmartcustomerapp.fragment.customerAppFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.activity.customerapp.CropingMainActivity;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.utils.Utils;

import butterknife.BindView;



public class customerappUserInfoFragment extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Username = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    public static final String UserAccountNumber = "UserAccountNo";
    public static final String usertypeid = "usertypeid";
    public static final String gender = "GenderKey";
    public static final String Address = "AddressKey";

    ImageView profileImageView;
    @BindView(R.id.s_name)
    EditText name;
    @BindView(R.id.s_email)
    EditText email;
    @BindView(R.id.s_mobileno)
    EditText mbno;
    @BindView(R.id.s_address)
    EditText address;
    @BindView(R.id.s_city)
    EditText city;
    @BindView(R.id.s_postal)
    EditText postal;
    @BindView(R.id.s_state)
    EditText state;
    @BindView(R.id.Edituserphoto)
    ImageView ephoto;
    Toast toast;
    String email1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.customerapp_userdetails_infofragment,container,false);

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

                ephoto.setOnClickListener(View  -> {
                    Toast.makeText(getActivity(), "Testing", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(getContext(),CropingMainActivity.class);
                    startActivity(intent);
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

        profileImageView = view.findViewById(R.id.profileImageView);
        int id = R.drawable.baseline_image_black_24;
        Utils.setCornerRadiusImageToImageView(view.getContext(), profileImageView, id, 20, 2, R.color.md_white_1000);
        setname((EditText) view.findViewById(R.id.s_name));

        name = view.findViewById(R.id.s_name);
        email = view.findViewById(R.id.s_email);
        mbno = view.findViewById(R.id.s_mobileno);
        address = view.findViewById(R.id.s_address);
        city = view.findViewById(R.id.s_city);
        postal = view.findViewById(R.id.s_postal);
        state = view.findViewById(R.id.s_state);
        ephoto=view.findViewById(R.id.Edituserphoto);
//        name.setText(ApplicationConstants.username);
//        email.setText(ApplicationConstants.email);
//        mbno.setText(ApplicationConstants.mobileNo);
//        address.setText(ApplicationConstants.address);
//        city.setText(ApplicationConstants.gender);
    }

    public EditText getName() {
        return name;
    }

    public void setname(EditText name) {
        this.name = name;
    }


 //   public void onClick(View view) {
//        Intent intent =new Intent(view.getContext(),CropingMainActivity.class);
//        startActivity(intent);
//    }
}
