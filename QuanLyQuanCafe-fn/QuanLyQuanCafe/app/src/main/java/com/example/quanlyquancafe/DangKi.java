package com.example.quanlyquancafe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class DangKi extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtEmail;
    private EditText edtConfirmPassword;
    private View layoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangki);

        layoutLoading = findViewById(R.id.layoutLoading);
        edtUsername = findViewById(R.id.etUsername);
        edtPassword = findViewById(R.id.etPassword);
        edtEmail = findViewById(R.id.etEmail);
        edtConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();

                String confirmPassword = edtConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    edtUsername.setError("Vui lòng nhập tài khoản");
                    edtUsername.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    edtEmail.setError("Vui lòng nhập Email");
                    edtEmail.requestFocus();
                    return;
                }
                if (!isValid(email)) {
                    edtEmail.setError("Vui lòng nhập đúng định dạng Email");
                    edtEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    edtPassword.setError("Vui lòng nhập mật khẩu");
                    edtPassword.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    edtConfirmPassword.setError("Vui lòng nhập lại mật khẩu");
                    edtConfirmPassword.requestFocus();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    edtConfirmPassword.setError("Mật khẩu không khớp");
                    edtConfirmPassword.requestFocus();
                    return;
                }

                layoutLoading.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBHelper dbHelper = new DBHelper(DangKi.this);
                        Account account = dbHelper.register(email, username, password);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (account != null) {
                                    SessionManager.getInstance().setCurrentAccount(account);
                                    Toast.makeText(DangKi.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(DangKi.this, MenuActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    edtUsername.setError("Đăng ký thất bại. Tài khoản đã tồn tại.");
                                    edtUsername.requestFocus();
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }

    public boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}

