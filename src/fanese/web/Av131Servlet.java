package fanese.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AV1 Q3 E1", urlPatterns = {"/av131"})
public class Av131Servlet extends HttpServlet {
    /**
     * Constante para auxiliar no processamento
     */
    private static final String[] LINGUAGENS = new String[] {"java", "python", "ruby", "php", "c", "haskell", "julia"};

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/linguagens-form1.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        // Obtém as linguagens escolhidas
        String[] escolhidas = request.getParameterValues("linguagem");

        // Cria um cookie para cada linguagem e define os marcados
        if (escolhidas != null) {
            for (String linguagem : LINGUAGENS) {
                Cookie ck = new Cookie(linguagem, null);

                // Marca o cookie se a linguagem foi escolhida
                if (isEscolhida(escolhidas, linguagem)) {
                    ck.setValue("marcado");
                }
                // Linguagem não foi escolhida: remove o cookie!
                else {
                    ck.setMaxAge(0);
                }

                response.addCookie(ck);
            }

            // Mensagem de sucesso :-)
            response.getWriter().println("Sucesso");
        }

        // Nenhuma opção escolhida, remove todos os cookies de linguagem
        else {
            for (String linguagem : LINGUAGENS) {
                Cookie ck = new Cookie(linguagem, null);
                ck.setMaxAge(0);

                response.addCookie(ck);
            }

            // Mensagem de aviso
            response.getWriter().println("Todos os cookies removidos");
        }
    }

    /**
     * Método auxiliar para identificar se uma opção de linguagem está presente na lista de escolhidas
     */
    private static boolean isEscolhida(String[] escolhidas, String opcao) {
        for (String name : escolhidas) {
            if (name.equals(opcao)) {
                return true;
            }
        }

        return false;
    }
}
