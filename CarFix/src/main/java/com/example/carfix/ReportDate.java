package com.example.carfix;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.Calendar;

/**
 * Created by Anton on 13-12-1.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ReportDate extends Dialog implements NumberPicker.OnValueChangeListener{
    NumberPicker fromDay, fromMonth, fromYear, toDay, toMonth, toYear;
    Context context;
    static String fromPeriod, toPeriod;

    public ReportDate(Context cont, final int ReportIndex) {
        super(cont);
        context = cont;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.report_date_picker);

        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        fromDay = (NumberPicker)findViewById(R.id.pickDay);
        fromMonth = (NumberPicker)findViewById(R.id.pickMonth);
        fromYear = (NumberPicker)findViewById(R.id.pickYear);

        Calendar c = Calendar.getInstance();
        fromDay.setMinValue(1);
        fromDay.setMaxValue(31);

        fromMonth.setMinValue(1);
        fromMonth.setMaxValue(12);

        fromYear.setMinValue(1990);
        fromYear.setMaxValue(2999);

        fromDay.setValue(c.get(Calendar.DAY_OF_MONTH));
        fromMonth.setValue(c.get(Calendar.MONTH)+1);
        fromYear.setValue(c.get(Calendar.YEAR));

        fromDay.setOnValueChangedListener(this);
        fromMonth.setOnValueChangedListener(this);
        fromYear.setOnValueChangedListener(this);

        Button btnGetReport = (Button)findViewById(R.id.btnGetReport);
        btnGetReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromPeriod = String.format("%02d.%02d.%d, 00:00",fromDay.getValue(),fromMonth.getValue(),fromYear.getValue());

                dismiss();
                context.startActivity(new Intent(context, ReportDetails.class).putExtra("REPORT", ReportIndex));
            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        switch (picker.getId()){
            case R.id.pickDay:

                break;
            case R.id.pickMonth:
                fromDay.setMaxValue(ValidateDate(newVal, fromYear.getValue()));
                break;
            case R.id.pickYear:
                fromDay.setMaxValue(ValidateDate(fromMonth.getValue(), newVal));
                break;


        }
    }

    private int ValidateDate(int Month, int Year){
        boolean isLeapYear = isLeapYear(Year);

        switch(Month)
        {
            case 2:
                return isLeapYear ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    private boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }


}
