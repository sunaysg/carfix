package com.example.carfix;

/**
 * Created by SUNAY on 12/3/13.
 */
public class PartItem {
    private int ID;
    private String Name;
    private double Price;
    private String Man;

    public PartItem(){
        ID=0;
        Name="";
        Price=0;
        Man="";
    }

    public PartItem(int id, String name, double price, String man){
        ID=id;
        Name=name;
        Price=price;
        Man=man;
    }

    public void setPID(int id){ID=id; };
    public void setPName(String name){Name=name; };
    public void setPrice(double price){Price=price; };
    public void setMan(String man){Man=man; };

    public int getPID(){return ID;};
    public String getPName(){return Name;};
    public Double getPrice(){return Price;};
    public String getMan(){return Man;};

}
