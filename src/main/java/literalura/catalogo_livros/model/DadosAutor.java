package literalura.catalogo_livros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "autores")
public class DadosAutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonAlias("name")
    private String nomeAutor;

    @JsonAlias("birth_year")
    private int anoNascimento;

    @JsonAlias("death_year")
    private int anoFalecimento;

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<DadosLivro> livros = new ArrayList<>();


    public DadosAutor() {}

    public DadosAutor(Long id, String nomeAutor, int anoNascimento, int anoFalecimento, List<DadosLivro> livros) {
        this.nomeAutor = nomeAutor;
        this.anoNascimento = anoNascimento;
        this.anoFalecimento = anoFalecimento;
        this.livros = livros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public int getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(int anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public void setLivros(List<DadosLivro> livros) {
        this.livros = livros;
    }

    public List<DadosLivro> getLivros() {
        return livros;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DadosAutor that = (DadosAutor) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {

        String titulos = String.join("\n    - ",
                livros.stream()
                .map(DadosLivro::getTitulo)
                .toList());

        return "*---------------------------------------------------*\n" +
                "   Autor: " + nomeAutor + "\n" +
                "   Ano de nascimento: " + anoNascimento + "\n" +
                "   Ano de falecimento: " + anoFalecimento + "\n" +
                "   Livros: \n    - " + titulos + "\n";
    }

    public void printDadosAutor() {
        System.out.println(this);
    }
}
