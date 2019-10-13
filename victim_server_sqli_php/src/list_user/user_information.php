<?php

// connect to database
$mysqli = new mysqli("172.19.0.2","user","password","cmsuserdb");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
}
echo $mysqli->host_info . "<br><br>";

// build sql statement with input
$sql = "SELECT * FROM CMSUsers WHERE UserID = ".$_GET["UserID"];

// execute the query against the database
if (!$result = $mysqli->query($sql)) {
    echo ("Database Error <br><br>
           Query: <pre>".$sql."</pre><br>
           Errno: [{$mysqli->errno}] <br><br>
           {$mysqli->error}");
}

$rowcount = mysqli_num_rows($result);

$row = 1;
// iterate through record set
while($db_field = $result->fetch_assoc()) {
    if ($row <= $rowcount) {

        // display results in browser
        foreach (array_keys($db_field) as $key)
        {
            $value = $db_field[$key];
            print $key . " " . $value. "<br>";
        }
        $row++;

    }
}

// close database
$mysqli->close();

?>