package com.example.quanlyquancafe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class  OrderHistoryFragment extends Fragment {

    private OrderHistoryAdapter adapter;
    private DBHelper dbHelper;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DBHelper(requireContext());

        recyclerView = view.findViewById(R.id.recyclerView);

        List<OrderHistory> orderHistories;
        if (getArguments() != null && getArguments().getBoolean("is_get_all")) {
            orderHistories = dbHelper.getOrders();
        } else {
            orderHistories = dbHelper.getOrdersByAccountId(SessionManager.getInstance().getCurrentAccount().getId());
        }

        adapter = new OrderHistoryAdapter(orderHistories);

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

}
