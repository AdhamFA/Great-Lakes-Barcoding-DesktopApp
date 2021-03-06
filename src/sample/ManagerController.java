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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class ManagerController {
    @FXML
    private Parent root;
    @FXML
    private Button btnLogout, updateTickets;
    @FXML
    private Label lblWelcome, ticketsCounter;
    @FXML
    private MenuItem mst_New, mst_Inprogress, mst_Finished,
            rprt_Tickets, rprt_Invoices, rprt_Employees,
            rprt_Customers, rprt_Products, rcrd_Mileage,
            rcrd_Hours, help_About;
    @FXML
    private TableView<ManagerTicket> newTickets, wipTickets;

    @FXML //this method will change the scene when the button is clicked and the requirements are met
    private void logoutButtonAction(ActionEvent event) throws IOException {
        Parent loginView = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene loginViewScene = new Scene(loginView);
        newTickets.getItems().clear();
        wipTickets.getItems().clear();
        Credentials crd = new Credentials(null, null, null);
        //this will get the stage information
        Stage window;
        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
            if (buffer.equals(""))
                buffer.append("[]");
            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    @FXML
    private void helpOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("help.fxml"));
        fxmlLoader.load();
        Parent helpView = fxmlLoader.getRoot();
        Scene helpViewScene = new Scene(helpView);
        HelpController helpC = fxmlLoader.getController();

        InputStream in = new FileInputStream("README.md");
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));

        String help, line = bf.readLine();
        StringBuilder sb = new StringBuilder();

        while (line != null) {
            sb.append(line).append("\n");
            line = bf.readLine();
        }
        help = sb.toString();
        helpC.setTextarea(help);

        Stage window;
        window = (Stage) (root.getScene().getWindow());
        window.setScene(helpViewScene);
        window.show();
    }

    @FXML
    private void rprtOnAction(ActionEvent event) throws IOException {
        MenuItem miEvent = (MenuItem) event.getSource();
        String mieID = miEvent.getId();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("report.fxml"));
        fxmlLoader.load();
        Parent reportView = fxmlLoader.getRoot();
        Scene reportViewScene = new Scene(reportView);
        ReportController rc = fxmlLoader.getController();

        switch (mieID) {

            case "rprt_Customers":
                ArrayList<Customer> list = new ArrayList<>();
                JSONArray customers = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() +
                        "&password=" + Credentials.getPass() + "&request=getCustomer"));

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
                    list.add(new Customer(strName, strStreet, strCity, strState, strZIP,
                            strPhone, strEmail, strID));
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

            case "rprt_Products":
                ArrayList<Product> pList = new ArrayList<>();
                JSONArray products = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() +
                        "&password=" + Credentials.getPass() + "&request=getProduct"));
                TableColumn tcNum = new TableColumn("Number");
                TableColumn tcNam = new TableColumn("Name");
                TableColumn tcTyp = new TableColumn("Type");
                TableColumn tcDes = new TableColumn("Description");
                TableColumn tcMIS = new TableColumn("Minimum In Stock");
                TableColumn tcCIS = new TableColumn("Current In Stock");
                TableColumn tcORDR = new TableColumn("On Order");
                TableColumn tcPCST = new TableColumn("Purchase Cost");
                TableColumn tcSCST = new TableColumn("Sales Cost");
                rc.setTableColumns(tcNum, tcNam, tcTyp, tcDes, tcMIS, tcCIS, tcORDR, tcPCST, tcSCST);

                for (int i = 0; i < products.length(); i++) {
                    JSONObject product = products.getJSONObject(i);
                    String strNum = product.get("Inv_ItemNumber").toString();
                    String strNam = product.get("Inv_ItemName").toString();
                    String strTyp = product.get("Inv_ItemType").toString();
                    String strDes = product.get("Inv_Description").toString();
                    String strMIS = product.get("Inv_MinStock").toString();
                    String strCIS = product.get("Inv_CurrStock").toString();
                    String strORDR = product.get("Inv_OnOrder").toString();
                    String strPCST = product.get("Inv_PurchaseCost").toString();
                    String strSCST = product.get("Inv_SaleCost").toString();
                    pList.add(new Product(strNum, strNam, strTyp, strDes, strMIS, strCIS, strORDR, strPCST, strSCST));
                }
                ObservableList<Product> pobList = FXCollections.observableArrayList(pList);
                tcNum.setCellValueFactory(new PropertyValueFactory<Product, String>("number"));
                tcNam.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
                tcTyp.setCellValueFactory(new PropertyValueFactory<Product, String>("type"));
                tcDes.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
                tcMIS.setCellValueFactory(new PropertyValueFactory<Product, String>("mStock"));
                tcCIS.setCellValueFactory(new PropertyValueFactory<Product, String>("cStock"));
                tcORDR.setCellValueFactory(new PropertyValueFactory<Product, String>("onOrder"));
                tcPCST.setCellValueFactory(new PropertyValueFactory<Product, String>("pCost"));
                tcSCST.setCellValueFactory(new PropertyValueFactory<Product, String>("sCost"));
                rc.setTable(pobList);
                break;

            case "rprt_Employees":
                ArrayList<Employee> elist = new ArrayList<>();
                JSONArray employees = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() +
                        "&password=" + Credentials.getPass() + "&request=getEmployee"));

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
                rc.setTableColumns(eID, fName, lName, eStreet, eCity, eState, eZip, ePhone, eHire, eSalary,
                        eBirth, eTitle, eActive, eUser);

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
                    elist.add(new Employee(strEID, strEFN, strELN, strESTRT, strECTY, strESTT, strEZIP,
                            strEPHN, strEHRE, strESAL, strEBRTH, strET, strEACT, strEUSR));
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


            //Katrina attempting to code some more
            case "rprt_Tickets":
                ArrayList<Invoice> tlist = new ArrayList<>();
                JSONArray tickets = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() +
                        "&password=" + Credentials.getPass() + "&request=getInvoice"));

                TableColumn ticketNum = new TableColumn("Invoice Number");
                TableColumn tCustomer = new TableColumn("Customer");
                TableColumn tpoNum = new TableColumn("PO Number");
                TableColumn tstatus = new TableColumn("Status");
                TableColumn tempNum = new TableColumn("Employee Number");
                TableColumn trequested = new TableColumn("Date Requested");
                TableColumn tcompleted = new TableColumn("Date Completed");
                TableColumn treason = new TableColumn("Reason for Service");
                TableColumn trepairTime = new TableColumn("Repair Time");
                TableColumn tcomments = new TableColumn("Technician Comments");
                TableColumn tparts = new TableColumn("Parts Used");
                TableColumn ttechMiles = new TableColumn("Technician Mileage");
                TableColumn tbalance = new TableColumn("Service Balance");
                rc.setTableColumns(ticketNum, tCustomer, tpoNum, tstatus, tempNum, trequested,
                        treason, trepairTime, tcomments, tparts, ttechMiles, tbalance);

                for (int i = 0; i < tickets.length(); i++) {
                    JSONObject ticket = tickets.getJSONObject(i);
                    System.out.println(ticket);
                    String strInvId = ticket.get("Ser_InvoiceId").toString();
                    String strCusNum = ticket.get("Cus_CustomerNumber").toString();
                    String strPO = ticket.get("Ser_PurchaseOrder").toString();
                    String strStat = ticket.get("Ser_Status").toString();
                    String strEmpNum = ticket.get("Emp_EmployeeNumber").toString();
                    String strReqDate = ticket.get("Ser_DateRequested").toString();
                    String strCompDate = ticket.get("Ser_DateCompleted").toString();
                    String strReason = ticket.get("Ser_Reason").toString();
                    String strRepairTime = ticket.get("Ser_RepairTime").toString();
                    String strComments = ticket.get("Ser_Comments").toString();
                    String strParts = ticket.get("Ser_Parts").toString();
                    String strMiles = ticket.get("Ser_TechMiles").toString();
                    String strBalance = ticket.get("Ser_Balance").toString();

                    //if completed date is NOT NULL, display invoice
                    if (Integer.parseInt(strStat) == 0)
                        continue;

                    tlist.add(new Invoice(strInvId, strCusNum, strPO, strStat, strEmpNum, strReqDate, strCompDate,
                            strReason, strRepairTime, strComments, strParts, strMiles, strBalance));
                }
                System.out.println(tlist);
                ObservableList<Invoice> tobList = FXCollections.observableArrayList(tlist);
                ticketNum.setCellValueFactory(new PropertyValueFactory<Invoice, String>("iNumber"));
                tCustomer.setCellValueFactory(new PropertyValueFactory<Invoice, String>("cNumber"));
                tpoNum.setCellValueFactory(new PropertyValueFactory<Invoice, String>("poNumber"));
                tstatus.setCellValueFactory(new PropertyValueFactory<Invoice, String>("stat"));
                tempNum.setCellValueFactory(new PropertyValueFactory<Invoice, String>("eNumber"));
                trequested.setCellValueFactory(new PropertyValueFactory<Invoice, String>("reqDate"));
                treason.setCellValueFactory(new PropertyValueFactory<Invoice, String>("reason"));
                trepairTime.setCellValueFactory(new PropertyValueFactory<Invoice, String>("rTime"));
                tcomments.setCellValueFactory(new PropertyValueFactory<Invoice, String>("comment"));
                tparts.setCellValueFactory(new PropertyValueFactory<Invoice, String>("part"));
                ttechMiles.setCellValueFactory(new PropertyValueFactory<Invoice, String>("tMiles"));
                tbalance.setCellValueFactory(new PropertyValueFactory<Invoice, String>("iBalance"));

                rc.setTable(tobList);
                break;
        }


        Stage window;
        window = (Stage) (root.getScene().getWindow());
        window.setScene(reportViewScene);
        window.show();
    }

    public void initialize() {
        try {
            int totalT = 0;
            ArrayList<ManagerTicket> newTList = new ArrayList<>();
            ArrayList<ManagerTicket> wipTList = new ArrayList<>();
            JSONArray invoices = new JSONArray(readJsonFromUrl("https://dev.cis294.hfcc.edu/api.php?username=" + Credentials.getUser() +
                    "&password=" + Credentials.getPass() + "&request=getInvoice"));
            lblWelcome.setText("Welcome back, " + Credentials.getName());

            TableColumn newTCol = new TableColumn("New Service Tickets");
            TableColumn wipTCol = new TableColumn("Tickets In-Progress");

            newTickets.getColumns().add(newTCol);
            wipTickets.getColumns().add(wipTCol);

            for (int i = 0; i < invoices.length(); i++) {
                JSONObject invoice = invoices.getJSONObject(i);
                if (Integer.parseInt(invoice.get("Ser_Status").toString()) == 2) {
                    String strInvId = invoice.get("Ser_InvoiceId").toString();
                    String strCusNum = invoice.get("Cus_CustomerNumber").toString();
                    newTList.add(new ManagerTicket(strInvId, strCusNum));
                    totalT++;
                } else if (Integer.parseInt(invoice.get("Ser_Status").toString()) == 1) {
                    String strInvId = invoice.get("Ser_InvoiceId").toString();
                    String strCusNum = invoice.get("Cus_CustomerNumber").toString();
                    wipTList.add(new ManagerTicket(strInvId, strCusNum));
                    totalT++;
                }

            }
            ObservableList<ManagerTicket> newobList = FXCollections.observableArrayList(newTList);
            ObservableList<ManagerTicket> wipobList = FXCollections.observableArrayList(wipTList);

            newTCol.setCellValueFactory(new PropertyValueFactory<ManagerTicket, String>("customerTicketID"));
            wipTCol.setCellValueFactory(new PropertyValueFactory<ManagerTicket, String>("customerTicketID"));

            newTickets.setItems(newobList);
            wipTickets.setItems(wipobList);
            ticketsCounter.setText("Total Tickets: " + Integer.toString(totalT));

            if (wipTickets.getSelectionModel().isEmpty() && newTickets.getSelectionModel().isEmpty())
                updateTickets.setDisable(true);
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    @FXML
    public void updateProductOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("updateProduct.fxml"));
        Parent updateProductView = fxmlLoader.load();
        Scene updateProductViewScene = new Scene(updateProductView);
        Stage window;
        window = (Stage) (root.getScene().getWindow());
        window.setScene(updateProductViewScene);
        window.show();
    }

    @FXML
    public void createProductOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newProduct.fxml"));
        Parent newProductView = fxmlLoader.load();
        Scene newProductViewScene = new Scene(newProductView);
        Stage window;
        window = (Stage) (root.getScene().getWindow());
        window.setScene(newProductViewScene);
        window.show();
    }

    @FXML
    public void updateEmployeeOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("updateEmployee.fxml"));
        Parent updateEmployeeView = fxmlLoader.load();
        Scene updateEmployeeViewScene = new Scene(updateEmployeeView);
        Stage window;
        window = (Stage) (root.getScene().getWindow());
        window.setScene(updateEmployeeViewScene);
        window.show();
    }

    @FXML
    public void createEmployeeOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newEmployee.fxml"));
        Parent newEmployeeView = fxmlLoader.load();
        Scene newEmployeeViewScene = new Scene(newEmployeeView);
        Stage window;
        window = (Stage) (root.getScene().getWindow());
        window.setScene(newEmployeeViewScene);
        window.show();
    }

    @FXML
    public void updateTicketOnAction(ActionEvent event) throws IOException {
        ManagerTicket mt;
        if (!wipTickets.getSelectionModel().isEmpty()) {
            mt = new ManagerTicket(wipTickets.getSelectionModel().getSelectedItem().getCustomerTicketID());
        } else if (!newTickets.getSelectionModel().isEmpty()) {
            mt = new ManagerTicket(newTickets.getSelectionModel().getSelectedItem().getCustomerTicketID());
        } else
            return;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("updateTicket.fxml"));
        Parent updateTicketView = fxmlLoader.load();
        Scene updateTicketViewScene = new Scene(updateTicketView);
        Stage window;
        UpdateTicketController updateTicketController = fxmlLoader.getController();
        updateTicketController.initialize(mt);


        window = (Stage) (root.getScene().getWindow());
        window.setScene(updateTicketViewScene);
        window.show();
    }

    @FXML
    public void invoiceReportsOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("invoices.fxml"));
        Parent updateTicketView = fxmlLoader.load();
        Scene updateTicketViewScene = new Scene(updateTicketView);
        Stage window;
        window = (Stage) (root.getScene().getWindow());
        window.setScene(updateTicketViewScene);
        window.show();
    }

    @FXML
    public void onMouseClickedNew(MouseEvent event) {
        wipTickets.getSelectionModel().clearSelection();
        if (!newTickets.getSelectionModel().isEmpty())
            updateTickets.setDisable(false);
        else
            updateTickets.setDisable(true);
    }

    @FXML
    public void onMouseClickedWip(MouseEvent event) {
        newTickets.getSelectionModel().clearSelection();
        if (!wipTickets.getSelectionModel().isEmpty())
            updateTickets.setDisable(false);
        else
            updateTickets.setDisable(true);
    }

    public void resetColumns(){
        newTickets.getColumns().clear();
        wipTickets.getColumns().clear();
    }
}