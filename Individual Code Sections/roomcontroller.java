package controller;
/*declartion of useful libraries*/
import java.util.ArrayList;
import java.util.Scanner;
import facade.Data_Function;
import facade.File_IO;
import model.Room;
import model.Room.R_Status;
import model.Room.R_TYPE;
public class Rooms_Controller {
	/*xls file names where data will be stored*/
	private final static String stored_File_Name = "rooms";
	private static ArrayList<Room> rooms;
	private static Scanner input;
	
	//this is the constructor of the class Room_Controller
	public Rooms_Controller()
	{
		 try
		 {
			 
			 // take input from user
			 input = new Scanner(System.in);
			 // load data function is called
			 loadData();
		 }
		 finally
		 {
			 //  check the conditions after loading the data if room is null
			 if(rooms == null)
				 rooms = new ArrayList<Room>();
			 // you can perform actions like additions
		 }
	}

    public static void tryLoad()
    {
        try
        {
            input = new Scanner(System.in);
            loadData();
        }
        finally
        {
            if(rooms == null)
                rooms = new ArrayList<>();
        }
    }

    //print the data of the statics of the rooms
    public static void printAllRoomStatus()
    {
    	// counting constants declared for single rooms counting
        Integer single_count = 0;
        Integer v_single_count = 0;
        ArrayList<String> vacant_single = new ArrayList<>();
        ArrayList<String> occupied_single = new ArrayList<>();
        ArrayList<String> reserved_single = new ArrayList<>();
        ArrayList<String> undermaint_single = new ArrayList<>();
     // counting constants declared for double rooms counting
        Integer double_count = 0;
        Integer v_double_count = 0;
        ArrayList<String> vacant_double = new ArrayList<>();
        ArrayList<String> occupied_double = new ArrayList<>();
        ArrayList<String> reserved_double = new ArrayList<>();
        ArrayList<String> undermain_double = new ArrayList<>();
     // counting constants declared for deluxe rooms counting
        Integer deluxe_count = 0;
        Integer v_deluxe_count = 0;
        ArrayList<String> vacant_deluxe = new ArrayList<>();
        ArrayList<String> occupied_deluxe = new ArrayList<>();
        ArrayList<String> reserved_deluxe = new ArrayList<>();
        ArrayList<String> undermaint_deluxe = new ArrayList<>();
     // counting constants declared for vip rooms counting
        Integer vip_count = 0;
        Integer v_vip_count = 0;
        ArrayList<String> vacant_vip = new ArrayList<>();
        ArrayList<String> occupied_vip = new ArrayList<>();
        ArrayList<String> reserved_vip = new ArrayList<>();
        ArrayList<String> undermaint_vip = new ArrayList<>();


        System.out.println("\n===============================================");
        System.out.println("                Room Statistic ");
        System.out.println("===============================================");


        /*Calculate room count and room vacant and get room nos
        get number of free fixed and book rooms of this type 
        */

        for (Room r:rooms) {
            switch (r.getRoomType())
            {
                case Single_OnePerson :
                    single_count++;
                    switch (r.getRoomStatus())
                    {
                        case Vacant_Free:
                            v_single_count++;
                            vacant_single.add(r.getRoomNo());
                            break;
                        case Fixed:
                            occupied_single.add(r.getRoomNo());
                            break;
                        case Booked:
                            reserved_single.add(r.getRoomNo());
                            break;
                        case under_maintainnance:
                            undermaint_single.add(r.getRoomNo());
                            break;
                    }
                    break;
                    /*calculation and counting for room type of two  persons
                     get number of free fixed and book rooms of this type 
                     */

                case Double_TwoPersons :
                    double_count++;
                    switch (r.getRoomStatus())
                    {
                        case Vacant_Free:
                            v_double_count++;
                            vacant_double.add(r.getRoomNo());
                            break;
                        case Fixed:
                            occupied_double.add(r.getRoomNo());
                            break;
                        case Booked:
                            reserved_double.add(r.getRoomNo());
                            break;
                        case under_maintainnance:
                            undermain_double.add(r.getRoomNo());
                            break;
                    }
                    break;
                    // break the condtion
                   
                    /*calculation and counting for room type of deluxe
                    get number of free fixed and book rooms of this type 
                    */
                case Deluxe:
                    deluxe_count++;
                    switch (r.getRoomStatus())
                    {
                        case Vacant_Free:
                            v_deluxe_count++;
                            vacant_deluxe.add(r.getRoomNo());
                            break;
                        case Fixed:
                            occupied_deluxe.add(r.getRoomNo());
                            break;
                        case Booked:
                            reserved_deluxe.add(r.getRoomNo());
                            break;
                        case under_maintainnance:
                            undermaint_deluxe.add(r.getRoomNo());
                            break;
                    }
                    break;
                    /*calculation and counting for room type of vips
                    get number of free fixed and book rooms of this type 
                    */

                case VIP:
                    vip_count++;
                    switch (r.getRoomStatus())
                    {
                        case Vacant_Free:
                            v_vip_count++;
                            vacant_vip.add(r.getRoomNo());
                            break;
                        case Fixed:
                            occupied_vip.add(r.getRoomNo());
                            break;
                        case Booked:
                            reserved_vip.add(r.getRoomNo());
                            break;
                        case under_maintainnance:
                            undermaint_vip.add(r.getRoomNo());
                            break;
                    }
                    break;
            }
        }

        	/* counting the single rooms and checking the conditions if count is not 0 then
        	 printing values from as array object*/
        if(single_count != 0)
        {
            System.out.println("Single : " + v_single_count + " out of " + single_count);
            // check vacants room 
            if(vacant_single.size() != 0) {
                System.out.print("Vacant :");
                Data_Function.printAllFromArray(vacant_single);
            }
         // check occupied room 
            if(occupied_single.size() != 0) {
                System.out.print("\nOccupied :");
                Data_Function.printAllFromArray(occupied_single);
            }
         // check reserved room 
            if(reserved_single.size() != 0) {
                System.out.print("\nReserved :");
                Data_Function.printAllFromArray(reserved_single);
            }
         // check undermaintainnace room 
            if(undermaint_single.size() != 0) {
                System.out.print("\nUnder Maintenance :");
                Data_Function.printAllFromArray(undermaint_single);
            }
            System.out.println("\n");

        }


    	/* counting the double rooms and checking the conditions if count is not 0 then
    	 printing values from as array object*/
        
        if(double_count != 0)
        {
            System.out.println("Double : " + v_double_count + " out of " + double_count);
            // check  double vacants room 

            if(vacant_double.size() != 0) {
                System.out.print("Vacant :");
                //printing double
                Data_Function.printAllFromArray(vacant_double);
            }
            // check double occupied room 

            if(occupied_double.size() != 0) {
                System.out.print("\nOccupied :");
                Data_Function.printAllFromArray(occupied_double);
            }
            // check double reserved room 

            if(reserved_double.size() != 0) {
                System.out.print("\nReserved :");
                Data_Function.printAllFromArray(reserved_double);
            }
            
            // check double undermaintainnace room 

            if(undermain_double.size() != 0) {
                System.out.print("\nUnder Maintenance :");
                Data_Function.printAllFromArray(undermain_double);
            }
            System.out.println("\n");
        }

    	/* counting the deluxe rooms and checking the conditions if count is not 0 then
    	 printing values of rooms as object of an array.*/

        if(deluxe_count != 0)
        {
            System.out.println("Deluxe : " + v_deluxe_count + " out of " + deluxe_count);
            // check deluxe vacant room 
            if(vacant_deluxe.size() != 0) {
                System.out.print("Vacant :");
                Data_Function.printAllFromArray(vacant_deluxe);
            }
            // check deluxe occcupied room 
            if(occupied_deluxe.size() != 0) {
                System.out.print("\nOccupied :");
                Data_Function.printAllFromArray(occupied_deluxe);
            }
            // check deluxe reserved room 
            if(reserved_deluxe.size() != 0) {
                System.out.print("\nReserved :");
                Data_Function.printAllFromArray(reserved_deluxe);
            }
            // check deluxe undermaintainance room 
            if(undermaint_deluxe.size() != 0) {
                System.out.print("\nUnder Maintenance :");
                Data_Function.printAllFromArray(undermaint_deluxe);
            }

            System.out.println("\n");
        }
        /* counting the vip rooms and checking the conditions if count is not 0 then
   	 printing values of rooms as object of an array.*/

        if(vip_count != 0)
        {
            System.out.println("VIP Suite : " + v_vip_count + " out of " + vip_count);
            // check vip vacant room 
            if(vacant_vip.size() != 0) {
                System.out.print("Vacant :");
                Data_Function.printAllFromArray(vacant_vip);
            }
         // check vip occupied room 
            if(occupied_vip.size() != 0) {
                System.out.print("\nOccupied :");
                Data_Function.printAllFromArray(occupied_vip);
            }
         // check vip reserved room 
            if(reserved_vip.size() != 0) {
                System.out.print("\nReserved :");
                Data_Function.printAllFromArray(reserved_vip);
            }
         // check vip undermaintainnance room 
            if(undermaint_vip.size() != 0) {
                System.out.print("\nUnder Maintenance :");
                Data_Function.printAllFromArray(undermaint_vip);
            }
            System.out.println("\n");
        }

    }

