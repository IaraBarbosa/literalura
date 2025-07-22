package literalura.catalogo_livros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "livros")
public class DadosLivro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonAlias("title")
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_autor", referencedColumnName = "id")
    private DadosAutor autor;

    private String idioma;

    @JsonAlias("download_count")
    private Integer numeroDownloads;


    public DadosLivro() {
    }

    public DadosLivro(Long id, String titulo, DadosAutor autor, String idioma, Integer numeroDownloads) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDownloads = numeroDownloads;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public DadosAutor getAutor() {
        return autor;
    }

    public void setAutor(DadosAutor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DadosLivro that = (DadosLivro) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "*---------------------------------------------------*\n" +
               "   Título: " + titulo + "\n" +
               "   Autor: " + autor.getNomeAutor() + "\n" +
               "   Idioma: " + idioma + "\n" +
               "   Numero de downloads: " + numeroDownloads + "\n";
    }

    public void printDadosLivro() {
        System.out.println(this);
    }
}
