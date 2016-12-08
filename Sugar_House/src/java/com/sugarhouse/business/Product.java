/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sugarhouse.business;

/**
 *
 * @author Fenton
 */
public class Product {
    
    private int id;
    private String name;
    private String description;
    private double cost;
    private int inventory;
    private String imgSrc;
    private int isNew;
    
    
    
    public Product()
    {
        
    }
    
    public Product(int id, String name, String description, double cost, int inventory, String imgSrc, int isNew)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.inventory = inventory;
        this.imgSrc = imgSrc;
        this.isNew = isNew;
    }
    
    /**
     * 
     * Setters
     * 
     */
    public void setId(int id)
    {
        this.id = id;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public void setCost(double cost)
    {
            this.cost = cost;
    }
    
    public void setInventory(int inventory)
    {
        this.inventory = inventory;
    }
    
    public void setImageSrc(String imgSrc)
    {
        this.imgSrc = imgSrc;
    }
    
    public void setIsNew(int newly)
    {
        this.isNew = newly;
    }
    
    /**
     * 
     * Getters
     * 
     */
    public int getId()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public double getCost()
    {
            return cost;
    }
    
    public int getInventory()
    {
        return inventory;
    }
    
    public String getImageSrc()
    {
        return imgSrc;
    }
    
    public int getIsNew()
    {
        return isNew;
    }
    
    /**
     * 
     * Helpers
     * 
     */
    
    public boolean isAvailable()
    {
        return (inventory > 0);
    }
}
