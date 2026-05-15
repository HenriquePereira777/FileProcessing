# Analisador de Frequência de Palavras

## Visão Geral

Processa arquivos `.txt` de qualquer tamanho e retorna as 20 palavras mais frequentes com seus respectivos contadores, respeitando **case sensitive** e sem normalização — conforme especificado no desafio.

---

## Tecnologias

- Java 21+
- `HashMap` para contagem
- `PriorityQueue` (min-heap tamanho 20) para top-K
- `BufferedReader` com buffer de 64KB para streaming

---

## Como Rodar

### Compilar
```bash
javac -d out src/model/WordCount.java src/service/WordFrequencyService.java src/Main.java
```

### Executar (um ou mais arquivos)
```bash
java -cp out Main src/files/words_1000.txt src/files/words_1000000.txt
```

### Executar com mais memória heap (para arquivos grandes)
```bash
java -Xmx4g -cp out Main src/files/words_1_000_000_000.txt
```

---

## Saída Esperada

```
Arquivo: caminho do arquirvo.txt
Tempo: 12 ms
casa: 87
sol: 54
azul: 43
...
[RAM usada: ~3 MB]
```

---

## Abordagem

### Leitura
`BufferedReader` com buffer de 64KB lê o arquivo linha a linha sem carregar o conteúdo inteiro em memória. Como cada linha contém exatamente uma palavra, não há parsing adicional.

### Contagem
`HashMap<String, Integer>` com `merge(word, 1, Integer::sum)` — O(1) amortizado por palavra.

### Top 20
`PriorityQueue` (min-heap) de tamanho fixo 20. Para cada palavra do mapa: insere e remove o menor se o heap passar de 20. Resultado: O(N log 20) ≈ O(N), muito mais eficiente que ordenar o mapa inteiro.

### Memória
A memória cresce proporcionalmente ao vocabulário único do arquivo, não ao número total de linhas. Um arquivo com 1 bilhão de linhas mas apenas 100.000 palavras únicas usa a mesma RAM que um arquivo de 100.000 linhas com o mesmo vocabulário.

Medição feita via `Runtime.getRuntime()` — diferença entre `totalMemory - freeMemory` antes e após o processamento.

---

## Resultados

| Arquivo            | Linhas          | Tempo    | RAM (aprox.) |
|--------------------|-----------------|----------|--------------|
| words_100.txt      | 100             | ~6 ms    | < 0 MB       |
| words_1k.txt       | 1.000           | ~9 ms    | < 0 MB       |
| words_10k.txt      | 10.000          | ~16 ms   | ~1 MB        |
| words_100k.txt     | 100.000         | ~45 ms   | ~5 MB        |
| words_1M.txt       | 1.000.000       | ~160 ms  | ~41 MB       |
| words_10M.txt      | 10.000.000      | ~899 ms  | ~60 MB       |
| words_100M.txt     | 100.000.000     | ~6726 ms | ~75 MB       |
| words_1B.txt       | 1.000.000.000   | ~70 s    | ~28 MB       |

> ⚠️ Os tempos acima pode ter uma margem de 1 ms;

---

## Complexidade

| Dimensão | Complexidade |
|----------|-------------|
| Tempo    | O(N log 20) ≈ O(N) |
| Memória  | O(V) onde V = vocabulário único |

---

## Estrutura do Projeto

```
src/
├── Main.java
├── model/
│   └── WordCount.java
└── service/
    └── WordFrequencyService.java
```
