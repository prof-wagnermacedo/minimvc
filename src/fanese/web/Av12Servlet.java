package fanese.web;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebServlet(name = "AV1 Q2", urlPatterns = {"/livro"})
public class Av12Servlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        System.out.printf("Location: '%s'\n", response.getHeader("Location"));

        // Obtém a informação completa de local
        Locale locale = request.getLocale();

        // Obtém um código de idioma
        String idioma = locale.getLanguage();

        // Redireciona para a página específica da Wikipédia dos idiomas
        switch (idioma) {
            case "pt":
                String url = getUrl(request, "/livro/pt");
                response.sendRedirect(url);
                break;
            case "fr":
                url = getUrl(request, "/livro/fr");
                response.sendRedirect(url);
                break;
            default:
                url = getUrl(request, "/livro/en");
                response.sendRedirect(url);
        }

        System.out.printf("Location: '%s'\n", response.getHeader("Location"));
    }

    private static String getUrl(HttpServletRequest request, String relativePath) {
        return request.getContextPath() + relativePath;
    }
}
