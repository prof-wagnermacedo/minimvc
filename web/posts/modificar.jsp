<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form method="post">
    <input type="hidden" name="id" value="${post.id}">
    Horário:<br>
        <input type="text" disabled
               value="<fmt:formatDate value='${post.horario}' pattern='dd/MM/yyyy HH:mm'/>">
    <br><br>
    Título:<br>
        <input type="text" name="titulo" value="${post.titulo}">
    <br><br>
    Tag:<br>
        <input type="text" name="tag" value="${tag.nome}">
    <br><br>
    Texto:<br>
        <textarea name="texto" id="" cols="30" rows="10">${post.texto}</textarea>
    <br><br>
    <input type="submit">
</form>
