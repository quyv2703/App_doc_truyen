package com.example.appdoctruyen.Module.api;



import com.example.appdoctruyen.Module.User;
import com.example.appdoctruyen.Module.input.InputDangKi;
import com.example.appdoctruyen.Module.input.InputLogin;
import com.example.appdoctruyen.Module.input.InputTheoDoi;
import com.example.appdoctruyen.Module.output.ApiResponseFilterTheLoai;
import com.example.appdoctruyen.Module.output.ApiResponseListStoryByFollow;
import com.example.appdoctruyen.Module.output.ApiResponseList_Image;
import com.example.appdoctruyen.Module.output.ApiResponseStoryGenreDetail;
import com.example.appdoctruyen.Module.output.ListChapter;
import com.example.appdoctruyen.Module.output.LoginResponse;
import com.example.appdoctruyen.Module.output.OutputDangKi;
import com.example.appdoctruyen.Module.output.OutputGetListStoryByFollow;
import com.example.appdoctruyen.Module.output.OutputXacThuc;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
  
  @POST("login")
  Call<ApiReponse<LoginResponse>> login(@Body InputLogin login);
  // lấy user profile
  @GET("profile")
  Call<ApiReponse<User>> getUserProfile(@Header("Authorization") String token);
  // đăng kí
  @POST("register")
  Call<ApiReponse<OutputDangKi>> dangki(@Body InputDangKi dangki);
  // theo doi truyen
  @POST("story-api/storyFollowDetail")
  Call<InputTheoDoi> theoDoi(@Body InputTheoDoi theoDoi);
  // xác thực đăng kí
  @GET("verified")
  Call<ApiReponse<OutputXacThuc>> xacThuc(@Query("idAccount") int idAccount);
  // api lấy view của story
  @GET("viewDetail/story/{storyId}/viewCount")
  Call<ApiReponse<Integer>> getViewCount(@Path("storyId") int storyId);
  // lấy listchapter xong lâấy tên chapter mới nhất
  @GET("story-api/chapter")
  Call<ListChapter> getNewChapter(@Query("storyId") int storyId,@Query("page") int page, @Query("limit") int limit);
  // lấy ảnh của chapter
  @GET("story-api/chapterImage")
  Call<ApiResponseList_Image> getChapterImage(@Query("chapterId") int chapterId);
  @GET("story-api/story")
  Call<D_ApiResponse> getStory(@Query("page") int page, @Query("limit") int limit);
  @GET("story-api/genre")
  Call<ApiResponseFilterTheLoai> getGenre();
  @GET("story-api/storyGenreDetail")
  Call<ApiResponseStoryGenreDetail> getDetailStory(@Query("storyId") int storyId);
  // laays list truyen dang theo doi
  @GET("story-api/storyFollowDetail")
  Call<ApiResponseListStoryByFollow> getListStoryByFollow(@Query("userId") int userId);
}
