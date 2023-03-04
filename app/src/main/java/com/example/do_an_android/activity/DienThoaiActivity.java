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

      /*  //viết sự kiện khi Scroll listview
        listViewDienThoai.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
               //i trả về vị trí của phần tử đầu tiên trong số các phần tử có thể thấy được trên listview
                //i1 là số lượng các phần tử có thể thấy được trên listview
                //i2 là tổng số các phần tử đổ lên listview
                //cú pháp để biết listview đã hiển thị hết dữ liệu chưa đó là i+i1=i2
                //ví dụ có tổng cộng 10 phần tử đổ lên listview(i2=10), bình thường đối với màn hình
                //đt thì có thể thấy 4 phần tử 1 lúc, nếu cuộn thanh scroll đến 1 vị trí mà vị trí của
                //phần tử đầu tiên có thể thấy được là 6(i=6) tương ứng đây là phần tử thứ 7
                // mà có thể thấy 4 phần tử 1 lúc tức là nếu ko tính phần tử thứ 7 này thì còn 3 phần tử cuối
                //vậy 7+3 là 10 phần tử nên xác định được là đến cuối dữ liệu đúng với cú pháp (6+4=10)

                //ban đầu khi chạy litsview lên thì vì chưa scroll nên sẽ không gọi sự kiện này
                //vì vậy i,i1,i2 đều =0, sau đó khi scroll thì sự kiện này mới được gọi đến

//                if(i+i1==i2&&i2>0){
//                    Toast.makeText(DienThoaiActivity.this, "Đã hết dữ liệu", Toast.LENGTH_SHORT).show();
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

      //sự kiện click vào sản phẩm chuyển sang màn hình chi tiết sản phẩm
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

        //sự kiện click vào icon cart chuyển đến màn hình cartActivity
        ibtnCartDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DienThoaiActivity.this,CartActivity.class));
            }
        });
    }

    private void addCreate() {

        //lấy dữ liệu mà intent gửi qua từ MainActivity
        Intent intent=getIntent();
        this.id_TheLoai=intent.getIntExtra("id_TheLoai",-1);

        this.sanPhamAdapter =new SanPhamAdapter(
                DienThoaiActivity.this,
                R.layout.item_dt_mt_mtb,
                this.arrayListDienThoai
        );
        listViewDienThoai.setAdapter(sanPhamAdapter);


        Random rd=new Random();

        Toast.makeText(this, "Đang tải dữ liệu", Toast.LENGTH_LONG).show();
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

        //code cho các lựa chọn sắp xếp
        luaChonSapXepArrayList.add(new LuaChonSapXep("Hiện thị mặc định",R.drawable.ic_arrow_upward));
        luaChonSapXepArrayList.add(new LuaChonSapXep("Hiện thị tên tăng dần",R.drawable.ic_arrow_upward));
        luaChonSapXepArrayList.add(new LuaChonSapXep("Hiện thị tên giảm dần",R.drawable.ic_arrow_downward));
        luaChonSapXepArrayList.add(new LuaChonSapXep("Hiện thị giá tăng dần",R.drawable.ic_arrow_upward));
        luaChonSapXepArrayList.add(new LuaChonSapXep("Hiện thị giá giảm dần",R.drawable.ic_arrow_downward));
        adapterLuaChonSapXep=new AdapterLuaChonSapXep(
                DienThoaiActivity.this,
                R.layout.item_listview_lua_chon,
                luaChonSapXepArrayList
        );
        listViewLuaChonDT.setAdapter(adapterLuaChonSapXep);
    }

    private void getDataRequest() {
        //gửi dữ liệu id thể loại và lấy về dữ liệu các sản phẩm thuộc thể loại đó
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