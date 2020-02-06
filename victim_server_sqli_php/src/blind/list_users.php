<?php

// connect to database
$mysqli = new mysqli("172.19.0.2","user","password","cmsuserdb");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
}

// build sql statement with input
$sql = "SELECT * FROM CMSUsers WHERE UserID = ".$_GET["UserID"];

// execute the query against the database
$result = $mysqli->query($sql);

$rowcount = mysqli_num_rows($result);

// close database
$mysqli->close();

?>