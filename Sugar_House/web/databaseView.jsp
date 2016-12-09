<%-- 
    Document   : databaseView
    Created on : Dec 5, 2016, 7:49:44 PM
    Author     : danielfenton
--%>

<%@page import="java.text.NumberFormat"%>
<%@page import="com.sugarhouse.database.DatabaseCreator"%>
<%@page import="com.sugarhouse.business.Shopper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
<meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" href="assets/ico/favicon.ico">

        <title>Sugar House</title>

        <!-- Bootstrap core CSS -->
        <link href="assets/css/bootstrap.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="assets/css/style.css" rel="stylesheet">
        <link href="assets/css/font-awesome.min.css" rel="stylesheet">
        <style>
        table, td, th {    
            border: 1px solid #ddd;
            text-align: left;
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            padding: 15px;
        }
        button { 
          width:100px; 
        } 
        </style> 
    </head>
    <body>
    <% 
        //Only allow access to page if user is logged in and has the right privliages
        if(session.getAttribute("User") == null || !((Shopper)session.getAttribute("User")).getName().equals("admin"))
        {
            String redirectURL = "/Sugar_House//index.jsp";
            response.sendRedirect(redirectURL);
        }else
        {
            if(session.getAttribute("databaseConnection") == null)
            {
               session.setAttribute("databaseConnection", new DatabaseCreator());
            }

            DatabaseCreator dc = (DatabaseCreator)session.getAttribute("databaseConnection");
      %>        
        <div class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
			</div>
			<div id="nav" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-left">
                                    <li><a href="index.jsp">Home</a></li>
                                    <li><a href="admin.jsp">Admin</a></li>
                                    <li><a href="manageInventory.jsp">Manage Inventory</a></li>
                                    <li class="active"><a href="databaseView.jsp">Database</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
        <div class="container">
		<div class="row centered mt mb">


			<h2 align="left">Database View</h2>
                        
                        <h3>User Database</h3>
                        
                        <table>
                            <tr>
                                <th>ID</th>
                                <th>Login</th>
                                <th>Password</th>
                                <th>Email</th>
                            </tr>
                            
                            <%
                            for(String usr : dc.getUsers()){
                                String[] userData = usr.split(",");
                            %>
                            <tr>
                                <td><%=userData[0]%></td>
                                <td><%=userData[1]%></td>
                                <td><%=userData[2]%></td>
                                <td><%=userData[3]%></td>
                            </tr>
                            
                            <%}%>
                            
                        </table>
                            
                            <br/>
                            <br/>
                            <h3>Items Database</h3>
                        
                        <table>
                            <tr>
                                <th>ID</th>
                                <th>User ID</th>
                                <th>Name</th>
                                <th>Prod ID</th>
                                <th>Units</th>
                                <th>Unit Cost</th>
                            </tr>
                            
                            <%
                            NumberFormat formatter = NumberFormat.getCurrencyInstance();
                            for(String item : dc.getItems()){
                                String[] itemData = item.split(",");
                            %>
                            <tr>
                                <td><%=itemData[0]%></td>
                                <td><%=itemData[1]%></td>
                                <td><%=itemData[2]%></td>
                                <td><%=itemData[3]%></td>
                                <td><%=itemData[4]%></td>
                                <td><%=formatter.format(Double.parseDouble(itemData[5]))%></td>
                            </tr>
                            
                            <%}%>
                            
                        </table>

			
		</div>
		<!--/row -->
	</div>
	<!--/container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="assets/js/bootstrap.min.js"></script>
    <%}%>
    </body>
</html>
