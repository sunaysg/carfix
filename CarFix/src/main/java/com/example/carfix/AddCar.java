package com.example.carfix;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by SUNAY on 11/30/13.
 */
public class AddCar extends Activity {
    DBOperations dbOperations;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcar);

        dbOperations = new DBOperations(this);
        try {
            dbOperations.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final Spinner spiner = (Spinner)findViewById(R.id.textCOLOR);
        final String[] choices = {"White","Black","Silver","Red","Gray","Blue","Beige","Brown","Green","Yellow","Gold","Other"};
        ArrayAdapter<String> a =new ArrayAdapter<String>(AddCar.this,android.R.layout.simple_spinner_item, choices);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(a);


        final EditText textRNUMBER=(EditText) findViewById(R.id.textRNUMBER);
        final EditText textBRAND=(EditText) findViewById(R.id.textBRAND);
        final EditText textMODEL=(EditText) findViewById(R.id.textMODEL);
        final EditText textYEARPR=(EditText) findViewById(R.id.textYEARPR);
        final EditText textENUMBER=(EditText) findViewById(R.id.textENUMBER);
        final EditText textBNUMBER=(EditText) findViewById(R.id.textBNUMBER);
        //final EditText textCOLOR=(EditText) findViewById(R.id.textCOLOR);

        final EditText textCUBICS=(EditText) findViewById(R.id.textCUBICS);
        final EditText textINFO=(EditText) findViewById(R.id.textINFO);
        final EditText textOWNER=(EditText) findViewById(R.id.textOWNER);
        final EditText textPHONE=(EditText) findViewById(R.id.textPHONE);

       // textRNUMBER.setBackgroundColor(Color.rgb(100,255,80));
       // textENUMBER.setBackgroundColor(Color.rgb(100,255,80));
       // textBNUMBER.setBackgroundColor(Color.rgb(100,255,80));

        Button SaveB=(Button) findViewById(R.id.saveb);
        SaveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textRNUMBER.getText().toString().equals(""))
                    Toast.makeText(AddCar.this, "Enter Registration Number", Toast.LENGTH_LONG).show();
                else if(textENUMBER.getText().toString().equals(""))
                    Toast.makeText(AddCar.this, "Enter Engine Number", Toast.LENGTH_LONG).show();
                else if(textBNUMBER.getText().toString().equals(""))
                    Toast.makeText(AddCar.this, "Enter Body Number", Toast.LENGTH_LONG).show();
                else
                {
                    if(textYEARPR.getText().toString().equals(""))
                    {textYEARPR.setText("0");}
                    if(textCUBICS.getText().toString().equals(""))
                    {textCUBICS.setText("0");}
                    dbOperations.newCar(new CarItem(textRNUMBER.getText().toString(),
                        textBRAND.getText().toString(),
                        textMODEL.getText().toString(),
                        Integer.parseInt(textYEARPR.getText().toString()),
                        textENUMBER.getText().toString(),
                        textBNUMBER.getText().toString(),
                        spiner.getSelectedItem().toString(),
                        Integer.parseInt(textCUBICS.getText().toString()),
                        textINFO.getText().toString(),
                        textOWNER.getText().toString(),
                        textPHONE.getText().toString()));

                    Toast.makeText(AddCar.this, "Adding is successful!", Toast.LENGTH_LONG).show();


                    AlertDialog.Builder confirm=new AlertDialog.Builder(AddCar.this);
                    confirm.setTitle("Repair Card");
                    confirm.setMessage("Do you want to create a Repair Card for the automobile?");
                    confirm.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(AddCar.this,AddCard.class);
                            i.putExtra("REGNUMB", textRNUMBER.getText().toString());
                            startActivity(i);
                            finish();
                        }
                    });
                    confirm.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    confirm.show();


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
