package com.example.appdoctruyen.adapter.truyentranh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen.Module.TheLoai;
import com.example.appdoctruyen.R;

import java.util.ArrayList;
import java.util.List;

public class adapterRV_theloai extends RecyclerView.Adapter<adapterRV_theloai.ViewHolder> {

    Context context;
    List<TheLoai> arrayListTheLoai;

    public adapterRV_theloai(Context context, List<TheLoai> arrayListTheLoai) {
        this.context = context;
        this.arrayListTheLoai = arrayListTheLoai;
    }





    @NonNull
    @Override
    public adapterRV_theloai.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_theloai,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterRV_theloai.ViewHolder holder, int position) {
        TheLoai theLoai = arrayListTheLoai.get(position);
        holder.tvTenTheLoai.setText(theLoai.getName());

    }

    @Override
    public int getItemCount() {
        return arrayListTheLoai.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenTheLoai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenTheLoai=itemView.findViewById(R.id.tvTenTheLoai);
        }
    }
}
