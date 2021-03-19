package crud;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {
    private int registro;
    private String nomeAtual;
    private String loginAtual;
    private String senhaAtual;
    private boolean usuarioAdicionado;
    private boolean usuarioExcluido;

    private final int V = 1;
    private int[] registroAnterior;

    private PreparedStatement prepare;
    private ResultSet set;
    private CallableStatement call;

    ConexaoBancoDados connect = new ConexaoBancoDados();

    public int getRegistro() {
        return registro;
    }
    public String getNomeAtual() {
        return nomeAtual;
    }
    public String getLoginAtual() {
        return loginAtual;
    }
    public String getSenhaAtual() {
        return senhaAtual;
    }
    public boolean isUsuarioAdicionado() {
        return usuarioAdicionado;
    }
    public boolean isUsuarioExcluido() {
        return usuarioExcluido;
    }
    public ResultSet getSet() {
        return set;
    }

    public void contarCadastroUsuario() {

        try {
            call = connect.conectarBandoDados().prepareCall("{call sp_numeroUsuario(?)}");

            call.registerOutParameter(1, java.sql.Types.INTEGER);

            call.executeQuery();

            registro = call.getInt(1);

            call.close();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void criarUsuario(Usuario usuario) {
        registroAnterior = new int[V];
        registroAnterior[0] = registro;

        try {
            prepare = connect.conectarBandoDados().prepareStatement("INSERT INTO USUARIO (nome, login, senha, data_cadastro) VALUES (?, ?, ?, ?)");

            prepare.setString(1, usuario.getNome());
            prepare.setString(2, usuario.getLogin());
            prepare.setString(3, usuario.getSenha());
            prepare.setString(4, usuario.getDataCadastro());

            prepare.execute();

            contarCadastroUsuario();

            usuarioAdicionado = registroAnterior[0] < registro;

            if (usuarioAdicionado) {
                System.out.print("\nUsuário cadastrado em " + usuario.getDataCadastro() + ".");
            } else {
                System.out.print("\nCadastro não realizado");
            }

            prepare.close();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void buscarTodosUsuarios() {

        try {
            prepare = connect.conectarBandoDados().prepareStatement("SELECT * FROM USUARIO");
            prepare.execute();

            set = prepare.getResultSet();

            if (set.isBeforeFirst()) {
                System.out.println("\nBusca realizada com sucesso.");
            } else {
                System.out.println("\nBusca não realizada.");
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void buscarUmUsuario(Usuario usuario) {

        try {

            prepare = connect.conectarBandoDados().prepareStatement("SELECT * FROM USUARIO WHERE NOME = (?)");
            prepare.setString(1, usuario.getNome());
            prepare.execute();

            set = prepare.getResultSet();

            if (set.isBeforeFirst()) {
                System.out.println("\nBusca realizada com sucesso.");
            } else {
                System.out.println("\nO usuário não está cadastrado no sistema.");
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void gerarDadosUsuario() {

        try {

            System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%67s%5s\n", "","DADOS DOS USUÁRIOS");
            System.out.println("============================================================================================================================================================");
            System.out.printf("%-3s%-25s%-50s%-39s%-20s%-20s\n", "", "ID", "NOME", "LOGIN", "SENHA", "DATA DE CADASTRO");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
            while (set.next()) {
                System.out.printf("%-2s%-8s%-51s%-50s%-29s%-20s\n", "", set.getInt("idusuario"), set.getString("nome"),
                        set.getString("login"), set.getString("senha"), set.getString("data_cadastro"));
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
            }

            prepare.close();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void atualizarNomeUsuario(Usuario usuario, UsuarioMenu menu) {

        try {
            call = connect.conectarBandoDados().prepareCall("{call sp_atualizaNomeUsuario(?, ?)}");

            call.setString(1, menu.getNovoNome());
            call.setInt(2, usuario.getId());

            switch (menu.getConfirmaAlteracao()) {
                case 1:
                    call.execute();

                    prepare = connect.conectarBandoDados().prepareStatement("SELECT * FROM USUARIO WHERE NOME = (?) AND IDUSUARIO = (?)");
                    prepare.setString(1, menu.getNovoNome());
                    prepare.setInt(2, usuario.getId());
                    prepare.execute();
                    set = prepare.getResultSet();
                    if (set.next()) {
                        nomeAtual = set.getString("nome");
                    }

                    try {
                        if (nomeAtual.equals(menu.getNovoNome())) {
                            System.out.println("\nNome atualizado com sucesso.");
                        }
                    } catch (NullPointerException e){
                        System.out.println("\nO nome não foi atualizado.");
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println("\nOperação finalizada.");
                    break;
            }

            call.close();
            prepare.close();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void atualizarLoginUsuario(Usuario usuario, UsuarioMenu menu) {

        try {
            call = connect.conectarBandoDados().prepareCall("{call sp_atualizaLoginUsuario(?, ?)}");

            call.setString(1, menu.getNovoLogin());
            call.setInt(2, usuario.getId());

            switch (menu.getConfirmaAlteracao()) {
                case 1:
                    call.execute();

                    prepare = connect.conectarBandoDados().prepareStatement("SELECT * FROM USUARIO WHERE LOGIN = (?) AND IDUSUARIO = (?)");
                    prepare.setString(1, menu.getNovoLogin());
                    prepare.setInt(2, usuario.getId());
                    prepare.execute();
                    set = prepare.getResultSet();
                    if (set.next()) {
                        loginAtual = set.getString("login");
                    }

                    try {
                        if (loginAtual.equals(menu.getNovoLogin())) {
                            System.out.println("\nLogin alterado com sucesso.");
                        }
                    } catch (NullPointerException e) {
                        System.out.println("\nO login não foi atualizado.");
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println("\nOperação finalizada.");
                    break;
            }

            call.close();
            prepare.close();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void atualizarSenhaUsuario(Usuario usuario, UsuarioMenu menu) {

        try {
            call = connect.conectarBandoDados().prepareCall("{call sp_atualizaSenhaUsuario(?, ?)}");

            call.setString(1, menu.getNovaSenha());
            call.setInt(2, usuario.getId());

            switch (menu.getConfirmaAlteracao()) {
                case 1:
                    call.execute();

                    prepare = connect.conectarBandoDados().prepareStatement("SELECT * FROM USUARIO WHERE SENHA = (?) AND IDUSUARIO = (?)");
                    prepare.setString(1, menu.getNovaSenha());
                    prepare.setInt(2, usuario.getId());
                    prepare.execute();
                    set = prepare.getResultSet();
                    if (set.next()) {
                        senhaAtual = set.getString("senha");
                    }

                    try {
                        if (senhaAtual.equals(menu.getNovaSenha())) {
                            System.out.println("\nSenha alterada com sucesso.");
                        }
                    } catch (NullPointerException e) {
                        System.out.println("\nA senha não foi atualizada.");
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println("\nAs senhas digitadas são diferentes." +
                            "\nOperação finalizada.");
                    break;
            }

            call.close();
            prepare.close();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void excluirUsuario(Usuario usuario) {
        registroAnterior = new int[V];
        registroAnterior[0] = registro;

        try {
            prepare = connect.conectarBandoDados().prepareStatement("DELETE FROM USUARIO WHERE NOME = (?) AND IDUSUARIO = (?)");

            prepare.setString(1, usuario.getNome());
            prepare.setInt(2, usuario.getId());

            prepare.execute();

            contarCadastroUsuario();

            usuarioExcluido = registroAnterior[0] > registro;

            if (usuarioExcluido) {
                System.out.println("\nO usuário teve o seu cadastro excluído com sucesso.");
            } else {
                System.out.println("\nO usuário não teve o seu cadastro excluído.");
            }

            prepare.close();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
