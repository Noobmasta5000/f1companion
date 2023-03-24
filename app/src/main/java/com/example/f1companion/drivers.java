package com.example.f1companion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class drivers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);
        Intent intent = getIntent();


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api-formula-1.p.rapidapi.com/rankings/drivers?season=2023")
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
                    JSONObject data = new JSONObject(response.body().string());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int i = 0; i <= Integer.parseInt(data.getString("results")); i++) {
                                    try {
                                        //Get id of profile pic imageview
                                        String id = "profile_pic_" + i;
                                        int resID = getResources().getIdentifier(id, "id", getPackageName());
                                        ImageView profile_pic = findViewById(resID);

                                        //Get id of basic driver info
                                        id = "basic_driver_info_" + i;
                                        resID = getResources().getIdentifier(id, "id", getPackageName());
                                        TextView textView = findViewById(resID);

                                        //Get data from JSON and load into appropriate locations
                                        String driver_name = data.getJSONArray("response").getJSONObject(i).getJSONObject("driver").getString("name");
                                        String driver_team = data.getJSONArray("response").getJSONObject(i).getJSONObject("team").getString("name");
                                        String driver_points = data.getJSONArray("response").getJSONObject(i).getString("points");
                                        if (driver_points == "null")
                                            driver_points = "0";
                                        String driver_image_url = data.getJSONArray("response").getJSONObject(i).getJSONObject("driver").getString("image");
                                        textView.setText(driver_name + "\n" + driver_team + "\n" + driver_points + " pts");
                                        Picasso.get().load(driver_image_url).resize(192, 192).into(profile_pic);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
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