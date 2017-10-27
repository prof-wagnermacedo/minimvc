<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Linguagens Preferidas</title>
</head>
<body>
    <h1>Estratégia 2</h1>
    <p>Utilizando um único cookie para identificar os itens marcados.</p>

    <c:set var="escolhidas" value="${requestScope.escolhidas}"/>
    <form method="post">
        Linguagens preferidas: <br>
        <input type="checkbox" name="linguagem" value="java"
            ${escolhidas.contains('java')    ? 'checked' : ''}> Java    <br>
        <input type="checkbox" name="linguagem" value="python"
            ${escolhidas.contains('python')  ? 'checked' : ''}> Python  <br>
        <input type="checkbox" name="linguagem" value="ruby"
            ${escolhidas.contains('ruby')    ? 'checked' : ''}> Ruby    <br>
        <input type="checkbox" name="linguagem" value="php"
            ${escolhidas.contains('php')     ? 'checked' : ''}> PHP     <br>
        <input type="checkbox" name="linguagem" value="c"
            ${escolhidas.contains('c')       ? 'checked' : ''}> C       <br>
        <input type="checkbox" name="linguagem" value="haskell"
            ${escolhidas.contains('haskell') ? 'checked' : ''}> Haskell <br>
        <input type="checkbox" name="linguagem" value="julia"
            ${escolhidas.contains('julia')   ? 'checked' : ''}> Julia   <br>
        <br>
        <input type="submit">
    </form>
</body>
</html>
