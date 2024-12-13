package com.example.quanlyquancafe.admin;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyquancafe.DBHelper;
import com.example.quanlyquancafe.ImageUtil;
import com.example.quanlyquancafe.Product;
import com.example.quanlyquancafe.R;

public class ProductFragment extends Fragment {

    int PICK_IMAGE_REQUEST = 1;
    private Bitmap imageBitmap;

    private TextView tvTitle;
    private EditText edtId;
    private EditText edtName;
    private EditText edtQuantity;
    private EditText edtPrice;

    private ImageView ivImage;

    private Product productNeedEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getArguments().containsKey("product")) {
            productNeedEdit = (Product) getArguments().getSerializable("product");
        }

        tvTitle = view.findViewById(R.id.tvTitle);
        edtId = view.findViewById(R.id.etIdProduct);
        edtName = view.findViewById(R.id.etNameProduct);
        edtQuantity = view.findViewById(R.id.etQuantityProduct);
        edtPrice = view.findViewById(R.id.etPriceProduct);
        ivImage = view.findViewById(R.id.ivProduct);

        TextView btnCreate = view.findViewById(R.id.btnCreate);

        if (productNeedEdit != null) {
            tvTitle.setText("Sửa sản phẩm");
            edtId.setText(productNeedEdit.getId());
            edtId.setEnabled(false);
            edtName.setText(productNeedEdit.getName());
            edtPrice.setText(String.valueOf(productNeedEdit.getPrice()));
            if (productNeedEdit.getImage() != null) {
                imageBitmap = ImageUtil.convert(productNeedEdit.getImage());
                ivImage.setImageBitmap(imageBitmap);
            }
            btnCreate.setText("Sửa");
        }

        ImageView ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtId.getText().toString().trim();
                String name = edtName.getText().toString().trim();
                String price = edtPrice.getText().toString().trim();
                if (TextUtils.isEmpty(id)) {
                    edtId.setError("Vui lòng nhập ID sản phẩm");
                    edtId.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    edtName.setError("Vui lòng nhập tên sản phẩm");
                    edtName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(price)) {
                    edtPrice.setError("Vui lòng nhập giá sản phẩm");
                    edtPrice.requestFocus();
                    return;
                }
                String base64 = null;
                if (imageBitmap != null) {
                    base64 = ImageUtil.convert(imageBitmap);
                }
                Product product = new Product(id, name, base64, Integer.parseInt(price));
                DBHelper dbHelper = new DBHelper(requireContext());
                if (productNeedEdit != null) {
                    if (dbHelper.updateProduct(product) > 0) {
                        Toast.makeText(requireContext(), "Sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        requireActivity().getOnBackPressedDispatcher().onBackPressed();
                    } else {
                        Toast.makeText(requireContext(), "Sửa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (dbHelper.checkIdProduct(id)) {
                        edtId.setError("ID sản phẩm đã tồn tại");
                        edtId.requestFocus();
                    } else {
                        long result = dbHelper.addProduct(product);
                        if (result > 0) {
                            Toast.makeText(requireContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                            requireActivity().getOnBackPressedDispatcher().onBackPressed();
                        } else {
                            Toast.makeText(requireContext(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    @SuppressLint("IntentReset")
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), data.getData());
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 500, 500, false);
                ivImage.setImageBitmap(imageBitmap);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
