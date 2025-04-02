package repository;

import entidades.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioRepository {
    private Connection conexao;
    private final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/poo";
    private final String USUARIO = "root";
    private final String SENHA = "root";

    public UsuarioRepository() {
        try {
            this.conexao = DriverManager.getConnection(CONNECTION_STRING, USUARIO, SENHA);
            if (this.conexao.isClosed()) {
                System.out.println("❌ Não foi possível realizar a conexão com o banco de dados!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Não foi possível realizar a conexão com o banco de dados!");
            e.printStackTrace();
        }
    }

    public void inserirUsuario(Usuario usuario) {
        String sql = "INSERT INTO tb_usuarios (id, nome, cpf, idade) VALUES (?,?,?,?)";

        try {
            PreparedStatement ps = this.conexao.prepareStatement(sql);

            ps.setInt(1, usuario.getId());
            ps.setString(2, usuario.getNome());
            ps.setString(3, usuario.getCpf());
            ps.setInt(4, usuario.getIdade());
            ps.execute();
            System.out.println("✅ Usuário cadastrado com sucesso!");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Usuário " + usuario.getNome() + "já tem um cadastro no banco de dados!");
        } catch (SQLException e) {
            System.out.println("❌ Não foi possível inserir usuário no banco de dados");
            e.printStackTrace();
        }
    }

    public void excluirUsuario(Usuario usuario) {
        String sql = "DELETE FROM tb_Usuarios WHERE id = ?";
        try {

            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ps.setInt(1, usuario.getId());
            ps.execute();
            System.out.println("✅ Usuario excluido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE tb_Usuarios SET nome = ?, cpf = ?, idade = ? WHERE id = ?";
        try {

            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setInt(3, usuario.getIdade());
            ps.setInt(4, usuario.getId());
            ps.execute();
            System.out.println("✅ Usuario atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("❌ Não foi possível atualizar o usuário!");
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
                usuarioConsultado.setIdade(rs.getInt("idade"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioConsultado;
    }

    public List<Usuario> consultarPorIniciais(Usuario usuario) {
        String sql = "SELECT * FROM tb_usuarios WHERE nome LIKE ?;";
        List<Usuario> usuariosConsultados = new ArrayList<>();

        try {
            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ps.setString(1, usuario.getNome() + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuarioConsultado = new Usuario();
                usuarioConsultado.setId(rs.getInt("id"));
                usuarioConsultado.setNome(rs.getString("nome"));
                usuarioConsultado.setCpf(rs.getString("cpf"));
                usuarioConsultado.setIdade(rs.getInt("idade"));
                usuariosConsultados.add(usuarioConsultado);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuariosConsultados;
    }


    public List<Usuario> retornaTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "Select id, nome, cpf from tb_usuarios";
        try {
            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setNome(rs.getString("nome"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("❌ Não foi possivel realizar a pesquisa");
            e.printStackTrace();
        }
        return usuarios;
    }

    public static void imprimirListaUsuarios(List<Usuario> usuarios) {
        List<Usuario> usuariosOrdenados = usuarios.stream()
                .sorted(Comparator.comparing(Usuario::getNome))
                .collect(Collectors.toList());

        for (Usuario usuario : usuariosOrdenados) {
            System.out.println("ID: " + usuario.getId());
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("CPF: " + usuario.getCpf());
            System.out.println("Idade: " + usuario.getIdade());
            System.out.println("-----------------------------");
        }
    }

    public Usuario retornaId(int id) {
        Usuario usuario = null;
        String sql = "SELECT id, nome, cpf FROM tb_usuarios WHERE id = ?";

        try {
            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setNome(rs.getString("nome"));
            }
        } catch (Exception e) {
            System.out.println("❌ Não foi possível encontrar o usuário com ID " + id);
        }
        return usuario;
    }

    public void imprimirUsuario(int id) {
        UsuarioRepository repository = new UsuarioRepository();
        Usuario usuario = repository.retornaId(id);

        System.out.println("✅ Usuário encontrado!");
        System.out.println("ID: " + usuario.getId());
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("CPF: " + usuario.getCpf());
        System.out.println("Idade: " + usuario.getIdade());

    }

    public boolean idExiste(int id) throws Exception {
        String sql = "SELECT * FROM tb_usuarios WHERE id = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            try {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            } catch (Exception e) {
                throw new Exception("Este ID já está cadastrado em nosso banco de dados!");
            }
        } catch (Exception e) {
            throw new Exception("❌ Conexão com o banco de dados não foi realizada!");
        }
        return false;
    }

    public boolean cpfExiste(String cpf, Integer idIgnorar) throws SQLException {
        Usuario usuario = new Usuario();
        cpf = usuario.formatarCpf(cpf);

        String sql = "SELECT COUNT(*) FROM tb_usuarios WHERE cpf = ?" +
                (idIgnorar != null ? " AND id != ?" : "");

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            if (idIgnorar != null) {
                stmt.setInt(2, idIgnorar);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        }
        return false;
    }


}
