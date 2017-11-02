<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="mensagem"></div>

<form method="post">
    Horário:<br>
        <input type="date" name="horario-date">
        <input type="time" name="horario-time">
    <br><br>
    Título:<br>
        <input type="text" name="titulo">
    <br><br>
    Tag:<br>
        <input type="text" name="tag">
    <br><br>
    Texto:<br>
        <textarea name="texto" id="" cols="30" rows="10"></textarea>
    <br><br>
    <input type="submit">
</form>

<script>
    // Atribui uma ação ao evento de enviar o formulário
    $("form").submit(function (evt) {
        var form = this;

        // Envia os dados do formulário via AJAX
        $.post("<c:url value='/ctrl?command=Posts:publicar&ajax'/>", $(form).serialize())
            // Sucesso: coloca mensagem e reseta formulário
            .done(function () {
                $("#mensagem").html("Post criado com sucesso");
                form.reset();
            })
            // Falha: coloca mensagem
            .fail(function () {
                $("#mensagem").html("Falha ao criar o post");
            });

        // Previne o envio do formulário
        evt.preventDefault();
    });
</script>
