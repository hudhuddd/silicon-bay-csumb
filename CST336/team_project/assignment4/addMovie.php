<?php
require 'db_connection.php';
session_start();
if (!isset($_SESSION['id'])) {
    header("Location: login.php");
}

function getDirectorId($firstName, $lastName) {
    global $dbConn;
    //it uses the variables declared previously
    $sql = "SELECT * FROM director WHERE firstName = :firstName AND lastName = :lastName";
    $stmt = $dbConn -> prepare($sql);
    $stmt -> execute(array(":firstName" => $firstName,":lastName" => $lastName));
    $director = $stmt->fetch();
    if (!empty($director)) {
        return $director['directorId'];
    } else {
        return -1;
    }
}

function insertDirector($firstName, $lastName) {
    global $dbConn;
    //it uses the variables declared previously
    $sql = "INSERT INTO director (firstName, lastName) VALUES (:firstName, :lastName)";
    $stmt = $dbConn -> prepare($sql);
    $stmt -> execute(array(":firstName" => $firstName,":lastName" => $lastName));
    $last_id = $dbConn->lastInsertId();
    return $last_id;
}

function getMovieId($title) {
    global $dbConn;
    $sql = "SELECT * FROM movie WHERE title = :movieTitle";
    $stmt = $dbConn -> prepare($sql);
    $stmt -> execute(array(":movieTitle" => $title));
    $movie = $stmt->fetch();
    if (!empty($movie)) {;
        return $movie['movieId'];
    } else {
        return -1;
    }
}

if (isset($_POST['submit'])) {
    if(getMovieId($_POST['title']) < 0) {
        $directorId = getDirectorId($_POST['directorFirstName'], $_POST['directorLastName']);
        if($directorId < 0) {
            $directorId = insertDirector($_POST['directorFirstName'], $_POST['directorLastName']);
        }
        $sql = "INSERT INTO movie (title, releaseDate, certificate, language, budget, minuteRuntime,country, directorId) VALUES (:title, :releaseDate, :certificate, :language, :budget, :minuteRuntime,:country, :directorId)";

    $stmt = $dbConn -> prepare($sql);
    $stmt -> execute(array(":title" => $_POST['title'],
        ":releaseDate" => $_POST['releaseDate'],
        ":certificate" => $_POST['certificate'],
        ":language" => $_POST['language'],
        ":budget" => $_POST['budget'],
        ":minuteRuntime" => $_POST['minuteRuntime'],
        ":country" => $_POST['country'],
        ":directorId" => $directorId));
        header("Location: allMovies.php");
    } else {
        echo "Movie with same title already exists";
    }
}
?>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title></title>
        <link href="./styles.css" rel="stylesheet">
    </head>
    <body>
        <form method="post" action="logout.php" onsubmit="confirmLogout()">
            <input type="submit" value="Logout" />
        </form>
        <form method="post">
            <h3>Add New Entry:</h3>
            <table>
                <tr>
                    <td>Title:</td>
                    <td>
                    <input size="80" type="text" name="title"/>
                    </td>
                </tr>
                <tr>
                    <td>Release Date:</td>
                    <td>
                    <input type="text" name="releaseDate"/>
                    </td>
                </tr>
                <tr>
                    <td>Certificate:</td>
                    <td>
                    <input type="text" name="certificate"/>
                    </td>
                </tr>
                <tr>
                    <td>Language:</td>
                    <td>
                    <input type="text" name="language"/>
                    </td>
                </tr>
                <tr>
                    <td>Budget:</td>
                    <td>
                    <input type="text" name="budget"/>
                    </td>
                </tr>
                <tr>
                    <td>Minute Runtime:</td>
                    <td>
                    <input type="text" name="minuteRuntime"/>
                    </td>
                </tr>
                <tr>
                    <td>Country:</td>
                    <td>
                    <input type="text" name="country"/>
                    </td>
                </tr>
                <tr>
                    <td>Director First Name:</td>
                    <td>
                    <input type="text" name="directorFirstName"/>
                    </td>
                <tr>
                <tr>
                    <td>Director Last Name:</td>
                    <td>
                    <input type="text" name="directorLastName"/>
                    </td>
                <tr>
                    <td>
                    <input type="submit" name="submit" value="Add Movie"/>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
