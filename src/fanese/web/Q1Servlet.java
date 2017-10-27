package fanese.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Q1Servlet", urlPatterns = {"/questao1"})
public class Q1Servlet extends HttpServlet {
    /**
     * Esse método é chamado uma vez, apenas quando o servlet é iniciado.
     *
     * Aqui, eu defini um atributo "lista", no escopo da aplicação (contexto)
     * sendo o valor um objeto do tipo List vazio.
     */
    public void init() {
        List<String> lista = new ArrayList<>();
        getServletContext().setAttribute("lista", lista);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/q1-form.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtém a lista de strings do escopo da aplicação
        @SuppressWarnings("unchecked")
        List<String> lista = (List<String>) getServletContext().getAttribute("lista");

        //
        // A partir daqui, é com você...
        //

        // Obtém o item enviado como parâmetro
        String item = request.getParameter("item");

        // Adiciona o item à lista, mas apenas se o parâmetro existir
        if (item != null && !item.isEmpty()) {
            lista.add(item);

            // Feedback pro usuário: não é requisito da questão, mas ajuda ao testar
            response.getWriter()
                    .println("Item '" + item + "' adicionado à lista");
        }

        // Feedback negativo: também não é requisito
        else {
            response.sendError(422, "Nenhum item foi enviado");
        }
    }
}
