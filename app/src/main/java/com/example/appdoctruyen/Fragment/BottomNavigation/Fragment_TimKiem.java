package com.example.appdoctruyen.Fragment.BottomNavigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdoctruyen.Activity.TimKiem.BolocActivity;
import com.example.appdoctruyen.adapter.viewPagerAdapter.ViewPagerAdapterInFragment_TimKiem;
import com.example.appdoctruyen.databinding.FragmentTimKiemBinding;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_TimKiem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_TimKiem extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentTimKiemBinding bd;

    private OnSearchInteractionListener mListener;

    public interface OnSearchInteractionListener {
        void onSearchFocusChange(boolean hasFocus);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchInteractionListener) {
            mListener = (OnSearchInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSearchInteractionListener");
        }
    }

    public Fragment_TimKiem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_TimKiem.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_TimKiem newInstance(String param1, String param2) {
        Fragment_TimKiem fragment = new Fragment_TimKiem();
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


        bd = FragmentTimKiemBinding.inflate(inflater, container, false);



        return bd.getRoot();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*ImageButton filterTheLoai;
        filterTheLoai=view.findViewById(R.id.filterTheLoai);
        filterTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), BoLoc_ver2.class);
                startActivity(intent);
            }
        });*/

        setEvent();
    }

    private void setEvent() {
        bd.SearchBar.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (mListener != null) {
                mListener.onSearchFocusChange(hasFocus);
            }
        });
        bd.filterTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), BolocActivity.class);
                startActivity(intent);
            }
        });
        // chuyển đổi qua lại các tablayout kết hop viewpager2
        ViewPagerAdapterInFragment_TimKiem viewPagerAdapterInFragment_timKiem =
                new ViewPagerAdapterInFragment_TimKiem(Fragment_TimKiem.this);
        bd.viewPager2InFragmentTimKiem.setAdapter(viewPagerAdapterInFragment_timKiem);
        new TabLayoutMediator(bd.tabLayoutFragmentTimkiem, bd.viewPager2InFragmentTimKiem,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Tất cả");
                            break;
                        case 1:
                            tab.setText("Hoàn thành");
                            break;
                        case 2:
                            tab.setText("Đang tiến hành");
                            break;
                        default:
                            tab.setText("Tab " + (position + 1));
                    }
                }
        ).attach();
    }
}