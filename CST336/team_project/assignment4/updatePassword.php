<?
require 'db_connection.php';
session_start();
if (!isset($_SESSION['id'])) {
    header("Location: login.php");
}

// If we are receiving data from the password update form then change the password
if (isset($_POST['updatePassword'])) {
	$newpw = $_POST['password'];
	$sql = "UPDATE movie_admin SET password = :password WHERE id = :id";
	$stmt = $dbConn -> prepare($sql);
	$stmt -> execute(array(":password" => hash('sha1', $newpw), ":id" => $_SESSION['id']));
	echo "Password Succesfully Updated";
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
		<script>
			function confirmLogout(event) {
				var logout = confirm("Do you really want to log out?");
				if (!logout) {
					event.preventDefault();
				}
			}
		</script>
	</head>
	<body>
		<form method="post" action="logout.php" onsubmit="confirmLogout()">
			<input type="submit" value="Logout" />
		</form>
		<div>
			<form method="post">
				<p>
					New Password:
				</p>
				<input type="password" name="password" />
				<br />
				<input type="submit" name="updatePassword" value="Update Password">
			</form>
			<br />
			<br />
			<a href="allMovies.php"> Go back to main page </a>
		</div>
	</body>
</html>
