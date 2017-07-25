<?
require 'db_connection.php';//change this to your own database authentication file

function getId($id){
global $dbConn;

$sql = "SELECT * 
FROM movie_admin
WHERE id = :id";
$stmt = $dbConn -> prepare($sql);
$stmt -> execute(array(":id"=>$id));
return $stmt->fetch(); 
}

if (isset($_POST['save'])) { //checks whether we're coming from "save data" form

$sql = "UPDATE movie_admin
SET password = :password
WHERE id = :id";
$stmt = $dbConn -> prepare($sql);
$stmt -> execute(array(":id"=>$_POST['id'],
":password"=>$_POST['password']
));  

//":id"->$_POST['id']

echo "RECORD UPDATED!! <br> <br>"; 
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">

<!-- Always force latest IE rendering engine (even in intranet) & Chrome Frame 
Remove this if you use the .htaccess -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>Update Password</title>

</head>

<body>
<div>

<?

if (isset($_POST['id'])) {
$userInfo = getId($_POST['id']); ?>

<form method="post">
New Password: <input type="text" name="password" value="<?=$userInfo['password']?>" /><br />
<input type="submit" name="save" value="Save"> 
</form>

<? }

?>
<br /><br />
<a href="allMovies.php"> Go back to main page </a>

</div>
</body>
</html>