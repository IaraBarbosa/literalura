package literalura.catalogo_livros.repositories;

import literalura.catalogo_livros.model.DadosLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<DadosLivro, Long> {

    boolean existsByTitulo(String titulo);

    List<DadosLivro> findByIdioma(String idioma);
}
