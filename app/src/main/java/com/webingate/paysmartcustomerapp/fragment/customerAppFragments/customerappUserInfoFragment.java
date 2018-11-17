package com.webingate.paysmartcustomerapp.fragment.customerAppFragments;

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
    @BindView(R.id.nameEditText)
    EditText name;
    @BindView(R.id.s_email)
    EditText email;
    @BindView(R.id.s_mobileno)
    EditText mbno;
    @BindView(R.id.addressEditText)
    EditText address;
    @BindView(R.id.cityEditText)
    EditText city;
    @BindView(R.id.countryEditText)
    EditText postal;
    @BindView(R.id.stateEditText)
    EditText state;
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
//        initActions();
//
        return view;
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
        int id = R.drawable.profile2;
        Utils.setCornerRadiusImageToImageView(view.getContext(), profileImageView, id, 20, 2, R.color.md_white_1000);
        setname((EditText) view.findViewById(R.id.nameEditText));

        name = view.findViewById(R.id.nameEditText);
        email = view.findViewById(R.id.s_email);
        mbno = view.findViewById(R.id.s_mobileno);
        address = view.findViewById(R.id.addressEditText);
        city = view.findViewById(R.id.cityEditText);
        postal = view.findViewById(R.id.countryEditText);
        state = view.findViewById(R.id.stateEditText);
        name.setText(ApplicationConstants.username);
        email.setText(ApplicationConstants.email);
        mbno.setText(ApplicationConstants.mobileNo);
        address.setText(ApplicationConstants.address);
        city.setText(ApplicationConstants.gender);
    }

    public EditText getName() {
        return name;
    }

    public void setname(EditText name) {
        this.name = name;
    }

}
