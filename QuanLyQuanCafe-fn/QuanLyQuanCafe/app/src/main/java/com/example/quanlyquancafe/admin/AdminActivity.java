package com.example.quanlyquancafe.admin;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.quanlyquancafe.OrderHistoryFragment;
import com.example.quanlyquancafe.Product;
import com.example.quanlyquancafe.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminActivity extends AppCompatActivity {

    private BottomNavigationView bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("product");

                if (fragment instanceof ProductFragment) {
                    showProductFragment();
                } else {
                    finish();
                }
            }
        });

        bottom = findViewById(R.id.bottom);
        bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_admin_product) {
                    showProductFragment();
                } else if (item.getItemId() == R.id.menu_admin_bill) {
                    showBillFragment();
                }
                return true;
            }
        });
        bottom.setSelectedItemId(R.id.menu_admin_product);
    }

    private void showProductFragment() {
        bottom.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ProductManagerFragment())
                .commit();
    }

    private void showBillFragment() {
        bottom.setVisibility(View.VISIBLE);
        OrderHistoryFragment fragment = new OrderHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_get_all", true);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void showCreateProduct() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ProductFragment(), "product")
                .commit();
        bottom.setVisibility(View.GONE);
    }

    public void showEditProduct(Product product) {
        ProductFragment fragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, "product")
                .commit();
        bottom.setVisibility(View.GONE);
    }
}