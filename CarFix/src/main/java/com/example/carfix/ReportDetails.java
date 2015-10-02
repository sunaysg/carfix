package com.example.carfix;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Iskren on 13-12-19.
 */
public class ReportDetails extends Activity {
    int reportIndex;
    DBOperations dbOperations;
    adapter apt;
    ListView spisyk;

    ArrayList<CardItem> AllCards = new ArrayList<CardItem>();

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

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            reportIndex = bundle.getInt("REPORT",0);
        }

        switch (reportIndex){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                break;
            default:
                Toast.makeText(ReportDetails.this, "Error!", Toast.LENGTH_LONG).show();
                finish();
                break;
        }

        //refresh();
        //AsyncLoading asyncLoading = new AsyncLoading();
        //asyncLoading.execute();
        
        spisyk = (ListView)findViewById(R.id.listView);
        refresh();

        ImageButton AddRepCard = (ImageButton)findViewById(R.id.imageButton);
        AddRepCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this,"Natisnahte kopcheto!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(ReportDetails.this,AddCard.class));
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
        dbOperations.close();
        super.onPause();
        active = false;
    }

    public void refresh()
    {
        AsyncLoading asyncLoading = new AsyncLoading();
        asyncLoading.execute();
    }



    public class AsyncLoading extends AsyncTask<Void,Void,ArrayList<Object>> {
        @Override
        protected void onPreExecute(){
            if(dbOperations==null){
                dbOperations = new DBOperations(ReportDetails.this);
            }
            try{
                dbOperations.open();
            }catch(Exception e){}

            AllCards = dbOperations.getAllRepCards();
        }

        @Override
        protected ArrayList<Object> doInBackground(Void... params) {
            ArrayList<Object> values = new ArrayList<Object>();

            switch (reportIndex){
                case 1:
                    //Toast.makeText(ReportDetails.this, beforeDate.toString(),Toast.LENGTH_LONG).show();
                    Date afterDate = getDate(ReportDate.fromPeriod);
                    for(CardItem oneItem : AllCards){
                        Date itemDate = getDate(oneItem.getCheckInDate());
                        //Toast.makeText(ReportDetails.this,itemDate.toString(),Toast.LENGTH_LONG).show();
                        if(itemDate.after(afterDate))
                            values.add(oneItem);
                    }
                    break;
                case 2:
                    //Toast.makeText(ReportDetails.this, beforeDate.toString(),Toast.LENGTH_LONG).show();
                    Date beforeDate = getDate(ReportDate.fromPeriod);
                    for(CardItem oneItem : AllCards){
                        Date itemDate = getDate(oneItem.getCheckInDate());
                        //Toast.makeText(ReportDetails.this,itemDate.toString(),Toast.LENGTH_LONG).show();
                        if(itemDate.before(beforeDate))
                            values.add(oneItem);
                    }
                    break;
                case 3:
                    //Toast.makeText(ReportDetails.this, beforeDate.toString(),Toast.LENGTH_LONG).show();
                    Date toDate = getDate(ReportDate.fromPeriod);
                    for(CardItem oneItem : AllCards){
                        Date itemDate = getDate(oneItem.getCheckInDate());
                        //Toast.makeText(ReportDetails.this,itemDate.toString(),Toast.LENGTH_LONG).show();
                        if(itemDate.equals(toDate))
                            values.add(oneItem);
                    }
                    break;
                case 4:
                        String CarNumb = ReportRegNumber.RegNumber;
                        for(CardItem oneItem : AllCards){
                            if(oneItem.getCarRegNumber().equals(CarNumb))
                                values.add(oneItem);
                        }
                    break;
                case 5:
                        for(CardItem oneItem : AllCards){
                            if(oneItem.getCheckOutDate().length() == 0)
                                values.add(oneItem);
                        }
                    break;
            }

            return values;
        }
        @Override
        protected void onPostExecute(ArrayList<Object> values){
            if(values==null || values.isEmpty()){
                Toast.makeText(ReportDetails.this, "No available report!",Toast.LENGTH_LONG).show();
            }else{
                apt = new adapter(values);
                spisyk.setAdapter(apt);
        }
    }
    }



    //Adaptera koito zarejda dannite
    public class adapter extends BaseAdapter {

        ArrayList<Object> RepCards = new ArrayList<Object>();


        public adapter(ArrayList<Object> obj){
            RepCards=obj;
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

            String strCarN = ((CardItem)RepCards.get(position)).getCarRegNumber();
            String strEmpl = ((CardItem)RepCards.get(position)).getEmployee();
            String strCheckIn = ((CardItem)RepCards.get(position)).getCheckInDate();
            double strPrice = ((CardItem)RepCards.get(position)).getPrice();

            carN.setText(strCarN);
            empl.setText(strEmpl);
            checkIn.setText(strCheckIn);
            price.setText(String.valueOf(strPrice));


            ImageButton buttonDel=(ImageButton)convertView.findViewById(R.id.imageButton2);
            buttonDel.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder confirm=new AlertDialog.Builder(ReportDetails.this);
                    confirm.setTitle("Confirmation");
                    confirm.setMessage("Are you sure you want to remove this Repair Card from the list?");
                    confirm.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbOperations.deleteRepCard(((CardItem)RepCards.get(position)).getCardID());
                            refresh();
                            //finish();
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
                    Intent i = new Intent(ReportDetails.this,EditRepCard.class);
                    i.putExtra("CardID", ((CardItem)RepCards.get(position)).getCardID());
                    startActivity(i);
                    //trqbwa da prezerqjda spisyka

                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ReportDetails.this,RepCardData.class);
                    i.putExtra("CardID", ((CardItem)RepCards.get(position)).getCardID());
                    startActivity(i);
                }
            });


            return convertView;
        }
    }

    private Date getDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }
}

