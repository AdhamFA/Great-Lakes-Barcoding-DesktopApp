package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateProductController {

    @FXML
    private Button cancel;

    @FXML
    private void cancelScene(ActionEvent event) throws IOException {
        Parent managerView = FXMLLoader.load(getClass().getResource("manager.fxml"));
        Scene managerViewScene = new Scene(managerView);

        //this will get the stage information
        Stage window;
        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(managerViewScene);

        window.show();
    }
}
