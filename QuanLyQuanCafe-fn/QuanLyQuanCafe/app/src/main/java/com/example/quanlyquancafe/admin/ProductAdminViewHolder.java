package com.example.quanlyquancafe.admin;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyquancafe.ImageUtil;
import com.example.quanlyquancafe.Product;
import com.example.quanlyquancafe.R;

public class ProductAdminViewHolder extends RecyclerView.ViewHolder {

    private final ImageView ivProduct;
    private final TextView tvIdProduct;
    private final TextView tvNameProduct;
    private final TextView tvPriceProduct;

    private final TextView btnEdit;
    private final TextView btnDelete;

    private final OnProductListener listener;

    public ProductAdminViewHolder(View view, OnProductListener listener) {
        super(view);
        this.listener = listener;
        ivProduct = view.findViewById(R.id.ivProduct);

        tvIdProduct = view.findViewById(R.id.tvId);
        tvNameProduct = view.findViewById(R.id.tvName);
        tvPriceProduct = view.findViewById(R.id.tvPrice);

        btnEdit = view.findViewById(R.id.btnEdit);
        btnDelete = view.findViewById(R.id.btnDelete);
    }

    public void bind(Product product) {
        if (TextUtils.isEmpty(product.getImage())) {
            ivProduct.setImageResource(R.drawable.oip);
        } else {
            ivProduct.setImageBitmap(ImageUtil.decode(product.getImage()));
        }

        tvIdProduct.setText(product.getId());
        tvNameProduct.setText(product.getName());
        tvPriceProduct.setText(product.getPrice() + " VNĐ");

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onEdit(product);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onDelete(product);
                }
            }
        });
    }
}
