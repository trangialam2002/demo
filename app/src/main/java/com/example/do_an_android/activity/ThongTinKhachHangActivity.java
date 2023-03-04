package com.example.do_an_android.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.do_an_android.R;
import com.example.do_an_android.model.DuongDanRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHangActivity extends AppCompatActivity {
    TextView txtThongTinTen,txtThongTinEmail,txtThongTinMatKhau,txtThongTinDiaChi;
    EditText etxtThongTinTen,etxtThongTinEmail,etxtThongTinMatKhau,etxtThongTinDiaChi;
    ImageButton ibtnEditThongTin,ibtnXacNhanThongTin,ibtnBackThongTin;
    String thongTinTen="";
    String thongTinEmail="";
    String thongTinMatKhau="";
    String thongTinDiaChi="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);

        addControls();
        addCreate();
        addEvents();
    }

    private void addEvents() {
        //sự kiện thoát
        ibtnBackThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //sự kiện sửa thông tin khách hàng
        ibtnEditThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ibtnXacNhanThongTin.setVisibility(View.VISIBLE);
                ibtnEditThongTin.setVisibility(View.GONE);
                txtThongTinTen.setVisibility(View.GONE);
                txtThongTinEmail.setVisibility(View.GONE);
                txtThongTinMatKhau.setVisibility(View.GONE);
                txtThongTinDiaChi.setVisibility(View.GONE);
                etxtThongTinTen.setVisibility(View.VISIBLE);
                etxtThongTinEmail.setVisibility(View.VISIBLE);
                etxtThongTinMatKhau.setVisibility(View.VISIBLE);
                etxtThongTinDiaChi.setVisibility(View.VISIBLE);
                etxtThongTinTen.setText(thongTinTen);
                etxtThongTinEmail.setText(thongTinEmail);
                etxtThongTinMatKhau.setText(thongTinMatKhau);
                etxtThongTinDiaChi.setText(thongTinDiaChi);
            }
        });
        //sự kiện xác nhận cập nhật lại thông tin khách hàng
        ibtnXacNhanThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ibtnXacNhanThongTin.setVisibility(View.GONE);
                ibtnEditThongTin.setVisibility(View.VISIBLE);
                etxtThongTinTen.setVisibility(View.GONE);
                etxtThongTinEmail.setVisibility(View.GONE);
                etxtThongTinMatKhau.setVisibility(View.GONE);
                etxtThongTinDiaChi.setVisibility(View.GONE);
                txtThongTinTen.setVisibility(View.VISIBLE);
                txtThongTinEmail.setVisibility(View.VISIBLE);
                txtThongTinMatKhau.setVisibility(View.VISIBLE);
                txtThongTinDiaChi.setVisibility(View.VISIBLE);

                //cập nhật lại thông tin sau khi sửa
                thongTinTen=etxtThongTinTen.getText().toString().trim();
                thongTinMatKhau=etxtThongTinMatKhau.getText().toString().trim();
                thongTinDiaChi=etxtThongTinDiaChi.getText().toString().trim();
                txtThongTinTen.setText("Tên khách hàng:"+thongTinTen);
                txtThongTinEmail.setText("Email:"+thongTinEmail);
                txtThongTinMatKhau.setText("Mật khẩu:"+thongTinMatKhau);
                txtThongTinDiaChi.setText("Địa chỉ:"+thongTinDiaChi);
                //cập nhật lên database mysql
                String url=DuongDanRequest.capNhatDuLieuKhachHang;
                RequestQueue queue=Volley.newRequestQueue(ThongTinKhachHangActivity.this);
                StringRequest stringRequest=new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(ThongTinKhachHangActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
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
                        hashMap.put("tenCapNhat",thongTinTen);
                        hashMap.put("matKhauCapNhat",thongTinMatKhau);
                        hashMap.put("diaChiCapNhat",thongTinDiaChi);
                        hashMap.put("idKHCapNhat",DangNhapActivity.idKhachHang+"");
                        return hashMap;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }

    private void addCreate() {
        String url= DuongDanRequest.layDuLieuKhachHang;
        RequestQueue queue= Volley.newRequestQueue(ThongTinKhachHangActivity.this);
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            //mảng jsonarray lúc này chỉ có 1 phần tử nên không duyệt for vì chỉ lấy về
                            // 1 tài khoản
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            thongTinTen=jsonObject.getString("tenKhachHang");
                            thongTinEmail=jsonObject.getString("email");
                            thongTinMatKhau=jsonObject.getString("matKhau");
                            thongTinDiaChi=jsonObject.getString("diaChi");
                            txtThongTinTen.setText("Tên khách hàng:"+thongTinTen);
                            txtThongTinEmail.setText("Email:"+thongTinEmail);
                            txtThongTinMatKhau.setText("Mật khẩu:"+thongTinMatKhau);
                            txtThongTinDiaChi.setText("Địa chỉ:"+thongTinDiaChi);
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
                hashMap.put("idKH",DangNhapActivity.idKhachHang+"");
                return hashMap;
            }
        };
        queue.add(stringRequest);
    }

    private void addControls() {
        txtThongTinTen=findViewById(R.id.txtThongTinTen);
        txtThongTinEmail=findViewById(R.id.txtThongTinEmail);
        txtThongTinMatKhau=findViewById(R.id.txtThongTinMatKhau);
        txtThongTinDiaChi=findViewById(R.id.txtThongTinDiaChi);
        etxtThongTinTen=findViewById(R.id.etxtThongTinTen);
        etxtThongTinEmail=findViewById(R.id.etxtThongTinEmail);
        etxtThongTinMatKhau=findViewById(R.id.etxtThongTinMatKhau);
        etxtThongTinDiaChi=findViewById(R.id.etxtThongTinDiaChi);
        ibtnEditThongTin=findViewById(R.id.ibtnEditThongTin);
        ibtnXacNhanThongTin=findViewById(R.id.ibtnXacNhanThongTin);
        ibtnBackThongTin=findViewById(R.id.ibtnBackThongTin);
    }
}