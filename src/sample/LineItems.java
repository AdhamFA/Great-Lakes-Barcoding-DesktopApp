package sample;

import javafx.beans.property.SimpleStringProperty;

public class LineItems {
    private SimpleStringProperty invoiceID;
    private SimpleStringProperty itemNumber;
    private SimpleStringProperty quantity;
    private SimpleStringProperty comment;

    public LineItems(String invoiceID, String itemNumber, String quantity, String comment){
        this.invoiceID = new SimpleStringProperty(invoiceID);
        this.itemNumber = new SimpleStringProperty(itemNumber);
        this.quantity = new SimpleStringProperty(quantity);
        this.comment = new SimpleStringProperty(comment);
    }

    public String getInvoiceID() {
        return invoiceID.get();
    }

    public SimpleStringProperty invoiceIDProperty() {
        return invoiceID;
    }

    public String getItemNumber() {
        return itemNumber.get();
    }

    public SimpleStringProperty itemNumberProperty() {
        return itemNumber;
    }

    public String getQuantity() {
        return quantity.get();
    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }

    public String getComment() {
        return comment.get();
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }
}
