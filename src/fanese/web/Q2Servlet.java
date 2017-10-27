package fanese.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Wagner Macedo
 */
@WebServlet(name = "Q2Servlet", urlPatterns = {"/questao2"})
public class Q2Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtém o parâmetro da query string
        String paramBusca = request.getParameter("busca");

        // Se o parâmetro existir, obtém o cookie "busca" e escreve nele o valor do parâmetro
        if (paramBusca != null) {
            Cookie ckBusca = getCookie(request, "busca");
            ckBusca.setValue(paramBusca);

            // Coloca o novo valor do cookie na resposta
            response.addCookie(ckBusca);
        }

        // Devolve como conteúdo a página JSP
        RequestDispatcher rd = request.getRequestDispatcher("/q2-form.jsp");
        rd.forward(request, response);
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
