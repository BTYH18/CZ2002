
package assignemnt;


import java.io.File;
import java.io.IOException
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class GuestController {
	
	private final static String stored_File_Name = "guests";
	private static ArrayList<Guest> guests;
	private static Scanner  sc;
	
	public GuestController() {
		
		
		File tempFile = new File("test.txt");
		if (exists = tempFile.exists()) {
			loadData(tempFile);
		}
//		 try
//		 {
//			 sc = new Scanner(System.in);
//			 loadData();
//		 }
//		 finally
//		 {
//			 if(guests == null) {
//				 guests= new ArrayList<Guest>();
//			 }
//		 }
	}
	
	public static void checkIn() {
		
		System.out.println("\n=========================================");
		System.out.println("			   Check In Guest");
		System.out.println("=========================================");
		System.out.print("Guest Passport/Driving Licence Identity Number: ");
		int guestID = sc.nextInt();
		Integer guestExists = findGuestUsingID(guestID);
		if (guestExists == -1) {
			addGuest();
		}
		else {
			System.out.println("A guest with that ID is already added");
		}
		// add reservation stuff to this
		
	}
	
	public static void checkOut() {
		// reservation and payment stuff?? 
	}
	
	public static void addGuest() {
		
		String name;
		String country;
		String gender;
		String address;
		String identityType;
		int identityNum;
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
		
		System.out.print("Gender [M, W]: ");
		gender = sc.nextLine();
		while (!(gender.equals("M") || gender.equals("W"))) {
			System.out.print("Not a valid gender");
			System.out.print("Gender [M, W]: ");
			gender = sc.nextLine();
		}
		
		System.out.print("Country: ");
		country = sc.nextLine();
		
		System.out.print("Identity type [passport, drivers_license: ");
		identityType = sc.nextLine();
		while (!(identityType.equals("passport") || identityType.equals("drivers_license"))) {
			System.out.print("Not a valid ID type");
			System.out.print("Identity type [passport, drivers_license: ");
			identityType = sc.nextLine();
		}
		
		System.out.print("Identity number: ");
		identityNum = sc.nextInt();
		
		System.out.print("Contact: ");
		contact = sc.nextInt();
		
		
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
		
		
		System.out.print("Billing address: ");
		billAddress = sc.nextLine();
		
		guests.add(new Guest(name, country, gender, address, identityType, identityNum, contact, payType, nameOnCard, cardNum, billAddress));

		System.out.println("\nGuest "+name+" added!\n");
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
			}
			else {
				System.out.println("\n=================================================");
				System.out.println("			Searching Guest Information		");
				System.out.println("====================================================");
				System.out.print("Please Enter the Guest ID you want to search: ");
				int idNum = sc.nextInt();
				index = findGuestUsingID(idNum);
			}

			printGuest(index);
		}
		
		private static Integer findGuestUsingID(int x)
		{
			for(int i = 0; i <guests.size(); i++)
			{
				if (guests.get(i).getIdentityNum() == x)
					return i;
			}

			return  -1;
		}
		
		public static Integer findGuest(String name)
		{
			Integer index = 0;
			Integer match = -1;
			ArrayList<Integer> temp_guests = new ArrayList<Integer>();
			
			//For each guest in Guests
			for(index = 0; index < guests.size(); index++)
			{
				
				if(guests.get(index).getName().equals(name.toLowerCase()))
				{
					temp_guests.add(index);
					match = index;
				}
			}
			
			//If no entries found
			if(temp_guests.isEmpty())
			{
				System.out.println("No Entry Found!");
				return -1;
			}
			//If only one detail found
			else if(temp_guests.size() == 1)
			{	
				return temp_guests.get(0);
			}
			//If Found Exact Match
			else if(match != -1)
			{
				return match;
			}
			//If found multiple entries
			else if(temp_guests.size()>1)
			{
				System.out.println("Guests Found From Search: ");
				//For each Integer in temp_guests_id
				for(index = 0; index < temp_guests.size(); index++)
				{
					System.out.println(index+1+"	"+guests.get(temp_guests.get(index)).getName());
				}
			}
			
			return -1;
			
		}
		
		
		// change this cause it is to similar
		public static void updateGuest()
		{
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
			guests.get(index).setIdentity(idType, sc.nextInt());
			
			System.out.print("Contact");
			guests.get(index).setContact(sc.nextInt());
			
			System.out.println("Payment");
			System.out.print("Payment typ: ");
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

		private static void printGuest(Integer index)
		{
			if(index != -1) {
				System.out.println("\n===============================================");
				System.out.println("Name: " + guests.get(index).getName());
				System.out.println("Address: " + guests.get(index).getAddress());
				System.out.println("Gender: " + guests.get(index).getGender());
				System.out.println("Country: " + guests.get(index).getCountry());
				System.out.println("Contact: " + guests.get(index).getContact());
				System.out.println("Identity type: " + guests.get(index).getIdentityType());
				System.out.println("Identity number: " + guests.get(index).getIdentityNum());
				System.out.println("Credit Card: "+ guests.get(index).getPaymentDetails());
				System.out.println("");
			}
		}
		
		public static void tryLoad()
		{
			try
			{
				sc = new Scanner(System.in);
				loadData();
			}
			finally
			{
				if(guests == null)
					guests= new ArrayList<Guest>();
			}
		}
		
		public static void saveData()
		{
			File saveFile = new File("test.txt");
			for (int i; i < guests.size(); i++) {
				
			}
//			try
//			{
//				FileSave.writeSerializable(stored_File_Name,guests);
//
//			}
//			catch (Exception e)
//			{
//				System.out.println(e.getMessage());
//			}
		}
		
		private static void loadData(Scanner in)
		{
			String name;
			String country;
			String gender;
			String address;
			String idType;
			int idNum;
			int contact;
			String payType;
			String nameOnCard;
			int cardNum;
			String billAddress;
			
			guests = new ArrayList<Guest>();
			
			while (in.hasNext()) {
				name = in.nextLine();
				country = in.nextLine();
				gender = in.nextLine();
				address = in.nextLine();
				idType = in.nextLine();
				idNum = in.nextInt();
				contact = in.nextInt();
				payType = in.nextLine();
				nameOnCard = in.nextLine();
				cardNum = in.nextInt();
				billAddress = in.nextLine();
				guests.add(new Guest( name, country, gender, address, idType, idNum, contact, payType, nameOnCard, cardNum, billAddress))
			}
			in.close();
		}
	
}