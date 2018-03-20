<?php
include 'dbconfig.php';
header('Content-type:application/json');

$get_meme_query = "SELECT * FROM members   ORDER BY ID DESC";
    $result_query = mysqli_query($conn,$get_meme_query);
        while ($row = mysqli_fetch_assoc($result_query)){
            $result[] = array(
                "id" =>  utf8_encode( $row['id']),
                "college" => utf8_encode($row['username']),
            );
        }
        echo json_encode($result);
        mysqli_close($conn);

?>