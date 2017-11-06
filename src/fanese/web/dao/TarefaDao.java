package fanese.web.dao;

import fanese.web.model.Tarefa;
import org.sql2o.Connection;

import java.util.List;

/**
 * @author Wagner Macedo
 */
public class TarefaDao {
    /**
     * Referência da fábrica de conexões
     */
    private static final Database db = Database.getInstance();

    public List<Tarefa> obterLista() {
        String query =
            "SELECT id, descricao, concluida " +
            "FROM Tarefas " +
            "ORDER BY concluida";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                .executeAndFetch(Tarefa.class);
        }
    }

    public boolean adicionar(Tarefa tarefa) {
        String query =
            "INSERT INTO Tarefas (descricao) " +
            "VALUES (:descricao)";

        try (Connection con = db.open()) {
            con.createQuery(query)
                .addParameter("descricao", tarefa.getDescricao())
                .executeUpdate();

            // Obtém id gerado automaticamente pelo SGBD
            Integer id = con.getKey(Integer.class);
            tarefa.setId(id);

            return con.getResult() > 0;
        }
    }

    public boolean concluir(int tarefaId) {
        String query =
            "UPDATE Tarefas " +
            "SET concluida = 1" +
            "WHERE id = :id";

        try (Connection con = db.open()) {
            con.createQuery(query)
                .addParameter("id", tarefaId)
                .executeUpdate();

            return con.getResult() > 0;
        }
    }

    public boolean excluir(int id) {
        String query =
            "DELETE Tarefas WHERE id = :id";

        try (Connection con = db.open()) {
            con.createQuery(query)
                .addParameter("id", id)
                .executeUpdate();

            return con.getResult() > 0;
        }
    }
}
