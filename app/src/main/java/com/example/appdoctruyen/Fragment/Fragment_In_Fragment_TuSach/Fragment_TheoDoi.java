package com.example.appdoctruyen.Fragment.Fragment_In_Fragment_TuSach;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appdoctruyen.Activity.Chitiet_truyenActivity;
import com.example.appdoctruyen.Module.Interface.ItemClick;
import com.example.appdoctruyen.Module.api.ApiInterface;
import com.example.appdoctruyen.Module.api.RetrofitClient;
import com.example.appdoctruyen.Module.output.ApiResponseListStoryByFollow;
import com.example.appdoctruyen.Module.output.ApiResponseStoryGenreDetail;
import com.example.appdoctruyen.Module.output.OutputGetListStoryByFollow;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.adapter.tusach.RecyclerViewAdapter_TheoDoi;
import com.example.appdoctruyen.databinding.FragmentTheoDoiBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_TheoDoi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_TheoDoi extends Fragment implements ItemClick {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentTheoDoiBinding bd;
    RecyclerViewAdapter_TheoDoi adapter;



    public Fragment_TheoDoi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_TheoDoi.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_TheoDoi newInstance(String param1, String param2) {
        Fragment_TheoDoi fragment = new Fragment_TheoDoi();
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
        bd = FragmentTheoDoiBinding.inflate(inflater, container, false);
        return bd.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bd.recyclerViewTruyenDaTheoDoi.setHasFixedSize(true);
        bd.recyclerViewTruyenDaTheoDoi.setLayoutManager(new LinearLayoutManager(getContext()));
        setEvent();
    }

    private void setEvent() {
        SharedPreferences sharedPreferences1 = getContext().getSharedPreferences("myUser", Context.MODE_PRIVATE);
        int userId = sharedPreferences1.getInt("UserId", -1);
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstanceWithoutAuth().create(ApiInterface.class);
        Call<ApiResponseListStoryByFollow> call = apiInterface.getListStoryByFollow(userId);
        call.enqueue(new retrofit2.Callback<ApiResponseListStoryByFollow>() {

            @Override
            public void onResponse(Call<ApiResponseListStoryByFollow> call, Response<ApiResponseListStoryByFollow> response) {
                if (response.isSuccessful()) {
                    ApiResponseListStoryByFollow list = response.body();
                    if(list != null) {
                        List<OutputGetListStoryByFollow> list1 = list.getData();
                        adapter = new RecyclerViewAdapter_TheoDoi( list1,getContext(),  Fragment_TheoDoi.this);
                        bd.recyclerViewTruyenDaTheoDoi.setAdapter(adapter);

                    }else {
                        Log.e(TAG, "Story response is null.");
                    }
                } else {
                    Log.e(TAG, "Response unsuccessful.");
                }
            }

            @Override
            public void onFailure(Call<ApiResponseListStoryByFollow> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(int storyId) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstanceWithoutAuth().create(ApiInterface.class);
        Call<ApiResponseStoryGenreDetail> call = apiInterface.getDetailStory(storyId);
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
                        intent.putExtra("id", storyId);
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