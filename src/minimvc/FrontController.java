package minimvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            String[] cmdParams = request.getParameter(paramName).split(":");
            if (cmdParams.length > 2) {
                throw new IllegalArgumentException(paramName);
            }

            String cmdName = cmdParams[0];
            @SuppressWarnings("unchecked") Class<Command> c = (Class<Command>) Class.forName(commandPackage + "." + cmdName);
            Command command = c.newInstance();
            command.init(request, response);

            String cmdFunction = cmdParams.length == 2 ? cmdParams[1] : "index";
            Method m = c.getMethod(cmdFunction);
            m.invoke(command);
        }
        // Se o comando não existir, retorna HTTP 500
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException
                | NoSuchMethodException | InvocationTargetException e) {
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
