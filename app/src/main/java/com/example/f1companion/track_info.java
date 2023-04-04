package com.example.f1companion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
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

public class track_info extends menu {
    static JSONObject data = new JSONObject();
    private String id;
    private int matching_id;

    // Weather API stuff
    private String city;
    private static final String API_KEY = "3cf9e8a57fe2dd27dfaab5dfdd1af28d";
    //private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=London&appid=" + API_KEY;
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";
    private TextView temperatureTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_track_info);

        // Get data from bundle and load into appropriate locations
        Bundle bundle = getIntent().getExtras();
        try {
            data = new JSONObject(bundle.getString("Track"));
            id = data.getJSONObject("competition").getString("id");
            TextView textview;
            Log.d("ID", id);

            city = data.getJSONObject("competition").getJSONObject("location").getString("city");
            Log.d("CITY", city);

            // Load track fastest lap this season
            String track_lap_record_season_time = data.getJSONObject("fastest_lap").getString("time");
            if (track_lap_record_season_time.equals("null"))
                track_lap_record_season_time = "No Record";
            textview = findViewById(R.id.track_lap_best);
            textview.setText(track_lap_record_season_time);

            // Load track race day
            String track_race_time = data.getString("date").substring(0, Math.min(data.getString("date").length(), 10));
            textview = findViewById(R.id.track_race_time);
            textview.setText(track_race_time);

            // Load track race status
            String track_race_status = data.getString("status");
            textview = findViewById(R.id.track_race_status);
            textview.setText(track_race_status);

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
                                // Set title to circuit name
                                setTitle(data.getJSONArray("response").getJSONObject(matching_id).getJSONObject("competition").getString("name"));
                                TextView textview;

                                // Load track image
                                String track_image_url = data.getJSONArray("response").getJSONObject(matching_id).getString("image");
                                ImageView track_image = findViewById(R.id.track_image);
                                Picasso.get().load(track_image_url).fit().into(track_image);

                                // Load track name
                                String track_name = data.getJSONArray("response").getJSONObject(matching_id).getJSONObject("competition").getString("name")
                                        + "\n" + data.getJSONArray("response").getJSONObject(matching_id).getString("name");
                                textview = findViewById(R.id.track_name);
                                textview.setText(track_name);

                                // Load track location
                                String track_location = data.getJSONArray("response").getJSONObject(matching_id).getJSONObject("competition").getJSONObject("location").getString("city")
                                        + ",\n" + data.getJSONArray("response").getJSONObject(matching_id).getJSONObject("competition").getJSONObject("location").getString("country");
                                textview = findViewById(R.id.track_location);
                                textview.setText(track_location);

                                //////////////////////////////////
                                //Get name of city for WeatherAPI
                                //city = data.getJSONArray("response").getJSONObject(matching_id).getJSONObject("competition").getJSONObject("location").getString("city");
                                //Log.d("CITY", city);

                                // Load race length
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

                                // Load track lap historical record
                                String track_lap_record_historical_time = data.getJSONArray("response").getJSONObject(matching_id).getJSONObject("lap_record").getString("time");
                                if (track_lap_record_historical_time.equals("null"))
                                    track_lap_record_historical_time = "No Record";
                                textview = findViewById(R.id.track_lap_record);
                                textview.setText(track_lap_record_historical_time);

                                String track_lap_record_historical_driver = data.getJSONArray("response").getJSONObject(matching_id).getJSONObject("lap_record").getString("driver")
                                        + " ("
                                        + data.getJSONArray("response").getJSONObject(matching_id).getJSONObject("lap_record").getString("year") + ")";
                                if (track_lap_record_historical_driver.equals("null (null)"))
                                    track_lap_record_historical_driver = "";
                                textview = findViewById(R.id.track_lap_record_footer);
                                textview.setText(track_lap_record_historical_driver);

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


        ///////////////////////////////////////////////////////////////////////////////////////////////
        // Weather API stuff
        temperatureTextView = findViewById(R.id.temperatureTextView);

        // Testing city
        //String cityName = "New York";
        String cityName = city;
        //Log.d("CITY2", city);
        Log.d("CITY NAME", cityName);
        String url = String.format(BASE_URL, cityName, API_KEY);

        // Request http client
        OkHttpClient client2 = new OkHttpClient();
        Request request2 = new Request.Builder()
                //.url(BASE_URL)
                .url(url)
                .build();

        client2.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // handle network error
                Log.e("MainActivity", "API request failed", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // handle API error
                    return;
                }
                Log.d("CITY2", city);
                String responseBody = response.body().string();

                //function call
                float temperature = parseTemperatureFromResponse(responseBody);

                runOnUiThread(() -> temperatureTextView.setText(String.format("%.1f°C", temperature)));
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////


    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    // Function for Weather API
    private float parseTemperatureFromResponse(String responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONObject mainJsonObject = jsonObject.getJSONObject("main");
            float temperatureKelvin = (float) mainJsonObject.getDouble("temp");
            return temperatureKelvin - 273.15f; // convert from Kelvin to Celsius
        } catch (JSONException e) {
            e.printStackTrace();
            return Float.NaN;
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////

    public void goto_track_map(View view) {
        Intent intent = new Intent(this, track_map.class);
        Bundle bundle = new Bundle();
        try {
            bundle.putString("Track name", data.getJSONArray("response").getJSONObject(matching_id).getString("name"));
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}