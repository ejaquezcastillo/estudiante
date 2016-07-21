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
        <h1>${message}</h1>
    </div>

    <br>

    <!-- Registry -->
    <div class="box" id="registry">
        <table>
            <thead>
            <tr>
                <th colspan="4">REGISTRO DE ESTUDIANTE</th>
            </tr>
            <tr>
                <th>MATRICULA</th>
                <th>NOMBRES</th>
                <th>APELLIDOS</th>
                <th>TELEFONO</th>
            </tr>
            </thead>
            <tbody>
            <#list Registry?sort_by("matricula") as student>
            <tr>
                <td>${student.getMatricula()}</td>
                <td>${student.getName()}</td>
                <td>${student.getLastName()}</td>
                <td>${student.getTelephone()}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <br>

    <!-- Buttons -->
    <div class="box" id="buttons">
        <form action="http://localhost:4567/new" method="GET">
            <input type="submit" name="submit" value="NUEVO ESTUDIANTE">
        </form>
        <br>
        <form action="http://localhost:4567/modify" method="GET">
            MODIFICAR: <input type="text" name="matricula">
            <input type="submit" name="submit" value="MODIFICAR">
        </form>
        <br>
        <form action="http://localhost:4567/delete" method="POST">
            ELIMINAR: <input type="text" name="matricula">
            <input type="submit" name="submit" value="ELIMINAR">
        </form>
    </div>

    <br>

    <!-- Footer -->
    <div class="box" id="footer">
        <p>Copyright ejaquezcastillo</p>
    </div>
</div>
</body>
</html>