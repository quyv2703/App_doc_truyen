package com.example.appdoctruyen.adapter.truyentranh;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen.Module.Chapter;
import com.example.appdoctruyen.Module.api.ApiInterface;
import com.example.appdoctruyen.Module.api.RetrofitClient;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.utils.DateTimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;

public class RecyclerView_ListChuong_Adapter extends RecyclerView.Adapter<RecyclerView_ListChuong_Adapter.ViewHolder> {
    Context context;
    List<Chapter> listChapter;
    private OnItemClickListener clistener;
    public interface OnItemClickListener{
        void onItemClick(String name,int chapterId);
    }
    private ApiInterface apiInterface;

    public RecyclerView_ListChuong_Adapter(Context context, List<Chapter> listChapter,OnItemClickListener clistener) {
        this.context = context;
        this.listChapter = listChapter;
        this.apiInterface=RetrofitClient.getRetrofitInstanceWithoutAuth().create(ApiInterface.class);
        this.clistener=clistener;

    }

    /* public interface OnItemClickListener {
            void onItemClick(int chapterId );
        }*/

    @NonNull
    @Override
    public RecyclerView_ListChuong_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.custom_item_chapter, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView_ListChuong_Adapter.ViewHolder holder, int position) {
        Chapter chapter = listChapter.get(position);
        // tên chapter
        holder.tvChapter.setText(chapter.getName());
        // thời gian
        String isoDateString=chapter.getUpdatedAt();
        String timeAgo = DateTimeUtils.getTimeAgo(isoDateString);
        holder.tvTime.setText(timeAgo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clistener != null) {
                    clistener.onItemClick(chapter.getName(), chapter.getId());
                }
            }
        });


    }



    /*private String getTimeAgo(String isoDateString) {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date = isoFormat.parse(isoDateString);
            Date now = new Date();
            long differenceInMillis = now.getTime() - date.getTime();

            long daysDifference = TimeUnit.MILLISECONDS.toDays(differenceInMillis);
            if (daysDifference > 0) {
                return daysDifference + " ngày trước";
            } else {
                long hoursDifference = TimeUnit.MILLISECONDS.toHours(differenceInMillis);
                if (hoursDifference > 0) {
                    return hoursDifference + " giờ trước";
                } else {
                    long minutesDifference = TimeUnit.MILLISECONDS.toMinutes(differenceInMillis);
                    if (minutesDifference > 0) {
                        return minutesDifference + " phút trước";
                    } else {
                        return "Vừa xong";
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }*/

    @Override
    public int getItemCount() {
        return listChapter.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChapter,tvTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChapter = itemView.findViewById(R.id.tvChapter);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
