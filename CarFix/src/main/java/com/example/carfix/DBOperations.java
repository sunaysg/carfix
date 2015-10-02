package com.example.carfix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.provider.Telephony;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by SUNAY on 11/30/13.
 */
public class DBOperations {
    private DataBase dbHelper;
    private String[] CARS_TABLE_COLUMNS = {DataBase.RNUMBER, DataBase.BRAND, DataBase.MODEL, DataBase.YEARPR, DataBase.ENUMBER, DataBase.BNUMBER, DataBase.COLOR, DataBase.CUBICS, DataBase.INFO, DataBase.OWNER, DataBase.PHONE};
    private String[] PARTS_TABLE_COLUMNS = {DataBase.PART_ID, DataBase.PART_NAME, DataBase.PART_PRICE, DataBase.PART_MAN};
    private String[] REP_CARDS_TABLE_COLUMNS = {DataBase.CARD_ID, DataBase.CHECK_IN_DATE, DataBase.CHECK_OUT_DATE, DataBase.CAR_NUMBER, DataBase.REP_DESCR, DataBase.EMPLOYEE, DataBase.PART_LIST, DataBase.PRICE};


    private SQLiteDatabase database;

    public DBOperations(Context context)
    {
        dbHelper=new DataBase(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }




    //------TABLE CARS ----------------------------------------------------------------------------
    public int updateCars(CarItem carItem)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataBase.RNUMBER,carItem.getRegNumber());
        values.put(DataBase.BRAND,carItem.getBrand());
        values.put(DataBase.MODEL,carItem.getModel());
        values.put(DataBase.YEARPR,carItem.getYearProd());
        values.put(DataBase.ENUMBER,carItem.getEngNumber());
        values.put(DataBase.BNUMBER,carItem.getBdyNumber());
        values.put(DataBase.COLOR,carItem.getCColor());
        values.put(DataBase.CUBICS,carItem.getCubics());
        values.put(DataBase.INFO,carItem.getInfo());
        values.put(DataBase.OWNER,carItem.getOwner());
        values.put(DataBase.PHONE,carItem.getPhone());

