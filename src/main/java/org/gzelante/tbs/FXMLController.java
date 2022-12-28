package org.gzelante.tbs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.gzelante.tbs.config.ConfigManager;

import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
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

    private ConfigManager configManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.configManager = ConfigManager.getInstance();
        this.txtFldCurrConfigDir.setText(configManager.getConfig().getString(ConfigManager.CURRENT_PREFIX + ConfigManager.SUFFIX));
        this.lblBtnEdit = btnEdit.getText();
        this.mainPane.setMinWidth(Screen.getPrimary().getBounds().getWidth()*0.5);
        this.mainPane.setMinHeight(Screen.getPrimary().getBounds().getHeight()*0.5);
        this.btnEdit.setOnAction(enableTxtFldCurrConfigDir());
        this.btnReset.setOnAction(resetCurrentSaveDirTxtFld());
    }

    private EventHandler<ActionEvent> enableTxtFldCurrConfigDir() {
        return e -> {
            editBtnModifier(false, "Save", saveCurrentSaveDir());
        };
    }

    private EventHandler<ActionEvent> saveCurrentSaveDir() {
        return e -> {
            editBtnModifier(true, lblBtnEdit, enableTxtFldCurrConfigDir());
            this.setSaveDir();
        };
    }

    private void setSaveDir() {
        this.configManager.getConfig().setProperty(ConfigManager.CURRENT_PREFIX + ConfigManager.SUFFIX, this.txtFldCurrConfigDir.getText());
        try {
            this.configManager.save();
        } catch (ConfigurationException configurationException) {
            log.error(configurationException.getMessage(), configurationException);
        }
    }

    private EventHandler<ActionEvent> resetCurrentSaveDirTxtFld() {
        return event -> {
            this.txtFldCurrConfigDir.setText(this.configManager.getDefaultOSSaveDir());
            this.setSaveDir();
        };
    }

    private void editBtnModifier(boolean b, String save, EventHandler<ActionEvent> actionEventEventHandler) {
        txtFldCurrConfigDir.setDisable(b);
        btnEdit.setText(save);
        btnEdit.setOnAction(actionEventEventHandler);
    }


}
