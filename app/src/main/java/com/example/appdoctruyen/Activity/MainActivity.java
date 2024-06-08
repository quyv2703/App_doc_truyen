package com.example.appdoctruyen.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.appdoctruyen.Fragment.BottomNavigation.Fragment_TimKiem;
import com.example.appdoctruyen.Fragment.BottomNavigation.Fragment_Toi;
import com.example.appdoctruyen.Fragment.BottomNavigation.Fragment_TuSach;
import com.example.appdoctruyen.Fragment.BottomNavigation.Fragment_truyentranh;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements Fragment_TimKiem.OnSearchInteractionListener {

    private ActivityMainBinding bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bd.getRoot());
        // Mở Fragment_truyentranh khi Activity được tạo lần đầu tiên
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayoutcontent, new Fragment_truyentranh())
                    .commit();
        }
        bd.bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId=item.getItemId();
                if (itemId==R.id.btmnavTruyentranh){
                    selectedFragment= new Fragment_truyentranh();
                }
                if (itemId==R.id.TimKiem){
                    selectedFragment= new Fragment_TimKiem();
                }
                if (itemId==R.id.TuSach){
                    selectedFragment= new Fragment_TuSach();
                }
                if (itemId==R.id.Toi){
                    selectedFragment= new Fragment_Toi();
                }

                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayoutcontent, selectedFragment)
                        .commit();
                return true;
            }
        });




    }

    @Override
    public void onSearchFocusChange(boolean hasFocus) {
        if (hasFocus) {
            bd.bottomNav.setVisibility(View.GONE);
        } else {
            bd.bottomNav.setVisibility(View.VISIBLE);
        }

    }
   /* private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_view_tag, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/
}