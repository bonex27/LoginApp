<?php
//include 'index.php';
error_reporting(E_ERROR | E_PARSE);

//remove warnings error!
//Dati accesso DB
$servername = "fdb26.awardspace.net";
$username = "3249545_account";
$password = "LoginApp001";
$db = "3249545_account";

//decodifica file json
$json = '{"nome":"giulio","cognome":"marchesi","user":"bonex","pass":"123456","dataN":"2019-11-1","email":"p.bonechi@gmail.com"}'; //only for testing from browser
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
if(checkUser($json_obj -> user,$json_obj -> email) == 1)
{

                //error_log($error_message, 3, $log_file);

                $hash = md5($pass . $json_obj -> dataN);
                $sql = $conn->prepare('INSERT INTO `Account`(`nome`, `cognome`, `user`, `pass`, `dataN`, `email`) VALUES (?,?,?,?,?,?)');
                $sql->bind_param("ssssss", $nome, $cognome, $user,$pass,$dataN,$email);
                // AND pass = '" . $hash ."'
                $nome = $json_obj -> nome;
                $cognome = $json_obj -> cognome;
                $user = $json_obj -> user;
                $pass = md5($json_obj -> pass. $json_obj -> dataN);
                $dataN = $json_obj -> dataN;
                $email = $json_obj -> email;
                $sql->execute();

                $nome_mittente = $json_obj -> nome;
                $mail_mittente = "bonex@ipack.dx.am";
                $mail_destinatario = $json_obj -> email;

        // definisco il subject ed il body della mail
        $mail_oggetto = "Iscrizione bonex Login";
        $mail_corpo = "Ciao " . $json_obj -> nome . "benvenuto su ipack!";

        // aggiusto un po' le intestazioni della mail
        // E' in questa sezione che deve essere definito il mittente (From)
        // ed altri eventuali valori come Cc, Bcc, ReplyTo e X-Mailer
        $mail_headers = "From: " .  $nome_mittente . " <" .  $mail_mittente . ">\r\n";
        $mail_headers .= "Reply-To: " .  $mail_mittente . "\r\n";
        $mail_headers .= "X-Mailer: PHP/" . phpversion();

        mail($mail_destinatario, $mail_oggetto, $mail_corpo, $mail_headers);
        $myObj->status = "1";
        $myObj->nome = $nome;
        $myObj->cognome = $cognome;
        $myObj->user = $user;
        $myObj->dataN = $dataN;
        $myObj->email = $email;
        $myJSON = json_encode($myObj);
        echo $myJSON;
}
else if(checkUser($json_obj -> user,$json_obj -> email) == -1)//se user gia presente!
{
    $myObj->status = "-1";
    $myJSON = json_encode($myObj);
        echo $myJSON;
}
else if(checkUser($json_obj -> user,$json_obj -> email) == -2) //se email gia presente!
{
    $myObj->status = "-2";
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