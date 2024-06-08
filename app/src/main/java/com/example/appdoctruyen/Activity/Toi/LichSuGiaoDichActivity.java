package com.example.appdoctruyen.Activity.Toi;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.databinding.ActivityLichSuGiaoDichBinding;

public class LichSuGiaoDichActivity extends AppCompatActivity {
    private ActivityLichSuGiaoDichBinding bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd=ActivityLichSuGiaoDichBinding.inflate(getLayoutInflater());
        setContentView(bd.getRoot());

    }
}