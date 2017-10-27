package fanese.web.dao;

import fanese.web.model.Dependente;
import fanese.web.model.Funcionario;
import org.sql2o.Connection;

import java.util.List;

public class FuncionarioDao {
    /**
     * Referência da fábrica de conexões
     */
    private static final Database db = Database.getInstance();

    public Funcionario obter(int id) {
        String query =
            "SELECT id, nome, cpf, nascimento, salario " +
            "FROM Funcionarios " +
            "WHERE id = :id";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                .addParameter("id", id)
                .executeAndFetchFirst(Funcionario.class);
        }
    }

    public List<Funcionario> obterTodos() {
        String query =
            "SELECT id, nome, cpf, nascimento, salario " +
            "FROM Funcionarios";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                .executeAndFetch(Funcionario.class);
        }
    }

    public boolean adicionar(Funcionario f) {
        String query =
            "INSERT INTO Funcionarios (nome, cpf, nascimento, salario) " +
            "VALUES (:nome, :cpf, :nascimento, :salario)";

        try (Connection con = db.open()) {
            con.createQuery(query)
                .addParameter("nome", f.getNome())
                .addParameter("cpf", f.getCpf())
                .addParameter("nascimento", f.getNascimento())
                .addParameter("salario", f.getSalario())
                .executeUpdate();

            // Obtém id gerado automaticamente pelo SGBD
            Integer id = con.getKey(Integer.class);
            f.setId(id);

            return con.getResult() > 0;
        }
    }

    public boolean modificar(Funcionario f) {
        String query =
            "UPDATE Funcionarios " +
            "SET nome = :nome, cpf = :cpf, nascimento = :nascimento, salario = :salario " +
            "WHERE id = :id";

        try (Connection con = db.open()) {
            con.createQuery(query)
                .addParameter("id", f.getId())
                .addParameter("nome", f.getNome())
                .addParameter("cpf", f.getCpf())
                .addParameter("nascimento", f.getNascimento())
                .addParameter("salario", f.getSalario())
                .executeUpdate();

            return con.getResult() > 0;
        }
    }

    public boolean excluir(Funcionario f) {
        // Abre transação em vez de conexão normal
        try (Connection con = db.beginTransaction()) {
            // Exclui todos os dependentes do funcionário
            con.createQuery("DELETE Dependentes WHERE funcionario_id = :id")
                .addParameter("id", f.getId())
                .executeUpdate();

            // Depois remove o funcionário
            con.createQuery("DELETE Funcionarios WHERE id = :id")
                .addParameter("id", f.getId())
                .executeUpdate();

            int result = con.getResult();

            // Valida a transação
            con.commit();

            return result > 0;
        }
    }

    public List<Dependente> obterDependentes(Funcionario f) {
        String query =
            "SELECT id, nome, nascimento, funcionario_id " +
            "FROM Dependentes " +
            "WHERE funcionario_id = :id";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                .addParameter("id", f.getId())
                .executeAndFetch(Dependente.class);
        }
    }

    public boolean adicionarDependente(Funcionario f, Dependente dep) {
        // Define o relacionamento
        dep.setFuncionarioId(f.getId());

        // Reaproveita o DAO já existente
        DependenteDao dependenteDao = new DependenteDao();

        return dependenteDao.adicionar(dep);
    }

    public boolean excluirDependente(Funcionario f, Dependente dep) {
        // Verifica o relacionamento
        if (f.getId() != dep.getFuncionarioId()) {
            return false;
        }

        // Reaproveita o DAO já existente
        DependenteDao dependenteDao = new DependenteDao();

        return dependenteDao.excluir(dep);
    }
}
