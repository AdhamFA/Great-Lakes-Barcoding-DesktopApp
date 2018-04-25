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

public class UpdateProductController implements Initializable {

    @FXML
    private Button cancel, update;
    @FXML
    private TextField txtNAME, txtMIN, txtCUR, txtON, txtPURCH, txtSALE;
    @FXML
    private ComboBox cmbNUM;

    private String[] strNames, strMin, strCurr, strOnOrder, strPurchPrice, strSalePrice;

    private String id, strMIN, strCUR, strON, strPURCH, strSALE;


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
    private void updateProduct(ActionEvent event) throws IOException {
        Parent managerView = FXMLLoader.load(getClass().getResource("manager.fxml"));
        Scene managerViewScene = new Scene(managerView);

        strMIN = URLEncoder.encode(txtMIN.getText(), "UTF-8");
        strCUR = URLEncoder.encode(txtCUR.getText(), "UTF-8");
        strON = URLEncoder.encode(txtON.getText(), "UTF-8");
        strPURCH = URLEncoder.encode(txtON.getText(), "UTF-8");
        strSALE = URLEncoder.encode(txtON.getText(), "UTF-8");

        JSONObject result = new JSONObject(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() +
                "&password=" + Credentials.getPass() + "&request=updateProduct&productNum=" + id + "&minStock=" + strMIN + "&stock=" + strCUR +
                "&backOrder=" + strON + "&wholesale=" + strPURCH + "&retail=" + strSALE));

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            //fill combo box
            ArrayList<String> pList = new ArrayList<>();
            JSONArray products = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() +
                    "&password=" + Credentials.getPass() + "&request=getProduct"));
            strNames = new String[products.length()];
            strMin = new String[products.length()];
            strCurr = new String[products.length()];
            strOnOrder = new String[products.length()];
            strPurchPrice = new String[products.length()];
            strSalePrice = new String[products.length()];

            for (int i = 0; i < products.length(); i++) {
                JSONObject product = products.getJSONObject(i);
                String strNum = product.get("Inv_ItemNumber").toString();
                strNames[i] = product.get("Inv_ItemName").toString();
                strMin[i] = product.get("Inv_MinStock").toString();
                strCurr[i] = product.get("Inv_CurrStock").toString();
                strOnOrder[i] = product.get("Inv_OnOrder").toString();
                strPurchPrice[i] = product.get("Inv_PurchaseCost").toString();
                strSalePrice[i] = product.get("Inv_SaleCost").toString();
                pList.add(strNum);
            }

            ObservableList<String> pobList = FXCollections.observableArrayList(pList);

            cmbNUM.setItems(pobList);
        } catch (IOException e) {
        }

        //create event handlers to make sure that valid input is being entered
        txtMIN.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}")) {
                    txtMIN.setText(oldValue);
                }
            }
        });

        txtCUR.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}")) {
                    txtCUR.setText(oldValue);
                }
            }
        });

        txtON.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}")) {
                    txtON.setText(oldValue);
                }
            }
        });

        txtPURCH.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                    txtON.setText(oldValue);
                }
            }
        });

        txtSALE.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                    txtON.setText(oldValue);
                }
            }
        });

    }

    @FXML
    private void comboAction(ActionEvent event) {

        id = cmbNUM.getValue().toString();
        System.out.println(id);
        txtNAME.setText(strNames[cmbNUM.getSelectionModel().getSelectedIndex()]);

        txtMIN.setText((strMin[cmbNUM.getSelectionModel().getSelectedIndex()]));

        txtCUR.setText((strCurr[cmbNUM.getSelectionModel().getSelectedIndex()]));

        txtON.setText((strOnOrder[cmbNUM.getSelectionModel().getSelectedIndex()]));

        txtPURCH.setText((strPurchPrice[cmbNUM.getSelectionModel().getSelectedIndex()]));

        txtSALE.setText((strSalePrice[cmbNUM.getSelectionModel().getSelectedIndex()]));

    }

    @FXML
    private void updateScene(ActionEvent event) throws IOException {
        Parent managerView = FXMLLoader.load(getClass().getResource("manager.fxml"));
        Scene managerViewScene = new Scene(managerView);

        //this will get the stage information
        Stage window;
        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(managerViewScene);

        window.show();
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
            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

}