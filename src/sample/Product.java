package sample;

import javafx.beans.property.SimpleStringProperty;

public class Product {

    private final SimpleStringProperty number;
    private final SimpleStringProperty name;
    private final SimpleStringProperty type;
    private final SimpleStringProperty description;
    private final SimpleStringProperty mStock;
    private final SimpleStringProperty cStock;
    private final SimpleStringProperty onOrder;
    private final SimpleStringProperty pCost;
    private final SimpleStringProperty sCost;

    public Product(String number, String name, String type, String description, String mStock, String cStock, String onOrder, String pCost, String sCost){
        this.number = new SimpleStringProperty(number);
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
        this.mStock = new SimpleStringProperty(mStock);
        this.cStock = new SimpleStringProperty(cStock);
        this.onOrder = new SimpleStringProperty(onOrder);
        this.pCost = new SimpleStringProperty(pCost);
        this.sCost = new SimpleStringProperty(sCost);
    }

    public String getNumber() {
        return number.get();
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public String getmStock() {
        return mStock.get();
    }

    public SimpleStringProperty mStockProperty() {
        return mStock;
    }

    public String getcStock() {
        return cStock.get();
    }

    public SimpleStringProperty cStockProperty() {
        return cStock;
    }

    public String getOnOrder() {
        return onOrder.get();
    }

    public SimpleStringProperty onOrderProperty() {
        return onOrder;
    }

    public String getpCost() {
        return pCost.get();
    }

    public SimpleStringProperty pCostProperty() {
        return pCost;
    }

    public String getsCost() {
        return sCost.get();
    }

    public SimpleStringProperty sCostProperty() {
        return sCost;
    }
}
