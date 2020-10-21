package model;

import java.io.Serializable;

/**
 * Created by Gio 27/03/20
 *
 * Stores all payment details for Guest when checking out
 * Calculates total costs for Guest's reservation
 */
public class Payment implements Serializable {

    //Guest - Room model.Reservation Details
    private String roomNum;
    private Room.R_TYPE roomType;
    private String dateIn;
    private String dateOut;
    private int numDayStay;
    private double roomServiceCost;

    //Guest - Payment Details
    private PaymentDetails guestPayDetails;

    //Payment Invoice Details
    private int invoiceNum;
    private double costExcTax;
    private double totalCost;
    private double balance;         //Running balance if Guest paying with cash (i.e. if not paid in full)

    public Payment(String roomNum, Room.R_TYPE roomType, String dateIn, String dateOut, int numDayStay, double roomServiceCost, PaymentDetails guestPayDetails, int invoiceNum, double costExcTax, double totalCost, double balance) {
        this.roomNum = roomNum;
        this.roomType = roomType;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.numDayStay = numDayStay;
        this.roomServiceCost = roomServiceCost;
        this.guestPayDetails = guestPayDetails;
        this.invoiceNum = invoiceNum;
        this.costExcTax = costExcTax;
        this.totalCost = totalCost;
        this.balance = balance;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public Room.R_TYPE getRoomType() {
        return roomType;
    }

    public String getDateIn() {
        return dateIn;
    }

    public String getDateOut() {
        return dateOut;
    }

    public int getNumDayStay() {
        return numDayStay;
    }

    public double getRoomServiceCost() {
        return roomServiceCost;
    }

    public PaymentDetails getGuestPayDetails() {
        return guestPayDetails;
    }

    public int getInvoiceNum() {
        return invoiceNum;
    }

    public double getCostExcTax() {
        return costExcTax;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
