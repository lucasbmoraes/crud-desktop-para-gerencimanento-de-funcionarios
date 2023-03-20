package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Funcionario;

public class FuncionarioRepository {

    public void cadastrar(Funcionario funcionario) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        try {
            String consulta = "INSERT INTO funcionario(id,nome,cpf)" +
                    "VALUES (NEXTVAL('FUNCIONARIO_SEQ'),?,?)";

            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, funcionario.getNome());
            stm.setString(2, funcionario.getCpf());
            stm.execute();

        } catch (Exception e) {

        } finally {
            conexao.desconectar(conn);
        }
    }

    public ArrayList<Funcionario> consultar() throws SQLException {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        ArrayList<Funcionario> listaFuncionarios = new ArrayList<>();
        String consulta = "SELECT * FROM FUNCIONARIO";

        Statement stm = conn.createStatement();
        ResultSet resultado = stm.executeQuery(consulta);

        try {
            while (resultado.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(Integer.parseInt(resultado.getString("id")));
                funcionario.setNome(resultado.getString("nome"));
                funcionario.setCpf(resultado.getString("cpf"));
                listaFuncionarios.add(funcionario);
            }

        } catch (Exception e) {
            System.out.println("Não conseguiu realizar a consulta na database");

        } finally {
            conexao.desconectar(conn);
        }

        return listaFuncionarios;
    }

    public void excluir(int id) throws Exception {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        String consulta = "DELETE FROM funcionario WHERE ID= ?";
        PreparedStatement stm = conn.prepareStatement(consulta);
        stm.setInt(1, id);
        stm.execute();

        try {

        } catch (Exception e) {
            System.out.println("Não foi possível excluir");
        } finally {
            conexao.desconectar(conn);
        }
    }
}