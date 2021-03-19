package crud;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UsuarioDAOTeste {
    Calendar calendario = Calendar.getInstance();
    DateFormat data = new SimpleDateFormat("dd/MM/yyyy");
    String hoje = data.format(calendario.getTime());

    private final ByteArrayOutputStream saida = new ByteArrayOutputStream();

    ConexaoBancoDados connect = new ConexaoBancoDados();
    Usuario usuario = new Usuario();
    UsuarioMenu menu = new UsuarioMenu();
    UsuarioDAO dao = new UsuarioDAO();

    @Before
    public void configurar() {
        System.setOut(new PrintStream(saida));
    }

    @After
    public void fechar() throws IOException {
        saida.close();
    }

    @Test
    public void testeContarCadastroUsuario() {

        connect.conectarBandoDados();
        dao.contarCadastroUsuario();
        connect.desconectarBancoDados();

        Assert.assertEquals(11, dao.getRegistro());
    }

    @Test
    public void testeCriarUsuario() {
        usuario.setNome("Carolina");
        usuario.setLogin("carolina@samba.com");
        usuario.setSenha("chico");
        usuario.setDataCadastro(hoje);

        connect.conectarBandoDados();
        dao.contarCadastroUsuario();
        dao.criarUsuario(usuario);
        connect.desconectarBancoDados();

        Assert.assertTrue(dao.isUsuarioAdicionado());
    }

    @Test
    public void testeBuscarTodosUsuarios() {
        connect.conectarBandoDados();
        dao.buscarTodosUsuarios();
        connect.desconectarBancoDados();

        String expected = "\nBusca realizada com sucesso.\r\n";

        Assert.assertEquals(expected, saida.toString());
    }

    @Test
    public void testeBuscarUmUsuario() {
        usuario.setNome("Lúcia MacCartney");

        connect.conectarBandoDados();
        dao.buscarUmUsuario(usuario);
        connect.desconectarBancoDados();

        String ex = "\nBusca realizada com sucesso.\r\n";

        Assert.assertEquals(ex, saida.toString());
    }

    @Test
    public void testeAtualizarNomeUsuario() {
        menu.setNovoNome("Minolta Buffo");
        usuario.setId(19);
        menu.setConfirmaAlteracao(2);

        connect.conectarBandoDados();
        dao.atualizarNomeUsuario(usuario, menu);
        connect.desconectarBancoDados();

        Assert.assertEquals(menu.getNovoNome(), dao.getNomeAtual());
    }

    @Test
    public void testeAtualizarLoginUsuario() {
        menu.setNovoLogin("januaria@jan.com.br");
        usuario.setId(24);
        menu.setConfirmaAlteracao(2);

        connect.conectarBandoDados();
        dao.atualizarLoginUsuario(usuario, menu);
        connect.desconectarBancoDados();

        Assert.assertEquals(menu.getNovoLogin(), dao.getLoginAtual());
    }

    @Test
    public void testeAtualizarSenhaUsuario() {
        menu.setNovaSenha("buarque");
        usuario.setId(50);
        menu.setConfirmaAlteracao(1);

        connect.conectarBandoDados();
        dao.atualizarSenhaUsuario(usuario, menu);
        connect.desconectarBancoDados();

        Assert.assertEquals(menu.getNovaSenha(), dao.getSenhaAtual());
    }

    @Test
    public void testeExcluirUsuario() {
        usuario.setNome("Boris Grushenko");
        usuario.setId(13);

        connect.conectarBandoDados();
        dao.contarCadastroUsuario();
        dao.excluirUsuario(usuario);
        connect.desconectarBancoDados();

        Assert.assertTrue(dao.isUsuarioExcluido());
    }

}
