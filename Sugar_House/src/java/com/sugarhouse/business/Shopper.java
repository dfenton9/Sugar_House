/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sugarhouse.business;

import java.io.Serializable;

/**
 *
 * @author Fenton
 */
public class Shopper implements Serializable{
    
    private String name;
    private int id;
    
    public Shopper(String name, int id)
    {
        this.name = name;
        this.id = id;
    }
    
    /**
     * Getters
     */
    public String getName()
    {
        return name;
    }
    
    public int getId()
    {
        return id;
    }
    
}
