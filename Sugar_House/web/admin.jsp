<%-- 
    Document   : admin
    Created on : Nov 25, 2016, 11:35:00 PM
    Author     : Snyder
--%>

<%@page import="java.util.Locale"%>
<%@page import="com.sugarhouse.business.Shopper"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.sugarhouse.business.Order"%>
<%@page import="com.sugarhouse.business.Product"%>
<%@page import="com.sugarhouse.database.DatabaseCreator"%>
<!DOCTYPE html>
<html lang="en">
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

<!-- Just for debugging purposes. Don't actually copy this line! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

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

	<!-- Static navbar -->
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
                                    <li class="active"><a href="admin.jsp">Admin</a></li>
                                    <li><a href="manageInventory.jsp">Manage Inventory</a></li>
                                    <li><a href="databaseView.jsp">Database</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
	<div class="container">
		<div class="row centered mt mb">


			<h2 align="left">Administrator View</h2>


			<h3 align="left">Order History</h3>
			<table>
				<tr>
					<th>User</th>
					<th>Order Date</th>
					<th>Order Status</th>
					<th>Order Cost</th>
				</tr>
                            <% NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
                                for(Order order : dc.getOrders("id")) { %>
                                <tr>
                                    <td><%= dc.getUsersLogin(order.getUID()) %></td>
                                    <td><%= order.getOrderDate() %></td>
                                    <td><%= order.getStatus() %></td>
                                    <td><%= formatter.format(order.getTotalCost()) %></td>
                                </tr>
                            <%}%>
			</table>
                        <br>
                        <form action="PrintToExcel" method="post">
                            <div class="button-section">
                                <input type="submit" value="Print Orders to Excel" >
                                <input type="hidden" name="ReportType" value="Orders">
                            </div>
                        </form>
			<h3 align="left">Product Availability</h3>
			<table>
				<tr>
					<th>Product ID</th>
					<th>Product Name</th>
					<th>Product Availability</th>
					<th>Amount in Stock</th>
				</tr>
                            <% for(Product prod : dc.getProducts("id")) { %>
                                <tr>
                                    <td><%= prod.getId() %></td>
                                    <td><%= prod.getName() %></td>
                                    <td><%= (prod.isAvailable()? "Yes" : "No") %></td>
                                    <td><%= prod.getInventory() %></td>
                                </tr>
                            <%}%>
			</table>
                        <br>
                        <form action="PrintToExcel" method="post">
                            <div class="button-section">
                                <input type="submit" value="Print Inventory to Excel" >
                                <input type="hidden" name="ReportType" value="Inventory">
                            </div>
                        </form>

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