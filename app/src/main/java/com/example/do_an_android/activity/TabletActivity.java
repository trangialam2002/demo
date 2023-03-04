package com.example.do_an_android.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.example.do_an_android.adapter.AdapterLuaChonSapXep;
import com.example.do_an_android.adapter.SanPhamAdapter;
import com.example.do_an_android.model.DuongDanRequest;
import com.example.do_an_android.model.LuaChonSapXep;
import com.example.do_an_android.model.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TabletActivity extends AppCompatActivity {

    ListView listViewTablet;
    SanPhamAdapter sanPhamAdapter;
    ArrayList<SanPham> arrayListTablet=new ArrayList<>();

    ImageButton ibtnBackMTB,ibtnSearchMTB,ibtnLuaChonMTB,ibtnCloseMTB,ibtnCartMTB;
    int id_TheLoai=-1;

    TextView txttieudeMTB;
    EditText txtNhapTenMTB;
    ProgressBar progressBarMTB;
    ArrayList<SanPham>arrayListCopy=new ArrayList<>();

    ListView listViewLuaChonMTB;
    AdapterLuaChonSapXep adapterLuaChonSapXep;
    ArrayList<LuaChonSapXep>luaChonSapXepArrayList=new ArrayList<>();

    DrawerLayout drawerLuaChonMTB;
    String orderBy="idSanPham asc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);

        addControls();
        addCreate();
        addEvents();
    }

    private void addEvents() {
        ibtnBackMTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        ibtnSearchMTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txttieudeMTB.setVisibility(View.GONE);
                txtNhapTenMTB.setVisibility(View.VISIBLE);
                ibtnCloseMTB.setVisibility(View.VISIBLE);
                ibtnSearchMTB.setVisibility(View.GONE);
            }
        });
        ibtnCloseMTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayListTablet.clear();
                for(SanPham sp:arrayListCopy){
                    arrayListTablet.add(sp);
                }
                sanPhamAdapter.notifyDataSetChanged();
                txttieudeMTB.setVisibility(View.VISIBLE);
                txtNhapTenMTB.setVisibility(View.GONE);
                ibtnSearchMTB.setVisibility(View.VISIBLE);
                ibtnCloseMTB.setVisibility(View.GONE);
            }
        });

        txtNhapTenMTB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //i là trả về vị trí của kí tự vừa nhập
                //charSequence trả về chuỗi vừa nhập
                //Toast.makeText(DienThoaiActivity.this, charSequence.toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Toast.makeText(DienThoaiActivity.this, editable.toString(), Toast.LENGTH_SHORT).show();
                arrayListTablet.clear();
                for(SanPham sp:arrayListCopy){
                    if(sp.getTenSanPham().contains(editable.toString())){
                        arrayListTablet.add(sp);
                    }
                }
                sanPhamAdapter.notifyDataSetChanged();
            }
        });

        ibtnLuaChonMTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLuaChonMTB.openDrawer(GravityCompat.START);
            }
        });

        listViewLuaChonMTB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        orderBy="idSanPham asc";
                        getDataRequest();
                        break;
                    case 1:
                        orderBy="tenSanPham asc";
                        getDataRequest();
                        break;
                    case 2:
                        orderBy="tenSanPham desc";
                        getDataRequest();
                        break;
                    case 3:
                        orderBy="donGia asc";
                        getDataRequest();
                        break;
                    case 4:
                        orderBy="donGia desc";
                        getDataRequest();
                        break;
                    default:
                        break;
                }
            }
        });
        //sự kiện click vào sản phẩm chuyển sang màn hình chi tiết sản phẩm
        listViewTablet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SanPham sp=arrayListTablet.get(i);
                //Toast.makeText(MainActivity.this, sp.getTenSanPham(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(TabletActivity.this,ChiTietSPActivity.class);
                intent.putExtra("idSanPham",sp.getIdSanPham()+"");
                startActivity(intent);
            }
        });

        //sự kiện click vào icon cart chuyển đến màn hình cartActivity
        ibtnCartMTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TabletActivity.this,CartActivity.class));
            }
        });
    }
    private void getDataRequest() {
        //gửi dữ liệu id thể loại và lấy về dữ liệu các sản phẩm thuộc thể loại đó
        String urlMTB= DuongDanRequest.layDanhSachTheoTheLoai;
        RequestQueue queue= Volley.newRequestQueue(TabletActivity.this);
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                urlMTB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            arrayListTablet.clear();
                            arrayListCopy.clear();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String idDienThoai=jsonObject.getString("idSanPham");
                                String tenDienThoai=jsonObject.getString("tenSanPham");
                                String donGiaDienThoai=jsonObject.getString("donGia");
                                String imgDienThoai=jsonObject.getString("imgSanPham");
                                String moTaDienThoai=jsonObject.getString("moTa");
                                String idTheLoai=jsonObject.getString("idTheLoai");
                                arrayListTablet.add(new SanPham(Integer.parseInt(idDienThoai),
                                        tenDienThoai,Double.parseDouble(donGiaDienThoai),
                                        imgDienThoai,moTaDienThoai,Integer.parseInt(idTheLoai)));
                                arrayListCopy.add(new SanPham(Integer.parseInt(idDienThoai),
                                        tenDienThoai,Double.parseDouble(donGiaDienThoai),
                                        imgDienThoai,moTaDienThoai,Integer.parseInt(idTheLoai)));
                                sanPhamAdapter.notifyDataSetChanged();
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
                hashMap.put("id_TheLoai",id_TheLoai+"");
                hashMap.put("order_by",orderBy);
                return hashMap;
            }
        };
        queue.add(stringRequest);
    }


    private void addCreate() {
        //lấy dữ liệu mà intent gửi qua từ MainActivity
        Intent intent=getIntent();
        this.id_TheLoai=intent.getIntExtra("id_TheLoai",-1);

        this.sanPhamAdapter=new SanPhamAdapter(
                TabletActivity.this,
                R.layout.item_dt_mt_mtb,
                this.arrayListTablet
        );
        listViewTablet.setAdapter(sanPhamAdapter);


        Random rd=new Random();

        Toast.makeText(this, "Đang tải dữ liệu", Toast.LENGTH_LONG).show();
        CountDownTimer countDownTimer=new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {
                int n=rd.nextInt(15);
                progressBarMTB.setProgress(progressBarMTB.getProgress()+n);
            }

            @Override
            public void onFinish() {
                progressBarMTB.setProgress(progressBarMTB.getMax());
                progressBarMTB.setVisibility(View.INVISIBLE);

                getDataRequest();
            }
        };
        countDownTimer.start();

        //code cho các lựa chọn sắp xếp
        luaChonSapXepArrayList.add(new LuaChonSapXep("Hiện thị mặc định",R.drawable.ic_arrow_upward));
        luaChonSapXepArrayList.add(new LuaChonSapXep("Hiện thị tên tăng dần",R.drawable.ic_arrow_upward));
        luaChonSapXepArrayList.add(new LuaChonSapXep("Hiện thị tên giảm dần",R.drawable.ic_arrow_downward));
        luaChonSapXepArrayList.add(new LuaChonSapXep("Hiện thị giá tăng dần",R.drawable.ic_arrow_upward));
        luaChonSapXepArrayList.add(new LuaChonSapXep("Hiện thị giá giảm dần",R.drawable.ic_arrow_downward));
        adapterLuaChonSapXep=new AdapterLuaChonSapXep(
                TabletActivity.this,
                R.layout.item_listview_lua_chon,
                luaChonSapXepArrayList
        );
        listViewLuaChonMTB.setAdapter(adapterLuaChonSapXep);
    }

    private void addControls() {
        listViewTablet=this.findViewById(R.id.listViewMTB);
        ibtnBackMTB=this.findViewById(R.id.ibtnBackMTB);
        progressBarMTB=this.findViewById(R.id.progressBarMTB);
        ibtnSearchMTB=this.findViewById(R.id.ibtntimkiemMTB);
        ibtnLuaChonMTB=this.findViewById(R.id.ibtnluachonMTB);
        ibtnCloseMTB=this.findViewById(R.id.ibtnCloseMTB);
        ibtnCartMTB=findViewById(R.id.ibtnCartMTB);
        listViewLuaChonMTB=this.findViewById(R.id.listViewLuaChonMTB);
        drawerLuaChonMTB=this.findViewById(R.id.drawerLuaChonMTB);
        txttieudeMTB=this.findViewById(R.id.tieudeMTB);
        txtNhapTenMTB=this.findViewById(R.id.txtNhapTenMTB);
    }

}