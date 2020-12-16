package org.gzelante.tbs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import org.gzelante.tbs.config.ConfigManager;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML
    private Label labelCurrConfigDir;
    @FXML
    private TextField txtFldCurrConfigDir;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnReset;

    @FXML
    private GridPane mainPane;

    private String lblBtnEdit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConfigManager configManager = ConfigManager.getInstance();
        txtFldCurrConfigDir.setText(configManager.getConfig().getString(ConfigManager.CURRENT_PREFIX + ConfigManager.SUFFIX));
        this.lblBtnEdit = btnEdit.getText();
        this.mainPane.setMinWidth(Screen.getPrimary().getBounds().getWidth()*0.5);
        this.mainPane.setMinHeight(Screen.getPrimary().getBounds().getHeight()*0.5);
        this.btnEdit.setOnAction(enableTxtFldCurrConfigDir());
    }

    private EventHandler<ActionEvent> enableTxtFldCurrConfigDir() {
        return e -> {
            txtFldCurrConfigDir.setDisable(false);
            btnEdit.setText("Save");
            btnEdit.setOnAction(saveCurrentSaveDir());
        };
    }

    private EventHandler<ActionEvent> saveCurrentSaveDir() {
        return e -> {
            txtFldCurrConfigDir.setDisable(true);
            btnEdit.setText(lblBtnEdit);
            btnEdit.setOnAction(enableTxtFldCurrConfigDir());

        };
    }
}
