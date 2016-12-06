<%@page import="com.sugarhouse.business.Product"%>
<%@page import="com.sugarhouse.database.DatabaseCreator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="assets/ico/favicon.ico">

<title>Sugar House Market</title>

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
<script type="text/javascript" src="inputValidation.js">
	
</script>
</head>



<body>
      <% 
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
                                        <% if(session.getAttribute("Login").equals("true")){ %>
                                            <li><a href="<%=request.getContextPath()%>/loginController?action=logout">Logout</a></li>
                                        <%}else{%>
                                            <li><a href="logIn.jsp">Login</a></li>
                                        <%}%>
                                        <li><a href="shoppingCart.jsp">My Cart</a></li>
                                        <% if(session.getAttribute("isAdmin").equals("yes")){ %>
                                            <li><a href="admin.jsp">Admin</a></li>
                                        <%}%>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>



	<div id="workwrap">
		<div class="container">
			<div class="row">
				<div class="col-lg-6 col-lg-offset-3">
					<h1>Marketplace</h1>
				</div>
			</div><!--/row -->
		</div><!-- /container -->
	</div><!--/workwrap -->
        
	<section id="works"></section>
	<div class="container">
		<div class="row centered mt mb">
			<div class="col-lg-8 col-lg-offset-2">
				<h4>Welcome!</h4>
				<p>Sugar House is pleased to provide a wide selection of high quality maple products.</p>
			</div>
			<div class="col-lg-10 col-lg-offset-1 mt">
                            <% if(session.getAttribute("ErrorMsg") != null && !session.getAttribute("ErrorMsg").equals("")){%>
                                <div style="color:red;"><%=session.getAttribute("ErrorMsg")%></div>
                            <%} session.setAttribute("ErrorMsg","");%>
				<table>
                                    <% int index = 0;
                                        for(Product prod : dc.getProducts("name")) { %>
					<tr>
						<td><img class="img-responsive" src="<%=prod.getImageSrc()%>"
							width="600"></td>
						<td align="left">
                                                        <p><%= prod.getName() %></p>
							<p>Cost: $<%=prod.getCost()%></p>
							<p>Description: <%=prod.getDescription() %> </p>
                                                        <% if(prod.getInventory() < 10 && prod.getInventory() > 0 ) { %>
                                                        <p style="color:green;">Only <%= prod.getInventory() %> left in stock!</p>
                                                        <% }else if(prod.getInventory() < 1){ %>
                                                        <p style="color:red;">This item is out of stock!</p>
                                                        <% } %>
						</td>
                                                <td> <form name="prod<%=prod.getId()%>" action="shoppingCartController" method="get" onsubmit="return quantityValidation(this)">
						Quantity: <p></p> <input type="text" name="quantity" maxlength="3" size="9">
						<p></p>
						<div class="button-section">
                                                    <input type="submit" value="Add to Cart" <%if(!prod.isAvailable()){%> disabled<%}%> >
						<input type="hidden" name="action" value="add">
                                                <input type="hidden" name="name" value="<%=prod.getName()%>">
						<input type="hidden" name="ID" value="<%=prod.getId()%>">
						<input type="hidden" name="cost" value="<%=prod.getCost()%>">
						</div></form></td>					
					</tr>
                                    <%}%>
				</table>
			</div>

		</div>
		<!--/row -->
	</div>
	<!--/container -->


	<div id="footerwrap">
		<div class="container">
			<div class="row centered">
				<div class="col-lg-4">
					<p>
						<b></b>
					</p>
				</div>

				<div class="col-lg-4">
					<p>Free Shipping on all orders!</p>
				</div>
				<div class="col-lg-4">
					<p></p>
				</div>
			</div>
		</div>
	</div>
	<!--/footerwrap -->



	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="assets/js/bootstrap.min.js"></script>
</body>
</html>