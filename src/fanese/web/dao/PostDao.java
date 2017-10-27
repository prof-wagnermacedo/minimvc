package fanese.web.dao;

import fanese.web.model.Post;
import org.sql2o.Connection;

import java.util.List;

public class PostDao {
    /**
     * Referência da fábrica de conexões
     */
    private static final Database db = Database.getInstance();

    public Post obter(int id) {
        String query =
            "SELECT id, titulo, horario, texto " +
            "FROM Posts " +
            "WHERE id=:id";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                .addParameter("id", id)
                .executeAndFetchFirst(Post.class);
        }
    }

    public List<Post> obterTodos() {
        String query =
            "SELECT id, titulo, horario, texto " +
            "FROM Posts";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                .executeAndFetch(Post.class);
        }
    }

    public boolean adicionar(Post post) {
        String query =
            "INSERT INTO Posts (titulo, horario, texto) " +
            "VALUES (:titulo, :horario, :texto)";

        try (Connection con = db.open()) {
            con.createQuery(query)
                .addParameter("titulo", post.getTitulo())
                .addParameter("horario", post.getHorario())
                .addParameter("texto", post.getTexto())
                .executeUpdate();

            // Obtém id gerado automaticamente pelo SGBD
            Integer id = con.getKey(Integer.class);
            post.setId(id);

            return con.getResult() > 0;
        }
    }

    public boolean modificar(Post post) {
        String query =
            "UPDATE Posts " +
            "SET titulo = :titulo, horario = :horario, texto = :texto " +
            "WHERE id = :id";

        try (Connection con = db.open()) {
            con.createQuery(query)
                .addParameter("id", post.getId())
                .addParameter("titulo", post.getTitulo())
                .addParameter("horario", post.getHorario())
                .addParameter("texto", post.getTexto())
                .executeUpdate();

            return con.getResult() > 0;
        }
    }

    public boolean excluir(int id) {
        String query =
            "DELETE Posts WHERE id = :id";

        try (Connection con = db.open()) {
            con.createQuery(query)
                .addParameter("id", id)
                .executeUpdate();

            return con.getResult() > 0;
        }
    }
}
