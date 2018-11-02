package com.webingate.paysmartcustomerapp.adapter.uicollection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.object.GeneralList;

import java.util.ArrayList;

public class GeneralItemSpinnerAdapter extends BaseAdapter {

    //private int icons[];
    //private String[] fruits;
    ArrayList<GeneralList> itemslist;
    private LayoutInflater layoutInflater;

    public GeneralItemSpinnerAdapter(Context applicationContext, ArrayList<GeneralList> list) {
        this.itemslist = list;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return itemslist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.ui_customspinner_item, viewGroup, false);

        ImageView icon = view.findViewById(R.id.imageView);
        TextView names = view.findViewById(R.id.textView);

        String img = "@drawable/zimbabwe";
        //int res =  viewGroup.getResources().getIdentifier(this.itemslist.get(i).image, "drawable", viewGroup.getContext().getPackageName());
        int res =  viewGroup.getResources().getIdentifier(img, "drawable", viewGroup.getContext().getPackageName());

        icon.setImageResource(res);

       // icon.setImageResource(R.drawable.this.itemslist.get(i).image);
        //names.setText(this.itemslist.get(i).name);
        names.setText("zimbabwe");
        return view;
    }
}