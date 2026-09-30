import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    // ARRAY
    static Time[] times = new Time[10];
    static int totalTimes = 0;

    public static void cadastrarTime() {

        System.out.print("Nome do time: ");
        String nomeTime = scanner.nextLine();

        System.out.print("Nome do técnico: ");
        String nomeTecnico = scanner.nextLine();

        System.out.print("Idade do técnico: ");
        int idadeTecnico = Integer.parseInt(scanner.nextLine());

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
        System.out.print("Número do time: ");
        int numeroTime = Integer.parseInt(scanner.nextLine());
        Time time = times[numeroTime - 1];

        System.out.print("É goleiro? (s/n): ");
        String resposta = scanner.nextLine();

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());

        System.out.print("Número da camisa: ");
        int camisa = Integer.parseInt(scanner.nextLine());

        // POLIMORFISMO
        Jogador jogador;

        if (resposta.equalsIgnoreCase("s")) {
            jogador = new Goleiro(nome, idade, camisa);
        } else {
            System.out.print("Posição: ");
            String posicao = scanner.nextLine();
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
        System.out.print("Número do time: ");
        int numeroTime = Integer.parseInt(scanner.nextLine());

        times[numeroTime - 1].listarElenco();
    }

    public static void registrarPartida() {

        if (totalTimes < 2) {
            System.out.println("Cadastre pelo menos 2 times!");
            return;
        }

        listarTimes();

        System.out.print("Número do time da casa: ");
        int casa = Integer.parseInt(scanner.nextLine());

        System.out.print("Número do time visitante: ");
        int visitante = Integer.parseInt(scanner.nextLine());

        System.out.print("Gols do " + times[casa - 1].getNome() + ": ");
        int golsCasa = Integer.parseInt(scanner.nextLine());

        System.out.print("Gols do " + times[visitante - 1].getNome() + ": ");
        int golsVisitante = Integer.parseInt(scanner.nextLine());

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

        System.out.println("--- CLASSIFICAÇÃO ---");
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
            System.out.println("5 - Ver classificação");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            opcao = Integer.parseInt(scanner.nextLine());

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
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }
}
