package com.example.carfix;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Created by Iskren on 13-12-3.
 */
public class CarData extends Activity {
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
        DBOperations dbOperations = new DBOperations(this);
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
        SaveB.setVisibility(View.GONE); // View.VISIBLE  View.INVISIBLE
        final TextView Caption = (TextView)findViewById(R.id.textView2);
        Caption.setText("Car Data");
        final EditText textRNUMBER=(EditText) findViewById(R.id.textRNUMBER);
        final EditText textBRAND=(EditText) findViewById(R.id.textBRAND);
        final EditText textMODEL=(EditText) findViewById(R.id.textMODEL);
        final EditText textYEARPR=(EditText) findViewById(R.id.textYEARPR);
        final EditText textENUMBER=(EditText) findViewById(R.id.textENUMBER);
        final EditText textBNUMBER=(EditText) findViewById(R.id.textBNUMBER);
        //final EditText textCOLOR=(EditText) findViewById(R.id.textCOLOR);
        final Spinner spiner = (Spinner)findViewById(R.id.textCOLOR);
        spiner.setVisibility(View.GONE);

        final EditText Spinner = (EditText)findViewById(R.id.editTextSPINER);
        Spinner.setText(theItem.getCColor());
        Spinner.setVisibility(View.VISIBLE);
        Spinner.setEnabled(false);
        Spinner.setTextColor(Color.LTGRAY);
        Spinner.setBackgroundColor(Color.TRANSPARENT);

        final EditText textCUBICS=(EditText) findViewById(R.id.textCUBICS);
        final EditText textINFO=(EditText) findViewById(R.id.textINFO);
        final EditText textOWNER=(EditText) findViewById(R.id.textOWNER);
        final EditText textPHONE=(EditText) findViewById(R.id.textPHONE);

        textRNUMBER.setEnabled(false);
        textBRAND.setEnabled(false);
        textMODEL.setEnabled(false);
        textYEARPR.setEnabled(false);
        textENUMBER.setEnabled(false);
        textBNUMBER.setEnabled(false);
        textCUBICS.setEnabled(false);
        textINFO.setEnabled(false);
        //textINFO.setTextColor(Color.LTGRAY);

        textOWNER.setEnabled(false);
        textPHONE.setEnabled(false);



        textRNUMBER.setBackgroundColor(Color.TRANSPARENT);
        textBRAND.setBackgroundColor(Color.TRANSPARENT);
        textMODEL.setBackgroundColor(Color.TRANSPARENT);
        textYEARPR.setBackgroundColor(Color.TRANSPARENT);
        textENUMBER.setBackgroundColor(Color.TRANSPARENT);
        textBNUMBER.setBackgroundColor(Color.TRANSPARENT);
        textCUBICS.setBackgroundColor(Color.TRANSPARENT);
        textINFO.setBackgroundColor(Color.TRANSPARENT);
        textOWNER.setBackgroundColor(Color.TRANSPARENT);
        textPHONE.setBackgroundColor(Color.TRANSPARENT);

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

        ImageButton btnBack = (ImageButton)findViewById(R.id.imageButton2);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}