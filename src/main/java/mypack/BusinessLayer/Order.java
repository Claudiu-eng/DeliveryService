package mypack.BusinessLayer;

import java.io.Serializable;


public class Order implements Serializable {
    private int orderID,clientID;
    private Date date;
    private double value;

    public Order(int orderID, int clientID, Date date,double value) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.date = date;
        this.value=value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", clientID=" + clientID +
                ", date=" + date.toString() +
                '}';
    }

    @Override
    public int hashCode(){
        return (int) ((date.getYear()* date.getDay()%971+clientID+orderID)%97);
    }

}
