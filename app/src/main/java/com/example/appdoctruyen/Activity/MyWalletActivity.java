package com.example.appdoctruyen.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.databinding.ActivityMyWalletBinding;

public class MyWalletActivity extends AppCompatActivity {
    private ActivityMyWalletBinding bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd=ActivityMyWalletBinding.inflate(getLayoutInflater());

        setContentView(bd.getRoot());

    }
}