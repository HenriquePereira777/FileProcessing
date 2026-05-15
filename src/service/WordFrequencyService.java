package service;

import model.WordCount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordFrequencyService {

    public List<WordCount> processFile(String filePath) {

        // Armazena cada palavra e sua frequência
        Map<String, Integer> wordFrequency = new HashMap<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(filePath), 65536)) {

            String line;
            long linesProcessed = 0;

            // Lê o arquivo linha por linha para evitar carregar tudo na memória
            while ((line = br.readLine()) != null) {

                if (!line.isEmpty()) {

                    // Incrementa a frequência da palavra
                    wordFrequency.merge(line, 1, Integer::sum);
                }

                linesProcessed++;

                // Log de progresso para arquivos grandes
                if (linesProcessed % 1_000_000 == 0) {
                    System.out.println("Processed lines: " + linesProcessed);
                }
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Error reading file: " + filePath, e
            );
        }

        return getTopWords(wordFrequency);
    }

    private List<WordCount> getTopWords(
            Map<String, Integer> wordFrequency) {

        // Min-heap limitado aos 20 maiores valores
        PriorityQueue<Map.Entry<String, Integer>> heap =
                new PriorityQueue<>(Map.Entry.comparingByValue());

        for (Map.Entry<String, Integer> entry
                : wordFrequency.entrySet()) {

            heap.offer(entry);

            // Remove o menor elemento quando passar de 20
            if (heap.size() > 20) {
                heap.poll();
            }
        }

        List<WordCount> result = new ArrayList<>();

        // Converte os dados do heap para a lista final
        while (!heap.isEmpty()) {

            Map.Entry<String, Integer> entry = heap.poll();

            result.add(new WordCount(
                    entry.getKey(),
                    entry.getValue()
            ));
        }

        // Inverte para retornar do maior para o menor
        Collections.reverse(result);

        return result;
    }
}