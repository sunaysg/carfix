package com.example.carfix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by SUNAY on 12/3/13.
 */
public class AddPart extends Activity {
    DBOperations dbOperations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpart);

        dbOperations = new DBOperations(this);
        try {
            dbOperations.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final EditText textPID=(EditText) findViewById(R.id.textPID);
        final EditText textPMAN=(EditText) findViewById(R.id.textPMAN);
        final EditText textPRICE=(EditText) findViewById(R.id.textPPRICE);
        
        Button SaveB=(Button) findViewById(R.id.saveb);
        SaveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textPID.getText().toString().equals(""))
                    Toast.makeText(AddPart.this, "Enter Part Name", Toast.LENGTH_LONG).show();
                else if(textPMAN.getText().toString().equals(""))
                    Toast.makeText(AddPart.this, "Enter Man Name", Toast.LENGTH_LONG).show();
                else if(textPRICE.getText().toString().equals(""))
                    Toast.makeText(AddPart.this, "Enter Price", Toast.LENGTH_LONG).show();
                else
                {
                    dbOperations.newPart(textPID.getText().toString(),
                            Double.parseDouble(textPRICE.getText().toString()),
                            textPMAN.getText().toString() );

                    Toast.makeText(AddPart.this, "Adding is successful!", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(AddPart.this,PartList.class));
                    finish();
                }}
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
