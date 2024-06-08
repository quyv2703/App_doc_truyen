package com.example.appdoctruyen.Fragment.BottomNavigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdoctruyen.adapter.viewPagerAdapter.ViewPagerAdapterInFragment_TuSach;
import com.example.appdoctruyen.databinding.FragmentTuSachBinding;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_TuSach#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_TuSach extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentTuSachBinding bd;

    public Fragment_TuSach() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_TuSach.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_TuSach newInstance(String param1, String param2) {
        Fragment_TuSach fragment = new Fragment_TuSach();
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
        bd=FragmentTuSachBinding.inflate(inflater,container,false);
        return bd.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // chuyển đổi qua lại các tablayout kết hop viewpager2
        ViewPagerAdapterInFragment_TuSach viewPagerAdapterInFragmentTuSach =
                new ViewPagerAdapterInFragment_TuSach(Fragment_TuSach.this);

        bd.ViewPager2InFragmentTuSach.setAdapter(viewPagerAdapterInFragmentTuSach);

        new TabLayoutMediator(bd.tabLayout, bd.ViewPager2InFragmentTuSach,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Lịch sử");
                            break;
                        case 1:
                            tab.setText("Theo dõi");
                            break;

                        default:
                            tab.setText("Tab " + (position + 1));
                    }
                }
        ).attach();
    }
}