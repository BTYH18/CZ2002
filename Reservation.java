package HotelReservation;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Reservation {
	
    private static ArrayList<Reservation> reservations= new ArrayList<>();

    public Reservation() 
    {
    	File tempFile = new File("C:\\Users\\sebas\\Desktop\\NTU\\YEAR 2\\SEM 2\\CE2002\\Eclipse Workspace\\HotelReservation\\HotelReservation\\filename.txt");
		if (tempFile.exists()) {
			loadData("C:\\Users\\sebas\\Desktop\\NTU\\YEAR 2\\SEM 2\\CE2002\\Eclipse Workspace\\HotelReservation\\HotelReservation\\filename.txt");
		}
    }
    
    
	
	public enum reservation_status
	{
		CONFIRMED, IN_WAITLIST, CHECKED_IN, EXPIRED, CHECKED_OUT;
	}
	//Attributes
	private String guest_ID;
	private String room_No;
	private String payment_Card_No;
	private String date_Check_In;
	private String date_Check_Out;
	private String reservation_Date;
	private Integer num_Of_Adult;
	private Integer num_Of_Children;
	private reservation_status status;
	
	//Constructor
	public Reservation(String guest_ID, String room_No, String date_Check_In, String date_Check_Out,String reservation_Date,
			   Integer num_Of_Adult, Integer num_Of_Children, reservation_status status)
	{
		this.guest_ID = guest_ID;
		this.room_No = room_No;
		this.date_Check_In = date_Check_In;
		this.date_Check_Out = date_Check_Out;
		this.reservation_Date = reservation_Date;
		this.num_Of_Adult = num_Of_Adult;
		this.num_Of_Children = num_Of_Children;
		this.status = status;
	}
	
	
	//Setter
	
	public void setGuest_ID(String guest_ID)
	{
		this.guest_ID=guest_ID;
	}
	
	public void setRoom_No(String room_No)
	{
		this.room_No=room_No;
	}
	
	public void setPayment_Card_No(String payment_Card_No)
	{
		this.payment_Card_No=payment_Card_No;
	}
	
	public void setDate_Check_In(String date_Check_In)
	{
		this.date_Check_In=date_Check_In;
	}
	
	public void setDate_Check_Out(String date_Check_Out)
	{
		this.date_Check_Out=date_Check_Out;
	}
	
	public void setReservation_Date(String reservation_Date)
	{
		this.reservation_Date=reservation_Date;
	}
	
	public void setNum_Of_Adult(Integer num_Of_Adult)
	{
		this.num_Of_Adult=num_Of_Adult;
	}
	
	public void setNum_Of_Children(Integer num_Of_Children)
	{
		this.num_Of_Children=num_Of_Children;
	}
	
	public void setStatus(reservation_status status)
	{
		this.status=status;
	}
	
	//Getter
	
	public String getGuest_ID()
	{
		return this.guest_ID;
	}
	
	public String getRoom_No()
	{
		return this.room_No;
	}
	
	public String getPayment_Card_No()
	{
		return this.payment_Card_No;
	}
	
	public String getDate_Check_In()
	{
		return this.date_Check_In;
	}
	
	public String getDate_Check_Out()
	{
		return this.date_Check_Out;
	}
	
	public String getReservation_Date()
	{
		return this.reservation_Date;
	}
	
	public Integer getNum_Of_Adult()
	{
		return this.num_Of_Adult;
	}
	
	public Integer getNum_Of_Children()
	{
		return this.num_Of_Children;
	}
	
	public reservation_status getStatus()
	{
		return this.status;
	}
	
	//methods
	//add New Reservation
	
	public void addNewReservation()
	{
		System.out.println("================================================================");
		System.out.println("Creating NEW Reservation Now!! Please fill in the form carefully.");
		System.out.println("================================================================");
		Scanner sc = new Scanner(System.in); 

		// Enter guest details
		System.out.println("Please enter your Identification Number:");
		guest_ID = sc.nextLine();
			
		// Enter room details
		System.out.println("Please enter Room Number (eg: 02-01):");
		room_No = sc.nextLine();
		
		//Enter payment details
		System.out.println("Please enter CreditCard Number:");
		payment_Card_No = sc.nextLine();
			
		//Enter check in date
		System.out.println("Please enter Check in date (eg: 09/09/20):");
		date_Check_In = sc.nextLine();
			
		//Enter check out date
		System.out.println("Please enter Check out date (eg: 19/09/20):");
		date_Check_Out = sc.nextLine();
			
		//Enter number of adults
		System.out.println("Please enter the number of adults:");
		num_Of_Adult = Integer.parseInt(sc.nextLine());
		
		//Enter number of children
		System.out.println("Please enter the number of children:");
		num_Of_Children = Integer.parseInt(sc.nextLine());
		
		status = reservation_status.CONFIRMED;
		
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dateobj = new Date();

		reservations.add(new Reservation(guest_ID, room_No, date_Check_In, date_Check_Out,df.format(dateobj), num_Of_Adult, num_Of_Children, status));
		printReservationAcknowledgement(guest_ID,room_No,date_Check_In,date_Check_Out,df.format(dateobj),num_Of_Adult,num_Of_Children, status);
		System.out.println("");
		
		
	}
	
	
	public void editReservation()
	{
		 Integer reservation_Index;
	        String Guest_ID = null;
	        String Room_No = null;
	        String date_Check_In;
	        String date_Check_Out = null;
	        Integer num_Of_Adult;
	        Integer num_Of_Children;
	        Reservation.reservation_status status;
	        String[] CmpStrings;
	        boolean cmpFlag = false;
	        Scanner sc = new Scanner(System.in);

	        String datePatternRegex = "[0-3][0-9]/[0-1][0-9]/[1-2][0-9][0-9][0-9]";

	        //Reservation(String Guest_ID, String Room_No, String date_Check_In, String date_Check_Out,
	        //              Integer num_Of_Adult, Integer num_Of_Children, reservation_Status status)
	        System.out.println("\n==================================");
	        System.out.println("        Editing Reservation");
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
	            System.out.print("Check Out Date (DD/MM/YYYY)("+reservations.get(reservation_Index).getDate_Check_In()+"): ");
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
	
	public void checkReservation()
	{
		int reservation_Index;
		String Guest_ID = null;
		Scanner sc = new Scanner(System.in);
		System.out.println("Hello ! What is your Identification Number?");
		Guest_ID = sc.nextLine();
		reservation_Index = Reservation.searchReservationByGuestID(guest_ID);
		
		printReservationAcknowledgement(guest_ID,room_No,date_Check_In,date_Check_Out,reservations.get(reservation_Index).getReservation_Date(),num_Of_Adult,num_Of_Children,reservations.get(reservation_Index).getStatus());
		if(reservation_Index == -1)
        {
            System.out.println("No reservation found!");
            return;
        }

		
		
	}
	
	public static Integer searchReservationByGuestID(String GuestID)
    {
        Integer reserve_index = -1;
        for (Integer i = 0 ; i <reservations.size(); i++)
        {
            if(reservations.get(i).getGuest_ID().equals(GuestID))
                reserve_index =  i;
        }


        return reserve_index;


    }
	
	private static void printReservationAcknowledgement(String Guest_ID, String Room_No, String date_Check_In, String date_Check_Out,
            String reservation_Date, Integer num_Of_Adult, Integer num_Of_Children, Reservation.reservation_status status)
		{	
				System.out.println("==========================================");
				System.out.println("         Acknowledgement Receipts");
				System.out.println("==========================================");
				System.out.println("Guest Identity Number: "+Guest_ID);
				System.out.println("Date of reservation: "+reservation_Date);
				System.out.println("Date check-in: "+date_Check_In);
				System.out.println("Date check-out: "+date_Check_Out);
				System.out.println("Number of adult: "+num_Of_Adult);
				System.out.println("Number of children: "+num_Of_Children);
				System.out.println("Room Number: "+Room_No);
				System.out.println("Reservation Status: "+status);

		}
	
	 public static void saveData() {
	        try {
	        	FileWriter myWriter = new FileWriter("filename.txt");
	        	for(int i=0; i < reservations.size(); i++)
	        	{
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

	    
		public static void loadData(String savedFile) {
			String guest_ID;
			String room_No;
			String payment_Card_No;
			String date_Check_In;
			String date_Check_Out;
			String reservation_Date;
			Integer num_Of_Adult;
			Integer num_Of_Children;
			reservation_status status;
			reservations = new ArrayList<Reservation>();
			
			try {
				File file = new File(savedFile);
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
					status = reservation_status.valueOf(sc.nextLine());
					
					
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			        Date dateobj = new Date();
					
					reservations.add(new Reservation(guest_ID, room_No, date_Check_In, date_Check_Out,df.format(dateobj), num_Of_Adult, num_Of_Children, status));
					
				}
			}
			catch(FileNotFoundException e) {
				System.out.println("file not found: " + e);
			}
			}
}
