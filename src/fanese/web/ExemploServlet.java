package fanese.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/problematica")
public class ExemploServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Obtém os dados enviados
        String txtNome = request.getParameter("nome");
        String txtIdade = request.getParameter("idade");
        int idade = Integer.parseInt(txtIdade);
        // Usando o model
        Pessoa p = new Pessoa();
        p.setNome(txtNome);
        p.setIdade(Integer.parseInt(txtIdade));
        // Ligando o model <-> view
        request.setAttribute("pessoa", p);
        // Usando a view
        RequestDispatcher rd = request.getRequestDispatcher("/exemplo-view.jsp");
        rd.forward(request, response);
    }
}
