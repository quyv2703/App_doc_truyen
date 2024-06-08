package com.example.appdoctruyen.Fragment.Fragment_In_Fragment_TimKiem;

import static android.content.ContentValues.TAG;

import android.content.Intent;
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
import com.example.appdoctruyen.Module.Story;
import com.example.appdoctruyen.Module.api.ApiInterface;
import com.example.appdoctruyen.Module.api.D_ApiResponse;
import com.example.appdoctruyen.Module.api.RetrofitClient;
import com.example.appdoctruyen.Module.output.ApiResponseStoryGenreDetail;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.adapter.timkiem.RecyclerView_Truyen_Adapter;
import com.example.appdoctruyen.databinding.FragmentTatCaTruyenBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_TatCaTruyen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_TatCaTruyen extends Fragment implements RecyclerView_Truyen_Adapter.OnItemClick{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentTatCaTruyenBinding bd;
    RecyclerView_Truyen_Adapter recyclerView_truyenAdapter;
    private List<Story> arrayListtruyen;

    public Fragment_TatCaTruyen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_TatCaTruyen.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_TatCaTruyen newInstance(String param1, String param2) {
        Fragment_TatCaTruyen fragment = new Fragment_TatCaTruyen();
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
        bd=FragmentTatCaTruyenBinding.inflate(inflater,container,false);
        return bd.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bd.recyclerViewInFragmentTatCaTruyen.setHasFixedSize(true);
        bd.recyclerViewInFragmentTatCaTruyen.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        Toast.makeText(getContext(), "Đây là fragment Tất cả truyện", Toast.LENGTH_SHORT).show();
       setEvent();
    }

    private void setEvent() {
        ApiInterface apiInterface= RetrofitClient.getRetrofitInstanceWithoutAuth().create(ApiInterface.class);
        Call<D_ApiResponse> call = apiInterface.getStory(1, 10);
        call.enqueue(new retrofit2.Callback<D_ApiResponse>() {

            @Override
            public void onResponse(Call<D_ApiResponse> call, Response<D_ApiResponse> response) {
                if (response.isSuccessful()) {
                    D_ApiResponse d_apiResponse = response.body();
                    if (d_apiResponse != null) {

                        arrayListtruyen = d_apiResponse.getData().getStories();
                        recyclerView_truyenAdapter =
                                new RecyclerView_Truyen_Adapter(arrayListtruyen,
                                        getContext(),Fragment_TatCaTruyen.this);
                        bd.recyclerViewInFragmentTatCaTruyen.setAdapter(recyclerView_truyenAdapter);
                    } else {
                        Log.e(TAG, "Story response is null.");
                    }
                } else {
                    Log.e(TAG, "Response unsuccessful.");
                }
            }

            @Override
            public void onFailure(Call<D_ApiResponse> call, Throwable t) {

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