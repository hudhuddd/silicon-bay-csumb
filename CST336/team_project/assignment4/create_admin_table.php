<?php
require "db_connection.php";

$sql = "CREATE TABLE movie_admin (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
firstname varchar (50),
lastname varchar (50),
username varchar (50) NOT NULL,
password varchar (50) NOT NULL)";

$stmt = $dbConn -> prepare($sql);
$stmt -> execute();

$sql = "INSERT INTO movie_admin
(firstname, lastname, username, password)
VALUES
(:firstname, :lastname, :username, :password)";
$stmt = $dbConn -> prepare($sql);
$stmt -> execute(array(":firstname" => "Student", ":lastname" => "Project", ":username" => "andrea", ":password" => hash('sha1', 'secret')));
echo "Your admin table is created!";

$sql = "CREATE TABLE signin_log (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
dateTimeLog varchar (50))";

$stmt = $dbConn -> prepare($sql);
$stmt -> execute();

$sql = "INSERT INTO signin_log
(dateTimeLog)
VALUES
(:dateTimeLog)";
$stmt = $dbConn -> prepare($sql);
$stmt -> execute(array(":dateTimeLog" => date('y-m-d-h-i-s')));
echo "Your log table is created!";
?>
