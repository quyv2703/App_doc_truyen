package com.example.appdoctruyen.adapter.viewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appdoctruyen.Fragment.Fragment_In_BoLocActivity.Fragment_TheLoai;
import com.example.appdoctruyen.Fragment.Fragment_In_Fragment_TimKiem.Fragment_TatCaTruyen;

public class ViewPagerAdapter_In_BolocActivity extends FragmentStateAdapter {
    public ViewPagerAdapter_In_BolocActivity(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Fragment_TheLoai();
           /* case 1:
                return Fragment2.newInstance();
            case 2:
                return Fragment3.newInstance();*/
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;// gồm thể loại và sắp xếp
    }
}
