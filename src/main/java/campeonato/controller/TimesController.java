package campeonato.controller;

import campeonato.model.Campeonato;
import campeonato.model.Time;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller do gerenciamento de times (cadastrar, editar, excluir e listar).
 *
 * @author Gabriel Gomes, Lyan Gabriel e Vitor Marcelo
 */
public class TimesController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTecnico;

    @FXML
    private TextField txtIdadeTecnico;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Label lblMensagem;

    @FXML
    private TableView<Time> tabelaTimes;

    @FXML
    private TableColumn<Time, String> colNome;

    @FXML
    private TableColumn<Time, String> colTecnico;

    @FXML
    private TableColumn<Time, Number> colJogadores;

    @FXML
    private TableColumn<Time, Number> colPontos;

    private Campeonato campeonato;

    @FXML
    private void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTecnico.setCellValueFactory(dados -> new SimpleStringProperty(dados.getValue().getTecnico().getNome()));
        colJogadores.setCellValueFactory(dados -> Bindings.size(dados.getValue().getElenco()));
        colPontos.setCellValueFactory(new PropertyValueFactory<>("pontos"));

        // Ao selecionar um time, o formulário é preenchido para edição
        tabelaTimes.getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, novo) -> preencherFormulario(novo));

        // Property Binding: editar e excluir só ficam ativos com um time selecionado
        btnSalvar.disableProperty().bind(tabelaTimes.getSelectionModel().selectedItemProperty().isNull());
        btnExcluir.disableProperty().bind(tabelaTimes.getSelectionModel().selectedItemProperty().isNull());
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
        tabelaTimes.setItems(campeonato.getTimes());
    }

    @FXML
    private void cadastrar() {
        try {
            int idade = Integer.parseInt(txtIdadeTecnico.getText().trim());
            campeonato.cadastrarTime(txtNome.getText(), txtTecnico.getText(), idade);
            limparCampos();
            Mensagem.sucesso(lblMensagem, "Time cadastrado!");
        } catch (NumberFormatException e) {
            Mensagem.erro(lblMensagem, "Informe a idade do técnico com um número inteiro!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            Mensagem.erro(lblMensagem, e.getMessage());
        }
    }

    @FXML
    private void salvar() {
        Time selecionado = tabelaTimes.getSelectionModel().getSelectedItem();
        try {
            int idade = Integer.parseInt(txtIdadeTecnico.getText().trim());
            campeonato.editarTime(selecionado, txtNome.getText(), txtTecnico.getText(), idade);
            tabelaTimes.refresh();
            Mensagem.sucesso(lblMensagem, "Time atualizado!");
        } catch (NumberFormatException e) {
            Mensagem.erro(lblMensagem, "Informe a idade do técnico com um número inteiro!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            Mensagem.erro(lblMensagem, e.getMessage());
        }
    }

    @FXML
    private void excluir() {
        Time selecionado = tabelaTimes.getSelectionModel().getSelectedItem();
        try {
            campeonato.excluirTime(selecionado);
            limparCampos();
            Mensagem.sucesso(lblMensagem, "Time excluído!");
        } catch (IllegalStateException e) {
            Mensagem.erro(lblMensagem, e.getMessage());
        }
    }

    @FXML
    private void limpar() {
        limparCampos();
        Mensagem.limpar(lblMensagem);
    }

    private void limparCampos() {
        tabelaTimes.getSelectionModel().clearSelection();
        txtNome.clear();
        txtTecnico.clear();
        txtIdadeTecnico.clear();
    }

    private void preencherFormulario(Time time) {
        if (time == null) {
            return;
        }
        txtNome.setText(time.getNome());
        txtTecnico.setText(time.getTecnico().getNome());
        txtIdadeTecnico.setText(String.valueOf(time.getTecnico().getIdade()));
    }
}
