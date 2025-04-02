package entidades;

import java.util.List;
import java.util.Scanner;

import service.UsuarioService;
import repository.UsuarioRepository;

public class Usuario {

    private int id;
    private String nome;
    private String cpf;
    private int idade;
    private List<Usuario> usuarioList;

    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public static void insercaoDadosInserir() throws Exception {
        Usuario usuario = new Usuario();
        UsuarioService service = new UsuarioService();
        UsuarioRepository repository = new UsuarioRepository();
        Scanner scanner = new Scanner(System.in);

        while (true) {//ID

            try {

                System.out.println("Digite o seu ID: ");
                if (scanner.hasNextInt()) {
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    usuario.setId(id);
                    service.validarId(usuario, true);
                    break;
                } else {
                    scanner.nextLine();
                    throw new Exception("O ID deve ser um número válido!");
                }
            } catch (Exception e) {
                System.out.println("❌ Entrada de dado não permitida:  " + e.getMessage());
                scanner.nextLine();
            }
        }
        System.out.println("------------------------------");
        while (true) {//NOME
            try {
                System.out.println("Digite o nome do usuário: ");
                String nome = scanner.nextLine();
                usuario.setNome(nome);
                service.validarNome(usuario);
                break;
            } catch (Exception e) {
                System.out.println("❌ Entrada de dado não permitida: " + e.getMessage());
                scanner.nextLine();
            }
        }
        System.out.println("------------------------------");
        while (true) {//CPF
            try {
                System.out.println("Digite o CPF do usuário: ");
                String cpf = scanner.nextLine();
                usuario.setCpf(cpf);
                service.validarCpf(usuario, false, false);
                cpf = usuario.formatarCpf(cpf);
                usuario.setCpf(cpf);
                break;
            } catch (Exception e) {
                System.out.println("❌ Entrada de dado não permitida: " + e.getMessage());
                scanner.nextLine();
            }
        }
        System.out.println("------------------------------");
        while (true) {//IDADE
            try {
                System.out.println("Digite a idade do usuário: ");
                if (scanner.hasNextInt()) {
                    int idade = 0;
                    idade = scanner.nextInt();
                    scanner.nextLine();
                    usuario.setIdade(idade);
                    service.validarIdade(usuario);
                    break;
                } else {
                    throw new Exception("A idade deve ser um número válido");
                }
            } catch (Exception e) {
                System.out.println("❌ Entrada de dado não permitida: " + e.getMessage());
                scanner.nextLine();
            }
        }
        System.out.println("------------------------------");
        System.out.println("\uD83D\uDD0D Confira as informações!");
        System.out.println("ID: " + usuario.getId());
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("CPF: " + usuario.getCpf());
        System.out.println("Idade: " + usuario.getIdade());
        System.out.println("Este é o usuário que você deseja cadastrar? (S/N): ");
        String resposta = scanner.nextLine().trim().toLowerCase();
        if (resposta.equals("s") || resposta.equals("sim")) {
            repository.inserirUsuario(usuario);
            System.out.println("✅ Usuário cadastrado com sucesso!");
        } else if (resposta.equals("n") || resposta.equals("não")) {
            System.out.println("❌ Cadastro cancelado.");
        }
    }

    public static void insercaoDadosAlterar() throws Exception {
        Usuario usuario = new Usuario();
        UsuarioService service = new UsuarioService();
        UsuarioRepository repository = new UsuarioRepository();
        Scanner scanner = new Scanner(System.in);

        List<Usuario> usuarios = repository.retornaTodos();

        if (usuarios.isEmpty()) {
            System.out.println("Não há usuários cadastrados para alterar.");

        } else {
            UsuarioRepository.imprimirListaUsuarios(usuarios);

            while (true) { // SOLICITAÇÃO DO ID QUE SERÁ ALTERADO
                try {
                    System.out.println("Digite o ID do usuário que deseja alterar: ");
                    if (scanner.hasNextInt()) {
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        usuario.setId(id);
                        if (!repository.idExiste(id)) {
                            System.out.println("Nenhum usuário encontrado com esse ID!");
                        } else {
                            break;
                        }
                    } else {
                        System.out.println("O ID deve ser um número válido!");
                        scanner.nextLine();
                    }
                } catch (Exception e) {
                    System.out.println("❌ Entrada inválida: " + e.getMessage());
                    scanner.nextLine();
                }
            }
            System.out.println("------------------------------");
            while (true) { // SOLICITAÇÃO DO NOVO NOME
                try {
                    System.out.println("Digite o novo nome do usuário: ");
                    String nome = scanner.nextLine();
                    usuario.setNome(nome);
                    service.validarNome(usuario);
                    break;
                } catch (Exception e) {
                    System.out.println("❌ Entrada inválida: " + e.getMessage());
                    scanner.nextLine();
                }
            }
            System.out.println("------------------------------");
            while (true) { // SOLICITAÇÃO DO NOVO CPF
                try {
                    System.out.println("Digite o novo CPF do usuário: ");
                    String cpf = scanner.nextLine();
                    usuario.setCpf(cpf);
                    service.validarCpf(usuario, false, true);
                    usuario.setCpf(usuario.formatarCpf(cpf));
                    break;
                } catch (Exception e) {
                    System.out.println("❌ Entrada inválida: " + e.getMessage());
                    scanner.nextLine();
                }
            }
            System.out.println("------------------------------");
            while (true) { // SOLICITAÇÃO DA NOVA IDADE
                try {
                    System.out.println("Digite a nova idade do usuário: ");
                    if (scanner.hasNextInt()) {
                        int idade = scanner.nextInt();
                        scanner.nextLine();
                        usuario.setIdade(idade);
                        service.validarIdade(usuario);
                        break;
                    } else {
                        System.out.println("A idade deve ser um número válido!");
                        scanner.nextLine();
                    }
                } catch (Exception e) {
                    System.out.println("Entrada inválida: " + e.getMessage());
                    scanner.nextLine();
                }
            }
            System.out.println("------------------------------");
            repository.imprimirUsuario(usuario.getId());
            System.out.println("Este é o usuário que você deseja atualizar? (S/N): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (resposta.equals("s") || resposta.equals("sim")) {
                repository.atualizarUsuario(usuario);
                System.out.println("✅ Usuário atualizado com sucesso!");
            } else if (resposta.equals("n") || resposta.equals("não")) {
                System.out.println("❌ Atualização cancelada.");
            }
        }
    }

