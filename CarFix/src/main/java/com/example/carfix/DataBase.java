package com.example.carfix;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SUNAY on 11/30/13.
 */
public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="Cars.db";
    private static final int DATABASE_VERSION=1;

    //------TABLE CARS ----------------------------------------------------------------------------
    public static final String CARS="Cars";
    public static final String RNUMBER="_RegNumber";
    public static final String BRAND="_Brand";
    public static final String MODEL="_Model";
    public static final String YEARPR="_YearProd";
    public static final String ENUMBER="_EngNumber";
    public static final String BNUMBER="_BdyNumber";
    public static final String COLOR="_CColor";
    public static final String CUBICS="_Cubics";
    public static final String INFO="_Info";
    public static final String OWNER="_Owner";
    public static final String PHONE="_Phone";

    private static final String DATABASE_CREATE_CARS = "create table " + CARS+
            "(" + RNUMBER + " text primary key, " +
            BRAND + " text, " +
            MODEL + " text, " +
            YEARPR + " integer, " +
            ENUMBER + " text not null, " +
            BNUMBER + " text not null, " +
            COLOR + " text, " +
            CUBICS + " integer, " +
            INFO + " text, " +
            OWNER + " text, " +
            PHONE + " text);";

    //---------------------------------------------------------------------------------------------
    //------TABLE PARTS ---------------------------------------------------------------------------

    public static final String PARTS = "Parts";
    public static final String PART_ID = "_ID";
    public static final String PART_NAME = "_PName";
    public static final String PART_PRICE = "_PPrice";
    public static final String PART_MAN="_PMan";

    private static final String DATABASE_CREATE_PARTS = "create table " + PARTS +
            "(" + PART_ID + " integer primary key autoincrement, " +
            PART_NAME + " text  not null, " +
            PART_PRICE + " real not null, " +
            PART_MAN + " text  not null);";

    //---------------------------------------------------------------------------------------------
    //------TABLE REP CARDS ---------------------------------------------------------------------------

    public static final String REP_CARDS = "RepairCards";
    public static final String CARD_ID = "_ID";
    public static final String CHECK_IN_DATE = "_PName";
    public static final String CHECK_OUT_DATE = "_PPrice";
    public static final String CAR_NUMBER="_PMan";
    public static final String REP_DESCR = "_Description";
    public static final String EMPLOYEE = "_Employee";
    public static final String PART_LIST = "_Parts";
    public static final String PRICE = "_Price";

    private static final String DATABASE_CREATE_REP_CARDS = "create table " + REP_CARDS +
            "(" + CARD_ID + " integer primary key autoincrement, " +
            CHECK_IN_DATE + " text not null, " +
            CHECK_OUT_DATE + " text, " +
            CAR_NUMBER + " text not null, " +
            REP_DESCR + " text, " +
            EMPLOYEE + " text, " +
            PART_LIST + " text not null, " +
            PRICE + " real not null);";

    //------- FUNCTNS -----------------------------------------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_CARS);

        db.execSQL(DATABASE_CREATE_PARTS);
        CreateParts(db); //adding parts in the beggining

        db.execSQL(DATABASE_CREATE_REP_CARDS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CARS);
        db.execSQL("DROP TABLE IF EXISTS " + PARTS);
        db.execSQL("DROP TABLE IF EXISTS " + REP_CARDS);
        onCreate(db);
    }

    public DataBase (Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void CreateParts(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(PART_NAME,"Brakes");
        values.put(PART_PRICE,80.49);
        values.put(PART_MAN,"MOTUL");
        db.insert(PARTS,null,values);

        values.put(PART_NAME,"Brake Disks");
        values.put(PART_PRICE,105.20);
        values.put(PART_MAN,"WILWOOD");
        db.insert(DataBase.PARTS,null,values);

        values.put(PART_NAME,"Air Filter");
        values.put(PART_PRICE,35.50);
        values.put(PART_MAN,"AEM");
        db.insert(DataBase.PARTS,null,values);

        values.put(PART_NAME,"Air Filter");
        values.put(PART_PRICE,49.90);
        values.put(PART_MAN,"Injen Hydro");
        db.insert(DataBase.PARTS,null,values);

        values.put(PART_NAME,"Brake Disks");
        values.put(PART_PRICE,135.65);
        values.put(PART_MAN,"MARSHALL");
        db.insert(DataBase.PARTS,null,values);

        values.put(PART_NAME,"Brake Pads");
        values.put(PART_PRICE,44.10);
        values.put(PART_MAN,"MARSHALL");
        db.insert(DataBase.PARTS,null,values);

        values.put(PART_NAME,"Brake Pads");
        values.put(PART_PRICE,60.90);
        values.put(PART_MAN,"WILWOOD");
        db.insert(DataBase.PARTS,null,values);

        values.put(PART_NAME,"Turbo Charger");
        values.put(PART_PRICE,424.50);
        values.put(PART_MAN,"VW");
        db.insert(DataBase.PARTS,null,values);

        values.put(PART_NAME,"Turbo Charger");
        values.put(PART_PRICE,398.90);
        values.put(PART_MAN,"Renault");
        db.insert(DataBase.PARTS,null,values);

        values.put(PART_NAME,"Head Lights");
        values.put(PART_PRICE,362.99);
        values.put(PART_MAN,"Skoda");
        db.insert(DataBase.PARTS,null,values);
    }


}
