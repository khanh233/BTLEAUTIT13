package com.example.quanlyquancafe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlyquancafe.admin.AdminActivity;

public class DangNhapActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private TextView createAccountText;
    private View layoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);

        edtUsername = findViewById(R.id.usernameEditText);
        layoutLoading = findViewById(R.id.layoutLoading);
        edtPassword = findViewById(R.id.passwordEditText);
        createAccountText = findViewById(R.id.createAccountText);
        createAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangNhapActivity.this, DangKi.class));
            }
        });

        Button btnLogin = findViewById(R.id.loginButton);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString().trim(); // lay gia tri o username
                String password = edtPassword.getText().toString().trim(); // lay gia tri o username

                if (TextUtils.isEmpty(username)) {
                    edtUsername.setError("Username không được bỏ trống");
                    edtUsername.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    edtPassword.setError("Password không được bỏ trống");
                    edtPassword.requestFocus();
                    return;
                }

                layoutLoading.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBHelper dbHelper = new DBHelper(DangNhapActivity.this);

                        Account account = dbHelper.login(username, password);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                layoutLoading.setVisibility(View.GONE);
                                if (account != null) {
                                    SessionManager.getInstance().setCurrentAccount(account);
                                    Intent intent;
                                    if (account.getRole() == Role.USER.value) {
                                        intent = new Intent(DangNhapActivity.this, MenuActivity.class);
                                    } else {
                                        intent = new Intent(DangNhapActivity.this, AdminActivity.class);
                                    }
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(DangNhapActivity.this, "Dang nhap that bai", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }
}