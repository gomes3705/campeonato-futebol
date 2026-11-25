package campeonato.model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Time do campeonato: técnico, elenco e estatísticas da classificação.
 * As estatísticas são JavaFX Properties para que a TableView acompanhe as mudanças.
 *
 * @author Gabriel Gomes, Lyan Gabriel e Vitor Marcelo
 */
public class Time {

    public static final int MAX_JOGADORES = 30;

    private final StringProperty nome = new SimpleStringProperty();
    private final Tecnico tecnico;
    private final IntegerProperty pontos = new SimpleIntegerProperty(0);
    private final IntegerProperty vitorias = new SimpleIntegerProperty(0);
    private final IntegerProperty empates = new SimpleIntegerProperty(0);
    private final IntegerProperty derrotas = new SimpleIntegerProperty(0);
    private final IntegerProperty golsPro = new SimpleIntegerProperty(0);
    private final IntegerProperty golsContra = new SimpleIntegerProperty(0);

    // Valores calculados: atualizam sozinhos quando as estatísticas mudam (somente leitura)
    private final ReadOnlyIntegerWrapper jogos = new ReadOnlyIntegerWrapper();
    private final ReadOnlyIntegerWrapper saldoGols = new ReadOnlyIntegerWrapper();

    // COLEÇÃO: antes era um array de 30 posições
    private final ObservableList<Jogador> elenco = FXCollections.observableArrayList();

    // CONSTRUTOR
    public Time(String nome, Tecnico tecnico) {
        this.nome.set(validarNome(nome));
        this.tecnico = tecnico;
        jogos.bind(Bindings.createIntegerBinding(
                () -> vitorias.get() + empates.get() + derrotas.get(), vitorias, empates, derrotas));
        saldoGols.bind(Bindings.createIntegerBinding(
                () -> golsPro.get() - golsContra.get(), golsPro, golsContra));
    }

    public static String validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do time não pode ficar vazio!");
        }
        return nome.trim();
    }

    /** Adiciona um jogador ao elenco, respeitando o limite de 30 e a camisa única no time. */
    public void adicionarJogador(Jogador jogador) {
        if (elenco.size() >= MAX_JOGADORES) {
            throw new IllegalStateException("Elenco cheio! O jogador não foi cadastrado.");
        }
        if (numeroCamisaEmUso(jogador.getNumeroCamisa(), null)) {
            throw new IllegalArgumentException(
                    "A camisa " + jogador.getNumeroCamisa() + " já está em uso neste time!");
        }
        elenco.add(jogador);
    }

    public void removerJogador(Jogador jogador) {
        elenco.remove(jogador);
    }

    /** Altera os dados de um jogador. Valida tudo antes de modificar, para não alterar pela metade. */
    public void atualizarJogador(Jogador jogador, String nome, int idade, String posicao, int numeroCamisa) {
        String nomeValido = Pessoa.validarNome(nome);
        int idadeValida = Pessoa.validarIdade(idade);
        String posicaoValida = Jogador.validarPosicao(posicao);
        int camisaValida = Jogador.validarNumeroCamisa(numeroCamisa);
        if (numeroCamisaEmUso(camisaValida, jogador)) {
            throw new IllegalArgumentException("A camisa " + camisaValida + " já está em uso neste time!");
        }
        jogador.setNome(nomeValido);
        jogador.setIdade(idadeValida);
        jogador.setPosicao(posicaoValida);
        jogador.setNumeroCamisa(camisaValida);
    }

    /** Verifica se o número já pertence a outro jogador do time (o jogador "ignorado" não conta). */
    public boolean numeroCamisaEmUso(int numero, Jogador ignorado) {
        for (Jogador j : elenco) {
            if (j != ignorado && j.getNumeroCamisa() == numero) {
                return true;
            }
        }
        return false;
    }

    public void atualizarEstatisticas(int golsFeitos, int golsSofridos) {

        golsPro.set(golsPro.get() + golsFeitos);
        golsContra.set(golsContra.get() + golsSofridos);

        if (golsFeitos > golsSofridos) {
            vitorias.set(vitorias.get() + 1);
            pontos.set(pontos.get() + 3);
        } else if (golsFeitos == golsSofridos) {
            empates.set(empates.get() + 1);
            pontos.set(pontos.get() + 1);
        } else {
            derrotas.set(derrotas.get() + 1);
        }
    }

    public String getNome() {
        return nome.get();
    }

    /** Renomeia o time e mantém o nome exibido pelo técnico em dia. */
    public void setNome(String novoNome) {
        nome.set(validarNome(novoNome));
        tecnico.setTimeComandado(nome.get());
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public ObservableList<Jogador> getElenco() {
        return elenco;
    }

    public int getPontos() {
        return pontos.get();
    }

    public int getVitorias() {
        return vitorias.get();
    }

    public int getEmpates() {
        return empates.get();
    }

    public int getDerrotas() {
        return derrotas.get();
    }

    public int getJogos() {
        return jogos.get();
    }

    public int getGolsPro() {
        return golsPro.get();
    }

    public int getGolsContra() {
        return golsContra.get();
    }

    public int getSaldoGols() {
        return saldoGols.get();
    }

    // Métodos xxxProperty(): usados pela TableView (PropertyValueFactory) e pelos bindings
    public StringProperty nomeProperty() {
        return nome;
    }

    public IntegerProperty pontosProperty() {
        return pontos;
    }

    public IntegerProperty vitoriasProperty() {
        return vitorias;
    }

    public IntegerProperty empatesProperty() {
        return empates;
    }

    public IntegerProperty derrotasProperty() {
        return derrotas;
    }

    public IntegerProperty golsProProperty() {
        return golsPro;
    }

    public IntegerProperty golsContraProperty() {
        return golsContra;
    }

    public ReadOnlyIntegerProperty jogosProperty() {
        return jogos.getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty saldoGolsProperty() {
        return saldoGols.getReadOnlyProperty();
    }

    // Usado pelo ComboBox para exibir o time pelo nome
    @Override
    public String toString() {
        return getNome();
    }
}
