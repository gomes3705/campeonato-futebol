package campeonato.controller;

import campeonato.model.Campeonato;
import campeonato.model.Partida;
import campeonato.model.Time;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller das partidas: gerar confrontos, registrar resultados e consultar o histórico.
 *
 * @author Gabriel Gomes, Lyan Gabriel e Vitor Marcelo
 */
public class PartidasController {

    @FXML
    private Label lblPendentes;

    @FXML
    private Label lblRealizadas;

    @FXML
    private Label lblSelecionada;

    @FXML
    private Label lblGolsCasa;

    @FXML
    private Label lblGolsVisitante;

    @FXML
    private TextField txtGolsCasa;

    @FXML
    private TextField txtGolsVisitante;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Label lblMensagem;

    @FXML
    private TableView<Partida> tabelaPendentes;

    @FXML
    private TableColumn<Partida, Time> colPendCasa;

    @FXML
    private TableColumn<Partida, Time> colPendVisitante;

    @FXML
    private TableView<Partida> tabelaHistorico;

    @FXML
    private TableColumn<Partida, Time> colHistCasa;

    @FXML
    private TableColumn<Partida, String> colHistPlacar;

    @FXML
    private TableColumn<Partida, Time> colHistVisitante;

    // Listas exibidas nas tabelas (cópias das coleções do Campeonato, recarregadas a cada mudança)
    private final ObservableList<Partida> pendentes = FXCollections.observableArrayList();
    private final ObservableList<Partida> realizadas = FXCollections.observableArrayList();

    private Campeonato campeonato;

    @FXML
    private void initialize() {
        // O Time é exibido pelo nome (toString)
        colPendCasa.setCellValueFactory(new PropertyValueFactory<>("timeCasa"));
        colPendVisitante.setCellValueFactory(new PropertyValueFactory<>("timeVisitante"));
        colHistCasa.setCellValueFactory(new PropertyValueFactory<>("timeCasa"));
        colHistPlacar.setCellValueFactory(new PropertyValueFactory<>("placar"));
        colHistVisitante.setCellValueFactory(new PropertyValueFactory<>("timeVisitante"));

        tabelaPendentes.setItems(pendentes);
        tabelaHistorico.setItems(realizadas);

        lblPendentes.textProperty().bind(Bindings.size(pendentes).asString("Pendentes: %d"));
        lblRealizadas.textProperty().bind(Bindings.size(realizadas).asString("Realizadas: %d"));

        btnRegistrar.disableProperty().bind(tabelaPendentes.getSelectionModel().selectedItemProperty().isNull());
        tabelaPendentes.getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, novo) -> mostrarSelecionada(novo));
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
        atualizarListas();
    }

    @FXML
    private void gerarConfrontos() {
        try {
            int novos = campeonato.gerarConfrontos();
            atualizarListas();
            if (novos == 0) {
                Mensagem.sucesso(lblMensagem, "Todos os confrontos já foram gerados.");
            } else {
                Mensagem.sucesso(lblMensagem, novos + " confronto(s) gerado(s)!");
            }
        } catch (IllegalStateException e) {
            Mensagem.erro(lblMensagem, e.getMessage());
        }
    }

    @FXML
    private void registrarResultado() {
        Partida partida = tabelaPendentes.getSelectionModel().getSelectedItem();
        try {
            int golsCasa = Integer.parseInt(txtGolsCasa.getText().trim());
            int golsVisitante = Integer.parseInt(txtGolsVisitante.getText().trim());
            campeonato.registrarResultado(partida, golsCasa, golsVisitante);

            txtGolsCasa.clear();
            txtGolsVisitante.clear();
            atualizarListas();
            Mensagem.sucesso(lblMensagem, "Partida registrada: " + partida);
        } catch (NumberFormatException e) {
            Mensagem.erro(lblMensagem, "Informe os gols com números inteiros!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            Mensagem.erro(lblMensagem, e.getMessage());
        }
    }

    // Recarrega as tabelas a partir do Campeonato; a primeira da fila é a próxima partida
    private void atualizarListas() {
        pendentes.setAll(campeonato.getPartidasPendentes());
        realizadas.setAll(campeonato.getHistorico());
        tabelaPendentes.getSelectionModel().selectFirst();
        mostrarSelecionada(tabelaPendentes.getSelectionModel().getSelectedItem());
    }

    private void mostrarSelecionada(Partida partida) {
        if (partida == null) {
            lblSelecionada.setText("Nenhuma partida pendente.");
            lblGolsCasa.setText("Gols do mandante");
            lblGolsVisitante.setText("Gols do visitante");
            return;
        }
        lblSelecionada.setText(partida.toString());
        lblGolsCasa.setText("Gols do " + partida.getTimeCasa().getNome());
        lblGolsVisitante.setText("Gols do " + partida.getTimeVisitante().getNome());
    }
}
