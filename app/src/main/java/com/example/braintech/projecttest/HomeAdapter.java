package com.example.braintech.projecttest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.braintech.projecttest.Model.HomeFragmentModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private List<HomeFragmentModel.Data.Product> productList;
    private Context context;

    public HomeAdapter(List<HomeFragmentModel.Data.Product> productList,Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_data, viewGroup, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder homeViewHolder, int i) {

        HomeFragmentModel.Data.Product detail = productList.get(i);

        homeViewHolder.txt_product_name.setText(detail.getName());
        homeViewHolder.txt_product_dicription.setText(detail.getModel());
        homeViewHolder.txt_productcost.setText(detail.getPrice());
        Picasso.get().load(detail.getThumb()).into(homeViewHolder.img_productt);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder  {

        TextView txt_product_name,txt_product_dicription,txt_productcost;
        ImageView img_productt;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_product_name = (TextView)itemView.findViewById(R.id.txt_productName);
            txt_product_dicription = (TextView)itemView.findViewById(R.id.txt_productdiscription);
            txt_productcost = (TextView)itemView.findViewById(R.id.txt_product_cost);
            img_productt = (ImageView)itemView.findViewById(R.id.img_product);
        }

    }
}
