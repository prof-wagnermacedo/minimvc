<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Revisão</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
    <form id="form1" method="post" action="problematica1.jsp">
        Nome: <input type="text" name="nome"><br>
        Idade: <input type="number" name="idade"><br>
        <input type="submit">
    </form>

    <script>
        $("#form1")
            .submit(function (evt) {
                evt.preventDefault();

                var $form = $(this);
                var url = $form.attr("action");
                $.post(url, $form.serialize())
                    .always(function (response) {
                        alert(response);
                    });
            });
    </script>
</body>
</html>
