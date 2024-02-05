<?php
$conn = mysql_connect("localhost","marijus_maksim","2E1aZIE9");
mysql_select_db($conn,"marijus_maksim");
 
$q=mysql_query("SELECT * FROM Messages");
while($e=mysql_fetch_assoc($q))
        $output[]=$e;
 
print(json_encode($output));
 
mysql_close($conn);
?>