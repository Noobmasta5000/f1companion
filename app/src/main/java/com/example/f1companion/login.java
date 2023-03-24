package com.example.f1companion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class login extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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




