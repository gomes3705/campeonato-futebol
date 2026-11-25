package campeonato.controller;

import campeonato.model.Campeonato;
import campeonato.model.Time;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller da tabela de classificação.
 *
 * @author Gabriel Gomes, Lyan Gabriel e Vitor Marcelo
 */
public class ClassificacaoController {

    @FXML
    private TableView<Time> tabelaClassificacao;

    @FXML
    private TableColumn<Time, Number> colPosicao;

    @FXML
    private TableColumn<Time, String> colTime;

    @FXML
    private TableColumn<Time, Number> colPontos;

    @FXML
    private TableColumn<Time, Number> colJogos;

    @FXML
    private TableColumn<Time, Number> colVitorias;

    @FXML
    private TableColumn<Time, Number> colEmpates;

    @FXML
    private TableColumn<Time, Number> colDerrotas;

    @FXML
    private TableColumn<Time, Number> colGolsPro;

    @FXML
    private TableColumn<Time, Number> colGolsContra;

    @FXML
    private TableColumn<Time, Number> colSaldo;

    @FXML
    private void initialize() {
        // A posição é a linha do time na tabela já ordenada
        colPosicao.setCellValueFactory(
                dados -> new SimpleIntegerProperty(tabelaClassificacao.getItems().indexOf(dados.getValue()) + 1));
        colTime.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPontos.setCellValueFactory(new PropertyValueFactory<>("pontos"));
        colJogos.setCellValueFactory(new PropertyValueFactory<>("jogos"));
        colVitorias.setCellValueFactory(new PropertyValueFactory<>("vitorias"));
        colEmpates.setCellValueFactory(new PropertyValueFactory<>("empates"));
        colDerrotas.setCellValueFactory(new PropertyValueFactory<>("derrotas"));
        colGolsPro.setCellValueFactory(new PropertyValueFactory<>("golsPro"));
        colGolsContra.setCellValueFactory(new PropertyValueFactory<>("golsContra"));
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldoGols"));
    }

    public void setCampeonato(Campeonato campeonato) {
        // Os valores de cada linha são Properties (atualizam sozinhos); a ordem vem do Campeonato
        tabelaClassificacao.setItems(FXCollections.observableArrayList(campeonato.getClassificacao()));
    }
}
