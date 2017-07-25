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
$stmt -> execute ( array (":firstname" => "Student", ":lastname" => "Project", ":username" => "guzm3592", ":password" => hash('sha1', '5028afd')));
echo "Your admin table is created!";
?>