<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="nome" value="${param['nome']}"/>
<c:set var="idade" value="${param['idade']}"/>

<c:if test="${nome == null}">
    <c:set var="nome" value="[anônimo]"/>
</c:if>

<html>
<head>
    <meta charset=utf-8>
</head>
<body>
    <b>${empty nome ? '[anônimo]' : nome}</b> tem <b>${idade}</b> anos
    (de ${idade < 18 ? 'menor' : 'maior'})
</body>
</html>
