/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import model.Colecao;
import model.dao.ColecaoDaoJDBC;
import model.dao.DaoFactory;
import start.App;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;


public class PrincipalController implements Initializable {

    @FXML
    private Button btnIncluir;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnFiltrar;
    @FXML
    private Button btnLimpar;
     @FXML
    private TextField txtFiltro;
    @FXML
    private TableView<Colecao> tblPedidos;
    @FXML
    private TableColumn<Colecao, String> tblColNome;
    @FXML
    private TableColumn<Colecao, String> tblColEquipamento;
     @FXML
    private TableColumn<Colecao, String> tblColDataDoPedido;
    @FXML
    private TableColumn<Colecao, String> tblColRequerente;
    @FXML
    private TableColumn<Colecao, String> tblColDescricao;
    @FXML
    private TableColumn<Colecao, Boolean> tblColStatus;
    @FXML
    private Label lblNome;
    @FXML
    private Group grupoRadio;
    
    private Colecao colecaoSelecionada;
    private List<Colecao> listaPedidos;
    private ObservableList<Colecao> observableListPedidos;
    private String localImagem;
    @FXML
    private ImageView imgView;
    @FXML
    private Button btnEstatistica;
    @FXML
    private Button btnAdicionar;
     @FXML
    private CheckBox chkBxStatus;
    @FXML
    private Rectangle pnView;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEquipamento;
    @FXML
    private TextField txtRequerente;
    @FXML
    private TextField txtDescricao;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGravar;
     @FXML
    private TextField txtDataDoPedido;

    
    private static Colecao pedidoSelecionado;
    private final String diretorioImagens = "src/main/resources/imagens";
    private String caminhoImagem;
    private List<Colecao> listaColecao;
    private ObservableList<Colecao> observableListColecao;
    @FXML
    private ImageView imgView1;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblNome.setText("");
        cursorTratamento();
        carregarPedidos("");
    }
    private void tblElementoOnAction(MouseEvent event) {
        limparCampos();
        colecaoSelecionada = tblPedidos.selectionModelProperty().getValue().getSelectedItem();
        if (colecaoSelecionada != null) {
            txtNome.setText(pedidoSelecionado.getNome());
            txtEquipamento.setText(pedidoSelecionado.getEquipamento());
            txtRequerente.setText(pedidoSelecionado.getRequerente());
            txtDataDoPedido.setText(pedidoSelecionado.getDataDoPedido().toString());
            txtDescricao.setText(pedidoSelecionado.getDescricao());
             caminhoImagem = colecaoSelecionada.getLocalImagem();
            if (caminhoImagem != null) {
                try {
                        Image image = new Image(new File(diretorioImagens, caminhoImagem).toURI().toString());
                        imgView.setImage(image);
                        imgView.setFitHeight(294);
                        imgView.setFitWidth(200); 
                        imgView.setPreserveRatio(false);
                        imgView.setLayoutX(661);
                        imgView.setLayoutY(128); 
                        pnView.setVisible(false);
                        
                    
                } catch (Exception e) {
                    Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
                    alertErro.setTitle("Aviso");
                    alertErro.setContentText("Ocorreu um erro: " + e.getMessage());
                    alertErro.showAndWait();
                }
            }
            if (colecaoSelecionada.isStatus()) {
                chkBxStatus.setSelected(true);
            }
        }
    }

    
    @FXML
        private void tblOnAction(MouseEvent event) {
    colecaoSelecionada = tblPedidos.selectionModelProperty().getValue().getSelectedItem();

    if (colecaoSelecionada != null) {
        lblNome.setText(colecaoSelecionada.getNome());
        txtNome.setText(colecaoSelecionada.getNome());

        
    if (colecaoSelecionada.getDataDoPedido() != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        LocalDate localDate = colecaoSelecionada.getDataDoPedido();

        txtDataDoPedido.setText(localDate.format(formatter));
    } else {
        txtDataDoPedido.setText("");
    }
        
        txtDescricao.setText(colecaoSelecionada.getDescricao());
        txtEquipamento.setText(colecaoSelecionada.getEquipamento());
        chkBxStatus.setSelected(colecaoSelecionada.isStatus());
        txtRequerente.setText(colecaoSelecionada.getRequerente());
        caminhoImagem = colecaoSelecionada.getLocalImagem();

        if (caminhoImagem != null) {
            Image image = new Image(new File(diretorioImagens, caminhoImagem).toURI().toString());
            imgView.setImage(image);
            imgView.setFitHeight(294);
            imgView.setFitWidth(200); 
            imgView.setPreserveRatio(false);
            imgView.setLayoutX(661);
            imgView.setLayoutY(128); 
        } else {
            imgView.setImage(null);
        }
    }
}
    @FXML
    private void btnIncluirOnAction(ActionEvent event) throws IOException {
         limparCampos();
         lblNome.setText("");
         colecaoSelecionada = null;
        carregarPedidos(txtFiltro.getText());
    }

    @FXML
