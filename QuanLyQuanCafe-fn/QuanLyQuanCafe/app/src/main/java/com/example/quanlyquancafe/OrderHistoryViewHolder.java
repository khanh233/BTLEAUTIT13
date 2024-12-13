package com.example.quanlyquancafe;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvName;
    private final TextView tvAddress;
    private final TextView tvPhone;
    private final TextView tvTotal;
    private final RecyclerView recyclerView;

    public OrderHistoryViewHolder(View view) {
        super(view);

        tvName = view.findViewById(R.id.tvName);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvTotal = view.findViewById(R.id.tvTotal);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    public void bind(OrderHistory orderHistory) {
        tvName.setText(orderHistory.getUsername());
        tvAddress.setText(orderHistory.getAddress());
        tvPhone.setText(orderHistory.getPhone());
        long total = 0;
        for (Product item: orderHistory.getProducts()) {
            total += item.getPrice() * item.getQuantity();
        }
        tvTotal.setText(String.valueOf(total));
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new CartAdapter(orderHistory.getProducts(), true));
    }
}
