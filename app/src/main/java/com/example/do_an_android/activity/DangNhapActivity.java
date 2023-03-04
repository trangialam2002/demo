package com.example.do_an_android.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.util.HashMap;
import java.util.Map;

public class DangNhapActivity extends AppCompatActivity {
    EditText txtTaiKhoan,txtMatKhau;
    CheckBox chkLuu;
    ImageButton ibtnLogin;
    TextView txtChuyenDangky;
    //file SharedPreferences lưu thông tin đăng nhập
    String luuThongTinDangNhap="luu_thong_tin";

    //lưu id của khách hàng hiện tại đang đăng nhập vào tài khoản
    public static int idKhachHang=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        addControls();
        addEvents();
    }

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //lấy về dịch vụ internet
            ConnectivityManager manager=
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //nếu có internet
            if(manager.getActiveNetworkInfo()!=null){
                Toast.makeText(context, "Đã có internet", Toast.LENGTH_LONG).show();
                ibtnLogin.setEnabled(true);
                ibtnLogin.setImageResource(R.drawable.ic_login);
            }
            else{
                Toast.makeText(context, "Không có internet", Toast.LENGTH_LONG).show();
                ibtnLogin.setEnabled(false);
                ibtnLogin.setImageResource(R.drawable.ic_login_1);
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
            SharedPreferences preferences=getSharedPreferences(luuThongTinDangNhap,MODE_PRIVATE);
            if(preferences.getBoolean("chkLuu",false)==true){
                chkLuu.setChecked(true);
                txtTaiKhoan.setText(preferences.getString("taiKhoan",""));
                txtMatKhau.setText(preferences.getString("matKhau",""));
            }

        //lắng nghe các sự kiện của hệ thống, lắng nghe sự kiện gì thì lọc sự kiện
        //đó bằng IntentFilter và lọc trong onResume
        //lắng nghe kết nối internet
        IntentFilter filter=new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        //sau đó đăng kí broadcastReceiver
        //sau khi đăng kí receiver này thì phương thức onReceive sẽ tự động
        //thực thi
        registerReceiver(broadcastReceiver,filter);


    }

    private void addEvents() {
        //chuyển sang activity đăng ký tài khoản
        txtChuyenDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangNhapActivity.this, DangKyActivity.class));
                //xét animation cho activity đăng nhập,đăng ký
                overridePendingTransition(R.anim.ani_dangky,R.anim.ani_dangnhap);
            }
        });

        //sự kiện click button đăng nhập
        ibtnLogin.setOnClickListener(new View.OnClickListener() {
            String url= DuongDanRequest.kiemTraDuLieuDangNhapKhachHang;
            RequestQueue queue= Volley.newRequestQueue(DangNhapActivity.this);
            @Override
            public void onClick(View view) {
                String taiKhoan=txtTaiKhoan.getText().toString().trim();
                String matKhau=txtMatKhau.getText().toString().trim();
                if(taiKhoan.equals("")||matKhau.equals("")){
                    Toast.makeText(DangNhapActivity.this, "Nhập thiếu thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringRequest stringRequest=new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("0")){
                                    Toast.makeText(DangNhapActivity.this, "Tài khoản hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else{//đăng nhập thành công
                                    idKhachHang=Integer.parseInt(response);//lưu lại idkhachhang hiện tại
                                    //nếu click lưu thông tin đăng nhập
                                    if(chkLuu.isChecked()){
                                        SharedPreferences preferences=getSharedPreferences(luuThongTinDangNhap,MODE_PRIVATE);
                                        SharedPreferences.Editor editor=preferences.edit();
                                        editor.putString("taiKhoan",taiKhoan);
                                        editor.putString("matKhau",matKhau);
                                        editor.putBoolean("chkLuu",true);
                                        editor.commit();
                                    }
                                    else{
                                        SharedPreferences preferences=getSharedPreferences(luuThongTinDangNhap,MODE_PRIVATE);
                                        SharedPreferences.Editor editor=preferences.edit();
                                        editor.putBoolean("chkLuu",false);
                                        editor.commit();
                                    }
                                    //chuyển hướng đến trang chủ
                                    startActivity(new Intent(DangNhapActivity.this,MainActivity.class));
                                    overridePendingTransition(R.anim.ani_trangchu,R.anim.ani_dangnhap_thanhcong);
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
                        hashMap.put("taiKhoan",taiKhoan);
                        hashMap.put("matKhau",matKhau);
                        return hashMap;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }

    private void addControls() {
        txtTaiKhoan=findViewById(R.id.txtTaiKhoan);
        txtMatKhau=findViewById(R.id.txtMatKhau);
        chkLuu=findViewById(R.id.chkLuu);
        ibtnLogin=findViewById(R.id.ibtnLogin);
        txtChuyenDangky=findViewById(R.id.txtChuyenDangky);
    }
}