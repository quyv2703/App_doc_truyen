package com.example.appdoctruyen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdoctruyen.Module.api.ApiInterface;
import com.example.appdoctruyen.Module.api.ApiReponse;
import com.example.appdoctruyen.Module.api.RetrofitClient;
import com.example.appdoctruyen.Module.input.InputDangKi;
import com.example.appdoctruyen.Module.output.OutputDangKi;
import com.example.appdoctruyen.Module.output.OutputXacThuc;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.databinding.ActivityDangkiBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dangki extends AppCompatActivity {
    private ActivityDangkiBinding bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd=ActivityDangkiBinding.inflate(getLayoutInflater());
        setContentView(bd.getRoot());
        setEvent();

    }

    private void setEvent() {
        nutDangKi();

    }

    private void nutDangKi() {
        bd.buttonRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=bd.edtEmail.getText().toString();
                String matkhau=bd.edtPassword.getText().toString();
                String tennguoidung=bd.edtTennguoidung.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(Dangki.this, "Email không được để trống", Toast.LENGTH_SHORT).show();
                    bd.edtEmail.requestFocus();
                    return;
                }
                if(matkhau.isEmpty()){
                    Toast.makeText(Dangki.this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                    bd.edtPassword.requestFocus();
                    return;
                }
                if(tennguoidung.isEmpty()){
                    Toast.makeText(Dangki.this, "Tên người dùng không được để trống", Toast.LENGTH_SHORT).show();
                    bd.edtTennguoidung.requestFocus();
                    return;
                }
                InputDangKi inputDangKi= new InputDangKi(email,matkhau,tennguoidung);

                //Call api dang ki
                ApiInterface apiInterface= RetrofitClient.getRetrofitInstanceWithoutAuth().create(ApiInterface.class);
                Call<ApiReponse<OutputDangKi>> call=apiInterface.dangki(inputDangKi);
                call.enqueue(new Callback<ApiReponse<OutputDangKi>>() {
                    @Override
                    public void onResponse(Call<ApiReponse<OutputDangKi>> call, Response<ApiReponse<OutputDangKi>> response) {
                        if(response.isSuccessful()){
                            ApiReponse<OutputDangKi> dangKiApiReponse= response.body();
                            if (dangKiApiReponse != null && dangKiApiReponse.getStatusCode() == 201){
                                // call api xác thực đăng kí
                                CallApiXacThucDangKi(dangKiApiReponse.getData().getId());
                                Toast.makeText(Dangki.this, "Đăng kí tài khoản thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Dangki.this, Login.class);
                                startActivity(intent);
                            }
                        }

                    }
                    // call api xác thực đăng kí
                    private void CallApiXacThucDangKi(int id) {
                        Call<ApiReponse<OutputXacThuc>> call1=apiInterface.xacThuc(id);
                        call1.enqueue(new Callback<ApiReponse<OutputXacThuc>>() {

                            @Override
                            public void onResponse(Call<ApiReponse<OutputXacThuc>> call, Response<ApiReponse<OutputXacThuc>> response) {


                            }

                            @Override
                            public void onFailure(Call<ApiReponse<OutputXacThuc>> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ApiReponse<OutputDangKi>> call, Throwable t) {

                    }
                });


            }
        });
    }
}