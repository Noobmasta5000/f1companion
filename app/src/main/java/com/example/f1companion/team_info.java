package com.example.f1companion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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
            // Get driver data
            data = new JSONObject(bundle.getString("Driver"));
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
    }
}