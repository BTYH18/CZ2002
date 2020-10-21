package model;

import controller.ServiceMenuController;

import java.util.ArrayList;

/**
 * Created by Gianpaolo & Brighten
 */
public class RoomService {

    private String roomNo;
    private String remarks;

    private ArrayList<Order> orders = new ArrayList<>();

    public RoomService(String roomNo, String remarks) {
        this.roomNo = roomNo;
        this.remarks = remarks;
        this.orders = new ArrayList<>();
    }

    public void addOrders(ArrayList<Order> o) {
        this.orders.addAll(o);
    }

    public double getTotalCost() {
        double totalCost = 0.0;

        for (Order o : orders) {
            //TODO: possibly remove if condition for demo if orders aren't being updated
            if (o.getStatus().equals(Order.ServiceStatus.Delivered)) {
                totalCost += ServiceMenuController.getMenu().get(o.getMenuIndex()).getPrice() * o.getQuantity();
            }
        }

        return totalCost;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
