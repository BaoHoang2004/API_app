package com.example.ps34368.model;

public class SanPham {
    private int masp;
    private String tensp;
    private int giaban;
    private int soluong;

    //Constructor
    public SanPham(int masp, String tensp, int giaban, int soluong) {
        this.masp = masp;
        this.tensp = tensp;
        this.giaban = giaban;
        this.soluong = soluong;
    }

    public SanPham() {
    }

    //Getter and Setter

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGiaban() {
        return giaban;
    }

    public void setGiaban(int giaban) {
        this.giaban = giaban;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}

