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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button btnLogin;
    @FXML
    private TextField txtUN;
    @FXML
    private PasswordField txtPW;
    @FXML
    private Label lblError;



    @FXML //this method will change the scene when the button is clicked and the requirements are met
    private void handleButtonAction(ActionEvent event) throws IOException{
        Parent managerView = FXMLLoader.load(getClass().getResource("manager.fxml"));
        Scene managerViewScene = new Scene(managerView);
        String error;

        if(txtUN.getText().isEmpty() || txtPW.getText().isEmpty()){
            error = "PLEASE ENTER USERNAME AND PASSWORD";
            lblError.setText(error);
        }
        else {
                //this will get the stage information
            Stage window;
            window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(managerViewScene);
            window.show();
        }
    }

}
