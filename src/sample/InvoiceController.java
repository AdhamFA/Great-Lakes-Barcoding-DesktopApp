package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InvoiceController implements Initializable {

    @FXML
    private Button close, export;
    @FXML
    private TextField completionDate, customerNumber, customerPO, technicianNumber,
    txtPriceTotal;
    @FXML
    private TextArea comments;
    @FXML
    private TableView lineItemsTable;
    @FXML
    private ComboBox comInvoiceID;

    @FXML
    private void closeScene(ActionEvent event) throws IOException {
        Parent managerView = FXMLLoader.load(getClass().getResource("manager.fxml"));
        Scene managerViewScene = new Scene(managerView);

        //this will get the stage information
        Stage window;
        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(managerViewScene);

        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
