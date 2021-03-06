<?php
require 'db_connection.php';
session_start();

if (isset($_POST['login'])) {

	$sql = "SELECT * FROM movie_admin WHERE username = :username AND password = :password";

	$stmt = $dbConn -> prepare($sql);
	$stmt -> execute(array(":username" => $_POST['username'], ":password" => hash("sha1", $_POST['password'])));

	$record = $stmt -> fetch();

	if (empty($record)) {
		echo "Wrong username/password!";
	} else {
		$sql = "INSERT INTO signin_log (dateTimeLog) VALUES (:dateTimeLog)";
		$stmt = $dbConn -> prepare($sql);
		$stmt -> execute(array(":dateTimeLog" => date('y-m-d-h-i-s')));
		$_SESSION['username'] = $record['username'];
		$_SESSION['name'] = $record['firstname'] . " " . $record['lastname'];
		$_SESSION['id'] = $record['id'];
		header("Location: allMovies.php");
	}
}

if (isset($_POST['createuser'])) {
    $sql = "INSERT INTO movie_admin (firstname, lastname, username, password) VALUES (:firstname, :lastname, :username, :password)";

    $stmt = $dbConn -> prepare($sql);
    $stmt -> execute(array(":firstname" => $_POST['firstname'], ":lastname" => $_POST['lastname'], ":username" => $_POST['username'], ":password" => hash("sha1", $_POST['password'])));
    echo "Your user has been created";
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

		<title>Movies</title>
		<meta name="description" content="">
		<meta name="author" content="su5196">

		<meta name="viewport" content="width=device-width; initial-scale=1.0">

		<!-- Replace favicon.ico & apple-touch-icon.png in the root of your domain and delete these references -->
		<link rel="shortcut icon" href="/favicon.ico">
		<link rel="apple-touch-icon" href="/apple-touch-icon.png">

		<style>
			form {
				display: inline;
			}

		</style>

	</head>
	<body>
		<div>
			<h1>Login</h1>
			<form method="post">
				Username:
				<input type="text" name="username" />
				<br />
				<p></p>
				Password:
				<input type="password" name="password" />
				<br />
				<p></p>
				<input name="login" type="submit" value="Login" />
				<p></p>
			</form>
		</div>
		<div>
			<h1>Create a user</h1>
			<form method="post">
			    First name
			    <br>
                <input type="text" name="firstname" />
                <br>
			    Last name
			    <br>
                <input type="text" name="lastname" />
                <br>
				Username:
				<br>
				<input type="text" name="username" />
				<br>
				Password:
				<br>
				<input type="password" name="password" />
				<br>
				<input type="submit" name="createuser" value="Create User" />
			</form>
		</div>
	</body>
</html>
