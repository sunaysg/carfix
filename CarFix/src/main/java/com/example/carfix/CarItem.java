package com.example.carfix;

import java.util.HashMap;

/**
 * Created by SUNAY on 11/30/13.
 */
public class CarItem {
    private String RegNumber;
    private String Brand;
    private String Model;
    private int YearProd;
    private String EngNumber;
    private String BdyNumber;
    private String CColor;
    private int Cubics;
    private String Info;
    private String Owner;
    private String Phone;

    public CarItem(String Rn, String Br, String Md, int Yp, String En, String Bn, String Cl, int Cb, String In, String Ow, String Ph)
    {
        RegNumber=Rn;
        Brand=Br;
        Model=Md;
        YearProd=Yp;
        EngNumber=En;
        BdyNumber=Bn;
        CColor=Cl;
        Cubics=Cb;
        Info=In;
        Owner=Ow;
        Phone=Ph;

    }

    public CarItem() {
        RegNumber="X0000XX";
        Brand="";
        Model="";
        YearProd=0;
        EngNumber="0000000000";
        BdyNumber="0000000000";
        CColor="";
        Cubics=0;
        Info="";
        Owner="XXX";
        Phone="08XXXXXXXX";
    }


    public void setRegNumber(String RegNumber){ this.RegNumber=RegNumber; }
    public void setBrand(String Brand){ this.Brand=Brand; }
    public void setModel(String Model){ this.Model=Model; }
    public void setYearProd(int YearProd){ this.YearProd=YearProd; }
    public void setEngNumber(String EngNumber){ this.EngNumber=EngNumber; }
    public void setBdyNumber(String BdyNumber){ this.BdyNumber=BdyNumber; }
    public void setCColor(String CColor){ this.CColor=CColor; }
    public void setCubics(int Cubics){ this.Cubics=Cubics; }
    public void setInfo(String Info){ this.Info=Info; }
    public void setOwner(String Owner){ this.Owner=Owner; }
    public void setPhone(String Phone){ this.Phone=Phone; }

    public String getRegNumber(){ return this.RegNumber; }
    public String getBrand(){ return this.Brand; }
    public String getModel(){ return this.Model; }
    public int getYearProd(){ return this.YearProd; }
    public String getEngNumber(){ return this.EngNumber; }
    public String getBdyNumber(){ return this.BdyNumber; }
    public String getCColor(){ return this.CColor; }
    public int getCubics(){ return this.Cubics; }
    public String getInfo(){ return this.Info; }
    public String getOwner(){ return this.Owner; }
    public String getPhone(){ return this.Phone; }

}
