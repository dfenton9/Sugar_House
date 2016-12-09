/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sugarhouse.business;

import java.io.Serializable;

/**
 *
 * @author danielfenton
 */
public class CartItem implements Serializable{
    
    private int prodId;
    private int quantity;
    private double unitCost;
    private String name;
    
    public CartItem(int prodId, int quantity, double unitCost, String name)
    {
        this.prodId = prodId;
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.name = name;
    }
    
    
    public void setQuantity(int units)
    {
        this.quantity = units;
    }
    
    public int getProdId(){return prodId;}
    public int getQuantity(){return quantity;}
    public double getUnitCost(){return unitCost;}
    public String getName(){return name;}
    
    public String toString()
    {
        return "Name: " + name + ", Prod Id: " + prodId + ", Quantity: " + quantity + ", UnitCost: " + unitCost;  
    }
}
