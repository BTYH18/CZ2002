package app;

import controller.*;
import model.*;
import utility.*;

import java.util.Scanner;

public class MainController {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // load all data from files upon startup of program
        loadAllData();

        int choice = 0;

        System.out.println("===============================================");
        System.out.println("     Hotel Reservations and Payment System  ");
        System.out.println("                 Hotel AirD&D  ");
        System.out.println("===============================================");

        while (choice != 8) {
            System.out.print("\n1.Guest Function " +
                    "\n2.Reservation Function  " +
                    "\n3.Room Function" +
                    "\n4.Room Services Function" +
                    "\n5.Room Service Menu Item Function" +
                    "\n6.Current Room Status" +
                    "\n7.Room Occupancy Report" +
                    "\n8.Exit" +
                    "\nChoice:");

            try {
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        guestFunction();
                        break;
                    case 2:
                        reservationFunction();
                        break;
                    case 3:
                        roomFunction();
                        break;
                    case 4:
                        roomServiceFunction();
                        break;
                    case 5:
                        roomServiceMenuFunction();
                        break;
                    case 6:
                        RoomController.printAllRoomStatus();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        // save all data to files upon closing program
        saveAllData();
    }

    private static void guestFunction() {
        int choice;

        System.out.println("\n===============================================");
        System.out.println("                Guest Function");
        System.out.println("===============================================");


        System.out.print("1.Guest Check-In " +
                "\n2.Guest Check-Out " +
                "\n3.Add Guest  " +
                "\n4.Update Guest  " +
                "\nOther Numerical Number to Exit" +
                "\nChoice:");

        choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1:
                GuestController.guestCheckIn();
                break;
            case 2:
                GuestController.checkOut();
                break;
            case 3:
                GuestController.addGuest();
                break;
            case 4:
                GuestController.updateGuest();
                break;
            default:
                break;
        }
    }

    private static void reservationFunction() {
        ReservationController.displayMainMenu();
    }

    private static void roomFunction() {
        int choice;

        System.out.println("\n===============================================");
        System.out.println("                Room Function");
        System.out.println("===============================================");


        System.out.print("1.Add Room Detail " +
                "\n2.Update Room Detail  " +
                "\n3.Remove Room Detail " +
                "\n4.Print Room Detail " +
                "\n5.Update Room Status " +
                "\nOther Numerical Number to Exit" +
                "\nChoice:");

        choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            case 1:
                RoomController.addRoomDetail();
                break;
            case 2:
                RoomController.editRoomDetail();
                break;
            case 3:
                RoomController.removeRoomDetail();
                break;
            case 4:
                RoomController.printRoomDetails();
                break;
            case 5:
                RoomController.updateRoomSatus();
                break;
            default:
                break;
        }
    }

    private static void roomServiceFunction() {
        RoomServiceController.displayMainMenu();
    }

    private static void roomServiceMenuFunction() {
        ServiceMenuController.displayMainMenu();
    }

    private static void loadAllData() {
        GuestController.loadData();
        RoomController.loadData();
        Reservation.loadData();
        RoomServiceController.loadData();
        ServiceMenuController.loadData();
        PaymentController.loadData();
    }

    private static void saveAllData() {
        GuestController.saveData();
        RoomController.saveData();
        Reservation.saveData();
        RoomServiceController.saveData();
        ServiceMenuController.saveData();
        PaymentController.saveData();
    }
}
