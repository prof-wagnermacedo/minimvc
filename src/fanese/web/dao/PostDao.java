package fanese.web.dao;

import fanese.web.model.Post;
import fanese.web.model.Tag;
import org.sql2o.Connection;

import java.util.Collections;
import java.util.List;

public class PostDao {
    /**
     * Referência da fábrica de conexões
     */
    private static final Database db = Database.getInstance();

    public Post obter(int id) {
        String query =
            "SELECT id, titulo, horario, texto, modificado, tag_id " +
            "FROM Posts " +
            "WHERE id=:id";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                .addColumnMapping("tag_id", "tagId")
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

    public List<Post> obterVisiveis() {
        String query =
            "SELECT id, titulo, horario, texto " +
            "FROM Posts " +
            "WHERE horario <= GETDATE()";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                .executeAndFetch(Post.class);
        }
    }

    public List<Post> obterPorTag(String nome) {
        String query =
            "SELECT p.id, p.titulo, p.horario, p.texto " +
            "FROM Posts p " +
            "JOIN Tags t ON p.tag_id = t.id " +
            "WHERE p.horario < GETDATE() and t.nome = :nome";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                .addParameter("nome", nome)
                .executeAndFetch(Post.class);
        }
    }

    public boolean adicionar(Post post) {
        String query =
            "INSERT INTO Posts (titulo, horario, texto, tag_id) " +
            "VALUES (:titulo, :horario, :texto, :tag)";

        try (Connection con = db.open()) {
            con.createQuery(query)
                .addColumnMapping("tag_id", "tagId")
                .addParameter("titulo", post.getTitulo())
                .addParameter("horario", post.getHorario())
                .addParameter("texto", post.getTexto())
                .addParameter("tag", post.getTagId())
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
            "SET titulo = :titulo, texto = :texto, modificado = :modificado, tag_id = :tag " +
            "WHERE id = :id";

        try (Connection con = db.open()) {
            con.createQuery(query)
                .addColumnMapping("tag_id", "tagId")
                .addParameter("id", post.getId())
                .addParameter("titulo", post.getTitulo())
                .addParameter("texto", post.getTexto())
                .addParameter("modificado", post.getModificado())
                .addParameter("tag", post.getTagId())
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
