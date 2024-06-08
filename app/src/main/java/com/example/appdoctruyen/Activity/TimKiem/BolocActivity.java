package com.example.appdoctruyen.Activity.TimKiem;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.adapter.viewPagerAdapter.ViewPagerAdapter_In_BolocActivity;
import com.example.appdoctruyen.databinding.ActivityBolocBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class BolocActivity extends AppCompatActivity {
    private ActivityBolocBinding bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bd=ActivityBolocBinding.inflate(getLayoutInflater());
        setContentView(bd.getRoot());
        ViewPagerAdapter_In_BolocActivity adapter = new ViewPagerAdapter_In_BolocActivity(this);
        bd.viewPager2InActivityBoLoc.setAdapter(adapter);
        new TabLayoutMediator(bd.tabLayoutBoloc, bd.viewPager2InActivityBoLoc,
                (tab, position) ->{
                    switch (position) {
                        case 0:
                            tab.setText("Thể loại");
                            break;
                        case 1:
                            tab.setText("Sắp xếp");
                            break;

                        default:
                            tab.setText("Tab " + (position + 1));
                    }
                }
                ).attach();
        bd.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}