package model;

import controller.RoomController;
import utility.DataFunctions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sebas
 */
public class Reservation {

	private final static String FILE_NAME = "data/reservations.txt";

	private static ArrayList<ReservationModel> reservations= new ArrayList<>();

	public static ReservationModel getReservation(int index) {
		return reservations.get(index);
	}
	
	//methods
	//add New Reservation
	
	public void addNewReservation() {
		System.out.println("================================================================");
		System.out.println("Creating NEW Reservation Now!! Please fill in the form carefully.");
		System.out.println("================================================================");
		Scanner sc = new Scanner(System.in); 

		// Enter guest details
		System.out.println("Please enter your Identification Number:");
		String guest_ID = sc.nextLine();
			
		// Enter room details
		System.out.println("Please enter Room Number (eg: 02-01):");
		String room_No = sc.nextLine();
		
		//Enter payment details
		System.out.println("Please enter CreditCard Number:");
		String payment_Card_No = sc.nextLine();
			
		//Enter check in date
		System.out.println("Please enter Check in date (eg: 09/09/20):");
		String date_Check_In = sc.nextLine();
			
		//Enter check out date
		System.out.println("Please enter Check out date (eg: 19/09/20):");
		String date_Check_Out = sc.nextLine();
			
		//Enter number of adults
		System.out.println("Please enter the number of adults:");
		Integer num_Of_Adult = Integer.parseInt(sc.nextLine());
		
		//Enter number of children
		System.out.println("Please enter the number of children:");
		Integer num_Of_Children = Integer.parseInt(sc.nextLine());

		ReservationModel.reservation_status status;

		if (RoomController.getStatus(room_No) == Room.R_Status.Vacant_Free) {
			status = ReservationModel.reservation_status.CONFIRMED;
		} else {
			status = ReservationModel.reservation_status.IN_WAITLIST;
		}
		
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dateobj = new Date();

		reservations.add(new ReservationModel(guest_ID, room_No, date_Check_In, date_Check_Out, df.format(dateobj), num_Of_Adult, num_Of_Children, status));
		printReservationAcknowledgement(guest_ID, room_No, date_Check_In, date_Check_Out, df.format(dateobj), num_Of_Adult, num_Of_Children, status);
		System.out.println("");
	}
	
	public void editReservation() {
		Integer reservation_Index;
		String Guest_ID = null;
		String Room_No = null;
		String date_Check_In;
		String date_Check_Out = null;
		Integer num_Of_Adult;
		Integer num_Of_Children;
		ReservationModel.reservation_status status;
		String[] CmpStrings;
		boolean cmpFlag = false;
		Scanner sc = new Scanner(System.in);

		String datePatternRegex = "[0-3][0-9]/[0-1][0-9]/[1-2][0-9][0-9][0-9]";

		//Reservation(String Guest_ID, String Room_No, String date_Check_In, String date_Check_Out,
		//              Integer num_Of_Adult, Integer num_Of_Children, reservation_Status status)
		System.out.println("\n==================================");
		System.out.println("        Editing model.Reservation");
		System.out.println("==================================");

		////////////
		//Guest ID//
		////////////

		System.out.print("Please enter your Identification Number: ");
		Guest_ID = sc.nextLine();


		reservation_Index = Reservation.searchReservationByGuestID(Guest_ID);

		if(reservation_Index == -1)
		{
			System.out.println("No reservation found!");
			return;
		}


		////////////
		//Room Num//
		////////////
		cmpFlag = false;
		while (!cmpFlag) {
			cmpFlag = false;
			System.out.print("Room Number ("+reservations.get(reservation_Index).getRoom_No()+"): ");

			Room_No = sc.nextLine();
			cmpFlag = true;
		}

			reservations.get(reservation_Index).setRoom_No(Room_No);

		/////////////////
		//Date Check In//
		/////////////////
		do {
			cmpFlag = false;
			System.out.print("Check In Date (DD/MM/YYYY) ("+reservations.get(reservation_Index).getDate_Check_In()+"): ");
			date_Check_In = sc.nextLine();


		}
		while(cmpFlag);

		reservations.get(reservation_Index).setDate_Check_In(date_Check_In);

		//////////////////
		//Date Check Out//
		//////////////////
		do {
			cmpFlag = false;
			System.out.print("Check Out Date (DD/MM/YYYY)("+reservations.get(reservation_Index).getDate_Check_Out()+"): ");
			date_Check_Out = sc.nextLine();

		}
		while(cmpFlag);

		reservations.get(reservation_Index).setDate_Check_Out(date_Check_Out);

		////////////////////
		//Number Of Adults//
		////////////////////
		System.out.print("Number Of Adults("+reservations.get(reservation_Index).getNum_Of_Adult()+"): ");
		num_Of_Adult = Integer.parseInt(sc.nextLine());


		reservations.get(reservation_Index).setNum_Of_Adult(num_Of_Adult);


		//////////////////////
		//Number Of Children//
		//////////////////////
		System.out.print("Number Of Children ("+reservations.get(reservation_Index).getNum_Of_Children()+"): ");
		num_Of_Children = Integer.parseInt(sc.nextLine());

		reservations.get(reservation_Index).setNum_Of_Children(num_Of_Children);

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dateobj = new Date();

		reservations.get(reservation_Index).setReservation_Date(df.format(dateobj));


		printReservationAcknowledgement(Guest_ID,Room_No,date_Check_In,date_Check_Out,reservations.get(reservation_Index).getReservation_Date(),num_Of_Adult,num_Of_Children,reservations.get(reservation_Index).getStatus());
	}
	
