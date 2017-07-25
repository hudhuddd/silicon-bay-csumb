<?
require 'db_connection.php';//change this to your own database authentication file

if (isset($_POST['save'])) { //checks whether we're coming from "save data" form
$newpw = $_POST['password'];
$sql = "UPDATE movie_admin
SET password = :password
WHERE id = :id";
$stmt = $dbConn -> prepare($sql);
$stmt -> execute(array(":password"=> hash('sha1', $newpw), ":id"=>'1'));

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
<link href="styles.css" rel="stylesheet">

<title>Update Password</title>

</head>

<body>
<div>

<?

if (isset($_POST['id'])) {
  /*$userInfo = getId($_POST['id']);*/

  ?>

  <form method="post">
  New Password: <input type="text" name="password" /><br />
  <input type="submit" name="save" value="Save">
  </form>
<?
}

?>
<br /><br />
<a href="allMovies.php"> Go back to main page </a>

</div>
</body>
</html>
