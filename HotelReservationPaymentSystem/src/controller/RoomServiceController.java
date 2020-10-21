package controller;

import model.*;
import utility.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Gianpaolo & Brighten
 */
public class RoomServiceController {

    public static final String FILE_NAME = "data/room_services.txt";
    private static ArrayList<RoomService> services = new ArrayList<>();

    public static void displayMainMenu() {
        //Write main function menu instructions
        Scanner sc = new Scanner(System.in);
        int choice;

        System.out.println("\n===============================================");
        System.out.println("            Room Service Function");
        System.out.println("===============================================");

        System.out.print("1.Add Room Service " +
                "\n2.Edit Room Service Orders (Add/Update Status/Remove Orders to existing Room Services)  " +
                "\n3.Remove Room Service " +
                "\n3.Current Room Service Status" +
                "\nOther Numerical Number to Exit" +
                "\nChoice:");

        choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            case 1:
                addRoomService();
                break;
            case 2:
                editRoomService();
                break;
            case 3:
                removeRoomService();
            case 4:
                displayServiceStatus();
                break;
            default:
                break;
        }
    }

    public static void addRoomService() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=====================================");
        System.out.println("        Adding Room Service");
        System.out.println("=====================================");

        // Room Service details
        String roomNum = "";
        String remarks = "";
        ArrayList<Order> orders = new ArrayList<>();

        boolean valid = false;

        // Get room number and verify it exists
        while (!valid) {
            System.out.print("Room service room number: ");
            roomNum = sc.nextLine();

            if (RoomController.findRoom(roomNum) != -1) {
                valid = true;
            }
        }

        // Verify room is occupied
        if(RoomController.getStatus(roomNum) != Room.R_Status.Fixed) {
            System.out.print("Room "+ roomNum +" is not checked-in.\n");
            return;
        }

        System.out.println("Please choose item to order for room " + roomNum);
        System.out.println();

        ServiceMenuController.displayMenuItems();
        System.out.println();

        int itemIndex, quantity;
        boolean done = false;

        while (!done) {
            valid = false;
            while (!valid) {
                try {
                    System.out.println("Menu ID (-1 to exit): ");
                    String input = sc.nextLine();
                    itemIndex = Integer.parseInt(input);

                    if (itemIndex == -1) {
                        done = true;
                        break;
                    }

                    if (itemIndex < 0 || itemIndex > ServiceMenuController.getMenu().size()){
                        System.out.println("Item not found");
                        break;
                    }

                    System.out.print("Quantity : ");
                    quantity = Integer.parseInt(sc.nextLine());

                    orders.add(new Order(itemIndex, quantity));

                    valid = true;
                } catch (Exception e) {
                    System.out.println("Invalid input");
                    System.out.println("Exception - " + e);
                    valid = false;
                }
            }
        }

        // IF previous orders for current reservation in room then add to orders else create new service orders
        int serviceExists = getServiceIndex(roomNum);

        if (serviceExists == -1) {
            System.out.print("Remarks: ");
            remarks = sc.nextLine();

            services.add(new RoomService(roomNum, remarks));
            services.get(services.size() - 1).addOrders(orders);
        } else {
            services.get(serviceExists).addOrders(orders);
        }
    }

    public static void editRoomService() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=====================================");
        System.out.println("        Updating Room Service");
        System.out.println("=====================================");

        displayCurrentRoomServices();

        String roomNum = "";
        int roomServiceIndex = -1;
        boolean valid = false;

        // Get room number and verify it has an active room service tab
        while (!valid) {
            System.out.print("Room to update: ");
            roomNum = sc.nextLine();
            roomServiceIndex = getServiceIndex(roomNum);

            if (roomServiceIndex != -1) {
                valid = true;
            }
        }

        System.out.println("=======================================");
        System.out.println("    Updating Room Service for " + roomNum);
        System.out.println("    Enter '.' to retain the information");
        System.out.println("=======================================");

        System.out.println("\nListing Ordered Menu:");
        displayOrders(roomServiceIndex);

        ArrayList<Order> order = services.get(roomServiceIndex).getOrders();

        int choice = 0;
        boolean done = false;

        while (!done) {
            valid = false;

            System.out.println("\n1.Add order\n2.Update order status\n3.Delete order\nOthers numeric value to exit");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Exit Order Update");
                done = true;
                valid = true;
            }

            while (!valid) {
                try {
                    if (choice == 1) {
                        // add order
                        ServiceMenuController.displayMenuItems();

                        System.out.print("Menu ID: ");
                        int itemIndex = Integer.parseInt(sc.nextLine());

                        if (itemIndex < 0 || itemIndex > ServiceMenuController.getMenu().size()) {
                            System.out.println("Item not found");
                            break;
                        }

                        System.out.print("Quantity : ");
                        int quantity = Integer.parseInt(sc.nextLine());

                        order.add(new Order(itemIndex, quantity));

                        valid = true;
                    } else if (choice == 2) {
                        // update status
                        System.out.print("Order ID to update status: ");
                        int updateID = Integer.parseInt(sc.nextLine());

                        System.out.println("Current status for ID[" + updateID + "] - " + order.get(updateID).getStatus());
                        System.out.println("Possible statuses:");
                        for (int i = 0; i < Order.ServiceStatus.values().length; i++) {
                            Order.ServiceStatus status = Order.ServiceStatus.values()[i];
                            System.out.println(i + " - " + status);
                        }

                        System.out.print("Update status to (0/1/2) - ");
                        int decision = Integer.parseInt(sc.nextLine());

                        order.get(updateID).setStatus(Order.ServiceStatus.values()[decision]);
                        System.out.println("NEW status for ID[" + updateID + "] - " + order.get(updateID).getStatus());
                    } else if (choice == 3) {
                        // remove order
                        System.out.print("Order ID to remove: ");
                        int removeID = Integer.parseInt(sc.nextLine());

                        System.out.print("Are you sure you would like to remove - " + ServiceMenuController.getMenu().get(order.get(removeID).getMenuIndex()).getName() + "? (y/n) ");
                        String decision = sc.nextLine();
                        if (decision.equals("n") || decision.equals("N")) {
                            System.out.println("Delete cancelled");
                            return;
                        }

                        order.remove(removeID);
                        valid = true;
                    } else {
                        done = true;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input");
                    valid = false;
                    done = false;
                }
            }
        }

        System.out.println("Room Service Remarks - OLD: " + services.get(roomServiceIndex).getRemarks());
        System.out.print("                     - NEW: ");
        String newRemarks = sc.nextLine();
        if (!newRemarks.equals(".")) {
            services.get(roomServiceIndex).setRemarks(newRemarks);
        }

        System.out.println("\nFinished Updating Room Service for room - " + roomNum + "\n");
    }

    public static void removeRoomService() {
        Scanner sc = new Scanner(System.in);

        System.out.println("==========================");
        System.out.println("Remove Remove Room Service");
        System.out.println("==========================");

        displayCurrentRoomServices();

        String roomNum = "";
        int roomServiceIndex = -1;
        boolean valid = false;

        // Get room number and verify it has an active room service tab
        while (!valid) {
            System.out.print("Room to update: ");
            roomNum = sc.nextLine();
            roomServiceIndex = getServiceIndex(roomNum);

            if (roomServiceIndex != -1) {
                valid = true;
            }
        }

        //Confirm action
        System.out.print("Are you sure you would like to remove all orders for room - " + roomNum + "? (y/n) ");
        String choice = sc.nextLine();
        if (choice.equals("n") || choice.equals("N")) {
            System.out.println("Delete cancelled");
            return;
        }

        //Remove item from menu list
        deleteRoomService(roomServiceIndex);
        System.out.println("Successfully deleted all orders for room - " + roomNum);
    }

    public static void displayServiceStatus() {
        Scanner sc = new Scanner(System.in);

        System.out.println("===========================");
        System.out.println("Current Room Service Status");
        System.out.println("===========================");

        displayCurrentRoomServices();

        String roomNum = "";
        int roomServiceIndex = -1;
        boolean valid = false;

        // Get room number and verify it has an active room service tab
        while (!valid) {
            System.out.print("Room to display status: ");
            roomNum = sc.nextLine();
            roomServiceIndex = getServiceIndex(roomNum);

            if (roomServiceIndex != -1) {
                valid = true;
            }
        }

        System.out.println("========================================");
        System.out.println("  Room Service Orders for Room " + roomNum);
        System.out.println("========================================");

        displayOrders(roomServiceIndex);
    }

    public static void displayCurrentRoomServices() {
        System.out.println("Rooms with current (unpaid) room services: ");
        for (RoomService rs : services) {
            System.out.println("    " + rs.getRoomNo());
        }
    }

    public static void displayOrders(int index) {
        ArrayList<Order> orders = services.get(index).getOrders();
        System.out.println("ID - Name: Quantity @ Price = Total Cost || Status");

        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            ServiceMenuItem item = ServiceMenuController.getMenu().get(o.getMenuIndex());
            double cost = o.getQuantity() * item.getPrice();
            //System.out.println(i + " - " + item.getName() + ": " + o.getQuantity() + " @ $" + item.getPrice() + "/each = $" + cost + " || " + o.getStatus());
            System.out.printf("%s - %s: %s @ %s/each = $%.2f || %s", i, item.getName(), o.getQuantity(), item.getPrice(), cost, o.getStatus());
        }
    }

    public static int getServiceIndex(String roomNum) {
        for (int i = 0; i < services.size(); i++) {
            RoomService rs = services.get(i);
            if (rs.getRoomNo().equals(roomNum)) {
                return i;
            }
        }

        return -1;
    }

    public static double getServiceCost(int index) {
        return services.get(index).getTotalCost();
    }

    public static void deleteRoomService(int index) {
        services.remove(index);
    }

    public static void saveData() {
        try {
            FileWriter FileWriter = new FileWriter(FILE_NAME);
            StringBuilder sb = new StringBuilder();

            for (RoomService r : services) {
                sb.append(r.getRoomNo()).append(",");
                sb.append(r.getRemarks()).append(",");
                for (Order o : r.getOrders()) {
                    sb.append(o.getMenuIndex()).append(",");
                    sb.append(o.getQuantity()).append(",");
                    sb.append(o.getStatus()).append(",");
                }
                sb.append("\n");
            }

            FileWriter.write(sb.toString());
            FileWriter.flush();
            FileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void loadData() {
        services = new ArrayList<>();

        try {
            // Reading the object from a file
            // FileReader fileReader = new FileReader(stored_File_Name);
            File fileReader = new File(FILE_NAME);
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                String dataPerLine = scanner.nextLine();
                String[] arrSplit = dataPerLine.split(",");

                String roomNo = arrSplit[0];
                String remarks = arrSplit[1];

                int numOrders = (arrSplit.length - 2) / 3;
                ArrayList<Order> orders = new ArrayList<>();

                for (int i = 0; i < numOrders; i++) {
                    int menuIndex = Integer.parseInt(arrSplit[2 + i]);
                    int quantity = Integer.parseInt(arrSplit[3 + i]);
                    Order.ServiceStatus status = Order.ServiceStatus.valueOf(arrSplit[4 + i]);

                    orders.add(new Order(menuIndex, quantity));
                    orders.get(orders.size() - 1).setStatus(status);
                }

                services.add(new RoomService(roomNo, remarks));
                services.get(services.size() - 1).addOrders(orders);
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            //  check the conditions after loading the data if room is null
            if (services.isEmpty()) {
                services = new ArrayList<>();
            }
        }
    }
}
