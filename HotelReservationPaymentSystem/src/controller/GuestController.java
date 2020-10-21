package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import model.*;

/**
 * Created by Anton
 */
public class GuestController {

    private final static String FILE_NAME = "data/guests.txt";

    private static ArrayList<Guest> guests = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void guestCheckIn() {

        System.out.println("\n=========================================");
        System.out.println("			   Check In Guest");
        System.out.println("=========================================");
        System.out.print("Guest Passport/Driving Licence Identity Number: ");
        String guestID = sc.nextLine();
        int guestExists = findGuestUsingID(guestID);

        if (guestExists == -1) {
            addGuest();
        } else {
            System.out.println("A guest with that ID is already added");
        }

        int reservationIndex = Reservation.searchReservationByGuestID(guestID);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dateobj = new Date();

        if (reservationIndex != -1) {
            if (Reservation.getReservation(reservationIndex).getDate_Check_In().equals(df.format(dateobj))) {
                //Update Reservation Status
                Reservation.updateReservationStatus(reservationIndex, ReservationModel.reservation_status.CHECKED_IN);

                //Update Room Status
                RoomController.updateRoomSatus(Reservation.getReservation(reservationIndex).getRoom_No(), Room.R_Status.Fixed);

                System.out.println("=========================================");
                System.out.println("		Guest check-in complete!");
                System.out.println("=========================================");
            } else {
                System.out.println("Sorry, Reservation check in date is " + Reservation.getReservation(reservationIndex).getDate_Check_In());
            }
        }
    }

    public static void checkOut() {
        // reservation and payment stuff
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dateobj = new Date();

        System.out.println("\n=========================================");
        System.out.println("		   Checking Out Guest");
        System.out.println("=========================================");


        System.out.print("Room Number: ");
        String roomNum = sc.nextLine();
        int reservationIndex = Reservation.searchReservationByRoomNDate(roomNum, df.format(dateobj));
        int roomServiceIndex = RoomServiceController.getServiceIndex(roomNum);

        if(reservationIndex != -1) {
            // calculate payment for check out
            int paymentIndex = PaymentController.addNewPayment(roomNum,
                    RoomController.getRoomType(roomNum),
                    Reservation.getLengthOfStay(reservationIndex),
                    Reservation.getReservation(reservationIndex).getDate_Check_In(),
                    Reservation.getReservation(reservationIndex).getDate_Check_Out(),
                    guests.get(findGuestUsingID(Reservation.getReservation(reservationIndex).getGuest_ID())).getPaymentDetails());

            //process payment for check out
            PaymentController.pay(paymentIndex);
            PaymentController.generateInvoice(paymentIndex);

            // reset necessary data values upon check out
            //Reservation.getReservation(reservationIndex).updateReservationStatus(reservationIndex, Reservation.reservation_status.CHECKED_OUT);
            RoomController.updateRoomSatus(roomNum, Room.R_Status.Vacant_Free);

            //TODO: remove delete calls below if causing errors
            PaymentController.deletePayment(paymentIndex);
            RoomServiceController.deleteRoomService(roomServiceIndex);
            Reservation.deleteReservation(reservationIndex);
        } else {
            System.out.println("Room Number is not time for book out!");
        }
    }

