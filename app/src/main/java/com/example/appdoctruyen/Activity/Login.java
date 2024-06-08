package com.example.appdoctruyen.Activity;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appdoctruyen.Module.User;
import com.example.appdoctruyen.Module.api.ApiInterface;
import com.example.appdoctruyen.Module.api.ApiReponse;
import com.example.appdoctruyen.Module.input.InputLogin;
import com.example.appdoctruyen.Module.output.LoginResponse;
import com.example.appdoctruyen.Module.api.RetrofitClient;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;


    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {

                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        try {
                            GoogleSignInAccount signInAccount = task.getResult(ApiException.class);
                            AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                            auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        auth = FirebaseAuth.getInstance();
                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        startActivity(intent);


                                        Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        } catch (ApiException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
    );
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_OK) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            auth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Đăng nhập thành công
                                // Lấy access token
                                String accessToken = account.getIdToken();
                                // Lưu trữ access token, ví dụ: trong SharedPreferences
                                SharedPreferences.Editor editor = getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
                                editor.putString("accessToken", accessToken);
                                editor.apply();
                                // Chuyển sang MainActivity
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finish(); // Đóng Activity hiện tại để không quay lại khi nhấn nút back
                            } else {
                                // Đăng nhập thất bại
                                Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (ApiException e) {
            // Xử lý lỗi khi đăng nhập không thành công
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
        }
    }*/


    private ActivityLoginBinding bd;
    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(bd.getRoot());
        FirebaseApp.initializeApp(Login.this);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(Login.this, options);
        auth = FirebaseAuth.getInstance();
        setEvent();

    }

    private void setEvent() {
        //nut dang ki
        bd.textViewDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Dangki.class);
                startActivity(intent);
            }
        });
        // nut dang nhap
        nutDangNhap();


        // nut dang nhap voi google
        bd.buttonLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = googleSignInClient.getSignInIntent();
                activityResultLauncher.launch(intent);


            }
        });
    }

    private void nutDangNhap() {
        bd.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = bd.edtEmail.getText().toString().trim();
                String password = bd.edtPassword.getText().toString().trim();
                InputLogin login = new InputLogin(username, password);
                // Call API to login
                ApiInterface apiInterface = RetrofitClient.getRetrofitInstanceWithoutAuth().create(ApiInterface.class);
                Call<ApiReponse<LoginResponse>> call = apiInterface.login(login);
                call.enqueue(new Callback<ApiReponse<LoginResponse>>() {
                    @Override
                    public void onResponse(Call<ApiReponse<LoginResponse>> call, Response<ApiReponse<LoginResponse>> response) {
                        if (response.isSuccessful()) {
                            ApiReponse<LoginResponse> apiReponse = response.body();
                            if (apiReponse != null && apiReponse.getStatusCode() == 400) {
                                Toast.makeText(Login.this, "Email chưa được xác minh", Toast.LENGTH_SHORT).show();
                            }

                            if (apiReponse != null && apiReponse.getStatusCode() == 200) {
                                LoginResponse loginResponse = apiReponse.getData();
                                String accessToken = loginResponse.getAccessToken();
                                // Lưu trữ access token, ví dụ: trong SharedPreferences
                                //Gọi api get profile
                                String authToken="Bearer "+accessToken;
                                // Call API to get user profile and save
                                getUserProfile(authToken);
                                luuDuLieuVaoSharedPrefefences(accessToken);

                                Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                // Chuyển đến màn hình chính hoặc thực hiện các hoạt động khác sau khi đăng nhập thành công
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            }


                        } else if(response.errorBody() != null) {
                            try {
                                // Chuyển đổi error body thành ErrorResponse
                                ApiReponse<LoginResponse> errorResponse =  new Gson().fromJson(response.errorBody().string(), new TypeToken<ApiReponse<LoginResponse>>(){}.getType());
                                if(errorResponse.getStatusCode() == 404) {
                                    Toast.makeText(Login.this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            }


                        }

                    }

                    private void getUserProfile(String accessToken) {
                        Call<ApiReponse<User>> call = apiInterface.getUserProfile(accessToken);
                        call.enqueue(new Callback<ApiReponse<User>>() {

                            @Override
                            public void onResponse(Call<ApiReponse<User>> call, Response<ApiReponse<User>> response) {
                                if (response.isSuccessful()) {
                                    ApiReponse<User> apiReponse = response.body();
                                    if (apiReponse != null && apiReponse.getStatusCode() == 200) {
                                        User user = apiReponse.getData();
                                        savaUserProfile(user);
                                    }else {
                                        Toast.makeText(Login.this, "Lỗi khi get profile", Toast.LENGTH_SHORT).show();
                                    }


                                }else {
                                    Toast.makeText(Login.this, "Không thành công", Toast.LENGTH_SHORT).show();
                                }

                            }

                            private void savaUserProfile(User user) {
                                SharedPreferences.Editor editor = getSharedPreferences("myUser", MODE_PRIVATE).edit();
                                editor.putString("displayName", user.getDisplayName());
                                editor.putString("email", user.getEmail());
                                editor.putString("photoURL", user.getPhotoURL());
                                editor.putInt("UserId",user.getId());
                                editor.apply();
                            }

                            @Override
                            public void onFailure(Call<ApiReponse<User>> call, Throwable t) {
                                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onFailure: ", t);

                            }
                        });
                    }

                    private void luuDuLieuVaoSharedPrefefences(String accessToken) {
                        SharedPreferences.Editor editor = getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
                        editor.putString("accessToken", accessToken);
                        editor.apply();
                    }

                    @Override
                    public void onFailure(Call<ApiReponse<LoginResponse>> call, Throwable t) {
                    }
                });



            }
        });
    }
}



