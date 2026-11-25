package campeonato.model;

/**
 * Técnico responsável por um time.
 *
 * @author Gabriel Gomes, Lyan Gabriel e Vitor Marcelo
 */
public class Tecnico extends Pessoa {

    private String timeComandado;

    // CONSTRUTOR
    public Tecnico(String nome, int idade, String timeComandado) {
        super(nome, idade);
        this.timeComandado = timeComandado;
    }

    public void setTimeComandado(String timeComandado) {
        this.timeComandado = timeComandado;
    }

    // POLIMORFISMO
    // SOBRESCRITA
    @Override
    public String apresentar() {
        return getNome() + ", " + getIdade() + " anos, técnico do " + timeComandado;
    }
}
