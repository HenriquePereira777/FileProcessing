import model.WordCount;
import service.WordFrequencyService;

import java.util.List;

public class Main {

  public static void main(String[] args) {

    // Permite processar múltiplos arquivos via linha de comando
    // Caso nenhum seja informado, utiliza um arquivo padrão
    String[] filePaths = args.length > 0
            ? args
            : new String[]{
            "src/files/words_1_000_000_000-001.txt"
    };

    WordFrequencyService service = new WordFrequencyService();

    for (String filePath : filePaths) {

      Runtime runtime = Runtime.getRuntime();

      // Solicita limpeza de memória antes da medição
      runtime.gc();

      long memoryBefore =
              runtime.totalMemory() - runtime.freeMemory();

      long start = System.currentTimeMillis();

      List<WordCount> result =
              service.processFile(filePath);

      long end = System.currentTimeMillis();

      long memoryAfter =
              runtime.totalMemory() - runtime.freeMemory();

      System.out.println("Arquivo: " + filePath);
      System.out.println("Tempo: " + (end - start) + " ms");

      // Exibe as 20 palavras mais frequentes
      result.forEach(System.out::println);

      long memUsedMB =
              (memoryAfter - memoryBefore) / (1024 * 1024);

      System.out.println(
              "[RAM usada: ~" + memUsedMB + " MB]"
      );

      System.out.println();
    }
  }
}