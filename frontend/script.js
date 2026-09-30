// array de times, igual no projeto Java
let times = [];

function cadastrarTime() {

    let nomeTime = document.getElementById("nomeTime").value;
    let nomeTecnico = document.getElementById("nomeTecnico").value;

    if (nomeTime === "" || nomeTecnico === "") {
        alert("Preencha o nome do time e do técnico!");
        return;
    }

    times.push({
        nome: nomeTime,
        tecnico: nomeTecnico,
        jogadores: [],
        pontos: 0,
        vitorias: 0,
        empates: 0,
        derrotas: 0,
        golsPro: 0,
        golsContra: 0
    });

    document.getElementById("nomeTime").value = "";
    document.getElementById("nomeTecnico").value = "";

    atualizarSelects();
    atualizarTabela();
}

function cadastrarJogador() {

    let indiceTime = document.getElementById("selectTime").value;
    let nome = document.getElementById("nomeJogador").value;
    let posicao = document.getElementById("posicaoJogador").value;

    if (indiceTime === "" || nome === "") {
        alert("Selecione o time e digite o nome do jogador!");
        return;
    }

    times[indiceTime].jogadores.push({ nome: nome, posicao: posicao });

    document.getElementById("nomeJogador").value = "";
    document.getElementById("posicaoJogador").value = "";

    alert("Jogador cadastrado!");
}

function registrarPartida() {

    let indiceCasa = document.getElementById("selectCasa").value;
    let indiceVisitante = document.getElementById("selectVisitante").value;
    let golsCasa = parseInt(document.getElementById("golsCasa").value);
    let golsVisitante = parseInt(document.getElementById("golsVisitante").value);

    if (indiceCasa === "" || indiceVisitante === "" || indiceCasa === indiceVisitante) {
        alert("Selecione dois times diferentes!");
        return;
    }

    if (isNaN(golsCasa) || isNaN(golsVisitante)) {
        alert("Digite o placar!");
        return;
    }

    atualizarEstatisticas(times[indiceCasa], golsCasa, golsVisitante);
    atualizarEstatisticas(times[indiceVisitante], golsVisitante, golsCasa);

    document.getElementById("golsCasa").value = "";
    document.getElementById("golsVisitante").value = "";

    atualizarTabela();
}

function atualizarEstatisticas(time, golsFeitos, golsSofridos) {

    time.golsPro += golsFeitos;
    time.golsContra += golsSofridos;

    if (golsFeitos > golsSofridos) {
        time.vitorias++;
        time.pontos += 3;
    } else if (golsFeitos === golsSofridos) {
        time.empates++;
        time.pontos += 1;
    } else {
        time.derrotas++;
    }
}

function atualizarSelects() {

    let selects = [
        document.getElementById("selectTime"),
        document.getElementById("selectCasa"),
        document.getElementById("selectVisitante")
    ];

    selects.forEach(function (select) {
        select.innerHTML = "";
        times.forEach(function (time, indice) {
            let opcao = document.createElement("option");
            opcao.value = indice;
            opcao.text = time.nome;
            select.appendChild(opcao);
        });
    });
}

function atualizarTabela() {

    // ordena por pontos, igual no Java (bubble sort bem simples)
    let ordenados = times.slice();

    for (let i = 0; i < ordenados.length - 1; i++) {
        for (let j = 0; j < ordenados.length - 1 - i; j++) {
            if (ordenados[j + 1].pontos > ordenados[j].pontos) {
                let aux = ordenados[j];
                ordenados[j] = ordenados[j + 1];
                ordenados[j + 1] = aux;
            }
        }
    }

    let tabela = document.getElementById("tabelaClassificacao");
    tabela.innerHTML = "<tr><th>Time</th><th>Pts</th><th>V</th><th>E</th><th>D</th><th>SG</th></tr>";

    ordenados.forEach(function (time) {
        let saldoGols = time.golsPro - time.golsContra;
        let linha = "<tr><td>" + time.nome + "</td><td>" + time.pontos + "</td><td>" + time.vitorias
                + "</td><td>" + time.empates + "</td><td>" + time.derrotas + "</td><td>" + saldoGols + "</td></tr>";
        tabela.innerHTML += linha;
    });
}
