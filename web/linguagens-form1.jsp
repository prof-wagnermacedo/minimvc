<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Linguagens Preferidas</title>
</head>
<body>
    <h1>Estratégia 1</h1>
    <p>Utilizando um cookie para cada item da lista.</p>

    <form method="post">
        Linguagens preferidas: <br>
        <input type="checkbox" name="linguagem" value="java"
            ${cookie['java'].value == 'marcado'    ? 'checked' : ''}> Java    <br>
        <input type="checkbox" name="linguagem" value="python"
            ${cookie['python'].value == 'marcado'  ? 'checked' : ''}> Python  <br>
        <input type="checkbox" name="linguagem" value="ruby"
            ${cookie['ruby'].value == 'marcado'    ? 'checked' : ''}> Ruby    <br>
        <input type="checkbox" name="linguagem" value="php"
            ${cookie['php'].value == 'marcado'     ? 'checked' : ''}> PHP     <br>
        <input type="checkbox" name="linguagem" value="c"
            ${cookie['c'].value == 'marcado'       ? 'checked' : ''}> C       <br>
        <input type="checkbox" name="linguagem" value="haskell"
            ${cookie['haskell'].value == 'marcado' ? 'checked' : ''}> Haskell <br>
        <input type="checkbox" name="linguagem" value="julia"
            ${cookie['julia'].value == 'marcado'   ? 'checked' : ''}> Julia   <br>
        <br>
        <input type="submit">
    </form>
</body>
</html>
