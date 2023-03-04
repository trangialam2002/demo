package com.example.do_an_android.adapter;

import android.app.Activity;
import android.text.TextUtils;
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

public class SanPhamAdapter extends ArrayAdapter<SanPham> {
    Activity context;
    int resource;
    @NonNull List<SanPham> objects;

    DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");
    public SanPhamAdapter(@NonNull Activity context, int resource, @NonNull List<SanPham> objects) {
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
        TextView tenSanPham=view.findViewById(R.id.txtTenDT_MT_MTB);
        TextView giaSanPham=view.findViewById(R.id.txtGiaDT_MT_MTB);
        TextView moTaSanPham=view.findViewById(R.id.txtMoTaDT_MT_MTB);
        ImageView imgSanPham=view.findViewById(R.id.imgDT_MT_MTB);
        SanPham sp=objects.get(position);
        tenSanPham.setText(sp.getTenSanPham());
        giaSanPham.setText(decimalFormat.format(sp.getDonGia()));

        //thiết lập giới hạn số dòng cho textview mô tả
        moTaSanPham.setMaxLines(2);
        //thiết lập dấu ... cuối câu
        moTaSanPham.setEllipsize(TextUtils.TruncateAt.END);
        moTaSanPham.setText(sp.getMoTa());

        Picasso.with(this.context).load(sp.getImgSanPham()).placeholder(R.drawable.camera_icon).into(imgSanPham);
        Animation animation=AnimationUtils.loadAnimation(context,R.anim.ani_sanpham);
        view.startAnimation(animation);
        return view;
    }
}
