package com.example.breaking_bad.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.breaking_bad.Adapter.MyListAdapter;
import com.example.breaking_bad.Api.JsonPaceholderApi;
import com.example.breaking_bad.Data.Post;
import com.example.breaking_bad.R;
import com.example.breaking_bad.Helper.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  {

    // Session Manager Class
    SessionManager session;
    Button logout;
    SearchView simplesearchView;
    RecyclerView recyclerView;
    MyListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        // Session class instance
        session = new SessionManager(getApplicationContext());

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */

    //    session.checkLogin();


        logout = (Button) findViewById(R.id.logout);
        simplesearchView = (SearchView) findViewById(R.id.simpleSearchView);
        recyclerView = findViewById(R.id.recycleview);






        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity
                session.logoutUser();
                finish();
            }
        });

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("https://breakingbadapi.com/api/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        JsonPaceholderApi jsonPaceholderApi = retrofit.create(JsonPaceholderApi.class);

        List<Post> arrayList = new ArrayList<>();
        Call<List<Post>> call = jsonPaceholderApi.getPost();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful()){
                   // Toast.makeText(getApplicationContext(),
                   //         "Code :"+response,Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                  //  Toast.makeText(getApplicationContext(),
                  //          "Code :"+response,Toast.LENGTH_LONG).show();
                   // progressBar.setVisibility(View.GONE);
                    List<Post> posts = response.body();
                    for(Post post: posts) {

                        Post post1 = new Post(null,null);

                        Log.d("name",post.getName());
                        post1.setName(post.getName());
                        post1.setImg(post.getImg());
                        arrayList.add(post1);
                    }


                    mAdapter = new MyListAdapter(arrayList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager
                            (getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);


                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        simplesearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                for (int i=0;i<arrayList.size();i++){
                    if((arrayList.get(i).getName().toLowerCase(Locale.getDefault()).equals(query.toLowerCase(Locale.getDefault())))){
                        mAdapter.filter(query);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();

                    }
                }


               /* if(arrayList.contains(query)){
                    mAdapter.filter(query);
                }else{
                    Toast.makeText(MainActivity.this, arrayList.size()+"",Toast.LENGTH_LONG).show();

                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                    mAdapter.filter(newText);


                return false;
            }
        });



    }
}