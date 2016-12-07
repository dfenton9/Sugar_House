/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sugarhouse.controllers;

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
public class inventoryController extends HttpServlet {

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
        String url = "/manageInventory.jsp";
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        if(session.getAttribute("databaseConnection") == null)
        {
           session.setAttribute("databaseConnection", new DatabaseCreator());
        }
        DatabaseCreator dc = (DatabaseCreator)session.getAttribute("databaseConnection");
        

        
        if(action.equals("update"))
        {
            
            int productID = Integer.parseInt(request.getParameter("id"));
            int units = Integer.parseInt(request.getParameter("quantity"));

            String strCost = request.getParameter("cost");
            if(strCost.charAt(0) == '$')
            {
                strCost = strCost.substring(1);
            }

            double cost = Double.parseDouble(strCost);
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            
            dc.updateInventory(productID, name, cost, units, description);
        
            
        }else if (action.equals("insert"))
        {
            int units = Integer.parseInt(request.getParameter("quantity"));

            String strCost = request.getParameter("cost");
            if(strCost.charAt(0) == '$')
            {
                strCost = strCost.substring(1);
            }

            double cost = Double.parseDouble(strCost);
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            
            //dc.insertInventory(name, cost, units, description);
        }else if (action.equals("remove"))
        {
            int productID = Integer.parseInt(request.getParameter("id"));
            dc.removeInventoryItem(productID);
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
