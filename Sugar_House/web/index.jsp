<%@page import="com.sugarhouse.business.Shopper"%>
<%@page import="com.sugarhouse.database.DatabaseCreator" %>
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
        if(session.getAttribute("databaseConnection") == null)
        {
           session.setAttribute("databaseConnection", new DatabaseCreator());
        }

        if(session.getAttribute("isAdmin") == null)
        {
           session.setAttribute("isAdmin", "no");
        }
          
        if(session.getAttribute("Login") == null)
        {
           session.setAttribute("Login", "false");
        }
        
        if(session.getAttribute("User") == null)
        {
           session.setAttribute("User", null);
        }
          
          DatabaseCreator dc = (DatabaseCreator)session.getAttribute("databaseConnection");
      %>

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


	<div id="headerwrap">
	    <div class="container">
			<div class="row">
				<div class="col-lg-10 col-lg-offset-3 welcome_banner">
					<h4>
                                            <% if(session.getAttribute("User") != null){%>
                                            <%=((Shopper)session.getAttribute("User")).getName() + "," %>
                                            <%}%>
                                            Welcome to
                                        </h4>
					<h1>SUGAR HOUSE</h1>
				</div>
			</div><!--/row -->
	    </div> <!-- /container -->
	</div><!--/headerwrap -->
	
	<section id="works"></section>
	<div class="container">
		<div class="row centered mt mb">
			
			<div class="col-lg-4 col-md-4 col-sm-4 gallery">
				<a href="marketplace.jsp"><img src="assets/img/portfolio/marketplace.png" class="img-responsive"></a>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-4 gallery">
				<a href="about.jsp"><img src="assets/img/portfolio/ourstory.png" class="img-responsive"></a>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-4 gallery">
				<a href="newArrivals.jsp"><img src="assets/img/portfolio/newarrivals.png" class="img-responsive"></a>
			</div>
			
	</div><!--/row -->
	</div><!--/container -->


	<div id="footerwrap">
		<div class="container">
			<div class="row centered">
				<div class="col-lg-4">
					<p><b></b></p>
				</div>
			
				<div class="col-lg-4">
					<p>Free Shipping on all orders!</p>
				</div>
				<div class="col-lg-4">
					<p></p>
				</div>
			</div>
		</div>
	</div><!--/footerwrap -->
	


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
  </body>
</html>