    public static void addGuest() {
        String name;
        String country;
        String gender;
        String address;
        String identityType;
        String identityNum;
        int contact;
        String payType;
        String nameOnCard;
        int cardNum;
        String billAddress;

        System.out.println("\n=================================================");
        System.out.println("			   Add New Guest. \n" +
                "			     Fill Up The Form :");
        System.out.println("=================================================");

        System.out.print("Name: ");
        name = sc.nextLine();

        System.out.print("Address: ");
        address = sc.nextLine();

        System.out.print("Gender: ");
        gender = sc.nextLine();

        System.out.print("Country: ");
        country = sc.nextLine();

        System.out.print("Identity type: ");
        identityType = sc.nextLine();
        while (!(identityType.equals("passport") || identityType.equals("drivers_license"))) {
            System.out.print("Not a valid ID type");
            System.out.print("Identity type [passport, drivers_license: ");
            identityType = sc.nextLine();
        }

        System.out.print("Identity number: ");
        identityNum = sc.nextLine();

        System.out.print("Contact: ");
        contact = sc.nextInt();

        System.out.println("Payment");
        System.out.print("Payment type [credit, debit, cash]: ");
        payType = sc.nextLine();
        while (!(payType.equals("credit") || payType.equals("debit") || payType.equals("cash"))) {
            System.out.print("Not a valid payment type");
            System.out.print("Payment type [credit, debit, cash]: ");
            payType = sc.nextLine();
        }

        if (payType.equals("credit") || payType.equals("debit")) {
            System.out.print("Name on card: ");
            nameOnCard = sc.nextLine();
            System.out.print("Card number: ");
            cardNum =  Integer.parseInt(sc.nextLine());
            System.out.print("Billing address: ");
            billAddress = sc.nextLine();
        }
        else {
            nameOnCard = "pay cash";
            cardNum = -1;
            billAddress = "pay cash";
        }

        guests.add(new Guest(name, country, gender, address, identityType, identityNum, contact, payType, nameOnCard, cardNum, billAddress));

        System.out.println("\nGuest " + name + " added!\n");
    }

    //Searching for guest
    public static void searchGuest(boolean byName) {
        Integer index;

        if (byName) {
            System.out.println("\n=================================================");
            System.out.println("			Searching Guest Information		");
            System.out.println("====================================================");
            System.out.print("Please Enter the Guest Name you want to search: ");
            String name = sc.nextLine();
            index = findGuest(name);
        } else {
            System.out.println("\n=================================================");
            System.out.println("			Searching Guest Information		");
            System.out.println("====================================================");
            System.out.print("Please Enter the Guest ID you want to search: ");
            String idNum = sc.nextLine();
            index = findGuestUsingID(idNum);
        }

        printGuest(index);
    }

    private static Integer findGuestUsingID(String x) {
        for (int i = 0; i < guests.size(); i++) {
            if (guests.get(i).getIdentityNum().equals(x))
                return i;
        }

        return -1;
    }

    public static Integer findGuest(String name) {
        Integer index = 0;
        Integer match = -1;
        ArrayList<Integer> temp_guests = new ArrayList<Integer>();

        //For each guest in Guests
        for (index = 0; index < guests.size(); index++) {

            if (guests.get(index).getName().equals(name.toLowerCase())) {
                temp_guests.add(index);
                match = index;
            }
        }

        //If no entries found
        if (temp_guests.isEmpty()) {
            System.out.println("No Entry Found!");
            return -1;
        }
        //If only one detail found
        else if (temp_guests.size() == 1) {
            return temp_guests.get(0);
        }
        //If Found Exact Match
        else if (match != -1) {
            return match;
        }
        //If found multiple entries
        else if (temp_guests.size() > 1) {
            System.out.println("Guests Found From Search: ");
            //For each Integer in temp_guests_id
            for (index = 0; index < temp_guests.size(); index++) {
                System.out.println(index + 1 + "	" + guests.get(temp_guests.get(index)).getName());
            }
        }

        return -1;

    }


    // change this cause it is to similar
    public static void updateGuest() {
        Integer index;

        System.out.println("\n=================================================");
        System.out.println("			Updating Guest");
        System.out.println("==================================================");
        System.out.print("Please Enter the Guest Name you want to update: ");
        String name = sc.nextLine();
        index = findGuest(name);
        editGuest(index);
        // change this cause it is to similar
    }