	public void checkReservation() {
		int reservation_Index;
		String Guest_ID = null;
		Scanner sc = new Scanner(System.in);

		System.out.println("Hello ! What is your Identification Number?");
		Guest_ID = sc.nextLine();
		reservation_Index = Reservation.searchReservationByGuestID(Guest_ID);
		
		printReservationAcknowledgement(Guest_ID, reservations.get(reservation_Index).getRoom_No(), reservations.get(reservation_Index).getDate_Check_In(),
										reservations.get(reservation_Index).getDate_Check_Out(), reservations.get(reservation_Index).getReservation_Date(),
										reservations.get(reservation_Index).getNum_Of_Adult(), reservations.get(reservation_Index).getNum_Of_Children(),
										reservations.get(reservation_Index).getStatus());

		if (reservation_Index == -1) {
            System.out.println("No reservation found!");
            return;
        }
	}
	
	public static Integer searchReservationByGuestID(String GuestID) {
        Integer reserve_index = -1;
        for (Integer i = 0 ; i <reservations.size(); i++) {
            if(reservations.get(i).getGuest_ID().equals(GuestID))
                reserve_index =  i;
        }

        return reserve_index;
    }

    public static Integer searchReservationByRoomNDate(String Room ,String Date) {
		Integer reserve_index = -1;
		for (Integer i = 0 ; i < reservations.size(); i++) {
			if(reservations.get(i).getRoom_No().equals(Room) && reservations.get(i).getDate_Check_Out().equals(Date)) {
				reserve_index = i;
			}
		}

		return reserve_index;
	}

	public static Integer getLengthOfStay(Integer index) {
		return DataFunctions.daysBetween(reservations.get(index).getDate_Check_In(), reservations.get(index).getDate_Check_Out(),"dd/MM/yyyy");
	}
	
	private static void printReservationAcknowledgement(String Guest_ID, String Room_No, String date_Check_In, String date_Check_Out,
														String reservation_Date, Integer num_Of_Adult, Integer num_Of_Children,
														ReservationModel.reservation_status status) {
		System.out.println("==========================================");
		System.out.println("         Acknowledgement Receipts");
		System.out.println("==========================================");
		System.out.println("Guest Identity Number: " + Guest_ID);
		System.out.println("Date of reservation: " + reservation_Date);
		System.out.println("Date check-in: " + date_Check_In);
		System.out.println("Date check-out: " + date_Check_Out);
		System.out.println("Number of adult: " + num_Of_Adult);
		System.out.println("Number of children: " + num_Of_Children);
		System.out.println("Room Number: " + Room_No);
		System.out.println("Reservation Status: " + status);
	}

	public static double calculatePrice(String checkIn,String checkOut, String roomNum)
	{
		Integer numDays = DataFunctions.daysBetween(checkIn,checkOut,"dd/MM/yyyy");
		Integer numWeekends = DataFunctions.weekendsBetween(checkIn, checkOut,"dd/MM/yyyy");

		double roomCost = 1.0;
		double discRoomCost = 1.0;
		Room.R_TYPE type = RoomController.getRoomType(roomNum);

		switch (type)
		{
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
		}

		return discRoomCost * (numDays - numWeekends) + roomCost * numWeekends;
	}

	public static void updateReservationStatus(int index, ReservationModel.reservation_status status) {
		reservations.get(index).setStatus(status);
	}

	public static void deleteReservation(int index) {
		reservations.remove(index);
	}

	public static void saveData() {
		try {
			FileWriter myWriter = new FileWriter(FILE_NAME);

			for(int i=0; i < reservations.size(); i++) {
				myWriter.write(reservations.get(i).getGuest_ID() + "\n");
				myWriter.write(reservations.get(i).getPayment_Card_No() + "\n");
				myWriter.write(reservations.get(i).getReservation_Date() + "\n");
				myWriter.write(reservations.get(i).getRoom_No() + "\n");
				myWriter.write(reservations.get(i).getDate_Check_In() + "\n");
				myWriter.write(reservations.get(i).getDate_Check_Out() + "\n");
				myWriter.write(reservations.get(i).getNum_Of_Adult() + "\n");
				myWriter.write(reservations.get(i).getNum_Of_Children() + "\n");
				myWriter.write(reservations.get(i).getStatus() + "\n");
			}

			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("There is an error");
			e.printStackTrace();
		}
	}


	public static void loadData() {
		String guest_ID;
		String room_No;
		String payment_Card_No;
		String date_Check_In;
		String date_Check_Out;
		String reservation_Date;
		Integer num_Of_Adult;
		Integer num_Of_Children;
		ReservationModel.reservation_status status;
		reservations = new ArrayList<ReservationModel>();

		try {
			File file = new File(FILE_NAME);
			Scanner sc = new Scanner(file);

			while (sc.hasNext()) {
				guest_ID = sc.nextLine();
				room_No = sc.nextLine();
				payment_Card_No = sc.nextLine();
				date_Check_In = sc.nextLine();
				date_Check_Out = sc.nextLine();
				reservation_Date = sc.nextLine();
				num_Of_Adult = sc.nextInt();
				num_Of_Children = sc.nextInt();
				sc.nextLine();
				status = ReservationModel.reservation_status.valueOf(sc.nextLine());

				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date dateobj = new Date();

				reservations.add(new ReservationModel(guest_ID, room_No, date_Check_In, date_Check_Out, df.format(dateobj), num_Of_Adult, num_Of_Children, status));
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("file not found: " + e);
		}
	}
}
