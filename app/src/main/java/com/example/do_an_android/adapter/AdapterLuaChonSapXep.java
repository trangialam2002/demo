package com.example.do_an_android.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.do_an_android.R;
import com.example.do_an_android.model.LuaChonSapXep;

import java.util.List;

public class AdapterLuaChonSapXep extends ArrayAdapter<LuaChonSapXep> {
    Activity context;
    int resource;
    @NonNull List<LuaChonSapXep> objects;
    public AdapterLuaChonSapXep(@NonNull Activity context, int resource, @NonNull List<LuaChonSapXep> objects) {
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
        ImageView img=view.findViewById(R.id.img_lua_chon_sap_xep);
        TextView txt=view.findViewById(R.id.txt_ten_lua_chon_sap_xep);
        LuaChonSapXep sapXep=objects.get(position);
        img.setImageResource(sapXep.getIconSapXep());
        txt.setText(sapXep.getTxtSapXep());
        return view;
    }
}
