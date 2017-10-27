<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<p>
    Publicado em
    <fmt:formatDate value="${post.horario}" pattern="dd/MM/yyyy HH:mm"/>
</p>
<p>${post.texto}</p>
