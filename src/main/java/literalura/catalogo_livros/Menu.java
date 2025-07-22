package literalura.catalogo_livros;

import literalura.catalogo_livros.model.DadosAutor;
import literalura.catalogo_livros.model.DadosLivro;
import literalura.catalogo_livros.services.AutorService;
import literalura.catalogo_livros.services.LivroService;
import literalura.catalogo_livros.utils.Estatisticas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class Menu {

    @Autowired
    public AutorService autorService;

    @Autowired
    public LivroService livroService;


    public void processaOpcao() {
        Scanner sc = new Scanner(System.in);

        String opcao = "";
        do {
            exibeMenu();
            System.out.print("Digite a opção desejada: ");
            opcao = sc.nextLine();

            switch (opcao) {
                case "1" -> buscaLivroPeloTitulo(sc);
                case "2" -> listarLivrosRegistrados();
                case "3" -> listarAutoresRegistredos();
                case "4" -> listarAutoresVivosEmAno(sc);
                case "5" -> listarLivrosPorIdioma(sc);
                case "6" -> {
                    sc.close();
                    System.out.println("Até Logo!");
                }
                default -> System.out.printf("Selecione uma opção válida. %n%n");
            }
        }
        while (!opcao.equals("6"));
    }

    private void exibeMenu() {
        System.out.println("""
                *------------------------------------------------------------*
                |                       LiterAlura                           |
                |____________________________________________________________|
                |                                                            |
                |       1. Buscar livro pelo título                          |
                |       2. Listar livros registrados                         |
                |       3. Listar autores registrados                        |
                |       4. Listar autores vivos em um determinado ano        |
                |       5. Listar livros em um determinado idioma            |
                |                                                            |
                |       6. Sair                                              |
                |____________________________________________________________|
                """);
    }

    private void buscaLivroPeloTitulo(Scanner sc) {
        System.out.print("Digite o título do livro que deseja buscar: ");
        var titulo = sc.nextLine();

        var dadosLivro = livroService.obterDadosLivro(titulo);
        if (dadosLivro == null) {
            return;
        }

        if (livroService.verificaSeLivroExiste(dadosLivro.getTitulo())) {
            System.out.println("O livro " + dadosLivro.getTitulo() + " já está cadastrado.");
            return;
        }

        System.out.println("Livro encontrado! ");
        System.out.println("Deseja cadastrar o título " + dadosLivro.getTitulo() + "? (S/N)");

        if (sc.nextLine().equalsIgnoreCase("S")) {
            livroService.save(dadosLivro);
        }
    }

    private void listarLivrosRegistrados() {
        System.out.print("""
                *------------------------------------------------------------*
                                 Lista de livros registrados                
                |____________________________________________________________|
                """);

        List<DadosLivro> livros = livroService.retornaLivrosRegistrados();

        if (livros.isEmpty()) {
            System.out.println("\nNenhum título registrado!\n");
            return;
        }

        Estatisticas.retornaEstatisticas(livros, "livros");
        livros.forEach(DadosLivro::printDadosLivro);
    }

    private void listarAutoresRegistredos() {
        System.out.print("""
                *------------------------------------------------------------*
                                 Lista de autores registrados               
                |____________________________________________________________|
                """);

        List<DadosAutor> autores = autorService.listarAutoresRegistrados();

        if (autores.isEmpty()) {
            System.out.println("\nNenhum autor registrado!\n");
            return;
        }

        Estatisticas.retornaEstatisticas(autores, "autores");
        autores.forEach(DadosAutor::printDadosAutor);
    }

    private void listarAutoresVivosEmAno(Scanner sc) {
        System.out.print("Digite o ano desejado: ");
        String ano = sc.nextLine();

        if (!ano.matches("\\d+")) {
            System.out.println("Digite um ano válido!\n");
            return;
        }

        int anoValido = Integer.parseInt(ano);
        if (anoValido <= 0 || anoValido > LocalDate.now().getYear()){
            System.out.println("Digite um ano válido!\n");
            return;
        }

        System.out.printf("""
                *------------------------------------------------------------*
                                Lista de autores vivos em %d              
                |____________________________________________________________|
                """, anoValido);

        List<DadosAutor> autores = autorService.listarAutoresVivosEmAno(anoValido);

        if (autores.isEmpty()) {
            System.out.println("\nNenhum autor encontrado para o ano informado!\n");
            return;
        }

        Estatisticas.retornaEstatisticas(autores, "autores");
        autores.forEach(DadosAutor::printDadosAutor);

    }

    private void listarLivrosPorIdioma(Scanner sc) {
        System.out.println("Digite o idioma que deseja buscar: \n" +
                "   es - Espanhol \n" +
                "   en - Inglês \n" +
                "   fr - Francês \n" +
                "   pt - Português");

        var idiomaEscolhido = sc.nextLine().toLowerCase();
        var idioma = switch(idiomaEscolhido) {
            case "es" -> "Espanhol";
            case "en" -> "Inglês";
            case "fr" -> "Francês";
            case "pt" -> "Português";
            default -> {
                System.out.println("Idioma inválido!\n");
                yield null;
            }
        };

        if (idioma == null) {
            return;
        }

        System.out.printf("""
                *------------------------------------------------------------*
                           Lista de livros registrados em %s                
                |____________________________________________________________|
                """, idioma);

        List<DadosLivro> livros = livroService.retornaLivrosPorIdioma(idiomaEscolhido);

        if (livros.isEmpty()) {
            System.out.println("\nNenhum título encontrado no idioma " + idioma + "\n");
            return;
        }

        Estatisticas.retornaEstatisticas(livros, "livros");
        livros.forEach(DadosLivro::printDadosLivro);
    }
}
