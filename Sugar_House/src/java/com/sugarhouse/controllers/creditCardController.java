/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sugarhouse.controllers;

import com.sugarhouse.business.Shopper;
import com.sugarhouse.business.ShoppingCart;
import com.sugarhouse.database.DatabaseCreator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author danielfenton
 */
public class creditCardController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String errMsg = "";
        String url = "/checkout.jsp";

        HttpSession session = request.getSession();
        if(session.getAttribute("databaseConnection") == null)
        {
           session.setAttribute("databaseConnection", new DatabaseCreator());
        }
        DatabaseCreator dc = (DatabaseCreator)session.getAttribute("databaseConnection");
        errMsg = "";
        String creditType = request.getParameter("cardType");
        String creditNumber = request.getParameter("creditNumber");
        String expirationDate = request.getParameter("date");
        String regex = "\\d+";
        System.out.println("Credit type is: " + creditType);
        System.out.println("Credit number is: " + creditNumber);
        System.out.println("Exp date is: " + expirationDate);

        //Check if all fields were entered
        if(creditType == null || creditNumber == null || expirationDate == null){
                errMsg += "One or more fields was left empty. Please fill out all fields to continue.";
        }
        //check if credit number is numeric
        if(!creditNumber.matches(regex)){
                errMsg += "Credit number provided is invalid.";
                System.out.println("Invalid credit number");
        }
        if(errMsg.equals(""))
        {
            session = request.getSession();
            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
            //Modify the inventory to reflect the completion of this order
            updateInventory(cart, dc);
            //Add this order to the Orders table
            dc.insertOrder(((Shopper)session.getAttribute("User")).getId(), cart.getTotalCost());
            dc.removeItems(((Shopper)session.getAttribute("User")).getId());
            cart.resetCart();
            //Redirect user to the thank you page
            url = "/thankyou.jsp";
            session.setAttribute("cart", cart);
        }
        
        session.setAttribute("ErrorMsg", errMsg);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);         
    }
    
    private void updateInventory( ShoppingCart cart, DatabaseCreator dc)
    {
        List<String> items = new ArrayList<String>();
        items = cart.getItems();

        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            String[] splitItem = item.split(",");
            int itemQuantity = Integer.parseInt(splitItem[1]);
            int itemID = Integer.parseInt(splitItem[2]);

            dc.updateInventory(itemID, itemQuantity);


        }
    }    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
