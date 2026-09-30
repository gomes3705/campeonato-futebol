public class Jogador extends Pessoa {

    // ENCAPSULAMENTO
    private String posicao;
    private int numeroCamisa;
    private int golsMarcados;

    // CONSTRUTOR
    // HERANÇA
    public Jogador(String nome, int idade, String posicao, int numeroCamisa) {
        super(nome, idade);
        this.posicao = posicao;
        this.numeroCamisa = numeroCamisa;
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

    // POLIMORFISMO
    // SOBRESCRITA
    @Override
    public void apresentar() {
        System.out.println(nome + ", " + idade + " anos, joga de " + posicao
                + ", camisa " + numeroCamisa + ", " + golsMarcados + " gols marcados");
    }
}
