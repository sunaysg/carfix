package com.example.carfix;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by SUNAY on 12/3/13.
 */
public class EditPart extends Activity {

    DBOperations dbOperations;
    PartItem theItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpart);


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

        ArrayList<PartItem> temp = new ArrayList<PartItem>();
        temp = dbOperations.getAllParts();
        theItem = temp.get(NumbPosition);

        //VIZUALIZACIQ I ZAKLUCHWANE
        Button SaveB=(Button) findViewById(R.id.saveb);
        final TextView Caption = (TextView)findViewById(R.id.textView2);
        Caption.setText("Edit Part");

        final EditText textPID=(EditText) findViewById(R.id.textPID);
        final EditText textPMAN=(EditText) findViewById(R.id.textPMAN);
        final EditText textPRICE=(EditText) findViewById(R.id.textPPRICE);

        textPID.setText(theItem.getPName());
        textPMAN.setText(theItem.getMan());
        textPRICE.setText(theItem.getPrice().toString());

        /*
        textRNUMBER.setBackgroundColor(Color.rgb(100, 255, 80));
        textENUMBER.setBackgroundColor(Color.rgb(100,255,80));
        textBNUMBER.setBackgroundColor(Color.rgb(100,255,80));
        */

        Button SaveBB=(Button) findViewById(R.id.saveb);
        SaveBB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textPID.getText().toString().equals(""))
                    Toast.makeText(EditPart.this, "Enter Part Name", Toast.LENGTH_LONG).show();
                else if(textPMAN.getText().toString().equals(""))
                    Toast.makeText(EditPart.this, "Enter Man Name", Toast.LENGTH_LONG).show();
                else if(textPRICE.getText().toString().equals(""))
                    Toast.makeText(EditPart.this, "Enter Price", Toast.LENGTH_LONG).show();
                else
                {
                    dbOperations.updateParts(new PartItem(theItem.getPID(),
                            textPID.getText().toString(),
                            Double.parseDouble(textPRICE.getText().toString()),
                            textPMAN.getText().toString()));
                    Toast.makeText(EditPart.this, "Editing is successful!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EditPart.this,PartList.class));
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
