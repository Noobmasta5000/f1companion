package com.example.f1companion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class tracks extends menu {
    static JSONObject data = new JSONObject();
    static ArrayList<String> favorite_drivers = new ArrayList<String>();
    static ArrayList<String> favorite_teams = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_tracks);

        // Setup connection to API
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api-formula-1.p.rapidapi.com/races?type=race&season=2023")
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
                                    // Get id of profile pic imageview
                                    String id = "profile_pic_" + i;
                                    int resID = getResources().getIdentifier(id, "id", getPackageName());
                                    ImageView track_pic = findViewById(resID);

                                    // Get id of basic track info textview
                                    id = "basic_track_info_" + i;
                                    resID = getResources().getIdentifier(id, "id", getPackageName());
                                    TextView textView = findViewById(resID);

                                    // Get data from JSON and load into appropriate locations
                                    String competition_name = data.getJSONArray("response").getJSONObject(i).getJSONObject("competition").getString("name");
                                    String track_name = data.getJSONArray("response").getJSONObject(i).getJSONObject("circuit").getString("name");

                                    String track_image_url = data.getJSONArray("response").getJSONObject(i).getJSONObject("circuit").getString("image");
                                    textView.setText(competition_name + "\n" + track_name);
                                    Picasso.get().load(track_image_url).fit().into(track_pic);
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

    public void goto_track_info(View view) {

        int num = Integer.parseInt(getResources().getResourceEntryName(view.getId()).replace("track_",""));

        try {
            String track_info = data.getJSONArray("response").getJSONObject(num).toString();
            Intent intent = new Intent(this, track_info.class);
            Bundle bundle = new Bundle();
            bundle.putString("Track", track_info);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void goto_drivers(View view) {
        Intent intent = new Intent(this, drivers.class);
        startActivity(intent);
    }

    public void goto_teams(View view) {
        Intent intent = new Intent(this, teams.class);
        startActivity(intent);
    }

    public void goto_tracks(View view) {
        Intent intent = new Intent(this, tracks.class);
        startActivity(intent);
    }
}