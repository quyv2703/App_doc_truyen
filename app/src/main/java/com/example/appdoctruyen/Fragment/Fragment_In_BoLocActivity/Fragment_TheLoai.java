package com.example.appdoctruyen.Fragment.Fragment_In_BoLocActivity;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdoctruyen.Activity.TimKiem.BolocActivity;
import com.example.appdoctruyen.Module.TheLoai;
import com.example.appdoctruyen.Module.api.ApiInterface;
import com.example.appdoctruyen.Module.api.RetrofitClient;
import com.example.appdoctruyen.Module.output.ApiResponseFilterTheLoai;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.adapter.truyentranh.adapterRV_theloai;
import com.example.appdoctruyen.databinding.FragmentTheLoaiBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_TheLoai#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_TheLoai extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentTheLoaiBinding bd;
    adapterRV_theloai adapterRVTheloai;
    List<TheLoai> listTheLoai;


    public Fragment_TheLoai() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_TheLoai.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_TheLoai newInstance(String param1, String param2) {
        Fragment_TheLoai fragment = new Fragment_TheLoai();
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
        bd = FragmentTheLoaiBinding.inflate(inflater, container, false);
      return bd.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bd.recyclerViewInFragmentTheLoai.setHasFixedSize(true);
        bd.recyclerViewInFragmentTheLoai.setLayoutManager(new GridLayoutManager(getContext(), 3));
        setEvent();
    }

    private void setEvent() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstanceWithoutAuth().create(ApiInterface.class);
        Call<ApiResponseFilterTheLoai> call = apiInterface.getGenre();
        call.enqueue(new retrofit2.Callback<ApiResponseFilterTheLoai>() {
            @Override
            public void onResponse(Call<ApiResponseFilterTheLoai> call, Response<ApiResponseFilterTheLoai> response) {
                if(response.isSuccessful()){
                    ApiResponseFilterTheLoai apiResponseFilterTheLoai=response.body();
                    assert apiResponseFilterTheLoai != null;
                    listTheLoai=apiResponseFilterTheLoai.getData();
                    adapterRVTheloai = new adapterRV_theloai(getContext(), listTheLoai);
                    bd.recyclerViewInFragmentTheLoai.setAdapter(adapterRVTheloai);
                } else {
                    Log.e(TAG, "Response unsuccessful.");
                }
            }

            @Override
            public void onFailure(Call<ApiResponseFilterTheLoai> call, Throwable t) {
                Log.e(TAG, "Failed to fetch filter: " + t.getMessage());
            }
        });

    }
}