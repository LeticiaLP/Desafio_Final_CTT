package crud;

import java.util.Scanner;

public class UsuarioPrincipal {

    ConexaoBancoDados connect = new ConexaoBancoDados();
    UsuarioMenu menu = new UsuarioMenu();
    UsuarioDAO dao = new UsuarioDAO();
    Usuario usuario = new Usuario();
    Scanner in = new Scanner(System.in);

    public void construirCriarUsuario() {

        dao.contarCadastroUsuario();
        menu.menuCriarUsuario(usuario);
        dao.criarUsuario(usuario);
    }

    public void construirBuscarUsuario() {

        try {
            do {
                menu.menuBuscarUsuario(usuario);

                switch (menu.getBusca()) {
                    case 1:
                        dao.buscarTodosUsuarios();
                        Thread.sleep(3000);
                        dao.gerarDadosUsuario();
                        break;
                    case 2:
                        dao.buscarUmUsuario(usuario);
                        if (!dao.getSet().isBeforeFirst()) {
                            System.exit(0);
                        }
                        Thread.sleep(3000);
                        dao.gerarDadosUsuario();
                        break;
                    default:
                        System.out.println("\nCódigo desconhecido." +
                                "\nInicie novamente.");
                        Thread.sleep(4000);
                        break;
                }

            } while (menu.getBusca() < 1 || menu.getBusca() > 2);

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void construirAtualizarUsuario() {

        try {
            System.out.println("\n------------------------------------------------------------");
            System.out.printf("%22s%5s\n", "", "BUSCA POR USUÁRIO");
            System.out.println("------------------------------------------------------------\n");
            System.out.print("Nome: ");
            usuario.setNome(in.nextLine());
            dao.buscarUmUsuario(usuario);
            if (!dao.getSet().isBeforeFirst()) {
                System.exit(0);
            }
            Thread.sleep(3000);
            dao.gerarDadosUsuario();
            Thread.sleep(5000);
            menu.menuAtualizarUsuario(usuario);

            if (menu.getAtualiza() == 1) {
                dao.atualizarNomeUsuario(usuario, menu);
            } else if (menu.getAtualiza() == 2) {
                dao.atualizarLoginUsuario(usuario, menu);
            } else if (menu.getAtualiza() == 3) {
                dao.atualizarSenhaUsuario(usuario, menu);
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void construirExcluirUsuario() {
        int confirma;

        try {
            dao.contarCadastroUsuario();
            System.out.println("\n------------------------------------------------------------");
            System.out.printf("%22s%5s\n", "", "BUSCA POR USUÁRIO");
            System.out.println("------------------------------------------------------------\n");
            System.out.print("Nome: ");
            usuario.setNome(in.nextLine());
            dao.buscarUmUsuario(usuario);
            if (!dao.getSet().isBeforeFirst()) {
                System.exit(0);
            }
            Thread.sleep(3000);
            dao.gerarDadosUsuario();
            Thread.sleep(7000);
            System.out.println("\nDigite o nome e a ID do usuário que deseja excluir o cadastro.");
            System.out.print("Nome: ");
            usuario.setNome(in.nextLine());
            System.out.print("ID: ");
            usuario.setId(in.nextInt());
            System.out.println("\nConfirmar a exclusão do usuário " + usuario.getNome() + ", de ID " + usuario.getId() + "?" +
                    "\n1 - Sim" +
                    "\n2 - Não");
            confirma = in.nextInt();
            if (confirma == 1) {
                dao.excluirUsuario(usuario);
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void iniciarUsuario() throws InterruptedException {
        int iniciar;

        connect.conectarBandoDados();

        do {
            do {
                menu.menuUsuario();

                switch (menu.getMenu()) {
                    case 1:
                        construirCriarUsuario();
                        break;
                    case 2:
                        construirBuscarUsuario();
                        break;
                    case 3:
                        construirAtualizarUsuario();
                        break;
                    case 4:
                        construirExcluirUsuario();
                        break;
                    default:
                        System.out.println("\nCódigo desconhecido." +
                                "\nInicie novamente.");
                        Thread.sleep(4000);
                        break;
                }

            } while (menu.getMenu() < 1 || menu.getMenu() > 4);

            connect.desconectarBancoDados();

            System.out.println("\nIniciar operação novamente?" +
                    "\n1 - Sim" +
                    "\n2 - Não");
            iniciar = in.nextInt(); in.nextLine();

        } while (iniciar == 1);

        System.out.println("\nOperação finalizada.");
    }
}
