<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Questão 1</title>
</head>
<body>

<%-- Formulário de cadastro --%>
<h1>Questão 1</h1>
<form method="post">
    <fieldset>
        <legend>Cadastro de Itens</legend>
        Item: <br>
        <input type="text" name="item">
        <br><br>
        <input type="submit">
    </fieldset>
</form>

<%-- OPCIONAL: declara o tipo da variável --%>
<jsp:useBean id="lista" scope="application" type="java.util.List"/>

<%-- Tabela de itens --%>
<h2>Lista de Itens</h2>
<table border="1">
    <c:forEach var="valor" items="${lista}">
        <tr>
            <td>${valor}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
