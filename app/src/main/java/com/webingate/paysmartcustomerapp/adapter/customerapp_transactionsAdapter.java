package com.webingate.paysmartcustomerapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.customerapp.Deo.WalletBalanceResponse;
import com.webingate.paysmartcustomerapp.object.DirectoryHome9FlightsVO;
import com.webingate.paysmartcustomerapp.utils.Utils;

import java.util.List;

public class customerapp_transactionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<WalletBalanceResponse> translist;

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, WalletBalanceResponse flight, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public customerapp_transactionsAdapter(List<WalletBalanceResponse> flightsList) {
        this.translist = flightsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View flightsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customerapp_tras_item, parent, false);
        return new FlightsViewHolder(flightsView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FlightsViewHolder) {
            WalletBalanceResponse tlist = translist.get(position);
            FlightsViewHolder flightsViewHolder = (FlightsViewHolder) holder;
            Context context = ((FlightsViewHolder) holder).flightsCardView.getContext();
            flightsViewHolder.flightsDescTextView.setText(tlist.getComments());
            //flightsViewHolder.flightsDescTextView.setText(tlist.getAmount());
            if (itemClickListener != null) {
                ((FlightsViewHolder) holder).flightsCardView.setOnClickListener(view -> itemClickListener.onItemClick(view, translist.get(position), position));
            }
        }
    }

    @Override
    public int getItemCount() {
        int a ;
        if(translist != null && !translist.isEmpty()) {
            a = translist.size();
        }
        else {
            a = 0;
        }
        return a;
    }

    public class FlightsViewHolder extends RecyclerView.ViewHolder {

        CardView flightsCardView;
        ImageView flightsImageView;
        TextView flightsTitleTextView;
        TextView flightsDescTextView;
        TextView flightsPriceTextView;
        TextView flightsPeriodTextView;


        public FlightsViewHolder(View itemView) {
            super(itemView);
            flightsCardView = itemView.findViewById(R.id.placeHolderCardView);
            flightsImageView = itemView.findViewById(R.id.flightsImageView);
            flightsTitleTextView = itemView.findViewById(R.id.flightsTitleTextView);
            flightsDescTextView = itemView.findViewById(R.id.tdd);
            flightsPriceTextView = itemView.findViewById(R.id.flightsPriceTextView);

        }
    }
}
