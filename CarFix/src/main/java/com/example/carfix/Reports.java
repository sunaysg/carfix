package com.example.carfix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Created by Iskren on 14-1-7.
 */
public class Reports extends Activity {
    //DBOperations dbOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports);


        //final ArrayList<CarItem> valuess  = dbOperations.getAllCars();

        Button DateBeggining = (Button)findViewById(R.id.buttonDateBeggining);
        DateBeggining.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportDate reportDate = new ReportDate(Reports.this,1);
                reportDate.show();
                //startActivity(new Intent(Reports.this,ReportDetails.class));
            }
        });

        Button DateEnd = (Button)findViewById(R.id.buttonDateEnd);
        DateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportDate reportDate = new ReportDate(Reports.this,2);
                reportDate.show();
            }
        });

        Button DateStartRepair = (Button)findViewById(R.id.buttonBeginRepair);
        DateStartRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportDate reportDate = new ReportDate(Reports.this,3);
                reportDate.show();
            }
        });

        Button CarNumber = (Button)findViewById(R.id.buttonAutomobile);
        CarNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReportRegNumber reportCar = new ReportRegNumber(Reports.this,4);
                reportCar.show();
            }
        });


        Button NotFinished = (Button)findViewById(R.id.buttonNotFinished);
        NotFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Reports.this, ReportDetails.class).putExtra("REPORT", 5));
            }
        });

        ImageButton btnBack = (ImageButton)findViewById(R.id.imageButton2);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
