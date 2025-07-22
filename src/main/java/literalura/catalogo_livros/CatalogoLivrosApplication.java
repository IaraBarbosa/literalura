package literalura.catalogo_livros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogoLivrosApplication implements CommandLineRunner {

	@Autowired
	private Menu menu;


	public static void main(String[] args) {
		SpringApplication.run(CatalogoLivrosApplication.class, args);
	}

	@Override
	public void run(String... args) {
		menu.processaOpcao();
	}
}

