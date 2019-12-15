<?php
//remove warnings error!
error_reporting(E_ERROR | E_PARSE);

//Dati accesso DB
$servername = "fdb26.awardspace.net";
$username = "3249545_account";
$password = "LoginApp001";
$db = "3249545_account";

//decodifica file json
$json = '{"user":"bonex","pass":"123456"}'; //only for testing from browser
$json_obj = json_decode (file_get_contents ("php://input"));
//$json_obj = json_decode ($json); //only for testing only for testing from browser



// Create connection  {"user":"bonex","pass":"123456"}
try {
    	$conn = mysqli_connect($servername,$username,$password,$db);
    }
catch(PDOException $e)
    {
    	die("OOPs something went wrong");
    }
	$user = $json_obj -> user;
	$pass = $json_obj -> pass;
        $sql = "SELECT * FROM Account WHERE user = '" . $user . "'";
        // AND pass = '" . $hash ."'

        $result = $conn->query($sql);

if ($result->num_rows > 0) {
    //output data of each row

    while($row = $result->fetch_assoc()) {
    $hash = md5($pass . $row['dataN']);
    //echo 'ciao';
    //echo '1@'.$row['user']."@".$row['nome']."@".$row['cognome'].'@'.$row['dataN'];
    if($hash == $row['pass'])
    {
    $myObj->status = "1";
     $myObj->user = $row['user'];
    $myObj->sname = $row['cognome'];
     $myObj->name = $row['nome'];
        $myObj->sname = $row['cognome'];
        $myObj->dataN = $row['dataN'];
        $myJSON = json_encode($myObj);
        echo $myJSON;
        }
        else//Se password errata
        {
            $myObj->status = "2";
                $myJSON = json_encode($myObj);
                echo $myJSON;

        }
        }
}

else {
        $myObj->status = "0";
        $myJSON = json_encode($myObj);
        echo $myJSON;

}



function checkUser($user,$email)
{
$servername = "fdb26.awardspace.net";
$username = "3249545_account";
$password = "LoginApp001";
$db = "3249545_account";

try {
    	$conn = mysqli_connect($servername,$username,$password,$db);
    }
catch(PDOException $e)
    {
    	die("OOPs something went wrong");
    }
$sql = "SELECT * FROM Account";
        // AND pass = '" . $hash ."'

        $result = $conn->query($sql);

if ($result->num_rows > 0) {
    //output data of each row

    while($row = $result->fetch_assoc()) {
    if($user == $row['user'])
    {
    //echo $user . $row['user'];
        return -1;
     }
     else if($email == $row['email'])
     {
             return -2;
     }
    }
    return 1;
 }
}


?>