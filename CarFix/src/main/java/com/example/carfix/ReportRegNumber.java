package com.example.carfix;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Iskren on 14-1-9.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ReportRegNumber extends Dialog {
    DBOperations dbOperations;

    Context context;
    static String RegNumber;

    public ReportRegNumber(Context cont, final int ReportIndex) {
        super(cont);
        context = cont;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.report_regnumber_picker);

        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dbOperations = new DBOperations(context);
        try {
            dbOperations.open();
        } catch (SQLException e) {

            e.printStackTrace();
        }

        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.textCarNumb);
        final ArrayList<CarItem> valuess  = dbOperations.getAllCars();
        AutoAdapter adapter = new AutoAdapter(context, valuess, autoCompleteTextView);
        autoCompleteTextView.setAdapter(adapter);

        Button btnGetReport = (Button)findViewById(R.id.btnGetReport);
        btnGetReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correct = false;
                for(CarItem it : valuess){
                    if (it.getRegNumber().equals(autoCompleteTextView.getText().toString())){
                        correct = true;
                        break;
                    }
                }
                if(correct){
                    RegNumber = autoCompleteTextView.getText().toString();
                    dismiss();
                    context.startActivity(new Intent(context, ReportDetails.class).putExtra("REPORT", ReportIndex));
                }
            }
        });

    }

}
