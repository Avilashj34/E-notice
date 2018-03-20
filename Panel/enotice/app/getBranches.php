<?php
include 'dbconfig.php';
header('Content-type:application/json');

$get_meme_query = "SELECT * FROM categories   ORDER BY ID DESC";
    $result_query = mysqli_query($conn,$get_meme_query);
        while ($row = mysqli_fetch_assoc($result_query)){
            $result[] = array(
                "id" =>  utf8_encode( $row['id']),
                "category" => utf8_encode($row['category']),
            );
        }
        echo json_encode($result);
        mysqli_close($conn);

?>