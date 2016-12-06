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

</head>

<body>
    <% 
        //Only allow access to page if user is logged in and has the right privliages
        if(session.getAttribute("User") == null)
        {
            String redirectURL = "/Sugar_House//index.jsp";
            response.sendRedirect(redirectURL);
        }else
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
			<h2 align="left">Thank you</h2>


			<div class="section"></div>
			<div class="inner-wrap" align="left">
				<!-- Main text for the confirmation page -->
				<p>Thank you for shopping with Sugar House. An email
					confirmation will be sent to the provided email. If you have any
					concerns about your order please reach out to us at
					customerservice@sugarhouse.com</p>

			</div>
		</div>
	</div>
    <%}%>
</body>
</html>