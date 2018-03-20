<?php
include 'dbconfig.php';
header('Content-type:application/json');
if(isset($_POST['case'])){
	$case = $_POST['case'];
	switch($case){
		case "login":
			doLogin($conn);
			break;
		case "signup":
			doSignup($conn);
			break;
		case "info":
			getCollege($conn);	
			break;
	}
}

function doLogin($conn){
	$email = $_POST['email'];
	$password = $_POST['password'];
	$query = "SELECT * FROM students WHERE BINARY email= '$email' AND password ='$password'";
	
	$result = mysqli_query($conn,$query);
	$id = null;
	if(mysqli_num_rows($result)==1){
		$success = true;
		$message = "Login successful";
		
		while($row = mysqli_fetch_assoc($result) ){
			$id = $row['id'];
		}
	}else{
		$success = false;
		$message = "Check login details";
		$id = null;
	}
	$output = array(
		"success" => $success,
		"message" => $message,
		"id" => $id,
	);
	echo json_encode($output);
}


function doSignup($conn){
	$fname = $_POST['fname'];
	$lname = $_POST['lname'];
	$email = $_POST['email'];
	$enrollment = $_POST['enrollment'];
	$college = $_POST['college'];
	$branch = $_POST['branch'];
	$password = $_POST['password'];
	
	
	
	$query = "INSERT INTO students 
				(email,fname,lname,enrollment,password,college,branch)
				VALUES
                ('$email','$fname', '$lname', '$enrollment', '$password', '$college','$branch')";
	$select_query = "SELECT * FROM users WHERE email= '$email' ";
	$select_result = mysqli_query($conn,$select_query);

	if(mysqli_num_rows($select_result )>0){
		$success = false;
		$message = "User already exists...";
		
		
	}else{
		if(mysqli_query($conn,$query)){
			$success = true;
			$message = "Signup successful";
		}else{
			$success = false;
			$message = "Something went wrong.. Error: ".mysqli_error($conn);
		}
	}
	
	
	$output = array(
		"success" => $success,
		"message" => $message
		
	);
	echo json_encode($output);
}

function getCollege($conn){
	$email = $_POST['email'];
	$password = $_POST['password'];
	$userid= $_POST['uid'];
	
	$query = "SELECT * FROM students WHERE BINARY email= '$email' AND password ='$password' AND id = '$uid'";
	
	$result = mysqli_query($conn,$query);
	$id = null;
	if(mysqli_num_rows($result)==1){
		$success = true;
		$message = "Login successful";
		
		while($row = mysqli_fetch_assoc($result) ){
			$college = $row['college'];
			$branch = $row['branch'];
		}
	}else{
		$success = false;
		$message = "Check login details";
		$id = null;
	}
	$output = array(
		"success" => $success,
		"message" => $message,
		"college" => $college,
		"branch" => $branch 
	);
	echo json_encode($output);
}
?>