<?php
include 'dbconfig.php';

if (isset($_POST['email'])) {
    
change_pass($conn);
  
}

function change_pass($conn){
	$email = $_POST['email'];
	$password = $_POST['password'];
   	$new_password = $_POST['newpassword'];

	$query = "SELECT * FROM students WHERE email= '$email' AND password ='$password'";

	$update = "UPDATE students SET password = '$new_password' WHERE email= '$email'";

	$result = mysqli_query($conn,$query);

	$id = null;
	if(mysqli_num_rows($result)>0){
	
        	if(mysqli_query($conn,$update)){
		        $success = true;
			$message = "Password Changed!";
        	}else{
		        $success = false;
			$message = "Something went wrong!, Please try Again.";
        	}
	}else{
		$success = false;
		$message = "Please Enter valid details";
		$id = null;
	}
	
	$output = array(
		"success" => $success,
		"message" => $message,
		"id" => $id,
	);
	echo json_encode($output);
	    mysqli_close($conn);

}
?>