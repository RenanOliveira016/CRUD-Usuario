package service;

import entidades.Usuario;
import repository.UsuarioRepository;

public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public void validarId(Usuario usuario, boolean seCadastro) throws Exception {


        if (usuario.getId() == 0) throw new Exception("O valor do ID não pode ser 0");
        else if (usuario.getId() < 0) throw new Exception("O valor do ID não pode ser negativo");

        boolean idExiste = usuarioRepository.idExiste(usuario.getId());

        if (seCadastro && idExiste) throw new Exception("Este ID já existe em nosso banco de dados!");
        else if (!seCadastro && !idExiste) throw new Exception("Este ID não existe em nosso banco de dados!");
    }

    public void validarNome(Usuario usuario) throws Exception {

        if (usuario.getNome().length() > 100) {
            throw new Exception("Limite de caracteres excedido para o nome");
        } else if (!usuario.getNome().matches("^[a-zA-Zà-úÀ-ÚçÇ ]+$")) {
            throw new Exception("Nome inválido: Não pode conter números ou caracteres especiais");
        } else if (usuario.getNome().isEmpty() || usuario.getNome() == null) {
            throw new Exception("O nome não pode ser vazio");
        }
    }

    public void validarCpf(Usuario usuario, boolean isBusca, boolean isCadastro) throws Exception {

        if (!usuario.getCpf().matches("[0-9]+")) {
            throw new Exception("O CPF deve conter apenas números");
        } else if (usuario.getCpf().length() > 11) {
            throw new Exception("O CPF deve conter até 11 dígitos");
        } else if (usuario.getCpf() == null || usuario.getCpf().isEmpty()) {
            throw new Exception("O CPF não pode ser vazio");
        } else if (isCadastro && usuarioRepository.cpfExiste(usuario.getCpf(), null)) {
            throw new Exception("Esse CPF já existe em nosso banco de dados!");
        } else if (!isCadastro && !isBusca && usuarioRepository.cpfExiste(usuario.getCpf(), usuario.getId())) {
            throw new Exception("Esse CPF já está cadastrado para outro usuário!");
        } else if (isBusca && !usuarioRepository.cpfExiste(usuario.getCpf(), null)) {
            throw new Exception("Esse CPF não existe em nosso banco de dados!");
        }
    }

    public void validarIdade(Usuario usuario) throws Exception {

        if (usuario.getIdade() < 0) {
            throw new Exception("A idade não pode ser negativa");
        } else if (usuario.getIdade() > 120) {
            throw new Exception("A idade não pode ser maior que 120 anos");
        } else if (usuario.getIdade() == 0) {
            throw new Exception("A idade não pode ser vazia");
        }
    }
}
