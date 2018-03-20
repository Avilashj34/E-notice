<?php
include 'dbconfig.php';
header('Content-type:application/json');

if (isset($_POST['uid'])) {
    $uid = $_POST['uid'];
    $college_id = $_POST['college_id'];
    $message = $_POST['message'];
    
    $message = mysqli_real_escape_string($conn,$message);

    $query = "INSERT INTO feedbacks 
				(uid,collegeid,message)
				VALUES
                ('$uid','$college_id', '$message')";
    
    $spam_check_query = "SELECT * FROM feedbacks WHERE message = '$message'  ";
    $spam_query_result = mysqli_query($conn, $spam_check_query);

    if (mysqli_num_rows($spam_query_result)>0) {
        $success = false;
		$message = "feedback already sended..";
    } else {
        if (mysqli_query($conn, $query)) {
            $success = true;
            $message = "Feedback Sent";
        } else {
            $success = false;
            $message = "Something went wrong.. ".mysqli_error($conn);
        }
    }
    $output = array(
        "success" => $success,
        "message" => $message
        
    );

    echo json_encode($output);
        mysqli_close($conn);
}
