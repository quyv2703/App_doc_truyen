package com.example.appdoctruyen.Activity.TimKiem;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.appdoctruyen.Module.TheLoai;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.adapter.truyentranh.adapterRV_theloai;

import com.example.appdoctruyen.databinding.ActivityBoLocVer2Binding;

import java.util.List;

public class BoLoc_ver2 extends AppCompatActivity {
    private ActivityBoLocVer2Binding bd;
    private adapterRV_theloai adapterRVTheloai;
    private List<TheLoai> listTheLoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd=ActivityBoLocVer2Binding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(bd.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bd.recyclerviewTheLoai1.setHasFixedSize(true);
        bd.recyclerviewTheLoai1.setLayoutManager(new GridLayoutManager(this, 3));
      /*  ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<ApiResponseFilterTheLoai> call = apiInterface.getGenre();
        call.enqueue(new retrofit2.Callback<ApiResponseFilterTheLoai>() {
            @Override
            public void onResponse(Call<ApiResponseFilterTheLoai> call, Response<ApiResponseFilterTheLoai> response) {
                if(response.isSuccessful()){
                    ApiResponseFilterTheLoai apiResponseFilterTheLoai=response.body();
                    assert apiResponseFilterTheLoai != null;
                    listTheLoai=apiResponseFilterTheLoai.getData();
                    adapterRVTheloai = new adapterRV_theloai(BoLoc_ver2.this, listTheLoai);
                    bd.recyclerviewTheLoai.setAdapter(adapterRVTheloai);
                } else {
                    Log.e(TAG, "Response unsuccessful.");
                }
            }

            @Override
            public void onFailure(Call<ApiResponseFilterTheLoai> call, Throwable t) {
                Log.e(TAG, "Failed to fetch filter: " + t.getMessage());
            }
        });*/
    }
}