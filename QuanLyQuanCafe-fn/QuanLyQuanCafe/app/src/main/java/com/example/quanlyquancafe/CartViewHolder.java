package com.example.quanlyquancafe;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyquancafe.admin.OnProductListener;

public class CartViewHolder extends RecyclerView.ViewHolder {

    private final ImageView ivProduct;
    private final TextView tvNameProduct;
    private final TextView tvPriceProduct;
    private final TextView tvQuantity;

    private final TextView btnDelete;
    private final OnProductListener listener;
    private final boolean isHideButtonDelete;

    public CartViewHolder(View view, OnProductListener listener, boolean isHideButtonDelete) {
        super(view);
        this.listener = listener;
        this.isHideButtonDelete = isHideButtonDelete;

        ivProduct = view.findViewById(R.id.ivProduct);

        tvNameProduct = view.findViewById(R.id.tvName);
        tvPriceProduct = view.findViewById(R.id.tvPrice);
        tvQuantity = view.findViewById(R.id.tvQuantity);
        btnDelete = view.findViewById(R.id.btnDelete);
    }

    public void bind(Product product) {
        if (TextUtils.isEmpty(product.getImage())) {
            ivProduct.setImageResource(R.drawable.oip);
        } else {
            ivProduct.setImageBitmap(ImageUtil.decode(product.getImage()));
        }
        tvNameProduct.setText(product.getName());
        tvPriceProduct.setText("Giá: " + product.getPrice() + " VNĐ");
        tvQuantity.setText("Số lượng: " + product.getQuantity());

        if (isHideButtonDelete) {
            btnDelete.setVisibility(View.GONE);
        } else {
            btnDelete.setVisibility(View.VISIBLE);
        }
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
