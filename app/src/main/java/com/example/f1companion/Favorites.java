package com.example.f1companion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Favorites extends menu {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String userID;

    static ArrayList<String> favorite_drivers = new ArrayList<String>();
    static ArrayList<String> favorite_teams = new ArrayList<String>();;
    private JSONObject drivers = new JSONObject();
    private JSONObject teams = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_favorites);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        userID = user.getUid();


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                // Retrieve Favorite Drivers list
                String receivedDrivers = dataSnapshot.child(userID).child("Favorite Drivers").getValue(String.class);
                String[] receivedArr = receivedDrivers.split(", ");

                ArrayList<String> receivedList = new ArrayList<String>();

                for(String s : receivedArr){
                    receivedList.add(s);
                }

                // Retrieve Favorite Teams list
                String receivedTeams = dataSnapshot.child(userID).child("Favorite Teams").getValue(String.class);
                String[] teamsArr = receivedTeams.split(", ");

                ArrayList<String> teamsList = new ArrayList<String>();

                for(String s : teamsArr){
                    teamsList.add(s);
                }

                // Set favorite_drivers and favorite_teams to respective ArrayList<String>
                favorite_drivers = receivedList;
                favorite_teams = teamsList;

                // Log.d for debugging
                Log.d("RETRIEVED DRIVERS DATA", receivedDrivers);
                Log.d("CONVERTED DRIVERS LIST", receivedList.toString());
                Log.d("DRIVERS LIST DATA", favorite_drivers.toString());

                Log.d("RETRIEVED TEAMS DATA", receivedTeams);
                Log.d("CONVERTED TEAMS LIST", teamsList.toString());
                Log.d("TEAMS LIST DATA", favorite_teams.toString());

                // Retrieve theme
                Utils.sTheme = Integer.parseInt(dataSnapshot.child(userID).child("Theme").getValue(String.class));
                Log.d("THEME", Integer.toString(Utils.sTheme));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException());
            }
        });

        //Log.d("LIST DATA", favorite_drivers.toString());


        /*
        if(user == null){
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();
        }
        else{
            textView.setText(user.getEmail());
        }
        */

        // Setup connection to API
        OkHttpClient client1 = new OkHttpClient();
        Request request1 = new Request.Builder()
                .url("https://api-formula-1.p.rapidapi.com/rankings/drivers?season=2023")
                .get()
                .addHeader("X-RapidAPI-Key", "0801c0f8camshbbda9eeceafe698p181139jsn5ca56dcbfb99")
                .addHeader("X-RapidAPI-Host", "api-formula-1.p.rapidapi.com")
                .build();
        client1.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                Log.d("MESSAGE", "FAILURE");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    drivers = new JSONObject(response.body().string());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int j = 0; j < favorite_drivers.size(); j++)
                                {
                                    for (int i = 0; i < Integer.parseInt(drivers.getString("results")); i++) {
                                        try {
                                            if (favorite_drivers.get(j).equals(drivers.getJSONArray("response").getJSONObject(i).getJSONObject("driver").getString("id")))
                                            {
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
                                                String driver_image_url = drivers.getJSONArray("response").getJSONObject(i).getJSONObject("driver").getString("image");
                                                Picasso.get().load(driver_image_url).resize(192,192).into(driver_image);

                                                // Setup driver info
                                                TextView driver_info = new TextView(context);
                                                ViewGroup.LayoutParams driver_info_layoutparams = new ViewGroup.LayoutParams(600, ViewGroup.LayoutParams.MATCH_PARENT);
                                                driver_info.setGravity(Gravity.CENTER_VERTICAL);
                                                driver_info.setLayoutParams(driver_info_layoutparams);
                                                String driver_name = drivers.getJSONArray("response").getJSONObject(i).getJSONObject("driver").getString("name");
                                                String driver_team = drivers.getJSONArray("response").getJSONObject(i).getJSONObject("team").getString("name");
                                                String driver_points = drivers.getJSONArray("response").getJSONObject(i).getString("points");
                                                if (driver_points == "null")
                                                    driver_points = "0";
                                                driver_info.setText(driver_name + "\n" + driver_team + "\n" + driver_points + " pts");

                                                // Add views to linearlayout and scrollview
                                                linearLayout.addView(driver_position);
                                                linearLayout.addView(driver_image);
                                                linearLayout.addView(driver_info);
                                                LinearLayout ll = findViewById(R.id.favorite_drivers_linearlayout);
                                                ll.addView(linearLayout);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
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

        // Setup connection to API
        OkHttpClient client2 = new OkHttpClient();
        Request request2 = new Request.Builder()
                .url("https://api-formula-1.p.rapidapi.com/rankings/teams?season=2023")
                .get()
                .addHeader("X-RapidAPI-Key", "0801c0f8camshbbda9eeceafe698p181139jsn5ca56dcbfb99")
                .addHeader("X-RapidAPI-Host", "api-formula-1.p.rapidapi.com")
                .build();
        client2.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                Log.d("MESSAGE", "FAILURE");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    teams = new JSONObject(response.body().string());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int j = 0; j < favorite_teams.size(); j++)
                                {
                                    for (int i = 0; i < Integer.parseInt(teams.getString("results")); i++) {
                                        try {
                                            if (favorite_teams.get(j).equals(teams.getJSONArray("response").getJSONObject(i).getJSONObject("team").getString("id")))
                                            {
                                                Context context = getApplicationContext();

                                                // Setup Linear Layout
                                                LinearLayout linearLayout = new LinearLayout(context);
                                                LinearLayout.LayoutParams linearlayout_layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                                linearLayout.setLayoutParams(linearlayout_layoutparams);

                                                // Setup team position
                                                TextView team_position = new TextView(context);
                                                ViewGroup.LayoutParams driver_postion_layoutparams = new ViewGroup.LayoutParams(192, 192);
                                                team_position.setTextSize(TypedValue.COMPLEX_UNIT_PX,144);
                                                team_position.setGravity(Gravity.CENTER);
                                                team_position.setLayoutParams(driver_postion_layoutparams);
                                                team_position.setText(Integer.toString(i+1));
                                                switch(i+1) {
                                                    case 1:
                                                        team_position.setTextColor(ContextCompat.getColor(context, R.color.gold));
                                                        break;
                                                    case 2:
                                                        team_position.setTextColor(ContextCompat.getColor(context, R.color.silver));
                                                        break;
                                                    case 3:
                                                        team_position.setTextColor(ContextCompat.getColor(context, R.color.bronze));
                                                        break;
                                                    default:
                                                }

                                                // Setup team image
                                                ImageView team_image = new ImageView(context);
                                                ViewGroup.LayoutParams driver_image_layoutparams = new ViewGroup.LayoutParams(436, 192);
                                                team_image.setLayoutParams(driver_image_layoutparams);
                                                String team_image_url = teams.getJSONArray("response").getJSONObject(i).getJSONObject("team").getString("logo");
                                                Picasso.get().load(team_image_url).resize(340,192).into(team_image);

                                                // Setup team info
                                                TextView team_info = new TextView(context);
                                                ViewGroup.LayoutParams driver_info_layoutparams = new ViewGroup.LayoutParams(452, ViewGroup.LayoutParams.MATCH_PARENT);
                                                team_info.setGravity(Gravity.CENTER_VERTICAL);
                                                team_info.setLayoutParams(driver_info_layoutparams);
                                                String team_name = teams.getJSONArray("response").getJSONObject(i).getJSONObject("team").getString("name");
                                                String team_points = teams.getJSONArray("response").getJSONObject(i).getString("points");
                                                if (team_points == "null")
                                                    team_points = "0";
                                                team_info.setText(team_name + "\n" + team_points + " pts");

                                                // Add views to linearlayout and scrollview
                                                linearLayout.addView(team_position);
                                                linearLayout.addView(team_image);
                                                linearLayout.addView(team_info);
                                                LinearLayout ll = findViewById(R.id.favorite_teams_linearlayout);
                                                ll.addView(linearLayout);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
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