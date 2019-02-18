<?php
	$to      = 'martinpueyo14@yahoo.com.ar';
	$subject = $_POST['subject'] ;
	
	$message = wordwrap($_POST['message'], 70, "\r\n");
	$message = 'from: '.$_POST['name'].'<'.$_POST['email'].">\r\n\r\n".$message;

	mail($to, $subject, $message);
	echo 'Sent';
?>
