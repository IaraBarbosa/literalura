package literalura.catalogo_livros.utils;

import java.util.IntSummaryStatistics;
import java.util.List;

public class Estatisticas {

    public static <T> void retornaEstatisticas(List<T> T, String message) {
        IntSummaryStatistics statisticas = T.stream()
                .mapToInt(x -> 1)
                .summaryStatistics();

        System.out.println("Total de " + message + ": " + statisticas.getCount() + "\n");
    }
}
