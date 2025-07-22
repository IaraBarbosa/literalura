package literalura.catalogo_livros.repositories;

import literalura.catalogo_livros.model.DadosAutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<DadosAutor, Long> {

    Optional<DadosAutor> findByNomeAutor(String nomeAutor);

    List<DadosAutor> findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(int anoLess, int anoGreater);
}
