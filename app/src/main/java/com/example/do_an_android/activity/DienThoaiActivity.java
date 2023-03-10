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
import android.view.ContextMenu;
import android.view.MenuInflater;
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

public class DienThoaiActivity extends AppCompatActivity {
    ListView listViewDienThoai;
    SanPhamAdapter sanPhamAdapter;
    ArrayList<SanPham>arrayListDienThoai=new ArrayList<>();

    ImageButton ibtnBackDT,ibtnSearchDT,ibtnLuaChonDT,ibtnCloseDT,ibtnCartDT;
    TextView txttieudeDT;
    EditText txtNhapTenDT;
    int id_TheLoai=-1;

    ProgressBar progressBarDT;

    ArrayList<SanPham>arrayListCopy=new ArrayList<>();

    ListView listViewLuaChonDT;
    AdapterLuaChonSapXep adapterLuaChonSapXep;
    ArrayList<LuaChonSapXep>luaChonSapXepArrayList=new ArrayList<>();

    DrawerLayout drawerLuaChonDT;
    String orderBy="idSanPham asc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);

        addControls();
        addCreate();
        addEvents();
    }


    private void addEvents() {
        ibtnBackDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

      /*  //vi???t s??? ki???n khi Scroll listview
        listViewDienThoai.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
               //i tr??? v??? v??? tr?? c???a ph???n t??? ?????u ti??n trong s??? c??c ph???n t??? c?? th??? th???y ???????c tr??n listview
                //i1 l?? s??? l?????ng c??c ph???n t??? c?? th??? th???y ???????c tr??n listview
                //i2 l?? t???ng s??? c??c ph???n t??? ????? l??n listview
                //c?? ph??p ????? bi???t listview ???? hi???n th??? h???t d??? li???u ch??a ???? l?? i+i1=i2
                //v?? d??? c?? t???ng c???ng 10 ph???n t??? ????? l??n listview(i2=10), b??nh th?????ng ?????i v???i m??n h??nh
                //??t th?? c?? th??? th???y 4 ph???n t??? 1 l??c, n???u cu???n thanh scroll ?????n 1 v??? tr?? m?? v??? tr?? c???a
                //ph???n t??? ?????u ti??n c?? th??? th???y ???????c l?? 6(i=6) t????ng ???ng ????y l?? ph???n t??? th??? 7
                // m?? c?? th??? th???y 4 ph???n t??? 1 l??c t???c l?? n???u ko t??nh ph???n t??? th??? 7 n??y th?? c??n 3 ph???n t??? cu???i
                //v???y 7+3 l?? 10 ph???n t??? n??n x??c ?????nh ???????c l?? ?????n cu???i d??? li???u ????ng v???i c?? ph??p (6+4=10)

                //ban ?????u khi ch???y litsview l??n th?? v?? ch??a scroll n??n s??? kh??ng g???i s??? ki???n n??y
                //v?? v???y i,i1,i2 ?????u =0, sau ???? khi scroll th?? s??? ki???n n??y m???i ???????c g???i ?????n

//                if(i+i1==i2&&i2>0){
//                    Toast.makeText(DienThoaiActivity.this, "???? h???t d??? li???u", Toast.LENGTH_SHORT).show();
//                }
            }
        });*/

        ibtnSearchDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txttieudeDT.setVisibility(View.GONE);
                txtNhapTenDT.setVisibility(View.VISIBLE);
                ibtnCloseDT.setVisibility(View.VISIBLE);
                ibtnSearchDT.setVisibility(View.GONE);
            }
        });

        ibtnCloseDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayListDienThoai.clear();
                for(SanPham sp:arrayListCopy){
                    arrayListDienThoai.add(sp);
                }
                sanPhamAdapter.notifyDataSetChanged();
                txttieudeDT.setVisibility(View.VISIBLE);
                txtNhapTenDT.setVisibility(View.GONE);
                ibtnSearchDT.setVisibility(View.VISIBLE);
                ibtnCloseDT.setVisibility(View.GONE);
            }
        });

      txtNhapTenDT.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              
          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
          }

          @Override
          public void afterTextChanged(Editable editable) {
              arrayListDienThoai.clear();
                          for(SanPham sp:arrayListCopy){
                              if(sp.getTenSanPham().contains(editable.toString())){
                                  arrayListDienThoai.add(sp);
                              }
                          }
              sanPhamAdapter.notifyDataSetChanged();
          }
      });

      ibtnLuaChonDT.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              drawerLuaChonDT.openDrawer(GravityCompat.START);
          }
      });

      listViewLuaChonDT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

      //s??? ki???n click v??o s???n ph???m chuy???n sang m??n h??nh chi ti???t s???n ph???m
      listViewDienThoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              SanPham sp=arrayListDienThoai.get(i);
              //Toast.makeText(MainActivity.this, sp.getTenSanPham(), Toast.LENGTH_SHORT).show();
              Intent intent=new Intent(DienThoaiActivity.this,ChiTietSPActivity.class);
              intent.putExtra("idSanPham",sp.getIdSanPham()+"");
              startActivity(intent);
          }
      });

        //s??? ki???n click v??o icon cart chuy???n ?????n m??n h??nh cartActivity
        ibtnCartDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DienThoaiActivity.this,CartActivity.class));
            }
        });
    }

    private void addCreate() {

        //l???y d??? li???u m?? intent g???i qua t??? MainActivity
        Intent intent=getIntent();
        this.id_TheLoai=intent.getIntExtra("id_TheLoai",-1);

        this.sanPhamAdapter =new SanPhamAdapter(
                DienThoaiActivity.this,
                R.layout.item_dt_mt_mtb,
                this.arrayListDienThoai
        );
        listViewDienThoai.setAdapter(sanPhamAdapter);


        Random rd=new Random();

        Toast.makeText(this, "??ang t???i d??? li???u", Toast.LENGTH_LONG).show();
        CountDownTimer countDownTimer=new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {
                    int n=rd.nextInt(15);
                      progressBarDT.setProgress(progressBarDT.getProgress()+n);
            }

            @Override
            public void onFinish() {
                progressBarDT.setProgress(progressBarDT.getMax());
                progressBarDT.setVisibility(View.INVISIBLE);

                getDataRequest();
            }
        };
        countDownTimer.start();

        //code cho c??c l???a ch???n s???p x???p
        luaChonSapXepArrayList.add(new LuaChonSapXep("Hi???n th??? m???c ?????nh",R.drawable.ic_arrow_upward));
        luaChonSapXepArrayList.add(new LuaChonSapXep("Hi???n th??? t??n t??ng d???n",R.drawable.ic_arrow_upward));
        luaChonSapXepArrayList.add(new LuaChonSapXep("Hi???n th??? t??n gi???m d???n",R.drawable.ic_arrow_downward));
        luaChonSapXepArrayList.add(new LuaChonSapXep("Hi???n th??? gi?? t??ng d???n",R.drawable.ic_arrow_upward));
        luaChonSapXepArrayList.add(new LuaChonSapXep("Hi???n th??? gi?? gi???m d???n",R.drawable.ic_arrow_downward));
        adapterLuaChonSapXep=new AdapterLuaChonSapXep(
                DienThoaiActivity.this,
                R.layout.item_listview_lua_chon,
                luaChonSapXepArrayList
        );
        listViewLuaChonDT.setAdapter(adapterLuaChonSapXep);
    }

    private void getDataRequest() {
        //g???i d??? li???u id th??? lo???i v?? l???y v??? d??? li???u c??c s???n ph???m thu???c th??? lo???i ????
        String urlDienThoai= DuongDanRequest.layDanhSachTheoTheLoai;
        RequestQueue queue= Volley.newRequestQueue(DienThoaiActivity.this);
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                urlDienThoai,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            arrayListDienThoai.clear();
                            arrayListCopy.clear();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                   String idDienThoai=jsonObject.getString("idSanPham");
                                String tenDienThoai=jsonObject.getString("tenSanPham");
                                String donGiaDienThoai=jsonObject.getString("donGia");
                                String imgDienThoai=jsonObject.getString("imgSanPham");
                                String moTaDienThoai=jsonObject.getString("moTa");
                                String idTheLoai=jsonObject.getString("idTheLoai");
                                arrayListDienThoai.add(new SanPham(Integer.parseInt(idDienThoai),
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

    private void addControls() {
        listViewDienThoai=this.findViewById(R.id.listViewSP);
        ibtnBackDT=this.findViewById(R.id.ibtnBackDT);
        progressBarDT=this.findViewById(R.id.progressBarDT);
        ibtnSearchDT=this.findViewById(R.id.ibtntimkiemDT);
        ibtnLuaChonDT=this.findViewById(R.id.ibtnluachonDT);
        ibtnCloseDT=this.findViewById(R.id.ibtnCloseDT);
        ibtnCartDT=findViewById(R.id.ibtnCartDT);
        txttieudeDT=this.findViewById(R.id.tieudeDT);
        txtNhapTenDT=this.findViewById(R.id.txtNhapTenDT);
        listViewLuaChonDT=this.findViewById(R.id.listViewLuaChonDT);
        drawerLuaChonDT=this.findViewById(R.id.drawerLuaChonDT);
    }

}