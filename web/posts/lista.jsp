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

        $("form").submit(function (evt) {
            // Faz a filtragem por AJAX
            $.get("${control}=Posts:porTag", $(this).serialize())
                .done(function (response) {
                    $("#posts").html(response);
                });

            // Previne o envio do formulário
            evt.preventDefault();
        });
    });
</script>

<c:if test="${isAdmin}">
    <p>Modifique um post clicando em 📝</p>
</c:if>

<div id="posts">
    <%@ include file="quadro.jsp" %>
</div>
