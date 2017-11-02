package commands;

import fanese.web.dao.PostDao;
import fanese.web.dao.TagDao;
import fanese.web.model.Post;
import fanese.web.model.Tag;
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

        // Verifica se lista como administrador, mostrando link para modificar posts
        boolean isAdmin = getParameter("admin") != null;
        setAttribute("isAdmin", isAdmin);

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

        // Define, se houver, o atributo tag
        Integer tagId = post.getTagId();
        if (tagId != null) {
            TagDao tagDao = new TagDao();

            Tag tag = tagDao.obter(tagId);
            setAttribute("tag", tag);
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

            // Obtém o parâmetro tag
            String tagNome = getParameter("tag");

            // Obtém o id da tag existente ou nova
            Integer tagId = obterOuCriarTag(tagNome);

            // Prepara o objeto para inserção
            Post post = new Post();
            post.setHorario(horario);
            post.setTitulo(titulo);
            post.setTexto(texto);
            post.setTagId(tagId);

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

    public void atualizar() throws ServletException, IOException, ParseException {
        PostDao dao = new PostDao();

        // Obtém e converte id
        int id = Integer.parseInt(getParameter("id"));

        if ("POST".equals(request.getMethod())) {
            // Obtém os outros parâmetros da requisição
            String titulo = getParameter("titulo");
            String texto  = getParameter("texto");

            // Obtém o parâmetro tag
            String tagNome = getParameter("tag");

            // Obtém o id da tag existente ou nova
            Integer tagId = obterOuCriarTag(tagNome);

            // Prepara o objeto de modificação
            Post post = new Post();
            post.setId(id);
            post.setTitulo(titulo);
            post.setTexto(texto);
            post.setTagId(tagId);

            // Define o horário de modificação
            Date agora = new Date();
            post.setModificado(agora);

            // Tenta inserir o post no banco de dados
            if (!dao.modificar(post)) {
                response.sendError(400, "Erro inesperado ao modificar");
                return;
            }

            // Redireciona para a visualização do post modificado
            redirect(contextUrl("/ctrl?command=Posts:visualizar&id=" + id));
        }
        else {
            // Obtém post do banco de dados
            Post post = dao.obter(id);
            setAttribute("post", post);

            // Obtém, se necessário, a tag do banco de dados
            Integer tagId = post.getTagId();
            if (tagId != null) {
                TagDao tagDao = new TagDao();
                setAttribute("tag", tagDao.obter(tagId));
            }

            // Encaminha para a página mestre
            String params = "include=/posts/modificar.jsp&title=Modifique um post";
            forward("/master-page.jsp?" + params);
        }
    }

    private Integer obterOuCriarTag(String tagNome) {
        if (tagNome != null) {
            // Limpa espaços em branco antes de continuar
            tagNome = tagNome.trim();

            // Se o nome da tag não está vazio...
            if (!tagNome.isEmpty()) {
                TagDao tagDao = new TagDao();

                // ...obtém o id da tag
                Tag tag = tagDao.obterPorNome(tagNome);
                if (tag != null) {
                    return tag.getId();
                }

                // Mas se não existe tag, então cria uma nova...
                tag = new Tag();
                tag.setNome(tagNome);

                // ...e utiliza o novo id
                tagDao.adicionar(tag);
                return tag.getId();
            }
        }

        // Se não foi passada uma tag
        return null;
    }

    public void porTag() throws ServletException, IOException {
        PostDao dao = new PostDao();

        // Obtém o parâmetro da requisição
        String tag = getParameter("filtro");
        tag = (tag == null) ? "" : tag.trim();

        // Se não foi passada uma tag, então, obtém todos
        if (tag.isEmpty()) {
            setAttribute("posts", dao.obterTodos());
        }
        // caso contrário, obtém apenas os marcados pela tag
        else {
            setAttribute("posts", dao.obterPorTag(tag));
        }

        // Mostra link para modificar posts se tiver parâmetro admin
        boolean isAdmin = getParameter("admin") != null;
        setAttribute("isAdmin", isAdmin);

        forward("/posts/quadro.jsp");
    }
}
