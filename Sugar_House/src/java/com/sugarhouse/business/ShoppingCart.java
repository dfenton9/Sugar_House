/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sugarhouse.business;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Fenton
 */
//FYI - I am using the Murach "ch19_ex1_cart_sol" example for reference

@SuppressWarnings("serial")
public class ShoppingCart implements Serializable {

    private ArrayList<CartItem> items;
    private double totalCost;
    //private int quantity;
    //private int productID;

    public ShoppingCart() {
            items = new ArrayList<CartItem>();
            totalCost = 0;
    }

    public void addItem(CartItem item) {
            items.add(item);
            totalCost = totalCost + (item.getUnitCost() * item.getQuantity());
    }

    public void removeItem(CartItem item) {
            boolean wasRemoved = items.remove(item);
            System.out.println("Was (" + item.toString() + ") Removed? " + wasRemoved);
            totalCost = totalCost - (item.getUnitCost() * item.getQuantity());
    }
    
    public void appendItems(ArrayList<CartItem> newItems)
    {
        for(CartItem item : newItems)
        {
            if(!items.contains(item))
            {
                items.add(item);
            }
        }
    }
    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartItem> items) {
        this.items = items;
    }
    public Double getTotalCost(){
    	return totalCost;
    }
    public void setTotalCost(Double totalCost){
    	this.totalCost = totalCost;
    }
    
    public CartItem alreadyInCart(int prodId)
    {
        for(CartItem item : items)
        {
            if(item.getProdId() == prodId)
                return item;
        }
        return null;
    }
    
    public void resetCart()
    {
        this.items.clear();
        this.totalCost = 0;
    }
    
    public void recalculateTotal()
    {
        this.totalCost = 0;
        for(CartItem item : items)
        {
            this.totalCost += (item.getQuantity() * item.getUnitCost());
        }
    }
}
