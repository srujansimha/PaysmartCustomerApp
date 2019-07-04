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
import com.webingate.paysmartcustomerapp.customerapp.Deo.CustomerFlightResponce;

import java.util.ArrayList;

/**
 * Created by Panacea-Soft on 17/7/18.
 * Contact Email : teamps.is.cool@gmail.com
 * Website : http://www.panacea-soft.com
 */
public class FlightSchedulelist extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    //private List<ShopItem> shopItemList;
    private OnItemClickListener itemClickListener;
    private ArrayList<CustomerFlightResponce> placeArrayList;

    public interface OnItemClickListener {
        void onItemClick(View view, CustomerFlightResponce obj, int position);
        //void onAddToCartClick(View view, ShopItem obj, int position);
        //void onMenuClick(View view, ShopItem obj, int position);
        //void onItemClick(View view, Place obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public FlightSchedulelist(ArrayList<CustomerFlightResponce> placeArrayList) {
        this.placeArrayList = placeArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_list_items, parent, false);

        return new PlaceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof PlaceViewHolder) {

            CustomerFlightResponce gamelist = placeArrayList.get(position);

            PlaceViewHolder holder = (PlaceViewHolder) viewHolder;
//            holder.gametitlename.setText(gamelist.getName()+"-"+gamelist.getGameNo());
//            holder.gametype1.setText(gamelist.getGameTypeName());
//            holder.gametimelist.setText(gamelist.getDate()+" at "+gamelist.getTime());
//            holder.maptypename1.setText(gamelist.getGameMapName());
//            holder.versiontypename1.setText(gamelist.getGameVersion());

            Context context = holder.holderCardView.getContext();
             holder.imageView6.setImageResource(R.drawable.kingfishers);
             holder.src.setText(gamelist.getSourceterminal());
             holder.dest.setText(gamelist.getDestinationterminal());
             holder.amount.setText(gamelist.getAmount());
//            if(gamelist.getGameImage().matches("")){
//                //holder.gameimage.setImageResource(R.drawable.game1);
//            }
//            else{
//                byte[] decodedString= Base64.decode(gamelist.getGameImage().substring(gamelist.getGameImage().indexOf(",")+1), Base64.DEFAULT);
//                Bitmap image1 = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                holder.gameimage.setImageBitmap(image1);
//            }

//            else{
//                int id = Utils.getDrawableInt(context, shopItem.imageName);
//                Utils.setImageToImageView(context, holder.gameimage, id);            }


            if ( itemClickListener != null ) {
                holder.holderCardView.setOnClickListener((View v) -> itemClickListener.onItemClick(v, placeArrayList.get(position), position));
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
        ImageView imageView6;
        TextView src,dest,amount;
        CardView holderCardView;


        PlaceViewHolder(View view) {
            super(view);
             holderCardView=view.findViewById(R.id.placeHolderCardView);
             imageView6=(ImageView)view.findViewById(R.id.imageView6);
              src=(TextView)view.findViewById(R.id.src);
              dest=(TextView)view.findViewById(R.id.dest);
              amount=(TextView)view.findViewById(R.id.amount);
//            gameimage=view.findViewById(R.id.gameimage);
//            holderCardView = view.findViewById(R.id.holderCardView);
//            gametitlename=view.findViewById(R.id.gametitlename);
//            gametimelist=view.findViewById(R.id.gametimelist);
//            gametype1=view.findViewById(R.id.gametype1);
//            maptypename1= view.findViewById(R.id.maptypename1);
//            versiontypename1=view.findViewById(R.id.versiontypename1);
        }
    }
}
