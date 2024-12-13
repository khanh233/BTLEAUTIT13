package com.example.quanlyquancafe;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyquancafe.admin.OnProductListener;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    private final View parent;
    private final ImageView ivProduct;
    private final TextView tvNameProduct;
    private final TextView tvPriceProduct;

    private final OnProductListener listener;

    public ProductViewHolder(View view, OnProductListener listener) {
        super(view);
        this.listener = listener;
        parent = view.findViewById(R.id.parent);
        ivProduct = view.findViewById(R.id.ivProduct);

        tvNameProduct = view.findViewById(R.id.tvName);
        tvPriceProduct = view.findViewById(R.id.tvPrice);
    }

    public void bind(Product product) {
        if (TextUtils.isEmpty(product.getImage())) {
            ivProduct.setImageResource(R.drawable.oip);
        } else {
            ivProduct.setImageBitmap(ImageUtil.decode(product.getImage()));
        }
        tvNameProduct.setText(product.getName());
        tvPriceProduct.setText("Giá: " + product.getPrice() + " VNĐ");

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(product);
                }
            }
        });
    }
}
