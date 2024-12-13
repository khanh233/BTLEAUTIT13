package com.example.quanlyquancafe;

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

import com.example.quanlyquancafe.admin.BillManagerFragment;
import com.example.quanlyquancafe.admin.ProductFragment;
import com.example.quanlyquancafe.admin.ProductManagerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private BottomNavigationView bottom;
    public ArrayList<Product> selectProduct = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("cart");

                if (fragment instanceof CartFragment) {
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
                if (item.getItemId() == R.id.menu_home) {
                    showProductFragment();
                } else if (item.getItemId() == R.id.menu_bill) {
                    showOrderHistoryFragment();
                }
                return true;
            }
        });
        bottom.setSelectedItemId(R.id.menu_home);
    }

    private void showProductFragment() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ListProductFragment())
                .commit();
        bottom.setVisibility(View.VISIBLE);
    }

    private void showOrderHistoryFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new OrderHistoryFragment())
                .commit();
        bottom.setVisibility(View.VISIBLE);
    }

    public void showCartFragment() {

        CartFragment fragment = new CartFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list_product", selectProduct);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, "cart")
                .commit();

        bottom.setVisibility(View.GONE);
    }
}