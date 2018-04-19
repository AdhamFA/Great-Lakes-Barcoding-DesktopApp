package sample;

import javafx.beans.property.SimpleStringProperty;

public class Customer
{
    private final SimpleStringProperty name;
    private final SimpleStringProperty street;
    private final SimpleStringProperty city;
    private final SimpleStringProperty state;
    private final SimpleStringProperty zip;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty email;
    private final SimpleStringProperty id;

    /*constructor to load up parsed JSON data in the form of objects, before being
    loaded into the columns of the JavaFX tableview*/
    Customer(String name,String street,String city,String state,String zip,String phone,String email,String id)
    {
        this.name = new SimpleStringProperty(name);
        this.street = new SimpleStringProperty(street);
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
        this.zip = new SimpleStringProperty(zip);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.id = new SimpleStringProperty(id);
    }

    public String getName() {
        return name.get();
    }

    public String getStreet() {
        return street.get();
    }

    public String getCity() {
        return city.get();
    }

    public String getState() {
        return state.get();
    }

    public String getZip() {
        return zip.get();
    }

    public String getPhone() {
        return phone.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getId() {
        return id.get();
    }
}