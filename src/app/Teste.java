package app;

import entidades.Usuario;
import repository.UsuarioRepository;
import service.UsuarioService;

import java.util.List;
import java.util.Scanner;

public class Teste {
    public static void main(String[] args) throws Exception {

        Usuario usuario = new Usuario();
        UsuarioRepository repository = new UsuarioRepository();


        try {
            usuario.insercaoDadosInserir();
        } catch (Exception e) {
            System.out.println("Entrada de dado não permitida:  " + e.getMessage());
            System.out.println("Por gentileza, insira os dados novamente");
        }


//        List<Usuario> usuarios = repository.retornaTodos();
//        repository.imprimirListaUsuarios(usuarios);

    }
}
