# Campeonato de Futebol

Sistema em Java, via console, para gerenciamento de um campeonato de futebol. Permite o cadastro e remoção de times, o registro e a edição de partidas, a geração automática da tabela de jogos (pontos corridos), a exibição da classificação e a persistência dos dados em arquivo.

Projeto desenvolvido para a matéria de Projeto de Programação, aplicando os conceitos de Programação Orientada a Objetos (POO).

## Como funciona

O projeto é dividido em 3 classes:

- **Time** — representa um time e suas estatísticas (pontos, vitórias, empates, derrotas, gols pró e gols contra). Contém `atualizarEstatisticas()`, responsável por aplicar a pontuação após uma partida, e `desfazerResultado()`, que reverte o efeito de uma partida (usado na edição de placar).
- **Partida** — representa o confronto entre dois times, com o placar. Tem `registrarResultado()`, `desfazerResultado()` e `editarPlacar()`, que juntos permitem corrigir o resultado de uma partida já registrada sem bagunçar as estatísticas.
- **Main** — controla o menu, lê as entradas do usuário e chama os métodos das outras classes. Também cuida da leitura/escrita do arquivo de save.

### Regras de pontuação

- Vitória: 3 pontos
- Empate: 1 ponto
- Derrota: 0 pontos
- Critério de desempate: saldo de gols

## Funcionalidades

1. Cadastrar time
2. Remover time (bloqueado se o time já tiver partidas registradas)
3. Registrar partida
4. Editar partida — corrige o placar de uma partida já lançada, desfazendo e reaplicando o efeito nas estatísticas
5. Gerar tabela de jogos por pontos corridos — cria automaticamente os confrontos entre todos os times cadastrados, cada um jogando contra todos os outros uma vez
6. Ver tabela de classificação, ordenada por pontos e saldo de gols
7. Listar partidas registradas
8. Salvar o campeonato em um arquivo de texto
9. Carregar um campeonato salvo anteriormente

## Persistência em arquivo

Os dados são salvos em formato texto simples, separados por `;`:

```
TIMES
Flamengo;9;5;2;3;0;0
Palmeiras;4;3;3;1;1;1
PARTIDAS
Flamengo;Palmeiras;2;1
```

## Como rodar

```bash
javac Time.java Partida.java Main.java
java Main
```

## Autores

Gabriel Gomes, Lyan Gabriel e Vitor Marcelo
