<?php

// connect to database
$mysqli = new mysqli("172.19.0.2","user","password","productdb");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
}
echo $mysqli->host_info . "<br><br>";

// build sql statement with input
$sql = "SELECT * FROM Products WHERE Price < '".$_GET["value"]."'"."ORDER BY ProductName";

// execute the query against the database
if (!$result = $mysqli->query($sql)) {
    echo ("Database Error <br><br>
           Query: <pre>".$sql."</pre><br>
           Errno: [{$mysqli->errno}] <br><br>
           {$mysqli->error}");
}
// iterate through record set
while($row = $result->fetch_assoc())
{
// display results in browser
echo "Name : {$row['ProductName']} <br>".
     "Product ID : {$row['ProductID']} <br>".
     "Price : {$row['Price']} <br><br>";
}

// close database
$mysqli->close();

?>
