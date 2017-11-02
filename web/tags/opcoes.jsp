<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:forEach var="tag" items="${tags}">
    <option value="${tag.nome}"></option>
</c:forEach>
