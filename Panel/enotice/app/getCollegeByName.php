<?php
include 'dbconfig.php';
header('Content-type:application/json');

if(isset($_POST['uid'])){

$uid = $_POST['uid'];


$get_college_id_query = "SELECT * FROM students WHERE id = '$uid'";

    $college_id_result= mysqli_query($conn,$get_college_id_query );
    while ($row = mysqli_fetch_assoc($college_id_result)){
            $college_name = $row['college'];
            $branch = $row['branch'];
            $enroll = $row['enrollment'];
            $fname = $row['fname'];
            $lname = $row['lname'];
        }

$get_college_query = "SELECT * FROM members WHERE username = '$college_name '";

    $result_query = mysqli_query($conn,$get_college_query );
        while ($row = mysqli_fetch_assoc($result_query)){
            $result[] = array(
                "id" =>  utf8_encode( $row['id']),
                "college" => utf8_encode($row['username']),
                "branch" => $branch,
                "enroll" => $enroll,
                "fname" => $fname,
                "lname" => $lname
            );
        }
        echo json_encode($result);
        mysqli_close($conn);
        
        
}
?>