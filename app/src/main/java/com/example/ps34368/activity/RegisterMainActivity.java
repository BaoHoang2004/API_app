package com.example.ps34368.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ps34368.R;
import com.example.ps34368.database.DbHelper;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterMainActivity extends AppCompatActivity {
    TextInputEditText edtUserNameResigter;
    TextInputEditText edtFullNameResigter;
    TextInputEditText edtPasswordResigter;
    TextInputEditText edtConfirmPasswordresigter;
    Button btnResigter;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);

        //Ánh xạ
        edtUserNameResigter = findViewById(R.id.userNameResigter);
        edtFullNameResigter = findViewById(R.id.fullNameResigter);
        edtPasswordResigter = findViewById(R.id.passwordResigter);
        edtConfirmPasswordresigter = findViewById(R.id.confirmPasswordResigter);
        btnResigter = findViewById(R.id.btnResigter);
        tvLogin = findViewById(R.id.tvLogin);

        //Bắt sự kiện click vào tvLogin
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterMainActivity.this,LoginActivity.class));

            }
        });

        //Bắt sự kiện click button Resigter
        btnResigter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDanhNhap = edtUserNameResigter.getText().toString();
                String hoTen = edtFullNameResigter.getText().toString();
                String matKhau = edtPasswordResigter.getText().toString();
                String confirmPassword = edtConfirmPasswordresigter.getText().toString();

                DbHelper dbHelper = new DbHelper(RegisterMainActivity.this);
                // kiểm tra nhập liệu
                if (tenDanhNhap.isEmpty() || hoTen.isEmpty() || matKhau.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(RegisterMainActivity.this, "Bạn cần nhập đầy đủ", Toast.LENGTH_SHORT).show();
                }else {
                    boolean check = dbHelper.checkUsername(tenDanhNhap);
                    if (check){
                        edtUserNameResigter.setError("Username đã tồn tại");
                    }else {
                        if (confirmPassword.compareTo(matKhau) == 0){
                            if (isValid(matKhau)){
                                dbHelper.resigter(tenDanhNhap,matKhau,hoTen);
                                Toast.makeText(RegisterMainActivity.this, "inserted thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterMainActivity.this,LoginActivity.class));
                            }
                            else {
                                Toast.makeText(RegisterMainActivity.this, "Mật khẩu bảo mật kém", Toast.LENGTH_SHORT).show();

                            }

                        }else {
                            Toast.makeText(RegisterMainActivity.this, "password và confirm không khớp", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });

    }

    //Hàm kiểm tra tính hợp lệ của password
    private static boolean isValid(String passwordHere){
        int flag1 = 0, flag2 = 0 , flag3 = 0;
        if (passwordHere.length()<0){
            return false;
        }else {
            for (int p = 0 ;p <passwordHere.length(); p++){
                //kiểm tra ký tự có phải chữ hay k
                if (Character.isLetter(passwordHere.charAt(p))){
                    flag1 = 1;
                }
            }
            for (int r = 0 ; r<passwordHere.length(); r++){
                // kiem tra số
                if (Character.isDigit(passwordHere.charAt(r))){
                    flag2 = 1;
                }
            }
            for (int s = 0; s<passwordHere.length();s++){
                //Lấy ký tự hiện tại
                char c = passwordHere.charAt(s);
                //--và kiểm tra xem có thuộc ký tự đặc biệt như dấu chấm câu
                //(33-46) hoặc '@' (64)
                if (c >= 33 && c <= 46 || c == 64){
                    flag3 = 1;
                }
            }
            if (flag1 == 1 && flag2 == 1 && flag3 == 1){
                return true;
            }else {
                return false;
            }
        }
    }
}