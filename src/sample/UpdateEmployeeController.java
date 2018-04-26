package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateEmployeeController implements Initializable {
    @FXML
    private Button cancel;
    @FXML
    private ComboBox comID;
    @FXML
    private TextField txtFName, txtLName, txtStreet, txtCity, txtState,
            txtZIP, txtNUM1, txtNUM2, txtNUM3, txtSalary, txtTitle, txtUser;

    private String[] strFname;
    private String[] strLname;
    private String[] strAddr;
    private String[] strCity;
    private String[] strState;
    private String[] strZip;
    private String[] strNum1;
    private String[] strNum2;
    private String[] strNum3;
    private String[] strSalary;
    private String[] strTitle;
    private String[] strUsrn;

    private String id;


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            //fill combo box
            ArrayList<String> eList = new ArrayList<>();
            JSONArray employees = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() +
                    "&password=" + Credentials.getPass() + "&request=getEmployee"));

            strFname  = new String[employees.length()];
            strLname  = new String[employees.length()];
            strAddr   = new String[employees.length()];
            strCity   = new String[employees.length()];
            strState  = new String[employees.length()];
            strZip    = new String[employees.length()];
            strSalary = new String[employees.length()];
            strTitle  = new String[employees.length()];
            strUsrn   = new String[employees.length()];
            strNum1 = new String[employees.length()];
            strNum2 = new String[employees.length()];
            strNum3 = new String[employees.length()];
            String strstr = new String();
            String[] strPhone;

            for (int i = 0; i < employees.length(); i++) {
                JSONObject employee = employees.getJSONObject(i);
                String strEmpNum = employee.get("Emp_EmployeeNumber").toString();
                strFname[i]  = employee.get("Emp_FirstName").toString();
                strLname[i]  = employee.get("Emp_LastName").toString();
                strAddr[i]   = employee.get("Emp_StreetAddress").toString();
                strCity[i]   = employee.get("Emp_City").toString();
                strState[i]  = employee.get("Emp_State").toString();
                strZip[i]    = employee.get("Emp_ZipCode").toString();
                strstr  = employee.get("Emp_PhoneNumber").toString();
                strPhone = strstr.split("-");
                strNum1[i] = strPhone[0];
                strNum2[i] = strPhone[1].toString();
                strNum3[i] = strPhone[2].toString();
                strSalary[i] = employee.get("Emp_Salary").toString();
                strTitle[i]  = employee.get("Emp_JobTitle").toString();
                strUsrn[i]   = employee.get("Emp_Username").toString();

                eList.add(strEmpNum);
            }

            ObservableList<String> eobList = FXCollections.observableArrayList(eList);

            comID.setItems(eobList);
        } catch (IOException e) {
        }

        //create event handlers to make sure that valid input is being entered

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
    }

    private String readJsonFromUrl(String urlString) throws IOException, JSONException {
        System.out.println(urlString);
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
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    @FXML
    private void comboAction(ActionEvent event) {

        id = comID.getValue().toString();
        System.out.println(id);
        txtFName.setText(strFname[comID.getSelectionModel().getSelectedIndex()]);

        txtLName.setText((strLname[comID.getSelectionModel().getSelectedIndex()]));

        txtStreet.setText((strAddr[comID.getSelectionModel().getSelectedIndex()]));

        txtCity.setText((strCity[comID.getSelectionModel().getSelectedIndex()]));

        txtState.setText((strState[comID.getSelectionModel().getSelectedIndex()]));

        txtZIP.setText((strZip[comID.getSelectionModel().getSelectedIndex()]));

        txtNUM1.setText((strNum1[comID.getSelectionModel().getSelectedIndex()]));

        txtNUM2.setText((strNum2[comID.getSelectionModel().getSelectedIndex()]));

        txtNUM3.setText((strNum3[comID.getSelectionModel().getSelectedIndex()]));

        txtSalary.setText((strSalary[comID.getSelectionModel().getSelectedIndex()]));

        txtTitle.setText((strTitle[comID.getSelectionModel().getSelectedIndex()]));

        txtUser.setText((strUsrn[comID.getSelectionModel().getSelectedIndex()]));

    }

    @FXML
    private void updateEmployee(ActionEvent event) throws IOException {
        Parent managerView = FXMLLoader.load(getClass().getResource("manager.fxml"));
        Scene managerViewScene = new Scene(managerView);

        String strFname = URLEncoder.encode(txtFName.getText(), "UTF-8");
        String strLname = URLEncoder.encode(txtLName.getText(), "UTF-8");
        String strAddr = URLEncoder.encode(txtStreet.getText(), "UTF-8");
        String strCity = URLEncoder.encode(txtCity.getText(), "UTF-8");
        String strState = URLEncoder.encode(txtState.getText(), "UTF-8");
        String strZip = URLEncoder.encode(txtZIP.getText(), "UTF-8");
        String strNum1 = URLEncoder.encode(txtNUM1.getText(), "UTF-8");
        String strNum2 = URLEncoder.encode(txtNUM2.getText(), "UTF-8");
        String strNum3 = URLEncoder.encode(txtNUM3.getText(), "UTF-8");
        String strSalary = URLEncoder.encode(txtSalary.getText(), "UTF-8");
        String strTitle = URLEncoder.encode(txtTitle.getText(), "UTF-8");
        String strUsrn = URLEncoder.encode(txtUser.getText(), "UTF-8");

        JSONObject result = new JSONObject(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() +
                "&password=" + Credentials.getPass() + "&request=updateProductEmployee&empNum=" + id + "&fName=" + strFname + "&lName=" + strLname +
                "&address=" + strAddr + "&city=" + strCity + "&state=" + strState + "&zip=" + strZip + "&phone=" + strNum1 + "-" + strNum2 + "-" + strNum3 +
                "&salary=" + strSalary + "&title=" + strTitle + "&empUser=" + strUsrn));
        boolean isWorking = result.getBoolean("result");
        if (isWorking) {
            JFrame j = new JFrame();
            JOptionPane.showMessageDialog(j,"Product Successfully Updated!");
        }
        else if (!isWorking){
            JFrame j = new JFrame();
            JOptionPane.showMessageDialog(j, "Failed To Update Product.", "Alert" , JOptionPane.WARNING_MESSAGE);
        }

        //this will get the stage information
        Stage window;
        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(managerViewScene);

        window.show();
    }
}
