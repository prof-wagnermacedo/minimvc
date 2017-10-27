<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String txtNome = request.getParameter("nome");
    String txtIdade = request.getParameter("idade");

    if (txtNome == null) {
        txtNome = "[anônimo]";
    }
    String classeIdade = "menor";
    int idade = Integer.parseInt(txtIdade);
    if (idade >= 18) {
        classeIdade = "maior";
    }
%>
<html>
<head>
    <meta charset=utf-8>
</head>
<body>
    <b><%= txtNome %></b> tem <b><%= idade %></b> anos (de <%= classeIdade %>)
</body>
</html>
