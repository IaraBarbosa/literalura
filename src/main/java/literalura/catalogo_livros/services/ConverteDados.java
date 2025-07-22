package literalura.catalogo_livros.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import literalura.catalogo_livros.model.DadosAutor;
import literalura.catalogo_livros.model.DadosLivro;
import org.springframework.stereotype.Component;

@Component
public class ConverteDados {

    private final ObjectMapper mapper = new ObjectMapper();


    public DadosLivro obterDadosLivro(String json) {
        try {
            JsonNode rootNode = mapper.readTree(json);
            var primeiroResultado = rootNode.path("results").get(0);

            DadosAutor autor = obterDadosAutor(primeiroResultado);
            String idioma = obterIdioma(primeiroResultado);

            DadosLivro livro = mapper.treeToValue(primeiroResultado, DadosLivro.class);

            if (livro != null) {
                livro.setId(null);
                livro.setAutor(autor);
                livro.setIdioma(idioma);
            }
            return livro;
        } catch (JsonProcessingException e) {
            System.out.println("Erro ao converter JSON (Livro) para objeto: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean validaRetorno(String json) {
        try {
            JsonNode rootNode = mapper.readTree(json);
            if (rootNode.get("count").asInt() != 0) {
                return true;
            }
        } catch (JsonProcessingException e) {
            System.out.println("Nenhum livro encontrado com o título informado!\n");
            throw new RuntimeException(e);
        }
        return false;
    }

    private DadosAutor obterDadosAutor(JsonNode primeiroResultado) {
        try {
            JsonNode primeiroAutorNode = primeiroResultado.path("authors").get(0);
            return mapper.treeToValue(primeiroAutorNode, DadosAutor.class);
        }
        catch (JsonProcessingException e) {
            System.out.println("Erro ao converter JSON (Autor) para objeto: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String obterIdioma(JsonNode primeiroResultado) {
        JsonNode primeiroIdiomaNode = primeiroResultado.path("languages").get(0);
        return primeiroIdiomaNode.asText();
    }
}
