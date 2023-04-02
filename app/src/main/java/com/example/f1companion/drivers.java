package com.example.f1companion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class drivers extends menu {
    static JSONObject data = new JSONObject();
    static ArrayList<String> favorite_drivers = new ArrayList<String>();
    static ArrayList<String> favorite_teams = new ArrayList<String>();

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference myRef;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);

        // Read favorites list from firebase


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
                    data = new JSONObject(response.body().string());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int i = 0; i < Integer.parseInt(data.getString("results")); i++) {
                                    try {
                                        Context context = getApplicationContext();

                                        // Setup Linear Layout
                                        LinearLayout linearLayout = new LinearLayout(context);
                                        LinearLayout.LayoutParams linearlayout_layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                        linearLayout.setLayoutParams(linearlayout_layoutparams);

                                        // Setup driver position
                                        TextView driver_position = new TextView(context);
                                        ViewGroup.LayoutParams driver_postion_layoutparams = new ViewGroup.LayoutParams(192, 192);
                                        driver_position.setTextSize(TypedValue.COMPLEX_UNIT_PX,144);
                                        driver_position.setGravity(Gravity.CENTER);
                                        driver_position.setLayoutParams(driver_postion_layoutparams);
                                        driver_position.setText(Integer.toString(i+1));
                                        switch(i+1) {
                                            case 1:
                                                driver_position.setTextColor(ContextCompat.getColor(context, R.color.gold));
                                                break;
                                            case 2:
                                                driver_position.setTextColor(ContextCompat.getColor(context, R.color.silver));
                                                break;
                                            case 3:
                                                driver_position.setTextColor(ContextCompat.getColor(context, R.color.bronze));
                                                break;
                                            default:
                                        }

                                        // Setup driver image
                                        ImageView driver_image = new ImageView(context);
                                        ViewGroup.LayoutParams driver_image_layoutparams = new ViewGroup.LayoutParams(288, 192);
                                        driver_image.setLayoutParams(driver_image_layoutparams);
                                        String driver_image_url = data.getJSONArray("response").getJSONObject(i).getJSONObject("driver").getString("image");
                                        Picasso.get().load(driver_image_url).resize(192,192).into(driver_image);

                                        // Setup driver info
                                        TextView driver_info = new TextView(context);
                                        ViewGroup.LayoutParams driver_info_layoutparams = new ViewGroup.LayoutParams(504, ViewGroup.LayoutParams.MATCH_PARENT);
                                        driver_info.setGravity(Gravity.CENTER_VERTICAL);
                                        driver_info.setLayoutParams(driver_info_layoutparams);
                                        String driver_name = data.getJSONArray("response").getJSONObject(i).getJSONObject("driver").getString("name");
                                        String driver_team = data.getJSONArray("response").getJSONObject(i).getJSONObject("team").getString("name");
                                        String driver_points = data.getJSONArray("response").getJSONObject(i).getString("points");
                                        if (driver_points == "null")
                                            driver_points = "0";
                                        driver_info.setText(driver_name + "\n" + driver_team + "\n" + driver_points + " pts");

                                        // Setup favorite checkbox
                                        CheckBox favorite_button = new CheckBox(context);
                                        ViewGroup.LayoutParams favorite_button_layoutparams = new ViewGroup.LayoutParams(96, ViewGroup.LayoutParams.MATCH_PARENT);
                                        favorite_button.setGravity(Gravity.CENTER);
                                        favorite_button.setLayoutParams(favorite_button_layoutparams);
                                        if (favorite_drivers.contains(data.getJSONArray("response").getJSONObject(i).getJSONObject("driver").getString("id")))
                                        {
                                            favorite_button.setChecked(true);
                                            favorite_button.setButtonDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
                                        } else {
                                            favorite_button.setButtonDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                                        }
                                        int finalI = i;
                                        favorite_button.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                if(favorite_button.isChecked()) {
                                                    try {
                                                        favorite_drivers.add(data.getJSONArray("response").getJSONObject(finalI).getJSONObject("driver").getString("id"));
                                                        favorite_button.setButtonDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
                                                        //Toast.makeText(this, "Added " + data.getJSONArray("response").getJSONObject(finalI).getJSONObject("driver").getString("name") + " to favorites", Toast.LENGTH_SHORT).show();
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                } else {
                                                    String id = null;
                                                    try {
                                                        id = data.getJSONArray("response").getJSONObject(finalI).getJSONObject("driver").getString("id");
                                                        favorite_drivers.remove(id);
                                                        favorite_button.setButtonDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                                                        //Toast.makeText(this, "Added " + data.getJSONArray("response").getJSONObject(finalI).getJSONObject("driver").getString("name") + " to favorites", Toast.LENGTH_SHORT).show();
                                                    } catch (JSONException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                }
                                                // Add code to save favorites list to firebase
                                                mAuth = FirebaseAuth.getInstance();
                                                database = FirebaseDatabase.getInstance();
                                                myRef = database.getReference();
                                                user = mAuth.getCurrentUser();
                                                String userID = user.getUid();
                                                String new_drivers = favorite_drivers.toString();
                                                new_drivers = new_drivers.replace("[", "");
                                                new_drivers = new_drivers.replace("]", "");

                                                //myRef.setValue(new_drivers);
                                                myRef.child(userID).child("Favorite Drivers").setValue(new_drivers);

                                                Log.d("USER ID", userID);
                                                Log.d("FAVORITES",favorite_drivers.toString());
                                                Log.d("new drivers",new_drivers);


                                            }
                                        });



                                        // Add views to linearlayout and scrollview
                                        linearLayout.addView(driver_position);
                                        linearLayout.addView(driver_image);
                                        linearLayout.addView(driver_info);
                                        linearLayout.addView(favorite_button);
                                        LinearLayout ll = findViewById(R.id.driver_list);
                                        ll.addView(linearLayout);
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