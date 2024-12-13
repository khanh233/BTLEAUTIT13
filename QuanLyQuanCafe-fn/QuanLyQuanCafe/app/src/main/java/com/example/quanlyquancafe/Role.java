package com.example.quanlyquancafe;

public enum Role {
    ADMIN(1),
    USER(2);

    public final int value;

    private Role(int value) {
        this.value = value;
    }
}
