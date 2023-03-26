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
import java.lang.reflect.Array;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class teams extends AppCompatActivity {
    static JSONObject data = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        Intent intent = getIntent();

        // Setup connection to API
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api-formula-1.p.rapidapi.com/rankings/teams?season=2023")
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
                            try {
                                for (int i = 0; i < Integer.parseInt(data.getString("results")); i++)
                                {
                                    //Get id of profile pic imageview
                                    String id = "profile_pic_" + i;
                                    int resID = getResources().getIdentifier(id, "id", getPackageName());
                                    ImageView profile_pic = findViewById(resID);

                                    //Get id of basic driver info textview
                                    id = "basic_team_info_" + i;
                                    resID = getResources().getIdentifier(id, "id", getPackageName());
                                    TextView textView = findViewById(resID);

                                    //Get data from JSON and load into appropriate locations
                                    String team_name = data.getJSONArray("response").getJSONObject(i).getJSONObject("team").getString("name");
                                    String team_points = data.getJSONArray("response").getJSONObject(i).getString("points");
                                    if (team_points == "null")
                                        team_points = "0";
                                    String driver_image_url = data.getJSONArray("response").getJSONObject(i).getJSONObject("team").getString("logo");
                                    textView.setText(team_name + "\n" + team_points + " pts");
                                    Picasso.get().load(driver_image_url).fit().into(profile_pic);
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