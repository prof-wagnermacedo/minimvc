package fanese.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "AV1 Q3 E2", urlPatterns = {"/av132"})
public class Av132Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        // Obtém o cookie de linguagens
        Cookie ckLinguagens = getCookie(request, "linguagens");

        // Passa a lista de linguagens presente no cookie para o JSP
        String escolhidas = ckLinguagens.getValue();
        if (escolhidas != null) {
            // Cria um array com a lista de escolhidas
            String[] arrayEscolhidas = escolhidas.split(":");

            // Passando o atributo "escolhidas" no escopo request
            List<String> listaEscolhidas = Arrays.asList(arrayEscolhidas);
            request.setAttribute("escolhidas", listaEscolhidas);
        }

        RequestDispatcher rd = request.getRequestDispatcher("/linguagens-form2.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        // Obtém as linguagens escolhidas
        String[] escolhidas = request.getParameterValues("linguagem");

        // Cria um cookie para as linguagens escolhidas
        if (escolhidas != null) {
            // Cria string única para identificar as linguagens escolhidas
            // ex: "java:python:ruby"
            String chaveEscolhidas = String.join(":", escolhidas);

            // Escreve a string única no cookie
            Cookie ck = new Cookie("linguagens", chaveEscolhidas);
            response.addCookie(ck);

            // Mensagem de sucesso :-)
            response.getWriter().println("Sucesso");
        }

        // Nenhuma opção escolhida, remove o cookie de linguagens
        else {
            Cookie ck = new Cookie("linguagens", null);
            ck.setMaxAge(0);
            response.addCookie(ck);

            // Mensagem de aviso
            response.getWriter().println("Cookie 'linguagens' removido");
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
