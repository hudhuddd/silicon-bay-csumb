<?php
require 'db_connection.php';
session_start();
if (!isset($_SESSION['id'])) {
    header("Location: login.php");
}

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

if (isset($_POST['update'])) {
    $updateField = $_POST['updateField'];
    $sql = "UPDATE movie SET " . $updateField . " = :updateValue WHERE movieId = :mId";
    $stmt = $dbConn -> prepare($sql);
    $stmt -> execute(array(":updateValue" => $_POST['updateValue'], ":mId" => $_GET['movieId']));
    echo "Your movie was updated";
}

?>
<!DOCTYPE html>
<html lang="en">
	<head>
		<link href="styles.css" rel="stylesheet">
		<title>Movie Details</title>
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

		<form action="updatePassword.php" method="post">
			<input type="hidden" name="id" value=<?php echo $_SESSION['id']; ?> />
			<input type="submit" name="update" value="Update Password" />
		</form>
		<a href="allMovies.php">
		<p style = "text-align:center">
			Return to All Movies Page
		</p> </a>
		<div style="float:left">
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
		    echo "<h2><a href='allMovies.php?deleteMovieId=$movie[movieId]'>Delete Movie</a></h2>";
		}
		?>
		</div>
		<div style="float:right">
        <h3>Update Movie</h3>
        <form method="post">
            <table>
                <tr>
                    <td>Value to change:</td>
                    <input type="radio" name="updateField" value="title">
                    Title
                    <br>
                    <input type="radio" name="updateField" value="releaseDate">
                    Release Date
                    <br>
                    <input type="radio" name="updateField" value="certificate">
                    Certificate
                    <br>
                    <input type="radio" name="updateField" value="language">
                    Language
                    <br>
                    <input type="radio" name="updateField" value="budget">
                    Budget
                    <br>
                    <input type="radio" name="updateField" value="minuteRuntime">
                    Minute Runtime
                    <br>
                    <input type="radio" name="updateField" value="country">
                    Country
                    <br>
                    <input type="radio" name="updateField" value="directorId">
                    Director ID
                </tr>
                <tr>
                    <td>Value it will  be changed too:</td>
                    <td>
                    <input type="text" name="updateValue"/>
                    </td>
                </tr>
                <tr>
                    <td>
                    <input type="submit" name="update" value="update"/>
                    </td>
                </tr>
            </table>
        </form>
</div>

	</body>
</html>
