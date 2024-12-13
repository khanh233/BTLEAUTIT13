package com.example.quanlyquancafe;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyquancafe.admin.OnProductListener;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Product> products;
    private final OnProductListener listener;
    private boolean isHideButtonDelete = false;

    public CartAdapter(List<Product> products, OnProductListener listener) {
        this.products = products;
        this.listener = listener;
    }

    public CartAdapter(List<Product> products, boolean isHideButtonDelete) {
        this.products = products;
        this.listener = null;
        this.isHideButtonDelete = isHideButtonDelete;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_product, parent, false);
        return new CartViewHolder(view, listener, isHideButtonDelete);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
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
