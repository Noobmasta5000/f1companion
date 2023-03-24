package com.example.f1companion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class drivers extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);
        Intent intent = getIntent();

        // Add onclick functionality to favorite buttons
        Button favorite_0 = findViewById(R.id.favorite_0);
        favorite_0.setOnClickListener(this);
        Button favorite_1 = findViewById(R.id.favorite_1);
        favorite_1.setOnClickListener(this);
        Button favorite_2 = findViewById(R.id.favorite_2);
        favorite_2.setOnClickListener(this);
        Button favorite_3 = findViewById(R.id.favorite_3);
        favorite_3.setOnClickListener(this);
        Button favorite_4 = findViewById(R.id.favorite_4);
        favorite_4.setOnClickListener(this);
        Button favorite_5 = findViewById(R.id.favorite_5);
        favorite_5.setOnClickListener(this);
        Button favorite_6 = findViewById(R.id.favorite_6);
        favorite_6.setOnClickListener(this);
        Button favorite_7 = findViewById(R.id.favorite_7);
        favorite_7.setOnClickListener(this);
        Button favorite_8 = findViewById(R.id.favorite_8);
        favorite_8.setOnClickListener(this);
        Button favorite_9 = findViewById(R.id.favorite_9);
        favorite_9.setOnClickListener(this);
        Button favorite_10 = findViewById(R.id.favorite_10);
        favorite_10.setOnClickListener(this);
        Button favorite_11 = findViewById(R.id.favorite_11);
        favorite_11.setOnClickListener(this);
        Button favorite_12 = findViewById(R.id.favorite_12);
        favorite_12.setOnClickListener(this);
        Button favorite_13 = findViewById(R.id.favorite_13);
        favorite_13.setOnClickListener(this);
        Button favorite_14 = findViewById(R.id.favorite_14);
        favorite_14.setOnClickListener(this);
        Button favorite_15 = findViewById(R.id.favorite_15);
        favorite_15.setOnClickListener(this);
        Button favorite_16 = findViewById(R.id.favorite_16);
        favorite_16.setOnClickListener(this);
        Button favorite_17 = findViewById(R.id.favorite_17);
        favorite_17.setOnClickListener(this);
        Button favorite_18 = findViewById(R.id.favorite_18);
        favorite_18.setOnClickListener(this);
        Button favorite_19 = findViewById(R.id.favorite_19);
        favorite_19.setOnClickListener(this);

        // Setup connection to API
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

        Button button= (Button)findViewById(R.id.favorite_0);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // click handling code
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favorite_0:
                Button favorite_0 = findViewById(R.id.favorite_0);
                favorite_0.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                break;
            case R.id.favorite_1:
                // do your code
                break;
            case R.id.favorite_2:
                // do your code
                break;
            case R.id.favorite_3:
                // do your code
                break;
            case R.id.favorite_4:
                // do your code
                break;
            case R.id.favorite_5:
                // do your code
                break;
            case R.id.favorite_6:
                // do your code
                break;
            case R.id.favorite_7:
                // do your code
                break;
            case R.id.favorite_8:
                // do your code
                break;
            case R.id.favorite_9:
                // do your code
                break;
            case R.id.favorite_10:
                // do your code
                break;
            case R.id.favorite_11:
                // do your code
                break;
            case R.id.favorite_12:
                // do your code
                break;
            case R.id.favorite_13:
                // do your code
                break;
            case R.id.favorite_14:
                // do your code
                break;
            case R.id.favorite_15:
                // do your code
                break;
            case R.id.favorite_16:
                // do your code
                break;
            case R.id.favorite_17:
                // do your code
                break;
            case R.id.favorite_18:
                // do your code
                break;
            case R.id.favorite_19:
                // do your code
                break;
            default:
                break;
        }
    }
}