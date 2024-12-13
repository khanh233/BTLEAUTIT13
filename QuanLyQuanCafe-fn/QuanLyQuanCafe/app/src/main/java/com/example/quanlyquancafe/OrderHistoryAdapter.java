package com.example.quanlyquancafe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryViewHolder> {

    private List<OrderHistory> orderHistories;

    public OrderHistoryAdapter(List<OrderHistory> orderHistories) {
        this.orderHistories = orderHistories;
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_admin, parent, false);
        return new OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        if (position < orderHistories.size()) {
            holder.bind(orderHistories.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return orderHistories != null ? orderHistories.size() : 0;
    }
}