    //Update the statis of rooms
    public static void updateRoomSatus()
    {
        String[] CmpStrings;
        boolean cmpFlag;
        String Status_String;
        R_Status Status;

        Integer index = findRoom();
        System.out.print("Current Room Status: ");
        // types
        CmpStrings = new String[]{"1","2","3","4"};
        do
        {
            cmpFlag = false;
            System.out.print("\nStatus Type \n1 Vacant\n2 Occupied\n3 Reserved\n4 Under Maintenance\nEnter Your Choice:");
            Status_String =  input.nextLine();
            // if data given in input not find

            if(!Data_Function.stringContainsItems(Status_String,CmpStrings))
            {
                System.out.println(Status_String + " not in the list!");
                cmpFlag = true;
            }
        }while(cmpFlag);// till condition fails assign the status as 
        Status = R_Status.values()[Integer.parseUnsignedInt(Status_String)-1];
        
        
        //getting positon of the room
        rooms.get(index).setRoomStatus(Status);
    }

    public static void updateRoomSatus(String roomNum, R_Status status)
    {
        rooms.get(findRoom(roomNum)).setRoomStatus(status);
    }

    //Adding New Room Detail
    public static void addRoomDetail()
    {
        System.out.println("===============================================");
        System.out.println("      Adding Room Detail for Room ");
        System.out.println("===============================================");

        Integer index = findRoom();
        String room_detail_name;

        System.out.println("");
        System.out.println("Adding Room Detail for Room "+rooms.get(index).getRoomNo());
        System.out.println("");
        System.out.print("Detail Name: ");
        room_detail_name = input.nextLine();
        //input added on below index
        

        rooms.get(index).add_roomdetaills(room_detail_name);
    }

