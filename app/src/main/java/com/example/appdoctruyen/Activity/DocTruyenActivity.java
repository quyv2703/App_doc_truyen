package com.example.appdoctruyen.Activity;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen.Module.Chapter;
import com.example.appdoctruyen.Module.ImageChapter;
import com.example.appdoctruyen.Module.api.ApiInterface;
import com.example.appdoctruyen.Module.api.RetrofitClient;
import com.example.appdoctruyen.Module.output.ApiResponseList_Image;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.adapter.RecyclerView_ImageChapter_Adapter;
import com.example.appdoctruyen.adapter.truyentranh.RecyclerView_ListChuong_Adapter;
import com.example.appdoctruyen.databinding.ActivityDocTruyenBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DocTruyenActivity extends AppCompatActivity implements RecyclerView_ListChuong_Adapter.OnItemClickListener {
    private ActivityDocTruyenBinding bd;
    RecyclerView_ImageChapter_Adapter adapter;
    List<ImageChapter> listImageChapter;
    private boolean isAppBarVisible = true;
    private boolean isBottomBarVisible = true;
    private BottomSheetBehavior<View> bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd = ActivityDocTruyenBinding.inflate(getLayoutInflater());
        setContentView(bd.getRoot());
        bd.recyclerViewDocTruyen.setHasFixedSize(true);
        bd.recyclerViewDocTruyen.setLayoutManager(new LinearLayoutManager(this));
        setEVent();

    }

    private void setEVent() {
        //bottomsheet hien thi listchuong
        bottomSheetHienThiListChuong();
        bd.imgback.setOnClickListener(v -> finish());
        //
        callApiLayAnhTruyen();
        bd.recyclerViewDocTruyen.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    // Vuốt xuống
                    hideBars();
                } else if (dy < 0) {
                    // Vuốt lên
                    showBars();
                }
            }

            private void showBars() {
                if (!isAppBarVisible) {
                    bd.appBar.animate().translationY(0).setDuration(200);
                    isAppBarVisible = true;
                }
                if (!isBottomBarVisible) {
                    bd.bottomBar.animate().translationY(0).setDuration(200);
                    isBottomBarVisible = true;
                }
            }

            private void hideBars() {
                if (isAppBarVisible) {
                    bd.appBar.animate().translationY(-bd.appBar.getHeight()).setDuration(200);
                    isAppBarVisible = false;
                }
                if (isBottomBarVisible) {
                    bd.bottomBar.animate().translationY(bd.bottomBar.getHeight()).setDuration(200);
                    isBottomBarVisible = false;
                }
            }
        });
    }

    private void bottomSheetHienThiListChuong() {
        bottomSheetBehavior=BottomSheetBehavior.from(bd.bottomSheet);
       /* Intent intent = getIntent();
        ArrayList<Chapter> chapters = intent.getParcelableArrayListExtra("listChapter");
        RecyclerView_ListChuong_Adapter recyclerView_listChuong_adapter = new RecyclerView_ListChuong_Adapter(
                DocTruyenActivity.this, chapters, DocTruyenActivity.this);
        bd.RecyclerViewBottomSheet.setAdapter(recyclerView_listChuong_adapter);*/
        bd.listChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // Khi trạng thái của BottomSheet thay đổi
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        // Trạng thái thu gọn
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        // Trạng thái mở rộng
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        // Trạng thái ẩn
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_SETTLING:
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        // Các trạng thái khác
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // Khi BottomSheet trượt lên/xuống
            }
        });
    }

    private void callApiLayAnhTruyen() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String tokenAuth = sharedPreferences.getString("accessToken", "No Token");
        Intent intent = getIntent();
        int chapterId = intent.getIntExtra("chapterId", 0);
        String chapterName = intent.getStringExtra("nameChapter");
        bd.tvNameChapter.setText(chapterName);
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstanceWithAuth(tokenAuth).create(ApiInterface.class);
        Call<ApiResponseList_Image> call = apiInterface.getChapterImage(chapterId);
        call.enqueue(new retrofit2.Callback<ApiResponseList_Image>() {

            @Override
            public void onResponse(Call<ApiResponseList_Image> call, Response<ApiResponseList_Image> response) {
                if (response.isSuccessful()) {
                    ApiResponseList_Image apiResponseListImage = response.body();
                    listImageChapter = new ArrayList<>();
                    listImageChapter.addAll(apiResponseListImage.getData());
                    adapter = new RecyclerView_ImageChapter_Adapter(listImageChapter, DocTruyenActivity.this);
                    bd.recyclerViewDocTruyen.setAdapter(adapter);
                } else {
                    Log.e(TAG, "Response unsuccessful.");
                }

            }

            @Override
            public void onFailure(Call<ApiResponseList_Image> call, Throwable t) {
                Log.e(TAG, "Failed to fetch imageChapter: " + t.getMessage());
            }


        });
    }

    @Override
    public void onItemClick(String name, int chapterId) {

    }
}