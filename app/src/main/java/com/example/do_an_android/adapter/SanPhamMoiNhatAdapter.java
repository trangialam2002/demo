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
import com.example.do_an_android.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class SanPhamMoiNhatAdapter extends ArrayAdapter<SanPham> {
    Activity context;
    int resource;
    @NonNull List<SanPham> objects;

    //format tiền tệ phần nguyên sẽ phân cách bởi dấu , còn phần thập phân là dấu chấm
    //tức là làm tròn đến 3 chữ số thập phân và phần nguyên có 9 chữ số là đến hàng trăm triệu
    //nếu như ở phần nguyên có 5 chữ số(<9 chữ số) thì tự động format 5 chữ số tức là đến hàng chục nghìn
    DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");
    public SanPhamMoiNhatAdapter(@NonNull Activity context, int resource, @NonNull List<SanPham> objects) {
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
        ImageView imgSPNew=view.findViewById(R.id.imgSPNew);
        TextView txtTenSPNew=view.findViewById(R.id.txtTenSPNew);
        TextView txtGiaSPNew=view.findViewById(R.id.txtGiaSPNew);

        SanPham sp=objects.get(position);
        txtTenSPNew.setText(sp.getTenSanPham());
        txtGiaSPNew.setText(decimalFormat.format(sp.getDonGia())+" VNĐ");
        Picasso.with(context).load(sp.getImgSanPham())
                .placeholder(R.drawable.camera_icon)
                .into(imgSPNew);
        return view;
    }
}
