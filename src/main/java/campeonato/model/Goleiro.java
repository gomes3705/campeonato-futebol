package campeonato.model;

/**
 * Goleiro: jogador que, além dos gols, registra defesas.
 *
 * @author Gabriel Gomes, Lyan Gabriel e Vitor Marcelo
 */
// HERANÇA
public class Goleiro extends Jogador {

    // ENCAPSULAMENTO
    private int defesas;

    // CONSTRUTOR
    public Goleiro(String nome, int idade, int numeroCamisa) {
        super(nome, idade, "Goleiro", numeroCamisa);
        this.defesas = 0;
    }

    public void defender() {
        defesas++;
    }

    public int getDefesas() {
        return defesas;
    }

    // SOBRESCRITA: a posição do goleiro é sempre "Goleiro" e não pode ser alterada
    @Override
    public void setPosicao(String posicao) {
    }

    @Override
    public String getTipo() {
        return "Goleiro";
    }

    // POLIMORFISMO
    // SOBRESCRITA
    @Override
    public String apresentar() {
        return super.apresentar() + ", " + defesas + " defesas realizadas";
    }
}
