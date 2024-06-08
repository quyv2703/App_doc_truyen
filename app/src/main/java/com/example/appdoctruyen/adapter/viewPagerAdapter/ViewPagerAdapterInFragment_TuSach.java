package com.example.appdoctruyen.adapter.viewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appdoctruyen.Fragment.Fragment_In_Fragment_TuSach.Fragment_LichSu;
import com.example.appdoctruyen.Fragment.Fragment_In_Fragment_TuSach.Fragment_TheoDoi;
import com.example.appdoctruyen.Fragment.BottomNavigation.Fragment_TuSach;

public class ViewPagerAdapterInFragment_TuSach  extends FragmentStateAdapter {
    private static final int NUM_TABS = 2;

    public ViewPagerAdapterInFragment_TuSach(@NonNull Fragment_TuSach fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Fragment_LichSu();
            case 1:
                return new Fragment_TheoDoi();

            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
