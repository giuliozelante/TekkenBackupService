package org.gzelante.tbs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.gzelante.tbs.config.ConfigManager;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML
    private Label labelCurrConfigDir;
    @FXML
    private TextField txtFldCurrConfigDir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConfigManager configManager = new ConfigManager();
        txtFldCurrConfigDir.setText(configManager.getConfig().getString(ConfigManager.CURRENT_PREFIX + ConfigManager.SUFFIX));
    }
}
