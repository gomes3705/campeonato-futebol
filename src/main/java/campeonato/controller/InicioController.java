package campeonato.controller;

import campeonato.model.Campeonato;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller do painel inicial com os totais do campeonato.
 *
 * @author Gabriel Gomes, Lyan Gabriel e Vitor Marcelo
 */
public class InicioController {

    @FXML
    private Label lblTotalTimes;

    @FXML
    private Label lblTotalJogadores;

    @FXML
    private Label lblPartidasRealizadas;

    @FXML
    private Label lblPartidasPendentes;

    public void setCampeonato(Campeonato campeonato) {
        // Property Binding: o total de times acompanha a lista automaticamente
        lblTotalTimes.textProperty().bind(Bindings.size(campeonato.getTimes()).asString());

        // Os demais totais são calculados cada vez que a tela é aberta
        lblTotalJogadores.setText(String.valueOf(campeonato.getTotalJogadores()));
        lblPartidasRealizadas.setText(String.valueOf(campeonato.getTotalPartidasRealizadas()));
        lblPartidasPendentes.setText(String.valueOf(campeonato.getTotalPartidasPendentes()));
    }
}
