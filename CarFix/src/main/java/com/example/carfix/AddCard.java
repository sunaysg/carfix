package com.example.carfix;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by SUNAY on 12/10/13.
 */
public class AddCard extends Activity {
    DBOperations dbOperations;

    HashMap<Integer,PartItem> partList = new HashMap<Integer, PartItem>();
    double totalPrice = 0;

    TextView price;

    DecimalFormat newFormat = new DecimalFormat("#.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcard);

        dbOperations = new DBOperations(this);
        try {
            dbOperations.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final Button CarParts=(Button)findViewById(R.id.textPartList);
        final EditText Cost=(EditText)findViewById(R.id.textCost);
        final EditText CheckIn=(EditText)findViewById(R.id.textDateIn);
        CheckIn.setText(getDate());
        final EditText Empl=(EditText)findViewById(R.id.textEmployee);
        final EditText repInfo=(EditText)findViewById(R.id.textRepInfo);

        CarParts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regnum obj = new regnum(AddCard.this);
                obj.show();
                obj.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {


                        double roundPrice =  Double.valueOf(newFormat.format(totalPrice+totalPrice*0.3));
                        Cost.setText(String.valueOf(roundPrice));
                        String part_list = "";
                        int count = partList.keySet().size();
                        int razdelitel = 1;
                        for(Integer part : partList.keySet()){
                            if(razdelitel==count)
                                part_list = part_list + part;
                            else{
                                part_list = part_list + part + ",";
                                razdelitel++;
                            }
                        }
                        CarParts.setText(part_list);
                    }
                });
            }
        });

    //Za Wywejdane an RegNumber
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.textCarN);
        final ArrayList<CarItem> valuess  = dbOperations.getAllCars();
        AutoAdapter adapter = new AutoAdapter(AddCard.this, valuess, autoCompleteTextView);
        autoCompleteTextView.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        String REGNUMBER;
        if (extras != null) {
            REGNUMBER = extras.getString("REGNUMB");
            autoCompleteTextView.setText(REGNUMBER);
            autoCompleteTextView.dismissDropDown();
        }

        //za izpiswane
        final EditText CheckOutDate = (EditText)findViewById(R.id.textDateOut);
        CheckOutDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CheckOutDate.setText(getDate());
                return false;
            }
        });



        //za ZAPIS BUTON
        final Button saveData=(Button)findViewById(R.id.saveb);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autoCompleteTextView.getText().toString().equals(""))
                    Toast.makeText(AddCard.this, "Enter Registration Number", Toast.LENGTH_LONG).show();
                else if(CarParts.getText().toString().equals(""))
                    Toast.makeText(AddCard.this, "Enter Parts for the Repair", Toast.LENGTH_LONG).show();
                else
                {
                    boolean correct = false;
                    for(CarItem it : valuess){
                        if (it.getRegNumber().equals(autoCompleteTextView.getText().toString())){
                            correct = true;
                            break;
                        }
                    }

                    if(correct){
                        dbOperations.newRepCard(CheckIn.getText().toString(),
                            CheckOutDate.getText().toString(),
                            autoCompleteTextView.getText().toString(),
                            repInfo.getText().toString(),
                            Empl.getText().toString(),
                            CarParts.getText().toString(),
                            Double.parseDouble(Cost.getText().toString()));

                        Toast.makeText(AddCard.this, "Adding is successful!", Toast.LENGTH_LONG).show();
                        finish();

                    }
                    else{
                        Toast.makeText(AddCard.this, "Enter Correct Registration Number", Toast.LENGTH_LONG).show();
                    }

                }
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

    private String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(new Date());
    }


class regnum extends Dialog{



    public regnum(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setContentView(R.layout.regnumbers);

        price = (TextView)findViewById(R.id.txtPrice);
        Button done = (Button)findViewById(R.id.btnAdd);

        ArrayList<PartItem> pp = dbOperations.getAllParts();

        totalPrice = 0;

        for(PartItem pid : partList.values()) //keySet wryshta ArrayList ot kluchowete na HashMapa
        {
            for(PartItem pIt : pp){
                //if(pIt.getPID() == Integer.parseInt(pid.get(String.valueOf(pIt.getPID())))){
                if(pid.getPID() == pIt.getPID()){
                    totalPrice += pIt.getPrice();
                    break;
                }
            }
        }

        price.setText(String.valueOf(totalPrice));

        ListView spisyk= (ListView)findViewById(R.id.listView);
        adapter apt = new adapter(pp);
        spisyk.setAdapter(apt);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

public class adapter extends BaseAdapter {

        ArrayList<PartItem> parts = new ArrayList<PartItem>();

        public adapter(ArrayList<PartItem> c){
            parts=c;
        }
        @Override
        public int getCount() {
            return parts.size();
        }

        @Override
        public Object getItem(int position) {
            return parts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView=inflater.inflate(R.layout.regnumberitem, null);
            }


            TextView pName=(TextView)convertView.findViewById(R.id.partName);
            TextView pMan=(TextView)convertView.findViewById(R.id.partMan);
            final CheckBox addPart=(CheckBox)convertView.findViewById(R.id.checkBox);

            if(partList.containsKey(parts.get(position).getPID())){
                addPart.setChecked(true);
            }else
                addPart.setChecked(false);


            String partName=parts.get(position).getPName();
            String partMan=parts.get(position).getMan();

            pName.setText(partName);
            pMan.setText(partMan);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*if(addPart.isChecked()){
                        addPart.setChecked(false);
                        //partList.remove(String.valueOf(parts.get(position).getPID()));
                        partList.remove(parts.get(position));
                        totalPrice -= parts.get(position).getPrice();
                    }
                    else{
                        addPart.setChecked(true);
                        partList.put(parts.get(position),true);

                        //partList.add(String.valueOf(parts.get(position).getPID()), true);
                        //partList.add(new HashMap<String, Boolean>());
                        totalPrice += parts.get(position).getPrice();
                    }*/
                    if(partList.containsKey(parts.get(position).getPID())){
                        partList.remove(parts.get(position).getPID());
                        addPart.setChecked(false);
                        totalPrice -= parts.get(position).getPrice();
                        totalPrice = Double.valueOf(newFormat.format(totalPrice));
                    }else{
                        partList.put(parts.get(position).getPID(),parts.get(position));
                        addPart.setChecked(true);
                        totalPrice += parts.get(position).getPrice();
                        totalPrice = Double.valueOf(newFormat.format(totalPrice));
                    }

                    price.setText(String.valueOf(totalPrice));
                }
            });

            addPart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(partList.containsKey(parts.get(position).getPID())){
                        partList.remove(parts.get(position).getPID());
                        addPart.setChecked(false);
                        totalPrice -= parts.get(position).getPrice();
                        totalPrice = Double.valueOf(newFormat.format(totalPrice));
                    }else{
                        partList.put(parts.get(position).getPID(),parts.get(position));
                        addPart.setChecked(true);
                        totalPrice += parts.get(position).getPrice();
                        totalPrice = Double.valueOf(newFormat.format(totalPrice));
                    }

                    price.setText(String.valueOf(totalPrice));
                }
            });


            return convertView;
        }


    }
}

}



