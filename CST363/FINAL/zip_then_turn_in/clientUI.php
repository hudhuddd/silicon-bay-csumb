
<?php
//This program pulls basic data from our latest_backup mysql view and presents
//it in an html table.
require "db_connection.php";

function fetchLatestBackupReport() {
    global $dbConn;
    $sql = "SELECT backupID, clientName, serverName FROM latest_backup";
    $stmt = $dbConn -> prepare($sql);
    $stmt -> execute();
    return $stmt -> fetchAll();
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Server Backup Report</title>
</head>
<body>
  <h1>Latest Backup Report</h1>
  <table>
    <tr>
      <td>Backup ID</td>
      <td>Client Name</td>
      <td>Server Name</td>
    </tr>
    <?php
    $report=fetchLatestBackupReport();
    foreach($report as $client){
      echo "<tr>";
      echo "<td>$client[backupID]</td>";
      echo "<td>$client[clientName]</td>";
      echo "<td>$client[serverName]</td>";
      echo "</tr>";
    }
    ?>
  </table>
</body>
</html>
