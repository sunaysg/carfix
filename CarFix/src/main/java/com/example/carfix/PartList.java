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
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by SUNAY on 12/3/13.
 */
public class PartList extends Activity {
    DBOperations dbOperations;
    adapter apt;
    ListView spisyk;

    boolean active = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partslist);

        dbOperations = new DBOperations(this);
        try {
            dbOperations.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        spisyk= (ListView)findViewById(R.id.listView);
        refresh();

        ImageButton AddPartB = (ImageButton)findViewById(R.id.imageButton);
        AddPartB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this,"Natisnahte kopcheto!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(PartList.this,AddPart.class));
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
        apt = new adapter(dbOperations.getAllParts());
        spisyk.setAdapter(apt);
    }
    public class adapter extends BaseAdapter {

        ArrayList<PartItem> Parts = new ArrayList<PartItem>();

        public adapter(ArrayList<PartItem> c){
            Parts=c;
        }
        @Override
        public int getCount() {
            return Parts.size();
        }

        @Override
        public Object getItem(int position) {
            return Parts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView=inflater.inflate(R.layout.patrlistitem, null);
            }
            TextView partname=(TextView)convertView.findViewById(R.id.pnameID);
            TextView partman=(TextView)convertView.findViewById(R.id.manID);
            TextView partprice=(TextView)convertView.findViewById(R.id.priceID);

            String NpnameID=Parts.get(position).getPName();
            String NmanID=Parts.get(position).getMan();
            String NpriceID=Parts.get(position).getPrice().toString();

            partname.setText(NpnameID);
            partman.setText(NmanID);
            partprice.setText(NpriceID);


            ImageButton buttonDel=(ImageButton)convertView.findViewById(R.id.imageButton2);
            buttonDel.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder confirm=new AlertDialog.Builder(PartList.this);
                    confirm.setTitle("Confirmation");
                    confirm.setMessage("Are you sure you want to remove this part from the list?");
                    confirm.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ArrayList<CardItem> RepCards = dbOperations.getAllRepCards();
                            boolean found = false;
                            for(CardItem c : RepCards){
                                String[] partList = c.getParts().split(",");
                                for(int z=0; z<partList.length; z++)
                                    if(Integer.parseInt(partList[z]) == Parts.get(position).getPID()){
                                        found = true;
                                        break;
                                    }
                                if(found == true)
                                    break;
                            }
                            if(found==false)
                                dbOperations.deletePart(Parts.get(position).getPID());
                            else
                                Toast.makeText(PartList.this,"The part is in USE and canot be deleted.",Toast.LENGTH_LONG).show();

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
                    Intent i = new Intent(PartList.this,EditPart.class);
                    i.putExtra("N_Item", position);
                    startActivity(i);

                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(PartList.this,PartData.class);
                    i.putExtra("N_Item", position);
                    startActivity(i);
                }
            });


            return convertView;
        }
    }
}
