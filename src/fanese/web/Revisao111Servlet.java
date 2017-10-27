package fanese.web;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebServlet(name = "R1.1 Q1", urlPatterns = {"/revisao111"})
public class Revisao111Servlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        // Obtém a informação completa de local
        Locale locale = request.getLocale();

        // Obtém um código de idioma
        String idioma = locale.getLanguage();

        // Redireciona para a página específica da Wikipédia dos idiomas
        switch (idioma) {
            case "pt":
                response.sendRedirect("https://pt.wikipedia.org/");
                break;
            case "en":
                response.sendRedirect("https://en.wikipedia.org/");
                break;
            case "es":
                response.sendRedirect("https://es.wikipedia.org/");
                break;

            // Isto não foi pedido, mas se não for nenhum dos idiomas,
            // manda para a página principal da Wikipédia.
            default:
                response.sendRedirect("https://www.wikipedia.org/");
        }
    }
}
