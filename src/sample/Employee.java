package sample;

import javafx.beans.property.SimpleStringProperty;

public class Employee {
    private final SimpleStringProperty number;
    private final SimpleStringProperty firstN;
    private final SimpleStringProperty lastN;
    private final SimpleStringProperty street;
    private final SimpleStringProperty city;
    private final SimpleStringProperty state;
    private final SimpleStringProperty zip;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty hDate;
    private final SimpleStringProperty salary;
    private final SimpleStringProperty bDate;
    private final SimpleStringProperty title;
    private final SimpleStringProperty active;
    private final SimpleStringProperty username;
    private final SimpleStringProperty comboListing;

    /*constructor to load up parsed JSON data in the form of objects, before being
    loaded into the columns of the JavaFX tableview*/

    public Employee(String number, String firstN, String lastN, String street, String city,
                    String state, String zip, String phone, String hDate, String salary, String bDate,
                    String title, String active, String username) {
        this.number = new SimpleStringProperty(number);
        this.firstN = new SimpleStringProperty(firstN);
        this.lastN = new SimpleStringProperty(lastN);
        this.street = new SimpleStringProperty(street);
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
        this.zip = new SimpleStringProperty(zip);
        this.phone = new SimpleStringProperty(phone);
        this.hDate = new SimpleStringProperty(hDate);
        this.salary = new SimpleStringProperty(salary);
        this.bDate = new SimpleStringProperty(bDate);
        this.title = new SimpleStringProperty(title);
        this.active = new SimpleStringProperty(active);
        this.username = new SimpleStringProperty(username);
        this.comboListing = null;
    }

    public String getComboListing() {
        return comboListing.get();
    }

    public SimpleStringProperty comboListingProperty() {
        return comboListing;
    }

    public Employee(String number, String firstN, String lastN){
        this.number = new SimpleStringProperty(number);
        this.comboListing = new SimpleStringProperty(number + "-" + lastN + ", " + firstN);
        this.firstN = null;
        this.lastN = null;
        this.active = null;
        this.bDate = null;
        this.city = null;
        this.hDate = null;
        this.phone = null;

        this.salary = null;
        this.state = null;
        this.street = null;
        this.title = null;
        this.zip = null;
        this.username = null;
    }

    public String getNumber() {
        return number.get();
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

    public String getFirstN() {
        return firstN.get();
    }

    public SimpleStringProperty firstNProperty() {
        return firstN;
    }

    public String getLastN() {
        return lastN.get();
    }

    public SimpleStringProperty lastNProperty() {
        return lastN;
    }

    public String getStreet() {
        return street.get();
    }

    public SimpleStringProperty streetProperty() {
        return street;
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public String getState() {
        return state.get();
    }

    public SimpleStringProperty stateProperty() {
        return state;
    }

    public String getZip() {
        return zip.get();
    }

    public SimpleStringProperty zipProperty() {
        return zip;
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public String gethDate() {
        return hDate.get();
    }

    public SimpleStringProperty hDateProperty() {
        return hDate;
    }

    public String getSalary() {
        return salary.get();
    }

    public SimpleStringProperty salaryProperty() {
        return salary;
    }

    public String getbDate() {
        return bDate.get();
    }

    public SimpleStringProperty bDateProperty() {
        return bDate;
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public String getActive() {
        return active.get();
    }

    public SimpleStringProperty activeProperty() {
        return active;
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }
}