private void btnExcluirOnAction(ActionEvent event) throws Exception {
    Colecao pedidoSelecionado = tblPedidos.getSelectionModel().getSelectedItem();
    if (pedidoSelecionado == null) {
        Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
        alertErro.setTitle("Aviso");
        alertErro.setContentText("Nenhum item selecionado para exclusão.");
        alertErro.showAndWait();
        return;
    }
    
    ColecaoDaoJDBC dao = DaoFactory.novoColecaoDAO();

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Aviso");
    alert.setContentText("Confirma exclusão de " + pedidoSelecionado.getNome() + "?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        try {
            dao.excluir(pedidoSelecionado);
            limparCampos();
            carregarPedidos("");
        } catch (Exception e) {
            e.printStackTrace();
            String mensagem = "Ocorreu um erro: " + e.getMessage();
            Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
            alertErro.setTitle("Aviso");
            alertErro.setContentText(mensagem);
            alertErro.showAndWait();
        }
    }
}


    @FXML
    private void btnFiltrarOnAction(ActionEvent event) {
        carregarPedidos(txtFiltro.getText());
    }

    @FXML
    private void btnLimparOnAction(ActionEvent event) {
        txtFiltro.clear();
        limparCampos();
        lblNome.setText("");
        carregarPedidos("");
    }

    public void carregarPedidos(String param) {
        tblColNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblColEquipamento.setCellValueFactory(new PropertyValueFactory<>("equipamento"));
        tblColRequerente.setCellValueFactory(new PropertyValueFactory<>("requerente"));
        tblColDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tblColStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblColDataDoPedido.setCellValueFactory(new PropertyValueFactory<>("dataDoPedido"));


         try {
            ColecaoDaoJDBC dao = DaoFactory.novoColecaoDAO();
            listaColecao = dao.listar(param);
        } catch (Exception e) {
            Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
            alertErro.setTitle("Aviso");
            alertErro.setContentText("Ocorreu um erro: " + e.getMessage());
            alertErro.showAndWait();
        }

        observableListColecao = FXCollections.observableArrayList(listaColecao);
        tblPedidos.setItems(observableListColecao);
    }

    @FXML
    private void btnAdicionarOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.png", "*.jpeg"));
        java.io.File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {

                File diretorioImagensFile = new File(diretorioImagens);
                if (!diretorioImagensFile.exists()) {
                    diretorioImagensFile.mkdirs();
                }

                Path destino = Paths.get(diretorioImagens + File.separator + file.getName());
                Files.copy(file.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

                caminhoImagem = file.getName();
                Image image = new Image(new File(diretorioImagens, file.getName()).toURI().toString());
                if (image != null) {
                    imgView.setImage(image);
                    imgView.setFitHeight(294);
                    imgView.setFitWidth(200); 
                    imgView.setPreserveRatio(false);
                    imgView.setLayoutX(661);
                    imgView.setLayoutY(128); 
                    pnView.setVisible(false);
                }
            } catch (IOException e) {
                Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
                alertErro.setTitle("Aviso");
                alertErro.setContentText("Ocorreu um erro: " + e.getMessage());
                alertErro.showAndWait();
            }
        }
    }
    
     @FXML
        private void limparCampos() {
        txtNome.clear();
        chkBxStatus.setSelected(false);
        txtEquipamento.clear();
        txtRequerente.clear();
        txtDescricao.clear();
        txtDataDoPedido.clear();
        imgView.setImage(null);
        caminhoImagem = null;
        pnView.setVisible(true);
        lblNome.setText("");
    }
        
        @FXML
    private void btnCancelarOnAction(ActionEvent event) {
        limparCampos();
        colecaoSelecionada = null;
        carregarPedidos(txtFiltro.getText());
    }
    
     @FXML
    private void btnEstatisticaOnAction(ActionEvent event) {
        EstatisticaController.setListaColecao(listaColecao);

        try {
            App.setRoot("Estatistica");
        } catch (Exception e) {
            Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
            alertErro.setTitle("Aviso");
            alertErro.setContentText("Ocorreu um erro: " + e.getMessage());
            alertErro.showAndWait();
        }
    }
    
         @FXML
    private void btnGravarOnAction(ActionEvent event) {
    Colecao colecao = new Colecao();
    colecao.setNome(txtNome.getText());
    colecao.setEquipamento(txtEquipamento.getText());
    colecao.setRequerente(txtRequerente.getText());
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    try {
        LocalDate dataPedido = LocalDate.parse(txtDataDoPedido.getText(), formatter);
        colecao.setDataDoPedido(dataPedido);
    } catch (DateTimeParseException e) {
        Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
        alertErro.setTitle("Aviso");
        alertErro.setContentText("Data inválida. Use o formato: dd/MM/yyyy");
        alertErro.showAndWait();
        return;
    }

    colecao.setDescricao(txtDescricao.getText());
    colecao.setLocalImagem(caminhoImagem);
    colecao.setStatus(chkBxStatus.isSelected());

    try {
        ColecaoDaoJDBC dao = DaoFactory.novoColecaoDAO();

        if (colecaoSelecionada == null) {
            dao.incluir(colecao);
        } else {
            colecao.setId(colecaoSelecionada.getId());
            dao.editar(colecao);
            colecaoSelecionada = null;
        }

        limparCampos();
        carregarPedidos(txtFiltro.getText());
    } catch (Exception e) {
        Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
        alertErro.setTitle("Aviso");
        alertErro.setContentText("Ocorreu um erro: " + e.getMessage());
        alertErro.showAndWait();
    }
}
     private void cursorTratamento() {
        txtNome.setTooltip(new Tooltip("Insira o nome da usina para registro"));
        tblColEquipamento.setCellValueFactory(new PropertyValueFactory<>("Insira o equipamento"));
        tblColDataDoPedido.setCellValueFactory(new PropertyValueFactory<>("Insira a data do pedido"));
        tblColRequerente.setCellValueFactory(new PropertyValueFactory<>("Insira o requerente"));
        tblColDescricao.setCellValueFactory(new PropertyValueFactory<>("Insira a descrição do problema"));
        btnAdicionar.setTooltip(new Tooltip("Selecione a imagem desejada para o equipamento"));
        chkBxStatus.setTooltip(new Tooltip("marque caso ja tenha sido concluido o pedio"));
    }
}     
      