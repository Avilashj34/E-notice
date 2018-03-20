<?php
$servername = "localhost";
$username = "whyphhwe_dev";
$password = "jaihanuman456";
$dbname = "whyphhwe_enoticeboard";

$conn = mysqli_connect($servername, $username, $password, $dbname);

if (!$conn) {
  # code...
  die("Error in Connection...".mysqli_connect_error());
}
?>