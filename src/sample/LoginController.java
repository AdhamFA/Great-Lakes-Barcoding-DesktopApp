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
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

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

    @FXML
    public String readJsonFromUrl(String urlString) throws IOException, JSONException {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            if(buffer.equals(""))
                buffer.append("[]");
            return buffer.toString();
        }
        catch (Throwable e){
            lblError.setText("USERNAME OR PASSWORD MISMATCH");
            return "[]";
        }
        finally {
            if (reader != null)
                reader.close();
        }
    }

    @FXML //this method will change the scene when the button is clicked and the requirements are met
    private void handleButtonAction(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("manager.fxml"));
        loader.load();
        Parent managerView = loader.getRoot();
        Scene managerViewScene = new Scene(managerView);
        String error, userName = txtUN.getText().toString();
        String un = txtUN.getText(), pw = txtPW.getText();



        if(txtUN.getText().isEmpty() || txtPW.getText().isEmpty()){
            error = "PLEASE ENTER USERNAME AND PASSWORD";
            lblError.setText(error);
        }
        else {
           JSONArray json = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + un + "&password=" + pw + "&request=employeeLogin"));
            System.out.println(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + un + "&password=" + pw + "&request=employeeLogin"));
            if (json.length() == 0){
            error = "USERNAME AND PASSWORD DON'T MATCH";
                lblError.setText(error);
            }
            else {
            //this will get the stage information
                ManagerController controller = loader.getController();
                Stage window;
                window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(managerViewScene);
                window.show();
           }
        }
    }

}
