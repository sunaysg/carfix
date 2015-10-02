package com.example.carfix;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by SUNAY on 12/3/13.
 */
public class EditCar extends Activity {

    DBOperations dbOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcar);
        //wzemame podadenata poziciq podadena ot Spisyka
        Bundle extras = getIntent().getExtras();
        int NumbPosition=0;
        if (extras != null) {
            NumbPosition = extras.getInt("N_Item");
            //NumbPosition = extras.getString("name");
            // and get whatever type user account id is
        }
        dbOperations = new DBOperations(this);
        try {
            dbOperations.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<CarItem> temp = new ArrayList<CarItem>();
        temp = dbOperations.getAllCars();
        CarItem theItem = temp.get(NumbPosition);
        //VIZUALIZACIQ I ZAKLUCHWANE
        Button SaveB=(Button) findViewById(R.id.saveb);
        final TextView Caption = (TextView)findViewById(R.id.textView2);

        final Spinner spiner = (Spinner)findViewById(R.id.textCOLOR);
        final String[] choices = {"Pink","Red"};
        ArrayAdapter<String> a =new ArrayAdapter<String>(EditCar.this,android.R.layout.simple_spinner_item, choices);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(a);

        for(int p=0; p<choices.length; p++)
            if (theItem.getCColor().equals(choices[p])){
                spiner.setSelection(p);
                break;
            }

        Caption.setText("Edit Car");
        final EditText textRNUMBER=(EditText) findViewById(R.id.textRNUMBER);
        final EditText textBRAND=(EditText) findViewById(R.id.textBRAND);
        final EditText textMODEL=(EditText) findViewById(R.id.textMODEL);
        final EditText textYEARPR=(EditText) findViewById(R.id.textYEARPR);
        final EditText textENUMBER=(EditText) findViewById(R.id.textENUMBER);
        final EditText textBNUMBER=(EditText) findViewById(R.id.textBNUMBER);
        final EditText textCUBICS=(EditText) findViewById(R.id.textCUBICS);
        final EditText textINFO=(EditText) findViewById(R.id.textINFO);
        final EditText textOWNER=(EditText) findViewById(R.id.textOWNER);
        final EditText textPHONE=(EditText) findViewById(R.id.textPHONE);

        textRNUMBER.setEnabled(false);

        textRNUMBER.setText(theItem.getRegNumber());
        textBRAND.setText(theItem.getBrand());
        textMODEL.setText(theItem.getModel());
        textYEARPR.setText(String.valueOf(theItem.getYearProd()));
        textENUMBER.setText(theItem.getEngNumber());
        textBNUMBER.setText(theItem.getBdyNumber());
        textCUBICS.setText(String.valueOf(theItem.getCubics()) );
        textINFO.setText(theItem.getInfo());
        textOWNER.setText(theItem.getOwner());
        textPHONE.setText(theItem.getPhone());

        //textRNUMBER.setBackgroundColor(Color.rgb(100, 255, 80));
        //textENUMBER.setBackgroundColor(Color.rgb(100,255,80));
        //textBNUMBER.setBackgroundColor(Color.rgb(100,255,80));

        Button SaveBB=(Button) findViewById(R.id.saveb);
        SaveBB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textRNUMBER.getText().toString().equals(""))
                    Toast.makeText(EditCar.this, "Enter Registration Number", Toast.LENGTH_LONG).show();
                else if(textENUMBER.getText().toString().equals(""))
                    Toast.makeText(EditCar.this, "Enter Engine Number", Toast.LENGTH_LONG).show();
                else if(textBNUMBER.getText().toString().equals(""))
                    Toast.makeText(EditCar.this, "Enter Body Number", Toast.LENGTH_LONG).show();
                else
                {
                    if(textYEARPR.getText().toString().equals(""))
                    {textYEARPR.setText("0");}
                    if(textCUBICS.getText().toString().equals(""))
                    {textCUBICS.setText("0");}
                    dbOperations.updateCars(new CarItem(textRNUMBER.getText().toString(),
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
                    Toast.makeText(EditCar.this, "Editing is successful!", Toast.LENGTH_LONG).show();

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
