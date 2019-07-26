package com.webingate.paysmartcustomerapp.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerFlightResponce;
import com.webingate.paysmartcustomerapp.customerapp.Deo.GetCustomerBookingListResponse;

import java.util.List;

public class customerappGetalyftBookingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GetCustomerBookingListResponse> flightsList;

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, GetCustomerBookingListResponse flight, int position);
        //void onDownloadClick(View view, GetCustomerBookingListResponse flight, int position);

        //void onDownloadClick(View view, customerappFlightBookingAdapter obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public customerappGetalyftBookingAdapter(List<GetCustomerBookingListResponse> flightsList) {
        this.flightsList = flightsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View flightsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookinggetalyft_completed_list_item, parent, false);
        return new FlightsViewHolder(flightsView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FlightsViewHolder) {
            GetCustomerBookingListResponse flightsVO = flightsList.get(position);
            FlightsViewHolder flightsViewHolder = (FlightsViewHolder) holder;
            //Context context = holder.flightsImageView.getContext();

            flightsViewHolder.time.setText(flightsVO.getBookedDate());
            flightsViewHolder.price.setText("$ " + flightsVO.getAmount());
            flightsViewHolder.booking_no.setText("Booking No " + flightsVO.getId());
            flightsViewHolder.source.setText(flightsVO.getSrc());

            flightsViewHolder.destination.setText(flightsVO.getDest());


//            flightsViewHolder.flightsTitleTextView.setText(flightsVO.getCountry());
//            flightsViewHolder.flightsDescTextView.setText(flightsVO.getDescription());
//            flightsViewHolder.flightsPeriodTextView.setText("Travel Period- " + flightsVO.getDuration());
//            flightsViewHolder.flightsPriceTextView.setText(flightsVO.getPrice());

            //int flightImageId = Utils.getDrawableInt(context, flightsVO.getImage());
            //Utils.setImageToImageView(context, flightsViewHolder.flightsImageView, flightImageId);

            if (itemClickListener != null) {
                ((FlightsViewHolder) holder).flightsCardView.setOnClickListener(view -> itemClickListener.onItemClick(view, flightsList.get(position), position));
                //((FlightsViewHolder) holder).download.setOnClickListener(view -> itemClickListener.onDownloadClick(view, flightsList.get(position), position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return flightsList.size();
    }

    public class FlightsViewHolder extends RecyclerView.ViewHolder {

        CardView flightsCardView;

        TextView time, booking_no, source, destination, price;



        public FlightsViewHolder(View itemView) {
            super(itemView);

            flightsCardView = itemView.findViewById(R.id.trans1CardView);
            time=itemView.findViewById(R.id.time);
            booking_no=itemView.findViewById(R.id.booking_no);
            source=itemView.findViewById(R.id.source);
            destination=itemView.findViewById(R.id.destination);
            price=itemView.findViewById(R.id.price);


        }
    }
}