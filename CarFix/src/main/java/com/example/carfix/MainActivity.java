package com.example.carfix;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton showcars = (ImageButton)findViewById(R.id.buttonCars);
        showcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,CarList.class));
            }
        });

        ImageButton showparts = (ImageButton)findViewById(R.id.buttonParts);
        showparts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PartList.class));
            }
        });

        ImageButton showcards = (ImageButton)findViewById(R.id.buttonCards);
        showcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RepCardList.class));
            }
        });

        ImageButton showReports = (ImageButton)findViewById(R.id.buttonReports);
        showReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Reports.class));
            }
        });

        ImageView showMeInfo = (ImageView)findViewById(R.id.imageView);
        showMeInfo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MainActivity.this,AboutUs.class));
                return false;
            }
        });


    }
}
