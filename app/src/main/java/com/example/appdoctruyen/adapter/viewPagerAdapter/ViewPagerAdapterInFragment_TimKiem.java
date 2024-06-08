package com.example.appdoctruyen.adapter.viewPagerAdapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appdoctruyen.Fragment.Fragment_In_Fragment_TimKiem.Fragment_DangTienHanh;
import com.example.appdoctruyen.Fragment.Fragment_In_Fragment_TimKiem.Fragment_HoanThanh;
import com.example.appdoctruyen.Fragment.Fragment_In_Fragment_TimKiem.Fragment_TatCaTruyen;
import com.example.appdoctruyen.Fragment.BottomNavigation.Fragment_TimKiem;

public class ViewPagerAdapterInFragment_TimKiem extends FragmentStateAdapter {
    private static final int NUM_TABS = 3;

    public ViewPagerAdapterInFragment_TimKiem(@NonNull Fragment_TimKiem fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("ViewPagerDebug", "Creating fragment at position: " + position);
        switch (position) {
            case 0:
                return new Fragment_TatCaTruyen();
            case 1:
                return new Fragment_HoanThanh();
            case 2:
                return new Fragment_DangTienHanh();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }


}
