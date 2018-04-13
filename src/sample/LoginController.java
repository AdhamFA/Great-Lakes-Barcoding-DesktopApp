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
    private Button btnClose;
    @FXML
    private TextField txtUN;
    @FXML
    private PasswordField txtPW;
    @FXML
    private Label lblError;


    @FXML
    private void close(){
        Stage stage = (Stage) btnClose.getScene().getWindow();

        stage.close();
    }

    @FXML //this method will change the scene when the button is clicked and the requirements are met
    private void handleButtonAction(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("manager.fxml"));
        loader.load();
        Parent managerView = loader.getRoot();
        Scene managerViewScene = new Scene(managerView);
        String error, userName = txtUN.getText().toString();

        if(txtUN.getText().isEmpty() || txtPW.getText().isEmpty()){
            error = "PLEASE ENTER USERNAME AND PASSWORD";
            lblError.setText(error);
        }
        else {
            //this will get the stage information
            ManagerController controller = loader.getController();
            controller.setText(userName);
            Stage window;
            window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(managerViewScene);
            window.show();
        }
    }

}
