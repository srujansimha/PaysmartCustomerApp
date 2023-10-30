package com.webingate.paysmartcustomerapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.Deo.ConfigData;
import com.webingate.paysmartcustomerapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Panacea-Soft on 17/7/18.
 * Contact Email : teamps.is.cool@gmail.com
 * Website : http://www.panacea-soft.com
 */
public class customerappNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<ConfigData> placeArrayList;
    private OnItemClickListener itemClickListener;

    Context mcontext;
    Button approve;

    public interface OnItemClickListener {
        void onItemClick(View view, ConfigData obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public customerappNotificationAdapter(ArrayList<ConfigData> placeArrayList, Context mcontext) {
        this.placeArrayList = placeArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customerapp_notificationlist_item, parent, false);

        return new VehicleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof VehicleViewHolder) {

            ConfigData place = placeArrayList.get(position);

            VehicleViewHolder holder = (VehicleViewHolder) viewHolder;
            holder.placeNameTextView.setText("");
            Context context = holder.placeHolderCardView.getContext();
            int id = Utils.getDrawableInt(context, "home9_profile");
            Utils.setImageToImageView(context, holder.placeImageView, id);
            // int id = Utils.getDrawableInt(context, place.getPhoto());
//            if(place.getPhoto()!=null){
//                byte[] decodedString= Base64.decode(place.getPhoto().substring(place.getPhoto().indexOf(",")+1), Base64.DEFAULT);
//                Bitmap image1 = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                holder.placeImageView.setImageBitmap(image1);
//            }
//            else
//            {
//                int id = Utils.getDrawableInt(context, "home9_profile");
//                Utils.setImageToImageView(context, holder.placeImageView, id);
//            }
//            holder.typeTextView.setText(place.getVehicleGroup());
//            holder.cityTextView.setText(place.getVehicleType());
            holder.placeRatingBar.setRating(Float.parseFloat("4"));
            holder.totalRatingTextView.setText("4");
            holder.ratingCountTextView.setText("4");
//
//
//
//            holder.approve.setOnClickListener(v -> {
//                ApplicationConstants.vid = String.valueOf(place.getId());
//                ApplicationConstants.registrationNo=place.getRegistrationNo();
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("change",1);
//                jsonObject.addProperty("IsApproved",1);
//                jsonObject.addProperty("RegistrationNo",ApplicationConstants.registrationNo);
//                jsonObject.addProperty("Email",ApplicationConstants.email);
//                saveApprovals(jsonObject);
//
//            });

//            DriverViewHolder holder = (DriverViewHolder) viewHolder;
//
//            holder.placeNameTextView.setText(place.name);
//
//            Context context = holder.placeHolderCardView.getContext();
//
//            int id = Utils.getDrawableInt(context, place.imageName);
//            Utils.setImageToImageView(context, holder.placeImageView, id);
//
//            holder.typeTextView.setText(place.type);
//            holder.cityTextView.setText(place.city);
//            holder.placeRatingBar.setRating(Float.parseFloat(place.totalRating));
//            holder.totalRatingTextView.setText(place.totalRating);
//            holder.ratingCountTextView.setText(place.ratingCount);

//            if (place.discount != null && Integer.parseInt(place.discount) > 0) {
//                holder.promoCardView.setVisibility(View.VISIBLE);
//                String discount = place.discount + " %";
//                holder.promoAmtTextView.setText(discount);
//            } else {
//                holder.promoCardView.setVisibility(View.GONE);
//            }

            if ( itemClickListener != null ) {
                holder.placeHolderCardView.setOnClickListener((View v) -> itemClickListener.onItemClick(v, placeArrayList.get(position), position));
            }
        }
    }

    @Override
    public int getItemCount() {
        int a ;
        if(placeArrayList != null && !placeArrayList.isEmpty()) {
            a = placeArrayList.size();
        }
        else {
            a = 0;
        }
        return a;
    }

    public class VehicleViewHolder extends RecyclerView.ViewHolder {
        public ImageView placeImageView;
        public TextView placeNameTextView;
        public TextView typeTextView;
        public TextView cityTextView;
        public TextView totalRatingTextView;
        public TextView ratingCountTextView;
        public RatingBar placeRatingBar;
        //  public TextView promoAmtTextView;
        public CardView promoCardView;
        public CardView placeHolderCardView;
        public Button approve;


        public VehicleViewHolder(View view) {
            super(view);


            placeImageView = view.findViewById(R.id.placeImageView);
            placeNameTextView = view.findViewById(R.id.placeNameTextView);
            typeTextView = view.findViewById(R.id.distanceTextView);
            cityTextView = view.findViewById(R.id.cityTextView);
            totalRatingTextView = view.findViewById(R.id.totalRatingTextView);
            ratingCountTextView = view.findViewById(R.id.ratingCountTextView);
            placeRatingBar = view.findViewById(R.id.placeRatingBar);
            //   promoAmtTextView = view.findViewById(R.id.promoAmtTextView);
            promoCardView = view.findViewById(R.id.promoCardView);
            placeHolderCardView = view.findViewById(R.id.placeHolderCardView);
            approve = view.findViewById(R.id.approve);
            mcontext = view.getContext();
        }


    }
}
