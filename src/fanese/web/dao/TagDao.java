package fanese.web.dao;

import fanese.web.model.Tag;
import org.sql2o.Connection;

import java.util.List;

public class TagDao {
    /**
     * Referência da fábrica de conexões
     */
    private static final Database db = Database.getInstance();

    public Tag obter(int id) {
        String query =
            "SELECT id, nome " +
            "FROM Tags " +
            "WHERE id=:id";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                .addParameter("id", id)
                .executeAndFetchFirst(Tag.class);
        }
    }

    public List<Tag> obterTodos() {
        String query =
            "SELECT id, nome " +
            "FROM Tags";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                .executeAndFetch(Tag.class);
        }
    }

    public boolean adicionar(Tag tag) {
        String query =
            "INSERT INTO Tags (nome) " +
            "VALUES (:nome)";

        try (Connection con = db.open()) {
            con.createQuery(query)
                .addParameter("nome", tag.getNome())
                .executeUpdate();

            // Obtém id gerado automaticamente pelo SGBD
            Integer id = con.getKey(Integer.class);
            tag.setId(id);

            return con.getResult() > 0;
        }
    }
}
