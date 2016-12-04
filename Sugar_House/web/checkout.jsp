<%-- 
    Document   : checkout
    Created on : Nov 26, 2016, 3:20:12 AM
    Author     : Snyder
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

.ui-datepicker-calendar {
	display: none;
}
​
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
			<h2 align="left">Payment Information</h2>

			<%@ page import="com.sugarhouse.business.ShoppingCart"%>
			<%@ page import="java.util.*"%>
			<%@ page import="javax.servlet.*"%>

			<%
				session = request.getSession();
				ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
				ArrayList<String> items = cart.getItems();
				Double totalCost = cart.getTotalCost();
			%>
                        <% if(session.getAttribute("ErrorMsg") != null && !session.getAttribute("ErrorMsg").equals("")){%>
                            <div style="color:red;"><%=session.getAttribute("ErrorMsg")%></div>
                        <%} session.setAttribute("ErrorMsg","");%>
			<form action="creditCardController" method="post">
				<div class="section"></div>
				<div class="inner-wrap" align="left">
					<!-- Main text for the confirmation page -->
					<p>Your total is: $${cart.totalCost}0. Please fill in the
						payment information below to checkout.</p>
					<div class="section"></div>
					<div class="inner-wrap" align="left">
						<label>Credit Card Type</label>
						<div class="radioboxes">
							<input type="radio" name="cardType" checked value="VISA">VISA<br>
							<input type="radio" name="cardType" value="Mastercard">Mastercard<br>
							<input type="radio" name="cardType" value="Discover">Discover
						</div>
						<label>Credit Card Number <input type="text"
							name="creditNumber" value="" maxlength="10" id="creditNumber" /></label>

						<br> <label>Credit Card Expiration Date (MM/yyyy) <script
								type="text/javascript" src="jquery-1.12.4.js"></script> <script
								type="text/javascript" src="jquery-ui.js"></script> <script
								type="text/javascript" src="calendar.js"></script> <input
							name="date" type="text" id='txtDate' onkeydown="return false"
							value="12/2020" maxlength="7">

						</label>
					</div>
				</div>

				<div class="button-section" align="left">
					<input type="submit" value="Confirm Order" style="width: 250px"
						onclick="isNumeric()"> <input type="hidden" name="action"
						value="confirm">
				</div>
			</form>
		</div>
	</div>
</body>
</html>