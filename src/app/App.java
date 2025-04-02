package app;

import entidades.Usuario;
import repository.UsuarioRepository;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int opc = 0;
        UsuarioRepository repository = new UsuarioRepository();
        Usuario usuario = new Usuario();
        System.out.println("Bem-vindo ao Sistema de Cadastro de Usuários!");

        do {
            Usuario.menu();
            opc = scanner.nextInt();
            scanner.nextLine();
            Usuario.limpartela();


            switch (opc) {
                case 1:

                    System.out.println("Cadastrar Usuário");
                    usuario.insercaoDadosInserir();
                    System.out.println("");
                    System.out.println("Pressione Enter para continuar.");
                    scanner.nextLine();

                    break;
                case 2:

                    System.out.println("Listar Usuários");
                    List<Usuario> usuarios = repository.retornaTodos();
                    if (usuarios.isEmpty()) {
                        System.out.println("Não há usuarios cadastrados no banco de dados");
                    } else {
                        UsuarioRepository.imprimirListaUsuarios(usuarios);
                        System.out.println("");
                        System.out.println("Pressione Enter para continuar.");
                        scanner.nextLine();
                    }
                    break;

                case 3:

                    System.out.println("Atualizar Usuário");
                    usuario.insercaoDadosAlterar();
                    System.out.println("Pressione Enter para continuar.");
                    scanner.nextLine();
                    break;
                case 4:

                    System.out.println("Deletar Usuário");
                    usuario.insercaoDadosDeletar();
                    System.out.println("Pressione Enter para continuar.");
                    scanner.nextLine();
                    break;
                case 5:
                    System.out.println("Consultar usuário pelo CPF");
                    usuario.consultarDadosCpf();
                    System.out.println("Pressione Enter para continuar.");
                    scanner.nextLine();
                    break;
                case 6:
                    System.out.println("Consultar usuário pelas iniciais");
                    usuario.consultarDadosIniciais();
                    System.out.println("Pressione Enter para continuar.");
                    scanner.nextLine();
                    break;
                case 7:
                    System.out.println("Saindo do sistema...");
                    System.out.println("Obrigado por usar nosso sistema!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opc < 7);
    }
}
