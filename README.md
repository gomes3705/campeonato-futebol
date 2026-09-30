# ⚽ Campeonato de Futebol

Sistema desenvolvido em **Java**, executado via console, para gerenciamento de um campeonato de futebol.

O projeto foi desenvolvido para a disciplina de **Projeto de Programação**, com foco na aplicação dos conceitos de **Programação Orientada a Objetos (POO)** trabalhados em aula.

## 📋 Sobre o projeto

O sistema permite cadastrar e remover times, registrar e editar partidas, gerar automaticamente os confrontos de um campeonato no formato de pontos corridos, consultar a classificação e salvar os dados do campeonato em um arquivo de texto.

A aplicação funciona por meio de um **menu no console**, no qual o usuário escolhe as operações que deseja realizar.

## 🏗️ Estrutura do projeto

O projeto é organizado nas seguintes classes:

### `Time`

Representa um time participante do campeonato.

A classe armazena:

* Nome do time
* Pontos
* Vitórias
* Empates
* Derrotas
* Gols marcados
* Gols sofridos

Também possui métodos responsáveis por atualizar e desfazer as estatísticas após o registro ou alteração de uma partida.

### `Partida`

Representa uma partida entre dois times.

A classe armazena:

* Time da casa
* Time visitante
* Placar da partida

Possui métodos para:

* Registrar o resultado
* Desfazer o resultado
* Editar o placar de uma partida já registrada

A edição de uma partida primeiro desfaz as estatísticas do resultado anterior e depois aplica o novo resultado.

### `Main`

É responsável pelo funcionamento principal do sistema.

A classe:

* Exibe o menu;
* Recebe os dados digitados pelo usuário;
* Cadastra e remove times;
* Registra e edita partidas;
* Gera os confrontos do campeonato;
* Exibe a classificação;
* Lista as partidas;
* Salva os dados em arquivo;
* Carrega um campeonato salvo anteriormente.

## 🏆 Regras do campeonato

O sistema utiliza o sistema tradicional de pontuação:

| Resultado | Pontos |
| --------- | -----: |
| Vitória   |      3 |
| Empate    |      1 |
| Derrota   |      0 |

Em caso de empate na pontuação, o **saldo de gols** é utilizado como critério de desempate.

**Saldo de gols = gols marcados − gols sofridos**

## ⚙️ Funcionalidades

O sistema possui as seguintes funcionalidades:

1. **Cadastrar time**
2. **Remover time**

   * A remoção é bloqueada caso o time já possua partidas registradas.
3. **Registrar partida**
4. **Editar partida**

   * Permite corrigir o placar de uma partida já registrada.
   * O resultado anterior é desfeito antes que o novo resultado seja aplicado.
5. **Gerar tabela de jogos**

   * Cria automaticamente os confrontos entre os times cadastrados.
   * Cada time enfrenta todos os outros uma vez.
6. **Visualizar classificação**

   * Exibe os times ordenados por pontos e saldo de gols.
7. **Listar partidas**

   * Exibe as partidas já registradas e seus respectivos placares.
8. **Salvar campeonato**

   * Salva os dados em um arquivo de texto.
9. **Carregar campeonato**

   * Recupera os dados de um campeonato salvo anteriormente.

## 📊 Exemplo de classificação

Após o registro das partidas, o sistema mantém as estatísticas de cada time para gerar a classificação do campeonato.

Exemplo:

```text
CLASSIFICAÇÃO

1. Flamengo
   Pontos: 15
   Vitórias: 5
   Empates: 0
   Derrotas: 0
   Saldo de gols: +8

2. Palmeiras
   Pontos: 10
   Vitórias: 3
   Empates: 1
   Derrotas: 1
   Saldo de gols: +3
```

## 💾 Persistência dos dados

Os dados do campeonato podem ser armazenados em um **arquivo de texto simples**.

As informações são separadas pelo caractere `;`.

Exemplo:

```text
TIMES
Flamengo;9;5;2;3;0;0
Palmeiras;4;3;3;1;1;1
PARTIDAS
Flamengo;Palmeiras;2;1
```

O arquivo contém duas partes principais:

* `TIMES` → informações e estatísticas dos times;
* `PARTIDAS` → partidas registradas e seus placares.

Dessa forma, o campeonato pode ser salvo e carregado posteriormente.

## 📚 Conceitos de POO

O projeto foi desenvolvido utilizando conceitos de **Programação Orientada a Objetos** apresentados durante as aulas.

Entre eles:

* **Encapsulamento**
* **Abstração**
* **Herança**
* **Polimorfismo**
* **Classes e objetos**
* **Arrays**
* **Construtores**
* **Métodos**

Os conceitos são utilizados para organizar as informações e comportamentos do sistema de forma estruturada.

## ▶️ Como executar

É necessário ter o **Java (JDK)** instalado.

No terminal, dentro da pasta do projeto, compile os arquivos:

```bash
javac Time.java Partida.java Main.java
```

Depois, execute o programa:

```bash
java Main
```

## 👥 Autores

**Gabriel Gomes**
**Lyan Gabriel**
**Vitor Marcelo**

Projeto desenvolvido para a disciplina de **Projeto de Programação**.
