<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Mini Blog - ${param['title']}</title>
</head>
<body>
    <h1>Mini Blog</h1>
    <nav>
        <c:url var="control" value="/ctrl?command"/>
        <ul>
            <li><a href="${control}=Posts:index">Lista de Posts</a></li>
            <li><a href="${control}=Posts:publicar">Novo Post</a></li>
            <li><a href="${control}=Posts:index&amp;admin">Modifique Posts</a></li>
        </ul>
    </nav>

    <h2>${param['title']}</h2>
    <jsp:include page="${param['include']}"/>
</body>
</html>
