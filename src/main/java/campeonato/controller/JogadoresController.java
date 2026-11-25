package campeonato.controller;

import campeonato.model.Campeonato;
import campeonato.model.Goleiro;
import campeonato.model.Jogador;
import campeonato.model.Time;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller do gerenciamento de jogadores e goleiros de cada time.
 *
 * @author Gabriel Gomes, Lyan Gabriel e Vitor Marcelo
 */
public class JogadoresController {

    @FXML
    private ComboBox<Time> cbTime;

    @FXML
    private Label lblTecnico;

    @FXML
    private Label lblElenco;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtIdade;

    @FXML
    private TextField txtCamisa;

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private TextField txtPosicao;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnGol;

    @FXML
    private Button btnDefesa;

    @FXML
    private Label lblDetalhe;

    @FXML
    private Label lblMensagem;

    @FXML
    private TableView<Jogador> tabelaElenco;

    @FXML
    private TableColumn<Jogador, Number> colCamisa;

    @FXML
    private TableColumn<Jogador, String> colNome;

    @FXML
    private TableColumn<Jogador, Number> colIdade;

    @FXML
    private TableColumn<Jogador, String> colPosicao;

    @FXML
    private TableColumn<Jogador, Number> colGols;

    @FXML
    private TableColumn<Jogador, String> colDefesas;

    private Campeonato campeonato;

    @FXML
    private void initialize() {
        colCamisa.setCellValueFactory(new PropertyValueFactory<>("numeroCamisa"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colPosicao.setCellValueFactory(new PropertyValueFactory<>("posicao"));
        colGols.setCellValueFactory(new PropertyValueFactory<>("golsMarcados"));
        // Só o Goleiro tem defesas
        colDefesas.setCellValueFactory(dados -> {
            Jogador jogador = dados.getValue();
            if (jogador instanceof Goleiro) {
                return new SimpleStringProperty(String.valueOf(((Goleiro) jogador).getDefesas()));
            }
            return new SimpleStringProperty("-");
        });

        cbTipo.setItems(FXCollections.observableArrayList("Jogador", "Goleiro"));
        cbTipo.setValue("Jogador");

        // O goleiro não escolhe posição: o campo fica bloqueado
        txtPosicao.disableProperty().bind(cbTipo.valueProperty().isEqualTo("Goleiro"));
        cbTipo.valueProperty().addListener((obs, antigo, novo) -> {
            if ("Goleiro".equals(novo)) {
                txtPosicao.setText("Goleiro");
            } else if ("Goleiro".equals(txtPosicao.getText())) {
                txtPosicao.clear();
            }
        });

        cbTime.valueProperty().addListener((obs, antigo, novo) -> selecionarTime(novo));
        tabelaElenco.getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, novo) -> preencherFormulario(novo));

        // Property Binding: habilita os botões conforme a seleção
        BooleanBinding semSelecao = tabelaElenco.getSelectionModel().selectedItemProperty().isNull();
        btnCadastrar.disableProperty().bind(cbTime.valueProperty().isNull());
        btnSalvar.disableProperty().bind(semSelecao);
        btnExcluir.disableProperty().bind(semSelecao);
        btnGol.disableProperty().bind(semSelecao);
        btnDefesa.disableProperty().bind(Bindings.createBooleanBinding(
                () -> !(tabelaElenco.getSelectionModel().getSelectedItem() instanceof Goleiro),
                tabelaElenco.getSelectionModel().selectedItemProperty()));
        // O tipo (jogador/goleiro) só pode ser escolhido ao cadastrar
        cbTipo.disableProperty().bind(semSelecao.not());
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
        cbTime.setItems(campeonato.getTimes());
        cbTime.getSelectionModel().selectFirst();
    }

