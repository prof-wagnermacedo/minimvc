package fanese.web.dao;

import fanese.web.model.Dependente;
import fanese.web.model.Funcionario;
import org.sql2o.Connection;

import java.util.List;

public class DependenteDao {
    /**
     * Referência da fábrica de conexões
     */
    private static final Database db = Database.getInstance();

    public Dependente obter(int id) {
        String query =
            "SELECT id, nome, nascimento, funcionario_id " +
            "FROM Dependentes " +
            "WHERE id = :id";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                .addParameter("id", id)
                .executeAndFetchFirst(Dependente.class);
        }
    }

    public List<Dependente> obterTodos() {
        String query =
            "SELECT id, nome, nascimento, funcionario_id " +
            "FROM Dependentes";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                .executeAndFetch(Dependente.class);
        }
    }

    public boolean adicionar(Dependente dep) {
        String query =
            "INSERT INTO Dependentes (nome, nascimento, funcionario_id) " +
            "VALUES (:nome, :nascimento, :funcionario_id)";

        try (Connection con = db.open()) {
            con.createQuery(query)
                .addParameter("nome", dep.getNome())
                .addParameter("nascimento", dep.getNascimento())
                .executeUpdate();

            // Obtém id gerado automaticamente pelo SGBD
            Integer id = con.getKey(Integer.class);
            dep.setId(id);

            return con.getResult() > 0;
        }
    }

    public boolean modificar(Dependente dep) {
        String query =
            "UPDATE Dependentes " +
            "SET nome = :nome, nascimento = :nascimento " +
            "WHERE id = :id";

        try (Connection con = db.open()) {
            con.createQuery(query)
                .addParameter("id", dep.getId())
                .addParameter("nome", dep.getNome())
                .addParameter("nascimento", dep.getNascimento())
                .executeUpdate();

            return con.getResult() > 0;
        }
    }

    public boolean excluir(Dependente dep) {
        String query =
            "DELETE Dependentes WHERE id = :id";

        try (Connection con = db.open()) {
            con.createQuery(query)
                .addParameter("id", dep.getId())
                .executeUpdate();

            return con.getResult() > 0;
        }
    }

    public Funcionario obterFuncionario(Dependente dep) {
        // Reaproveita o DAO já existente
        FuncionarioDao funcionarioDao = new FuncionarioDao();

        return funcionarioDao.obter(dep.getFuncionarioId());
    }
}
