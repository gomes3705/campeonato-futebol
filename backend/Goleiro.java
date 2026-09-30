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

    // POLIMORFISMO
    // SOBRESCRITA
    @Override
    public void apresentar() {
        super.apresentar();
        System.out.println("   Defesas realizadas: " + defesas);
    }
}
