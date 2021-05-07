package com.example.loginscreen;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import javax.security.auth.callback.CallbackHandler;

public interface JsonPlaceHolderApi {
 @GET("check")
 Call<List<Post>> getPosts();
}
