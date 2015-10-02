package com.example.carfix;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by SUNAY on 12/3/13.
 */

public class PartData extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpart);
        //wzemame podadenata poziciq podadena ot Spisyka
        Bundle extras = getIntent().getExtras();
        int NumbPosition=0;
        if (extras != null) {
            NumbPosition = extras.getInt("N_Item");
        }
        DBOperations dbOperations = new DBOperations(this);
        try {
            dbOperations.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<PartItem> temp = new ArrayList<PartItem>();
        temp = dbOperations.getAllParts();
        PartItem theItem = temp.get(NumbPosition);

        //VIZUALIZACIQ I ZAKLUCHWANE
        Button SaveB=(Button) findViewById(R.id.saveb);
        SaveB.setVisibility(View.GONE); // View.VISIBLE  View.INVISIBLE

        final TextView Caption = (TextView)findViewById(R.id.textView2);
        Caption.setText("Part Data");

        final EditText textPID=(EditText) findViewById(R.id.textPID);
        final EditText textPMAN=(EditText) findViewById(R.id.textPMAN);
        final EditText textPRICE=(EditText) findViewById(R.id.textPPRICE);

        textPID.setEnabled(false);
        textPMAN.setEnabled(false);
        textPRICE.setEnabled(false);

        textPID.setBackgroundColor(Color.TRANSPARENT);
        textPMAN.setBackgroundColor(Color.TRANSPARENT);
        textPRICE.setBackgroundColor(Color.TRANSPARENT);

        textPID.setText(theItem.getPName());
        textPMAN.setText(theItem.getMan());
        textPRICE.setText(theItem.getPrice().toString());

        ImageButton btnBack = (ImageButton)findViewById(R.id.imageButton2);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}