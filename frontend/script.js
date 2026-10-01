// Array de times, igual no projeto Java
let times = [];

// Controle de navegação dos cards (1 por página com animação)
let cardAtual = 0;
let isTransitioning = false;

/**
 * Realiza a troca animada entre os cards.
 * Combina movimentação horizontal (slide) e fade (opacidade).
 * @param {number} novoIndice - Índice do card de destino (0 a 3)
 */
function irParaCard(novoIndice) {
    if (novoIndice === cardAtual || isTransitioning) return;
    if (novoIndice < 0 || novoIndice > 3) return;

    isTransitioning = true;

    const cards = [
        document.getElementById("card-0"),
        document.getElementById("card-1"),
        document.getElementById("card-2"),
        document.getElementById("card-3")
    ];

    const cardSaindo = cards[cardAtual];
    const cardEntrando = cards[novoIndice];
    const avancando = novoIndice > cardAtual;

    // Define a classe de animação de saída (fade out + deslocamento)
    cardSaindo.className = "secao card " + (avancando ? "anim-out-left" : "anim-out-right");

    setTimeout(function () {
        // Esconde o card anterior
        cardSaindo.className = "secao card";

        // Prepara e anima a entrada do novo card (fade in + deslocamento)
        cardAtual = novoIndice;
        cardEntrando.className = "secao card ativo " + (avancando ? "anim-in-right" : "anim-in-left");

        // Atualiza a barra de progresso / abas do topo
        atualizarAbas();

        // Remove a classe de animação após conclusão
        setTimeout(function () {
            cardEntrando.className = "secao card ativo";
            isTransitioning = false;
        }, 260);

    }, 220);
}

/**
 * Atualiza os botões da barra superior (Stepper / Abas)
 */
function atualizarAbas() {
    for (let i = 0; i <= 3; i++) {
        let btn = document.getElementById("stepBtn" + i);
        if (btn) {
            if (i === cardAtual) {
                btn.classList.add("ativo");
            } else {
                btn.classList.remove("ativo");
            }
        }
    }
}

/**
 * Exibe notificação toast elegante sem travar a interface
 */
function mostrarNotificacao(mensagem, tipo) {
    const container = document.getElementById("toastContainer");
    if (!container) return;

    const toast = document.createElement("div");
    toast.className = "toast " + (tipo === "erro" ? "toast-erro" : "toast-sucesso");

    const icone = tipo === "erro" ? "⚠️" : "✅";
    toast.innerHTML = `<span>${icone}</span><span>${mensagem}</span>`;

    container.appendChild(toast);

    setTimeout(function () {
        toast.classList.add("toast-out");
        setTimeout(function () {
            if (toast.parentNode) {
                toast.parentNode.removeChild(toast);
            }
        }, 250);
    }, 3000);
}

// ==========================================
// AÇÕES DO CAMPEONATO
// ==========================================

