package com.example.ps34368.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ps34368.R;
import com.example.ps34368.database.DbHelper;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edtUserNameLogin;
    TextInputEditText edtPasswordLogin;
    Button btnLogin;
    TextView tvForgotPassword;
    TextView tvResigter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ánh xạ
        edtUserNameLogin = findViewById(R.id.userNameLogin);
        edtPasswordLogin = findViewById(R.id.passwordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvResigter = findViewById(R.id.tvResigter);

        //Bắt sự kiện click vào forgotpassword
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
            }
        });

        //Bắt sự kiện click tvResigter
        tvResigter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterMainActivity.class));
            }
        });

        //Bắt sự kiện click button Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDangNhap = edtUserNameLogin.getText().toString();
                String matKhau = edtPasswordLogin.getText().toString();

                DbHelper dbHelper = new DbHelper(LoginActivity.this);

                // Kiểm tra nhập liệu
                if (tenDangNhap.isEmpty() || matKhau.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Bạn cần nhập đầy đủ", Toast.LENGTH_SHORT).show();
                }else {
                    if (dbHelper.login(tenDangNhap,matKhau) == 1){
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        //lưu tên đang nhập
                        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("tendangnhap",tenDangNhap);
                        editor.apply();//lưu dữ liệu vào data với key và value

                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }else {
                        Toast.makeText(LoginActivity.this, "đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}