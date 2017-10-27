package fanese.web.dao;

import org.sql2o.Sql2o;

public class Database extends Sql2o {

    /**
     * Constantes das informações de conexão ao SGBD
     */
    private static final String
            JDBC_URL = "jdbc:sqlserver://localhost;databaseName=UN3",
            USER = "sa",
            PASSWORD = "123456";

    //<editor-fold desc="Instância única da classe (padrão singleton)">
    private Database() {
        super(JDBC_URL, USER, PASSWORD);

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Database INSTANCE = new Database();

    public static Database getInstance() {
        return INSTANCE;
    }
    //</editor-fold>
}
