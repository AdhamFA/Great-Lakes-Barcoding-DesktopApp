package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerController {
    @FXML
    private Button btnLogout;
    @FXML
    private Label lblWelcome;
    @FXML
    private MenuItem mst_New, mst_Inprogress, mst_Finished,
                     rprt_Tickets, rprt_Invoices, rprt_Employees,
                     rprt_Customers, rprt_Contracts, rcrd_Mileage,
                     rcrd_Hours, help_About;

    @FXML
    public void setText(String name){
        lblWelcome.setText("Welcome back, " + name +"!");
    }

    @FXML //this method will change the scene when the button is clicked and the requirements are met
    private void handleButtonAction(ActionEvent event) throws IOException {
        Parent loginView = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene loginViewScene = new Scene(loginView);

        //this will get the stage information
        Stage window;
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginViewScene);
        window.show();
    }
}
