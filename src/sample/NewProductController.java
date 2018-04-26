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
import javafx.scene.control.TextArea;
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

public class NewProductController implements Initializable {

    private String strNUM, strNAM, strSAL, strPUR, strTYP, strDES;

    @FXML
    private Button cancel, create;
    @FXML
    private TextField txtNUM, txtNAM, txtSAL, txtPUR;
    @FXML
    private ComboBox<String> cmbTYP;
    @FXML
    private TextArea txtDES;

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
    private void createProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("manager.fxml"));
        Parent managerView = loader.load();
        Scene managerViewScene = new Scene(managerView);
        Boolean exists = false;

        if (txtDES.getText().isEmpty() || txtNAM.getText().isEmpty() || txtNUM.getText().isEmpty() || txtSAL.getText().isEmpty() || txtPUR.getText().isEmpty() || cmbTYP.getSelectionModel().isEmpty()) {
            JFrame j = new JFrame();
            JOptionPane.showMessageDialog(j, "Please Make Sure All Fields Are Filled.");
        } else {
            JSONArray products = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() + "&password=" + Credentials.getPass() +
                    "&request=getProduct"));

            for (int i = 0; i < products.length(); i++) {
                JSONObject product = products.getJSONObject(i);
                String partNum = product.get("Inv_ItemNumber").toString();
                if (partNum.equals(txtNAM.getText()))
                    exists = true;
            }
            if(!exists){
            strDES = URLEncoder.encode(txtDES.getText(), "UTF-8");
            strNAM = URLEncoder.encode(txtNAM.getText(), "UTF-8");
            strNUM = URLEncoder.encode(txtNUM.getText(), "UTF-8");
            strPUR = URLEncoder.encode(txtPUR.getText(), "UTF-8");
            strSAL = URLEncoder.encode(txtSAL.getText(), "UTF-8");
            strTYP = URLEncoder.encode(cmbTYP.getSelectionModel().getSelectedItem(), "UTF-8");

            JSONObject update = new JSONObject(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() + "&password=" + Credentials.getPass() +
                    "&request=createProduct&productNum=" + strNUM + "&name=" + strNAM + "&type=" + strTYP + "&description=" + strDES + "&minStock=0&stock=0&backOrder=0&wholesale=" +
                    strPUR + "&retail=" + strSAL));
            boolean result = update.getBoolean("result");
            if (result) {
                JFrame j = new JFrame();
                JOptionPane.showMessageDialog(j, "Product Created Successfully!");
            } else if (!result) {
                JFrame j = new JFrame();
                JOptionPane.showMessageDialog(j, "Failed To Create Product.", "Alert", JOptionPane.WARNING_MESSAGE);
            }

            ManagerController mc = loader.getController();
            mc.resetColumns();
            mc.initialize();
            //this will get the stage information
            Stage window;
            window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(managerViewScene);

            window.show();}
            else {
                JFrame j = new JFrame();
                JOptionPane.showMessageDialog(j, "Product Already Created.");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> combo = new ArrayList<>();
        combo.add("Label");
        combo.add("Printer");
        combo.add("Scanner");
        combo.add("Part");
        ObservableList<String> obCombo = FXCollections.observableArrayList(combo);
        cmbTYP.setItems(obCombo);

        txtNUM.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}")) {
                    txtNUM.setText(oldValue);
                }
            }
        });
        txtSAL.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                    txtSAL.setText(oldValue);
                }
            }
        });
        txtPUR.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                    txtPUR.setText(oldValue);
                }
            }
        });
    }

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
            return buffer.toString();
        } catch (Throwable e) {
            JFrame j = new JFrame();
            JOptionPane.showMessageDialog(j, "Failed To Create Product.", "Alert", JOptionPane.WARNING_MESSAGE);
            return null;
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
