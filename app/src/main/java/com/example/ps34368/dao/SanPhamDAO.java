package com.example.ps34368.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ps34368.database.DbHelper;
import com.example.ps34368.model.SanPham;

import java.util.ArrayList;

public class SanPhamDAO {
    private final DbHelper dbHelper;

    public SanPhamDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    //Hàm lấy danh sách sản phẩm
    public ArrayList<SanPham> getListSanPham(){
        //tạo một danh sách để add dữ liệu vào SanPham
        ArrayList<SanPham> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor c = database.rawQuery("select * from Products",null);
            if (c.getCount() > 0){
                c.moveToFirst();
                do {
                    list.add(new SanPham(c.getInt(0),
                            c.getString(1),
                            c.getInt(2),
                            c.getInt(3)));
                }while (c.moveToNext());
                database.setTransactionSuccessful();
            }
        }catch (Exception e){
            Log.e("Error", "getListSanPham: " + e);
        }finally {
            database.endTransaction();
        }
        return list;
    }

    //Hàm thêm sản phẩm
    public boolean addSanPham(SanPham sanPham){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensp",sanPham.getTensp());
        values.put("giaban",String.valueOf(sanPham.getGiaban()));
        values.put("soluong",String.valueOf(sanPham.getSoluong()));

        long check = database.insert("Products",null,values);
        return check != -1;
    }

    //Hàm delete sản phẩm
    public boolean deleteSanPham(int maSP){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long check = database.delete("Products","masp=?",
                new String[]{String.valueOf(maSP)});
        return check != -1;
    }

    //Hàm update sản phẩm
    public boolean updateSanPham(SanPham sanPham){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensp",sanPham.getTensp());
        values.put("giaban",String.valueOf(sanPham.getGiaban()));
        values.put("soluong",String.valueOf(sanPham.getSoluong()));

        long check = database.update("Products",values,"masp=?",
                new String[]{String.valueOf(sanPham.getMasp())});
        return check != -1;
    }
}
