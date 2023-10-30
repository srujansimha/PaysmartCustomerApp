package com.webingate.paysmartcustomerapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerFlightResponce;
import com.webingate.paysmartcustomerapp.model.BookingType;
import com.webingate.paysmartcustomerapp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class customerappFlightBookingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CustomerFlightResponce> flightsList;

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, CustomerFlightResponce flight, int position);
        void onDownloadClick(View view, CustomerFlightResponce flight, int position);

        //void onDownloadClick(View view, customerappFlightBookingAdapter obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public customerappFlightBookingAdapter(List<CustomerFlightResponce> flightsList) {
        this.flightsList = flightsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View flightsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_completed_list_item, parent, false);
        return new FlightsViewHolder(flightsView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FlightsViewHolder) {
            CustomerFlightResponce flightsVO = flightsList.get(position);
            FlightsViewHolder flightsViewHolder = (FlightsViewHolder) holder;
            //Context context = flightsViewHolder.flightsImageView.getContext();

              flightsViewHolder.orderdate.setText(flightsVO.getTransactionDate());
              flightsViewHolder.transNoTextView1.setText(flightsVO.getTransGatewayId());
              flightsViewHolder.Statusedit.setText(flightsVO.getStatusName());
              flightsViewHolder.Statusedit.setTextColor(Color.GREEN);
              flightsViewHolder.textView156.setText(flightsVO.getAmount());
              flightsViewHolder.useredit.setText(flightsVO.getEmailid());
              flightsViewHolder.mobileedit.setText(flightsVO.getContactNo());
              flightsViewHolder.ticketnoedit.setText(flightsVO.getTicketNo());
//            flightsViewHolder.flightsTitleTextView.setText(flightsVO.getCountry());
//            flightsViewHolder.flightsDescTextView.setText(flightsVO.getDescription());
//            flightsViewHolder.flightsPeriodTextView.setText("Travel Period- " + flightsVO.getDuration());
//            flightsViewHolder.flightsPriceTextView.setText(flightsVO.getPrice());

            //int flightImageId = Utils.getDrawableInt(context, flightsVO.getImage());
            //Utils.setImageToImageView(context, flightsViewHolder.flightsImageView, flightImageId);

            if (itemClickListener != null) {
                ((FlightsViewHolder) holder).flightsCardView.setOnClickListener(view -> itemClickListener.onItemClick(view, flightsList.get(position), position));
                ((FlightsViewHolder) holder).download.setOnClickListener(view -> itemClickListener.onDownloadClick(view, flightsList.get(position), position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return flightsList.size();
    }

    public class FlightsViewHolder extends RecyclerView.ViewHolder {

        CardView flightsCardView;
        ImageView flightsImageView;
        TextView orderdate,useredit,mobileedit,Statusedit,ticketnoedit,transNoTextView1,textView156;
        Button download;


        public FlightsViewHolder(View itemView) {
            super(itemView);

            flightsCardView = itemView.findViewById(R.id.trans1CardView);
            orderdate=itemView.findViewById(R.id.textView160);
            useredit=itemView.findViewById(R.id.useredit);
            mobileedit=itemView.findViewById(R.id.mobileedit);
            Statusedit=itemView.findViewById(R.id.Statusedit);
            ticketnoedit=itemView.findViewById(R.id.ticketnoedit);
            download=itemView.findViewById(R.id.download);
            transNoTextView1=itemView.findViewById(R.id.transNoTextView1);
            textView156=itemView.findViewById(R.id.textView156);

        }
    }
}