        //updating row
        return db.update(DataBase.CARS, values,DataBase.RNUMBER + " = ?", new String[] { carItem.getRegNumber() } );
    }

    public CarItem newCar(CarItem carItem)
    {
        ContentValues values = new ContentValues();
        values.put(DataBase.RNUMBER,carItem.getRegNumber());
        values.put(DataBase.BRAND,carItem.getBrand());
        values.put(DataBase.MODEL,carItem.getModel());
        values.put(DataBase.YEARPR,carItem.getYearProd());
        values.put(DataBase.ENUMBER,carItem.getEngNumber());
        values.put(DataBase.BNUMBER,carItem.getBdyNumber());
        values.put(DataBase.COLOR,carItem.getCColor());
        values.put(DataBase.CUBICS,carItem.getCubics());
        values.put(DataBase.INFO,carItem.getInfo());
        values.put(DataBase.OWNER,carItem.getOwner());
        values.put(DataBase.PHONE,carItem.getPhone());

        database.insert(DataBase.CARS,null,values);
        return carItem;
    }

    public void deleteCar(String rnumber)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DataBase.CARS,DataBase.RNUMBER + " = ?", new String[] {rnumber});
    }

    public ArrayList<CarItem> getAllCars(){
        ArrayList<CarItem>Cars = new ArrayList<CarItem>();
        Cursor cursor = database.query(DataBase.CARS, CARS_TABLE_COLUMNS,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            CarItem carItem = new CarItem();
            carItem.setRegNumber(cursor.getString(0));
            carItem.setBrand(cursor.getString(1));
            carItem.setModel(cursor.getString(2));
            carItem.setYearProd(cursor.getInt(3));
            carItem.setEngNumber(cursor.getString(4));
            carItem.setBdyNumber(cursor.getString(5));
            carItem.setCColor(cursor.getString(6));
            carItem.setCubics(cursor.getInt(7));
            carItem.setInfo(cursor.getString(8));
            carItem.setOwner(cursor.getString(9));
            carItem.setPhone(cursor.getString(10));

            Cars.add(0,carItem);
            cursor.moveToNext();
        }
        cursor.close();
        //Collections.reverse(Cars);
        return Cars;
    }
    //---------------------------------------------------------------------------------------------
    //------TABLE PARTS ----------------------------------------------------------------------------
    public int updateParts(PartItem partItem)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataBase.PART_ID, partItem.getPID());
        values.put(DataBase.PART_NAME,partItem.getPName());
        values.put(DataBase.PART_PRICE,partItem.getPrice());
        values.put(DataBase.PART_MAN,partItem.getMan());


        //updating row
        return db.update(DataBase.PARTS, values,DataBase.PART_ID + " = ?", new String[] { String.valueOf(partItem.getPID())} );
    }

    public PartItem newPart(String name, double price, String man)
    {
        ContentValues values = new ContentValues();
        values.put(DataBase.PART_NAME,name);
        values.put(DataBase.PART_PRICE,price);
        values.put(DataBase.PART_MAN,man);

        long partID =  database.insert(DataBase.PARTS,null,values);

        Cursor cursor = database.query(DataBase.PARTS,PARTS_TABLE_COLUMNS,DataBase.PART_ID + " = " + partID, null, null, null, null);
        cursor.moveToFirst();
        PartItem ret = new PartItem(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2),cursor.getString(3));
        return ret;
    }

    public void deletePart(int pnumber)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DataBase.PARTS,DataBase.PART_ID + " = ?", new String[] {String.valueOf(pnumber)});
    }

    public ArrayList<PartItem> getAllParts(){
        ArrayList<PartItem>Parts = new ArrayList<PartItem>();
        Cursor cursor = database.query(DataBase.PARTS, PARTS_TABLE_COLUMNS,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            PartItem partItem = new PartItem();
            partItem.setPID(cursor.getInt(0));
            partItem.setPName(cursor.getString(1));
            partItem.setPrice(cursor.getDouble(2));
            partItem.setMan(cursor.getString(3));

            Parts.add(0,partItem);
            cursor.moveToNext();
        }
        cursor.close();
        //Collections.reverse(Parts);
        return Parts;
    }

    //---------------------------------------------------------------------------------------------
    //------TABLE REP_CARDS ----------------------------------------------------------------------------
    public int updateRepCards(CardItem cardItem)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataBase.CARD_ID, cardItem.getCardID());
        values.put(DataBase.CHECK_IN_DATE,cardItem.getCheckInDate().toString());
        values.put(DataBase.CHECK_OUT_DATE,cardItem.getCheckOutDate().toString());
        values.put(DataBase.CAR_NUMBER,cardItem.getCarRegNumber());
        values.put(DataBase.REP_DESCR,cardItem.getDescription());
        values.put(DataBase.EMPLOYEE,cardItem.getEmployee());
        values.put(DataBase.PART_LIST,cardItem.getParts());
        values.put(DataBase.PRICE,cardItem.getPrice());

        //updating row
        return db.update(DataBase.REP_CARDS, values,DataBase.CARD_ID + " = ?", new String[] { String.valueOf(cardItem.getCardID())} );
    }

    public CardItem newRepCard(String inDate, String outDate, String cRegN, String Desc, String Empl, String parts, double Pr)
    {
        ContentValues values = new ContentValues();
        values.put(DataBase.CHECK_IN_DATE,inDate);//not null
        values.put(DataBase.CHECK_OUT_DATE,outDate);
        values.put(DataBase.CAR_NUMBER,cRegN);//not null
        values.put(DataBase.REP_DESCR,Desc);
        values.put(DataBase.EMPLOYEE,Empl);
        values.put(DataBase.PART_LIST,parts);//not null
        values.put(DataBase.PRICE,Pr);//not null

        long cardID =  database.insert(DataBase.REP_CARDS,null,values);

        Cursor cursor = database.query(DataBase.REP_CARDS,REP_CARDS_TABLE_COLUMNS,DataBase.CARD_ID + " = " + cardID, null, null, null, null);
        cursor.moveToFirst();
        CardItem ret = new CardItem(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getDouble(7));
        return ret;
    }

    public void deleteRepCard(int cNumb)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DataBase.REP_CARDS,DataBase.CARD_ID + " = ?", new String[] {String.valueOf(cNumb)});
    }

    public ArrayList<CardItem> getAllRepCards(){
        ArrayList<CardItem> Cards = new ArrayList<CardItem>();
        Cursor cursor = database.query(DataBase.REP_CARDS, REP_CARDS_TABLE_COLUMNS,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            CardItem card = new CardItem();
            card.setCardID(cursor.getInt(0));
            card.setCheckInDate(cursor.getString(1));
            card.setCheckOutDate(cursor.getString(2));
            card.setCarRegNumber(cursor.getString(3));
            card.setDescription(cursor.getString(4));
            card.setEmployee(cursor.getString(5));
            card.setParts(cursor.getString(6));
            card.setPrice(cursor.getDouble(7));

            //Cards.add(card);
            Cards.add(0,card);
            cursor.moveToNext();
        }
        cursor.close();
        //Collections.reverse(Cards);
        return Cards;
    }



}

