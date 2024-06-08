package com.example.appdoctruyen.Activity;

import static com.example.appdoctruyen.Module.api.RetrofitClient.BASE_URL;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.appdoctruyen.Module.Chapter;
import com.example.appdoctruyen.Module.Story;
import com.example.appdoctruyen.Module.TheLoai;
import com.example.appdoctruyen.Module.api.ApiInterface;
import com.example.appdoctruyen.Module.input.InputTheoDoi;
import com.example.appdoctruyen.Module.output.ApiResponseList_Image;
import com.example.appdoctruyen.Module.output.ApiResponseStoryGenreDetail;
import com.example.appdoctruyen.Module.api.RetrofitClient;
import com.example.appdoctruyen.Module.output.ListChapter;
import com.example.appdoctruyen.adapter.truyentranh.RecyclerView_ListChuong_Adapter;
import com.example.appdoctruyen.databinding.ActivityChitietTruyenBinding;
import com.example.appdoctruyen.utils.DateTimeUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;


import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chitiet_truyenActivity extends AppCompatActivity implements RecyclerView_ListChuong_Adapter.OnItemClickListener {
    private ActivityChitietTruyenBinding bd;
    //  private RecyclerView recyclerView;

    private RecyclerView_ListChuong_Adapter recyclerView_listChuong_adapter;


    private boolean isTextViewExpanded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd = ActivityChitietTruyenBinding.inflate(getLayoutInflater());
        setContentView(bd.getRoot());
       // BottomSheetBehavior<ConstraintLayout> behavior = BottomSheetBehavior.from(bd.bottomSheet);
        // Cho phép BottomSheet bị ẩn
      //  behavior.setHideable(true);

// Đặt trạng thái mặc định là thu gọn khi ban đầu
       // behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        // Nhận Intent từ Fragment trước đó
        Intent intent = getIntent();

        // Lấy giá trị ID được gửi từ Fragment
        int storyId = intent.getIntExtra("id", -1); // -1 là giá trị mặc định nếu không tìm thấy ID
        setEvent(storyId);
       /* bd.rvTheloai.setLayoutManager(new LinearLayoutManager(
                Chitiet_truyenActivity.this,LinearLayoutManager.HORIZONTAL,false));
        bd.rvTheloai.setHasFixedSize(true);
        adapterRV_theloai adapterRVTheloai= new adapterRV_theloai(Chitiet_truyenActivity.this,arrayListTheLoai);
        bd.rvTheloai.setAdapter(adapterRVTheloai);*/
        // Call api
        bd.recyclerviewListChuong.setLayoutManager(new LinearLayoutManager(
                Chitiet_truyenActivity.this, LinearLayoutManager.VERTICAL, false));
        bd.recyclerviewListChuong.setHasFixedSize(true);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            // Xử lý kết quả từ DocTruyenActivity nếu cần
        }
    }


    private void setEvent(int storyId) {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String tokenAuth = sharedPreferences.getString("accessToken", "No Token");
       SharedPreferences sharedPreferences1 = getSharedPreferences("myUser", Context.MODE_PRIVATE);
        int userId = sharedPreferences1.getInt("UserId", -1);
        //trở về
        bd.imgback.setOnClickListener(v -> finish());
        // nút xem thêm
        nutXemThem();
        //hiển thị chi tiết truyện
        getDetailStory(storyId);
        //hiển thị danh sách các chương cuả truyện
        getChapter(storyId);
        // theo doi truyen
        theoDoiTruyen(storyId,userId,tokenAuth);
        // đọc từ đầu
        docTuDau(storyId);


    }

    private void docTuDau(int storyId) {
        bd.btnDoctudau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // lấy id của chapter đầu tiên để xử lí nút đọc từ đầu
                ApiInterface apiInterface = RetrofitClient.getRetrofitInstanceWithoutAuth().create(ApiInterface.class);
                Call<ListChapter> call = apiInterface.getNewChapter(storyId, 1, 100);
                call.enqueue(new retrofit2.Callback<ListChapter>() {

                    @Override
                    public void onResponse(Call<ListChapter> call, Response<ListChapter> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getError() == null) {
                            class ChapterComparator implements Comparator<Chapter> {
                                @Override
                                public int compare(Chapter chapter1, Chapter chapter2) {
                                    // So sánh theo ngày tạo của các chương
                                    return chapter1.getCreatedAt().compareTo(chapter2.getCreatedAt());
                                }
                            }

                            // Sắp xếp danh sách các chương theo ngày tạo
                            List<Chapter> chapters = response.body().getData().getChapters();

                            Collections.sort(chapters, new ChapterComparator());
                             int chapterId = chapters.get(0).getId();
                             String nameChapter= chapters.get(0).getName();
                            Intent intent = new Intent(Chitiet_truyenActivity.this, DocTruyenActivity.class);
                            intent.putExtra("chapterId", chapterId);
                            intent.putExtra("nameChapter", nameChapter);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onFailure(Call<ListChapter> call, Throwable t) {

                    }
                });


            }
        });
    }

    private void theoDoiTruyen(int storyId, int userId, String tokenAuth) {
        bd.btnTheodoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface apiInterface = RetrofitClient.getRetrofitInstanceWithAuth(tokenAuth).create(ApiInterface.class);
                InputTheoDoi inputTheoDoi= new InputTheoDoi(storyId,userId);
                Call<InputTheoDoi> call = apiInterface.theoDoi(inputTheoDoi);
                call.enqueue(new retrofit2.Callback<InputTheoDoi>() {
                    @Override
                    public void onResponse(Call<InputTheoDoi> call, Response<InputTheoDoi> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(Chitiet_truyenActivity.this, "Theo dõi truyện thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Chitiet_truyenActivity.this, "Lỗi response", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<InputTheoDoi> call, Throwable t) {

                    }
                });
            }
        });

    }

    private void getChapter(int storyId) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstanceWithoutAuth().create(ApiInterface.class);
        Call<ListChapter> call = apiInterface.getNewChapter(storyId, 1, 100);
        call.enqueue(new retrofit2.Callback<ListChapter>() {

            @Override
            public void onResponse(Call<ListChapter> call, Response<ListChapter> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getError() == null) {
                    class ChapterComparator implements Comparator<Chapter> {
                        @Override
                        public int compare(Chapter chapter1, Chapter chapter2) {
                            // So sánh theo ngày tạo của các chương
                            return chapter1.getCreatedAt().compareTo(chapter2.getCreatedAt());
                        }
                    }

                    // Sắp xếp danh sách các chương theo ngày tạo
                    List<Chapter> chapters = response.body().getData().getChapters();
                  /*  ArrayList<Chapter> chapterss = new ArrayList<>();
                    chapterss.addAll(chapters);*/
                   // chapters.get(0).getId();
                    Collections.sort(chapters, new ChapterComparator());
                 /*   Intent intent = new Intent(Chitiet_truyenActivity.this, DocTruyenActivity.class);
                    intent.putParcelableArrayListExtra("listChapter", chapterss);
                    startActivityForResult(intent, 1);*/
                    if (!chapters.isEmpty()) {
                        recyclerView_listChuong_adapter = new RecyclerView_ListChuong_Adapter(
                                Chitiet_truyenActivity.this, chapters, Chitiet_truyenActivity.this);
                        bd.recyclerviewListChuong.setAdapter(recyclerView_listChuong_adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListChapter> call, Throwable t) {

            }
        });

    }

    private void getDetailStory(int storyId) {


        // Xử lý dữ liệu nhận được
        if (storyId != -1) {
            // Thực hiện các hành động dựa trên storyId, ví dụ:
            // Hiển thị thông tin của truyện có ID là storyId
            ApiInterface apiInterface = RetrofitClient.getRetrofitInstanceWithoutAuth().create(ApiInterface.class);
            Call<ApiResponseStoryGenreDetail> call = apiInterface.getDetailStory(storyId);
            call.enqueue(new retrofit2.Callback<ApiResponseStoryGenreDetail>() {

                @Override
                public void onResponse(Call<ApiResponseStoryGenreDetail> call, Response<ApiResponseStoryGenreDetail> response) {
                    if (response.isSuccessful()) {
                        ApiResponseStoryGenreDetail apiResponseStoryGenreDetail = response.body();
                        Story story = apiResponseStoryGenreDetail.getData().get(0).getStory();
                        TheLoai genre = apiResponseStoryGenreDetail.getData().get(0).getGenre();
                        bd.tvName.setText(story.getTitle());
                        String timeAgo= DateTimeUtils.getTimeAgo(story.getUpdatedAt());
                        bd.tvNgayCapNhat.setText("Ngày cập nhật: " + timeAgo);
                        String loaiboHTML = story.getDescription();
                        // Parse HTML with Jsoup
                        Document doc = Jsoup.parse(loaiboHTML);
                        // Remove CSS
                        doc.select("style").remove();
                        // Get plain text
                        String plainText = doc.text();
                        bd.tvDescription.setText(plainText);
                        bd.tvTheLoai.setText(genre.getName());

                        // Tải hình ảnh từ URL và thiết lập nó làm nền cho View bằng Glide

                        String imageUrl = BASE_URL + "story-api/" + story.getCoverImageUrl();
                        // Sử dụng Glide để tải hình ảnh và đặt nó làm background của layout
                        Glide.with(getBaseContext())
                                .load(imageUrl)
                                .into(new CustomTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        bd.chiTietTruyenLayout.setBackground(resource);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                        // Xử lý khi cần dọn dẹp hoặc placeholder
                                    }
                                });

                    }

                }

                @Override
                public void onFailure(Call<ApiResponseStoryGenreDetail> call, Throwable t) {

                }
            });
        } else {
            // Xử lý trường hợp không tìm thấy ID, ví dụ:
            // Hiển thị thông báo lỗi
            Toast.makeText(this, "Không tìm thấy ID truyện", Toast.LENGTH_SHORT).show();
        }
    }

    private void nutXemThem() {
        // Kiểm tra xem có cần hiển thị nút "Xem thêm" hay không
        bd.tvDescription.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = bd.tvDescription.getLineCount();

                bd.readMoreTextView.setVisibility(View.VISIBLE);
                bd.tvDescription.setMaxLines(3);
                bd.tvDescription.setEllipsize(TextUtils.TruncateAt.END);

            }
        });

        bd.readMoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextViewExpanded) {
                    bd.tvDescription.setMaxLines(3);
                    bd.tvDescription.setEllipsize(TextUtils.TruncateAt.END);
                    bd.readMoreTextView.setText("Xem thêm");
                } else {
                    bd.tvDescription.setMaxLines(Integer.MAX_VALUE);
                    bd.tvDescription.setEllipsize(null);
                    bd.readMoreTextView.setText("Thu gọn");
                }
                isTextViewExpanded = !isTextViewExpanded;
            }
        });
    }

    @Override
    public void onItemClick(String nameChapter, int chapterId) {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String tokenAuth = sharedPreferences.getString("accessToken", "No Token");
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstanceWithAuth(tokenAuth).create(ApiInterface.class);
        Call<ApiResponseList_Image> call = apiInterface.getChapterImage(chapterId);
        call.enqueue(new retrofit2.Callback<ApiResponseList_Image>() {

            @Override
            public void onResponse(Call<ApiResponseList_Image> call, Response<ApiResponseList_Image> response) {
                if (response.isSuccessful()) {
                    ApiResponseList_Image apiResponseListImage = response.body();
                    // kiểm tra list ảnh xem có null hay khong
                    if (apiResponseListImage.getData().size() == 0) {
                        Toast.makeText(Chitiet_truyenActivity.this, "Hiện không có ảnh nào", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(Chitiet_truyenActivity.this, DocTruyenActivity.class);
                        intent.putExtra("chapterId", chapterId);
                        intent.putExtra("nameChapter", nameChapter);
                        startActivity(intent);
                    }


                } else {
                    Toast.makeText(Chitiet_truyenActivity.this, "Lỗi response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ApiResponseList_Image> call, Throwable t) {

            }
        });

    }
}