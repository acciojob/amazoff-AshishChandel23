package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        this.deliveryTime = convertDeliveryTime(deliveryTime);
        this.id = id;
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }

    private int convertDeliveryTime(String deliverTime){
        String[] time = deliverTime.split(":");
        return Integer.parseInt(time[0])*60+Integer.parseInt(time[1]);
    }
    private String convertDeliveryTime(int deliverTime){
        int hh = deliveryTime/60;
        int mm = deliveryTime%60;
        String HH = String.valueOf(hh);
        String MM = String.valueOf(mm);
        return String.format("%s:%s",HH,MM);
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = convertDeliveryTime(deliveryTime);
    }
    public String getDeliveryTimeAsString(){
       return convertDeliveryTime(this.deliveryTime);
    }
    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
