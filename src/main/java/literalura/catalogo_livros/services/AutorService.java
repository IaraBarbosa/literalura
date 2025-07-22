package literalura.catalogo_livros.services;

import literalura.catalogo_livros.model.DadosAutor;
import literalura.catalogo_livros.model.DadosLivro;
import literalura.catalogo_livros.repositories.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    public AutorRepository autorRepository;


    public void salvaSeNaoExiste(DadosLivro livro) {
        DadosAutor autor = livro.getAutor();
        if (autorRepository.findByNomeAutor(autor.getNomeAutor()).isEmpty()) {
            autorRepository.saveAndFlush(autor);
        }
    }

    public List<DadosAutor> listarAutoresRegistrados() {
        return autorRepository.findAll();
    }

    public List<DadosAutor> listarAutoresVivosEmAno(int ano) {
        return autorRepository.findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(ano, ano);
    }
}