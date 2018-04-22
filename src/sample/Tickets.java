package sample;

import javafx.beans.property.SimpleStringProperty;

public class Tickets {
    private final SimpleStringProperty cusNumInvID;

    public String getCusNumInvID() {
        return cusNumInvID.get();
    }

    public SimpleStringProperty cusNumInvIDProperty() {
        return cusNumInvID;
    }

    public Tickets(String cusnum, String invID){
        this.cusNumInvID = new SimpleStringProperty(cusnum + "-" + invID);

    }
}
