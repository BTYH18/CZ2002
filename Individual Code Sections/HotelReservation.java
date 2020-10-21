package HotelReservation;

import java.util.Scanner;
import java.io.IOException;

public class HotelReservation {
	
	public static void main(String[] args) throws IOException 
	{
		Scanner sc = new Scanner(System.in);
		int sel = 0;
		Reservation reservation = new Reservation();
			
		do
		{
			System.out.println("Welcome to AirD&D Reservation: ");
			System.out.println("Select (1) to Create a new Reservation");
			System.out.println("Select (2) to Edit Reservation");
			System.out.println("Select (3) to Check Reservation Receipt");
			System.out.println("Select (4) to Exit Reservation");
			sel = sc.nextInt();
			
			switch(sel)
			{
				case 1 :
					reservation.addNewReservation();
					break;
				case 2 :
					reservation.editReservation();
					break;
				case 3 :
					reservation.checkReservation();
					break;
			}
			
			
		} while(sel>0 && sel<4);
	}
}
