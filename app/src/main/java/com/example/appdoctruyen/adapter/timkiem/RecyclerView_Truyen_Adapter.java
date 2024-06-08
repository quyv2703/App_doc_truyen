package com.example.appdoctruyen.adapter.timkiem;

import static com.example.appdoctruyen.Module.api.RetrofitClient.BASE_URL;

import android.content.Context;
import android.util.Log;
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
import com.example.appdoctruyen.adapter.truyentranh.adapterRV_truyentranhmoi;
import com.example.appdoctruyen.utils.DateTimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerView_Truyen_Adapter extends RecyclerView.Adapter<RecyclerView_Truyen_Adapter.ViewHolder> {
    List<Story> listTruyen;
    Context context;
    private OnItemClick mlistener;
    public interface OnItemClick{
        void onItemClick(int position);
    }
    private ApiInterface apiInterface;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
   // private final SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public RecyclerView_Truyen_Adapter(List<Story> listTruyen, Context context,OnItemClick mlistener) {
        this.listTruyen = listTruyen;
        this.context = context;
        this.mlistener=mlistener;
        this.apiInterface = RetrofitClient.getRetrofitInstanceWithoutAuth().create(ApiInterface.class);
    }

    @NonNull
    @Override
    public RecyclerView_Truyen_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_bxh_hot,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView_Truyen_Adapter.ViewHolder holder, int position) {
        Story story=listTruyen.get(position);
        Glide.with(context).load(BASE_URL + "story-api/" +story.getCoverImageUrl()).into(holder.imgOfStory);
        holder.tvName.setText(story.getTitle());
        // lấy thông tin chương mới nhất
        fetchLatestChapter(holder, story.getId());
        // chuyển về dạng 2 ngày trước
        String isoDateString=story.getUpdatedAt();
        Log.d("DateTimeUtils", "isoDateString before calling getTimeAgo: " + isoDateString);
        if (isoDateString == null || isoDateString.isEmpty()) {
            holder.tvTime.setText("Không có thông tin thời gian");
        } else {
            String timeAgo = DateTimeUtils.getTimeAgo(isoDateString);
            Log.d("DateTimeUtils", "timeAgo: " + timeAgo);
            holder.tvTime.setText(timeAgo);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mlistener != null) {
                    mlistener.onItemClick(story.getId());
                }
            }
        });

       // holder.tvLuotXem.setText(story.getViews());
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
        return listTruyen.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgOfStory;
        TextView tvName,tvChapter,tvTime,tvLuotXem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOfStory=itemView.findViewById(R.id.imgOfStory);
            tvName=itemView.findViewById(R.id.tvName);
            tvChapter=itemView.findViewById(R.id.tvChapter);
            tvTime=itemView.findViewById(R.id.tvTime);
            tvLuotXem=itemView.findViewById(R.id.tvLuotXem);
        }
    }
}
