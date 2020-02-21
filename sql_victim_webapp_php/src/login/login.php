<html lang="en">
<head>
</head>
<body>

    <h2>Enter Username and Password</h2>
    <form action="login.php" method="get">
        <label style="width: 120px; display:inline-block" for="user"><b>Username</b></label>
        <input type="text" placeholder="Enter Username" name="user" required>

        <br><br>

        <label style="width: 120px; display:inline-block" for="password"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" required>

        <br><br>

        <button type="submit">Login</button>
    </form>

    <?php if (isset($_GET["user"]) && isset($_GET["password"])) : // check if credentials are delivered ?>
        <?php
        {
            // connect to database
            $mysqli = new mysqli("172.19.0.2","user","password","cmsuserdb");

            // build sql statement with input
            $sql = "SELECT userid FROM CMSUsers WHERE user = '". $_GET["user"]. "' AND password = '" . $_GET["password"] . "'";

            // execute the query against the database
            $result = $mysqli->query($sql);

            // check to see how many rows were returned from the database
            $rowcount = mysqli_num_rows($result);

            // close database
            $mysqli->close();

            // if a row is returned then the credentials must be valid, so
            // forward the user to the admin pages
            if ($rowcount != 0 ) { echo('<script>window.location.replace("admin_page.php");</script>'); }

            // if a row is not returned then the credentials must be invalid
            else { echo('Incorrect username or password, please try again.');}
        }
        ?>
    <?php endif; ?>

</body>
</html>
