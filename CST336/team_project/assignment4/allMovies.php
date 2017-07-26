<?php
require "db_connection.php";
session_start();
if (!isset($_SESSION['id'])) {
    header("Location: login.php");
}

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

// If movie is deleted it will come to this page for the deletion to occur.
if (isset($_GET['deleteMovieId'])) {
    $sql = "DELETE FROM movie WHERE movieId = :mId";
    $stmt = $dbConn -> prepare($sql);
    $stmt -> execute(array(":mId" => $_GET['deleteMovieId']));
}
?>
<!DOCTYPE html>
<html lang="en">
    <head>
        <script>
            function confirmLogout(event) {
                var logout = confirm("Do you really want to log out?");
                if (!logout) {
                    event.preventDefault();
                }
            }
        </script>
        <link href="./styles.css" rel="stylesheet">
        <title>All Movies</title>
    </head>

    <body>

        <form method="post" action="logout.php" onsubmit="confirmLogout()">
            <input type="submit" value="Logout" />
        </form>

        <form action="updatePassword.php" method="post">
            <input type="hidden" name="id" value=<?php echo $_SESSION['id'] ?> />
            <input type="submit" name="update" value="Update Password" />
        </form>
        <h2><a href="addMovie.php">Add a movie</a></h2>
        <div id="wrapper">
            <table>
                <tr>
                    <th>Title</th>
                    <th>Year - Sort <a href="allMovies.php?yearSort=ASC">Ascending</a> / <a href="allMovies.php?yearSort=DESC">Descending</a></th>
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
            echo "<div align='center'>Average Runtime: " . (int)$runtimeAverage['avgRuntime'] . " minutes <div/>";
            echo "<br>";
            echo "Sum of Runtime: $runtimeSum[sumRuntime] minutes";
            ?>

            <table>
                <tr>
                    <th>List of Directors</th>
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
