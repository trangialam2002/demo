package com.example.do_an_android.model;

public class SanPham{
    private int idSanPham;
    private String tenSanPham;
    private double donGia;
    private String imgSanPham;
    private String moTa;
    private int idTheLoai;

    public SanPham() {
    }

    public SanPham(int idSanPham, String tenSanPham, double donGia, String imgSanPham, String moTa, int idTheLoai) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.donGia = donGia;
        this.imgSanPham = imgSanPham;
        this.moTa = moTa;
        this.idTheLoai = idTheLoai;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public String getImgSanPham() {
        return imgSanPham;
    }

    public void setImgSanPham(String imgSanPham) {
        this.imgSanPham = imgSanPham;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getIdTheLoai() {
        return idTheLoai;
    }

    public void setIdTheLoai(int idTheLoai) {
        this.idTheLoai = idTheLoai;
    }

}
