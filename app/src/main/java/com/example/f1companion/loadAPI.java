package com.example.f1companion;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class loadAPI {
    private static final OkHttpClient client = new OkHttpClient();
    static JSONObject jsonobject;
    public static JSONObject getAPI(Context context, String url) throws JSONException {
        Request request = new Request.Builder()
                .url("https://api-formula-1.p.rapidapi.com/" + url)
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
                    jsonobject = new JSONObject(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        return jsonobject;
    }
}
