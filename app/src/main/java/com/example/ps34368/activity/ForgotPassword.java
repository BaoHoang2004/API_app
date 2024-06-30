package com.example.ps34368.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ps34368.R;
import com.example.ps34368.database.DbHelper;
import com.google.android.material.textfield.TextInputEditText;

public class ForgotPassword extends AppCompatActivity {
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        TextInputEditText edtUsernameForgot = findViewById(R.id.userNameForgot);
        Button btnReset = findViewById(R.id.btnReset);

        db = new DbHelper(this);

        //Bắt sự kiện click btnReset
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDangNhap = edtUsernameForgot.getText().toString();
                boolean check = db.checkUsername(tenDangNhap);

                if (check){
                    Intent intent = new Intent(ForgotPassword.this,ResetPassword.class);
                    intent.putExtra("username",tenDangNhap);
                    startActivity(intent);
                }else {
                    Toast.makeText(ForgotPassword.this, "User không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}