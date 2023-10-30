package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.ApplicationConstants;
import com.webingate.paysmartcustomerapp.customerapp.Deo.RegisterUserResponse;
import com.webingate.paysmartcustomerapp.fragment.customerAppFragments.customerappPaymentmodeFragment;
import com.webingate.paysmartcustomerapp.fragment.customerAppFragments.customerappUserInfoFragment;
import com.webingate.paysmartcustomerapp.utils.Utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import butterknife.BindView;
import cropper.CropImage;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class customerappUserDetailsActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Phone = "phoneKey";
    public static final String ID = "idKey";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String Password = "passwordkey";
    public static final String Mobileotp = "mobileotpkey";
    public static final String Emailotp = "emailotpkey";
    public static final String Dateofbirth = "dateofbirth";
    public static final String Gender = "gender";
    public static final String Paymenttype = "paymenttype";
    public static final String Profilepic = "profilepic";
    public static final String Passwordotp = "passwordotpkey";
    public static final String UserAccountNo = "UserAccountNokey";
    public static final String Isocode = "ISOCodekey";
    public static final String UserPhoto = "UserPhoto";
    Toast toast;

//ImageView profileImageView;
    private int position = 1;
    private int maxPosition = 1;
    private Button nextButton, prevButton;
    private TextView imageNoTextView;

    EditText email;
    EditText name;
    EditText address;
    EditText city;
    EditText mno;
    EditText postal;
    EditText state;
    ImageView profileImageView;
