package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


//import com.example.test.R;
//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;
//
//import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.utils.Tools;

import butterknife.BindView;
import cropper.CropImage;
import cropper.CropImageView;

public class CropingMainActivity extends AppCompatActivity {

  Toolbar toolbar;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main1);

    initUI();
  }

  /** Start pick image activity with chooser. */
  public void onSelectImageClick(View view) {
    CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(this);
  }

  private void initUI(){
    initToolbar();
  }

  private void initToolbar() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("Click For Camera");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    Tools.setSystemBarColor(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    // handle result of CropImageActivity
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
      CropImage.ActivityResult result = CropImage.getActivityResult(data);
      if (resultCode == RESULT_OK) {
        ((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());
        Toast.makeText(
                        this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG)
                .show();
      } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
        Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
      }
    }
  }
}
