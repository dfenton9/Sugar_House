<%-- 
    Document   : logIn
    Created on : Nov 7, 2016, 8:29:05 PM
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
<script type="text/javascript" src="registrationInputValidation.js">
	
</script>
<style>
th, td {
	padding: 15px;
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
			<form action="loginController" method="POST"
				onsubmit="return inputValidation()">
				<h2 align="left">New User Registration</h2>

				<table>
					<tr>
						<td>Login ID:</td>
						<td><input type="text" name="loginId" id="loginId"></td>
					</tr>
					<tr>
						<td>Email:</td>
						<td><input type="email" name="email" id="email"></td>
					</tr>
					<tr>
						<td>Confirm Email:</td>
						<td><input type="email" name="confirmEmail" id="confirmEmail"></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type="password" name="password" id="password"></td>
					</tr>
					<tr>
						<td>Confirm Password:</td>
						<td><input type="password" name="confirmPassword"
							id="confirmPassword"></td>
					</tr>
					<tr>
						<td></td>
						<td><div class="button-section">
								<input type="submit" value="Register" style="width: 200px">
								<input type="hidden" name="action" value="register">
							</div></td>
					</tr>
				</table>
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
</body>
</html>

