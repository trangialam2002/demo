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

public class LapTopActivity extends AppCompatActivity {

    ListView listViewLapTop;
    SanPhamAdapter sanPhamAdapter;
    ArrayList<SanPham> arrayListLapTop=new ArrayList<>();

    ImageButton ibtnBackMT,ibtnSearchMT,ibtnLuaChonMT,ibtnCloseMT,ibtnCartMT;
    int id_TheLoai=-1;

    TextView txttieudeMT;
    EditText txtNhapTenMT;
    ProgressBar progressBarMT;
    ArrayList<SanPham>arrayListCopy=new ArrayList<>();

    ListView listViewLuaChonMT;
    AdapterLuaChonSapXep adapterLuaChonSapXep;
    ArrayList<LuaChonSapXep>luaChonSapXepArrayList=new ArrayList<>();

    DrawerLayout drawerLuaChonMT;
    String orderBy="idSanPham asc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_top);

        addControls();
        addCreate();
        addEvents();
    }

    private void addEvents() {
        ibtnBackMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ibtnSearchMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(DienThoaiActivity.this, "copy="+arrayListCopy.size(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(DienThoaiActivity.this, "dt="+arrayListDienThoai.size(), Toast.LENGTH_SHORT).show();

                txttieudeMT.setVisibility(View.GONE);
                txtNhapTenMT.setVisibility(View.VISIBLE);
                ibtnCloseMT.setVisibility(View.VISIBLE);
                ibtnSearchMT.setVisibility(View.GONE);
            }
        });
        ibtnCloseMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayListLapTop.clear();
                for(SanPham sp:arrayListCopy){
                    arrayListLapTop.add(sp);
                }
                sanPhamAdapter.notifyDataSetChanged();
                txttieudeMT.setVisibility(View.VISIBLE);
                txtNhapTenMT.setVisibility(View.GONE);
                ibtnSearchMT.setVisibility(View.VISIBLE);
                ibtnCloseMT.setVisibility(View.GONE);
            }
        });

        txtNhapTenMT.addTextChangedListener(new TextWatcher() {
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
                arrayListLapTop.clear();
                for(SanPham sp:arrayListCopy){
                    if(sp.getTenSanPham().contains(editable.toString())){
                        arrayListLapTop.add(sp);
                    }
                }
                sanPhamAdapter.notifyDataSetChanged();
            }
        });

        ibtnLuaChonMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLuaChonMT.openDrawer(GravityCompat.START);
            }
        });

        listViewLuaChonMT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        listViewLapTop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SanPham sp=arrayListLapTop.get(i);
                //Toast.makeText(MainActivity.this, sp.getTenSanPham(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(LapTopActivity.this,ChiTietSPActivity.class);
                intent.putExtra("idSanPham",sp.getIdSanPham()+"");
                startActivity(intent);
            }
        });

        //sự kiện click vào icon cart chuyển đến màn hình cartActivity
        ibtnCartMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LapTopActivity.this,CartActivity.class));
            }
        });
    }

    private void getDataRequest() {
        //gửi dữ liệu id thể loại và lấy về dữ liệu các sản phẩm thuộc thể loại đó
        String urlDienThoai= DuongDanRequest.layDanhSachTheoTheLoai;
        RequestQueue queue= Volley.newRequestQueue(LapTopActivity.this);
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                urlDienThoai,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            arrayListLapTop.clear();
                            arrayListCopy.clear();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String idDienThoai=jsonObject.getString("idSanPham");
                                String tenDienThoai=jsonObject.getString("tenSanPham");
                                String donGiaDienThoai=jsonObject.getString("donGia");
                                String imgDienThoai=jsonObject.getString("imgSanPham");
                                String moTaDienThoai=jsonObject.getString("moTa");
                                String idTheLoai=jsonObject.getString("idTheLoai");
                                arrayListLapTop.add(new SanPham(Integer.parseInt(idDienThoai),
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
                LapTopActivity.this,
                R.layout.item_dt_mt_mtb,
                this.arrayListLapTop
        );
        listViewLapTop.setAdapter(sanPhamAdapter);


        Random rd=new Random();

        Toast.makeText(this, "Đang tải dữ liệu", Toast.LENGTH_LONG).show();
        CountDownTimer countDownTimer=new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {
                int n = rd.nextInt(15);
                progressBarMT.setProgress(progressBarMT.getProgress() + n);
            }

            @Override
            public void onFinish() {
                progressBarMT.setProgress(progressBarMT.getMax());
                progressBarMT.setVisibility(View.INVISIBLE);
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
                LapTopActivity.this,
                R.layout.item_listview_lua_chon,
                luaChonSapXepArrayList
        );
        listViewLuaChonMT.setAdapter(adapterLuaChonSapXep);
    }

    private void addControls() {
        listViewLapTop=this.findViewById(R.id.listViewMT);
        ibtnBackMT=this.findViewById(R.id.ibtnBackMT);
        progressBarMT=this.findViewById(R.id.progressBarMT);
        ibtnSearchMT=this.findViewById(R.id.ibtntimkiemMT);
        ibtnLuaChonMT=this.findViewById(R.id.ibtnluachonMT);
        ibtnCloseMT=this.findViewById(R.id.ibtnCloseMT);
        ibtnCartMT=findViewById(R.id.ibtnCartMT);
        listViewLuaChonMT=this.findViewById(R.id.listViewLuaChonMT);
        drawerLuaChonMT=this.findViewById(R.id.drawerLuaChonMT);
        txttieudeMT=this.findViewById(R.id.tieudeMT);
        txtNhapTenMT=this.findViewById(R.id.txtNhapTenMT);
    }

}