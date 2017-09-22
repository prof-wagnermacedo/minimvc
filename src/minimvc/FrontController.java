package minimvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {
    private String commandPackage;
    private String paramName;

    @Override
    public void init() throws ServletException {
        commandPackage = getInitParameter("commandPackage");
        if (commandPackage == null) {
            commandPackage = "commands";
        }

        paramName = getInitParameter("paramName");
        if (paramName == null) {
            paramName = "command";
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Executa um comando conforme a URL
        try {
            String commandName = request.getParameter(paramName);

            @SuppressWarnings("unchecked")
            Class<Command> c = (Class<Command>) Class.forName(commandPackage + "." + commandName);
            Command command = c.newInstance();

            command.execute(request, response);
        }
        // Se o comando não existir, retorna HTTP 500
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
