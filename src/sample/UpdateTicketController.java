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

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateTicketController {

    private String strMIL, strTIM, strDAT, strTechID, strPRT, strCMT, strInvID, strStatus;
    private ArrayList<String> strTechs;

    @FXML
    private TableView table;

    @FXML
    private Button cancel, update, deleteLine, addLine;
    @FXML
    private TextField txtMIL, txtTIM, txtLineQuan, txtLineComment;
    @FXML
    private TextArea txtPRT, txtCMT;
    @FXML
    private ComboBox comTechID, comPartNum;
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
    private void updateTicket(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("manager.fxml"));
        Parent managerView = loader.load();
        Scene managerViewScene = new Scene(managerView);


        strTechID = strTechs.get(comTechID.getSelectionModel().getSelectedIndex());
        strStatus = "1";

        if(!isDone.isSelected()) {
            datePicker.setValue(LocalDate.now());
            strDAT = URLEncoder.encode(datePicker.getValue().toString(), "UTF-8");
        }
        else {
            strDAT = URLEncoder.encode(datePicker.getValue().toString(), "UTF-8");
            strStatus = "0";
        }

        if(txtMIL.getText().trim().equals(""))
            strMIL = "0.00";
        else
            strMIL = URLEncoder.encode(txtMIL.getText(), "UTF-8");
        if(txtTIM.getText().trim().equals(""))
            strTIM = "0.00";
        else
            strTIM = URLEncoder.encode(txtTIM.getText(), "UTF-8");
        strCMT = URLEncoder.encode(txtCMT.getText(), "UTF-8");
        strPRT = URLEncoder.encode(txtPRT.getText(), "UTF-8");

        JSONObject update = new JSONObject(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() +
                "&password=" + Credentials.getPass() + "&request=updateInvoice&invoiceId=" + strInvID + "&dateCom=" + strDAT + "&status=" + strStatus +
                "&time=" + strTIM + "&comment=" + strCMT + "&parts=" + strPRT + "&miles=" + strMIL + "&empNumber=" + strTechID));
        String result = update.get("result").toString();
        if (result.equals("true")) {
            JFrame j = new JFrame();
            JOptionPane.showMessageDialog(j,"Ticket Updated Successfully!");
        }
        else if (result.equals("false")){
            JFrame j = new JFrame();
            JOptionPane.showMessageDialog(j, "Failed To Update Ticket.", "Alert" , JOptionPane.WARNING_MESSAGE);
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

    @FXML
    public void initialize(ManagerTicket mt) throws IOException {
        datePicker.disableProperty().bind(isDone.selectedProperty().not());

        if (!mt.getCustomerTicketID().equals("0-0")) {
            String empTicket;
            String[] splitID;
            splitID = mt.getCustomerTicketID().split("-");
            strInvID = splitID[0];

            ArrayList<String> idList = new ArrayList<>();

           // JSONArray parts = new JSONArray(readJsonFromUrl(""))

            JSONArray techs = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() +
                    "&password=" + Credentials.getPass() + "&request=getEmployee&title=technician"));

            JSONArray invoices = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() +
                    "&password=" + Credentials.getPass() + "&request=getInvoice&invoiceId="+strInvID));
            JSONObject invoice = invoices.getJSONObject(0);
            txtTIM.setText(invoice.get("Ser_RepairTime").toString());
            txtMIL.setText(invoice.get("Ser_TechMiles").toString());
            txtCMT.setText(invoice.get("Ser_Comments").toString());

            if (txtCMT.getText().equals("null"))
                txtCMT.setText("");

            txtPRT.setText(invoice.get("Ser_Parts").toString());

            if (txtPRT.getText().equals("null"))
                txtPRT.setText("");

            empTicket = invoice.get("Emp_EmployeeNumber").toString();

            strTechs = new ArrayList<>();

            for (int i = 0; i < techs.length(); i++) {
                JSONObject tech = techs.getJSONObject(i);
                String id = tech.get("Emp_EmployeeNumber").toString();
                String fName = tech.get("Emp_FirstName").toString();
                String lName = tech.get("Emp_LastName").toString();
                idList.add(id + "-" + lName + ", " + fName);
                strTechs.add(id);
            }
            ObservableList<String> idOBList = FXCollections.observableArrayList(idList);

            comTechID.setItems(idOBList);

            comTechID.getSelectionModel().select(strTechs.indexOf(empTicket));

            if(empTicket.equals("null"))
                comTechID.getSelectionModel().selectFirst();

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
            return buffer.toString();}
        catch (Throwable e){
            JFrame j = new JFrame();
            JOptionPane.showMessageDialog(j, "Failed To Update Ticket.", "Alert" , JOptionPane.WARNING_MESSAGE);
            return null;
        }
        finally {
            if (reader != null)
                reader.close();
        }
    }
}
