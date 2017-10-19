<?php
 
$response = array();
 
if (isset($_GET['pid']) ) {
 
    $pid = $_GET['pid'];
    $name = $_GET['name'];
    $price = $_GET['price'];
    $description = $_GET['description'];
 
    require 'db_connect.php';
 
    $db = new DB_CONNECT();
 
    $result = mysql_query("UPDATE wp_inventory SET owner = '$owner', location = '$location', description = '$description' WHERE id = $id");
 
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Product successfully updated.";
 
        echo json_encode($response);
    } else {
 
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    echo json_encode($response);
}
?>