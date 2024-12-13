package com.example.quanlyquancafe.admin;

import com.example.quanlyquancafe.Product;

public interface OnProductListener {

    default void onItemClick(Product product) {
    }

    default void onEdit(Product product) {
    }

    default void onDelete(Product product) {
    }
}
