/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sugarhouse.controllers;

import com.sugarhouse.business.ShoppingCart;
import com.sugarhouse.database.DatabaseCreator;
import java.io.IOException;
import java.io.PrintWriter;
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
public class shoppingCartController extends HttpServlet {

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
        String url = "/index.jsp";
        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        if(session.getAttribute("databaseConnection") == null)
        {
           session.setAttribute("databaseConnection", new DatabaseCreator());
        }
        DatabaseCreator dc = (DatabaseCreator)session.getAttribute("databaseConnection");
        
        if(action.equals("add") || action.equals("addNew") || action.equals("remove")){

            //TODO: If user is not logged in, redirect to login page

            String quantityString = request.getParameter("quantity");
            int productID = Integer.parseInt(request.getParameter("ID"));
            double cost = Double.parseDouble(request.getParameter("cost"));
            String name = request.getParameter("name");



            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
            if (cart == null) {
                    cart = new ShoppingCart();
            }

            //if the user enters a negative or invalid quantity,
            //the quantity is automatically reset to 1.
            int quantity;
            try {
                    quantity = Integer.parseInt(quantityString);
                    if (quantity < 0) {
                            quantity = 1;
                    }
            } catch (NumberFormatException nfe) {
                    quantity = 1;
            }
            if(action.equals("add") || action.equals("addNew")){
                int inStock = dc.getInventoryUnits(productID);
                if(inStock - quantity < 1)
                {
                    errMsg = "Only " + inStock + " " + name +" product(s) in stock. Please enter a quantity of " + inStock +" or less.";
                }else
                {
                  cart.addItem(quantity, productID, cost, name);  
                }
                if(action.equals("add"))
                    url = "/marketplace.jsp";
                if(action.equals("addNew"))
                    url = "/newArrivals.jsp";
            }
            if(action.equals("remove")){
                    cart.removeItem(quantity, productID, cost, name);
                    url = "/shoppingCart.jsp";
            }
            session.setAttribute("cart", cart);


        }
        
        session.setAttribute("ErrorMsg", errMsg);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response); 
        
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
