public abstract class Pessoa {

    // ENCAPSULAMENTO
    protected String nome;
    protected int idade;

    // CONSTRUTOR
    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    // ABSTRAÇÃO
    public abstract void apresentar();
}
