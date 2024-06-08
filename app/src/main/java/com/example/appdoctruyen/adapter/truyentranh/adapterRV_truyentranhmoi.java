package com.example.appdoctruyen.adapter.truyentranh;

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
import com.example.appdoctruyen.Module.Chapter;
import com.example.appdoctruyen.Module.Story;
import com.example.appdoctruyen.Module.api.ApiInterface;
import com.example.appdoctruyen.Module.api.RetrofitClient;
import com.example.appdoctruyen.Module.output.ListChapter;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.utils.DateTimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Collections;

import java.util.Date;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adapterRV_truyentranhmoi extends RecyclerView.Adapter<adapterRV_truyentranhmoi.ViewHolder> {

    Context context;
    List<Story> arrayListTruyen;
    private OnItemClickListener listener;
    private  ApiInterface apiInterface;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public interface OnItemClickListener {
        void onItemClick(int storyId );
    }

    public adapterRV_truyentranhmoi(Context context, List<Story> arrayListTruyen, OnItemClickListener listener) {
        this.context = context;
        this.arrayListTruyen = arrayListTruyen;
        this.listener = listener;
        this.apiInterface = RetrofitClient.getRetrofitInstanceWithoutAuth().create(ApiInterface.class);
    }


    @NonNull
    @Override
    public adapterRV_truyentranhmoi.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_truyentranhmoi, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterRV_truyentranhmoi.ViewHolder holder, int position) {
        Story truyen = arrayListTruyen.get(position);
        holder.tvName.setText(truyen.getTitle());
        // chuyển sang dạng mấy ngày trước
        String isoDateString=truyen.getUpdatedAt();
        String timeAgo = DateTimeUtils.getTimeAgo(isoDateString);
        holder.tvUpdateAt.setText(timeAgo);
        // ảnh truyện
        Glide.with(context).load(BASE_URL + "story-api/" + truyen.getCoverImageUrl()).into(holder.imgTitle);
        // Gọi API để lấy danh sách chapter và hiển thị chapter mới nhất
        fetchLatestChapter(holder, truyen.getId());

        // lắng nghe sự kiện click vào item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(truyen.getId());
                }
            }
        });
    }



    private void fetchLatestChapter(ViewHolder holder, int storyId) {
        Call<ListChapter> call = apiInterface.getNewChapter(storyId, 1, 100);
        call.enqueue(new Callback<ListChapter>() {
            @Override
            public void onResponse(Call<ListChapter> call, Response<ListChapter> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getError() == null) {
                    List<Chapter> chapters = response.body().getData().getChapters();
                    if (!chapters.isEmpty()) {
                        // Sắp xếp danh sách chương theo updatedAt
                        Collections.sort(chapters, (c1, c2) -> {
                            try {
                                Date date1 = sdf.parse(c1.getUpdatedAt());
                                Date date2 = sdf.parse(c2.getUpdatedAt());
                                return date2.compareTo(date1); // Sắp xếp giảm dần
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return 0;
                            }
                        });
                        // Lấy name của chương mới nhất
                        String latestChapterName = chapters.get(0).getName();

                        holder.tvChapter.setText(latestChapterName);
                    } else {
                        holder.tvChapter.setText("No chapters available");
                    }
                } else {
                    holder.tvChapter.setText("Failed to load chapters");
                }
            }

            @Override
            public void onFailure(Call<ListChapter> call, Throwable t) {
                holder.tvChapter.setText("Failed to load chapters");
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayListTruyen.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvUpdateAt, tvChapter;
        ImageView imgTitle;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imgTitle = itemView.findViewById(R.id.imgTitle);
            tvUpdateAt = itemView.findViewById(R.id.tvUpdateAt);
            tvChapter = itemView.findViewById(R.id.tvChapter);
        }

    }
}
