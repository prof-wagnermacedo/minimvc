<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url var="control" value="/ctrl?command"/>

<form>
    Filtrar por tag: <input name="filtro" list="tags">
    <datalist id="tags"></datalist>
    <button type="submit">Buscar</button>
</form>

<script>
    $(function () {
        $("input[name='filtro']").keyup(function () {
            $.get("${control}=Tags:opcoes", {filtro: this.value})
                .done(function (response) {
                    $("#tags").html(response);
                });
        });
    });
</script>

<c:if test="${isAdmin}">
    <p>Modifique um post clicando em 📝</p>
</c:if>

<c:forEach var="p" items="${posts}">
    <p>
        <fmt:formatDate value="${p.horario}" pattern="dd/MM/yyyy HH:mm"/>
        <c:if test="${isAdmin}">
            <a href="${control}=Posts:atualizar&amp;id=${p.id}">📝</a>
        </c:if>
        <br>
        <a href="${control}=Posts:visualizar&amp;id=${p.id}">${p.titulo}</a>
    </p>
</c:forEach>
