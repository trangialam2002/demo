package com.example.do_an_android.model;

public class Cart {
    private int idGioHang;
    private int idSanPham;
    private String tenSanPham;
    private Double donGia;
    private int soLuong;
    private String img;
    private int idKhachHang;

    public Cart() {
    }

    public Cart(int idGioHang, int idSanPham, String tenSanPham, Double donGia, int soLuong, String img, int idKhachHang) {
        this.idGioHang = idGioHang;
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.img = img;
        this.idKhachHang=idKhachHang;
    }

    public int getIdGioHang() {
        return idGioHang;
    }

    public void setIdGioHang(int idGioHang) {
        this.idGioHang = idGioHang;
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

    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(int idKhachHang) {
        this.idKhachHang = idKhachHang;
    }
}
