<?php
$conn = mysql_connect("localhost","marijus_maksim","2E1aZIE9");
mysql_select_db($conn,"marijus_maksim");
 
$q=mysql_query("INSERT INTO Messages (Text, GPSlattitude, GPSllongtitude)
VALUES ('".mysql_escape_string($_REQUEST['Text'])."', ".mysql_escape_string($_REQUEST['lattitude'].)",".mysql_escape_string($_REQUEST['longtitude']).")");
while($e=mysql_fetch_assoc($q))
        $output[]=$e;
 
print(json_encode($output));
 
mysql_close($conn);
?>