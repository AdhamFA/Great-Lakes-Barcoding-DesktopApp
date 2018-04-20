package sample;

import javafx.beans.property.SimpleStringProperty;

public class Invoice {
    private final SimpleStringProperty iNumber;
    private final SimpleStringProperty cNumber;
    private final SimpleStringProperty poNumber;
    private final SimpleStringProperty stat;
    private final SimpleStringProperty eNumber;
    private final SimpleStringProperty reqDate;
    private final SimpleStringProperty compDate;
    private final SimpleStringProperty reason;
    private final SimpleStringProperty rTime;
    private final SimpleStringProperty comment;
    private final SimpleStringProperty part;
    private final SimpleStringProperty tMiles;
    private final SimpleStringProperty iBalance;

    public Invoice(String iNumber, String cNumber, String poNumber, String stat, String eNumber, String reqDate, String compDate, String reason, String rTime, String comment, String part, String tMiles, String iBalance ){
        this.iNumber = new SimpleStringProperty(iNumber);
        this.cNumber = new SimpleStringProperty(cNumber);
        this.poNumber = new SimpleStringProperty(poNumber);
        this.stat = new SimpleStringProperty(stat);
        this.eNumber = new SimpleStringProperty(eNumber);
        this.reqDate = new SimpleStringProperty(reqDate);
        this.compDate = new SimpleStringProperty(compDate);
        this.reason = new SimpleStringProperty(reason);
        this.rTime = new SimpleStringProperty(rTime);
        this.comment = new SimpleStringProperty(comment);
        this.part = new SimpleStringProperty(part);
        this.tMiles = new SimpleStringProperty(tMiles);
        this.iBalance = new SimpleStringProperty(iBalance);
    }

    public String getiNumber() {
        return iNumber.get();
    }

    public SimpleStringProperty iNumberProperty() {
        return iNumber;
    }

    public String getcNumber() {
        return cNumber.get();
    }

    public SimpleStringProperty cNumberProperty() {
        return cNumber;
    }

    public String getPoNumber() {
        return poNumber.get();
    }

    public SimpleStringProperty poNumberProperty() {
        return poNumber;
    }

    public String getStat() {
        return stat.get();
    }

    public SimpleStringProperty statProperty() {
        return stat;
    }

    public String geteNumber() {
        return eNumber.get();
    }

    public SimpleStringProperty eNumberProperty() {
        return eNumber;
    }

    public String getReqDate() {
        return reqDate.get();
    }

    public SimpleStringProperty reqDateProperty() {
        return reqDate;
    }

    public String getCompDate() {
        return compDate.get();
    }

    public SimpleStringProperty compDateProperty() {
        return compDate;
    }

    public String getReason() {
        return reason.get();
    }

    public SimpleStringProperty reasonProperty() {
        return reason;
    }

    public String getrTime() {
        return rTime.get();
    }

    public SimpleStringProperty rTimeProperty() {
        return rTime;
    }

    public String getComment() {
        return comment.get();
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    public String getPart() {
        return part.get();
    }

    public SimpleStringProperty partProperty() {
        return part;
    }

    public String gettMiles() {
        return tMiles.get();
    }

    public SimpleStringProperty tMilesProperty() {
        return tMiles;
    }

    public String getiBalance() {
        return iBalance.get();
    }

    public SimpleStringProperty iBalanceProperty() {
        return iBalance;
    }
}
