public class Partida {

    private Time timeCasa;
    private Time timeVisitante;
    private int golsCasa;
    private int golsVisitante;

    // CONSTRUTOR
    public Partida(Time timeCasa, Time timeVisitante, int golsCasa, int golsVisitante) {
        this.timeCasa = timeCasa;
        this.timeVisitante = timeVisitante;
        this.golsCasa = golsCasa;
        this.golsVisitante = golsVisitante;
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

    public void finalizar() {
        timeCasa.atualizarEstatisticas(golsCasa, golsVisitante);
        timeVisitante.atualizarEstatisticas(golsVisitante, golsCasa);
    }

    public void imprimir() {
        System.out.println(timeCasa.getNome() + " " + golsCasa + " x " + golsVisitante + " " + timeVisitante.getNome());
    }
}
