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
import javax.servlet.http.HttpSession;

import com.sugarhouse.business.ShoppingCart;

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

		if(action.equals("new user")){
			//Redirect user to registration page if they are new
			url = "/register.jsp";
		}
		if(action.equals("checkout")){
			//Redirect user to the checkout page
			url = "/checkout.jsp";
		}
		if(action.equals("marketplace")){
			//Redirect user to the marketplace
			url = "/marketplace.jsp";
		}
		if(action.equals("confirm")){
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
				return;
			}
			//check if credit number is numeric
			if(!creditNumber.matches(regex)){
				errMsg += "Credit number provided is invalid.";
				System.out.println("Invalid credit number");
				return;
			}
			if(!errMsg.equals("")){
				//Redirect user to the thank you page
				url = "/thankyou.jsp";}
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
				return;}

			if(!password.equals(confirmPassword)){
				errMsg += "Passwords do not match. ";
				return;
			}
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
			//TODO: If the user is an admin, redirect to the Admin View (inventory)
		}if(action.equals("add") || action.equals("remove")){

			//TODO: If user is not logged in, redirect to login page

			String quantityString = request.getParameter("quantity");
			String productID = request.getParameter("ID");
			double cost = Double.parseDouble(request.getParameter("cost"));

			System.out.println("Unit cost: " + cost);
			System.out.println("quantity: " + quantityString);
			System.out.println("product ID: " + productID);


			HttpSession session = request.getSession();
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
			if(action.equals("add")){
				cart.addItem(quantity, productID, cost);
				url = "/marketplace.jsp";
			}
			if(action.equals("remove")){
				cart.removeItem(quantity, productID, cost);
				url = "/shoppingCart.jsp";
			}
			session.setAttribute("cart", cart);

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
