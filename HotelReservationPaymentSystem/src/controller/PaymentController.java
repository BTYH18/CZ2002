package controller;

import model.*;
import utility.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Gianpaolo
 */
public class PaymentController {

    public static final String FILE_NAME = "data/payments.txt";
    private static ArrayList<Payment> payments = new ArrayList<>();

    public static final double TAX = 1.07;
    public static final double SERVICE_CHARGE = 1.10;

    /**
     * Creates new payment details for given reservation upon check-out of Gues
     * @param roomNum - room number which Guest stayed in
     * @param roomType - room type
     * @param numDayStay - total days of Guest's total stay
     * @param dateIn - date of Guest check-in
     * @param dateOut - date of Guest check-out
     * @param paymentDetail - billing detatils of Guest
     * @return int invoiceNum - index reference of Guest's payment details
     */
    public static int addNewPayment(String roomNum, Room.R_TYPE roomType, int numDayStay, String dateIn, String dateOut, PaymentDetails paymentDetail) {
        //calculate costs
        double roomCost = Reservation.calculatePrice(dateIn, dateOut, roomNum);

        double roomServiceCost = 0;
        int roomServiceIndex = RoomServiceController.getServiceIndex(roomNum);       //room service index reference for ArrayList stored in RoomServicesController
        if (roomServiceIndex != -1) {
            roomServiceCost = RoomServiceController.getServiceCost(roomServiceIndex);
        }

        double costExcTax = roomCost + roomServiceCost;
        double totalCost = costExcTax * TAX * SERVICE_CHARGE;

        int invoiceNum = payments.size();

        payments.add(new Payment(roomNum, roomType, dateIn, dateOut, numDayStay, roomServiceCost, paymentDetail, invoiceNum, costExcTax, totalCost, totalCost));

        return invoiceNum;
    }

    /**
     * Processes Guest's payment for reservation
     *  - if paying by cash asks for paid amount and calculates remaining balance and change if necessary
     *  - if paying by credit card then processes payment using saved card details
     * @param index - reference index of required payment details for Guest
     */
    public static void pay(int index) {
        Scanner sc = new Scanner(System.in);

        if (payments.get(index).getGuestPayDetails().getPayType().equals("cash")) {
            System.out.println("Paying By Cash");
            System.out.println("Please input the Amount Given : $");
            double paidAmount = sc.nextDouble();

            if (paidAmount >= payments.get(index).getTotalCost()) {
                double change = paidAmount - payments.get(index).getTotalCost();
                payments.get(index).setBalance(0);
                System.out.printf("Please return guests change Amount $%.2f\n", change);
            } else {
                payments.get(index).setBalance(payments.get(index).getTotalCost() - paidAmount);
                System.out.printf("Payment not full. Balance of $%.2f\n", payments.get(index).getBalance());
            }
        } else {
            System.out.println("Paying By " + payments.get(index).getGuestPayDetails().getPayType());
            System.out.println(" - Card Name " + payments.get(index).getGuestPayDetails().getNameOnCard());
            System.out.println(" - Card Number " + payments.get(index).getGuestPayDetails().getCardNo());
            payments.get(index).setBalance(0);
        }
    }

