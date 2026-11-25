package campeonato.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Guarda todos os dados do campeonato em memória e aplica as regras de negócio.
 * É a única fonte de dados: todas as telas usam a mesma instância.
 *
 * @author Gabriel Gomes, Lyan Gabriel e Vitor Marcelo
 */
public class Campeonato {

    // O array do projeto original comportava no máximo 10 times
    public static final int MAX_TIMES = 10;

    // List (observável, para a TableView): times cadastrados
    private final ObservableList<Time> times = FXCollections.observableArrayList();

    // Map: busca de time pelo nome (garante nomes únicos)
    private final Map<String, Time> timesPorNome = new HashMap<>();

    // Queue: partidas pendentes, na ordem em que devem acontecer
    private final Queue<Partida> partidasPendentes = new ArrayDeque<>();

    // List: histórico das partidas já realizadas
    private final List<Partida> historico = new ArrayList<>();

    // Set: confrontos já gerados (evita gerar o mesmo jogo duas vezes)
    private final Set<String> confrontos = new HashSet<>();

    // ---------------------------------------------------------------- times

    public Time cadastrarTime(String nomeTime, String nomeTecnico, int idadeTecnico) {
        if (times.size() >= MAX_TIMES) {
            throw new IllegalStateException("Limite de " + MAX_TIMES + " times atingido!");
        }
        String nome = Time.validarNome(nomeTime);
        if (timesPorNome.containsKey(chave(nome))) {
            throw new IllegalArgumentException("Já existe um time com esse nome!");
        }
        Tecnico tecnico = new Tecnico(nomeTecnico, idadeTecnico, nome);
        Time time = new Time(nome, tecnico);
        times.add(time);
        timesPorNome.put(chave(nome), time);
        return time;
    }

    public void editarTime(Time time, String novoNome, String nomeTecnico, int idadeTecnico) {
        String nome = Time.validarNome(novoNome);
        Time existente = timesPorNome.get(chave(nome));
        if (existente != null && existente != time) {
            throw new IllegalArgumentException("Já existe um time com esse nome!");
        }
        String nomeTecnicoValido = Pessoa.validarNome(nomeTecnico);
        int idadeValida = Pessoa.validarIdade(idadeTecnico);

        timesPorNome.remove(chave(time.getNome()));
        time.setNome(nome);
        timesPorNome.put(chave(nome), time);
        time.getTecnico().setNome(nomeTecnicoValido);
        time.getTecnico().setIdade(idadeValida);
        reconstruirConfrontos();
    }

    /** Um time que já disputou partidas não pode ser excluído, para não bagunçar a classificação. */
    public void excluirTime(Time time) {
        for (Partida p : historico) {
            if (p.envolve(time)) {
                throw new IllegalStateException(
                        "Não é possível excluir um time que já disputou partidas.");
            }
        }
        times.remove(time);
        timesPorNome.remove(chave(time.getNome()));
        partidasPendentes.removeIf(p -> p.envolve(time));
        reconstruirConfrontos();
    }

    // -------------------------------------------------------------- partidas

    /** Gera os confrontos que ainda não existem (todos contra todos, turno único). Retorna quantos foram criados. */
    public int gerarConfrontos() {
        if (times.size() < 2) {
            throw new IllegalStateException("Cadastre pelo menos 2 times!");
        }
        int novos = 0;
        for (int i = 0; i < times.size(); i++) {
            for (int j = i + 1; j < times.size(); j++) {
                Time casa = times.get(i);
                Time visitante = times.get(j);
                // add() devolve false se o confronto já estava no Set
                if (confrontos.add(chaveConfronto(casa, visitante))) {
                    partidasPendentes.offer(new Partida(casa, visitante));
                    novos++;
                }
            }
        }
        return novos;
    }

    /** Registra o resultado de uma partida pendente. Uma partida só aceita resultado uma vez. */
    public void registrarResultado(Partida partida, int golsCasa, int golsVisitante) {
        if (golsCasa < 0 || golsVisitante < 0) {
            throw new IllegalArgumentException("O placar não pode ter gols negativos!");
        }
        if (!partidasPendentes.remove(partida)) {
            throw new IllegalStateException("O resultado desta partida já foi registrado!");
        }
        partida.finalizar(golsCasa, golsVisitante);
        historico.add(partida);
    }

    /** Cópia das partidas pendentes, na ordem de realização. */
    public List<Partida> getPartidasPendentes() {
        return new ArrayList<>(partidasPendentes);
    }

    /** Cópia do histórico de partidas realizadas. */
    public List<Partida> getHistorico() {
        return new ArrayList<>(historico);
    }

    // ---------------------------------------------------------- classificação

    /** Ordena por pontos e, em caso de empate, por saldo de gols (critério do projeto original). */
    public List<Time> getClassificacao() {
        List<Time> tabela = new ArrayList<>(times);
        tabela.sort((a, b) -> {
            int porPontos = Integer.compare(b.getPontos(), a.getPontos());
            if (porPontos != 0) {
                return porPontos;
            }
            return Integer.compare(b.getSaldoGols(), a.getSaldoGols());
        });
        return tabela;
    }

    // ----------------------------------------------------------------- totais

    public ObservableList<Time> getTimes() {
        return times;
    }

    public int getTotalJogadores() {
        int total = 0;
        for (Time t : times) {
            total += t.getElenco().size();
        }
        return total;
    }

    public int getTotalPartidasRealizadas() {
        return historico.size();
    }

    public int getTotalPartidasPendentes() {
        return partidasPendentes.size();
    }

    // ------------------------------------------------------------ auxiliares

    private String chave(String nome) {
        return nome.trim().toLowerCase();
    }

    // O confronto A x B é o mesmo que B x A, então a chave não depende da ordem
    private String chaveConfronto(Time a, Time b) {
        String nomeA = chave(a.getNome());
        String nomeB = chave(b.getNome());
        if (nomeA.compareTo(nomeB) <= 0) {
            return nomeA + "|" + nomeB;
        }
        return nomeB + "|" + nomeA;
    }

    // Refaz o Set depois que um time é renomeado ou excluído
    private void reconstruirConfrontos() {
        confrontos.clear();
        for (Partida p : partidasPendentes) {
            confrontos.add(chaveConfronto(p.getTimeCasa(), p.getTimeVisitante()));
        }
        for (Partida p : historico) {
            confrontos.add(chaveConfronto(p.getTimeCasa(), p.getTimeVisitante()));
        }
    }
}
