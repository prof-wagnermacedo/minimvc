package commands;

import fanese.web.dao.PostDao;
import fanese.web.model.Post;
import minimvc.Command;

import javax.servlet.ServletException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Posts extends Command {
    public void index() throws ServletException, IOException {
        PostDao dao = new PostDao();

        // Obtém lista de posts do banco de dados
        List<Post> posts = dao.obterVisiveis();
        setAttribute("posts", posts);

        // Encaminha para a página mestre
        String params = "include=/posts/lista.jsp&title=Lista de Posts";
        forward("/master-page.jsp?" + params);
    }

    public void visualizar() throws ServletException, IOException {
        PostDao dao = new PostDao();

        // Obtém parâmetro da requisição
        String paramId = getParameter("id");
        int id = Integer.parseInt(paramId);

        // Obtém post do banco de dados
        Post post = dao.obter(id);

        // Verifica se o post está apto a ser visualizado
        Date agora = new Date();
        if (agora.before(post.getHorario())) {
            response.sendError(404, "Página não existe");
            return;
        }

        // Define o atributo da camada view
        setAttribute("post", post);

        // Encaminha para a página mestre
        String params = "include=/posts/visualizar.jsp&title=" + post.getTitulo();
        forward("/master-page.jsp?" + params);
    }

    public void publicar() throws ServletException, IOException, ParseException {
        if ("POST".equals(request.getMethod())) {
            PostDao dao = new PostDao();

            // Obtém parâmetros do horário
            String horarioDate = getParameter("horario-date");
            String horarioTime = getParameter("horario-time");

            // Converte o horário para o formato apropriado
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddH:m");
            Date horario = sdf.parse(horarioDate + horarioTime);

            // Obtém os outros parâmetros da requisição
            String titulo = getParameter("titulo");
            String texto  = getParameter("texto");

            // Prepara o objeto para inserção
            Post post = new Post();
            post.setHorario(horario);
            post.setTitulo(titulo);
            post.setTexto(texto);

            // Tenta inserir o post no banco de dados
            if (!dao.adicionar(post)) {
                response.sendError(400, "Erro inesperado ao inserir");
                return;
            }

            // Redireciona para a lista de posts
            redirect(contextUrl("/ctrl?command=Posts:index"));
        }
        else {
            // Encaminha para a página mestre
            String params = "include=/posts/adicionar.jsp&title=Publique um post";
            forward("/master-page.jsp?" + params);
        }
    }
}
