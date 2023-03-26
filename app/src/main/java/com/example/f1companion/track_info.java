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
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class track_info extends AppCompatActivity {
    static JSONObject data = new JSONObject();
    String id;
    int matching_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_info);

        String string;
        TextView textview;

        // Get data from bundle and load into appropriate locations
        Bundle bundle = getIntent().getExtras();
        try {
            data = new JSONObject(bundle.getString("Track"));
            id = data.getJSONObject("competition").getString("id");
            Log.d("ID", id);

            // Load fastest lap data
            string = data.getJSONObject("circuit").getString("image");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Setup connection to API
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api-formula-1.p.rapidapi.com/circuits")
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
                                TextView textview;
                                // Load track image
                                String track_image_url = data.getJSONArray("response").getJSONObject(matching_id).getString("image");
                                ImageView track_image = findViewById(R.id.track_image);
                                Picasso.get().load(track_image_url).fit().into(track_image);

                                // Load track name
                                String track_name = data.getJSONArray("response").getJSONObject(matching_id).getJSONObject("competition").getString("name")
                                        + "\n" +
                                        data.getJSONArray("response").getJSONObject(matching_id).getString("name");
                                textview = findViewById(R.id.track_name);
                                textview.setText(track_name);

                                // Load track location
                                String track_location = data.getJSONArray("response").getJSONObject(matching_id).getJSONObject("competition").getJSONObject("location").getString("city")
                                        + ",\n" +
                                        data.getJSONArray("response").getJSONObject(matching_id).getJSONObject("competition").getJSONObject("location").getString("country");
                                textview = findViewById(R.id.track_location);
                                textview.setText(track_location);

                                // Load track length
                                String track_total_length = data.getJSONArray("response").getJSONObject(matching_id).getString("race_distance");
                                textview = findViewById(R.id.track_total_length);
                                textview.setText(track_total_length);

                                // Load lap length
                                String track_lap_length = data.getJSONArray("response").getJSONObject(matching_id).getString("length");
                                textview = findViewById(R.id.track_lap_length);
                                textview.setText(track_lap_length);

                                // Load lap count
                                String track_lap_count = data.getJSONArray("response").getJSONObject(matching_id).getString("laps");
                                textview = findViewById(R.id.track_lap_count);
                                textview.setText(track_lap_count);

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