    public static void insercaoDadosDeletar() throws Exception {
        Usuario usuario = new Usuario();
        UsuarioRepository repository = new UsuarioRepository();
        UsuarioService service = new UsuarioService();
        Scanner scanner = new Scanner(System.in);
        List<Usuario> usuarios = repository.retornaTodos();

        if (usuarios.isEmpty()) {
            System.out.println("Não há nenhum cadastro no banco de dados!");
            System.out.println("Pressione Enter para continuar.");
            scanner.nextLine();
        } else {
            UsuarioRepository.imprimirListaUsuarios(usuarios);

            System.out.println("Digite o ID do usuário que você deseja deletar do banco de dados");
            int id = scanner.nextInt();
            scanner.nextLine();
            usuario.setId(id);
            service.validarId(usuario, false);
            repository.retornaId(id);
            repository.imprimirUsuario(usuario.getId());
            System.out.println("Este é o usuário que você deseja excluir? (S/N): ");
            String resposta = scanner.nextLine().trim().toLowerCase();

            if (resposta.equals("s") || resposta.equals("sim")) {
                repository.excluirUsuario(usuario);
                System.out.println("✅ Usuário excluído com sucesso!");
            } else if (resposta.equals("n") || resposta.equals("não")) {
                System.out.println("Pressione Enter para voltar ao menu.");
            }
        }
    }

    public static void consultarDadosCpf() throws Exception {
        Usuario usuario = new Usuario();
        UsuarioService service = new UsuarioService();
        UsuarioRepository repository = new UsuarioRepository();
        Scanner scanner = new Scanner(System.in);

        while (true) {//CPF
            try {
                System.out.println("Digite o CPF do usuário: ");
                String cpf = scanner.nextLine();
                usuario.setCpf(cpf);
                service.validarCpf(usuario, true, false);
                cpf = usuario.formatarCpf(cpf);
                usuario.setCpf(cpf);
                Usuario usuarioConsultado = repository.consultarCpf(usuario);
                if (usuarioConsultado == null) {
                    throw new Exception("Nenhum usuário encontrado com esse CPF!");

                } else {
                    repository.imprimirUsuario(usuarioConsultado.getId());
                    System.out.println("Pressione Enter para continuar.");
                    scanner.nextLine();
                }
                break;

            } catch (Exception e) {
                System.out.println("❌ Entrada de dado não permitida: " + e.getMessage());
                scanner.nextLine();
            }

        }

    }

    public static void consultarDadosIniciais() throws Exception {
        Usuario usuario = new Usuario();
        UsuarioService service = new UsuarioService();
        UsuarioRepository repository = new UsuarioRepository();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Digite as iniciais do nome do usuário: ");
                String iniciais = scanner.nextLine();
                usuario.setNome(iniciais);

                service.validarNome(usuario);

                List<Usuario> usuariosConsultados = repository.consultarPorIniciais(usuario);

                if (usuariosConsultados.isEmpty()) {
                    System.out.println("❌ Nenhum usuário encontrado para as iniciais: " + iniciais);
                } else {
                    repository.imprimirListaUsuarios(usuariosConsultados);
                }

                break;
            } catch (Exception e) {
                System.out.println("❌ Entrada de dado não permitida: " + e.getMessage());
                scanner.nextLine();
            }
        }


    }

    public static void limpartela() {
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }

    public static void menu() {
        System.out.println("Por gentileza, escolha uma das opções para continuar: ");
        System.out.println("1 - Cadastrar Usuário");
        System.out.println("2 - Listar Usuários");
        System.out.println("3 - Atualizar Usuário");
        System.out.println("4 - Deletar Usuário");
        System.out.println("5 - Consultar pelo CPF");
        System.out.println("6 - Consultar pelas iniciais");
        System.out.println("7 - Sair");
    }

    public String formatarCpf(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        cpf = String.format("%011d", Long.parseLong(cpf));
        cpf = cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");

        return cpf;
    }
}