/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sugarhouse.business;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author danielfenton
 */
public class Order {
    
    private int id;
    private int uid;
    private double total_cost;
    private Date order_date;
    private String status;
    private DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    
    public Order(int id, int uid, double cost, Date date, String status)
    {
        this.id = id;
        this.uid = uid;
        this.total_cost = cost;
        this.order_date = date;
        this.status = status;
        
    }
    
    public int getID(){return id;}
    
    public int getUID(){return uid;}
    
    public double getTotalCost(){return total_cost;}
    
    public String getOrderDate(){return df.format(order_date);}
    
    public String getStatus(){return status;}
    
}
