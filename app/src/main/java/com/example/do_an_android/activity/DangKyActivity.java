package com.example.do_an_android.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.do_an_android.R;
import com.example.do_an_android.model.DuongDanRequest;

import java.util.HashMap;
import java.util.Map;

public class DangKyActivity extends AppCompatActivity {
    EditText txtTenDangKy,txtEmailDangKy,txtMatKhauDangKy,txtDiaChiDangKy;
    ImageButton ibtnXacNhanDangKy;
    TextView txtChuyenDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        addControls();
        addEvents();
    }

    private void addEvents() {
        String url= DuongDanRequest.themDuLieuKhachHang;
        RequestQueue queue= Volley.newRequestQueue(DangKyActivity.this);
        ibtnXacNhanDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count=0;
                String tenDangKy=txtTenDangKy.getText().toString().trim();
                String emailDangKy=txtEmailDangKy.getText().toString().trim();

                StringBuilder str = new StringBuilder(emailDangKy);
                int vt=str.reverse().toString().indexOf(".");//vt=3

                for(int i=0;i<emailDangKy.length();i++){
                    if(emailDangKy.charAt(i)=='.'){// hàm charAt(i)trả về giá trị Char của chuỗi tại vị trí có chỉ số index
                        count++;
                    }
                }
                String matKhauDangKy=txtMatKhauDangKy.getText().toString().trim();
                String diaChiDangKy=txtDiaChiDangKy.getText().toString().trim();
                if(tenDangKy.equals("")||emailDangKy.equals("")||matKhauDangKy.equals("")||diaChiDangKy.equals("")){
                    Toast.makeText(DangKyActivity.this, "Nhập thiếu thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!(emailDangKy.contains("@gmail.com")&&vt==3&&count==1)){
                    Toast.makeText(DangKyActivity.this, "Email chưa đúng định dạng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(matKhauDangKy.length()<6){
                    Toast.makeText(DangKyActivity.this, "Mật khẩu tối thiểu 6 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringRequest stringRequest=new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("1")){
                                    Toast.makeText(DangKyActivity.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else if(response.equals("0")){
                                    Toast.makeText(DangKyActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                                    txtTenDangKy.setText("");
                                    txtTenDangKy.requestFocus();
                                    txtEmailDangKy.setText("");
                                    txtMatKhauDangKy.setText("");
                                    txtDiaChiDangKy.setText("");
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
                        hashMap.put("tenDangKy",tenDangKy);
                        hashMap.put("emailDangKy",emailDangKy);
                        hashMap.put("matKhauDangKy",matKhauDangKy);
                        hashMap.put("diaChiDangKy",diaChiDangKy);
                        return hashMap;
                    }
                };
                queue.add(stringRequest);
            }
        });

        //khi đã có tài khoản thì chuyển sang activity đăng nhập
        txtChuyenDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangKyActivity.this, DangNhapActivity.class));
                //xét animation cho activity đăng nhập,đăng ký
                overridePendingTransition(R.anim.ani_dangky,R.anim.ani_dangnhap);
            }
        });
    }

    private void addControls() {
        txtTenDangKy=findViewById(R.id.txtTenDangKy);
        txtEmailDangKy=findViewById(R.id.txtEmailDangKy);
        txtMatKhauDangKy=findViewById(R.id.txtMatKhauDangKy);
        txtDiaChiDangKy=findViewById(R.id.txtDiaChiDangKy);
        ibtnXacNhanDangKy=findViewById(R.id.ibtnXacNhanDangKy);
        txtChuyenDangNhap=findViewById(R.id.txtChuyenDangNhap);
    }
}