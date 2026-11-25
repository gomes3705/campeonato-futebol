package campeonato.controller;

import java.io.IOException;

import campeonato.model.Campeonato;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;

/**
 * Controller da tela principal: menu de navegação e área onde as outras telas são carregadas.
 *
 * @author Gabriel Gomes, Lyan Gabriel e Vitor Marcelo
 */
public class PrincipalController {

    @FXML
    private StackPane painelConteudo;

    @FXML
    private ToggleGroup grupoMenu;

    private Campeonato campeonato;

    @FXML
    private void initialize() {
        // Impede que o botão do menu fique "desmarcado" ao clicar nele de novo
        grupoMenu.selectedToggleProperty().addListener((obs, antigo, novo) -> {
            if (novo == null) {
                antigo.setSelected(true);
            }
        });
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
        mostrarInicio();
    }

    // ---------------------------------------------------------- navegação

    @FXML
    private void mostrarInicio() {
        InicioController controller = abrirTela("inicio.fxml");
        controller.setCampeonato(campeonato);
    }

    @FXML
    private void mostrarTimes() {
        TimesController controller = abrirTela("times.fxml");
        controller.setCampeonato(campeonato);
    }

    @FXML
    private void mostrarJogadores() {
        JogadoresController controller = abrirTela("jogadores.fxml");
        controller.setCampeonato(campeonato);
    }

    @FXML
    private void mostrarPartidas() {
        PartidasController controller = abrirTela("partidas.fxml");
        controller.setCampeonato(campeonato);
    }

    @FXML
    private void mostrarClassificacao() {
        ClassificacaoController controller = abrirTela("classificacao.fxml");
        controller.setCampeonato(campeonato);
    }

    @FXML
    private void sair() {
        Platform.exit();
    }

    /** Carrega um FXML, coloca a tela no centro e devolve o controller dela. */
    private <T> T abrirTela(String arquivo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/campeonato/view/" + arquivo));
            Parent tela = loader.load();
            painelConteudo.getChildren().setAll(tela);
            return loader.getController();
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível carregar a tela " + arquivo, e);
        }
    }
}
