package com.example.f1companion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
                                    Context context = getApplicationContext();

                                    // Setup Linear Layout
                                    LinearLayout linearLayout = new LinearLayout(context);
                                    LinearLayout.LayoutParams linearlayout_layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    linearLayout.setId(i);
                                    linearLayout.setLayoutParams(linearlayout_layoutparams);

                                    linearLayout.setOnClickListener(new LinearLayout.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            goto_track_info(view);
                                        }
                                    });

                                    // Setup track number
                                    TextView track_number = new TextView(context);
                                    ViewGroup.LayoutParams track_number_layoutparams = new ViewGroup.LayoutParams(192, 192);
                                    track_number.setTextSize(TypedValue.COMPLEX_UNIT_PX,144);
                                    track_number.setGravity(Gravity.CENTER);
                                    track_number.setLayoutParams(track_number_layoutparams);
                                    track_number.setText(Integer.toString(i+1));

                                    // Setup track image
                                    ImageView track_image = new ImageView(context);
                                    ViewGroup.LayoutParams track_image_layoutparams = new ViewGroup.LayoutParams(340, 192);
                                    track_image.setLayoutParams(track_image_layoutparams);
                                    String track_image_url = data.getJSONArray("response").getJSONObject(i).getJSONObject("circuit").getString("image");
                                    Picasso.get().load(track_image_url).fit().into(track_image);

                                    // Setup track info
                                    TextView track_info = new TextView(context);
                                    ViewGroup.LayoutParams track_info_layoutparams = new ViewGroup.LayoutParams(548, ViewGroup.LayoutParams.MATCH_PARENT);
                                    track_info.setGravity(Gravity.CENTER_VERTICAL);
                                    track_info.setLayoutParams(track_info_layoutparams);
                                    String track_name = data.getJSONArray("response").getJSONObject(i).getJSONObject("competition").getString("name")
                                            + "\n" + data.getJSONArray("response").getJSONObject(i).getJSONObject("circuit").getString("name");
                                    track_info.setText(track_name);

                                    // Add views to linearlayout and scrollview
                                    linearLayout.addView(track_number);
                                    linearLayout.addView(track_image);
                                    linearLayout.addView(track_info);
                                    LinearLayout ll = findViewById(R.id.track_list);
                                    ll.addView(linearLayout);
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

        int num = view.getId();

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