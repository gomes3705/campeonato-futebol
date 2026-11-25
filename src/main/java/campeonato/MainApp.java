package campeonato;

import java.io.IOException;

import campeonato.controller.PrincipalController;
import campeonato.model.Campeonato;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Ponto de entrada da aplicação JavaFX.
 *
 * @author Gabriel Gomes, Lyan Gabriel e Vitor Marcelo
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Carrega a tela principal declarada em FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/campeonato/view/principal.fxml"));
        Parent raiz = loader.load();

        // Os dados do campeonato ficam em memória e são compartilhados por todas as telas
        PrincipalController controller = loader.getController();
        controller.setCampeonato(new Campeonato());

        Scene scene = new Scene(raiz, 1100, 740);
        scene.getStylesheets().add(getClass().getResource("/campeonato/css/style.css").toExternalForm());

        stage.setTitle("Campeonato de Futebol");
        stage.setMinWidth(900);
        stage.setMinHeight(640);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
