package dados;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FuncionarioDao {

    Connection con;
    PreparedStatement st;
    ResultSet rs;

    public boolean conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/controlefuncionarios", "root", "");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            return false;
        }
    }

    public int salvar(Funcionario funcionario) {
        try {
            st = con.prepareStatement("INSERT INTO funcionario VALUES(?,?,?,?)"); // INSERT usar para executar o executeUpdate
            st.setString(1, funcionario.getMatricula());
            st.setString(2, funcionario.getNome());
            st.setString(3, funcionario.getCargo());
            st.setDouble(4, funcionario.getSalario());
            return st.executeUpdate();
        } catch (SQLException ex) {
            return ex.getErrorCode();
        }
    }

    public Funcionario consultar(String matricula) {
        try {
            st = con.prepareStatement("SELECT * FROM funcionario WHERE matricula=?"); // para uso do SELECT executa em executeQuery
            st.setString(1, matricula);
            rs = st.executeQuery();
            if (rs.next()) { // next = verifica se encontrou algum dado solicitado
                Funcionario funcionario = new Funcionario();
                funcionario.setMatricula(rs.getString("matricula"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setSalario(rs.getDouble("salario"));
                return funcionario;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
}