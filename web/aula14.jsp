<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Obtém a informação completa de local --%>
<c:set var="locale" value="${pageContext.request.locale}"/>

<%-- Obtém um código de idioma --%>
<c:set var="idioma" value="${locale.language}"/>

<%-- Redireciona para a página específica da Wikipédia dos idiomas --%>
<c:choose>
    <c:when test="${idioma == 'pt'}">
        <c:redirect url="https://pt.wikipedia.org/" />
    </c:when>
    <c:when test="${idioma == 'en'}">
        <c:redirect url="https://en.wikipedia.org/" />
    </c:when>
    <c:when test="${idioma == 'es'}">
        <c:redirect url="https://es.wikipedia.org/" />
    </c:when>

    <%-- Isto não foi pedido, mas se não for nenhum dos idiomas,
         manda para a página principal da Wikipédia. --%>
    <c:otherwise>
        <c:redirect url="https://www.wikipedia.org/" />
    </c:otherwise>
</c:choose>
