package com.example.carfix;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Iskren on 13-12-19.
 */
public class RepCardData extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcard);

        Bundle extras = getIntent().getExtras();
        int CardID=0;
        if (extras != null) {
            CardID = extras.getInt("CardID");
        }
        DBOperations dbOperations = new DBOperations(this);
        try {
            dbOperations.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<CardItem> temp = new ArrayList<CardItem>();
        temp = dbOperations.getAllRepCards();
        CardItem theItem = null;
        for(CardItem ob : temp)
            if(CardID == ob.getCardID()){
                theItem= ob;
                break;
            }

        Button SaveB=(Button) findViewById(R.id.saveb);
        SaveB.setVisibility(View.GONE); // View.VISIBLE  View.INVISIBLE
        final TextView Caption = (TextView)findViewById(R.id.textView2);
        Caption.setText("Repait Card Data");

        final Button CarParts=(Button)findViewById(R.id.textPartList);
        final EditText Cost=(EditText)findViewById(R.id.textCost);
        final EditText CheckIn=(EditText)findViewById(R.id.textDateIn);
        final EditText Empl=(EditText)findViewById(R.id.textEmployee);
        final EditText repInfo=(EditText)findViewById(R.id.textRepInfo);
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.textCarN);
        final EditText CheckOutDate = (EditText)findViewById(R.id.textDateOut);

        CarParts.setEnabled(false);
        Cost.setEnabled(false);
        CheckIn.setEnabled(false);
        Empl.setEnabled(false);
        repInfo.setEnabled(false);
        autoCompleteTextView.setEnabled(false);
        CheckOutDate.setEnabled(false);

        CarParts.setBackgroundColor(Color.TRANSPARENT);
        Cost.setBackgroundColor(Color.TRANSPARENT);
        CheckIn.setBackgroundColor(Color.TRANSPARENT);
        Empl.setBackgroundColor(Color.TRANSPARENT);
        repInfo.setBackgroundColor(Color.TRANSPARENT);
        autoCompleteTextView.setBackgroundColor(Color.TRANSPARENT);
        CheckOutDate.setBackgroundColor(Color.TRANSPARENT);

        CarParts.setText(theItem.getParts());
        Cost.setText(String.valueOf(theItem.getPrice()));
        CheckIn.setText(theItem.getCheckInDate());
        Empl.setText(theItem.getEmployee());
        repInfo.setText(theItem.getDescription());
        autoCompleteTextView.setText(theItem.getCarRegNumber());
        CheckOutDate.setText(theItem.getCheckOutDate());
        CheckOutDate.setHint("");

        ImageButton btnBack = (ImageButton)findViewById(R.id.imageButton2);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
