package campeonato.model;

/**
 * Jogador de um time.
 *
 * @author Gabriel Gomes, Lyan Gabriel e Vitor Marcelo
 */
public class Jogador extends Pessoa {

    // ENCAPSULAMENTO
    private String posicao;
    private int numeroCamisa;
    private int golsMarcados;

    // CONSTRUTOR
    // HERANÇA
    public Jogador(String nome, int idade, String posicao, int numeroCamisa) {
        super(nome, idade);
        this.posicao = validarPosicao(posicao);
        this.numeroCamisa = validarNumeroCamisa(numeroCamisa);
        this.golsMarcados = 0;
    }

    public void marcarGol() {
        golsMarcados++;
    }

    public int getGolsMarcados() {
        return golsMarcados;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = validarPosicao(posicao);
    }

    public int getNumeroCamisa() {
        return numeroCamisa;
    }

    public void setNumeroCamisa(int numeroCamisa) {
        this.numeroCamisa = validarNumeroCamisa(numeroCamisa);
    }

    // POLIMORFISMO: o Goleiro sobrescreve este método
    public String getTipo() {
        return "Jogador";
    }

    public static String validarPosicao(String posicao) {
        if (posicao == null || posicao.trim().isEmpty()) {
            throw new IllegalArgumentException("A posição não pode ficar vazia!");
        }
        return posicao.trim();
    }

    public static int validarNumeroCamisa(int numeroCamisa) {
        if (numeroCamisa <= 0) {
            throw new IllegalArgumentException("O número da camisa deve ser maior que zero!");
        }
        return numeroCamisa;
    }

    // POLIMORFISMO
    // SOBRESCRITA
    @Override
    public String apresentar() {
        return getNome() + ", " + getIdade() + " anos, joga de " + posicao
                + ", camisa " + numeroCamisa + ", " + golsMarcados + " gols marcados";
    }
}
