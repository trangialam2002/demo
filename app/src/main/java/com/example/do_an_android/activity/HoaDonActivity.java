package com.example.do_an_android.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.do_an_android.R;
import com.example.do_an_android.adapter.AdapterHoaDon;
import com.example.do_an_android.model.DuongDanRequest;
import com.example.do_an_android.model.HoaDon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HoaDonActivity extends AppCompatActivity {
    ImageButton ibtnBackHoaDon;
    TextView txtTongSanPhamHoaDon,txtTongTienHoaDon;

    ArrayList<HoaDon>hoaDonArrayList=new ArrayList<>();
    AdapterHoaDon adapterHoaDon;
    ListView listViewHoaDon;

    DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");
    Double sum=0.0;
    int sumSoLuongSP=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        addControls();
        addCreate();
        addEvents();
    }

    private void addEvents() {
        ibtnBackHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HoaDonActivity.this,CartActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(HoaDonActivity.this,CartActivity.class));
    }

    private void addCreate() {
        int idKH=DangNhapActivity.idKhachHang;
        String url= DuongDanRequest.layDuLieuHoaDon;
        RequestQueue queue= Volley.newRequestQueue(HoaDonActivity.this);
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            hoaDonArrayList.clear();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                HoaDon hoaDon=new HoaDon();
                                hoaDon.setIdHoaDon(Integer.parseInt(jsonObject.getString("idHoaDon")));
                                hoaDon.setIdSanPham(Integer.parseInt(jsonObject.getString("idSanPham")));
                                hoaDon.setTenSanPham(jsonObject.getString("tenSanPham"));
                                hoaDon.setSoLuong(Integer.parseInt(jsonObject.getString("soLuong")));
                                hoaDon.setDonGia(Double.parseDouble(jsonObject.getString("donGia")));
                                hoaDon.setImg(jsonObject.getString("img"));
                                hoaDon.setTongTien(Double.parseDouble(jsonObject.getString("tongTien")));
                                sum+=Double.parseDouble(jsonObject.getString("tongTien"));
                                sumSoLuongSP++;
                                hoaDon.setIdKhachHang(Integer.parseInt(jsonObject.getString("idKhachHang")));
                                hoaDon.setThoiGianThanhToan(jsonObject.getString("thoiGianThanhToan"));
                                hoaDonArrayList.add(hoaDon);
                            }
                            adapterHoaDon=new AdapterHoaDon(
                                    HoaDonActivity.this,
                                    R.layout.item_hoadon,
                                    hoaDonArrayList
                            );
                            listViewHoaDon.setAdapter(adapterHoaDon);
                            txtTongTienHoaDon.setText(decimalFormat.format(sum)+" VNĐ");
                            txtTongSanPhamHoaDon.setText(sumSoLuongSP+" sản phẩm");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String>hashMap=new HashMap<>();
                hashMap.put("idKH",idKH+"");
                return hashMap;
            }
        };
        queue.add(stringRequest);
    }

    private void addControls() {
        ibtnBackHoaDon=findViewById(R.id.ibtnBackHoaDon);
        txtTongSanPhamHoaDon=findViewById(R.id.txtTongSanPhamHoaDon);
        txtTongTienHoaDon=findViewById(R.id.txtTongTienHoaDon);
        listViewHoaDon=findViewById(R.id.listViewHoaDon);
    }
}