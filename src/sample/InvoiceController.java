package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.sound.sampled.Line;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class InvoiceController implements Initializable {

    private ArrayList<String> strCompletionDate, strCustomerNumber, strCustomerPO, strTechNumber, strPriceTotal, strComments, strInvoiceID, strMileage, strHours;
    private String strID, strMile, strHour;
    private ObservableList<LineItems> tobList = FXCollections.observableArrayList();

    @FXML
    private Button close, export;
    @FXML
    private TextField completionDate, customerNumber, customerPO, technicianNumber,
            txtPriceTotal;
    @FXML
    private TextArea comments;
    @FXML
    private TableView lineItemsTable;
    @FXML
    private ComboBox comInvoiceID;

    @FXML
    private void closeScene(ActionEvent event) throws IOException {
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
            JSONArray invoices = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() + "&password=" + Credentials.getPass() + "&request=getInvoice"));
            strComments = new ArrayList<>();
            strInvoiceID = new ArrayList<>();
            strPriceTotal = new ArrayList<>();
            strTechNumber = new ArrayList<>();
            strCustomerPO = new ArrayList<>();
            strCustomerNumber = new ArrayList<>();
            strCompletionDate = new ArrayList<>();
            strMileage = new ArrayList<>();
            strHours = new ArrayList<>();

            for (int i = 0; i < invoices.length(); i++) {
                JSONObject invoice = invoices.getJSONObject(i);
                if (!invoice.get("Ser_Status").toString().equals("0"))
                    continue;
                else {
                    strComments.add(invoice.get("Ser_Comments").toString());
                    strInvoiceID.add(invoice.get("Ser_InvoiceId").toString());
                    strPriceTotal.add(invoice.get("Ser_Balance").toString());
                    strTechNumber.add(invoice.get("Emp_EmployeeNumber").toString());
                    strCustomerPO.add(invoice.get("Ser_PurchaseOrder").toString());
                    strCustomerNumber.add(invoice.get("Cus_CustomerNumber").toString());
                    strCompletionDate.add(invoice.get("Ser_DateCompleted").toString());
                    strMileage.add(invoice.get("Ser_TechMiles").toString());
                    strHours.add(invoice.get("Ser_RepairTime").toString());
                }
            }

            ObservableList<String> olID = FXCollections.observableArrayList(strInvoiceID);
            comInvoiceID.setItems(olID);


        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void comboAction(ActionEvent event) throws IOException {
        lineItemsTable.getColumns().clear();
        tobList.clear();
        strID = comInvoiceID.getValue().toString();
        strHour = strHours.get(comInvoiceID.getSelectionModel().getSelectedIndex());
        strMile = strMileage.get(comInvoiceID.getSelectionModel().getSelectedIndex());

        TableColumn quantity = new TableColumn("Quantity");
        TableColumn itemNumber = new TableColumn("Item Number");
        TableColumn comment = new TableColumn("Comment");
        TableColumn price = new TableColumn("Item Price");
        TableColumn total = new TableColumn("Item Total");

        lineItemsTable.getColumns().addAll(itemNumber, comment, quantity, price, total);

        itemNumber.setCellValueFactory(new PropertyValueFactory<LineItems, String>("itemNumber"));
        comment.setCellValueFactory(new PropertyValueFactory<LineItems, String>("comment"));
        quantity.setCellValueFactory(new PropertyValueFactory<LineItems, String>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<LineItems, String>("price"));
        total.setCellValueFactory(new PropertyValueFactory<LineItems, String>("total"));

        completionDate.setText(strCompletionDate.get(comInvoiceID.getSelectionModel().getSelectedIndex()));

        customerNumber.setText((strCustomerNumber.get(comInvoiceID.getSelectionModel().getSelectedIndex())));

        customerPO.setText((strCustomerPO.get(comInvoiceID.getSelectionModel().getSelectedIndex())));

        technicianNumber.setText((strTechNumber.get(comInvoiceID.getSelectionModel().getSelectedIndex())));

        txtPriceTotal.setText(strPriceTotal.get(comInvoiceID.getSelectionModel().getSelectedIndex()));

        comments.setText(strComments.get(comInvoiceID.getSelectionModel().getSelectedIndex()));

        JSONArray tickets = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() +
                "&password=" + Credentials.getPass() + "&request=getLineItem"));

        for (int i = 0; i < tickets.length(); i++) {
            JSONObject ticket = tickets.getJSONObject(i);
            System.out.println(ticket);
            String strInvId = ticket.get("Ser_InvoiceId").toString();
            String strItemNum = ticket.get("Inv_ItemNumber").toString();
            String strQuantity = ticket.get("Quantity").toString();
            String strComment = ticket.get("Comments").toString();

            JSONArray prices = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() + "&password=" + Credentials.getPass() + "&request=getProduct&productNum=" + strItemNum));
            JSONObject priceJSON = prices.getJSONObject(0);
            String strPrice = priceJSON.get("Inv_SaleCost").toString();

            if (strInvId.equals(strID))
                tobList.add(new LineItems(strInvId, strItemNum, strQuantity, strComment, strPrice));
        }

        tobList.add(new LineItems(strID, "", strMile, "Mileage", "44.5"));
        tobList.add(new LineItems(strID, "", strHour, "Hours", "50"));
        lineItemsTable.setItems(tobList);


        double totalPrice = 0;
        Iterator<LineItems> iterator = tobList.iterator();
        while (iterator.hasNext()){
            LineItems lineItems = iterator.next();
            double itemTotal = Double.parseDouble(lineItems.getTotal());
            totalPrice += itemTotal;
        }
        txtPriceTotal.setText(Double.toString(totalPrice));
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
}
