<?php
session_start();
if (isset($_SESSION['username'])) {
    header("location:../index.php");
}
?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="../css/bootstrap.css" rel="stylesheet" media="screen">
    <link href="../css/main.css" rel="stylesheet" media="screen">
    <link type="text/css" rel="stylesheet" href="../css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="../css/form.css" media="screen,projection"/>
    
    <style>
	
     </style>
  </head>

  <body>
  <div class="bg"></div>
  
    <div class="container">
    	<div class="form-title blue center-align white-text"><h5><b>Welcome To E-Notice</b></h5></div>
    	
                <div class="custom-content">
                    <form class="col s12 " id="notification" name="form1" method="post" onsubmit="event.preventDefault(); " action="checklogin.php">
                        <div class="row">
                            <div class="input-field ">
                                <label for="myusername">Username</label>
                                <input id="myusername" type="text" class="validate" name="myusername" required>
                            </div>
                            <div class="input-field ">
                                <label for="mypassword">Password</label>
                                <input id="mypassword" type="password" class="validate" name="mypassword" required>
                            </div>
                            <div class="col-lg-6 col-lg-offset-3">
                            	<button  name="Submit" id="submit" class="btn btn-primary btn-block" type="submit">Sign in</button>
                        	<br>
	    			<a  href="signup.php" id="signup" class="btn btn-primary btn-block">Create new account</a>
	    			
	    			</div>
                        </div>
                        
                        

        		<div id="message"></div>
                    </form>
               </div>
    	
    	
	<!--<form class="form-signin" name="form1" method="post" action="checklogin.php">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input name="myusername" id="myusername" type="text" class="form-control" placeholder="Username" autofocus>
        <input name="mypassword" id="mypassword" type="password" class="form-control" placeholder="Password">
        
        <button name="Submit" id="submit" class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
	    <a href="signup.php" name="Sign Up" id="signup" class="btn btn-lg btn-primary btn-block" type="submit">Create new account</a>

        <div id="message"></div>
      	</form>-->
      

    </div> <!-- /container -->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery-2.2.4.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <!-- The AJAX login script -->
    <script src="js/login.js"></script>
    
        <!--Import jQuery before materialize.js-->
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="../js/materialize.min.js"></script>

  </body>
</html>
