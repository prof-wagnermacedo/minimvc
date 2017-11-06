<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="comando" value="/ctrl?command=Tarefas"/>

<html>
<head>
    <title>Lista de Tarefas</title>
    <style>
        #tarefas {
            list-style: none;
            padding-left: 0;
        }

        .concluida-true {
            color: gray;
            text-decoration: line-through;
        }
    </style>
</head>
<body>
    <h1>Lista de Tarefas</h1>

    <form action="${comando}:criar" method="post">
        <input type="text" name="descricao" placeholder="Escreva uma tarefa">
        <button type="submit">Nova</button>
    </form>

    <form method="post">
        <ul id="tarefas">
        <c:forEach var="tarefa" items="${tarefas}">
            <li class="concluida-${tarefa.concluida}">
                <label>
                    <input type="radio" name="tarefa" value="${tarefa.id}">
                    ${tarefa.descricao}
                </label>
            </li>
        </c:forEach>
        </ul>

        <button type="submit" formaction="${comando}:completar">Completar</button>
        <button type="submit" formaction="${comando}:excluir">Excluir</button>
    </form>
</body>
</html>
