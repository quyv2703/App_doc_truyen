package com.example.appdoctruyen.adapter.tusach;

import static com.example.appdoctruyen.Module.api.RetrofitClient.BASE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdoctruyen.Module.Interface.ItemClick;
import com.example.appdoctruyen.Module.Story;
import com.example.appdoctruyen.Module.output.ApiResponseListStoryByFollow;
import com.example.appdoctruyen.Module.output.OutputDangKi;
import com.example.appdoctruyen.Module.output.OutputGetListStoryByFollow;
import com.example.appdoctruyen.R;

import java.util.List;

public class RecyclerViewAdapter_TheoDoi extends RecyclerView.Adapter<RecyclerViewAdapter_TheoDoi.ViewHolder>{
    List<OutputGetListStoryByFollow> listStory;
    Context context;
    private ItemClick itemClick;

    public RecyclerViewAdapter_TheoDoi(List<OutputGetListStoryByFollow> listStory, Context context, ItemClick itemClick) {
        this.listStory = listStory;
        this.context = context;
        this.itemClick=itemClick ;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_TheoDoi.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_theodoi,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_TheoDoi.ViewHolder holder, int position) {
        OutputGetListStoryByFollow story = listStory.get(position);
        holder.tvName.setText(story.getStory().getTitle());
        Glide.with(context).load(BASE_URL+"story-api/"+story.getStory().getCoverImageUrl()).into(holder.imgOfStory);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClick != null) {
                    itemClick.onClick(story.getStory().getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listStory.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgOfStory;
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOfStory = itemView.findViewById(R.id.imgOfStory);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
