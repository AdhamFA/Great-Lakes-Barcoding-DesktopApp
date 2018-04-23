package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateTicketController {

    @FXML
    private Button cancel, update;
    @FXML
    private TextField txtMIL, txtTIM;
    @FXML
    private TextArea txtPRT, txtCMT;
    @FXML
    private ComboBox comTechID;
    @FXML
    private CheckBox isDone;
    @FXML
    private DatePicker datePicker;

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

    @FXML
    public void initialize(ManagerTicket mt) throws IOException {
        if (!mt.getCustomerTicketID().equals("0-0")) {
            ArrayList<String> idList = new ArrayList<>();
            JSONArray techs = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() +
                    "&password=" + Credentials.getPass() + "&request=getEmployee&title=technician"));
            idList.add("none");
            for (int i = 0; i < techs.length(); i++) {
                JSONObject tech = techs.getJSONObject(i);
                String id = tech.getString("Emp_EmployeeNumber");
                String fName = tech.getString("Emp_FirstName");
                String lName = tech.getString("Emp_LastName");
                idList.add(id + "-" + lName + ", " + fName);
            }
            ObservableList<String> idOBList = FXCollections.observableArrayList(idList);

            comTechID.setItems(idOBList);
            txtMIL.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                        txtMIL.setText(oldValue);
                    }
                }
            });

            txtTIM.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                        txtTIM.setText(oldValue);
                    }
                }
            });
        }
    }

    @FXML
    public String readJsonFromUrl(String urlString) throws IOException, JSONException {
        BufferedReader reader = null;
        try {
            URL url = new URL(URLEncoder.encode(urlString, "UTF-8"));
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            if (buffer.equals(""))
                buffer.append("[]");
            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
