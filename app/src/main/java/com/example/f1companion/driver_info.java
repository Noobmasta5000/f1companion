package com.example.f1companion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class driver_info extends menu {

    static JSONObject data = new JSONObject();
    String first_name, last_name;
    private int matching_id;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_info);
        Bundle bundle = getIntent().getExtras();

        try {
            data = new JSONObject(bundle.getString("Driver"));
            // Get driver id and other meaninful data
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Setup connection to API
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api-formula-1.p.rapidapi.com/drivers?id=" + id)
                //.url("https://api-formula-1.p.rapidapi.com/drivers?id=49")
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
                    data = new JSONObject(response.body().string());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Find track that matches id
                            try {
                                for (int i = 0; i < Integer.parseInt(data.getString("results")); i++)
                                {
                                    if (id.equals(data.getJSONArray("response").getJSONObject(i).getJSONObject("competition").getString("id")))
                                    {
                                        matching_id = i;
                                        Log.d("MATCHING", Integer.toString(matching_id));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                // Set title to driver name
                                setTitle(data.getJSONArray("response").getJSONObject(matching_id).getJSONObject("competition").getString("name"));

                                TextView textview;
                                // Load driver image
                                String track_image_url = data.getJSONArray("response").getJSONObject(matching_id).getString("image");
                                ImageView driver_image = findViewById(R.id.track_image);
                                Picasso.get().load(track_image_url).fit().into(driver_image);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}