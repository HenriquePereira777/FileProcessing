package model;

public class WordCount {

    // Representa a palavra encontrada no arquivo
    private final String word;

    // Quantidade de ocorrências da palavra
    private final int count;

    public WordCount(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {

        // Formato amigável para exibição no console
        return word + ": " + count;
    }
}