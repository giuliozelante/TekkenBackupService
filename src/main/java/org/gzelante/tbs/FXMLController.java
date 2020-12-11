package org.gzelante.tbs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.EventHandler;
import org.gzelante.tbs.config.ConfigManager;

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

    private String lblBtnEdit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConfigManager configManager = new ConfigManager();
        txtFldCurrConfigDir.setText(configManager.getConfig().getString(ConfigManager.CURRENT_PREFIX + ConfigManager.SUFFIX));
        this.lblBtnEdit = btnEdit.getText();
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
