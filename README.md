# ⚽ Campeonato de Futebol

Sistema em Java, executado via console, para gerenciamento de um campeonato de futebol.

Desenvolvido para a disciplina de Projeto de Programação, aplicando os conceitos de Programação Orientada a Objetos (POO) trabalhados em aula: abstração, encapsulamento, herança, polimorfismo (sobrecarga e sobrescrita) e arrays.

## Estrutura do projeto

```
backend/    → classes Java (lógica do sistema)
frontend/   → html, css e js (interface visual, não conectada ao backend)
```

### `backend/`

- **Pessoa** (abstrata) — nome e idade, com o método `apresentar()` abstrato.
- **Jogador** `extends Pessoa` — posição, número da camisa, gols marcados.
- **Goleiro** `extends Jogador` — herança em dois níveis; adiciona defesas.
- **Tecnico** `extends Pessoa` — time que comanda.
- **Time** — guarda o elenco num array de `Jogador`, além das estatísticas (pontos, vitórias, empates, derrotas, gols).
- **Partida** — confronto entre dois times, com sobrecarga no método `registrarGol()`.
- **Main** — menu no console que liga tudo.

### `frontend/`

Interface visual simples (HTML, CSS e JS puro), só pra ter algo pra mostrar no navegador. Não se conecta ao backend Java — guarda os dados em memória, no próprio JavaScript.

## Regras de pontuação

| Resultado | Pontos |
| --------- | -----: |
| Vitória   |      3 |
| Empate    |      1 |
| Derrota   |      0 |

Critério de desempate: saldo de gols.

## Funcionalidades (backend)

1. Cadastrar time
2. Cadastrar jogador (normal ou goleiro) em um time
3. Ver elenco de um time
4. Registrar partida
5. Ver classificação, ordenada por pontos e saldo de gols

## Como executar o backend

```bash
cd backend
javac Pessoa.java Jogador.java Goleiro.java Tecnico.java Time.java Partida.java Main.java
java Main
```

## Como executar o frontend

Abrir o arquivo `frontend/index.html` direto no navegador.

## Autores

Gabriel Gomes, Lyan Gabriel e Vitor Marcelo

Projeto desenvolvido para a disciplina de Projeto de Programação.