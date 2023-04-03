package com.example.f1companion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class team_info extends menu {
    private static JSONObject data = new JSONObject();
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_team_info);
        Bundle bundle = getIntent().getExtras();

        try {
            // Get team data
            data = new JSONObject(bundle.getString("Team"));
            id = data.getJSONObject("team").getString("id");

            /* Load season results
            TextView text;
            String content;
            text = findViewById(R.id.team_points);
            content = data.getString("points");
            text.setText("\n" + content + " pts\n");
            */

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Setup connection to API
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api-formula-1.p.rapidapi.com/teams?id=" + id)
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
                            try {
                                // Set title to team name
                                setTitle(data.getJSONArray("response").getJSONObject(0).getString("name"));

                                ImageView image;
                                TextView text;
                                String content;

                                // Load team image
                                image = findViewById(R.id.team_image);
                                content = data.getJSONArray("response").getJSONObject(0).getString("logo");
                                Picasso.get().load(content).fit().into(image);

                                // Load team name, location
                                text = findViewById(R.id.team_name);
                                content = data.getJSONArray("response").getJSONObject(0).getString("name") + "\n\n"
                                        + data.getJSONArray("response").getJSONObject(0).getString("base");
                                text.setText(content);

                                // Load team principal
                                text = findViewById(R.id.team_principal);
                                content = data.getJSONArray("response").getJSONObject(0).getString("director");
                                text.setText(content);

                                // Load team president
                                text = findViewById(R.id.team_president);
                                content = data.getJSONArray("response").getJSONObject(0).getString("president");
                                text.setText(content);

                                // Load team teachnical manager
                                text = findViewById(R.id.team_technical_manager);
                                content = data.getJSONArray("response").getJSONObject(0).getString("technical_manager");
                                text.setText(content);

                                // Load team entry year
                                text = findViewById(R.id.team_entry);
                                content = data.getJSONArray("response").getJSONObject(0).getString("first_team_entry");
                                text.setText(content);

                                // Load team championships won
                                text = findViewById(R.id.team_championships);
                                content = data.getJSONArray("response").getJSONObject(0).getString("world_championships");
                                text.setText(content);

                                // Load team races won
                                text = findViewById(R.id.team_wins);
                                content = data.getJSONArray("response").getJSONObject(0).getJSONObject("highest_race_finish").getString("number");
                                text.setText(content);

                                // Load team pole positions
                                text = findViewById(R.id.team_pole_positions);
                                content = data.getJSONArray("response").getJSONObject(0).getString("pole_positions");
                                text.setText(content);

                                // Load team fastest laps
                                text = findViewById(R.id.team_fastest_laps);
                                content = data.getJSONArray("response").getJSONObject(0).getString("fastest_laps");
                                text.setText(content);

                                // Load team engine supplier
                                text = findViewById(R.id.team_engine);
                                content = data.getJSONArray("response").getJSONObject(0).getString("engine");
                                text.setText(content);

                                // Load team chassis
                                text = findViewById(R.id.team_chassis);
                                content = data.getJSONArray("response").getJSONObject(0).getString("chassis");
                                text.setText(content);

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