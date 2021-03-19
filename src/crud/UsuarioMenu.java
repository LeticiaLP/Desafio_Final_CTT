package crud;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class UsuarioMenu {
    private int menu;
    private int busca;
    private int atualiza;
    private String novoNome;
    private String novoLogin;
    private String novaSenha;
    private int confirmaAlteracao;

    Scanner in = new Scanner(System.in);

    public int getMenu() {
        return menu;
    }
    public int getBusca() {
        return busca;
    }
    public int getAtualiza() {
        return atualiza;
    }
    public String getNovoNome() {
        return novoNome;
    }
    public void setNovoNome(String novoNome) {
        this.novoNome = novoNome;
    }
    public String getNovoLogin() {
        return novoLogin;
    }
    public void setNovoLogin(String novoLogin) {
        this.novoLogin = novoLogin;
    }
    public String getNovaSenha() {
        return novaSenha;
    }
    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
    public int getConfirmaAlteracao() {
        return confirmaAlteracao;
    }
    public void setConfirmaAlteracao(int confirmaAlteracao) {
        this.confirmaAlteracao = confirmaAlteracao;
    }

    public void menuUsuario() {
        System.out.println("\n------------------------------------------------------------");
        System.out.printf("%20s%5s\n", "", "SISTEMA DE USUÁRIOS");
        System.out.println("------------------------------------------------------------\n");
        System.out.println("Qual atividade deseja executar?" +
                "\n1 - Cadastrar novo usuário" +
                "\n2 - Buscar informações do usuário" +
                "\n3 - Atualizar informações do usuário" +
                "\n4 - Excluir cadastro do usuário");
        menu = in.nextInt(); in.nextLine();
    }

    public void menuCriarUsuario(Usuario usuario) {
        Calendar calendario = Calendar.getInstance();
        DateFormat data = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("\n------------------------------------------------------------");
        System.out.printf("%20s%5s\n", "", "CADASTRO DE USUÁRIOS");
        System.out.println("------------------------------------------------------------\n");
        System.out.print("Nome: ");
        usuario.setNome(in.nextLine());
        System.out.print("Login (e-mail): ");
        usuario.setLogin(in.nextLine());
        System.out.print("Senha: ");
        usuario.setSenha(in.nextLine());

        usuario.setDataCadastro(data.format(calendario.getTime()));
    }

    public void menuBuscarUsuario(Usuario usuario) {
        System.out.println("\n------------------------------------------------------------");
        System.out.printf("%22s%5s\n", "","BUSCA POR USUÁRIOS");
        System.out.println("------------------------------------------------------------");
        System.out.println("\nQual informação deseja obter dos usuários?" +
                "\n1 - Todos os dados dos usuários" +
                "\n2 - Todos os dados de um usuário");
        busca = in.nextInt(); in.nextLine();

        if (busca == 2) {
            System.out.print("\nNome: ");
            usuario.setNome(in.nextLine());
        }
    }

    public void menuAtualizarUsuario(Usuario usuario) {
        String novaSenha2;

        System.out.println("\n------------------------------------------------------------");
        System.out.printf("%16s%5s\n", "","ATUALIZAR DADOS DOS USUÁRIOS");
        System.out.println("------------------------------------------------------------");

        do {
            System.out.println("\nQual informação dos usuários deseja atualizar?" +
                    "\n1 - Nome" +
                    "\n2 - Login" +
                    "\n3 - Senha");
            atualiza = in.nextInt(); in.nextLine();

            System.out.println("\nDigite a ID do usuário.");

            if (atualiza > 0 && atualiza < 4) {
                System.out.print("ID: ");
                usuario.setId(in.nextInt()); in.nextLine();

            }

            switch (atualiza) {
                case 1:
                    System.out.println("\nDigite o novo nome do usuário.");
                    System.out.print("Novo nome: ");
                    novoNome = in.nextLine();
                    System.out.println("\nConfirmar a alteração do nome " + usuario.getNome() + ", de ID " + usuario.getId() + ", para " + novoNome + "?" +
                            "\n1 - Sim" +
                            "\n2 - Não");
                    confirmaAlteracao = in.nextInt();
                    break;
                case 2:
                    System.out.println("\nDigite o novo login do usuário.");
                    System.out.print("Novo login: ");
                    novoLogin = in.nextLine();
                    System.out.println("\nConfirmar a alteração do login para " + novoLogin + "?" +
                            "\n1 - Sim" +
                            "\n2 - Não");
                    confirmaAlteracao = in.nextInt();
                    break;
                case 3:
                    System.out.println("\nDigite a nova senha do usuário.");
                    System.out.print("Nova senha: ");
                    novaSenha = in.nextLine();
                    System.out.print("Confirmar nova senha: ");
                    novaSenha2 = in.nextLine();
                    if (novaSenha.equals(novaSenha2)) {
                        confirmaAlteracao = 1;
                    } else {
                        confirmaAlteracao = 2;
                    }
                    break;
                default:
                    System.out.println("\nCódigo desconhecido." +
                            "\nInicie novamente.");
                    break;
            }

        } while (atualiza < 1 || atualiza > 3);
    }
}
