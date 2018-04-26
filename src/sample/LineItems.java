package sample;

import javafx.beans.property.SimpleStringProperty;

import java.io.StringReader;

public class LineItems {
    private SimpleStringProperty invoiceID;
    private SimpleStringProperty itemNumber;
    private SimpleStringProperty quantity;
    private SimpleStringProperty comment;
    private SimpleStringProperty price;
    private SimpleStringProperty total;

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public String getTotal() {
        return total.get();
    }

    public SimpleStringProperty totalProperty() {
        return total;
    }

    public LineItems(String invoiceID, String itemNumber, String quantity, String comment, String price){
        this.invoiceID = new SimpleStringProperty(invoiceID);
        this.itemNumber = new SimpleStringProperty(itemNumber);
        this.quantity = new SimpleStringProperty(quantity);
        this.comment = new SimpleStringProperty(comment);
        this.price = new SimpleStringProperty(price);
        Double tot = Double.parseDouble(price) * Double.parseDouble(quantity);
        total = new SimpleStringProperty(Double.toString(tot));


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
