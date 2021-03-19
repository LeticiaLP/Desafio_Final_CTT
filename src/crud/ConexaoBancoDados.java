package crud;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoBancoDados {
    private final String url = "jdbc:mysql://localhost/dados";
    private final String user = "root";
    private final String password = "1234";

    private Connection connection;

    public Connection conectarBandoDados() {

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());

        }

        return connection;
    }

    public void desconectarBancoDados() {

        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ConexaoBancoDados connect = new ConexaoBancoDados();

        if (connect.conectarBandoDados() != null) {
            System.out.println("Conectado!");
            connect.desconectarBancoDados();
        }
    }

}