    /**
     * Create and display invoice for required Guest's reservation
     * @param index - reference index of required payment details for Guest
     */
    public static void generateInvoice(int index) {
        double roomCost = 0, discRoomCost = 0;
        int roomServiceIndex;

        System.out.println("===================================================");
        System.out.println("                 Payment Receipt");
        System.out.println("===================================================");

        if (payments.get(index).getBalance() != 0) {
            // Withstanding balance existing on payment
            System.out.println("There is still an existing withstanding balance on this reservation - Please complete payment.");
            String balance = String.format("%.2f", payments.get(index).getBalance());
            System.out.println("Total Balance Amount : $" + balance);
        }
        else {
            // Generate Invoice
            System.out.println("Check-In-Date: " + payments.get(index).getDateIn());
            System.out.println("Check-Out-Date: " + payments.get(index).getDateOut());
            System.out.println("Number of Stay: " + payments.get(index).getNumDayStay());
            System.out.println("Room No: " + payments.get(index).getRoomNum());
            System.out.println("Room Type: " + payments.get(index).getRoomType());

            switch (payments.get(index).getRoomType()) {
                case Single_OnePerson:
                    roomCost = 100.0;
                    discRoomCost = 80;
                    break;
                case Double_TwoPersons:
                    roomCost = 180.0;
                    discRoomCost = 145.0;
                    break;
                case Deluxe:
                    roomCost = 280.0;
                    discRoomCost = 220.0;
                    break;
                case VIP:
                    roomCost = 500.0;
                    discRoomCost = 400.0;
                    break;
            }

            System.out.printf("Room Cost Weekend (per day): %.2f\n", roomCost);
            System.out.printf("Room Cost Weekday (per day): %.2f\n", discRoomCost);

            roomServiceIndex = RoomServiceController.getServiceIndex(payments.get(index).getRoomNum());

            if (roomServiceIndex != -1) {
                System.out.println("Room Service Orders:");
                System.out.println("====================");
                RoomServiceController.displayOrders(roomServiceIndex);
                System.out.printf("Service Cost: %.2f\n", RoomServiceController.getServiceCost(roomServiceIndex));
            }

            System.out.printf("Cost (excluding tax): %.2f\n", payments.get(index).getCostExcTax());
            System.out.printf("Total Amount: %.2f\n", payments.get(index).getTotalCost());
        }

        System.out.println("===================================================");
    }

    public static void deletePayment(int index) {
        payments.remove(index);
    }

    public static void saveData() {
        try {
            FileWriter FileWriter = new FileWriter(FILE_NAME);
            StringBuilder sb = new StringBuilder();

            for (Payment p : payments) {
                sb.append(p.getRoomNum()).append(",");
                sb.append(p.getRoomType()).append(",");
                sb.append(p.getDateIn()).append(",");
                sb.append(p.getDateOut()).append(",");
                sb.append(p.getNumDayStay()).append(",");
                sb.append(p.getRoomServiceCost()).append(",");
                sb.append(p.getGuestPayDetails().getPayType()).append(",");
                sb.append(p.getGuestPayDetails().getNameOnCard()).append(",");
                sb.append(p.getGuestPayDetails().getCardNo()).append(",");
                sb.append(p.getGuestPayDetails().getBillAddress()).append(",");
                sb.append(p.getInvoiceNum()).append(",");
                sb.append(p.getCostExcTax()).append(",");
                sb.append(p.getTotalCost()).append(",");
                sb.append(p.getBalance()).append(",");
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
        payments = new ArrayList<>();

        try {
            // Reading the object from a file
            // FileReader fileReader = new FileReader(stored_File_Name);
            File fileReader = new File(FILE_NAME);
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                String dataPerLine = scanner.nextLine();
                String[] arrSplit = dataPerLine.split(",");

                String roomNum = arrSplit[0];
                Room.R_TYPE roomType = Room.R_TYPE.valueOf(arrSplit[1]);
                String dateIn = arrSplit[2];
                String dateOut = arrSplit[3];
                int numDayStay = Integer.parseInt(arrSplit[4]);
                double roomServiceCost = Double.parseDouble(arrSplit[5]);

                String payType = arrSplit[6];
                String nameOnCard = arrSplit[7];
                int cardNum = Integer.parseInt(arrSplit[8]);
                String billAddress = arrSplit[9];
                PaymentDetails pd = new PaymentDetails(payType, nameOnCard, cardNum, billAddress);

                int invoiceNum = Integer.parseInt(arrSplit[10]);
                double costExcTax = Double.parseDouble(arrSplit[11]);
                double totalCost = Double.parseDouble(arrSplit[12]);
                double balance = Double.parseDouble(arrSplit[13]);

                payments.add(new Payment(roomNum, roomType, dateIn, dateOut, numDayStay, roomServiceCost, pd, invoiceNum, costExcTax, totalCost, balance));
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            //  check the conditions after loading the data if room is null
            if (payments.isEmpty()) {
                payments = new ArrayList<Payment>();
            }
        }
    }
}
