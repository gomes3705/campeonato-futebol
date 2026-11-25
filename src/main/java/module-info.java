module campeonato.futebol {
    requires javafx.controls;
    requires javafx.fxml;

    // O JavaFX precisa iniciar a aplicação, instanciar os controllers (campos @FXML)
    // e ler as propriedades do Model nas TableViews.
    exports campeonato to javafx.graphics;
    opens campeonato.controller to javafx.fxml;
    opens campeonato.model to javafx.base;
}
