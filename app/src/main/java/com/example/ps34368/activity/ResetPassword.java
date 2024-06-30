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

public class ResetPassword extends AppCompatActivity {
    DbHelper db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        TextView usernameReset = findViewById(R.id.userReset);
        TextInputEditText passwordReset = findViewById(R.id.passwordReset);
        TextInputEditText confirmPasswordReset = findViewById(R.id.confirmPasswordReset);
        Button btnReset = findViewById(R.id.btnConfirm);

        //Nhận dữ liệu đã được check từ Forgot Password
        db = new DbHelper(this);
        Intent intent = getIntent();
        usernameReset.setText(intent.getStringExtra("username"));

        //Click btnConfirm
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDangNhapReset = usernameReset.getText().toString();
                String newPassword = passwordReset.getText().toString();
                String newConfirm = confirmPasswordReset.getText().toString();

                //Kiểm tra nhập liệu
                if (tenDangNhapReset.isEmpty() || newPassword.isEmpty() || newConfirm.isEmpty()){
                    Toast.makeText(ResetPassword.this, "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (newConfirm.compareTo(newPassword) == 0){
                        boolean check = db.updatePassword(tenDangNhapReset,newPassword);
                        if (check){
                            startActivity(new Intent(ResetPassword.this,LoginActivity.class));
                            Toast.makeText(ResetPassword.this, "Đã cập nhật Password thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ResetPassword.this, "Cập nhập Password thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(ResetPassword.this, "Confirm và Password không khớp", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}