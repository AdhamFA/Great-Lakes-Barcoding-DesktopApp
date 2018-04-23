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
    private void close() {
        Stage stage = (Stage) btnClose.getScene().getWindow();

        stage.close();
    }

    @FXML
    public String readJsonFromUrl(String urlString) throws IOException{ //}, JSONException {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            if (buffer.equals(""))
                buffer.append("[]");
            return buffer.toString();
        } catch (Exception e) {
            lblError.setText("INCORRECT CREDENTIALS, CHECK USERNAME OR PASSWORD");
            return null;
        } finally
         {
            if (reader != null)
                reader.close();
        }
    }

    @FXML //this method will change the scene when the button is clicked and the requirements are met
    private void handleButtonAction(ActionEvent event) throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("manager.fxml"));
        loader.load();
        Parent managerView = loader.getRoot();
        Scene managerViewScene = new Scene(managerView);
        String error, userName = txtUN.getText().toString();
        String un = txtUN.getText(), pw = txtPW.getText();

        //Process p1 = java.lang.Runtime.getRuntime().exec("ping -n 1 www.google.com");
        //int returnVal = p1.waitFor();
        //boolean reachable = (returnVal == 0);

        /*if (!reachable) {
            error = "INTERNET IS DOWN, CHECK CONNECTION";
            lblError.setText(error);
        } else*/ if (txtUN.getText().isEmpty() || txtPW.getText().isEmpty()) {
            error = "PLEASE ENTER USERNAME AND PASSWORD";
            lblError.setText(error);
        } else {
            JSONArray json = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + un + "&password=" + pw +
                    "&request=getEmployee&empUser=" + un));
            JSONObject names = json.getJSONObject(0);

            String fName = names.get("Emp_FirstName").toString();
            String lName = names.get("Emp_LastName").toString();
            Credentials crd = new Credentials(fName+" "+lName, un, pw);
            ManagerController controller = loader.getController();
            controller.initialize();
            Stage window;
            window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(managerViewScene);
            window.show();
        }
    }
}
