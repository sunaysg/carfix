package com.example.carfix;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Iskren on 13-12-19.
 */
public class RepCardList extends Activity {

    DBOperations dbOperations;
    adapter apt;
    ListView spisyk;

    boolean active = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repcardslist);


        dbOperations = new DBOperations(this);
        try {
            dbOperations.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        spisyk= (ListView)findViewById(R.id.listView);
        refresh();

        ImageButton AddRepCard = (ImageButton)findViewById(R.id.imageButton);
        AddRepCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this,"Natisnahte kopcheto!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(RepCardList.this,AddCard.class));
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

    @Override
    public void onResume(){
        if(!active){
            refresh();
        }
        super.onResume();
    }

    @Override
    public void onPause(){
        active=false;
        super.onPause();
    }

    public void refresh()
    {
        apt = new adapter(dbOperations.getAllRepCards());
        spisyk.setAdapter(apt);
    }

    public class adapter extends BaseAdapter {

        ArrayList<CardItem> RepCards = new ArrayList<CardItem>();

        public adapter(ArrayList<CardItem> c){
            RepCards=c;
        }
        @Override
        public int getCount() {
            return RepCards.size();
        }

        @Override
        public Object getItem(int position) {
            return RepCards.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView=inflater.inflate(R.layout.repcardlistitem, null);
            }
            TextView carN=(TextView)convertView.findViewById(R.id.carNumber);
            TextView empl=(TextView)convertView.findViewById(R.id.employeeID);
            TextView checkIn=(TextView)convertView.findViewById(R.id.CheckInDate);
            TextView price=(TextView)convertView.findViewById(R.id.priceID);

            String strCarN = RepCards.get(position).getCarRegNumber();
            String strEmpl = RepCards.get(position).getEmployee();
            String strCheckIn = RepCards.get(position).getCheckInDate();
            double strPrice = RepCards.get(position).getPrice();

            carN.setText(strCarN);
            empl.setText(strEmpl);
            checkIn.setText(strCheckIn);
            price.setText(String.valueOf(strPrice));


            ImageButton buttonDel=(ImageButton)convertView.findViewById(R.id.imageButton2);
            buttonDel.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder confirm=new AlertDialog.Builder(RepCardList.this);
                    confirm.setTitle("Confirmation");
                    confirm.setMessage("Are you sure you want to remove this Repair Card from the list?");
                    confirm.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbOperations.deleteRepCard(RepCards.get(position).getCardID());
                            refresh();
                        }
                    });
                    confirm.setNegativeButton("No",null);
                    confirm.show();
                }

            });

            ImageButton buttonEdit=(ImageButton)convertView.findViewById(R.id.imageButton);
            buttonEdit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(RepCardList.this,EditRepCard.class);
                    i.putExtra("CardID", ((CardItem)RepCards.get(position)).getCardID());
                    startActivity(i);

                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(RepCardList.this,RepCardData.class);
                    i.putExtra("CardID", ((CardItem)RepCards.get(position)).getCardID());
                    startActivity(i);
                }
            });


            return convertView;
        }
    }

}

