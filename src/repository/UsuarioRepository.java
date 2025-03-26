package repository;

import entidades.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private Connection conexao;
    private String CONNECTION_STRING = "jdbc:mysql://localhost:3306/POO";
    private String USUARIO = "root";
    private String SENHA = "root";

    public UsuarioRepository() {
        try {
            this.conexao = DriverManager.getConnection(CONNECTION_STRING, USUARIO, SENHA);
            if (!this.conexao.isClosed()) {
                System.out.println("A conexão foi realizada com sucesso!");
            } else {
                System.out.println("Não foi possível realizar a conexão com o banco de dados!");
            }
        } catch (SQLException e) {
            System.out.println("Não foi possível realizar a conexão com o banco de dados!");
            e.printStackTrace();
        }
    }

    public void inserir (Usuario usuario) {
        String sql = "INSERT INTO tb_Usuarios (id, nome, cpf, idade) VALUES (?,?,?,?)";
        try (PreparedStatement ps = this.conexao.prepareStatement(sql)){
            ps.setInt(1, usuario.getId());
            ps.setString(2, usuario.getNome());
            ps.setString(3, usuario.getCpf());
            ps.setInt(4, usuario.getIdade());
            ps.execute();
            System.out.println("Usuario inserido com sucesso!");
        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Usuário " + usuario.getNome() + "já tem um cadastro no banco de dados!");
        } catch (SQLException e) {
            System.out.println("Não foi possível inserir usuário no banco de dados");
            e.printStackTrace();
        }
    }

    public void excluir (Usuario usuario) {
        String sql = "DELETE FROM tb_Usuarios WHERE id = ?";
        try (PreparedStatement ps = this.conexao.prepareStatement(sql)){

            ps.setInt(1, usuario.getId());
            ps.execute();
            System.out.println("Usuario excluido com sucesso!");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void atualizar (Usuario usuario) {
        String sql = "UPDATE tb_Usuarios SET nome = ?, cpf = ?, idade = ? WHERE id = ?";
        try(PreparedStatement ps = this.conexao.prepareStatement(sql)){
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setInt(3, usuario.getIdade());
            ps.execute();
            System.out.println("Usuario atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Não foi possível atualizar o usuário!");
            e.printStackTrace();
        }
    }

    public Usuario consultarCpf(Usuario usuario) {
        String sql = "SELECT * FROM tb_usuarios WHERE cpf = ?;";
        Usuario usuarioConsultado = null;
        try {
            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ps.setString(1, usuario.getCpf());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuarioConsultado = new Usuario();
                usuarioConsultado.setId(rs.getInt("id"));
                usuarioConsultado.setNome(rs.getString("nome"));
                usuarioConsultado.setCpf(rs.getString("cpf"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioConsultado;
    }

    public List<Usuario> retornaTodos(){
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "Select id, nome, cpf from tb_usuarios";
        try {
            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setNome(rs.getString("nome"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Não foi possivel realizar a pesquisa");
            e.printStackTrace();
        }
        return usuarios;
    }

}
