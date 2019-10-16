<!DOCTYPE html>
<html>
<body>

<form method="get" action="show_employee_table.php" onsubmit="setDBParams()">
    <h2>Select Time Sheet</h2>
    <select id="selectEmployee">
    <?php

    // connect to database
    $mysqli = new mysqli("172.19.0.2","user","password","employeedb");
    if ($mysqli->connect_errno) {
        echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
    }
    echo $mysqli->host_info . "<br><br>";

    // build sql statement with input
    $sql = "SELECT * FROM Timesheets";

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
        $id = $row['EmployeeName'] . "-". $row['EmployeeID'] . "-" . $row['Date'];
        echo ("<option value=\"{$id}\">{$id}</option>");
    }

    // close database
    $mysqli->close();

    ?>
    </select>
    <h2>Select information to show</h2>
    <select id="columnsToUse" multiple>
        <option value="Topic">Topic</option>
        <option value="JobDetails">Job Details</option>
        <option value="DayRate">Day Rate</option>
        <option value="WorkingHours">Working Hours</option>
    </select>
    <input type="hidden" id="column1" name="column1" value="1"/>
    <input type="hidden" id="column2" name="column2" value="2"/>
    <input type="hidden" id="column3" name="column3" value="3"/>
    <input type="hidden" id="table" name="table" value="4"/>

    <br><br>
    <input type="submit" value="Show">
    <div id="errorMessages"></div>
</form>

<script>
    function setDBParams() {
        colId = 0;
        selectedValues = getSelectValues(document.getElementById('columnsToUse'));
        if (selectedValues.length == 3)
        {
            selectedValues.forEach(setupParam);
            document.getElementById('table').value = document.getElementById('selectEmployee').value;
        }
        else
        {

        }
    }

    function setupParam(item)
    {
        colId = colId + 1;
        document.getElementById('column' + colId).value = item;
    }

    function getSelectValues(select) {
        var result = [];
        var options = select && select.options;
        var opt;

        for (var i=0, iLen=options.length; i<iLen; i++) {
            opt = options[i];

            if (opt.selected) {
                result.push(opt.value || opt.text);
            }
        }
        return result;
    }
</script>

</body>
</html>
