package com.webingate.paysmartcustomerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.webingate.paysmartcustomerapp.R;
import com.webingate.paysmartcustomerapp.object.DirectoryHome9ProductsVO;
import com.webingate.paysmartcustomerapp.object.GetalyftVehiclelist;
import com.webingate.paysmartcustomerapp.utils.Utils;

import java.util.List;

public class customerapp_VehicleTypesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GetalyftVehiclelist> productsList;
    private OnItemClickListener itemClickListener;
    private int selectedPos;

    public interface OnItemClickListener {
        void onItemClick(View view, GetalyftVehiclelist product, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public customerapp_VehicleTypesAdapter(List<GetalyftVehiclelist> productsList) {
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customerapp_dashboard_vehicletypeitem, parent, false);
        return new ProductsViewHolder(productView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductsViewHolder) {

            if (position == 0) {

                GetalyftVehiclelist productsVO = productsList.get(position);
                ProductsViewHolder productsViewHolder = (ProductsViewHolder) holder;
                Context context = productsViewHolder.productImageView.getContext();

                productsViewHolder.productTextView.setText(productsVO.getName());
                 if(productsVO.getselected()=="0"){
                     int productImageId = Utils.getDrawableInt(context, productsVO.getSelectedIcon());
                     Utils.setImageToImageView(context, productsViewHolder.productImageView, productImageId);

                 }
                 else{
                     int productImageId = Utils.getDrawableInt(context, productsVO.getIcon());
                     Utils.setImageToImageView(context, productsViewHolder.productImageView, productImageId);
                 }
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context selViewContext = v.getContext();
//                      for(int i=0 ; i<productsList.size() ; i++){
//                          productsList.get(i).setselected("0");
//                      }
//                        if(selViewContext!=null){
//                            int productImageId = Utils.getDrawableInt(context, productsVO.getIcon());
//                            Utils.setImageToImageView(context, productsViewHolder.productImageView, productImageId);
//                        }
                        int productImageId = Utils.getDrawableInt(selViewContext, productsVO.getSelectedIcon());
                        Utils.setImageToImageView(selViewContext, productsViewHolder.productImageView, productImageId);

                       // v.setBackgroundColor(0x7f06003a);
                    }
                };
                productsViewHolder.productImageView.setOnClickListener(listener);

            } else {
                if (position == 9) {

                    ProductsViewHolder productsViewHolder = (ProductsViewHolder) holder;
                    Context context = productsViewHolder.productImageView.getContext();

                    productsViewHolder.productTextView.setText("All Products");

                    int allProductImageId = R.drawable.home9_all_products;
                    Utils.setImageToImageView(context, productsViewHolder.productImageView, allProductImageId);

                } else {

                    GetalyftVehiclelist productsVO = productsList.get(position);
                    ProductsViewHolder productsViewHolder = (ProductsViewHolder) holder;
                    Context context = productsViewHolder.productImageView.getContext();

                    productsViewHolder.productTextView.setText(productsVO.getName());
                    if(productsVO.getselected()=="0"){
                        int productImageId = Utils.getDrawableInt(context, productsVO.getSelectedIcon());
                        Utils.setImageToImageView(context, productsViewHolder.productImageView, productImageId);

                    }
                    else{
                        int productImageId = Utils.getDrawableInt(context, productsVO.getIcon());
                        Utils.setImageToImageView(context, productsViewHolder.productImageView, productImageId);
                    }
                    int productImageId = Utils.getDrawableInt(context, productsVO.getIcon());
                    Utils.setImageToImageView(context, productsViewHolder.productImageView, productImageId);

                    View.OnClickListener listener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Context selViewContext = v.getContext();

                            int productImageId = Utils.getDrawableInt(selViewContext, productsVO.getSelectedIcon());
                            Utils.setImageToImageView(selViewContext, productsViewHolder.productImageView, productImageId);

                           // v.setBackgroundColor(0x7f06003a);
                        }
                    };
                    productsViewHolder.productImageView.setOnClickListener(listener);
                }
            }

            if (itemClickListener != null) {
                for(int i=0 ; i<productsList.size() ; i++){
                    if(i==position-1)
                        productsList.get(i).setselected("1");
                    else
                        productsList.get(i).setselected("0");
                }
//                selectedPos = position;
//                productsList.get(selectedPos).setselected("0");
//                productsList.get(position).setselected("1");
                ((ProductsViewHolder) holder).productConstraintLayout.setOnClickListener(
                        view -> itemClickListener.onItemClick(view, productsList.get(position), position)
                );
            }

        }
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }
    
    public int getSelectedPos(){
        return selectedPos;
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout productConstraintLayout;
        ImageView productImageView;
        TextView productTextView;

        public ProductsViewHolder(View itemView) {
            super(itemView);
            productConstraintLayout = itemView.findViewById(R.id.productConstraint);
            productImageView = itemView.findViewById(R.id.productImageView);
            productTextView = itemView.findViewById(R.id.productTextView);
        }
    }
}
