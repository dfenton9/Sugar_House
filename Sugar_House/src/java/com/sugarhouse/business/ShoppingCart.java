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

	private ArrayList<String> items;
	private double totalCost;
	//private int quantity;
	//private int productID;

	public ShoppingCart() {
		items = new ArrayList<String>();
		totalCost = 0;
	}

	public void addItem(int quantity, int productID, double unitCost, String name) {
		String order = name + "," +quantity + "," + productID + "," + unitCost;
		items.add(order);
		totalCost = totalCost + (quantity * unitCost);
		return;
	}

	public void removeItem(int quantity, int productID, double unitCost, String name) {
		String order = name + "," + quantity + "," + productID + "," + unitCost;
		items.remove(order);
		totalCost = totalCost - (quantity * unitCost);
		return;
	}
    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }
    public Double getTotalCost(){
    	return totalCost;
    }
    public void setTotalCost(Double totalCost){
    	this.totalCost = totalCost;
    }
}
