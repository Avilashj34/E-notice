<?php
include 'app/dbconfig.php';

if (isset($_SESSION['username'])) {
    $college_name = $_SESSION['username'];

    $get_college_query = "SELECT * FROM members WHERE username = '$college_name '";
   
    

    if ($result_query = mysqli_query($conn, $get_college_query)) {
        while ($row = mysqli_fetch_assoc($result_query)) {
            $college_id =   $row['id'];
            $college_name =$row['username'];
        }
        $get_messages_query = "SELECT * FROM feedbacks WHERE collegeid = '$college_id' ORDER BY id DESC ";
            if ($result_query = mysqli_query($conn, $get_messages_query)) {
                while ($row = mysqli_fetch_assoc($result_query)) {
                    $message =   $row['message'];
                    $uid = $row['uid'];
                }
		$get_userinfo = "SELECT * FROM students WHERE id = '$uid'";

                if ($result_query = mysqli_query($conn, $get_userinfo)) {
                    while ($row = mysqli_fetch_assoc($result_query)) {
                        $fname =   $row['fname'];
                        $lname = $row['lname'];
                        $enrollment = $row['enrollment'];
			$final_feedback = "<b>[".$fname." ".$lname." "."(".$enrollment.")"."]</b>: ".$message;
     
      			$update_message_query = "UPDATE feedbacks SET message = '$final_feedback ' WHERE uid = '$uid'";
     			
     			if (mysqli_query($conn, $update_message_query )) {
		          
		        } else {
		           
		        }

                    }
                }
            }
        
    }else{
       $message = "Something went wrong.. Error: ".mysqli_error($conn);
    }

        mysqli_close($conn);
}
