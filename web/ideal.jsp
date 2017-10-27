<jsp:useBean id="pessoa" scope="request" type="fanese.web.Pessoa"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset=utf-8>
</head>
<body>
    <b>${pessoa.nome == null ? '[anônimo]' : pessoa.nome}</b>
    tem <b>${pessoa.idade}</b> anos
    (de ${pessoa.idade < 18 ? 'menor' : 'maior'})
</body>
</html>
