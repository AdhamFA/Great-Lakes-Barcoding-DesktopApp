package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;

public class NewEmployeeController implements Initializable {

    @FXML
    private Button cancel, create;
    @FXML
    private TextField txtFName, txtLName, txtStreet, txtCity, txtState,
            txtZIP, txtNUM1, txtNUM2, txtNUM3, txtSSN, txtSalary, txtTitle, txtUserName, txtPassword;
    private String strFName, strLName, strStreet, strCity, strState,
    strZIP, strNUM1, strNUM2, strNUM3, strSSN, strSalary, strTitle, strUserName, strPassword,
    strBirth, strHire;

    @FXML
    private DatePicker birth, hire;

    @FXML
    public String readJsonFromUrl(String urlString) throws IOException, JSONException {
        BufferedReader reader = null;
        try {
            System.out.println(urlString);
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            if (buffer.equals(""))
                buffer.append("[]");
            return buffer.toString();}
        catch (Throwable e){
            JFrame j = new JFrame();
            JOptionPane.showMessageDialog(j, "Failed To Create Account.", "Alert" , JOptionPane.WARNING_MESSAGE);
            return null;
        }
        finally {
            if (reader != null)
                reader.close();
        }
    }

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
    private void createAccount(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("manager.fxml"));
        Parent managerView = loader.load();
        Scene managerViewScene = new Scene(managerView);

        strFName = URLEncoder.encode(txtFName.getText(), "UTF-8");
        strLName = URLEncoder.encode(txtLName.getText(), "UTF-8");
        strStreet = URLEncoder.encode(txtStreet.getText(), "UTF-8");
        strCity = URLEncoder.encode(txtCity.getText(), "UTF-8");
        strState = URLEncoder.encode(txtState.getText(), "UTF-8");
        strZIP = URLEncoder.encode(txtZIP.getText(), "UTF-8");
        strNUM1 = URLEncoder.encode(txtNUM1.getText(), "UTF-8");
        strNUM1+= "-";
        strNUM2 = URLEncoder.encode(txtNUM2.getText(), "UTF-8");
        strNUM2+= "-";
        strNUM3 = URLEncoder.encode(txtNUM3.getText(), "UTF-8");
        strSSN = URLEncoder.encode(txtSSN.getText(), "UTF-8");
        strSalary = URLEncoder.encode(txtSalary.getText(), "UTF-8");
        strTitle = URLEncoder.encode(txtTitle.getText(), "UTF-8");
        strUserName = URLEncoder.encode(txtUserName.getText(), "UTF-8");
        strPassword = URLEncoder.encode(txtPassword.getText(), "UTF-8");
        strBirth = URLEncoder.encode(birth.toString(), "UTF-8");
        strHire = URLEncoder.encode(hire.toString(), "UTF-8");

        JSONObject create = new JSONObject(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() + "&password=" + Credentials.getPass() +
                "&request=createEmployee&fName=" + strFName + "&lName=" + strLName + "&address=" + strStreet + "&city=" + strCity + "&state=" + strState + "&zip=" + strZIP +
                "&phone=" + strNUM1 + strNUM2 + strNUM3 + "&socialSecurity=" + strSSN + "&birthDate=" + strBirth +
                "&hireDate=" + strHire + "&title=" + strTitle + "&active=A&empUser=" + strUserName + "&empPass=" + strPassword));
        boolean result = create.getBoolean("result");
        if (result) {
            JFrame j = new JFrame();
            JOptionPane.showMessageDialog(j,"Employee Account Created Successfully!");
        }
        else if (!result){
            JFrame j = new JFrame();
            JOptionPane.showMessageDialog(j, "Failed To Create Account.", "Alert" , JOptionPane.WARNING_MESSAGE);
        }

        ManagerController mc = loader.getController();
        mc.resetColumns();
        mc.initialize();
        //this will get the stage information
        Stage window;
        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(managerViewScene);

        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtNUM1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,4}")) {
                    txtNUM1.setText(oldValue);
                }
            }
        });
        txtNUM2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}")) {
                    txtNUM2.setText(oldValue);
                }
            }
        });
        txtNUM3.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,4}")) {
                    txtNUM3.setText(oldValue);
                }
            }
        });

        txtZIP.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,5}")) {
                    txtZIP.setText(oldValue);
                }
            }
        });
        txtSalary.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                    txtSalary.setText(oldValue);
                }
            }
        });
        txtSSN.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,9}")) {
                    txtSSN.setText(oldValue);
                }
            }
        });

    }
}
