import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    // ARRAY
    static Time[] times = new Time[10];
    static int totalTimes = 0;

    public static void cadastrarTime() {

        // VALIDAÇÃO: o array comporta no máximo 10 times
        if (totalTimes >= times.length) {
            System.out.println("Limite de 10 times atingido!");
            return;
        }

        System.out.print("Nome do time: ");
        String nomeTime = scanner.nextLine().trim();
        if (nomeTime.isEmpty()) {
            System.out.println("O nome do time não pode ficar vazio!");
            return;
        }

        System.out.print("Nome do técnico: ");
        String nomeTecnico = scanner.nextLine().trim();
        if (nomeTecnico.isEmpty()) {
            System.out.println("O nome do técnico não pode ficar vazio!");
            return;
        }

        System.out.print("Idade do técnico: ");
        int idadeTecnico = Integer.parseInt(scanner.nextLine());
        if (idadeTecnico <= 0) {
            System.out.println("A idade deve ser maior que zero!");
            return;
        }

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
        if (numeroTime < 1 || numeroTime > totalTimes) {
            System.out.println("Número de time inválido!");
            return;
        }
        Time time = times[numeroTime - 1];

        System.out.print("É goleiro? (s/n): ");
        String resposta = scanner.nextLine();
        if (!resposta.equalsIgnoreCase("s") && !resposta.equalsIgnoreCase("n")) {
            System.out.println("Responda com s ou n!");
            return;
        }

        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("O nome do jogador não pode ficar vazio!");
            return;
        }

        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());
        if (idade <= 0) {
            System.out.println("A idade deve ser maior que zero!");
            return;
        }

        System.out.print("Número da camisa: ");
        int camisa = Integer.parseInt(scanner.nextLine());
        if (camisa <= 0) {
            System.out.println("O número da camisa deve ser maior que zero!");
            return;
        }

        // POLIMORFISMO
        Jogador jogador;

        if (resposta.equalsIgnoreCase("s")) {
            jogador = new Goleiro(nome, idade, camisa);
        } else {
            System.out.print("Posição: ");
            String posicao = scanner.nextLine().trim();
            if (posicao.isEmpty()) {
                System.out.println("A posição não pode ficar vazia!");
                return;
            }
            jogador = new Jogador(nome, idade, posicao, camisa);
        }

        if (time.adicionarJogador(jogador)) {
            System.out.println("Jogador cadastrado!");
        } else {
            System.out.println("Elenco cheio! O jogador não foi cadastrado.");
        }
    }

    public static void verElenco() {

        if (totalTimes == 0) {
            System.out.println("Nenhum time cadastrado!");
            return;
        }

        listarTimes();
        System.out.print("Número do time: ");
        int numeroTime = Integer.parseInt(scanner.nextLine());
        if (numeroTime < 1 || numeroTime > totalTimes) {
            System.out.println("Número de time inválido!");
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

        System.out.print("Número do time da casa: ");
        int casa = Integer.parseInt(scanner.nextLine());
        if (casa < 1 || casa > totalTimes) {
            System.out.println("Número de time da casa inválido!");
            return;
        }

        System.out.print("Número do time visitante: ");
        int visitante = Integer.parseInt(scanner.nextLine());
        if (visitante < 1 || visitante > totalTimes) {
            System.out.println("Número de time visitante inválido!");
            return;
        }
        if (casa == visitante) {
            System.out.println("Um time não pode jogar contra ele mesmo!");
            return;
        }

        System.out.print("Gols do " + times[casa - 1].getNome() + ": ");
        int golsCasa = Integer.parseInt(scanner.nextLine());

        System.out.print("Gols do " + times[visitante - 1].getNome() + ": ");
        int golsVisitante = Integer.parseInt(scanner.nextLine());
        if (golsCasa < 0 || golsVisitante < 0) {
            System.out.println("O placar não pode ter gols negativos!");
            return;
        }

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

        // ARRAY: copia os times para ordenar a tabela sem mudar a ordem do cadastro
        Time[] classificacao = new Time[totalTimes];
        for (int i = 0; i < totalTimes; i++) {
            classificacao[i] = times[i];
        }

        // CLASSIFICAÇÃO: ordena por pontos e, em caso de empate, por saldo de gols
        for (int i = 0; i < totalTimes - 1; i++) {
            for (int j = 0; j < totalTimes - 1 - i; j++) {
                boolean trocar = classificacao[j + 1].getPontos() > classificacao[j].getPontos();
                if (classificacao[j + 1].getPontos() == classificacao[j].getPontos()
                        && classificacao[j + 1].getSaldoGols() > classificacao[j].getSaldoGols()) {
                    trocar = true;
                }

                if (trocar) {
                    Time aux = classificacao[j];
                    classificacao[j] = classificacao[j + 1];
                    classificacao[j + 1] = aux;
                }
            }
        }

        System.out.println("--- CLASSIFICAÇÃO ---");
        for (int i = 0; i < totalTimes; i++) {
            Time t = classificacao[i];
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
