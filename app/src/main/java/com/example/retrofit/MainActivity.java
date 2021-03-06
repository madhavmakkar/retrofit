package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.retrofit.api_interfaces.jsonplaceholderapi;
import com.example.retrofit.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        textViewResult=findViewById(R.id.textview);

        createPost();

    }


    void createPost(){
        Post post = new Post(50,"Retrofit project","retrofit Body Data");

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")

                .addConverterFactory(GsonConverterFactory.create())

                .build();
        jsonplaceholderapi jsonPlaceHolderApi = retrofit.create(jsonplaceholderapi.class);

        Call<Post> call= jsonPlaceHolderApi.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }
                Post postResponse = response.body();

                String content="";
                content+="Code: "+ response.code()+"\n";
                content+="ID: "+postResponse.getId()+"\n";
                content+="User ID: "+postResponse.getUserId()+"\n";
                content+="Title: "+postResponse.getTitle()+"\n";
                content+="Text: "+postResponse.getText()+"\n\n";

                textViewResult.append(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
    public void getPosts(){
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonplaceholderapi jsonPlaceHolderApi = retrofit.create(jsonplaceholderapi.class);

        Call<List<Post>> call=jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code:"+response.code());
                    return;
                }
                List<Post> posts=response.body();
                for (Post post: posts){
                    String content="";
                    content += "ID: " + post.getId() +"\n";
                    content += "User ID: " +post.getUserId() +"\n";
                    content += "Title: " +post.getTitle() +"\n";
                    content += "Text: " +post.getText() +"\n";

                    textViewResult.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }
}