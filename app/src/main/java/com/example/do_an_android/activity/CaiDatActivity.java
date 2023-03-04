package com.example.do_an_android.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.do_an_android.R;

public class CaiDatActivity extends AppCompatActivity {
    ImageButton ibtnbackCaiDat,ibtnCartCaiDat;
    LinearLayout thongtin,hoadon,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caidat);

        addControls();
        addEvents();
    }

    private void addEvents() {
        //sự kiện logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(CaiDatActivity.this);
                alert.setTitle("Thông báo");
                alert.setIcon(R.drawable.ic_thongbao2);
                alert.setMessage("Xác nhận thoát phần mềm?");
                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(CaiDatActivity.this,DangNhapActivity.class));
                        overridePendingTransition(R.anim.ani_logout2,R.anim.ani_logout1);
                    }
                });
                alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();

            }
        });

        //sự kiện back
        ibtnbackCaiDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //sự kiện mở giỏ hàng
        ibtnCartCaiDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CaiDatActivity.this,CartActivity.class));
            }
        });

        //sự kiện thông tin khách hàng
        thongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CaiDatActivity.this,ThongTinKhachHangActivity.class));
                overridePendingTransition(R.anim.ani_thongtin2,R.anim.ani_thongtin1);
            }
        });
        //sự kiện thông tin hóa đơn
        hoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CaiDatActivity.this,HoaDonActivity.class));
                overridePendingTransition(R.anim.ani_logout2,R.anim.ani_lienhe1);
            }
        });
    }

    private void addControls() {
        ibtnbackCaiDat=findViewById(R.id.ibtnbackCaiDat);
        ibtnCartCaiDat=findViewById(R.id.ibtnCartCaiDat);
        thongtin=findViewById(R.id.thongtin);
        hoadon=findViewById(R.id.hoadon);
        logout=findViewById(R.id.logout);
    }
}