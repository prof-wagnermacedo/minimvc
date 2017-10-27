package fanese.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "R1.1 Q2", urlPatterns = {"/revisao112"})
public class Revisao112Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/revisao112.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        // Obtém o parâmetro do formulário
        String paramGosto = request.getParameter("gosto");

        // Devolve resposta com um cookie se houver parâmetro
        if (paramGosto != null) {
            Cookie ckGosto = getCookie(request, "gosto");
            ckGosto.setValue(paramGosto);

            // Código para adicionar cookie na resposta
            response.addCookie(ckGosto);

            // Mensagem de sucesso :-)
            response.getWriter().println("Sucesso");
        }

        // Mensagem de erro se não houver parâmetro
        else {
            response.sendError(422, "Não foi passado o parâmetro 'gosto'");
        }
    }

    /**
     * Obtém um cookie da requisição ou cria um novo
     */
    private static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie ck : cookies) {
                if (name.equals(ck.getName())) {
                    return ck;
                }
            }
        }

        return new Cookie(name, null);
    }
}
