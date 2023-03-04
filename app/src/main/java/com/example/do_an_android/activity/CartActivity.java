package com.example.do_an_android.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.do_an_android.R;
import com.example.do_an_android.adapter.AdapterCart;
import com.example.do_an_android.model.Cart;
import com.example.do_an_android.model.DuongDanRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    public static LinearLayout container_empty_cart;
    ImageButton ibtnBackCart;

    public static ListView listViewCart;
    public static AdapterCart adapterCart;
    public static ArrayList<Cart>cartArrayList=new ArrayList<>();

    public static TextView txtTongTien;
    public static Button btnThanhToanGioHang,btnMuaHang;

    public static Double sum=0.0;
    DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        addControls();
        addCreate();
        addEvents();
    }

    private void addEvents() {
        ibtnBackCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                sum=0.0;
            }
        });

        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this,MainActivity.class));
            }
        });
        //sự kiện thanh toán giỏ hàng và chuyển sang hóa đơn
        btnThanhToanGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lấy tất cả dữ liệu giỏ hàng ứng với idkhachhang để đưa sang hóa đơn đồng thời
                //xóa các sản phẩm vừa thanh toán khỏi giỏ hàng
                int idKH=DangNhapActivity.idKhachHang;
                String url=DuongDanRequest.capNhatDuLieuHoaDon;
                RequestQueue queue=Volley.newRequestQueue(CartActivity.this);
                StringRequest stringRequest=new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(CartActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
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
                        hashMap.put("idKHCurrent",idKH+"");
                        return hashMap;
                    }
                };
                queue.add(stringRequest);

                startActivity(new Intent(CartActivity.this,HoaDonActivity.class));
                overridePendingTransition(R.anim.ani_logout2,R.anim.ani_lienhe1);
            }
        });
    }

    private void addCreate() {
        String url= DuongDanRequest.layDuLieuGioHang;
        RequestQueue queue= Volley.newRequestQueue(CartActivity.this);
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cartArrayList.clear();
                        sum=0.0;
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String idCart=jsonObject.getString("idGioHang");
                                    String idSP=jsonObject.getString("idSanPham");
                                    String tenSP=jsonObject.getString("tenSanPham");
                                    Double donGia=Double.parseDouble(jsonObject.getString("donGia"));
                                    String imgSP=jsonObject.getString("img");
                                    int soLuong=Integer.parseInt(jsonObject.getString("soLuong"));
                                    int idKhachHang=Integer.parseInt(jsonObject.getString("idKhachHang"));
                                    sum+=donGia*soLuong;

                                    cartArrayList.add(new Cart(Integer.parseInt(idCart),Integer.parseInt(idSP),
                                            tenSP,donGia,soLuong,imgSP,idKhachHang));
                            }
                            if(cartArrayList.size()==0){
                                container_empty_cart.setVisibility(View.VISIBLE);
                                listViewCart.setVisibility(View.GONE);
                                txtTongTien.setText("Tổng Tiền: 0 VNĐ");
                                btnThanhToanGioHang.setEnabled(false);
                            }
                            else{
                                btnThanhToanGioHang.setEnabled(true);
                                container_empty_cart.setVisibility(View.GONE);
                                listViewCart.setVisibility(View.VISIBLE);
                                adapterCart=new AdapterCart(
                                        CartActivity.this,
                                        R.layout.item_listview_cart,
                                        cartArrayList
                                );
                                listViewCart.setAdapter(adapterCart);
                                txtTongTien.setText("Tổng Tiền: "+decimalFormat.format(sum)+" VNĐ");
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
                HashMap<String,String>hashMap=new HashMap<>();
                hashMap.put("idKH",DangNhapActivity.idKhachHang+"");
                return hashMap;
            }
        };
        queue.add(stringRequest);
    }

    private void addControls() {
        container_empty_cart=findViewById(R.id.container_empty_cart);
        listViewCart=findViewById(R.id.listViewCart);
        txtTongTien=findViewById(R.id.txtTongTien);
        btnThanhToanGioHang=findViewById(R.id.btnThanhToanGioHang);
        btnMuaHang=findViewById(R.id.btnMuaHang);
        ibtnBackCart=findViewById(R.id.ibtnBackCart);
    }
}