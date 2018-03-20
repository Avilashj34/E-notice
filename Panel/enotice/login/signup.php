<?php
  session_start();

  if (isset($_SESSION['username'])) {
      session_start();
      session_destroy();
  }


?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Signup</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="../css/bootstrap.css" rel="stylesheet" media="screen">
    <link href="../css/main.css" rel="stylesheet" media="screen">
    <link type="text/css" rel="stylesheet" href="../css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="../css/form.css" media="screen,projection"/>
  </head>

  <body>
  <div class="bg"></div>
    <div class="container">
    
    <div class="form-title blue center-align white-text"><h5><b>Welcome To E-Notice</b></h5></div>
    	
                <div class="custom-content">
                    <form class="col s12 " id="usersignup" name="usersignup" method="post" onsubmit="event.preventDefault(); " action="createuser.php">
                        <div class="row">
                            <div class="input-field ">
                                <label for="newuser">College Name</label>
                                <input id="newuser" type="text" class="validate" name="newuser" required>
                            </div>
                            <div class="input-field ">
                                <label for="email">Email</label>
                                <input id="email" type="text" class="validate" name="email" required>
                            </div>
                            <div class="input-field ">
                                <label for="password1">Password</label>
                                <input id="password1" type="password" class="validate" name="password1" required>
                            </div>
                            <div class="input-field ">
                                <label for="password2">Repeat Password</label>
                                <input id="password2" type="password" class="validate" name="password2" required>
                            </div>
                            <br>
                            <div class="col-lg-6 col-lg-offset-3" > 
                            <button name="Submit" id="submit" class="btn btn-primary btn-block" type="submit">Sign up</button>
				</div>
                        </div>
        		<div id="message"></div>
                    </form>
               </div>
    
    

      <!--<form class="form-signup" id="usersignup" name="usersignup" method="post" action="createuser.php">
        <h2 class="form-signup-heading">Register</h2>
        <input name="newuser" id="newuser" type="text" class="form-control" placeholder="Username" autofocus>
        <input name="email" id="email" type="text" class="form-control" placeholder="Email">
<br>
        <input name="password1" id="password1" type="password" class="form-control" placeholder="Password">
        <input name="password2" id="password2" type="password" class="form-control" placeholder="Repeat Password">

        <button name="Submit" id="submit" class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>

        <div id="message"></div>
      </form>-->

    </div> <!-- /container -->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="//code.jquery.com/jquery.js"></script>
    
     <!--Import jQuery before materialize.js-->
<script type="text/javascript" src="../js/materialize.min.js"></script>

    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script type="text/javascript" src="js/bootstrap.js"></script>

    <script src="js/signup.js"></script>


    <!--<script src="http://jqueryvalidation.org/files/dist/jquery.validate.min.js"></script>-->
<script src="http://jqueryvalidation.org/files/dist/additional-methods.min.js"></script>
<script>

$( "#usersignup" ).validate({
  rules: {
	email: {
		email: true,
		required: true
	},
    password1: {
      required: true,
      minlength: 4
	},
    password2: {
      equalTo: "#password1"
    }
  }
});
</script>

  </body>
</html>
