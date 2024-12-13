package com.example.quanlyquancafe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyquancafe.admin.OnProductListener;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private final ArrayList<Product> products = new ArrayList<>();
    private CartAdapter cartAdapter;

    private EditText edtAddress;
    private EditText edtPhone;

    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DBHelper(requireContext());

        view.findViewById(R.id.ivBack).setOnClickListener(view1 -> {
            onBack();
        });
        if (getArguments() != null && getArguments().containsKey("list_product")) {
            ArrayList<Product> original = (ArrayList<Product>) getArguments().getSerializable("list_product");
            assert original != null;
            original.forEach(product -> {
                if (products.isEmpty()) {
                    product.setQuantity(1);
                    products.add(product);
                } else {
                    int index = -1;
                    for (int i = 0; i < products.size(); i++) {
                        if (products.get(i).getId().equals(product.getId())) {
                            index = i;
                            break;
                        }
                    }
                    if (index == -1) {
                        product.setQuantity(1);
                        products.add(product);
                    } else {
                        products.get(index).setQuantity(products.get(index).getQuantity() + 1);
                    }
                }
            });

            long total = 0L;
            for (Product item : products) {
                total += item.getPrice() * item.getQuantity();
            }
            TextView tvTotal = view.findViewById(R.id.tvTotal);
            tvTotal.setText(total + " VNĐ");

            RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

            cartAdapter = new CartAdapter(products, new OnProductListener() {
                @Override
                public void onDelete(Product product) {
                    products.remove(product);
                    ((MenuActivity) requireActivity()).selectProduct = products;
                    cartAdapter.setProducts(products);
                }
            });
            recyclerView.setAdapter(cartAdapter);
        }

        edtAddress = view.findViewById(R.id.edtAddress);
        edtPhone = view.findViewById(R.id.edtPhone);

        TextView btnPay = view.findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = edtAddress.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();

                if (products.isEmpty()) {
                    return;
                }

                if (address.isEmpty()) {
                    edtAddress.setError("Vui lòng nhập địa chỉ");
                    edtAddress.requestFocus();
                    return;
                }
                if (phone.isEmpty()) {
                    edtPhone.setError("Vui lòng nhập số điện thoại");
                    edtPhone.requestFocus();
                    return;
                }
                if (phone.length() < 10) {
                    edtPhone.setError("Vui lòng nhập đúng định dạng số điện thoại");
                    edtPhone.requestFocus();
                    return;
                }

                btnPay.setEnabled(false);

                if (SessionManager.getInstance().getCurrentAccount() == null) {
                    return;
                }
                long accountId = SessionManager.getInstance().getCurrentAccount().getId();
                if (dbHelper.createBill(products, address, phone, accountId)) {
                    ((MenuActivity) requireActivity()).selectProduct.clear();
                    Toast.makeText(requireContext(), "Tạo hoá đơn thành công!", Toast.LENGTH_SHORT).show();
                    onBack();
                } else {
                    Toast.makeText(requireContext(), "Tạo hoá đơn thất bại! Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    btnPay.setEnabled(true);
                }
            }
        });
    }

    private void onBack() {
        requireActivity().getOnBackPressedDispatcher().onBackPressed();
    }
}
