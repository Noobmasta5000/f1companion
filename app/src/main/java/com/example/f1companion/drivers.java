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
                            for (int i = 0; i <= 19; i++) {
                                try {
                                    //Get id of profile pic imageview
                                    String id = "profile_pic_" + i;
                                    int resID = getResources().getIdentifier(id, "id", getPackageName());
                                    ImageView profile_pic = findViewById(resID);

                                    //Get id of profile pic imageview
                                    id = "ranking_" + i;
                                    resID = getResources().getIdentifier(id, "id", getPackageName());
                                    ImageView ranking = findViewById(resID);

                                    //Get id of basic
                                    id = "basic_driver_info_" + i;
                                    resID = getResources().getIdentifier(id, "id", getPackageName());
                                    TextView textView = findViewById(resID);

                                    //Get data from api and load into appropriate locations
                                    String driver_name = data.getJSONArray("response").getJSONObject(i).getJSONObject("driver").getString("name");
                                    String driver_team =data.getJSONArray("response").getJSONObject(i).getJSONObject("team").getString("name");
                                    String driver_number =data.getJSONArray("response").getJSONObject(i).getJSONObject("driver").getString("number");
                                    textView.setText(driver_name + "\n" + driver_team + "\nDriver number: " + driver_number);
                                    Picasso.get().load("https://media-2.api-sports.io/formula-1/drivers/25.png").into(profile_pic);
                                    Log.d("MESSAGE", driver_name + driver_team + driver_number);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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