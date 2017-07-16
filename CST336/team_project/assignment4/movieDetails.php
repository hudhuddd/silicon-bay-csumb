<?php

$host = "localhost";
$dbname = "guzm3592";

$username = "guzm3592";

$password = "5028afd";

$dbConn = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);

$dbConn -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

function fetchMovie($movieId) {
	global $dbConn;
	//it uses the variables declared previously
	$sql = "SELECT * FROM movie where movieId = $movieId";
	$stmt = $dbConn -> prepare($sql);
	$stmt -> execute();
	return $stmt -> fetch();
}

function fetchDirector($directorId) {
	global $dbConn;
	//it uses the variables declared previously
	$sql = "SELECT * FROM director where directorId = $directorId";
	$stmt = $dbConn -> prepare($sql);
	$stmt -> execute();
	return $stmt -> fetch();
}
?>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Movie Details</title>
	</head>

	<body>
		<?php
		if (isset($_GET['movieId'])) {
			$movieId = $_GET['movieId'];
			$movie = fetchMovie($movieId);
			$director = fetchDirector($movie['directorId']);
			echo "<h1>$movie[title]</h1>";
			echo "<dl>";
			echo "<dt>Release Date</dt>";
			echo "<dd>$movie[releaseDate]</dd>";
			echo "<dt>MPAA Rating</dt>";
			echo "<dd>$movie[certificate]</dd>";
			echo "<dt>Language</dt>";
			echo "<dd>$movie[language]</dd>";
			echo "<dt>Budget</dt>";
			echo "<dd>$ $movie[budget]</dd>";
			echo "<dt>Runtime</dt>";
			echo "<dd>$movie[minuteRuntime] minutes</dd>";
			echo "<dt>Country</dt>";
			echo "<dd>$movie[country]</dd>";
			echo "<dt>Director</dt>";
			echo "<dd>$director[firstName] $director[lastName]</dd>";
			echo "</dl>";
		}
		?>
	</body>
</html>