    private void selecionarTime(Time time) {
        limparCampos();
        lblElenco.textProperty().unbind();
        if (time == null) {
            tabelaElenco.setItems(FXCollections.observableArrayList());
            lblTecnico.setText("");
            lblElenco.setText("");
            return;
        }
        tabelaElenco.setItems(time.getElenco());
        lblTecnico.setText("Técnico: " + time.getTecnico().apresentar());
        lblElenco.textProperty().bind(Bindings.size(time.getElenco()).asString("Elenco: %d/" + Time.MAX_JOGADORES));
    }

    @FXML
    private void cadastrar() {
        Time time = cbTime.getValue();
        try {
            String nome = txtNome.getText();
            int idade = Integer.parseInt(txtIdade.getText().trim());
            int camisa = Integer.parseInt(txtCamisa.getText().trim());

            // POLIMORFISMO: a variável é Jogador, mas pode guardar um Goleiro
            Jogador jogador;
            if ("Goleiro".equals(cbTipo.getValue())) {
                jogador = new Goleiro(nome, idade, camisa);
            } else {
                jogador = new Jogador(nome, idade, txtPosicao.getText(), camisa);
            }
            time.adicionarJogador(jogador);

            limparCampos();
            Mensagem.sucesso(lblMensagem, "Jogador cadastrado!");
        } catch (NumberFormatException e) {
            Mensagem.erro(lblMensagem, "Idade e camisa devem ser números inteiros!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            Mensagem.erro(lblMensagem, e.getMessage());
        }
    }

    @FXML
    private void salvar() {
        Time time = cbTime.getValue();
        Jogador selecionado = tabelaElenco.getSelectionModel().getSelectedItem();
        try {
            int idade = Integer.parseInt(txtIdade.getText().trim());
            int camisa = Integer.parseInt(txtCamisa.getText().trim());
            time.atualizarJogador(selecionado, txtNome.getText(), idade, txtPosicao.getText(), camisa);
            tabelaElenco.refresh();
            lblDetalhe.setText(selecionado.apresentar());
            Mensagem.sucesso(lblMensagem, "Jogador atualizado!");
        } catch (NumberFormatException e) {
            Mensagem.erro(lblMensagem, "Idade e camisa devem ser números inteiros!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            Mensagem.erro(lblMensagem, e.getMessage());
        }
    }

    @FXML
    private void excluir() {
        Jogador selecionado = tabelaElenco.getSelectionModel().getSelectedItem();
        cbTime.getValue().removerJogador(selecionado);
        limparCampos();
        Mensagem.sucesso(lblMensagem, "Jogador removido do elenco!");
    }

    @FXML
    private void registrarGol() {
        Jogador selecionado = tabelaElenco.getSelectionModel().getSelectedItem();
        selecionado.marcarGol();
        tabelaElenco.refresh();
        lblDetalhe.setText(selecionado.apresentar());
    }

    @FXML
    private void registrarDefesa() {
        Goleiro goleiro = (Goleiro) tabelaElenco.getSelectionModel().getSelectedItem();
        goleiro.defender();
        tabelaElenco.refresh();
        lblDetalhe.setText(goleiro.apresentar());
    }

    @FXML
    private void limpar() {
        limparCampos();
        Mensagem.limpar(lblMensagem);
    }

    private void limparCampos() {
        tabelaElenco.getSelectionModel().clearSelection();
        txtNome.clear();
        txtIdade.clear();
        txtCamisa.clear();
        txtPosicao.clear();
        cbTipo.setValue("Jogador");
        lblDetalhe.setText("");
    }

    private void preencherFormulario(Jogador jogador) {
        if (jogador == null) {
            return;
        }
        cbTipo.setValue(jogador.getTipo());
        txtNome.setText(jogador.getNome());
        txtIdade.setText(String.valueOf(jogador.getIdade()));
        txtCamisa.setText(String.valueOf(jogador.getNumeroCamisa()));
        txtPosicao.setText(jogador.getPosicao());
        // POLIMORFISMO: apresentar() muda conforme o objeto seja Jogador ou Goleiro
        lblDetalhe.setText(jogador.apresentar());
    }
}
