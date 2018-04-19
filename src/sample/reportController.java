package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class reportController implements Initializable {

        @FXML
        private TableView table;

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
            finally {
                if (reader != null)
                    reader.close();
            }
        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            try {
                ArrayList<Customer> list = new ArrayList<>();
                JSONArray customers = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=jg23&password=I$lovedogs4433&request=getCustomer"));

                TableColumn name = new TableColumn("Name");
                TableColumn street = new TableColumn("Street");
                TableColumn city = new TableColumn("City");
                TableColumn state = new TableColumn("State");
                TableColumn zip = new TableColumn("ZIP");
                TableColumn id = new TableColumn("ID");
                TableColumn email = new TableColumn("Email");
                TableColumn phone = new TableColumn("Phone");
                table.getColumns().addAll(name, street, city, state, zip, id, email, phone);

                for (int i = 0; i < customers.length(); i++) {
                    JSONObject customer = customers.getJSONObject(i);
                    System.out.println(customer);
                    String strName = customer.getString("Cus_Name");
                    String strStreet = customer.getString("Cus_StreetAddress");
                    String strCity = customer.getString("Cus_City");
                    String strState = customer.getString("Cus_State");
                    String strZIP = customer.getString("Cus_Zipcode");
                    String strID = customer.getString("Cus_CustomerNumber");
                    String strEmail = customer.getString("Cus_Email");
                    String strPhone = customer.getString("Cus_PhoneNumber");
                    list.add(new Customer(strName, strStreet, strCity, strState, strZIP, strPhone, strEmail, strID));
                }
                System.out.println(list);
                ObservableList<Customer> obList = FXCollections.observableArrayList(list);
                name.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
                street.setCellValueFactory(new PropertyValueFactory<Customer, String>("street"));
                city.setCellValueFactory(new PropertyValueFactory<Customer, String>("city"));
                state.setCellValueFactory(new PropertyValueFactory<Customer, String>("state"));
                zip.setCellValueFactory(new PropertyValueFactory<Customer, String>("zip"));
                id.setCellValueFactory(new PropertyValueFactory<Customer, String>("id"));
                email.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
                phone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));

                table.setItems(obList);

                System.out.println(customers);
            } catch (IOException e) {
                e.printStackTrace();
            }


    }

}
