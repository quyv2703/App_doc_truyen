package com.example.appdoctruyen.Fragment.BottomNavigation;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appdoctruyen.Activity.Chitiet_truyenActivity;
import com.example.appdoctruyen.Module.Story;
import com.example.appdoctruyen.Module.api.ApiInterface;
import com.example.appdoctruyen.Module.output.ApiResponseStoryGenreDetail;
import com.example.appdoctruyen.Module.api.D_ApiResponse;
import com.example.appdoctruyen.Module.api.RetrofitClient;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.adapter.truyentranh.adapterRV_truyentranhmoi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_truyentranh#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_truyentranh extends Fragment implements adapterRV_truyentranhmoi.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Story> arrayListtruyen;
    private RecyclerView recyclerview;

    public Fragment_truyentranh() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_truyentranh.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_truyentranh newInstance(String param1, String param2) {
        Fragment_truyentranh fragment = new Fragment_truyentranh();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_truyentranh, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerview = view.findViewById(R.id.recyclerviewofTruyentranhmoi);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerview.setHasFixedSize(true);
        setEvent();
    }
    private void setEvent() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstanceWithoutAuth().create(ApiInterface.class);
        Call<D_ApiResponse> call = apiInterface.getStory(1, 10);
        call.enqueue(new retrofit2.Callback<D_ApiResponse>() {
            @Override
            public void onResponse(Call<D_ApiResponse> call, Response<D_ApiResponse> response) {
                if (response.isSuccessful()) {
                    D_ApiResponse d_apiResponse = response.body();
                    if (d_apiResponse != null) {
                        arrayListtruyen = d_apiResponse.getData().getStories();
                        adapterRV_truyentranhmoi adapterTruyentranhmoi =
                                new adapterRV_truyentranhmoi(getContext(),
                                        arrayListtruyen, Fragment_truyentranh.this);
                        recyclerview.setAdapter(adapterTruyentranhmoi);
                    } else {
                        Log.e(TAG, "Story response is null.");
                    }
                } else {
                    Log.e(TAG, "Response unsuccessful.");
                }
            }
            @Override
            public void onFailure(Call<D_ApiResponse> call, Throwable t) {
                Log.e(TAG, "Failed to fetch story: " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstanceWithoutAuth().create(ApiInterface.class);
        Call<ApiResponseStoryGenreDetail> call = apiInterface.getDetailStory(position);
        call.enqueue(new retrofit2.Callback<ApiResponseStoryGenreDetail>() {
            @Override
            public void onResponse(Call<ApiResponseStoryGenreDetail> call, retrofit2.Response<ApiResponseStoryGenreDetail> response) {
                if (response.isSuccessful()) {
                    ApiResponseStoryGenreDetail apiResponseStoryGenreDetail = response.body();
                    // kiểm tra xem list data chứa story and genre có rỗng hay không
                    if (apiResponseStoryGenreDetail.getData().size()==0) {
                        Toast.makeText(getContext(), "Hiện không có dữ liệu", Toast.LENGTH_SHORT).show();

                    } else {
                        // có dữ liệu trong list data chứa story and genre
                        // Xử lý khi người dùng click vào item
                        Intent intent = new Intent(getActivity(), Chitiet_truyenActivity.class);
                        intent.putExtra("id", position);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onFailure(Call<ApiResponseStoryGenreDetail> call, Throwable t) {

            }
        });
    }
}