@BindView(R.id.Edituserphoto)
ImageView ephoto;
    customerappUserInfoFragment userInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerapp_userdetails_activity);
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        ApplicationConstants.id = prefs.getInt(ID,0);
        ApplicationConstants.username= prefs.getString(Name, null);
        ApplicationConstants.email= prefs.getString(Email, null);
        ApplicationConstants.mobileNo= prefs.getString(Phone, null);
        ApplicationConstants.userAccountNo = prefs.getString(UserAccountNo,null);

        initData();

        initUI();

        initActions();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region Init Functions
    private void initData() {

    }


    private void initUI() {
        initToolbar();
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        imageNoTextView = findViewById(R.id.imageNoTextView);

        findViewById(R.id.nextButton);


        updatePositionTextView();
        setupFragment(new customerappUserInfoFragment());
    }

    private void updatePositionTextView() {
        imageNoTextView.setText(position + " of " + maxPosition);
    }

    private void setupFragment(Fragment fragment) {
        try {
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentLayout, fragment)
                    .commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initActions() {

        nextButton.setOnClickListener(v -> {

            if (position < maxPosition) {
                position++;
                updatePositionTextView();
                if(position == 1) {
                    Toast.makeText(this, "Step 1.", Toast.LENGTH_SHORT).show();
                    userInfoFragment =      new customerappUserInfoFragment();

                    setupFragment(userInfoFragment);

                    ephoto=(ImageView) findViewById(R.id.Edituserphoto);

                }
//                if(position == 2)
//                {
//                    //EditText name = (EditText)findViewById(R.id.s_name);
//
//
//                    //Toast.makeText(this, "Step 2.", Toast.LENGTH_SHORT).show();
//                    //setupFragment(new customerappPaymentmodeFragment());
//                }
            } else {
                name = findViewById(R.id.s_name);
                email = findViewById(R.id.s_email);
                mno = findViewById(R.id.s_mobileno);
                address = findViewById(R.id.s_address);
                city = findViewById(R.id.s_city);
                postal = findViewById(R.id.s_postal);
                state = findViewById(R.id.s_state);
                profileImageView = findViewById(R.id.profileImageView);

                JsonObject object = new JsonObject();
                object.addProperty("flag", "U");
                object.addProperty("id",ApplicationConstants.id);
                object.addProperty("Username",name.getText().toString());
                object.addProperty("Firstname",name.getText().toString());
                object.addProperty("UserAccountNo",ApplicationConstants.userAccountNo);
                //object.addProperty("lastname","kumar");
                object.addProperty("Mobilenumber",mno.getText().toString());
                object.addProperty("Email",email.getText().toString());
                object.addProperty("UserPhoto","data:" + ApplicationConstants.pic_format + ";base64," +  ApplicationConstants.pic_data);
                object.addProperty("Address",address.getText().toString());
                object.addProperty("Gender","44");
                RegisterCustomer(object);
               // Toast.makeText(this, "No More Step.", Toast.LENGTH_SHORT).show();
            }
        });

        prevButton.setOnClickListener(v -> {

            if (position > 1) {
                position--;

                updatePositionTextView();
                if(position == 1) {
                    Toast.makeText(this, "Step 1.", Toast.LENGTH_SHORT).show();
                    userInfoFragment =      new customerappUserInfoFragment();

                    setupFragment(userInfoFragment);

                    ephoto=(ImageView) findViewById(R.id.Edituserphoto);
                }
//                if(position == 2)
//                {
//                    Toast.makeText(this, "Step 2.", Toast.LENGTH_SHORT).show();
//                    setupFragment(new customerappPaymentmodeFragment());
//                }
            } else {
                Toast.makeText(this, "No More Step.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.baseline_menu_black_24);

        if(toolbar.getNavigationIcon() != null) {
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.md_white_1000), PorterDuff.Mode.DST_ATOP);
        }

        toolbar.setTitle("User Details");

        try {
            toolbar.setTitleTextColor(getResources().getColor(R.color.md_white_1000));
        }catch (Exception e){
            Log.e("TEAMPS","Can't set color.");
        }

        try {
            setSupportActionBar(toolbar);
        }catch (Exception e) {
            Log.e("TEAMPS","Error in set support action bar.");
        }

        try {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }catch (Exception e) {
            Log.e("TEAMPS","Error in set display home as up enabled.");
        }

    }
    //endregion

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Bitmap bitmap=null;
            if (resultCode == -1) {
                try {
                    Uri uri = result.getUri();
                    Uri uri1=data.getData();
                    bitmap = BitmapFactory.decodeFile(uri.getPath());
                    ApplicationConstants.pic_format = "image/jpeg";

                    //getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    inputStream.close();
                    String encodedImage = Base64.encodeToString(stringBuilder.toString().getBytes(), Base64.DEFAULT);
//                    ApplicationConstants.pic_data = encodedImage;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    ApplicationConstants.pic_data = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG)
                            .show();

                    Toast.makeText(this, "Cropping successful, URI: " + result.getUri(), Toast.LENGTH_LONG)
                            .show();
//                ephoto=(ImageView) findViewById(R.id.Edituserphoto);
//                ephoto.setImageURI(result.getUri());
                    ephoto=(ImageView) findViewById(R.id.profileImageView);
                    //ephoto.setImageURI(result.getUri());
                    ephoto.setImageBitmap(bitmap);
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }


        }
    }
    public void RegisterCustomer(JsonObject jsonObject){

        //StartDialogue();
        com.webingate.paysmartcustomerapp.customerapp.Utils.DataPrepare.get(customerappUserDetailsActivity.this).getrestadapter()
                .RegisterUserUpdate(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RegisterUserResponse>>() {
                    @Override
                    public void onCompleted() {
                        //DisplayToast("Successfully onCompleted");
                        //StopDialogue();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Log.d("OnError ", e.getMessage());
                            //DisplayToast("Successfully onError");
                            //DisplayToast("Unable to Register");
                            //StopDialogue();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    @Override
                    public void onNext(List<RegisterUserResponse> responseList) {
//                        DisplayToast("Successfully onNext");
                        RegisterUserResponse response=responseList.get(0);
                        if(response.getCode()!=null){
                            DisplayToast(response.getDescription());
                        }
                        else{
                            SharedPreferences sharedPref = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(UserAccountNo, response.getUserAccountNo());
                            editor.putString(Phone,response.getMobilenumber());
                            editor.putInt(ID,response.getId());
                            editor.putString(Name,response.getUsername());
                            editor.putString(UserPhoto,response.getUserPhoto());
                            editor.putString(Email,response.getEmail());
                            editor.commit();
                            startActivity(new Intent(customerappUserDetailsActivity.this,customerDashboardActivity.class));
                            finish();
                            //ApplicationConstants.photo=response.getUserPhoto();
                        }
                    }
                });
    }
    public void DisplayToast(String text){
        if(toast!=null){
            toast.cancel();
            toast=null;

        }
        toast= Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
        toast.show();

    }
}
