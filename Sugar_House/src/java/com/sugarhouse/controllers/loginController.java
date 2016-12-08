/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sugarhouse.controllers;

import com.sugarhouse.business.Shopper;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sugarhouse.business.ShoppingCart;
import com.sugarhouse.database.DatabaseCreator;

/**
 *
 * @author Fenton
 */
@SuppressWarnings("serial")
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
                
                HttpSession session = request.getSession();
                if(session.getAttribute("databaseConnection") == null)
                {
                   session.setAttribute("databaseConnection", new DatabaseCreator());
                }
                DatabaseCreator dc = (DatabaseCreator)session.getAttribute("databaseConnection");

		if(action.equals("new user")){
			//Redirect user to registration page if they are new
			url = "/register.jsp";
		}
		if(action.equals("marketplace")){
			//Redirect user to the marketplace
			url = "/marketplace.jsp";
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
				errMsg = "One or more fields was left empty. Please fill out all fields to continue.";
				return;
			}

			//Ensure the same email/password was entered in confirmation field
			if(!email.equals(confirmEmail)){
				errMsg += "Emails do not match. ";
				return;
                        }

			if(!password.equals(confirmPassword)){
				errMsg += "Passwords do not match. ";
				return;
			}
                        
                        if(!dc.registerUser(login, password, email))
                        {
                            errMsg += "Not able to register user.";
                            return;
                        }
			//If no registration issues occur, redirect user to login
			if(errMsg.equals(""))
                        {
                            Shopper user = dc.verifiyUser(login, password);
                            session.setAttribute("User", user);
                            session.setAttribute("Login","true");
                            url = "/index.jsp";
                                
                        }
		}
		if(action.equals("login")){
			String login = request.getParameter("loginId");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
                        Shopper user = dc.verifiyUser(login, password);

			//If no login issues occur, redirect user to the home page
			if(user != null)
                        {
                            if(login.equals("admin"))
                            {
                                session.setAttribute("isAdmin", "yes");
                            }
                            else
                            {
                                session.setAttribute("isAdmin", "no");
                            }
                            
                            dc.getAllItems();
                            
                            ShoppingCart usrCart = dc.getItems(user.getId());
                            
                            if(session.getAttribute("cart") != null)
                            {
                               usrCart.appendItems(((ShoppingCart) session.getAttribute("cart")).getItems());
                            }
                            
                            session.setAttribute("cart", usrCart);
                            session.setAttribute("User", user);
                            session.setAttribute("Login","true");
                            url = "/index.jsp";
                        }else
                        {
                            errMsg = "Invlaid Login or Password.";
                            url = "/logIn.jsp";
                        }
			//TODO: If the user is an admin, redirect to the Admin View (inventory)
		}
                if(action.equals("logout")){
                    ShoppingCart cart = (ShoppingCart)session.getAttribute("cart");
                    int usr_id = ((Shopper)session.getAttribute("User")).getId();
                    dc.removeItems(usr_id);
                    dc.addItems(usr_id, cart);
                    dc.getAllItems();
                    session.setAttribute("isAdmin", "no");
                    session.setAttribute("Login", "false");
                    session.setAttribute("User", null);
                    session.setAttribute("cart", null);
                    errMsg = "Successfully logged out.";
                    url = "/logIn.jsp";
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
