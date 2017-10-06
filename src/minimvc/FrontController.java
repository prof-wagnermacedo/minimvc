package minimvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

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
            // Verifica se o parâmetro é um comando
            String fullCommand = request.getParameter(paramName);
            if (!isCommand(fullCommand)) {
                response.sendError(422, "Sintaxe de comando inválida");
            }

            // Obtém as partes do comando
            String[] cmdParams = fullCommand.split(":");

            // Inicia a instância do comando
            String cmdName = cmdParams[0];
            @SuppressWarnings("unchecked") Class<Command> c = (Class<Command>) Class.forName(commandPackage + "." + cmdName);
            Command command = c.newInstance();
            command.init(request, response);

            // Realiza a chamada do método do comando
            String cmdFunction = cmdParams.length == 2 ? cmdParams[1] : "index";
            Method m = c.getMethod(cmdFunction);
            m.invoke(command);
        }
        // Se o comando ou método não existir, retorna HTTP 500
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException
                | NoSuchMethodException | InvocationTargetException e) {
            throw new ServletException(e);
        }
    }

    private static final Pattern COMMAND_RE = Pattern.compile("^[a-z_][a-z0-9_]*(?::[a-z_][a-z0-9_]*)?$");

    private boolean isCommand(String fullCommand) {
        return COMMAND_RE.matcher(fullCommand).matches();
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
