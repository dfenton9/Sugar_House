/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sugarhouse.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
        
        createTables();
    }
    
    private void createTables()
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
            System.out.println("Table was created!");
            stmt.close();
            stmt = null;

            stmt = conn.createStatement();
            stmt.execute("INSERT INTO USERS VALUES (1,'superuser','easyAce123','super.user@foo.com')");
            stmt.close();
            stmt = null;

            stmt = conn.createStatement();
            stmt.execute("INSERT INTO USERS VALUES (2,'admin','admin1234','admin.user@foo.com')");
            stmt.close();
            stmt = null;
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
            ResultSetMetaData rsmd = ret.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i=1; i<=numberCols; i++)
            {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i)+"\t\t");  
            }

            System.out.println("\n-------------------------------------------------");

            while(ret.next())
            {
                int id = ret.getInt(1);
                String login = ret.getString(2);
                String pw = ret.getString(3);
                String email = ret.getString(4);
                System.out.println(id + "\t\t" +login + "\t\t" + pw + "\t\t" + email);
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
    
    public boolean verifiyUser(String login, String password)
    {
        boolean retVal = false;
        
                Statement stmt = null;
        ResultSet ret = null;
        try {
            stmt = conn.createStatement();
            
            System.out.println("Login: " + login + ", PW: " + password);
            ret = stmt.executeQuery("select LOGIN_ID from users where (login_id ='" + login +"' and password ='" + password +"')");
            ResultSetMetaData rsmd = ret.getMetaData();
            int numberCols = rsmd.getColumnCount();

            for (int i=1; i<=numberCols; i++)
            {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i)+"\t\t");  
            }

            System.out.println("\n-------------------------------------------------");

                while(ret.next())
                {
                    String login_id = ret.getString(1);
                    System.out.println(login_id);
                }
                retVal = true;
            
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
            int newId;
            int currentMax = 0;
            ret = stmt.executeQuery("SELECT ID FROM USERS WHERE id=(SELECT MAX(id) FROM USERS)");
            while(ret.next())
            {
                currentMax = ret.getInt(1);
                 
            }
            newId = currentMax + 1;
            
            System.out.println("ID: " + newId + ", Login: " + login + ", PW: " + password + ", Email: " + email);
            stmt.execute("INSERT INTO USERS (ID, LOGIN_ID, PASSWORD, EMAIL) VALUES ("+ newId+ ",'" + login +"','" + password +"','"+ email +"')");
            retVal = true;
            
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
}
