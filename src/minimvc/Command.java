package minimvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Command {
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    /**
     * Método onde deve ser implementado a lógica do comando
     */
    protected abstract void execute() throws ServletException, IOException;

    /**
     * Método interno usado por FrontController
     */
    final void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        this.response = response;

        execute();
    }

    // Métodos utilitários

    protected final void forward(String path) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }

    protected final void redirect(String location) throws IOException {
        response.sendRedirect(location);
    }

    protected final String contextUrl(String relativePath) {
        if (relativePath == null) {
            relativePath = "";
        }
        return request.getContextPath() + relativePath;
    }

    protected final String contextUrl() {
        return contextUrl(null);
    }

    protected final Cookie getCookie(String name) {
        Cookie[] cookies = request.getCookies();

        for (Cookie ck : cookies) {
            if (ck.getName().equals(name)) {
                return ck;
            }
        }

        return null;
    }
}
