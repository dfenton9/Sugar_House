/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sugarhouse.controllers;

import com.sugarhouse.business.CartItem;
import com.sugarhouse.business.Shopper;
import com.sugarhouse.business.ShoppingCart;
import com.sugarhouse.database.DatabaseCreator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sugarhouse.util.MailUtilGmail;

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
        
        // get user name and email
        String emailAddress = String.valueOf(session.getAttribute("email"));
        String fullName = ((Shopper)session.getAttribute("User")).getName();
        Double totalCost = cart.getTotalCost();
        
		// send email to user
		String to = emailAddress;
		String from = "FA16.605782@gmail.com";
		String subject = "Shopping Confirmation - Sugar House";
		String body = "Dear " + fullName + ",\n\n" + "Thank you for shopping with Sugar House. Your purchase total was $" + totalCost + "0. Expect to recieve your items in 3-5 business days. Have a great day and thanks again!\n\n" + "Sugar House team";
		boolean isBodyHTML = false;
		try {
			MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
		} catch (MessagingException e) {
			errMsg = "ERROR: Unable to send email. " + "Check Tomcat logs for details. " + "ERROR MESSAGE: " + e.getMessage();
            url = "/shoppingCart.jsp";
			this.log("Unable to send email. \n" + "Here is the email you tried to send: \n" + "=====================================\n" + "TO: " + emailAddress + "\n" + "FROM: " + from + "\n" + "SUBJECT: " + subject + "\n" + "\n" + body + "\n\n");
		}
        
        session.setAttribute("ErrorMsg", errMsg);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);         
    }
    
    private void updateInventory( ShoppingCart cart, DatabaseCreator dc)
    {
        List<CartItem> items = new ArrayList<CartItem>();
        items = cart.getItems();

        for (CartItem item : items) {

            dc.updateInventory(item.getProdId(), item.getQuantity());


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
