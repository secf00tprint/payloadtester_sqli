
<?php

$manager = new MongoDB\Driver\Manager("mongodb://root:password@172.19.0.5:27017");


// http://127.0.0.1:8782/products.php?product[$gt]=%22%22
// http://127.0.0.1:8782/products.php?product[$ne]=Product
$filter = array("product" => $_GET["product"]);

$options = [];
$query = new MongoDB\Driver\Query($filter, $options);
$rows = $manager->executeQuery('nosqlidb.products', $query); // $mongo contains the connection object to MongoDB
foreach($rows as $r){
    $document = json_decode(json_encode($r),true);
    foreach($document as $key => $value) {
        echo "$key: $value, ";
    }
    echo "<br>";
}

?>

