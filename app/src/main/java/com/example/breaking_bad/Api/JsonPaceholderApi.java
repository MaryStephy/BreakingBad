package com.example.breaking_bad.Api;

import com.example.breaking_bad.Data.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPaceholderApi {
    @GET("characters")
    Call<List<Post>> getPost();

}
