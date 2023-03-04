package com.example.do_an_android.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.do_an_android.R;
import com.example.do_an_android.model.DuongDanRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ChiTietSPActivity extends AppCompatActivity {

    ImageButton ibtnBackChiTietSP,ibtnCart,ibtnGiamSoLuong,ibtnTangSoLuong;
    ImageView imgChiTietSP;
    TextView txtTenChiTietSP,txtGiaChiTietSP,txtMoTaSP;
    EditText txtSoLuong;
    Button btnThemVaoGioHang;

    //format đơn giá sản phẩm
    DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_spactivity);

        addControls();
        addCreate();
        addEvents();
    }
    private void addEvents() {
        ibtnBackChiTietSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ibtnGiamSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuongHienTai=Integer.parseInt(txtSoLuong.getText().toString());
                soLuongHienTai--;
                txtSoLuong.setText(soLuongHienTai+"");
                if(soLuongHienTai==1){
                    ibtnGiamSoLuong.setVisibility(View.INVISIBLE);
                    return;
                }
            }
        });
        ibtnTangSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuongHienTai=Integer.parseInt(txtSoLuong.getText().toString());
                soLuongHienTai++;
                txtSoLuong.setText(soLuongHienTai+"");
                if(soLuongHienTai>1){
                    ibtnGiamSoLuong.setVisibility(View.VISIBLE);
                }
            }
        });

        //sự kiện click vào icon cart chuyển đến màn hình cartActivity
        ibtnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChiTietSPActivity.this,CartActivity.class));
            }
        });

        //sự kiện thêm sản phẩm vào giỏ hàng
        btnThemVaoGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=getIntent();
                String idSanPham=intent.getStringExtra("idSanPham");
                String soLuong=txtSoLuong.getText().toString();
                String url=DuongDanRequest.themDuLieuGioHang;
                RequestQueue queue=Volley.newRequestQueue(ChiTietSPActivity.this);
                StringRequest stringRequest=new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(ChiTietSPActivity.this, response+"số lượng="+response.length(), Toast.LENGTH_SHORT).show();
                                //vì response nhận về có cả kí tự khoảng trắng nên cần trim() để xóa các khoản trắng đầu và cuối
                                if(response.trim().equals("hết hàng")){
                                    Toast.makeText(ChiTietSPActivity.this, "Sản phẩm đã hết hàng", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                else if(response.trim().equals("lớn hơn")){
                                    Toast.makeText(ChiTietSPActivity.this, "Số lượng muốn mua vượt quá số lượng có trong cửa hàng", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                else startActivity(new Intent(ChiTietSPActivity.this,CartActivity.class));

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
                        hashMap.put("id_SP_Add_To_Cart",idSanPham);
                        hashMap.put("soLuong",soLuong);
                        hashMap.put("idKH",DangNhapActivity.idKhachHang+"");
                        return hashMap;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }

    private void addCreate() {
        if(Integer.parseInt(txtSoLuong.getText().toString())==1){
            ibtnGiamSoLuong.setVisibility(View.GONE);
        }
        Intent intent=getIntent();
        String idSanPham=intent.getStringExtra("idSanPham");
        String url= DuongDanRequest.layDuLieuChiTietSanPham;
        RequestQueue queue= Volley.newRequestQueue(ChiTietSPActivity.this);
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String idChiTiet=jsonObject.getString("idSanPham");
                                String tenChiTiet=jsonObject.getString("tenSanPham");
                                String donGiaChiTiet=jsonObject.getString("donGia");
                                String imgChiTiet=jsonObject.getString("imgSanPham");
                                String moTaChiTiet=jsonObject.getString("moTa");
                                String idTheLoaiChiTietSP=jsonObject.getString("idTheLoai");

                                Picasso.with(ChiTietSPActivity.this).load(imgChiTiet)
                                        .into(imgChiTietSP);
                                txtTenChiTietSP.setText(tenChiTiet);
                                txtGiaChiTietSP.setText("Giá: "+decimalFormat.format(Double.parseDouble(donGiaChiTiet))+" VNĐ");
                                txtMoTaSP.setText(moTaChiTiet);
                            }
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
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("idSanPham",idSanPham);
                return hashMap;
            }
        };
        queue.add(stringRequest);
    }
    private void addControls() {
        ibtnBackChiTietSP=findViewById(R.id.ibtnBackChiTietSP);
        ibtnCart=findViewById(R.id.ibtnCartChiTiet);
        ibtnGiamSoLuong=findViewById(R.id.ibtnGiamSoLuong);
        ibtnTangSoLuong=findViewById(R.id.ibtnTangSoLuong);
        imgChiTietSP=findViewById(R.id.imgChiTietSP);
        txtTenChiTietSP=findViewById(R.id.txtTenChiTietSP);
        txtGiaChiTietSP=findViewById(R.id.txtGiaChiTietSP);
        txtMoTaSP=findViewById(R.id.txtMoTaSP);
        txtSoLuong=findViewById(R.id.txtSoLuong);
        btnThemVaoGioHang=findViewById(R.id.btnThemVaoGioHang);
    }
}