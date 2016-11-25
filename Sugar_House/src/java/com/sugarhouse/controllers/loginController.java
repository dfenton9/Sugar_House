/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sugarhouse.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Fenton
 */
public class loginController extends HttpServlet {

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

		if(action.equals("new user")){
			//Redirect user to registration page if they are new
			url = "/register.jsp";
		}
		if(action.equals("register")){
			//Get registration parameters
			String login = request.getParameter("loginId");
	        String email = request.getParameter("email");
	        String confirmEmail = request.getParameter("confirmEmail");
	        String password = request.getParameter("password");
	        String confirmPassword = request.getParameter("confirmPassword");
	        
	        //Ensure all fields were filled out
	        if(login.isEmpty() || email.isEmpty() || confirmEmail.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
	        	errMsg = "One or more fields can be left empty.";
	        }
	        
	        //Ensure the same email/password was entered in confirmation field
	        if(!email.equals(confirmEmail))
	            errMsg += "Emails do not match. ";
	    
	        if(!password.equals(confirmPassword))
	            errMsg += "Passwords do not match. ";
	        
	        //If no registration issues occur, redirect user to login
	        if(!errMsg.equals(""))
	            url = "/logIn.jsp";
	        
	        //TODO: Enter user into database
		}
		if(action.equals("login")){
	        String login = request.getParameter("loginId");
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");
	        
	        //TODO:Check if username/password combination is correct
	        
	      //If no login issues occur, redirect user to the home page
	        if(!errMsg.equals(""))
	            url = "/index.jsp";

		}

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
