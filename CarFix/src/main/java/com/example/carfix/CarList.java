package com.example.carfix;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by SUNAY on 12/1/13.
 */
    public class CarList extends Activity{
    DBOperations dbOperations;
    adapter apt;
    ListView spisyk;

    boolean active = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carlist);

        dbOperations = new DBOperations(this);
        try {
            dbOperations.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        spisyk= (ListView)findViewById(R.id.listView);
        refresh();

        ImageButton AddCarB = (ImageButton)findViewById(R.id.imageButton);
        AddCarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this,"Natisnahte kopcheto!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(CarList.this,AddCar.class));
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
        apt = new adapter(dbOperations.getAllCars());
        spisyk.setAdapter(apt);
    }

public class adapter extends BaseAdapter{

    ArrayList<CarItem> Cars = new ArrayList<CarItem>();

    public adapter(ArrayList<CarItem> c){
        Cars=c;
    }
    @Override
    public int getCount() {
        return Cars.size();
    }

    @Override
    public Object getItem(int position) {
        return Cars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.carlistitem, null);
        }
        TextView regID=(TextView)convertView.findViewById(R.id.regID);
        TextView brandID=(TextView)convertView.findViewById(R.id.brandID);
        TextView modelID=(TextView)convertView.findViewById(R.id.modelID);
        TextView yearID=(TextView)convertView.findViewById(R.id.yearID);

        String NregID=Cars.get(position).getRegNumber();
        String NbrandID=Cars.get(position).getBrand();
        String NmodelID=Cars.get(position).getModel();
        String NyearID=String.valueOf(Cars.get(position).getYearProd());
        regID.setText(NregID);
        brandID.setText(NbrandID);
        modelID.setText(NmodelID);
        yearID.setText(NyearID);

        ImageButton buttonDel=(ImageButton)convertView.findViewById(R.id.imageButton2);
        buttonDel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder confirm=new AlertDialog.Builder(CarList.this);
                confirm.setTitle("Confirmation");
                confirm.setMessage("Are you sure you want to remove this car from the list?");
                confirm.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbOperations.deleteCar(Cars.get(position).getRegNumber());
                        refresh();
                        //deleting all the REP card for the Vicecle
                        ArrayList<CardItem> cards = dbOperations.getAllRepCards();
                        for(CardItem c : cards)
                            if(Cars.get(position).getRegNumber().equals(c.getCarRegNumber()))
                                dbOperations.deleteRepCard(c.getCardID());

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
                Intent i = new Intent(CarList.this,EditCar.class);
                i.putExtra("N_Item", position);
                startActivity(i);

            }
        });



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(CarList.this, "Clicked the item", Toast.LENGTH_LONG).show();
                //startActivity(new Intent(CarList.this,CarData.class));
                Intent i = new Intent(CarList.this,CarData.class);
                //Bundle sendBundle = new Bundle();
                //sendBundle.putLong("value", position);
                //i.putExtras(sendBundle);

                i.putExtra("N_Item", position);
                startActivity(i);
            }
        });


        return convertView;
    }
}
}


