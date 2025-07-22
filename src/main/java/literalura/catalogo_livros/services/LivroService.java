package literalura.catalogo_livros.services;

import jakarta.transaction.Transactional;
import literalura.catalogo_livros.model.DadosAutor;
import literalura.catalogo_livros.model.DadosLivro;
import literalura.catalogo_livros.repositories.AutorRepository;
import literalura.catalogo_livros.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    public ConverteDados conversor;

    @Autowired
    public LivroRepository livroRepository;

    @Autowired
    public AutorService autorService;

    @Autowired
    public AutorRepository autorRepository;


    public DadosLivro obterDadosLivro(String titulo) {
        String jsonLivro = buscarLivroPeloTitulo(titulo);

        if (!conversor.validaRetorno(jsonLivro)) {
            System.out.println("Nenhum livro encontrado com o título informado!\n");
            return null;
        }

        return conversor.obterDadosLivro(jsonLivro);
    }

    public String buscarLivroPeloTitulo(String titulo) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();

            String gutendex_api = "https://gutendex.com/";
            String encodedTitulo = URLEncoder.encode(titulo, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(gutendex_api + "books?search=" + encodedTitulo))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao buscar livro pelo título: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void save(DadosLivro dadosLivro) {
        autorService.salvaSeNaoExiste(dadosLivro);

        Optional<DadosAutor> autor = autorRepository.findByNomeAutor(dadosLivro.getAutor().getNomeAutor());
        dadosLivro.setAutor(autor.orElseThrow());
        livroRepository.saveAndFlush(dadosLivro);

        System.out.println("\nCadastrado realizado com sucesso!");
        dadosLivro.printDadosLivro();
    }

    public boolean verificaSeLivroExiste(String titulo) {
        return livroRepository.existsByTitulo(titulo);
    }

    public List<DadosLivro> retornaLivrosPorIdioma(String idioma) {
        return livroRepository.findByIdioma(idioma);
    }

    public List<DadosLivro> retornaLivrosRegistrados() {
        return livroRepository.findAll();
    }
}

