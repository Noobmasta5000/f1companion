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
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_driver_info);
        Bundle bundle = getIntent().getExtras();

        try {
            // Get driver data
            data = new JSONObject(bundle.getString("Driver"));
            id = data.getJSONObject("driver").getString("id");

            // Load season results
            TextView text;
            String content;
            text = findViewById(R.id.season_points);
            content = data.getString("points");
            if (content == "null")
                content = "0";
            text.setText("\n" + content + " pts\n");

            text = findViewById(R.id.season_wins);
            content = data.getString("wins");
            if (content == "null")
                content = "0";
            text.setText(content + " wins\n");

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
                            try {
                                // Set title to driver name
                                setTitle(data.getJSONArray("response").getJSONObject(0).getString("name"));

                                ImageView image;
                                TextView text;
                                String content;

                                // Load driver image
                                image = findViewById(R.id.driver_image);
                                content = data.getJSONArray("response").getJSONObject(0).getString("image");
                                Picasso.get().load(content).fit().into(image);

                                // Load team image
                                image = findViewById(R.id.team_image);
                                content = data.getJSONArray("response").getJSONObject(0).getJSONArray("teams").getJSONObject(0).getJSONObject("team").getString("logo");
                                Picasso.get().load(content).fit().into(image);

                                // Load driver name, abbreviation, number, team
                                text = findViewById(R.id.driver_name);
                                content = data.getJSONArray("response").getJSONObject(0).getString("name") + "\n\n"
                                        + data.getJSONArray("response").getJSONObject(0).getString("abbr") + " | " + data.getJSONArray("response").getJSONObject(0).getString("number") + "\n\n"
                                        + data.getJSONArray("response").getJSONObject(0).getJSONArray("teams").getJSONObject(0).getJSONObject("team").getString("name");
                                text.setText(content);

                                // Load historical results (points, wins, podiums, championships, races)
                                text = findViewById(R.id.historical_points);
                                content = data.getJSONArray("response").getJSONObject(0).getString("career_points");
                                if (content == "null")
                                    content = "0";
                                text.setText("\n" + content + " pts\n");

                                text = findViewById(R.id.historical_wins);
                                if (data.getJSONArray("response").getJSONObject(0).getJSONObject("highest_race_finish").getString("position") == "1")
                                    content = data.getJSONArray("response").getJSONObject(0).getJSONObject("highest_race_finish").getString("number");
                                else
                                    content = "0";
                                text.setText(content + " wins\n");

                                text = findViewById(R.id.historical_podiums);
                                content = data.getJSONArray("response").getJSONObject(0).getString("podiums");
                                if (content == "null")
                                    content = "0";
                                text.setText(content + " podiums\n");

                                text = findViewById(R.id.historical_races);
                                content = data.getJSONArray("response").getJSONObject(0).getString("grands_prix_entered");
                                if (content == "null")
                                    content = "0";
                                text.setText(content + " Races\n");

                                text = findViewById(R.id.historical_championships);
                                content = data.getJSONArray("response").getJSONObject(0).getString("world_championships");
                                text.setText(content + " Championships\n");

                                // Load birth info (nationality, birthdate, birthplace, country)
                                text = findViewById(R.id.nationality);
                                content = data.getJSONArray("response").getJSONObject(0).getString("nationality");
                                text.setText("\n" + content + "\n");

                                text = findViewById(R.id.birthdate);
                                content = data.getJSONArray("response").getJSONObject(0).getString("birthdate");
                                text.setText(content + "\n");

                                text = findViewById(R.id.birthplace);
                                content = data.getJSONArray("response").getJSONObject(0).getString("birthplace");
                                text.setText(content + "\n");

                                text = findViewById(R.id.country);
                                content = data.getJSONArray("response").getJSONObject(0).getJSONObject("country").getString("name") + " | "
                                        + data.getJSONArray("response").getJSONObject(0).getJSONObject("country").getString("code");
                                text.setText(content + "\n");

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