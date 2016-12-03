<%-- 
    Document   : shoppingCart
    Created on : Nov 7, 2016, 8:41:12 PM
    Author     : Fenton
--%>


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
	width: 100px;
}
</style>
</head>

<body>

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
					<!--             <li class="active"><a href="logIn.jsp">Login</a></li> -->
					<!--             <li><a href="shoppingCart.jsp">My Cart</a></li> -->
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
	<div class="container">
		<div class="row centered mt mb">
			<h2 align="left">Shopping Cart</h2>

			<%@ page import="com.sugarhouse.business.ShoppingCart"%>
			<%@ page import="java.util.*"%>
			<%@ page import="javax.servlet.*"%>
                        <%@ page import="java.text.NumberFormat"%>

			<%
				session = request.getSession();
                                ShoppingCart cart;
                                if(session.getAttribute("cart") == null)
                                {
                                    cart = new ShoppingCart();
                                     
                                }else
                                {
                                    cart = (ShoppingCart)session.getAttribute("cart");
                                }
				Double totalCost = cart.getTotalCost();
                                ArrayList<String> items = cart.getItems();

				if (items.isEmpty() || totalCost == 0) {
			%>
				<h3 align="left">You have not added anything to the cart. Please visit our
					marketplace!</h3>
				<form action="loginController" method="post">
					<div class="button-section" align="left">
						<input type="submit" value="Marketplace"> <input
							type="hidden" name="action" value="marketplace">
					</div>
				</form>

			<%
				} else {
                                NumberFormat formatter = NumberFormat.getCurrencyInstance();
			%>

			<table>
				<tr>
                                        <th>Name</th>
					<th>Item</th>
					<th>Quantity</th>
					<th>Cost</th>
					<th>Edit</th>
				</tr>
				<%
					for (int i = 0; i < items.size(); i++) {
							String item = items.get(i);
							String[] splitItem = item.split(",");
                                                        String itemName = splitItem[0];
							String itemQuantity = splitItem[1];
							String itemID = splitItem[2];
							Double singleItemCost = Double.parseDouble(splitItem[3]);
							Double multiItemCost = singleItemCost * Double.parseDouble(itemQuantity);
				%>

				<tr>
                                        <td><%=itemName %></td>
					<td><%=itemID%></td>
					<td><%=itemQuantity%></td>
                                        <td><%=formatter.format(multiItemCost)%></td>
					<td>
						<form action="loginController" method="post">
							<div class="button-section">
								<input type="submit" onclick="alert('Item removed from cart')" value="Remove from Cart"> 
                                                                <input type="hidden" name="action" value="remove"> 
                                                                <input type="hidden" name='name' value="<%=itemName%>">
                                                                <input type="hidden" name="ID" value="<%=itemID%>"> 
                                                                <input type="hidden" name="cost" value="<%=singleItemCost%>"> 
                                                                <input type="hidden" name="quantity" value="<%=itemQuantity%>">
							</div>
						</form>
					</td>
				</tr>
				<%
					}
				%>
				<tr>
                                        <td></td>
					<td>TOTAL COST</td>
					<td></td>
					<td><%=formatter.format(totalCost)%></td>
					<td></td>
				</tr>
			</table>

			<form action="loginController" method="POST" style="width: 400px">
				<p></p>
				<p></p>
				<p></p>
				<div class="button-section">
					<input type="submit" value="Checkout" style="width: 200px">
					<input type="hidden" name="action" value="checkout">
				</div>
				<p></p>
			</form>
			<%
				}
			%>
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
</body>