package com.example.ps34368.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "QLSP", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qND ="create table NguoiDung(tendangnhap text primary key, matkhau text, hoten text)";
        db.execSQL(qND);

        String qH = "create table Products(masp integer primary key autoincrement," +
                "tensp text, giaban integer, soluong integer)";
        db.execSQL(qH);
        //Nạp dữ liệu cho table Products
        String data = "insert into Products values(1,'Bánh quy bơ LU Pháp',45000,10)," +
                "(2,'Snack mực lăn muối ớt',8000,52),(3,'Snack khoai tây Lays',12000,38)," +
                "(4,'Bánh gạo One One',30000,11),(5,'Kẹo sữa Chocolate',25000,30)";
        db.execSQL(data);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists NguoiDung");
        db.execSQL("drop table if exists Products");
    }

    // Hàm Resigter
    public void resigter(String tenDangNhap, String matKhau, String hoTen){
        ContentValues cv = new ContentValues();
        cv.put("tendangnhap",tenDangNhap);
        cv.put("matkhau",matKhau);
        cv.put("hoten",hoTen);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("NguoiDung",null,cv);
        db.close();
    }

    // Hàm login
    public int login(String tenDangNhap, String matKhau){
        int result = 0;
        String str[] = new String[2]; // = 2 vì có 2 biến
        str[0] = tenDangNhap;
        str[1] = matKhau;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select*from NguoiDung where tendangnhap=? and matkhau=?",str);
        if (c.moveToNext()){
            result = 1;
        }
        return result;
    }

    //Hàm checkUsername
    public boolean checkUsername(String tenDangNhap){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select*from NguoiDung where tendangnhap =?",
                new String[]{tenDangNhap});
        if (c.getCount() >0){
            return true;
        }else {
            return false;
        }
    }

    //Hàm updatePassword
    public boolean updatePassword(String tenDangNhap, String pasword){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matKhau",pasword);
        long result = sqLiteDatabase.update("NguoiDung",contentValues,"tendangnhap=?",
                new String[]{tenDangNhap});
        return result != -1;
    }
}
