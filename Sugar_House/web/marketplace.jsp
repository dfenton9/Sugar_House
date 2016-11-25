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
</head>
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
					<li class="active"><a href="index.jsp">Home</a></li>
					<li><a href="logIn.jsp">Login</a></li>
					<li><a href="shoppingCart.jsp">My Cart</a></li>
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

				<table>
					<tr>
						<td><img class="img-responsive" src="assets/img/spoon.jpg"
							width="600"></td>
						<td align="left">
							<p>Cost: $50.00</p>
							<p>Item Number: 0001</p>
							<p>Description: 100% grade A organic maple syrup (36 oz)</p>
						</td>
						<td> <button type="button" onclick="alert('Item added to cart')">Add to cart</button> </td>
					</tr>
					<tr>
						<td><img class="img-responsive"
							src="assets/img/maple_bbq.jpg" width="600"></td>
						<td align="left">
							<p>Cost: $30.00</p>
							<p>Item Number: 0002</p>
							<p>Description: Maple infused BBQ sauce (24 oz)</p>
						</td>
						<td> <button type="button" onclick="alert('Item added to cart')">Add to cart</button> </td>
					</tr>
					<tr>
						<td><img class="img-responsive" src="assets/img/sampler.png"
							width="600"></td>
						<td align="left">
							<p>Cost: $50.00</p>
							<p>Item Number: 0003</p>
							<p>Description: Golden, amber, and dark maple syrup sampler
								(12 oz each)</p>
						</td>
						<td> <button type="button" onclick="alert('Item added to cart')">Add to cart</button> </td>
					</tr>
					<tr>
						<td><img class="img-responsive"
							src="assets/img/maple_sugar.jpg" width="600"></td>
						<td align="left">
							<p>Cost: $25.00</p>
							<p>Item Number: 0004</p>
							<p>Description: Pure, granulated, maple sugar (1 lb)</p>
						</td>
						<td> <button type="button" onclick="alert('Item added to cart')">Add to cart</button> </td>
					</tr>

					<tr>
						<td><img class="img-responsive"
							src="assets/img/maple_spread.jpg" width="600"></td>
						<td align="left">
							<p>Cost: $25.00</p>
							<p>Item Number: 0005</p>
							<p>Description: Pure maple syrup spread goes perfectly with
								morning toast or other baked goods (24 oz)</p>
						</td>
						<td> <button type="button" onclick="alert('Item added to cart')">Add to cart</button> </td>
					</tr>

					<tr>
						<td><img class="img-responsive"
							src="assets/img/new_candy.jpg" width="600"></td>
						<td align="left">
							<p>Cost: $10.00</p>
							<p>Item Number: 0006</p>
							<p>Description: Back again this year by popular demand are
								our premium maple candies</p>
						</td>
						<td> <button type="button" onclick="alert('Item added to cart')">Add to cart</button> </td>
					</tr>

					<tr>
						<td><img class="img-responsive"
							src="assets/img/holiday_present.jpg" width="600"></td>
						<td align="left">
							<p>Cost: $100.00</p>
							<p>Item Number: 0007</p>
							<p>Description: Our holiday package comes with our signature
								maple syrup, 3 boxes of maple candies, and a gold plated maple
								leaf tree ornament all beautifully wrapped and ready to deliver
								to friends or family.</p>
						</td>
						<td> <button type="button" onclick="alert('Item added to cart')">Add to cart</button> </td>
					</tr>
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