    //Remove Room Detail
    public static void removeRoomDetail()
    {
        String detailName;
        System.out.println("===============================================");
        System.out.println("      Removing Room Detail for Room ");
        System.out.println("===============================================");

        Integer rm_Index = findRoom();

        if(rm_Index != -1) {
            printRoomDetails(rm_Index);
            System.out.print("Detail Name: ");
            detailName = input.nextLine();
            //  deleting index where roo found
            Integer detail_Index = findDetail(rm_Index, detailName);

            if(detail_Index != -1) {
                rooms.get(rm_Index).delete_roomdetaills(detail_Index+1);
                System.out.println("Successfully remove " + detailName + " from " + rooms.get(rm_Index).getRoomNo());
            }
            else {
                System.out.println("No Such Detail!");
            }
        }
        else
        {
            System.out.println("Room Not Found!");
        }
    }

    //Edit Room Detail
    public static void editRoomDetail()
    {
        String detailName;
        String editedDetailName;
        System.out.println("\n===============================================");
        System.out.println("         Edit Room Detail for Room ");
        System.out.println("===============================================");

        Integer rm_Index = findRoom();

        if(rm_Index != -1) {
            printRoomDetails(rm_Index);
            System.out.print("Detail Name: ");
            detailName = input.nextLine();
            // getting input and find it and edit it.
            Integer detail_Index = findDetail(rm_Index, detailName);

            if(detail_Index != -1) {
                System.out.print("New Detail Name: ");
                editedDetailName = input.nextLine();
                rooms.get(rm_Index).getRoomDetail().set(detail_Index,editedDetailName);
                System.out.println("Successfully edit " + detailName +" to " + editedDetailName+" from " + rooms.get(rm_Index).getRoomNo());
            }// if not found then 
            else {
                System.out.println("No Such Detail!");
            }
        }
        else
        {
            System.out.println("Room Not Found!");
        }
    }

