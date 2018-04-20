package sample;

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
import java.util.ArrayList;

public class ManagerController {

    @FXML
    private Parent root;
    @FXML
    private Button btnLogout;
    @FXML
    private Label lblWelcome;
    @FXML
    private MenuItem mst_New, mst_Inprogress, mst_Finished,
                     rprt_Tickets, rprt_Invoices, rprt_Employees,
                     rprt_Customers, rprt_Contracts, rcrd_Mileage,
                     rcrd_Hours, help_About;

    @FXML
    public void setText(String name){
        lblWelcome.setText("Welcome back, " + name +"!");
    }

    @FXML //this method will change the scene when the button is clicked and the requirements are met
    private void logoutButtonAction(ActionEvent event) throws IOException {
        Parent loginView = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene loginViewScene = new Scene(loginView);

        //this will get the stage information
        Stage window;
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginViewScene);
        window.show();
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
        finally {
            if (reader != null)
                reader.close();
        }
    }

    @FXML
    private void rprtOnAction(ActionEvent event) throws IOException {
        MenuItem miEvent = (MenuItem) event.getSource();
        String mieID = miEvent.getId();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("report.fxml"));
        fxmlLoader.load();
        Parent reportView = fxmlLoader.getRoot();
        Scene reportViewScene = new Scene(reportView);
        reportController rc = fxmlLoader.getController();

        switch (mieID){

            case "rprt_Customers":
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
            rc.setTableColumns(name, street, city, state, zip, id, email, phone);

            for (int i = 0; i < customers.length(); i++) {
                JSONObject customer = customers.getJSONObject(i);
                System.out.println(customer);
                String strName = customer.getString("Cus_Name");
                String strStreet = customer.getString("Cus_StreetAddress");
                String strCity = customer.getString("Cus_City");
                String strState = customer.getString("Cus_State");
                String strZIP = customer.getString("Cus_ZipCode");
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

            rc.setTable(obList);
            break;

            /*case "rprt_Contracts":
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
                rc.setTableColumns(name, street, city, state, zip, id, email, phone);

                for (int i = 0; i < customers.length(); i++) {
                    JSONObject customer = customers.getJSONObject(i);
                    System.out.println(customer);
                    String strName = customer.getString("Cus_Name");
                    String strStreet = customer.getString("Cus_StreetAddress");
                    String strCity = customer.getString("Cus_City");
                    String strState = customer.getString("Cus_State");
                    String strZIP = customer.getString("Cus_ZipCode");
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

                rc.setTable(obList);
                break;*/

            case "rprt_Employees":
                ArrayList<Employee> elist = new ArrayList<>();
                JSONArray employees = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=jg23&password=I$lovedogs4433&request=getEmployee"));

                TableColumn eID = new TableColumn("ID");
                TableColumn fName = new TableColumn("First Name");
                TableColumn lName = new TableColumn("Last Name");
                TableColumn eStreet = new TableColumn("Street");
                TableColumn eCity = new TableColumn("City");
                TableColumn eState = new TableColumn("State");
                TableColumn eZip = new TableColumn("ZIP");
                TableColumn ePhone = new TableColumn("Phone");
                TableColumn eHire = new TableColumn("Hire Date");
                TableColumn eSalary = new TableColumn("Salary");
                TableColumn eBirth = new TableColumn("Birth Date");
                TableColumn eTitle = new TableColumn("Title");
                TableColumn eActive = new TableColumn("Active");
                TableColumn eUser = new TableColumn("UserName");
                rc.setTableColumns(eID, fName, lName, eStreet, eCity, eState, eZip, ePhone, eHire, eSalary, eBirth, eTitle, eActive, eUser);

                for (int i = 0; i < employees.length(); i++) {
                    JSONObject employee = employees.getJSONObject(i);
                    System.out.println(employee);
                    String strEID = employee.getString("Emp_EmployeeNumber");
                    String strEFN = employee.getString("Emp_FirstName");
                    String strELN = employee.getString("Emp_LastName");
                    String strESTRT = employee.getString("Emp_StreetAddress");
                    String strECTY = employee.getString("Emp_City");
                    String strESTT = employee.getString("Emp_State");
                    String strEZIP = employee.getString("Emp_ZipCode");
                    String strEPHN = employee.getString("Emp_PhoneNumber");
                    String strEHRE = employee.getString("Emp_HireDate");
                    String strESAL = employee.getString("Emp_Salary");
                    String strEBRTH = employee.getString("Emp_BirthDate");
                    String strET = employee.getString("Emp_JobTitle");
                    String strEACT = employee.getString("Emp_Active");
                    String strEUSR = employee.getString("Emp_Username");
                    elist.add(new Employee(strEID, strEFN, strELN, strESTRT, strECTY, strESTT, strEZIP, strEPHN, strEHRE, strESAL, strEBRTH, strET, strEACT, strEUSR));
                }
                ObservableList<Employee> eobList = FXCollections.observableArrayList(elist);
                eID.setCellValueFactory(new PropertyValueFactory<Customer, String>("number"));
                fName.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstN"));
                lName.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastN"));
                eStreet.setCellValueFactory(new PropertyValueFactory<Customer, String>("street"));
                eCity.setCellValueFactory(new PropertyValueFactory<Customer, String>("city"));
                eState.setCellValueFactory(new PropertyValueFactory<Customer, String>("state"));
                eZip.setCellValueFactory(new PropertyValueFactory<Customer, String>("zip"));
                ePhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
                eHire.setCellValueFactory(new PropertyValueFactory<Customer, String>("hDate"));
                eSalary.setCellValueFactory(new PropertyValueFactory<Customer, String>("salary"));
                eBirth.setCellValueFactory(new PropertyValueFactory<Customer, String>("bDate"));
                eTitle.setCellValueFactory(new PropertyValueFactory<Customer, String>("title"));
                eActive.setCellValueFactory(new PropertyValueFactory<Customer, String>("active"));
                eUser.setCellValueFactory(new PropertyValueFactory<Customer, String>("username"));

                rc.setTable(eobList);
                break;

           /* case "rprt_Invoices":
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
                rc.setTableColumns(name, street, city, state, zip, id, email, phone);

                for (int i = 0; i < customers.length(); i++) {
                    JSONObject customer = customers.getJSONObject(i);
                    System.out.println(customer);
                    String strName = customer.getString("Cus_Name");
                    String strStreet = customer.getString("Cus_StreetAddress");
                    String strCity = customer.getString("Cus_City");
                    String strState = customer.getString("Cus_State");
                    String strZIP = customer.getString("Cus_ZipCode");
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

                rc.setTable(obList);
                break;

            case "rprt_Tickets":
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
                rc.setTableColumns(name, street, city, state, zip, id, email, phone);

                for (int i = 0; i < customers.length(); i++) {
                    JSONObject customer = customers.getJSONObject(i);
                    System.out.println(customer);
                    String strName = customer.getString("Cus_Name");
                    String strStreet = customer.getString("Cus_StreetAddress");
                    String strCity = customer.getString("Cus_City");
                    String strState = customer.getString("Cus_State");
                    String strZIP = customer.getString("Cus_ZipCode");
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

                rc.setTable(obList);
                break;*/
        }


        Stage window;
        window = (Stage) (root.getScene().getWindow());
        window.setScene(reportViewScene);
        window.show();
    }

/*    @FXML inprogress
    private void serviceNewMIAction(ActionEvent event) throws IOException {
        Parent serviceView = FXMLLoader.load(getClass().getResource("serviceTickets.fxml"));
        Scene loginViewScene = new Scene(serviceView);

        //this will get the stage information
        Stage window;
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginViewScene);
        window.show();
    }*/
}
