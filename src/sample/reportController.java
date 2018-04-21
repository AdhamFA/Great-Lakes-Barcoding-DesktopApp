package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class reportController
{
    @FXML
    private TableView table;
    @FXML
    private Button close;

    public void setTable(ObservableList e){
        table.setItems(e);
    }

    public void setTableColumns(TableColumn... args){
        table.getColumns().addAll(args);
    }

    @FXML
    private void closeScene(ActionEvent event) throws IOException{
        Parent managerView = FXMLLoader.load(getClass().getResource("manager.fxml"));
        Scene managerViewScene = new Scene(managerView);

        //this will get the stage information
        Stage window;
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(managerViewScene);

        window.show();
    }
}
