<!DOCTYPE html>
<html>
<head>
    <title>ESTUDIANTES DB</title>
    <link type="text/css" rel="stylesheet" href="css/index.css" />
</head>
<body>
<div class="box" id="container">
    <!-- Header -->
    <div class="box" id="header">
        <h3>${message}</h3>
    </div>

    <br>

    <!-- Registry -->
    <div class="box" id="registry">
        <form action="http://localhost:4567/modify" method="POST">
            <fieldset style="width: 300px">
                <legend>MODIFICAR</legend>
                <!-- Matricula -->
                MATRICULA: <br>
                <input type="text" name="matricula" value=${matricula} readonly/>
                <br>
                <!-- Name -->
                NOMBRES: <br>
                <input type="text" name="name" value=${oldName} />
                <br>
                <!-- Last Name -->
                APELLIDOS: <br>
                <input type="text" name="last_name" value=${oldLastName} />
                <br>
                <!-- Telephone -->
                TELEFONOS: <br>
                <input type="text" name="telephone" value=${oldTelephone} />
                <br><br>
                <!-- Submit -->
                <input type="submit" name="submit" value="ENVIAR">
            </fieldset>
        </form>
        <br><br>
        <form action="http://localhost:4567" method="GET">
            <!-- Cancel -->
            <input type="submit" name="submit" value="MENU PRINCIPAL">
        </form>
    </div>
</div>
</body>
</html>