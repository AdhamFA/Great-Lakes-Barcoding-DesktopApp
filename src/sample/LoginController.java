package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button btnLogin;
    @FXML
    private Button btnLogout;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;
        if(event.getSource()==btnLogin){
            //get reference to the button's stage
            stage=(Stage) btnLogin.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("manager.fxml"));
        }
        else{
            stage=(Stage) btnLogout.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        }
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
