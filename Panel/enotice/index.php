<?php require "login/loginheader.php";
      include 'app/dbconfig.php';
 ?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="css/bootstrap.css" rel="stylesheet" media="screen">
    <!--<link href="css/main.css" rel="stylesheet" media="screen">-->

    <title>Send Alerts</title>

    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css" media="screen,projection" />
    <link type="text/css" rel="stylesheet" href="css/form.css" media="screen,projection" />
    <link type="text/css" rel="stylesheet" href="css/toast.css" media="screen,projection" />

    <style>
        .holder {
            background-color: #fff;
            width: 100%;
            height: 370px;
            overflow: hidden;
            padding: 10px;
            font-family: Helvetica;
             overflow-y:scroll;
        }

        .holder .mask {
            position: relative;
            left: 0px;
            top: 10px;
            width: 100%;
            height: 370px;
            overflow: hidden;
        }

        .holder ul {
            list-style: none;
            margin: 0;
            padding: 0;
            position: relative;
        }

        .holder ul li {
            padding: 10px 0px;
        }

        .holder ul li a {
            color: darkred;
            text-decoration: none;
        }
    </style>


    <!--Let browser know website is optimized for mobile-->
    <script type="text/javascript">
        function Toast(success, msg) {
            if (success) {
                $('#success').fadeIn(400).delay(3000).fadeOut(400); //fade out after 3 seconds
                document.getElementById("success").innerHTML = msg;
            } else {
                $('#error').fadeIn(400).delay(3000).fadeOut(400); //fade out after 3 seconds
                document.getElementById("error").innerHTML = msg;
            }
        }
        function sendData() {
            var college = "<?php  echo $_SESSION['username'];?>";


            var title = document.getElementById("notification").elements["title"].value;
            var message = document.getElementById("notification").elements["message"].value;
            var e = document.getElementById("category");
            var branch = e.options[e.selectedIndex].text;

            document.getElementById('myLoader').style.display = "block";
            console.log("Clicked");
            $.ajax({
                url: "ajax_alert.php",
                type: "POST",
                data: { "title": title, "message": message, "college": college, "branch": branch },
                dataType: 'json',
                cache: false,
                success: function (data) {

                    var success = data.success;



                    Toast(true, 'Alerts sent');

                    document.getElementById("notification").elements["title"].value = "";
                    document.getElementById("notification").elements["message"].value = "";
                    // document.getElementById("addstatus").elements["category"].value = "";
                    //                    document.getElementById("error_msg").innerHTML = "";
                    document.getElementById('myLoader').style.display = "none";
                },
                error: function (request, status, error) {
                    console.log(error);

                }
            });
        }
        function mySubmitFunction(evt) {
            evt.preventDefault();
            return false;
        }
    </script>
</head>

<body>
    <div class="bg"></div>
    <div class="container">
        <div class="row">
            <div class="col-lg-6">
                <div class="custom-container">
                    <div class="form-title blue center-align white-text">
                        <h5><b>Send Alerts</b></h5>
                    </div>
                    <div class="custom-content" style="min-height:370px">
                        <form class="col s12 " id="notification" onsubmit="event.preventDefault(); ">
                            <div class="row">
                                <div class="input-field ">
                                    <label for="title ">Title</label>
                                    <input id="title" type="text" class="validate" name="title" required>
                                </div>
                                <div class="input-field ">
                                    <label for="message">Message</label>
                                    <input id="message" type="text" class="validate" name="message" required>
                                </div>
                            </div>
                            <div id="myLoader" class="wait-loading row ">
                                <div class="col s1">
                                    <div class="preloader-wrapper small active">
                                        <div class="spinner-layer spinner-blue">
                                            <div class="circle-clipper left">
                                                <div class="circle"></div>
                                            </div>
                                            <div class="gap-patch">
                                                <div class="circle"></div>
                                            </div>
                                            <div class="circle-clipper right">
                                                <div class="circle"></div>
                                            </div>
                                        </div>

                                        <div class="spinner-layer spinner-red">
                                            <div class="circle-clipper left">
                                                <div class="circle"></div>
                                            </div>
                                            <div class="gap-patch">
                                                <div class="circle"></div>
                                            </div>
                                            <div class="circle-clipper right">
                                                <div class="circle"></div>
                                            </div>
                                        </div>

                                        <div class="spinner-layer spinner-yellow">
                                            <div class="circle-clipper left">
                                                <div class="circle"></div>
                                            </div>
                                            <div class="gap-patch">
                                                <div class="circle"></div>
                                            </div>
                                            <div class="circle-clipper right">
                                                <div class="circle"></div>
                                            </div>
                                        </div>

                                        <div class="spinner-layer spinner-green">
                                            <div class="circle-clipper left">
                                                <div class="circle"></div>
                                            </div>
                                            <div class="gap-patch">
                                                <div class="circle"></div>
                                            </div>
                                            <div class="circle-clipper right">
                                                <div class="circle"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col s5 valign-wrapper">
                                    <div class="valign">
                                        <h6>Sending please wait...</h6>
                                    </div>
                                </div>
                            </div>
                            <div class="input-field">
                                <select style="display: block; outline: none" id="category">
                                            <option style="outline: none" value="" disabled selected>Choose Branch
                                                
                                            </option>
                                            <?php
                                                include 'app/dbconfig.php';
                    
                                            $get_categories = "SELECT * FROM categories ";
                                            $category_list_result = mysqli_query($conn, $get_categories);
                                            while ($row = mysqli_fetch_assoc($category_list_result)) {
                                                echo "<option style='outline: none' value='" . $row['id'] . "'>" . $row['category'] . "</option>";

                                            }
                                            ?>

                                        </select>
                            </div><br><br><br>

                            <div class=" right-align">
                                <button class="btn-flat blue-text waves-effect waves-light " type="submit" name="action" onclick="sendData();">Submit
                                <i class="material-icons right">send</i>
                            </button>
                            </div>

                        </form>

                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="custom-container">
                    <div class="form-title blue center-align white-text">
                        <h5><b>Feedback</b></h5>
                    </div>
                    <div class="holder">
                        <ul id="ticker01">

                            <?php 
                            $college_name = $_SESSION['username'];

   			    $get_college_query = "SELECT * FROM members WHERE username = '$college_name '";
   
    

    				if ($result_query = mysqli_query($conn, $get_college_query)) {
        				while ($row = mysqli_fetch_assoc($result_query)) {
            					$college_id =   $row['id'];
            					$college_name =$row['username'];
        				}
        			}
                            
                            
                            
                            
                             $check= "SELECT * FROM feedbacks WHERE collegeid ='$college_id' ORDER BY id DESC";
                             $result= mysqli_query($conn, $check);
                             if(mysqli_num_rows($result) <=0){
                              	echo "<h6>No Feedbacks!</h6>";
                             }
                             while ($row = mysqli_fetch_assoc($result)) {
                                  //echo $row['message']."<br>";
                                  echo "<li>".$row['message']."</li>";
                            }
                             mysqli_close($conn);
                            ?>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="text-center">
        <div class='mytoast' id="success" style='display:none'></div>
        <div class='mytoast' id="error" style='display:none'></div>
    </div>


    <a style="width:230px; margin:0 auto;" href="login/logout.php" class="btn btn-default btn-lg btn-block">Logout</a>


    <!--Import jQuery before materialize.js-->
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
</body>

</html>