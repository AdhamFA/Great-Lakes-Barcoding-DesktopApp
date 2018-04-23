package sample;

import javafx.beans.property.SimpleStringProperty;

public class ManagerTicket {
    private final SimpleStringProperty customerTicketID;

    public ManagerTicket(String customer, String ticketID) {
        this.customerTicketID = new SimpleStringProperty(ticketID + "-" + customer);
    }

    public ManagerTicket(String customerTicketID) {
        this.customerTicketID = new SimpleStringProperty(customerTicketID);
    }

    public String getCustomerTicketID() {
        return customerTicketID.get();
    }

    public SimpleStringProperty customerTicketIDProperty() {
        return customerTicketID;
    }
}

