package fanese.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AtividadeServlet", urlPatterns = {"/atividade"})
public class AtividadeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        Cookie ckDoce = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie ck : cookies) {
            if (ck.getName().equals("doce")) {
                ckDoce = ck;
                break;
            }
        }

        if (ckDoce == null) {
            ckDoce = new Cookie("doce", "0.25");
        } else {
            double peso = Double.parseDouble(ckDoce.getValue());
            if (peso > 0.5) {
                peso = peso - 0.5;
            }

            ckDoce.setValue(String.valueOf(peso));
        }

        response.addCookie(ckDoce);
        response.getWriter().println("Peso: " + ckDoce.getValue());
    }
}
