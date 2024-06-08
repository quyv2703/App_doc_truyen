package com.example.appdoctruyen.adapter;

import static com.example.appdoctruyen.Module.api.RetrofitClient.BASE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdoctruyen.Module.ImageChapter;
import com.example.appdoctruyen.R;

import java.util.List;

public class RecyclerView_ImageChapter_Adapter extends RecyclerView.Adapter<RecyclerView_ImageChapter_Adapter.ViewHolder> {
    List<ImageChapter> listImageChapter;
    Context context;

    public RecyclerView_ImageChapter_Adapter(List<ImageChapter> listImageChapter, Context context) {
        this.listImageChapter = listImageChapter;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView_ImageChapter_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_image_chapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView_ImageChapter_Adapter.ViewHolder holder, int position) {
        ImageChapter imageChapter=listImageChapter.get(position);
        Glide.with(context).load(BASE_URL+"story-api/"+imageChapter.getPath()).into(holder.imgChapter);
    }

    @Override
    public int getItemCount() {
        return listImageChapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgChapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgChapter=itemView.findViewById(R.id.imgChapter);
        }
    }
}
