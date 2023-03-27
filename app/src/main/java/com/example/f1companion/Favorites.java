package com.example.f1companion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Favorites extends AppCompatActivity {

    FirebaseAuth auth;
    Button button;
    TextView textView;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // calling this activity's function to
        // use ActionBar utility methods
        ActionBar actionBar = getSupportActionBar();

        // adding icon in the ActionBar
        // actionBar.setIcon(R.drawable.app_logo);

        // methods to display the icon in the ActionBar
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

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

    }

    // method to inflate the options menu when
    // the user opens the menu for the first time
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // methods to control the operations that will
    // happen when user clicks on the action buttons
    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {

        switch (item.getItemId()){
            case R.id.settings:
                Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                // Firebase sign out code
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
                Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
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