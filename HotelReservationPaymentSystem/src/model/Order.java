package model;

/**
 * Created by Gianpaolo & Brighten
 */
public class Order {

    public enum ServiceStatus {
        Confirmed, Preparing, Delivered
    }

    int menuIndex;
    int quantity;
    private ServiceStatus status;

    public Order(int menuIndex, int quantity) {
        this.menuIndex = menuIndex;
        this.quantity = quantity;
        this.status = ServiceStatus.Confirmed;
    }

    public int getMenuIndex() {
        return menuIndex;
    }

    public int getQuantity() {
        return quantity;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }
}
