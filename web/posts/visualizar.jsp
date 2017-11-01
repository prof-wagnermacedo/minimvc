<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<p>
    Publicado em
    <fmt:formatDate value="${post.horario}" pattern="dd/MM/yyyy HH:mm"/>
    <c:if test="${post.modificado != null}">
        <ins>(modificado em <fmt:formatDate value="${post.modificado}" pattern="dd/MM/yyyy HH:mm"/>)</ins>
    </c:if>
</p>
<p>
    <c:choose>
        <c:when test="${tag == null}">
            Sem marcação de tag
        </c:when>
        <c:otherwise>
            Tag: ${tag.nome}
        </c:otherwise>
    </c:choose>
</p>
<p>${post.texto}</p>