function cadastrarTime() {
    let inputNome = document.getElementById("nomeTime");
    let inputTecnico = document.getElementById("nomeTecnico");

    let nomeTime = inputNome.value.trim();
    let nomeTecnico = inputTecnico.value.trim();

    if (nomeTime === "" || nomeTecnico === "") {
        mostrarNotificacao("Preencha o nome do time e do técnico!", "erro");
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

    inputNome.value = "";
    inputTecnico.value = "";

    atualizarSelects();
    atualizarTabela();

    mostrarNotificacao(`Time "${nomeTime}" cadastrado!`, "sucesso");

    // "adicionar um dá um fade pra abrir o outro" -> Avança para o card de cadastrar jogador com fade
    irParaCard(1);
}

function cadastrarJogador() {
    let select = document.getElementById("selectTime");
    let indiceTime = select.value;
    let inputNome = document.getElementById("nomeJogador");
    let inputPosicao = document.getElementById("posicaoJogador");

    let nome = inputNome.value.trim();
    let posicao = inputPosicao.value.trim();

    if (indiceTime === "" || indiceTime === null || nome === "") {
        mostrarNotificacao("Selecione um time e digite o nome do jogador!", "erro");
        return;
    }

    if (times.length === 0 || !times[indiceTime]) {
        mostrarNotificacao("Nenhum time válido disponível!", "erro");
        return;
    }

    times[indiceTime].jogadores.push({
        nome: nome,
        posicao: posicao || "Jogador"
    });

    inputNome.value = "";
    inputPosicao.value = "";

    mostrarNotificacao(`Jogador "${nome}" cadastrado no ${times[indiceTime].nome}!`, "sucesso");

    // "adicionar um dá um fade pra abrir o outro" -> Avança para o card de registrar partida com fade
    irParaCard(2);
}

function registrarPartida() {
    let selectCasa = document.getElementById("selectCasa");
    let selectVisitante = document.getElementById("selectVisitante");
    let inputGolsCasa = document.getElementById("golsCasa");
    let inputGolsVisitante = document.getElementById("golsVisitante");

    let indiceCasa = selectCasa.value;
    let indiceVisitante = selectVisitante.value;
    let golsCasa = parseInt(inputGolsCasa.value);
    let golsVisitante = parseInt(inputGolsVisitante.value);

    if (indiceCasa === "" || indiceVisitante === "" || indiceCasa === indiceVisitante) {
        mostrarNotificacao("Selecione dois times diferentes para a partida!", "erro");
        return;
    }

    if (isNaN(golsCasa) || isNaN(golsVisitante) || golsCasa < 0 || golsVisitante < 0) {
        mostrarNotificacao("Digite um placar válido com gols não negativos!", "erro");
        return;
    }

    let timeCasa = times[indiceCasa];
    let timeVisitante = times[indiceVisitante];

    atualizarEstatisticas(timeCasa, golsCasa, golsVisitante);
    atualizarEstatisticas(timeVisitante, golsVisitante, golsCasa);

    inputGolsCasa.value = "";
    inputGolsVisitante.value = "";

    atualizarTabela();

    mostrarNotificacao(`Partida salva: ${timeCasa.nome} ${golsCasa} x ${golsVisitante} ${timeVisitante.nome}!`, "sucesso");

    // "adicionar um dá um fade pra abrir o outro" -> Avança para a tabela de classificação com fade
    irParaCard(3);
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

    selects.forEach(function (select, selectIndex) {
        if (!select) return;
        select.innerHTML = "";

        if (times.length === 0) {
            let opcao = document.createElement("option");
            opcao.value = "";
            opcao.text = "Nenhum time cadastrado";
            select.appendChild(opcao);
            return;
        }

        times.forEach(function (time, indice) {
            let opcao = document.createElement("option");
            opcao.value = indice;
            opcao.text = time.nome;
            select.appendChild(opcao);
        });

        // Seleciona automaticamente o segundo time para o visitante se disponível
        if (selectIndex === 2 && times.length > 1) {
            select.selectedIndex = 1;
        }
    });
}

function atualizarTabela() {
    // Ordena por pontos e saldo de gols (bubble sort mantendo lógica original do Java)
    let ordenados = times.slice();

    for (let i = 0; i < ordenados.length - 1; i++) {
        for (let j = 0; j < ordenados.length - 1 - i; j++) {
            let saldoJ = ordenados[j].golsPro - ordenados[j].golsContra;
            let saldoJMais1 = ordenados[j + 1].golsPro - ordenados[j + 1].golsContra;

            // Critério 1: Pontos. Critério 2 de desempate: Saldo de Gols
            let deveTrocar = false;
            if (ordenados[j + 1].pontos > ordenados[j].pontos) {
                deveTrocar = true;
            } else if (ordenados[j + 1].pontos === ordenados[j].pontos) {
                if (saldoJMais1 > saldoJ) {
                    deveTrocar = true;
                }
            }

            if (deveTrocar) {
                let aux = ordenados[j];
                ordenados[j] = ordenados[j + 1];
                ordenados[j + 1] = aux;
            }
        }
    }

    let tabela = document.getElementById("tabelaClassificacao");
    if (!tabela) return;

    let conteudoTabela = "<thead><tr><th>Time</th><th>Pts</th><th>V</th><th>E</th><th>D</th><th>SG</th></tr></thead><tbody>";

    if (ordenados.length === 0) {
        conteudoTabela += `<tr><td colspan="6" class="tabela-vazia">Nenhum time cadastrado ainda. Cadastre times na etapa 1!</td></tr>`;
    } else {
        ordenados.forEach(function (time, index) {
            let saldoGols = time.golsPro - time.golsContra;
            let saldoFormatado = saldoGols > 0 ? "+" + saldoGols : saldoGols;
            let nomeExibicao = time.nome;

            if (index === 0 && (time.pontos > 0 || ordenados.length > 1)) {
                nomeExibicao = `<span class="posicao-lider">👑 ${time.nome}</span>`;
            }

            conteudoTabela += `
                <tr>
                    <td>${nomeExibicao}</td>
                    <td><span class="badge-pts">${time.pontos}</span></td>
                    <td>${time.vitorias}</td>
                    <td>${time.empates}</td>
                    <td>${time.derrotas}</td>
                    <td>${saldoFormatado}</td>
                </tr>
            `;
        });
    }

    conteudoTabela += "</tbody>";
    tabela.innerHTML = conteudoTabela;
}

// Atalho de tecla Enter nos campos de entrada
document.addEventListener("DOMContentLoaded", function () {
    atualizarSelects();
    atualizarTabela();

    const bindings = [
        { ids: ["nomeTime", "nomeTecnico"], acao: cadastrarTime },
        { ids: ["nomeJogador", "posicaoJogador"], acao: cadastrarJogador },
        { ids: ["golsCasa", "golsVisitante"], acao: registrarPartida }
    ];

    bindings.forEach(function (item) {
        item.ids.forEach(function (id) {
            let el = document.getElementById(id);
            if (el) {
                el.addEventListener("keyup", function (evento) {
                    if (evento.key === "Enter") {
                        item.acao();
                    }
                });
            }
        });
    });
});
