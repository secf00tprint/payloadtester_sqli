<?php

$customer_max_str_length = 20;
// names created with a random generator
$customer_names = array();

$prompt = array(
    1 => "Choose customer",
    2 => "Soft skills of customer"
);

// Populates names to choose
function populate_customer_names()
{

    global $customer_names;

    // connect to database
    $mysqli = new mysqli("172.19.0.2", "user", "password", "capabilitiesdb");
    if ($mysqli->connect_errno) {
        echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
    }

    // build sql statement with input
    $sql = "SELECT * from Users";

    // execute the query against the database
    if (!$result = $mysqli->query($sql)) {
        echo("Database Error <br><br>
               Query: <pre>" . $sql . "</pre><br>
               Errno: [{$mysqli->errno}] <br><br>
               {$mysqli->error}");
    }
    // iterate through record set
    $count = 1;
    while ($row = $result->fetch_assoc()) {
        $customer_names[$count]= $row["Givenname"]." ".$row["Surname"];
        $count++;
    }

    // close database
    $mysqli->close();
}

// Validation function to check string length
function validate_strlen($input_string)
{
    global $customer_max_str_length;
    return (strlen($input_string) <= $customer_max_str_length);
}

// Validation function to check if is allow set of customers
function validate_in_customers($input_string)
{
    global $customer_names;
    return in_array($input_string, $customer_names);
}

// Function to create options for dropdown box
function create_options($options_array)
{
    $return_str = "";
    foreach ($options_array as $value)
    {
        $return_str = $return_str . '<option>' . $value . '</option>';
    }
    return $return_str;
}

// output the form with possible validation errors
function out_form1($err_string)
{
    global $prompt;
    global $customer_names;
    echo '
<html>
<body>
<form method="GET">
    <b>'.$prompt[1].'</b><br><br>
    <select name="param" size="'.sizeof($customer_names). '">
        '.create_options($customer_names).'
    </select>
    <input type="hidden" name="form" value="form1"/>';
    if ($err_string != '') {
        echo '<br><br><i>'.$err_string.'</i>';
    }
    echo '
    <br><br><input type="submit" value="Submit"/>
</form>

</body>
</html>';
}

function out_form2()
{

    global $prompt;
    $capabilities = array();

    // connect to database
    $mysqli = new mysqli("172.19.0.2", "user", "password", "capabilitiesdb");
    if ($mysqli->connect_errno) {
        echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
    }

    // no need to validate param as form1 did this for us
    $sql = "SELECT * FROM Capabilities WHERE UserID=".$_GET["param"];

    // execute the query against the database
    if (!$result = $mysqli->query($sql)) {
        echo("Database Error <br><br>
               Query: <pre>" . $sql . "</pre><br>
               Errno: [{$mysqli->errno}] <br><br>
               {$mysqli->error}");
    }
    // iterate through record set
    while ($row = $result->fetch_assoc()) {
        array_push($capabilities, $row["Description"]);
    }

    // close database
    $mysqli->close();

    echo '
<html>
<body>
    <b>'.$prompt[2].'</b><br><br>';
    foreach($capabilities as $result) {
        echo $result, '<br>';
    }

    echo '
    <button onclick="history.go(-1);">Back </button>

</body>
</html>';
}


// main routine
populate_customer_names();
// process form
// is form1?
if ($_GET["form"] == "form1"){
    // first_ validation
    if (validate_strlen($_GET["param"]) == true) {
        // second validation
        if (validate_in_customers($_GET["param"]) == true) {
            header('Location: forms.php?form=form2&param='.array_search($_GET["param"], $customer_names));
        }
        else {
            out_form1("No known customer");
        }
    }
    else {
        out_form1("Name should have max ".$customer_max_str_length." chars");
    }
}
// is form2 ?
else if ($_GET["form"] == "form2"){
    out_form2();
}
// output standard else
else {
    out_form1(""); }
?>
