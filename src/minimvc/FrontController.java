package minimvc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class FrontController extends HttpServlet {
    private String commandPackage;
    private String paramName;
    private Authorization authorization;

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

        try {
            // Tenta abrir o arquivo de configurações de autorização
            InputStream authConfig = getServletContext().getResourceAsStream("/WEB-INF/authorization.xml");
            if (authConfig == null) {
                return;
            }

            // Leitura das configurações de autorização
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(authConfig);

            // Criação de estrutura de busca de permissões
            authorization = new Authorization();
            NodeList permissions = doc.getElementsByTagName("perm");

            for (int i = 0; i < permissions.getLength(); i++) {
                Element perm = (Element) permissions.item(i);

                String[] commands = perm.getTextContent().trim().split("\\s+");
                String[] groups = perm.getAttribute("name").trim().split("[\\s,]+");

                authorization.add(commands, groups);
            }
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ServletException(e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Executa um comando conforme a URL
        String cmdName = null;
        String cmdFunction = null;
        try {
            // Verifica o parâmetro do comando
            String fullCommand = request.getParameter(paramName);
            if (fullCommand == null) {
                response.sendError(422, "Comando não informado");
                return;
            }
            if (!isCommand(fullCommand)) {
                response.sendError(422, "Sintaxe de comando inválida: '" + fullCommand + "'");
                return;
            }

            // Obtém as partes do comando
            String[] cmdParams = fullCommand.split(":");
            cmdName = cmdParams[0];
            cmdFunction = cmdParams.length == 2 ? cmdParams[1] : "index";

            // Processo de autorização
            Collection groups = (Collection) request.getSession().getAttribute("groups");
            if (!authorization.isAuthorized(groups, cmdName, cmdFunction)) {
                response.sendError(403, "Usuário não autorizado");
                return;
            }

            // Inicia a instância do comando
            @SuppressWarnings("unchecked") Class<Command> c = (Class<Command>) Class.forName(commandPackage + "." + cmdName);
            Command command = c.newInstance();
            command.init(request, response);

            // Realiza a chamada do método do comando
            Method m = c.getMethod(cmdFunction);
            m.invoke(command);
        }
        // Se o comando ou método não existir, retorna HTTP 500
        catch (ClassNotFoundException e) {
            response.sendError(404, "Comando não encontrado: '" + cmdName + "'");
        } catch (IllegalAccessException | InstantiationException e) {
            response.sendError(404, "Falha ao iniciar comando: '" + cmdFunction + "'");
        } catch (NoSuchMethodException e) {
            response.sendError(404, "Método não encontrado: '" + cmdFunction + "'");
        } catch (InvocationTargetException e) {
            throw new ServletException(e.getCause());
        }
    }

    private static final Pattern COMMAND_RE = Pattern.compile("^[a-z_][a-z0-9_]*(?::[a-z_][a-z0-9_]*)?$", CASE_INSENSITIVE);

    private static boolean isCommand(String fullCommand) {
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

    private static class Authorization {
        final Map<String, Set<String>> permissions = new HashMap<>();

        void add(String[] commands, String[] groups) {
            for (String cmd : commands) {
                permissions.computeIfAbsent(cmd, k -> new HashSet<>())
                    .addAll(Arrays.asList(groups));
            }
        }

        public boolean isAuthorized(Collection userGroups, String cmdName, String cmdFunction) {
            if (userGroups == null) {
                userGroups = Collections.emptySet();
            }

            // Verifica se existe uma regra para o comando inteiro...
            Set<String> groups = permissions.get(cmdName);
            if (groups == null) {
                // ...não tendo, verifica se existe a regra "cmd:function"
                groups = permissions.get(cmdName + ":" + cmdFunction);

                // Não existindo nenhuma regra, está liberado.
                if (groups == null) {
                    return true;
                }
            }

            // Verifica se o comando atual está liberado para um dos grupos do usuário
            return !Collections.disjoint(groups, userGroups);
        }
    }
}
