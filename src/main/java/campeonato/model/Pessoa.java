package campeonato.model;

/**
 * Classe abstrata que representa uma pessoa do campeonato (jogador ou técnico).
 *
 * @author Gabriel Gomes, Lyan Gabriel e Vitor Marcelo
 */
public abstract class Pessoa {

    // ENCAPSULAMENTO
    private String nome;
    private int idade;

    // CONSTRUTOR
    public Pessoa(String nome, int idade) {
        this.nome = validarNome(nome);
        this.idade = validarIdade(idade);
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setNome(String nome) {
        this.nome = validarNome(nome);
    }

    public void setIdade(int idade) {
        this.idade = validarIdade(idade);
    }

    /** Regra de negócio: o nome não pode ficar vazio. Retorna o nome sem espaços nas pontas. */
    public static String validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ficar vazio!");
        }
        return nome.trim();
    }

    /** Regra de negócio: a idade deve ser maior que zero. */
    public static int validarIdade(int idade) {
        if (idade <= 0) {
            throw new IllegalArgumentException("A idade deve ser maior que zero!");
        }
        return idade;
    }

    // ABSTRAÇÃO
    // Retorna o texto de apresentação (antes era impresso no console, agora é exibido na tela).
    public abstract String apresentar();
}
