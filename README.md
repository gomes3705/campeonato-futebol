# ⚽ Campeonato de Futebol (JavaFX)

Aplicação desktop em **JavaFX** para gerenciar um campeonato de futebol: times, jogadores, partidas e classificação.

Evolução do projeto em console da disciplina de Projeto de Programação. Agora usa o padrão **MVC**, telas em **FXML**, estilo em **CSS** externo e dados em **coleções Java**, tudo guardado em memória (nada é salvo em arquivo ou banco).

## Funcionalidades

- **Início**: menu de navegação e totais de times, jogadores, partidas realizadas e pendentes.
- **Times**: cadastrar, editar, excluir e listar. Um time que já disputou partidas não pode ser excluído. Limite de 10 times e nomes únicos.
- **Jogadores**: cadastrar jogadores e goleiros em um time, editar, remover e consultar o elenco (limite de 30). Não permite camisa repetida no mesmo time. Goleiros mantêm as defesas; jogadores, os gols (`+1 gol` / `+1 defesa`).
- **Partidas**: gerar os confrontos (todos contra todos, turno único) sem repetir os já existentes, listar as pendentes na ordem de realização, registrar resultados e consultar o histórico. Uma partida só aceita resultado uma vez.
- **Classificação**: tabela com pontos, jogos, vitórias, empates, derrotas, gols pró, gols contra e saldo de gols.

### Regras de pontuação (mantidas do projeto original)

| Resultado | Pontos |
| --------- | -----: |
| Vitória   |      3 |
| Empate    |      1 |
| Derrota   |      0 |

Critério de classificação: pontos e, em caso de empate, saldo de gols.

## Estrutura MVC

```
src/main/java/
├── module-info.java
└── campeonato/
    ├── MainApp.java                 → Application: cria Stage, Scene, carrega o FXML e o CSS
    ├── model/                       → MODEL (não conhece View nem Controller)
    │   ├── Pessoa.java              (abstrata) nome e idade
    │   ├── Jogador.java             extends Pessoa
    │   ├── Goleiro.java             extends Jogador
    │   ├── Tecnico.java             extends Pessoa
    │   ├── Time.java                elenco e estatísticas
    │   ├── Partida.java             confronto entre dois times
    │   └── Campeonato.java          coleções e regras do campeonato
    └── controller/                  → CONTROLLER (eventos, Model e atualização da tela)
        ├── PrincipalController.java     menu e navegação entre telas
        ├── InicioController.java
        ├── TimesController.java
        ├── JogadoresController.java
        ├── PartidasController.java
        ├── ClassificacaoController.java
        └── Mensagem.java                exibe mensagens de erro/sucesso
src/main/resources/campeonato/
├── view/                            → VIEW (apenas estrutura, sem regra de negócio)
│   ├── principal.fxml  inicio.fxml  times.fxml
│   └── jogadores.fxml  partidas.fxml  classificacao.fxml
└── css/style.css                    → CSS (visual separado da estrutura e da lógica)
```

## Conteúdos utilizados

**JavaFX / MVC / FXML / CSS**

- `Application`, `Stage`, `Scene`, `Parent` e `FXMLLoader` (em `MainApp` e na navegação do `PrincipalController`).
- Arquivos FXML com `fx:controller`, `fx:id` e `onAction`; campos e métodos ligados por `@FXML`; método `initialize()`.
- `ObservableList` e `FXCollections` ligados à `TableView`/`TableColumn` e ao `ComboBox`.
- JavaFX Properties em `Time` (pontos, vitórias, gols...) e **Property Binding** (totais, botões habilitados conforme a seleção, contador do elenco).
- Layouts `BorderPane`, `VBox`, `HBox`, `StackPane`; componentes `Button`, `ToggleButton`, `Label`, `TextField`, `ComboBox`, `TableView`.
- CSS externo vinculado com `scene.getStylesheets().add(...)`: seletores `.root`, por classe e por ID, pseudo-classes `:hover`, `:focused`, `:pressed` e `:selected`, propriedades `-fx-*`, cores, fontes, bordas e cantos arredondados.

**Coleções Java** (todas dentro de `Campeonato`, a única fonte de dados compartilhada pelas telas)

| Coleção | Uso |
| ------- | --- |
| `ObservableList<Time>` (`FXCollections`) | times cadastrados, ligados às tabelas |
| `ObservableList<Jogador>` | elenco de cada time (antes era um array de 30 posições) |
| `Queue<Partida>` / `ArrayDeque` | partidas pendentes, na ordem de realização |
| `List<Partida>` / `ArrayList` | histórico de partidas realizadas |
| `Set<String>` / `HashSet` | confrontos já gerados (evita partidas duplicadas) |
| `Map<String, Time>` / `HashMap` | busca do time pelo nome (evita nomes repetidos) |

**POO (mantida do projeto original)**: abstração (`Pessoa`), herança em dois níveis (`Goleiro` → `Jogador` → `Pessoa`), encapsulamento, polimorfismo (`apresentar()` e `getTipo()` sobrescritos) e sobrecarga (`Partida.registrarGol`).

## Como executar

Requisitos: **JDK 17 ou superior** e **IntelliJ IDEA** (o Maven já vem embutido). O JavaFX é baixado pelo Maven.

1. No IntelliJ: **File → Open** e selecione a pasta do projeto (ou o arquivo `pom.xml`). Aguarde o Maven carregar as dependências.
2. Confira em **File → Project Structure → Project** se o SDK é o JDK 17 ou superior.
3. Abra `src/main/java/campeonato/MainApp.java` e clique no botão ▶ ao lado do método `main`.

Alternativa pelo terminal (com Maven instalado):

```bash
mvn clean javafx:run
```

## Autores

Gabriel Gomes, Lyan Gabriel e Vitor Marcelo

Projeto desenvolvido para a disciplina de Projeto de Programação.
