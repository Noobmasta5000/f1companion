package com.example.f1companion;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.firebase.auth.FirebaseAuth;

public class menu extends AppCompatActivity {

    // method to inflate the options menu when
    // the user opens the menu for the first time
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate(R.menu.main, menu);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        return super.onCreateOptionsMenu(menu);
    }

    // methods to control the operations that will
    // happen when user clicks on the action buttons
    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {
        Intent intent;

        switch (item.getItemId()){
            case R.id.home:
                intent = new Intent(this, Favorites.class);
                startActivity(intent);
                break;
            case R.id.themes:
                Toast.makeText(this, "Choose a theme", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                // Firebase sign out code
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
                Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
                break;
            case R.id.theme_1:
                Utils.changeToTheme(this, Utils.THEME_ALFA_ROMEO);
                Toast.makeText(this, "Alfa Romeo Theme Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.theme_2:
                Utils.changeToTheme(this, Utils.THEME_ALPHA_TAURI);
                Toast.makeText(this, "AlphaTauri Theme Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.theme_3:
                Utils.changeToTheme(this, Utils.THEME_ALPINE);
                Toast.makeText(this, "Alpine Theme Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.theme_4:
                Utils.changeToTheme(this, Utils.THEME_ASTON_MARTIN);
                Toast.makeText(this, "Aston Martin Theme Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.theme_5:
                Utils.changeToTheme(this, Utils.THEME_FERRARI);
                Toast.makeText(this, "Ferrari Theme Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.theme_6:
                Utils.changeToTheme(this, Utils.THEME_HAAS);
                Toast.makeText(this, "Haas Theme Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.theme_7:
                Utils.changeToTheme(this, Utils.THEME_MCLAREN);
                Toast.makeText(this, "McLaren Theme Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.theme_8:
                Utils.changeToTheme(this, Utils.THEME_MERCEDES);
                Toast.makeText(this, "Mercedes Theme Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.theme_9:
                Utils.changeToTheme(this, Utils.THEME_RED_BULL);
                Toast.makeText(this, "Red Bull Theme Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.theme_10:
                Utils.changeToTheme(this, Utils.THEME_WILLIAMS);
                Toast.makeText(this, "Willaims Theme Selected", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
