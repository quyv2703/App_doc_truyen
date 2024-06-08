package com.example.appdoctruyen.Fragment.BottomNavigation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appdoctruyen.Activity.Login;
import com.example.appdoctruyen.Activity.MyWalletActivity;
import com.example.appdoctruyen.databinding.FragmentToiBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Toi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Toi extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentToiBinding bd;

    public Fragment_Toi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Toi.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Toi newInstance(String param1, String param2) {
        Fragment_Toi fragment = new Fragment_Toi();
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
        bd = FragmentToiBinding.inflate(inflater, container, false);
        return bd.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEvent();

    }

    private void setEvent() {
        nutDangXuat();
        hienThiThongTin();
        napTien();
    }

    private void napTien() {
        bd.wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyWalletActivity.class);
                startActivity(intent);
            }
        });
    }

    private void hienThiThongTin() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("myUser", Context.MODE_PRIVATE);
        String displayName = sharedPreferences.getString("displayName", "No Name");
        String photoURL = sharedPreferences.getString("photoURL", "No Photo");
        bd.tvName.setText(displayName);
        Glide.with(getContext())
                .load(photoURL)
                .into(bd.tmgAvatar);
    }

    private void nutDangXuat() {
        bd.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }

            private void showLogoutDialog() {
                // Sử dụng AlertDialog.Builder để tạo dialog
                new AlertDialog.Builder(getContext())
                        .setTitle("Đăng xuất")
                        .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Thực hiện hành động đăng xuất
                                performLogout();
                            }

                            private void performLogout() {
                                Toast.makeText(getContext(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                                // Chuyển về màn hình đăng nhập
                                startActivity(new Intent(getContext(), Login.class));
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Đóng dialog
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false) // Không cho phép tắt dialog khi nhấn ra ngoài
                        .create()
                        .show();
            }
        });

    }
}