package com.driver;

import java.util.Arrays;
import java.util.List;

public class Time {
    public Time() {
    }

    public static  int convertStringToInt(String deliveryTime) throws NumberFormatException {
        String ar[]=deliveryTime.split(":");
        int hh=Integer.parseInt(ar[0]);
        int mm= Integer.parseInt(ar[1]);
        return hh*60+mm;
    }
    public static String convertIntToString(int deliveryTime){
        int hh=deliveryTime/60;
        int mm=deliveryTime%60;
        String HH=String.valueOf(hh);
        if(HH.length()==1) HH='0'+HH;
        String MM=String.valueOf(mm);
        if(MM.length()==1) MM='0'+MM;
        HH=HH+':';
        return HH+MM;
    }
}
