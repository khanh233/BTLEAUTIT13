package com.example.quanlyquancafe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyquancafe.admin.OnProductListener;

import java.util.ArrayList;

public class ListProductFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DBHelper dbHelper = new DBHelper(requireContext());

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        ProductAdapter adapter = new ProductAdapter(dbHelper.getProducts(), new OnProductListener() {

            @Override
            public void onItemClick(Product product) {
                ((MenuActivity) requireActivity()).selectProduct.add(product);
                Toast.makeText(requireContext(), "Thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });
        ImageView ivCart = view.findViewById(R.id.ivCart);
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((MenuActivity) requireActivity()).selectProduct.isEmpty()) {
                    Toast.makeText(requireContext(), "Bạn chưa thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    ((MenuActivity) requireActivity()).showCartFragment();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
