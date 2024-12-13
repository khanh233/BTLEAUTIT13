package com.example.quanlyquancafe.admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyquancafe.DBHelper;
import com.example.quanlyquancafe.Product;
import com.example.quanlyquancafe.R;

import java.util.ArrayList;

public class ProductManagerFragment extends Fragment {

    private ProductAdminAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_manager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DBHelper dbHelper = new DBHelper(requireContext());

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        TextView btnCreate = view.findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AdminActivity) requireActivity()).showCreateProduct();
            }
        });
        adapter = new ProductAdminAdapter(dbHelper.getProducts(), new OnProductListener() {

            @Override
            public void onEdit(Product product) {
                ((AdminActivity) requireActivity()).showEditProduct(product);
            }

            @Override
            public void onDelete(Product product) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Xác nhận")
                        .setMessage("Bạn có chắc chắn muốn xóa sản phẩm " + product.getName())
                        .setNegativeButton("Xoá", (dialogInterface, i) -> {
                            if (dbHelper.deleteProduct(product.getId())) {
                                adapter.setProducts(dbHelper.getProducts());
                            } else {
                                Toast.makeText(requireContext(), "Xoá sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("Huỷ", null)
                        .create()
                        .show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
