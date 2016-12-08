/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sugarhouse.database;

import com.sugarhouse.business.Order;
import com.sugarhouse.business.Product;
import com.sugarhouse.business.Shopper;
import com.sugarhouse.business.ShoppingCart;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
            
            DatabaseMetaData dbm;

            dbm = conn.getMetaData();
            ResultSet tables;
            
            tables = dbm.getTables(null, null, "USERS",null);  
            if(!tables.next())
                createUsers();
            
            tables = dbm.getTables(null, null, "PRODUCTS",null);  
            if(!tables.next())
                createInventory();
            
            tables = dbm.getTables(null, null, "ORDERS",null);  
            if(!tables.next())
                createOrders();
            
            tables = dbm.getTables(null, null, "ITEMS",null);  
            if(!tables.next())
                createItems();    
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

    }
    
    public List<String> getAllTableNames()
    {
        Statement stmt = null;
        ArrayList<String> tables = new ArrayList<String>();
        ResultSet ret;
        try {
            stmt = conn.createStatement();
            
            ret = stmt.executeQuery("select TABLE_NAME from sys.Tables");

            while(ret.next())
            {
                tables.add(ret.getString(1));
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
        
        return tables;
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
    
    public List<String> getUsers()
    {
        ArrayList<String> users = new ArrayList<String>();
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
                
                String userInfo = id +","+login+","+pw+","+email;
                users.add(userInfo);
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
        return users;
    }
    
    public String getUsersLogin(int id)
    {
        Statement stmt = null;
        ResultSet ret = null;
        String usr_login = null;
        try {
            stmt = conn.createStatement();
            
            ret = stmt.executeQuery("select LOGIN_ID from users where ID= " + id);

            while(ret.next())
            {
                 usr_login = ret.getString(1);
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
        return usr_login;
    }
    
    public Shopper verifiyUser(String login, String password)
    {
        Shopper retVal = null;
        
        Statement stmt = null;
        ResultSet ret = null;
        try {
            stmt = conn.createStatement();
            
            ret = stmt.executeQuery("select LOGIN_ID, ID from users where (login_id ='" + login +"' and password ='" + password +"')");

                Shopper usr = null;
                while(ret.next())
                {
                     usr = new Shopper(ret.getString(1),ret.getInt(2));
                }
                retVal = usr;
            
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
             +"PROD_COST DECIMAL(12,2),"
             +"PROD_INVENTORY INTEGER,"
             +"PROD_IMG_SRC VARCHAR(512),"
             + "NEW_PROD SMALLINT)");
            
            
            stmt.execute("INSERT INTO PRODUCTS VALUES (1,'Maple Leaves','1 Dozen Pure Maple Leave Candies', 6.99,200,'assets/img/new_candy.jpg',0)");
            stmt.execute("INSERT INTO PRODUCTS VALUES (2,'Fancy Syrup','1 Gallon of Pure Vermont Fancy Maple Syrup',48.99, 300,'assets/img/spoon.jpg',0)");
            stmt.execute("INSERT INTO PRODUCTS VALUES (3,'Fancy Syrup','1 Quart of Pure Vermont Fancy Maple Syrup',32.99, 300,'assets/img/spoon.jpg',0)");
            stmt.execute("INSERT INTO PRODUCTS VALUES (4,'Fancy Syrup','1 Pint of Pure Vermont Fancy Maple Syrup',14.99, 300,'assets/img/spoon.jpg',0)");
            stmt.execute("INSERT INTO PRODUCTS VALUES (5,'Syrup Sampler','Golden, Amber, and Dark Maple Syrup Sampler (12oz ea.)',12.99, 300,'assets/img/sampler.png',0)");
            stmt.execute("INSERT INTO PRODUCTS VALUES (6,'Maple BBQ Sauce','Maple infused BBQ Sauce (24oz)',10.99, 300,'assets/img/maple_bbq.jpg',0)");
            stmt.execute("INSERT INTO PRODUCTS VALUES (7,'Maple Cream','Pure Maple Syrup Spread goes perfectly with morning toast or other baked goods (8oz)',6.99, 300,'assets/img/maple_spread.jpg',0)");
            stmt.execute("INSERT INTO PRODUCTS VALUES (8,'Maple Butter','Pure Maple Syrup Spread goes perfectly with morning toast or other baked goods (8oz)',7.99, 300,'assets/img/maple_spread.jpg',0)");
            stmt.execute("INSERT INTO PRODUCTS VALUES (9,'Maple Sugar','Pure, Granulated, Maple Sugar (16oz)',15.99, 300,'assets/img/maple_sugar.jpg',0)");
            stmt.execute("INSERT INTO PRODUCTS VALUES (10,'Maple Basket','This holiday treat contains: Maple Cream (8oz), Maple Butter (8oz), Maple Leaves (12pc), 1 Pint of Syrup, Pancake Mix, and Maple Fudge (8oz).',59.99, 300,'assets/img/maple_basket.jpg',0)");
            stmt.execute("INSERT INTO PRODUCTS VALUES (11,'Maple Fudge','Pure Maple Fudge (8oz)',8.99, 300,'assets/img/maple_fudge.jpg',0)");
            stmt.execute("INSERT INTO PRODUCTS VALUES (12,'Medium Amber Syrup','1 Gallon of Pure Vermont Medium Amber Maple Syrup',48.99, 300,'assets/img/spoon.jpg',0)");
            stmt.execute("INSERT INTO PRODUCTS VALUES (13,'Medium Amber Syrup','1 Quart of Pure Vermont Medium Amber Maple Syrup',32.99, 300,'assets/img/spoon.jpg',0)");
            stmt.execute("INSERT INTO PRODUCTS VALUES (14,'Medium Amber Syrup','1 Pint of Pure Vermont Medium Amber Maple Syrup',14.99, 300,'assets/img/spoon.jpg',0)");
            stmt.execute("INSERT INTO PRODUCTS VALUES (15,'Dark Amber Syrup','1 Gallon of Pure Vermont Dark Amber Maple Syrup',48.99, 300,'assets/img/spoon.jpg',0)");
            stmt.execute("INSERT INTO PRODUCTS VALUES (16,'Dark Amber Syrup','1 Quart of Pure Vermont Dark Amber Maple Syrup',32.99, 300,'assets/img/spoon.jpg',0)");
            stmt.execute("INSERT INTO PRODUCTS VALUES (17,'Dark Amber Syrup','1 Pint of Pure Vermont Dark Amber Maple Syrup',14.99, 300,'assets/img/spoon.jpg',0)");
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
                int isNew = ret.getInt(7);
                Product prod = new Product(id, name, desc, cost, inventory, imgSrc, isNew);
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
             +"ORDER_TOTAL_COST DECIMAL(6,2),"
             +"ORDER_CREATED_DATE DATE,"
             +"ORDER_STATUS VARCHAR(64))");

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
    
    public void insertOrder(int usr_id, double total_cost)
    {
        Statement stmt = null;
        ResultSet ret = null;
        try {
            stmt = conn.createStatement();
            
            int newId;
            int currentMax = 0;
            //Calculate latest ID value
            ret = stmt.executeQuery("SELECT ID FROM ORDERS WHERE id=(SELECT MAX(id) FROM ORDERS)");
            while(ret.next())
            {
                currentMax = ret.getInt(1);

            }
            newId = currentMax + 1;
            
            //Generate current date
            //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            java.util.Date date_now = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(date_now.getTime());
            String status = "Confirmed";
            //Insert new user into table with all their information
            stmt.execute("INSERT INTO ORDERS (ID, USER_ID, ORDER_TOTAL_COST, ORDER_CREATED_DATE, ORDER_STATUS ) VALUES ("+ newId+ "," + usr_id +"," + total_cost +",'"+ sqlDate + "','" + status +"')");
            System.out.println("Successfully inserted new order: " + newId);
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
    
    public List<Order> getOrders(String sort)
    {
        String orderBy = "ID";
        if(sort.equals("date"))
            orderBy = "ORDER_CREATED_DATE";
        
        Statement stmt = null;
        ResultSet ret = null;
        List<Order> orders = new ArrayList<Order>();
        try {
            stmt = conn.createStatement();
            
            ret = stmt.executeQuery("select * from orders order by " + orderBy + " asc");

            while(ret.next())
            {
                int id = ret.getInt(1);
                int uid = ret.getInt(2);
                double cost = ret.getDouble(3);
                java.sql.Date date = ret.getDate(4);
                String status = ret.getString(5);
                
                Order order = new Order(id, uid, cost, date, status);
                orders.add(order);
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
        
        return orders;
        
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
    
    public void updateInventory(int id, String name, double cost, int units, String description, int isNew)
    {
        Statement stmt = null;
        ResultSet ret = null;
        try {
            
           stmt = conn.createStatement();
           
           if(stmt != null)
           {
               
            stmt.execute("UPDATE PRODUCTS SET PROD_NAME ='" + name + "', PROD_DESCRIPTION ='" + description + "', PROD_COST = " + cost + ", PROD_INVENTORY =" + units + ", NEW_PROD=" + isNew + " WHERE ID =" + id);
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
    
    public void insertInventory(String name, double cost, int units, String description, int isNew)
    {
        Statement stmt = null;
        ResultSet ret = null;
        try {
            stmt = conn.createStatement();
            
            int newId;
            int currentMax = 0;
            //Calculate latest ID value
            ret = stmt.executeQuery("SELECT ID FROM PRODUCTS WHERE id=(SELECT MAX(id) FROM PRODUCTS)");
            while(ret.next())
            {
                currentMax = ret.getInt(1);

            }
            newId = currentMax + 1;
            //Insert new user into table with all their information
            stmt.execute("INSERT INTO PRODUCTS (ID, PROD_NAME, PROD_DESCRIPTION, PROD_COST, PROD_INVENTORY, PROD_IMG_SRC, NEW_PROD ) VALUES ("+ newId+ ",'" + name +"','" + description +"',"+ cost + "," + units +",'assets/img/leaf.jpg',"+isNew+")");
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
    
        public void removeInventoryItem(int prod_id)
    {
        Statement stmt = null;
        try {
            
           stmt = conn.createStatement();
           
           if(stmt != null)
           {
                stmt.execute("DELETE FROM PRODUCTS WHERE ID =" + prod_id);
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
    
    public int getInventoryUnits(int id)
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
    
    public List<String> getItems()
    {
        ArrayList<String> items = new ArrayList<String>();
        Statement stmt = null;
        ResultSet ret = null;
        try {
            stmt = conn.createStatement();
            
            ret = stmt.executeQuery("select * from items");
            while(ret.next())
                {
                    String itemInfo = ret.getInt(1)+","+ret.getInt(2)+","+ret.getString(3)+","+ret.getInt(4)+","+ret.getInt(5)+","+ret.getDouble(6);
                    items.add(itemInfo);
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
        return items;
    }
    
    private void createItems()
    {
        Statement stmt = null;
        try {
            
           stmt = conn.createStatement();
           
           if(stmt != null){
            stmt.executeUpdate("CREATE TABLE ITEMS ( "
             +"ID INTEGER not null primary key,"
             +"USER_ID INTEGER,"
             +"NAME VARCHAR(512),"
             +"PROD_ID INTEGER,"
             +"QUANTITY INTEGER,"
             +"COST DECIMAL(6,2))");

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
    
    public void getAllItems()
    {
        Statement stmt = null;
        ResultSet ret = null;
        int inStock = -1;
        try {
            
           stmt = conn.createStatement();
           
           if(stmt != null)
           {
                //Calculate latest ID value
                ret = stmt.executeQuery("SELECT * FROM ITEMS");
                while(ret.next())
                {
                    System.out.println("(" + ret.getInt(1) +", "+ ret.getInt(2) +", "+ ret.getString(3)+", "+ ret.getInt(4)+", "+ ret.getInt(5)+", "+ ret.getDouble(6) +")");
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
        
    }
    
    public ShoppingCart getItems(int usr_id)
    {
        ShoppingCart sc = new ShoppingCart();
        Statement stmt = null;
        ResultSet ret = null;
        int inStock = -1;
        try {
            
           stmt = conn.createStatement();
           
           if(stmt != null)
           {
                //Calculate latest ID value
                ret = stmt.executeQuery("SELECT NAME, PROD_ID, QUANTITY, COST FROM ITEMS WHERE USER_ID=" + usr_id);
                while(ret.next())
                {
                    sc.addItem(ret.getInt(3), ret.getInt(2), ret.getDouble(4), ret.getString(1));
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
        return sc;
    }
    
    public void removeItems(int usr_id)
    {
        Statement stmt = null;
        try {
            
           stmt = conn.createStatement();
           
           if(stmt != null)
           {
                stmt.execute("DELETE FROM ITEMS WHERE USER_ID =" + usr_id);
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
    
    public void addItems(int usr_id, ShoppingCart cart)
    {
        Statement stmt = null;
        ResultSet ret = null;
        try {
            stmt = conn.createStatement();
                for (String items : cart.getItems()) {
                int newId;
                int currentMax = 0;
                //Calculate latest ID value
                ret = stmt.executeQuery("SELECT ID FROM ITEMS WHERE id=(SELECT MAX(id) FROM ITEMS)");
                while(ret.next())
                {
                    currentMax = ret.getInt(1);

                }
                newId = currentMax + 1;
            
                String[] splitItem = items.split(",");
                String name = splitItem[0];
                String quantity = splitItem[1];
                String productID = splitItem[2];
                Double singleItemCost = Double.parseDouble(splitItem[3]);
            
            
                stmt.execute("INSERT INTO ITEMS (ID, USER_ID, NAME, PROD_ID, QUANTITY, COST ) VALUES ("+ newId+ "," + usr_id +",'" + name +"',"+ productID + "," + quantity + "," + singleItemCost  +")");
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
    
    
   
}
