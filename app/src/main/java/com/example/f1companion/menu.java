package com.example.f1companion;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class menu extends AppCompatActivity {

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
        Intent intent;
        Context context = getApplicationContext();

        switch (item.getItemId()){
            case R.id.home:
                intent = new Intent(this, Favorites.class);
                startActivity(intent);
                break;
            case R.id.settings:
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

                break;
            case R.id.theme_2:

                break;
            case R.id.theme_3:

                break;
            case R.id.theme_4:

                break;
            case R.id.theme_5:

                break;
            case R.id.theme_6:

                break;
            case R.id.theme_7:

                break;
            case R.id.theme_8:
                setTheme(R.style.mercedes);
                Toast.makeText(this, "Mercedes Theme Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.theme_9:

                break;
            case R.id.theme_10:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
