package com.example.do_an_android.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.do_an_android.R;
import com.example.do_an_android.model.TheLoai;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuMainAdapter extends ArrayAdapter<TheLoai> {
    Activity context;
    int resource;
    @NonNull List<TheLoai> objects;
    public MenuMainAdapter(@NonNull Activity context, int resource, @NonNull List<TheLoai> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        view=inflater.inflate(this.resource,null);
        TextView txtMenu=view.findViewById(R.id.txtMenu);
        ImageView imgMenu=view.findViewById(R.id.imgMenu);
        TheLoai theLoai=objects.get(position);
        txtMenu.setText(theLoai.getTenTheLoai());
        Picasso.with(context)
                .load(theLoai.getImgTheLoai())
                .into(imgMenu);

        return view;
    }
}
