<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="formato.css">
	<title></title>
</head>
<body>
	<section>
		<div class="container">
	    	<div class="helper">
	        	<div class="content">
	        		<div align="center">
						<form id="form1" name="form1" method="post">
							<input type="text" name="id" id="id">
							<input name="Submit" type="submit" value="Cosulta">
						</form>
						<br>
						<br>
						<table border="1">
						<?php
							if (isset ($_POST['Submit'])) {
								echo "<tr><td>ID</td><td>Hora de registro</td></tr>";
								$id = $_POST['id'];
								if ($id !== "") {
									$empleado = array();
									$dbh = new PDO('mysql:host=sql9.freesqldatabase.com;dbname=sql9287688', "sql9287688", "RfccTfjCEe");
									$dbh->query("SET NAMES 'utf8'");
									$sql="select empleado.ID, chequeos.hf from empleado inner join chequeos on empleado.ID=chequeos.ID WHERE empleado.Nombre = '".$id."'";
									foreach ($dbh->query($sql) as $res)
									{
										$empleado[]=$res;
									}
									$dbh=null;
									for($i=0;$i<count($empleado);$i++)
									{
										echo "<tr><td>";
										echo $empleado[$i]["ID"];
										echo "</td><td>";
										echo $empleado[$i]["hf"];
										echo "</td></tr>";
									}
								}else{
									echo "<span style='color: red'>No se ejecuto la accion debido a que uno de los campos estaba vacio</span>";
								}
							}
						?>
						</table>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>