    public static void editGuest(Integer index) {

        System.out.println("\n=================================================");
        System.out.println("			   Edit the guest \n" +
                "			     Fill Up The Form :");
        System.out.println("=================================================");

        System.out.print("Name: ");
        guests.get(index).setName(sc.nextLine());

        System.out.print("Address: ");
        guests.get(index).setAddress(sc.nextLine());

        System.out.print("Gender: ");
        guests.get(index).setGender(sc.nextLine());

        System.out.print("Country");
        guests.get(index).setCountry(sc.nextLine());

        System.out.print("Identity type");
        String idType = sc.nextLine();
        System.out.print("Identity number");
        guests.get(index).setIdentity(idType, sc.nextLine());

        System.out.print("Contact");
        guests.get(index).setContact(sc.nextInt());

        System.out.println("Payment");
        System.out.print("Payment type: ");
        String payType = sc.nextLine();
        System.out.print("Name on card: ");
        String nameOnCard = sc.nextLine();
        System.out.print("Card number: ");
        int cardNum = sc.nextInt();
        System.out.print("Billing address: ");
        String billAddress = sc.nextLine();
        PaymentDetails temp = new PaymentDetails(payType, nameOnCard, cardNum, billAddress);
        guests.get(index).setPaymentDetails(temp);

    }

    private static void printGuest(Integer index) {
        if (index != -1) {
            System.out.println("\n===============================================");
            System.out.println("Name: " + guests.get(index).getName());
            System.out.println("Address: " + guests.get(index).getAddress());
            System.out.println("Gender: " + guests.get(index).getGender());
            System.out.println("Country: " + guests.get(index).getCountry());
            System.out.println("Contact: " + guests.get(index).getContact());
            System.out.println("Identity type: " + guests.get(index).getIdentityType());
            System.out.println("Identity number: " + guests.get(index).getIdentityNum());
            System.out.println("Credit Card: " + guests.get(index).getPaymentDetails());
            System.out.println();
        }
    }

    public static void saveData()
    {
        try {
            FileWriter myWriter = new FileWriter(FILE_NAME);

            for (int i=0; i < guests.size(); i++) {
                myWriter.write(guests.get(i).getName() + "\n");
                myWriter.write(guests.get(i).getCountry() + "\n");
                myWriter.write(guests.get(i).getGender() + "\n");
                myWriter.write(guests.get(i).getAddress() + "\n");
                myWriter.write(guests.get(i).getIdentityType() + "\n");
                myWriter.write(guests.get(i).getIdentityNum() + "\n");
                myWriter.write(guests.get(i).getContact() + "\n");
                myWriter.write(guests.get(i).getPaymentDetails().getPayType() + "\n");
                myWriter.write(guests.get(i).getPaymentDetails().getNameOnCard() + "\n");
                myWriter.write(guests.get(i).getPaymentDetails().getCardNo() + "\n");
                myWriter.write(guests.get(i).getPaymentDetails().getBillAddress() + "\n");
            }

            myWriter.flush();
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void loadData() {
        String name;
        String country;
        String gender;
        String address;
        String idType;
        String idNum;
        int contact;
        String payType;
        String nameOnCard;
        int cardNum;
        String billAddress;
        guests = new ArrayList<Guest>();

        try {
            File file = new File(FILE_NAME);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                name = sc.nextLine();
                country = sc.nextLine();
                gender = sc.nextLine();
                address = sc.nextLine();
                idType = sc.nextLine();
                idNum = sc.nextLine();
                contact = sc.nextInt();
                sc.nextLine();
                payType = sc.nextLine();
                nameOnCard = sc.nextLine();
                cardNum = sc.nextInt();
                sc.nextLine();
                billAddress = sc.nextLine();

                guests.add(new Guest( name, country, gender, address, idType, idNum, contact, payType, nameOnCard, cardNum, billAddress));

            }
        } catch(FileNotFoundException e) {
            System.out.println("file not found: " + e);
        } finally {
            //  check the conditions after loading the data if room is null
            if (guests.isEmpty()) {
                guests = new ArrayList<>();
            }
        }
    }
}
