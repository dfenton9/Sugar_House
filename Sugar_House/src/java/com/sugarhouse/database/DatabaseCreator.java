/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sugarhouse.database;

import com.sugarhouse.business.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author danielfenton
 */
public class DatabaseCreator {
    
    private Connection conn;
    private String protocol = "jdbc:derby:memory:";
    
    public DatabaseCreator()
    {
        conn = null;
        try
        {
            
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            Properties props = new Properties(); // connection properties
            // providing a user name and password is optional in the embedded
            // and derbyclient frameworks
            props.put("user", "user1");
            props.put("password", "user1");

            /* By default, the schema APP will be used when no username is
             * provided.
             * Otherwise, the schema name is the same as the user name (in this
             * case "user1" or USER1.)
             *
             * Note that user authentication is off by default, meaning that any
             * user can connect to your database using any password. To enable
             * authentication, see the Derby Developer's Guide.
             */

            String dbName = "sugarhouseDB"; // the name of the database

            /*
             * This connection specifies create=true in the connection URL to
             * cause the database to be created when connecting for the first
             * time. To remove the database, remove the directory derbyDB (the
             * same as the database name) and its contents.
             *
             * The directory derbyDB will be created under the directory that
             * the system property derby.system.home points to, or the current
             * directory (user.dir) if derby.system.home is not set.
             */
            conn = DriverManager.getConnection(protocol + dbName
                    + ";create=true", props);

            System.out.println("Connected to and created database " + dbName);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        
        createUsers();
        createInventory();
    }
    
    private void createUsers()
    {
        Statement stmt = null;
        try {
            
           stmt = conn.createStatement();
           
           if(stmt != null){
            stmt.executeUpdate("CREATE TABLE USERS ( "
             +"ID INTEGER not null primary key,"
             +"LOGIN_ID VARCHAR(30),"
             +"PASSWORD VARCHAR(20),"
             +"EMAIL VARCHAR(60))");
            
            stmt.execute("INSERT INTO USERS VALUES (1,'superuser','easyAce123','super.user@foo.com')");
            stmt.execute("INSERT INTO USERS VALUES (2,'admin','admin1234','admin.user@foo.com')");
           }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally
        {
           // Release resources
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void getUsers()
    {
        Statement stmt = null;
        ResultSet ret = null;
        try {
            stmt = conn.createStatement();
            
            ret = stmt.executeQuery("select * from users");

            while(ret.next())
            {
                int id = ret.getInt(1);
                String login = ret.getString(2);
                String pw = ret.getString(3);
                String email = ret.getString(4);
            }            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally
        {
            try {
                if(ret != null)
                    ret.close();
                if(stmt != null)
                    stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            ret = null;
            stmt = null;
        }
        
    }
    
    public String verifiyUser(String login, String password)
    {
        String retVal = null;
        
                Statement stmt = null;
        ResultSet ret = null;
        try {
            stmt = conn.createStatement();
            
            System.out.println("Login: " + login + ", PW: " + password);
            ret = stmt.executeQuery("select LOGIN_ID from users where (login_id ='" + login +"' and password ='" + password +"')");

                String login_id = null;
                while(ret.next())
                {
                     login_id = ret.getString(1);
                }
                retVal = login_id;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally
        {
            try {
                if(ret != null)
                    ret.close();
                if(stmt != null)
                    stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            ret = null;
            stmt = null;
        }
        System.out.println("retVal: " + retVal);
        return retVal;
    }
    
    public boolean registerUser(String login, String password, String email)
    {
        boolean retVal = false;

        Statement stmt = null;
        ResultSet ret = null;
        try {
            stmt = conn.createStatement();
            // Check if login currently exists in database
            ret = stmt.executeQuery("SELECT count(*) AS rowcount FROM USERS WHERE LOGIN_ID='" + login + "'");
            int count = -1;
            while(ret.next())
            {
                count = ret.getInt(1);
                 
            }
            
            if(count == 0)
            {
                int newId;
                int currentMax = 0;
                //Calculate latest ID value
                ret = stmt.executeQuery("SELECT ID FROM USERS WHERE id=(SELECT MAX(id) FROM USERS)");
                while(ret.next())
                {
                    currentMax = ret.getInt(1);

                }
                newId = currentMax + 1;
                //Insert new user into table with all their information
                stmt.execute("INSERT INTO USERS (ID, LOGIN_ID, PASSWORD, EMAIL) VALUES ("+ newId+ ",'" + login +"','" + password +"','"+ email +"')");
                retVal = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally
        {
            try {
                if(ret != null)
                    ret.close();
                if(stmt != null)
                    stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            ret = null;
            stmt = null;
        }
        
        return retVal;
    }
    
    private void createInventory()
    {
        Statement stmt = null;
        try {
            
           stmt = conn.createStatement();
           
           if(stmt != null){
            stmt.executeUpdate("CREATE TABLE PRODUCTS ( "
             +"ID INTEGER not null primary key,"
             +"PROD_NAME VARCHAR(48),"
             +"PROD_DESCRIPTION VARCHAR(512),"
             +"PROD_COST DECIMAL(6,2),"
             +"PROD_INVENTORY INTEGER,"
             +"PROD_IMG_SRC VARCHAR(512))");
            
            
            stmt.execute("INSERT INTO PRODUCTS VALUES (1,'Maple Leaves','1 Dozen Pure Maple Leave Candies', 6.99,200,'assets/img/new_candy.jpg')");
            stmt.execute("INSERT INTO PRODUCTS VALUES (2,'Fancy Syrup','1 Gallon of Pure Vermont Fancy Maple Syrup',48.99, 300,'assets/img/spoon.jpg')");
            stmt.execute("INSERT INTO PRODUCTS VALUES (3,'Fancy Syrup','1 Quart of Pure Vermont Fancy Maple Syrup',32.99, 300,'assets/img/spoon.jpg')");
            stmt.execute("INSERT INTO PRODUCTS VALUES (4,'Fancy Syrup','1 Pint of Pure Vermont Fancy Maple Syrup',14.99, 300,'assets/img/spoon.jpg')");
            stmt.execute("INSERT INTO PRODUCTS VALUES (5,'Syrup Sampler','Golden, Amber, and Dark Maple Syrup Sampler (12oz ea.)',12.99, 300,'assets/img/sampler.png')");
            stmt.execute("INSERT INTO PRODUCTS VALUES (6,'Maple BBQ Sauce','Maple infused BBQ Sauce (24oz)',10.99, 300,'assets/img/maple_bbq.jpg')");
            stmt.execute("INSERT INTO PRODUCTS VALUES (7,'Maple Cream','Pure Maple Syrup Spread goes perfectly with morning toast or other baked goods (8oz)',6.99, 300,'assets/img/maple_spread.jpg')");
            stmt.execute("INSERT INTO PRODUCTS VALUES (8,'Maple Butter','Pure Maple Syrup Spread goes perfectly with morning toast or other baked goods (8oz)',7.99, 300,'assets/img/maple_spread.jpg')");
            stmt.execute("INSERT INTO PRODUCTS VALUES (9,'Maple Sugar','Pure, Granulated, Maple Sugar (16oz)',15.99, 300,'assets/img/maple_sugar.jpg')");
            stmt.execute("INSERT INTO PRODUCTS VALUES (10,'Maple Basket','This holiday treat contains: Maple Cream (8oz), Maple Butter (8oz), Maple Leaves (12pc), 1 Pint of Syrup, Pancake Mix, and Maple Fudge (8oz).',59.99, 300,'assets/img/maple_basket.jpg')");
            stmt.execute("INSERT INTO PRODUCTS VALUES (11,'Maple Fudge','Pure Maple Fudge (8oz)',8.99, 300,'assets/img/maple_fudge.jpg')");
            stmt.execute("INSERT INTO PRODUCTS VALUES (12,'Medium Amber Syrup','1 Gallon of Pure Vermont Medium Amber Maple Syrup',48.99, 300,'assets/img/spoon.jpg')");
            stmt.execute("INSERT INTO PRODUCTS VALUES (13,'Medium Amber Syrup','1 Quart of Pure Vermont Medium Amber Maple Syrup',32.99, 300,'assets/img/spoon.jpg')");
            stmt.execute("INSERT INTO PRODUCTS VALUES (14,'Medium Amber Syrup','1 Pint of Pure Vermont Medium Amber Maple Syrup',14.99, 300,'assets/img/spoon.jpg')");
            stmt.execute("INSERT INTO PRODUCTS VALUES (15,'Dark Amber Syrup','1 Gallon of Pure Vermont Dark Amber Maple Syrup',48.99, 300,'assets/img/spoon.jpg')");
            stmt.execute("INSERT INTO PRODUCTS VALUES (16,'Dark Amber Syrup','1 Quart of Pure Vermont Dark Amber Maple Syrup',32.99, 300,'assets/img/spoon.jpg')");
            stmt.execute("INSERT INTO PRODUCTS VALUES (17,'Dark Amber Syrup','1 Pint of Pure Vermont Dark Amber Maple Syrup',14.99, 300,'assets/img/spoon.jpg')");
           }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally
        {
           // Release resources
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public List<Product> getProducts(String order)
    {
        String orderBy = "PROD_NAME";
        if(order.equals("id"))
            orderBy = "ID";
        
        Statement stmt = null;
        ResultSet ret = null;
        List<Product> products = new ArrayList<Product>();
        try {
            stmt = conn.createStatement();
            
            ret = stmt.executeQuery("select * from products order by " + orderBy + " asc");

            while(ret.next())
            {
                int id = ret.getInt(1);
                String name = ret.getString(2);
                String desc = ret.getString(3);
                double cost = ret.getDouble(4);
                int inventory = ret.getInt(5);
                String imgSrc = ret.getString(6);
                Product prod = new Product(id, name, desc, cost, inventory, imgSrc);
                products.add(prod);
            }            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally
        {
            try {
                if(ret != null)
                    ret.close();
                if(stmt != null)
                    stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            ret = null;
            stmt = null;
        }
        
        return products;
        
    }
    
    private void createOrders()
    {
        Statement stmt = null;
        try {
            
           stmt = conn.createStatement();
           
           if(stmt != null){
            stmt.executeUpdate("CREATE TABLE ORDERS ( "
             +"ID INTEGER not null primary key,"
             +"USER_ID INTEGER,"
             +"ORDER_INFORMATION VARCHAR(4096),"
             +"ORDER_COST DECIMAL(6,2),"
             +"PROD_INVENTORY INTEGER,"
             +"PROD_IMG_SRC VARCHAR(512))");
            System.out.println("Table was created!");

           }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally
        {
           // Release resources
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void updateInventory(int id, int amount)
    {
        Statement stmt = null;
        ResultSet ret = null;
        try {
            
           stmt = conn.createStatement();
           
           if(stmt != null)
           {
               int newVal;
                int val = 0;
                //Calculate latest ID value
                ret = stmt.executeQuery("SELECT PROD_INVENTORY FROM PRODUCTS WHERE ID=" + id);
                while(ret.next())
                {
                    val = ret.getInt(1);

                }
                if(val - amount < 0)
                    newVal = 0;
                else
                    newVal = val - amount;
               
            stmt.execute("UPDATE PRODUCTS SET PROD_INVENTORY =" + newVal + " WHERE ID =" + id);
           }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally
        {
           // Release resources
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public int getInventory(int id)
    {
        Statement stmt = null;
        ResultSet ret = null;
        int inStock = -1;
        try {
            
           stmt = conn.createStatement();
           
           if(stmt != null)
           {
                //Calculate latest ID value
                ret = stmt.executeQuery("SELECT PROD_INVENTORY FROM PRODUCTS WHERE ID=" + id);
                while(ret.next())
                {
                    inStock = ret.getInt(1);

                }
           }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally
        {
           // Release resources
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }  
        
        return inStock;
    }
}
