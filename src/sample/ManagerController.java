package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerController {
    @FXML
    private Button btnLogout;



    @FXML //this method will change the scene when the button is clicked and the requirements are met
    private void handleButtonAction(ActionEvent event) throws IOException {
        Parent loginView = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene loginViewScene = new Scene(loginView);

        //this will get the stage information
        Stage window;
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginViewScene);
        window.show();
    }
}
