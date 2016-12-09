<%-- 
    Document   : checkout
    Created on : Nov 26, 2016, 3:20:12 AM
    Author     : Snyder
--%>

<%@page import="java.text.NumberFormat"%>
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

button {
	width: 100px;
}

.ui-datepicker-calendar {
	display: none;
}€‹
</style>
<script>
    function toggleBillingDiv()
    {
        $("#billingInformation").toggle();
    }
</script>
<script type="text/javascript" src="inputValidation.js">
	
</script>
        
</head>

<body>
    <% 
        //Only allow access to page if user is logged in and has the right privliages
        if(session.getAttribute("User") == null)
        {
            String redirectURL = "/Sugar_House/index.jsp";
            response.sendRedirect(redirectURL);
        }
        else
        {
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
                                NumberFormat formatter = NumberFormat.getCurrencyInstance();
			%>
                        <% if(session.getAttribute("ErrorMsg") != null && !session.getAttribute("ErrorMsg").equals("")){%>
                            <div style="color:red;"><%=session.getAttribute("ErrorMsg")%></div>
                        <%} session.setAttribute("ErrorMsg","");%>
                        <form action="creditCardController" method="get" onsubmit="return validateConfirmationData(this)">
				<div class="section"></div>
				<div class="inner-wrap" align="left">
					<!-- Main text for the confirmation page -->
                                        <p>Your total is: <%=formatter.format(cart.getTotalCost())%>. Please fill in the
						payment information below to checkout.</p>
					<div class="section"></div>
					<div class="inner-wrap" align="left">
						<label>Credit Card Type</label>
						<div class="radioboxes">
                                                    <input type="radio" name="cardType" checked value="VISA"><span class="cbSpan">VISA</span><br>
							<input type="radio" name="cardType" value="Mastercard"><span class="cbSpan">Mastercard</span><br>
							<input type="radio" name="cardType" value="Discover"><span class="cbSpan">Discover</span>
						</div>
						<label>*Credit Card Number <input type="text"
							name="creditNumber" value="" maxlength="14" id="creditNumber" /></label>

						<br> <label>*Credit Card Expiration Date (MM/yyyy) <script
								type="text/javascript" src="jquery-1.12.4.js"></script> <script
								type="text/javascript" src="jquery-ui.js"></script> <script
								type="text/javascript" src="calendar.js"></script> <input
							name="date" type="text" id='txtDate' onkeydown="return false"
							value="12/2020" maxlength="7">

						</label>
					</div>
				</div>
                                <div class="inner-wrap" align="left" >
					<!-- Main text for the confirmation page -->
					<div class="section"></div>
					<div class="inner-wrap" align="left">
                                            
						<h2>Shipping & Billing Information</h2>
                                                <div id ="shippingInformation">
                                                        
                                                    <input type="checkbox" name="shipSameAsBill" value="same" onchange="toggleBillingDiv()"><span class="cbSpan">Shipping and Billing Addresses are the same.</span><br>

                                                    <fieldset>
                                                        <legend>Shipping Address</legend>
                                                        <table>
                                                            <tr>
                                                                <td><b>*Name:</b></td>
                                                                <td><input type="text" name="name" value="" maxlength="30" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td><b>*Address:</b></td>
                                                                <td><input type="text" name="address1" value="" maxlength="60" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td>Address:</td>
                                                                <td><input type="text" name="address2" value="" maxlength="60" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td><b>*City:</b></td>
                                                                <td><input type="text" name="city" value="" maxlength="30" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td><b>*State:</b></td>
                                                                <td><input type="text" name="state" value="" maxlength="25" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td><b>*Zip:</b></td>
                                                                <td><input type="text" name="zip" value="" maxlength="5" /></td>
                                                            </tr>
                                                        </table>
                                                    </fieldset>
                                                </div>
                                            <div id ="billingInformation" >
                                                <br>
                                                <fieldset>
                                                        <legend>Billing Address</legend>
                                                        <table>
                                                            <tr>
                                                                <td><b>*Name:</b></td>
                                                                <td><input type="text" name="billName" value="" maxlength="30" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td><b>*Address:</b></td>
                                                                <td><input type="text" name="billAddress1" value="" maxlength="60" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td>Address:</td>
                                                                <td><input type="text" name="billAddress2" value="" maxlength="60" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td><b>*City:</b></td>
                                                                <td><input type="text" name="billCity" value="" maxlength="30" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td><b>*State:</b></td>
                                                                <td><input type="text" name="billState" value="" maxlength="25" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td><b>*Zip:</b></td>
                                                                <td><input type="text" name="billZip" value="" maxlength="5" /></td>
                                                            </tr>
                                                        </table>
                                                    </fieldset>
                                            </div>
					</div>
                                        <br>
                                        <br>
				</div>

				<div class="button-section" align="left">
					<input type="submit" value="Confirm Order" style="width: 250px"> 
                                        <input type="hidden" name="action" value="confirm">
				</div>
			</form>
		</div>
	</div>
    <%}%>
</body>
</html>