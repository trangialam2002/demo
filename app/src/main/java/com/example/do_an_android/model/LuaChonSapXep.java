package com.example.do_an_android.model;

public class LuaChonSapXep {
    private String txtSapXep;
    private int iconSapXep;

    public LuaChonSapXep() {
    }

    public LuaChonSapXep(String txtSapXep, int iconSapXep) {
        this.txtSapXep = txtSapXep;
        this.iconSapXep = iconSapXep;
    }

    public String getTxtSapXep() {
        return txtSapXep;
    }

    public void setTxtSapXep(String txtSapXep) {
        this.txtSapXep = txtSapXep;
    }

    public int getIconSapXep() {
        return iconSapXep;
    }

    public void setIconSapXep(int iconSapXep) {
        this.iconSapXep = iconSapXep;
    }
}
