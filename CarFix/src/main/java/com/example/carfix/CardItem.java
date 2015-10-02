package com.example.carfix;

import java.util.Date;

/**
 * Created by Iskren on 13-12-18.
 */
public class CardItem {
    private int cardID;
    private String CheckInDate;
    private String CheckOutDate;
    private String CarRegNumber;
    private String Description;
    private String Employee;
    private String Parts;
    private double Price;

    public CardItem(int cID, String inDate, String outDate, String cRegN, String Desc, String Empl, String parts, double Pr){
        cardID=cID;
        CheckInDate = inDate;
        CheckOutDate = outDate;
        CarRegNumber=cRegN;
        Description=Desc;
        Employee = Empl;
        Parts = parts;
        Price = Pr;
    }

    public CardItem(){
        cardID=-1;
        CheckInDate = "";
        CheckOutDate = "";
        CarRegNumber="";
        Description="";
        Employee = "";
        Parts = "";
        Price = 0;
    }

    public void setCardID(int cID){cardID=cID;};
    public void setCheckInDate(String inDate){CheckInDate=inDate;};
    public void setCheckOutDate(String outDate){CheckOutDate=outDate;};
    public void setCarRegNumber(String cRegN){CarRegNumber=cRegN;};
    public void setDescription(String Desc){Description=Desc;};
    public void setEmployee(String Empl){Employee=Empl;};
    public void setParts(String parts){Parts=parts;};
    public void setPrice(double Pr){Price=Pr;};

    public int getCardID(){return cardID;};
    public String getCheckInDate(){return CheckInDate;};
    public String getCheckOutDate(){return CheckOutDate;};
    public String getCarRegNumber(){return CarRegNumber;};
    public String getDescription(){return Description;};
    public String getEmployee(){return Employee;};
    public String getParts(){return Parts;};
    public double getPrice(){return Price;};
}