    //Print Room Detail
    public static void printRoomDetails()
    {
        System.out.println("\n===============================================");
        System.out.println("         Print Room Detail for Room ");
        System.out.println("===============================================");

        Integer rm_Index = findRoom();

        if(rm_Index != -1) {
            printRoomDetails(rm_Index);

        }
        else
        {
            System.out.println("Room Not Found!");
        }
    }
// status print
    public static Room.R_Status getStatus(String RoomNum)
    {
        Integer index = findRoom(RoomNum);

        return rooms.get(index).getRoomStatus();
    }
//type
    public static Room.R_TYPE getRoomType(String RoomNum)
    {
        Integer index = findRoom(RoomNum);

        return rooms.get(index).getRoomType();
    }
//for room no
    public static Integer findRoom(String RoomNum)
    {
        Integer i;

        //For each Room in Rooms
        for(i = 0; i < rooms.size(); i++)
        {

            if(rooms.get(i).getRoomNo().toLowerCase().equals(RoomNum.toLowerCase()))
            {
                return i;
            }
        }

        System.out.println("No Room "+RoomNum+" FOUND!");
        return -1;

    }

    private static Integer findRoom()
    {
        System.out.print("Enter Room Number: ");
        String RoomNum = input.nextLine();
        Integer i;

        //For each Room in Rooms
        for(i = 0; i < rooms.size(); i++)
        {

            if(rooms.get(i).getRoomNo().toLowerCase().equals(RoomNum.toLowerCase()))
            {
                return i;
            }
        }

        System.out.println("No Room "+RoomNum+" FOUND!");
        return -1;

    }

    private static Integer findDetail(Integer room_Index,String detailName)
    {
        Integer i;

        //For each room detail in room Details
        for(i = 0; i < rooms.get(room_Index).getRoomDetail().size(); i++)
        {

            if(rooms.get(room_Index).getRoomDetail().get(i).toLowerCase().equals(detailName.toLowerCase()))
            {
                return i;
            }
        }

        System.out.println("No Detail "+detailName+" FOUND!");
        return -1;
    }

    private static void printRoomDetails(Integer index)
    {

        System.out.println("\n===============================================");
        System.out.println("Room Detail for Room "+rooms.get(index).getRoomNo()+" , "+rooms.get(index).getRoomType() + " , "+rooms.get(index).getRoomStatus());
        System.out.println("===============================================");
        System.out.println("Detail Name  ");
        System.out.println("-----------  ");

        for (Integer i = 0; i<rooms.get(index).getRoomDetail().size();i++) {

            System.out.format("%-12s\n",rooms.get(index).getRoomDetail().get(i));

        }
    }


    public static void addNewRoom()
    {
        String Room_No;
        String Rm_Type_Str;
        R_TYPE Rm_Type;
        String Rm_Stat_Str;
        R_Status Rm_Stat;
        String rm_Detail_name;
        String rm_Detail_status;
        ArrayList<String> room_Detail = new ArrayList<>();
        String[] CmpStrings;
        boolean cmpFlag;

        //Room(String room_No, room_Type rm_Type, room_Status rm_Stat)

        System.out.println("===============================================");
        System.out.println("Creating New Room. \nPlease Fill Up The Form :");
        System.out.println("===============================================");

        ///////////
        //Room_No//
        ///////////
        System.out.print("Room Number: ");
        Room_No = input.nextLine();

        /////////////////
        //Identity Type//
        /////////////////
        CmpStrings = new String[]{"1","2","3","4"};
        do
        {
            cmpFlag = false;
            System.out.print("\nRoom Type \n1 Single\n2 Double\n3 Deluxe\n4 VIP_Suite\nEnter Your Choice:");
            Rm_Type_Str =  input.nextLine();


            if(!Data_Function.stringContainsItems(Rm_Type_Str,CmpStrings))
            {
                System.out.println(Rm_Type_Str + " not in the list!");
                cmpFlag = true;
            }
        }while(cmpFlag);
        Rm_Type = Room.R_TYPE.values()[Integer.parseUnsignedInt(Rm_Type_Str)-1];

        //Data Dumping for Java garbrage collection to destroy
        CmpStrings = null;

        System.out.println("\nRoom Detail:");
        do
        {
            System.out.print("Room Detail Name (Enter 1 to stop adding) : ");
            rm_Detail_name = input.nextLine();

            if(!rm_Detail_name.equals("1"))
                room_Detail.add(rm_Detail_name);
        }while (!rm_Detail_name.equals("1"));

        System.out.println();
        rooms.add(new Room(Room_No, Rm_Type, Room.R_Status.Vacant_Free, room_Detail));
    }

    public static void deleteRoom()
    {
        Integer index = findRoom();
        try
        {
            rooms.remove(index);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.print("No Room Found!");
        }
    }


	public static void saveData()
	{
		try
		{
			File_IO.writeToXML(stored_File_Name,rooms);

		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public static void loadData()
	{
		try
		{
			rooms = (ArrayList<Room>) File_IO.readFromXML(stored_File_Name);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
}
