<%-- 
    Document   : manageInventory
    Created on : Dec 5, 2016, 7:49:12 PM
    Author     : danielfenton
--%>

<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.sugarhouse.business.Product"%>
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
        
        .table { display: table; } 
        .tr { display: table-row;}
        .td {display: table-cell;}
        .th {display: table-header-group;}
        .table, .td{    
            border: 1px solid #ddd;
            text-align: left;
        }

        .table {
            border-collapse: collapse;
            width: 100%;
        }

        .td {
            padding: 15px;
        }
        
        button { 
          width:100px; 
        } 
        input[type="submit"] {
            margin-bottom: 10px;
        }
        </style>  
        <script type="text/javascript" src="assets/js/inputValidation.js">
	
        </script>
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
                                    <li class="active"><a href="manageInventory.jsp">Manage Inventory</a></li>
                                    <li><a href="databaseView.jsp">Database</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
<div class="container">
		<div class="row centered mt mb">


			<h2 align="left">Manage Inventory View</h2>

			<h3 align="left">Inventory</h3>
                        
                        <table>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Cost</th>
                                <th>Units</th>
                                <th>Notes</th>
                                <th><p>New</p><p>Arrival</p></th>
                                <th>Action</th>
                            </tr>
                            <tr> 
                                <td>#</td>
                                <td>
                                    <form id="addItem" action="inventoryController" method="post" onsubmit="return inventoryValidation(this)" >
                                        <input type="text" name="name" value="">
                                        <input type="hidden" name="action" value="insert">
                                    </form>
                                </td>
                                <td>
                                    <input form="addItem" type="text" name="cost" value="">
                                </td>
                                <td>
                                    <input form="addItem" type="text" name="quantity" value="">
                                    
                                </td>
                                <td>
                                    <textarea name="description" form="addItem"></textarea>
                                </td>
                                <td>
                                    <input type="checkbox" name="isNew" value="new" form="addItem">
                                </td>
                                <td>
                                    <input type="submit" form="addItem" value="Add Item" >
                                </td>
                                    
                            </tr>
                            <% NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
                            for(Product prod : dc.getProducts("id")) { %>
                            <tr>
                                <td>
                                    <form id="<%=prod.getId()%>" action="inventoryController" method="post" onsubmit="return inventoryValidation(this)">
                                    <%= prod.getId() %>
                                    <input type="hidden" name="id" value="<%= prod.getId() %>">
                                    <input type="hidden" name="action" value="update">
                                    </form>
                                    <form id="rm<%=prod.getId()%>" action="inventoryController" method="post" onsubmit="return confirmDelete()">
                                    <input type="hidden" name="id" value="<%= prod.getId() %>">
                                    <input type="hidden" name="action" value="remove">
                                    </form>
                                </td>
                                <td>
                                    <input form="<%=prod.getId()%>" type="text" name="name" value="<%= prod.getName() %>">
                                </td>

                                <td>
                                    <input form="<%=prod.getId()%>" type="text" name="cost" value="<%= formatter.format(prod.getCost())%>">
                                </td>
                                <td>
                                    <input form="<%=prod.getId()%>" type="text" name="quantity" value="<%= prod.getInventory() %>">
                                </td>
                                <td>
                                    <textarea name="description" form="<%=prod.getId()%>"><%= prod.getDescription()%></textarea>
                                </td>
                                
                                <td>
                                    <input type="checkbox" name="isNew" value="new" form="<%=prod.getId()%>" <%if(prod.getIsNew() == 1){%>checked="checked"<%}%>>
                                </td>
                                <td>
                                    <input type="submit" form="<%=prod.getId()%>" value="Update Item" >
                                    <br>
                                    <input type="submit" form="rm<%=prod.getId()%>" value="Delete Item" >
                                </td>

                            </tr>   
                            <%}%>
			</table><!-- table -->

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
