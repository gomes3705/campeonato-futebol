public class Tecnico extends Pessoa {

    private String timeComandado;

    // CONSTRUTOR
    public Tecnico(String nome, int idade, String timeComandado) {
        super(nome, idade);
        this.timeComandado = timeComandado;
    }

    // POLIMORFISMO
    // SOBRESCRITA
    @Override
    public void apresentar() {
        System.out.println(nome + ", " + idade + " anos, técnico do " + timeComandado);
    }
}
