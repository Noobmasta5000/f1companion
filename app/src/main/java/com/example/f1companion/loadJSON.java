package com.example.f1companion;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class loadJSON {

    public static JSONObject getAssetJsonData(Context context) throws JSONException {
        String json = null;
        try {
            InputStream is = context.getAssets().open("rankings.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        Log.e("data", json);
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject;

    }
}

// Use this code to load json data from file into JSONObject
// JSONObject data = loadJSON.getAssetJsonData(getApplicationContext());
