package campeonato.controller;

import javafx.scene.control.Label;

/**
 * Mostra mensagens de erro e de sucesso em um Label. As cores ficam no style.css.
 *
 * @author Gabriel Gomes, Lyan Gabriel e Vitor Marcelo
 */
final class Mensagem {

    private Mensagem() {
    }

    static void erro(Label label, String texto) {
        exibir(label, texto, "msg-erro");
    }

    static void sucesso(Label label, String texto) {
        exibir(label, texto, "msg-sucesso");
    }

    static void limpar(Label label) {
        exibir(label, "", "msg-sucesso");
    }

    private static void exibir(Label label, String texto, String classeCss) {
        label.getStyleClass().removeAll("msg-erro", "msg-sucesso");
        label.getStyleClass().add(classeCss);
        label.setText(texto);
    }
}
