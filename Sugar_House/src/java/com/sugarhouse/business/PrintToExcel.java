/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sugarhouse.business;

import com.sugarhouse.database.DatabaseCreator;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author danielfenton
 */
public class PrintToExcel extends HttpServlet {


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


        HttpSession session = request.getSession();
        DatabaseCreator dc = (DatabaseCreator)session.getAttribute("databaseConnection");
        String reportType = request.getParameter("ReportType");
        
        List<Order> orders = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        boolean runReport = true;
        System.out.println("ReportType: " + reportType);
        if(reportType.equals("Orders"))
        {
            orders = dc.getOrders("id");
            System.out.println("Order: " + orders.size());
        }else if(reportType.equals("Inventory"))
        {
            products = dc.getProducts("id");
            System.out.println("Product: " + products.size());
        }else
        {
            runReport = false;
            System.out.println("Invalid report type. Must be [Orders] or [Inventory].");
        }
        
        
        
        if(runReport)
        {
            StringBuilder report = new StringBuilder("Sugar House " + reportType +" Report\n\n");
            if(reportType.equals("Inventory"))
            {
                report.append("ID\t").append("Name\t").append("Description\t").append("Inventory\t").append("Unit Cost($)\n");
                for(Product prod : products)
                {
                    report.append(prod.getId()).append("\t").append(prod.getName()).append("\t").append(prod.getDescription()).append("\t").append(prod.getInventory()).append("\t$").append(prod.getCost()).append("\n");
                }
            }
            
            if(reportType.equals("Orders"))
            {
                report.append("ID\t").append("Customer\t").append("Order Date\t").append("Order Status\t").append("Total Costs($)\n");
                for(Order order : orders)
                {
                    report.append(order.getID()).append("\t").append(dc.getUsersLogin(order.getUID())).append("\t").append(order.getOrderDate()).append("\t").append(order.getStatus()).append("\t$").append(order.getTotalCost()).append("\n");
                }  
            }

            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            Date today = new Date();
            String filename = df.format(today)+"_" + reportType + "_Summary.xls";

            response.setHeader("content-disposition","attachment; filename=" + filename);
            response.setHeader("cache-control", "no-cache");
            PrintWriter out = response.getWriter();
            out.println(report);
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
