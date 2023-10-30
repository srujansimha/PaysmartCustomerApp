package com.webingate.paysmartcustomerapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.Deo.AddCardResponse;

import java.util.ArrayList;

/**
 * Created by Panacea-Soft on 17/7/18.
 * Contact Email : teamps.is.cool@gmail.com
 * Website : http://www.panacea-soft.com
 */
public class customerappCardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<AddCardResponse> placeArrayList;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, AddCardResponse obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public customerappCardListAdapter(ArrayList<AddCardResponse> placeArrayList) {
        this.placeArrayList = placeArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customerapp_cardlist_item, parent, false);

        return new PlaceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof PlaceViewHolder) {
            AddCardResponse place = placeArrayList.get(position);
            PlaceViewHolder holder = (PlaceViewHolder) viewHolder;
            holder.Cardno.setText(place.getCardNumber());
            Context context = holder.placeHolderCardView.getContext();

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

    public class PlaceViewHolder extends RecyclerView.ViewHolder {

        public TextView Cardno;
        public CardView placeHolderCardView;

        public PlaceViewHolder(View view) {
            super(view);

            Cardno = view.findViewById(R.id.cardno);

            placeHolderCardView = view.findViewById(R.id.placeHolderCardView);
        }


    }

}
