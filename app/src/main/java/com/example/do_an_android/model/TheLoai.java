package com.example.do_an_android.model;

public class TheLoai {
    private int idTheLoai;
    private String tenTheLoai;
    private String imgTheLoai;

    public TheLoai() {
    }

    public TheLoai(int idTheLoai, String tenTheLoai, String imgTheLoai) {
        this.idTheLoai = idTheLoai;
        this.tenTheLoai = tenTheLoai;
        this.imgTheLoai = imgTheLoai;
    }

    public int getIdTheLoai() {
        return idTheLoai;
    }

    public void setIdTheLoai(int idTheLoai) {
        this.idTheLoai = idTheLoai;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    public String getImgTheLoai() {
        return imgTheLoai;
    }

    public void setImgTheLoai(String imgTheLoai) {
        this.imgTheLoai = imgTheLoai;
    }
}
