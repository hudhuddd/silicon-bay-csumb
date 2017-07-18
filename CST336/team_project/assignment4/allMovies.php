<?php

$host = "localhost";
$dbname = "guzm3592";

$username = "guzm3592";

$password = "5028afd";

$dbConn = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);

$dbConn -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

function fetchAllMovies($yearSort) {
	global $dbConn;
	//it uses the variables declared previously
	if ($yearSort == 'ASC') {
		$sql = "SELECT movieId, title, YEAR(releaseDate) AS releaseYear FROM movie ORDER BY releaseYear ASC";

	} else {
		$sql = "SELECT movieId, title, YEAR(releaseDate) as releaseYear FROM movie ORDER BY releaseYear DESC";
	}
	$stmt = $dbConn -> prepare($sql);
	$stmt -> execute();
	return $stmt -> fetchAll();
}

function fetchRuntimeSum() {
	global $dbConn;
	$sql = "SELECT SUM(minuteRuntime) AS sumRuntime FROM movie";
	$stmt = $dbConn -> prepare($sql);
	$stmt -> execute();
	return $stmt -> fetch();

}

function fetchRuntimeAverage() {
	global $dbConn;
	$sql = "SELECT AVG(minuteRuntime) AS avgRuntime FROM movie";
	$stmt = $dbConn -> prepare($sql);
	$stmt -> execute();
	return $stmt -> fetch();
}

function fetchAllDirectors() {
	global $dbConn;
	//it uses the variables declared previously
	$sql = "SELECT * FROM director";
	$stmt = $dbConn -> prepare($sql);
	$stmt -> execute();
	return $stmt -> fetchAll();
}
?>
<!DOCTYPE html>
<html lang="en">
	<head>
		<style>
		h1 {
			text-align:center;
			font-family:'Palatino';
		}
		h3 {
			font-family:'Palatino';
		}
		table {
			margin: 0 auto;
		}
		td {
			padding: 10px;
			border: black 1px solid;
		}
		#wrapper {
			width: 900px;
			margin: 0px auto;
		}

	</style>
		<title>All Movies</title>
		<h1>
   	   Movies
   		</h1>
	</head>

	<body>
		<div id="wrapper">
		<table>
			<tr>
				<th>Title</th>
				<th>Year - Sort <a href="allMovies.php?yearSort=ASC">ASC</a> / <a href="allMovies.php?yearSort=DESC">DESC</a></th>
				<a href="../../assignments/homepage.html"><p style = "text-align:center">Student's Home Page</p></a>
			</tr>
			<?php
			$yearSort = 'ASC';
			if (isset($_GET['yearSort'])) {
				$yearSort = $_GET['yearSort'];
			}
			$movies = fetchAllMovies($yearSort);
			foreach ($movies as $movie) {
				echo "<tr>";
				echo "<td><a href=\"movieDetails.php?movieId=$movie[movieId]\">$movie[title]</a></td>";
				echo "<td>$movie[releaseYear]</td>";
				echo "</tr>";
			}
			?>
		</table>

		<?php
		$runtimeAverage = fetchRuntimeAverage();
		$runtimeSum = fetchRuntimeSum();
		echo "<div align='center'>Average Runtime: " . (int) $runtimeAverage['avgRuntime'] ." minutes<div/>";
		echo "<br>";
		echo "Sum of Runtime: $runtimeSum[sumRuntime] minutes";
		?>

		<table>
			<tr>
				<th>Director</th>
			</tr>
			<?php
			$directors = fetchAllDirectors();
			foreach ($directors as $director) {
				echo "<tr>";
				echo "<td>$director[firstName] $director[lastName]</td>";
				echo "</tr>";
			}
			?>
		</table>
		</div>
	</body>
</html>