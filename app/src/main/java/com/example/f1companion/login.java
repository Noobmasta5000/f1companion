package com.example.f1companion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class login extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            JSONObject data = loadJSON.getAssetJsonData(getApplicationContext());
            TextView textView = findViewById(R.id.textview1);
            String name = data.getJSONArray("response").getJSONObject(0).getString("name");
            textView.setText(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Request request = new Request.Builder()
                .url("https://api-formula-1.p.rapidapi.com/drivers?name=lando%20norris")
                .get()
                .addHeader("X-RapidAPI-Key", "0801c0f8camshbbda9eeceafe698p181139jsn5ca56dcbfb99")
                .addHeader("X-RapidAPI-Host", "api-formula-1.p.rapidapi.com")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                Log.d("MESSAGE", "FAILURE");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject jsonobject = new JSONObject(response.body().string());
                    String name = jsonobject.getJSONArray("response").getJSONObject(0).getString("name");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView textView = findViewById(R.id.textview1);
                            textView.setText(name);
                        }
                    });
                    Log.d("NAME", name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });



    }

    public void goto_drivers(View view) {
        Intent intent = new Intent(this, drivers.class);
        startActivity(intent);
    }
}