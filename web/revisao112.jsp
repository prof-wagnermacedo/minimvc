<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Revisão 1.1 - Questão 2</title>
</head>
<body>
    <h1>Revisão 1.1 - Questão 2</h1>

    <c:set var="gosto" value="${cookie['gosto'].value}"/>
    <form method="post">
        Do que você mais gosta? <br>
        <input type="radio" name="gosto" value="cinema" ${gosto == 'cinema' ? 'checked' : ''}> Cinema <br>
        <input type="radio" name="gosto" value="livros" ${gosto == 'livros' ? 'checked' : ''}> Livros <br>
        <input type="radio" name="gosto" value="musica" ${gosto == 'musica' ? 'checked' : ''}> Música <br>
        <br>
        <input type="submit">
    </form>
</body>
</html>
