import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    // ARRAY
    static Time[] times = new Time[10];
    static int totalTimes = 0;

    // Metodo auxiliar para leitura segura de inteiros (evita NumberFormatException)
    public static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                // Caso o usuario digite "40 anos", "40+Anos", etc., tenta extrair os digitos
                String apenasDigitos = entrada.replaceAll("[^0-9]", "");
                if (!apenasDigitos.isEmpty()) {
                    try {
                        return Integer.parseInt(apenasDigitos);
                    } catch (NumberFormatException ignored) {}
                }
                System.out.println("Entrada invalida! Digite apenas numeros inteiros.");
            }
        }
    }

    public static void cadastrarTime() {

        System.out.print("Nome do time: ");
        String nomeTime = scanner.nextLine().trim();

        System.out.print("Nome do tecnico: ");
        String nomeTecnico = scanner.nextLine().trim();

        int idadeTecnico = lerInteiro("Idade do tecnico: ");

        Tecnico tecnico = new Tecnico(nomeTecnico, idadeTecnico, nomeTime);
        Time time = new Time(nomeTime, tecnico);

        times[totalTimes] = time;
        totalTimes++;

        System.out.println("Time cadastrado!");
    }

    public static void listarTimes() {
        for (int i = 0; i < totalTimes; i++) {
            System.out.println((i + 1) + " - " + times[i].getNome());
        }
    }

    public static void cadastrarJogador() {

        if (totalTimes == 0) {
            System.out.println("Cadastre um time primeiro!");
            return;
        }

        listarTimes();
        int numeroTime = lerInteiro("Numero do time: ");

        if (numeroTime < 1 || numeroTime > totalTimes) {
            System.out.println("Numero do time invalido!");
            return;
        }

        Time time = times[numeroTime - 1];

        System.out.print("E goleiro? (s/n): ");
        String resposta = scanner.nextLine().trim();

        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();

        int idade = lerInteiro("Idade: ");
        int camisa = lerInteiro("Numero da camisa: ");

        // POLIMORFISMO
        Jogador jogador;

        if (resposta.equalsIgnoreCase("s")) {
            jogador = new Goleiro(nome, idade, camisa);
        } else {
            System.out.print("Posicao: ");
            String posicao = scanner.nextLine().trim();
            jogador = new Jogador(nome, idade, posicao, camisa);
        }

        time.adicionarJogador(jogador);
        System.out.println("Jogador cadastrado!");
    }

    public static void verElenco() {

        if (totalTimes == 0) {
            System.out.println("Nenhum time cadastrado!");
            return;
        }

        listarTimes();
        int numeroTime = lerInteiro("Numero do time: ");

        if (numeroTime < 1 || numeroTime > totalTimes) {
            System.out.println("Numero do time invalido!");
            return;
        }

        times[numeroTime - 1].listarElenco();
    }

    public static void registrarPartida() {

        if (totalTimes < 2) {
            System.out.println("Cadastre pelo menos 2 times!");
            return;
        }

        listarTimes();

        int casa = lerInteiro("Numero do time da casa: ");
        int visitante = lerInteiro("Numero do time visitante: ");

        if (casa < 1 || casa > totalTimes || visitante < 1 || visitante > totalTimes || casa == visitante) {
            System.out.println("Selecao de times invalida! Escolha dois times diferentes da lista.");
            return;
        }

        int golsCasa = lerInteiro("Gols do " + times[casa - 1].getNome() + ": ");
        int golsVisitante = lerInteiro("Gols do " + times[visitante - 1].getNome() + ": ");

        Partida partida = new Partida(times[casa - 1], times[visitante - 1], golsCasa, golsVisitante);
        partida.finalizar();
        partida.imprimir();

        System.out.println("Partida registrada!");
    }

    public static void verClassificacao() {

        if (totalTimes == 0) {
            System.out.println("Nenhum time cadastrado!");
            return;
        }

        for (int i = 0; i < totalTimes - 1; i++) {
            for (int j = 0; j < totalTimes - 1 - i; j++) {
                if (times[j + 1].getPontos() > times[j].getPontos()) {
                    Time aux = times[j];
                    times[j] = times[j + 1];
                    times[j + 1] = aux;
                }
            }
        }

        System.out.println("--- CLASSIFICACAO ---");
        for (int i = 0; i < totalTimes; i++) {
            Time t = times[i];
            System.out.println((i + 1) + " - " + t.getNome() + "  " + t.getPontos() + "pts  "
                    + t.getVitorias() + "V " + t.getEmpates() + "E " + t.getDerrotas() + "D  SG:" + t.getSaldoGols());
        }
    }

    public static void main(String[] args) {

        int opcao;

        do {
            System.out.println();
            System.out.println("===== CAMPEONATO DE FUTEBOL =====");
            System.out.println("1 - Cadastrar time");
            System.out.println("2 - Cadastrar jogador");
            System.out.println("3 - Ver elenco de um time");
            System.out.println("4 - Registrar partida");
            System.out.println("5 - Ver classificacao");
            System.out.println("0 - Sair");

            opcao = lerInteiro("Opcao: ");

            switch (opcao) {
                case 1:
                    cadastrarTime();
                    break;
                case 2:
                    cadastrarJogador();
                    break;
                case 3:
                    verElenco();
                    break;
                case 4:
                    registrarPartida();
                    break;
                case 5:
                    verClassificacao();
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }

        } while (opcao != 0);
    }
}
