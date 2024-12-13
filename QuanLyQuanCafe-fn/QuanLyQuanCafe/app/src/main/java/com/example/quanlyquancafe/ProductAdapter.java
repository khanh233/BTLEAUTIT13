package com.example.quanlyquancafe;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyquancafe.admin.OnProductListener;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private List<Product> products;
    private OnProductListener listener;

    public ProductAdapter(List<Product> products, OnProductListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_user, parent, false);
        return new ProductViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if (position >= products.size()) {
            return;
        }
        Product product = products.get(position);
        if (product != null) {
            holder.bind(product);
        }
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }
}
