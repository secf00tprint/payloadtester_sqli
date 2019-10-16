<!DOCTYPE html>
<html>
<body>

<h1><?php echo $_GET['table'];?></h1>
<?php
    // connect to database
    $mysqli = new mysqli("172.19.0.2","user","password","employeedb");
    if ($mysqli->connect_errno) {
        echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
    }
    echo $mysqli->host_info . "<br><br>";

    // build sql statement with input
    $sql = "SELECT ".$_GET['column1'].",".$_GET['column2'].",".$_GET['column3']." FROM `".$_GET['table']."`";

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
        foreach ($row as $item)
        echo $item."<br>";
    }

    // close database
    $mysqli->close();

?>
<br>
<button onclick="history.go(-1);">Back </button>

</body>
</html>
