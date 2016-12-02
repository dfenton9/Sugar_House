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

    <title>Sugar House About Us</title>

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
<style>
h1 {
	color:#ffffff;
	padding-top: 10px;
	padding-bottom: 20px;
	letter-spacing: 4px;
	font-size: 80px;
	font-weight: bold;
}
</style>
  <body>

    <!-- Static navbar -->
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
        </div>
		<div id="nav" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-left">
            <li class="active"><a href="index.jsp">Home</a></li>
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
        </div><!--/.nav-collapse -->
      </div>
    </div>



	<div id="aboutwrap">
	    <div class="container">
			<div class="row">
				<div class="col-lg-6 col-lg-offset-3">
					<h1>Our Story</h1>			
				</div>
			</div><!--/row -->
	    </div> <!-- /container -->
	</div><!--/aboutwrap -->
	
	<div class="container">
		<div class="row centered mt mb">
			<div class="col-lg-8 col-lg-offset-2">
				<p>Sugar House is a family owned company founded in 1920.</p>
                                <p>Our product reaches back several generations and over the years the skill has been passed down to younger generations through long nights spent in Sugar House. Over the years the techniques have changed some but the end results is a purely sweet and delicious product.</p>
				<p>The current farm is over 200 years old and operates on about 1000 taps. Some of the taps are still buckets, but to help with the efficency of collecting the sap more sap lines are being installed each year.</p>
                                <p>We hope you enjoy our product and continue to spread its cheer by using it as gifts. If you are unsatisfied, please do not hesitate to contact us at <a href="mailto:mapleMaker@sugarhouse.com">mapleMaker@sugarhouse.com</a></p>
			</div>

		</div><!--/row -->
	</div><!--/container -->
	


<!-- 	<div id="footerwrap"> -->
<!-- 		<div class="container"> -->
<!-- 			<div class="row centered"> -->
<!-- 				<div class="col-lg-4"> -->
<!-- 					<p><b></b></p> -->
<!-- 				</div> -->
			
<!-- 				<div class="col-lg-4"> -->
<!-- 					<p>Free Shipping on all orders!</p> -->
<!-- 				</div> -->
<!-- 				<div class="col-lg-4"> -->
<!-- 					<p></p> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div>/footerwrap -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
  </body>
</html>