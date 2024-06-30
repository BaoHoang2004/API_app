package com.example.ps34368.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ps34368.R;
import com.example.ps34368.adapter.SanPhamAdapter;
import com.example.ps34368.dao.SanPhamDAO;
import com.example.ps34368.model.SanPham;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.navigationView);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        RecyclerView rcvResult = findViewById(R.id.rcvResult);
        ImageView icAddSP = findViewById(R.id.icAddSP);
        Toolbar toolbar = findViewById(R.id.toolBar);

        //Thiết lập thanh công cụ là thanh ứng dụng chính của activity
        setSupportActionBar(toolbar);
        //Cho phép hiển thị nút back trên thanh công cụ
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Setup DrawerToggle (dùng để thay dổi hình ảnh của nút toggle trê thanh toolbar)
        //Khi người dùng mở và đóng thanh điều hướng
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close);
        //bật chức năng hiển thị hình ảnh của DrawerToggle trên toolbar
        //khi chức năng này đc bật, hình ảnh mặc định của DrawerToggle sẽ đc hiển thị trên toolbar
        //Điều này cho phếp người dùng nhận biết đc sự mở và đóng của navigation drawer
        //thông qua hình ảnh
        drawerToggle.setDrawerIndicatorEnabled(true);

        //đồng bộ trạng thái của DrawerTggle với trạng thái của DrawerLayout
        drawerToggle.syncState();

        //gắn kết DrawerToggle với DrawerLayout
        drawerLayout.addDrawerListener(drawerToggle);

        //Hiển thị dữ liệu lên recycler
        SanPhamDAO sanPhamDAO = new SanPhamDAO(MainActivity.this);
        ArrayList<SanPham> listSanPham = sanPhamDAO.getListSanPham();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rcvResult.setLayoutManager(linearLayoutManager);

        SanPhamAdapter adapter = new SanPhamAdapter(MainActivity.this,listSanPham,sanPhamDAO);
        rcvResult.setAdapter(adapter);

        //Bắt sự kiện click vào item trong navigation
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.logOut){
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    Toast.makeText(MainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.qlsp) {
                    toolbar.setTitle("Quản lý sản phẩm");
                } else if (item.getItemId() == R.id.gioiThieu) {
                    toolbar.setTitle("Giới thiệu");
                }else {
                    toolbar.setTitle("Setting");
                }
                return true;
            }
        });

        // Add
        icAddSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = (MainActivity.this).getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_add,null);
                builder.setView(view);
                AlertDialog dialog = builder.create();

                //
                TextInputEditText edtTenSPAdd = view.findViewById(R.id.edtTenSPAdd);
                TextInputEditText edtGiaBanAdd = view.findViewById(R.id.edtGiaBanAdd);
                TextInputEditText edtSoLuongAdd = view.findViewById(R.id.edtSoLuongAdd);

                Button btnAdd = view.findViewById(R.id.btnAdd);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenSP = edtTenSPAdd.getText().toString();
                        String giaBan = edtGiaBanAdd.getText().toString();
                        String soLuong = edtSoLuongAdd.getText().toString();

                        //Kiểm tra nhập liệu
                        if (tenSP.isEmpty() || giaBan.isEmpty() || soLuong.isEmpty()){
                            Toast.makeText(MainActivity.this, "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                        }else {
                            SanPham sp = new SanPham();
                            sp.setTensp(tenSP);
                            sp.setGiaban(Integer.parseInt(giaBan));
                            sp.setSoluong(Integer.parseInt(soLuong));

                            boolean check = sanPhamDAO.addSanPham(sp);
                            if (check){
                                Toast.makeText(MainActivity.this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                                listSanPham.clear();
                                listSanPham.addAll(sanPhamDAO.getListSanPham());
                                adapter.notifyDataSetChanged();

                            }else {
                                Toast.makeText(MainActivity.this, "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                    dialog.show();

            }
        });

    }
}