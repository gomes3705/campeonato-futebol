package campeonato.model;

/**
 * Confronto entre dois times. Nasce pendente e passa a "realizada" quando o resultado é registrado.
 *
 * @author Gabriel Gomes, Lyan Gabriel e Vitor Marcelo
 */
public class Partida {

    private final Time timeCasa;
    private final Time timeVisitante;
    private int golsCasa;
    private int golsVisitante;
    private boolean realizada;

    // CONSTRUTOR
    public Partida(Time timeCasa, Time timeVisitante) {
        if (timeCasa == timeVisitante) {
            throw new IllegalArgumentException("Um time não pode jogar contra ele mesmo!");
        }
        this.timeCasa = timeCasa;
        this.timeVisitante = timeVisitante;
        this.realizada = false;
    }

    // SOBRECARGA
    public void registrarGol(Jogador jogador) {
        registrarGol(jogador, 1);
    }

    // SOBRECARGA
    public void registrarGol(Jogador jogador, int quantidadeDeGols) {
        for (int i = 0; i < quantidadeDeGols; i++) {
            jogador.marcarGol();
        }
    }

    /** Registra o placar e atualiza as estatísticas dos dois times. Só pode ser feito uma vez. */
    public void finalizar(int golsCasa, int golsVisitante) {
        if (realizada) {
            throw new IllegalStateException("O resultado desta partida já foi registrado!");
        }
        if (golsCasa < 0 || golsVisitante < 0) {
            throw new IllegalArgumentException("O placar não pode ter gols negativos!");
        }
        this.golsCasa = golsCasa;
        this.golsVisitante = golsVisitante;
        this.realizada = true;
        timeCasa.atualizarEstatisticas(golsCasa, golsVisitante);
        timeVisitante.atualizarEstatisticas(golsVisitante, golsCasa);
    }

    public boolean envolve(Time time) {
        return timeCasa == time || timeVisitante == time;
    }

    public Time getTimeCasa() {
        return timeCasa;
    }

    public Time getTimeVisitante() {
        return timeVisitante;
    }

    public boolean isRealizada() {
        return realizada;
    }

    public String getPlacar() {
        return realizada ? golsCasa + " x " + golsVisitante : "-";
    }

    @Override
    public String toString() {
        if (realizada) {
            return timeCasa.getNome() + " " + golsCasa + " x " + golsVisitante + " " + timeVisitante.getNome();
        }
        return timeCasa.getNome() + " x " + timeVisitante.getNome();
    }
}
