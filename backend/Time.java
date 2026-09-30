public class Time {

    private String nome;
    private Tecnico tecnico;
    private int pontos;
    private int vitorias;
    private int empates;
    private int derrotas;
    private int golsPro;
    private int golsContra;

    // ARRAY
    private Jogador[] elenco;
    private int totalJogadores;

    // CONSTRUTOR
    public Time(String nome, Tecnico tecnico) {
        this.nome = nome;
        this.tecnico = tecnico;
        this.elenco = new Jogador[30];
        this.totalJogadores = 0;
    }

    public void adicionarJogador(Jogador jogador) {
        if (totalJogadores < elenco.length) {
            elenco[totalJogadores] = jogador;
            totalJogadores++;
        } else {
            System.out.println("Elenco cheio!");
        }
    }

    // POLIMORFISMO
    public void listarElenco() {
        System.out.println("Técnico:");
        tecnico.apresentar();

        System.out.println("Elenco:");
        for (int i = 0; i < totalJogadores; i++) {
            elenco[i].apresentar();
        }
    }

    public void atualizarEstatisticas(int golsFeitos, int golsSofridos) {

        golsPro = golsPro + golsFeitos;
        golsContra = golsContra + golsSofridos;

        if (golsFeitos > golsSofridos) {
            vitorias++;
            pontos = pontos + 3;
        } else if (golsFeitos == golsSofridos) {
            empates++;
            pontos = pontos + 1;
        } else {
            derrotas++;
        }
    }

    public String getNome() {
        return nome;
    }

    public int getPontos() {
        return pontos;
    }

    public int getVitorias() {
        return vitorias;
    }

    public int getEmpates() {
        return empates;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public int getSaldoGols() {
        return golsPro - golsContra;
    }
}
