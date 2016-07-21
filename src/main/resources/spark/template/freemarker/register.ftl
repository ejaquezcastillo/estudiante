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
        <form acction="http://localhost:4567/new" method="POST">
            <fieldset style="width: 300px;">
                <legend>ESTUDIANTES DB</legend>
                <!-- Matricula -->
                MATRICULA: <br>
                <input type="text" name="matricula" />
                <br>
                <!-- Name -->
                NOMBRES: <br>
                <input type="text" name="name"/>
                <br>
                <!-- Last Name -->
                APELLIDOS: <br>
                <input type="text" name="last_name"/>
                <br>
                <!-- Telephone -->
                TELEFONO: <br>
                <input type="text" name="telephone"/>
                <br><br>
                <!-- Submit -->
                <input type="submit" name="submit" value="ENVIAR">
            </fieldset>
        </form>
        <br><br>
        <form action="http://localhost:4567" method="GET">
            <!-- Cancel -->
            <input type="submit" value="MENU PRINCIPAL">
        </form>
    </div>
</div>
</body>
</html>