package com.example.do_an_android.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.do_an_android.R;
import com.example.do_an_android.activity.CartActivity;
import com.example.do_an_android.activity.DangNhapActivity;
import com.example.do_an_android.model.Cart;
import com.example.do_an_android.model.DuongDanRequest;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterCart extends ArrayAdapter<Cart> {
    Activity context;
    int resource;
    @NonNull List<Cart> objects;
    DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");

    ArrayList<Integer> arrayListId=new ArrayList<>();
    ArrayList<Integer>arrayListSoLuong=new ArrayList<>();


    public AdapterCart(@NonNull Activity context, int resource, @NonNull List<Cart> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        view=inflater.inflate(this.resource,null);
        ImageView imgCart=view.findViewById(R.id.imgCart);
        TextView txtTenCart=view.findViewById(R.id.txtTenCart);
        TextView txtGiaCart=view.findViewById(R.id.txtGiaCart);
        ImageButton ibtnGiamCart=view.findViewById(R.id.ibtnGiamCart);
        ImageButton ibtnTangCart=view.findViewById(R.id.ibtnTangCart);
        ImageButton ibtnDeleteCart=view.findViewById(R.id.ibtnDeleteCart);
        EditText txtSoLuongCart=view.findViewById(R.id.txtSoLuongCart);
        Cart cart=this.objects.get(position);
        arrayListId.add(position,cart.getIdSanPham());
        arrayListSoLuong.add(position,cart.getSoLuong());
        Picasso.with(this.context).load(cart.getImg()).into(imgCart);
        txtTenCart.setText(cart.getTenSanPham());
        txtGiaCart.setText(decimalFormat.format(cart.getDonGia())+" VNĐ");
        txtSoLuongCart.setText(cart.getSoLuong()+"");

        if(Integer.parseInt(txtSoLuongCart.getText().toString())==1){
            ibtnGiamCart.setVisibility(View.GONE);
        }

        //sự kiện cho các button cộng trừ
        ibtnGiamCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuongHienTai=Integer.parseInt(txtSoLuongCart.getText().toString());
                soLuongHienTai--;
                txtSoLuongCart.setText(soLuongHienTai+"");
                if(soLuongHienTai==1){
                    ibtnGiamCart.setVisibility(View.GONE);
                }

                //position cũng chính là vị trí của các phần tử trong arrayListSoLuong
                //position là vị trí của phần tử trong listview cart vậy tương ứng mỗi phần tử là 1 số lượng
                //được lưu trong arrayListSoLuong
                //thay thế số lượng khi click button cộng/trừ cho số lượng ban đầu
                arrayListSoLuong.set(position,soLuongHienTai);
                CartActivity.sum-=cart.getDonGia();
                capNhatSoLuong(position);
            }
        });

        ibtnTangCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuongHienTai=Integer.parseInt(txtSoLuongCart.getText().toString());
                soLuongHienTai++;
                txtSoLuongCart.setText(soLuongHienTai+"");
                if(soLuongHienTai==2){
                    ibtnGiamCart.setVisibility(View.VISIBLE);
                }
                arrayListSoLuong.set(position,Integer.parseInt(txtSoLuongCart.getText().toString()));
                CartActivity.sum+=cart.getDonGia();
                capNhatSoLuong(position);
            }
        });
        //sự kiện click button xóa sản phẩm khỏi giỏ hàng
        ibtnDeleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idSPDelete=cart.getIdSanPham();
                Double giaSPDelete=cart.getDonGia();
                int soLuongSPDelete=Integer.parseInt(txtSoLuongCart.getText().toString());
                Double tongTienDelete=giaSPDelete*soLuongSPDelete;
                AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setTitle("Thông báo");
                alert.setIcon(R.drawable.ic_thongbao);
                alert.setMessage("Bạn có muốn xóa sản phẩm "+cart.getTenSanPham()+" không?");
                String url=DuongDanRequest.xoaDuLieuGioHang;
                RequestQueue queue=Volley.newRequestQueue(context);
                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //xóa sản phẩm khỏi bảng giỏ hàng
                        StringRequest stringRequest=new StringRequest(
                                Request.Method.POST,
                                url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        //xóa sản phẩm khỏi arrlistCart
                                        CartActivity.cartArrayList.remove(position);
                                        //cập nhật lại giao diện
                                        CartActivity.adapterCart.notifyDataSetChanged();

                                        CartActivity.sum-=tongTienDelete;
                                        CartActivity.txtTongTien.setText("Tổng Tiền: "+decimalFormat.format(CartActivity.sum)+" VNĐ");
                                        if(CartActivity.cartArrayList.size()==0){
                                            CartActivity.container_empty_cart.setVisibility(View.VISIBLE);
                                            CartActivity.listViewCart.setVisibility(View.GONE);
                                            CartActivity.txtTongTien.setText("Tổng Tiền: 0 VNĐ");
                                            CartActivity.btnThanhToanGioHang.setEnabled(false);
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
                                hashMap.put("idSPDelete",idSPDelete+"");
                                hashMap.put("idKH",DangNhapActivity.idKhachHang+"");
                                return hashMap;
                            }
                        };
                        queue.add(stringRequest);
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
        return view;
    }

    private void capNhatSoLuong(int position) {
        String url= DuongDanRequest.capNhatDuLieuGioHang;
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      CartActivity.txtTongTien.setText("Tổng Tiền: "+decimalFormat.format(CartActivity.sum)+" VNĐ");
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
                hashMap.put("idSanPham",objects.get(position).getIdSanPham()+"");
                hashMap.put("soLuongCapNhat",arrayListSoLuong.get(position)+"");
                hashMap.put("idKH", DangNhapActivity.idKhachHang+"");
                return hashMap;
            }
        };
        queue.add(stringRequest);
    }


}
