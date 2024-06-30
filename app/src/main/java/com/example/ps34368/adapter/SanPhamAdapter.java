package com.example.ps34368.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps34368.R;
import com.example.ps34368.dao.SanPhamDAO;
import com.example.ps34368.model.SanPham;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<SanPham> list;
    private final SanPhamDAO sanPhamDAO;

    //constructor


    public SanPhamAdapter(Context context, ArrayList<SanPham> list, SanPhamDAO sanPhamDAO) {
        this.context = context;
        this.list = list;
        this.sanPhamDAO = sanPhamDAO;
    }

    @NonNull
    @Override
    public SanPhamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item_recycler,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.ViewHolder holder, int position) {
        //set dữ liệu từng item lên recycler
        holder.tvTenSP.setText(list.get(position).getTensp());
        holder.tvGiaBanSP.setText(String.valueOf(list.get(position).getGiaban()) + " VNĐ -");
        holder.tvSoLuongSP.setText("SL: " + String.valueOf(list.get(position).getSoluong()));


        // Bắt sự kiện icDelete
        holder.icDeleteSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setIcon(R.drawable.ic_warning);
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm '" +
                        list.get(holder.getAdapterPosition()).getTensp() + "' không?");

                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int masp = list.get(holder.getAdapterPosition()).getMasp();
                        boolean check = sanPhamDAO.deleteSanPham(masp);
                        if (check){
                            Toast.makeText(context, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list = sanPhamDAO.getListSanPham();
                            notifyItemRemoved(holder.getAdapterPosition());
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        // Bắt sự kiện icEdit
        holder.icEditSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SanPham sanPham = list.get(holder.getAdapterPosition());
                dialogUpdateSanPham(sanPham);
            }
        });


    }

    private void dialogUpdateSanPham(SanPham sanPhamUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        //Ánh xạ các widget
        TextInputEditText edtTenSPEdit = view.findViewById(R.id.edtTenSPEdit);
        TextInputEditText edtGiaBanEdit = view.findViewById(R.id.edtGiaBanEdit);
        TextInputEditText edtSoLuongEdit = view.findViewById(R.id.edtSoLuongEdit);

        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        //set dữ liệu lên các edt để lấy giá trị cũ của SanPham cần Update
        edtTenSPEdit.setText(sanPhamUpdate.getTensp());
        edtGiaBanEdit.setText(String.valueOf(sanPhamUpdate.getGiaban()));
        edtSoLuongEdit.setText(String.valueOf(sanPhamUpdate.getSoluong()));


        //btnUpdate
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lấy dữ liệu từ các ô nhập trong dialog
                String title = edtTenSPEdit.getText().toString();
                String price = edtGiaBanEdit.getText().toString();
                String quantity = edtSoLuongEdit.getText().toString();

                SanPham sp = new SanPham();
                sp.setMasp(sanPhamUpdate.getMasp());
                sp.setTensp(title);
                sp.setGiaban(Integer.parseInt(price));
                sp.setSoluong(Integer.parseInt(quantity));
                
                boolean check = sanPhamDAO.updateSanPham(sp);
                if (check){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list = sanPhamDAO.getListSanPham();
                    notifyDataSetChanged();
                    //Đống dialog
                    dialog.dismiss();

                }else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //btnCancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //show dialog khi gọi
        dialog.show();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTenSP, tvGiaBanSP, tvSoLuongSP;
        public ImageView imgViewSP, icAddSP, icDeleteSP, icEditSP;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGiaBanSP = itemView.findViewById(R.id.tvGiaBanSP);
            tvSoLuongSP = itemView.findViewById(R.id.tvSoLuongSP);
            imgViewSP = itemView.findViewById(R.id.imgViewSP);
            icAddSP = itemView.findViewById(R.id.icAddSP);
            icDeleteSP = itemView.findViewById(R.id.icDeleteSP);
            icEditSP = itemView.findViewById(R.id.icEditSP);
        }
    }
}
