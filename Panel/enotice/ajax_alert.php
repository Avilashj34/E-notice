<?php
 include 'app/dbconfig.php';
 header('Content-type:application/json');
/**
 * Created by PhpStorm.
 * User: Amrish
 * Date: 22-01-2017
 * Time: 08:12 PM
 */
if (isset($_POST['title'])) {
    $title = $_POST['title'];
     $message = $_POST['message'];
   $topic = $_POST['college'];
    $branch = $_POST['branch'];
    

}
//echo $title;
     $find_college_query = "SELECT id FROM members where  username = '$topic' LIMIT 1 ";

        $college_result = mysqli_query($conn, $find_college_query );
	while ($row = mysqli_fetch_assoc($college_result )) {
   	$college_id =  $row['id'];
}
       
        mysqli_close($conn);



sendNotif($topic, $title, $message, $college_id,$branch);

function sendNotif($topic, $title, $message, $college_id,$branch)
{
//echo '/topics/'.$college_id."_".$branch;
//$sub= str_replace(' ','','/topics/'.$college_id."_".$branch);
$sub=str_replace(array(' ', '&', '.'), '', '/topics/'.$college_id."_".$branch);

	
    $path_to_firebase_cm = 'https://fcm.googleapis.com/fcm/send';

    	$data= array(
        'title' => $title,
        'body' => $message,
        'sound' => "enabled"

    );
    
     /* $data= array(
        'title' => $title,
        'body' => $message,
        'sound' => "enabled"

    );*/

    $fields = array(
    	//'to' => '/topics/131340943959786a49769b1_Civil',
        'to' => $sub,
        'data' => $data
    );
    
    $headers = array(
        'Authorization:key=AAAAbAsj69E:APA91bFbagqkG5UQUy0Hmf5z0jgzv46JQZZaMoIasXUKsW_Z_hftCN0BO_Yr7flqFpJjk5QeLm10u6jSC_k1mDnoTCqKMc5pw7OuHyQjUSLTDz6DhjPO_PjSldE9pgvV2r9x1rXt7D6r',
        'Content-Type:application/json'
    );
    

    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $path_to_firebase_cm);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_IPRESOLVE, CURL_IPRESOLVE_V4 );
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));

		
    $result = curl_exec($ch);

    curl_close($ch);
    echo $result;
}

