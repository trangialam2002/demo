package com.example.do_an_android.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.do_an_android.R;
import com.example.do_an_android.model.HoaDon;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterHoaDon extends ArrayAdapter<HoaDon> {
    Activity context;
    int resource;
    @NonNull List<HoaDon> objects;

    DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");
    public AdapterHoaDon(@NonNull Activity context, int resource, @NonNull List<HoaDon> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        view=inflater.inflate(resource,null);
        ImageView imgSPHoaDon=view.findViewById(R.id.imgSanPhamHoaDon);
        TextView tenSPHoaDon=view.findViewById(R.id.txtTenSPHoaDon);
        TextView soLuongSPHoaDon=view.findViewById(R.id.txtSoLuongHoaDon);
        TextView donGiaSPHoaDon=view.findViewById(R.id.txtDonGiaHoaDon);
        TextView tongTienSPHoaDon=view.findViewById(R.id.txtThanhTienHoaDon);
        TextView thoiGianSPHoaDon=view.findViewById(R.id.txtThoiGianHoaDon);
        HoaDon hoaDon=this.objects.get(position);
        Picasso.with(context).load(hoaDon.getImg()).into(imgSPHoaDon);
        tenSPHoaDon.setText(hoaDon.getTenSanPham());
        soLuongSPHoaDon.setText("x"+hoaDon.getSoLuong());
        donGiaSPHoaDon.setText(decimalFormat.format(hoaDon.getDonGia())+" VNĐ");
        tongTienSPHoaDon.setText(decimalFormat.format(hoaDon.getSoLuong()*hoaDon.getDonGia())+" VNĐ");
        thoiGianSPHoaDon.setText(hoaDon.getThoiGianThanhToan());
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.ani_in);
        view.startAnimation(animation);
        return view;
    }
}
