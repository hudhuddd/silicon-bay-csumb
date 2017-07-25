<?php

$host     = "localhost";
$dbname   = "guzm3592"; //change this to your otterID
$username = "guzm3592"; //change this to your otter ID
$password = "5028afd"; //change this to your database account password

// establish database connection
try
{
	$dbConn = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
}
catch(Exception $e)
{
	echo"Unable to connect to database!";
	exit();
}

$dbConn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); 

?>