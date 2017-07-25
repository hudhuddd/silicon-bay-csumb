<?php
require 'db_connection.php';
?>

<!DOCTYPE html>
<html lang="en">
<head>
<title></title>

</head>

<form method="post">
<h3>Add New Entry:</h3>
<table>
	<tr>
		<td>Title:</td>
		<td><input type="text" name="title"/></td>
	</tr>
	<tr>
		<td>Release Date:</td>
		<td><input type="text" name="releaseDate"/></td>
	</tr>
	<tr>
		<td>Certificate:</td>
		<td><input type="text" name="certificate"/></td>
	</tr>
	<tr>
		<td>Language:</td>
		<td><input type="text" name="language"/></td>
	</tr>
    <tr>
        <td>Budget:</td>
        <td><input type="text" name="budget"/></td>
    </tr>
    <tr>
        <td>Minute Runtime:</td>
        <td><input type="text" name="minuteRuntime"/></td>
    </tr>
    <tr>
        <td>Country:</td>
        <td><input type="text" name="country"/></td>
    </tr>
    <tr>
        <td>Movie ID:</td>
        <td><input type="text" name="movieId"/></td>
    </tr>
    <tr>
        <td>Director ID:</td>
        <td><input type="text" name="directorId"/></td>
	<tr>
		<td><input type="submit" name="submit" value="add"/></td>
	</tr>
</table>
<?php
if (isset($_POST['submit']))
	{
        $sql = "INSERT INTO movie
                (movieId, title, releaseDate, certificate, language,
                budget, minuteRuntime, country, directorId)
	            VALUES
                (:movieId, :title, :releaseDate, :certificate,
                :language, :budget,:minuteRuntime,:country, :directorId)";
        $stmt = $dbConn -> prepare($sql);
        $stmt -> execute (array (":movieId" => $_POST['movieId'],
                                ":title" => $_POST['title'],
                                ":releaseDate" => $_POST['releaseDate'],
                                ":certificate" => $_POST['certificate'],
                                ":language" => $_POST['language'],
                                ":budget" => $_POST['budget'],
                                ":minuteRuntime" => $_POST['minuteRuntime'],
                                ":country" => $_POST['country'],
                                ":directorId" => $_POST['directorId']));
        echo "Your Movie was added";
    }
?>
</form>
<br>
<br>
<br>
<form method="post">
<h3>Delete Entry:</h3>
    <table>
        <tr>
            <td>Movie ID</td>
            <td><input type="text" name="mId"/></td>
        </tr>
        <tr>
            <td><input type="submit" name="delete" value="delete"/></td>
        </tr>
    </table>

<?php
    if (isset($_POST['delete']))
        {
            $sql = "DELETE FROM movie WHERE movieId = :mId";
            $stmt = $dbConn -> prepare($sql);
            $stmt -> execute (array (":mId" => $_POST['mId']));
            echo "Your movie was deleted";
        }
?>
</form>
<br>
<br>
<br>
<form method="post">
	<h3>Update Entry:</h3>
    <table>
        <tr>
            <td>Enter the movieId</td>
            <td><input type="text" name="mId"/></td>
        </tr>
        <tr>
            <td>Value to change:</td>
            <input type="radio" name="updateField" value="title"> Title<br>
            <input type="radio" name="updateField" value="releaseDate"> Release Date<br>
            <input type="radio" name="updateField" value="certificate"> Certificate<br>
            <input type="radio" name="updateField" value="language"> Language<br>
            <input type="radio" name="updateField" value="budget"> Budget<br>
            <input type="radio" name="updateField" value="minuteRuntime"> Minute Runtime<br>
            <input type="radio" name="updateField" value="country"> Country<br>
            <input type="radio" name="updateField" value="directorId"> Director ID
        </tr>
        <tr>
            <td>Value it will  be changed too:</td>
            <td><input type="text" name="updateValue"/></td>
        </tr>
        <tr>
            <td><input type="submit" name="update" value="update"/></td>
        </tr>
    </table>

<?php
    if (isset($_POST['update']))
        {
        	$updateField = $_POST['updateField'];
			$sql = "UPDATE movie SET " . $updateField . " = :updateValue WHERE movieId = :mId";
            $stmt = $dbConn -> prepare($sql);
            $stmt -> execute (array (":updateValue" => $_POST['updateValue'],
                                     ":mId" => $_POST['mId']));
									
            echo "Your movie was updated";
        }
?>
</form>

</body>
</html>
