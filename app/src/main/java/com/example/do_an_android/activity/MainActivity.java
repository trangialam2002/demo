package com.example.do_an_android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.do_an_android.adapter.MenuMainAdapter;
import com.example.do_an_android.R;
import com.example.do_an_android.adapter.SanPhamMoiNhatAdapter;
import com.example.do_an_android.model.DuongDanRequest;
import com.example.do_an_android.model.SanPham;
import com.example.do_an_android.model.TheLoai;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageButton ibtnMenu,ibtnCartHome;
    ViewFlipper viewFlipper;
    DrawerLayout drawerLayout;

    //thiết lập cho menu main
    ListView listViewMenuMain;
    MenuMainAdapter adapter;
    ArrayList<TheLoai>arrayList=new ArrayList<>();

    //thiết lập cho sản phẩm mới nhất
    GridView gridViewSPNew;
    SanPhamMoiNhatAdapter sanPhamMoiNhatAdapter;
    ArrayList<SanPham>sanPhamArrayList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addCreate();
        addEvents();
    }

    private void addEvents() {
        Configuration config=getResources().getConfiguration();
        //cần phải xác định khi màn hình dọc thì mới xét thanh menu ngang, vì màn hình ngang thì
        //không cần nên nếu không xét thì ở màn hình ngang bị lỗi
        if(config.orientation==Configuration.ORIENTATION_PORTRAIT){
            ibtnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }



        //viết sự kiện cho item trong thanh menu main
        listViewMenuMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //i là vị trí của phần tử trong listview mà mỗi phần tử chính là
                //1 đối tượng thể loại
                if (i == 0) {//trang chủ
                    finish();
                    //đóng thanh menu main lại, lúc này cũng chỉ xét đóng thanh menu ngang khi
                    //màn hình dọc
                    if(config.orientation==Configuration.ORIENTATION_PORTRAIT){
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    startActivity(new Intent(MainActivity.this, MainActivity.class));

                } else if (i == 1) {//điện thoại
                    Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                    intent.putExtra("id_TheLoai", arrayList.get(i).getIdTheLoai());
                    startActivity(intent);

                    //đóng thanh menu main lại
                    if(config.orientation==Configuration.ORIENTATION_PORTRAIT){
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                } else if (i == 2) {//máy tính
                    Intent intent = new Intent(MainActivity.this, LapTopActivity.class);
                    intent.putExtra("id_TheLoai", arrayList.get(i).getIdTheLoai());
                    startActivity(intent);

                    //đóng thanh menu main lại
                    if(config.orientation==Configuration.ORIENTATION_PORTRAIT){
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }

                } else if (i == 3) {//máy tính bảng
                    Intent intent = new Intent(MainActivity.this, TabletActivity.class);
                    intent.putExtra("id_TheLoai", arrayList.get(i).getIdTheLoai());
                    startActivity(intent);

                    //đóng thanh menu main lại
                    if(config.orientation==Configuration.ORIENTATION_PORTRAIT){
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }

                } else if (i == 4) {//liên hệ
                    startActivity(new Intent(MainActivity.this,LienHeActivity.class));
                    overridePendingTransition(R.anim.ani_lienhe2,R.anim.ani_lienhe1);
                } else if (i == 5) {//cài đặt
                    startActivity(new Intent(MainActivity.this,CaiDatActivity.class));
                }
            }
        });

        //viết sự kiện khi click vào 1 item của gridview thì chuyển đến màn hình
        //chi tiết sản phẩm đó
        gridViewSPNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SanPham sp=sanPhamArrayList.get(i);
                //Toast.makeText(MainActivity.this, sp.getTenSanPham(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,ChiTietSPActivity.class);
                intent.putExtra("idSanPham",sp.getIdSanPham()+"");
                startActivity(intent);
            }
        });

        //sự kiện click vào icon cart chuyển đến màn hình cartActivity
        ibtnCartHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CartActivity.class));
            }
        });
    }

    private void addCreate() {
        //khởi tạo slider
        Integer[]arr=new Integer[4];
        arr[0]=R.drawable.slider1;
        arr[1]=R.drawable.slider2;
        arr[2]=R.drawable.slider3;
        arr[3]=R.drawable.slider4;
        for(int i=0;i<arr.length;i++){
            ImageView img=new ImageView(MainActivity.this);
            img.setImageResource(arr[i]);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(img);
        }
        Animation in= AnimationUtils.loadAnimation(MainActivity.this,R.anim.ani_in);
        viewFlipper.setInAnimation(in);
        Animation out=AnimationUtils.loadAnimation(MainActivity.this,R.anim.ani_out);
        viewFlipper.setOutAnimation(out);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        //code dữ liệu cho thanh menu

        //lấy dữ liệu request api cho thể loại
        String urlTheLoai= DuongDanRequest.layDuLieuTheLoai;
        RequestQueue queue =Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                urlTheLoai,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        arrayList.add(new TheLoai(0,"Trang Chủ","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_h6-5ce6Zk02Cgs5eesdzkUGvlQcz7-NqTA&usqp=CAU"));
                        for(int i=0;i<response.length();i++){
                            try {
                                JSONObject jsonObject=response.getJSONObject(i);
                                String idTheLoai=jsonObject.getString("idTheLoai");
                                String tenTheLoai=jsonObject.getString("tenTheLoai");
                                String imgTheLoai=jsonObject.getString("imgTheLoai");
                                arrayList.add(new TheLoai(Integer.parseInt(idTheLoai),tenTheLoai,imgTheLoai));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        arrayList.add(new TheLoai(0,"Liên Hệ","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5857nIJty7pY4vBmpHME90uoKaDPTsOLSxA&usqp=CAU"));
                        arrayList.add(new TheLoai(0,"Cài Đặt","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGekT3TToiENnzzklyjXpvdBPrZ2MN8VOcmhxyuEzJz0uyZCTO5EMBw5mCqBxJ3q7rjxc&usqp=CAU"));
                        adapter=new MenuMainAdapter(
                                MainActivity.this,
                                R.layout.item_list_menu,
                                arrayList
                        );
                        listViewMenuMain.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "lỗi1:"+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        queue.add(jsonArrayRequest);



        //code dữ liệu cho sản phẩm mới nhất
        //lấy sản phẩm mới nhất từ request
        String urlSPNew=DuongDanRequest.layDuLieuSanPhamMoiNhat;
        JsonArrayRequest jsonArrayRequest1=new JsonArrayRequest(
                Request.Method.GET,
                urlSPNew,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0;i<response.length();i++){
                            try {
                                JSONObject jsonObject=response.getJSONObject(i);
                                String idSPNew=jsonObject.getString("idSanPham");
                                String tenSPNew=jsonObject.getString("tenSanPham");
                                String donGia=jsonObject.getString("donGia");
                                String imgSPNew=jsonObject.getString("imgSanPham");
                                String moTa=jsonObject.getString("moTa");
                                String idTheLoai=jsonObject.getString("idTheLoai");
                                sanPhamArrayList.add(new SanPham(Integer.parseInt(idSPNew),tenSPNew,
                                        Double.parseDouble(donGia),imgSPNew,moTa,Integer.parseInt(idTheLoai)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        sanPhamMoiNhatAdapter=new SanPhamMoiNhatAdapter(
                                MainActivity.this,
                                R.layout.item_san_pham_moi_nhat,
                                sanPhamArrayList
                        );
                        gridViewSPNew.setAdapter(sanPhamMoiNhatAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "lỗi 2:"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(jsonArrayRequest1);

    }




    private void addControls() {
        ibtnMenu=this.findViewById(R.id.ibtnMenu);
        ibtnCartHome=findViewById(R.id.ibtnCartHome);
        viewFlipper=this.findViewById(R.id.viewFlipper);
        gridViewSPNew=this.findViewById(R.id.gridViewSPNew);
        drawerLayout=this.findViewById(R.id.drawer);
        listViewMenuMain=this.findViewById(R.id.listViewMenuMain);
    }



}