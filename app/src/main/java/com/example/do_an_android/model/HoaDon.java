package com.example.do_an_android.model;

public class HoaDon {
    private int idHoaDon;
    private int idSanPham;
    private String tenSanPham;
    private int soLuong;
    private Double donGia;
    private String img;
    private Double tongTien;
    private int idKhachHang;
    private String thoiGianThanhToan;

    public HoaDon() {
    }

    public HoaDon(int idHoaDon, int idSanPham, String tenSanPham, int soLuong, Double donGia, String img, Double tongTien, int idKhachHang, String thoiGianThanhToan) {
        this.idHoaDon = idHoaDon;
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.img = img;
        this.tongTien = tongTien;
        this.idKhachHang = idKhachHang;
        this.thoiGianThanhToan = thoiGianThanhToan;
    }

    public int getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(int idHoaDon) {
        this.idHoaDon = idHoaDon;
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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Double getTongTien() {
        return tongTien;
    }

    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }

    public int getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(int idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getThoiGianThanhToan() {
        return thoiGianThanhToan;
    }

    public void setThoiGianThanhToan(String thoiGianThanhToan) {
        this.thoiGianThanhToan = thoiGianThanhToan;
    }
}
