/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sugarhouse.business;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Fenton
 */
public class ShoppingCart implements Serializable{
    
    private List items;
    private double totalCost;
    
    public ShoppingCart()
    {
        
    }
}
