package model;
import java.util.ArrayList;
public class Room {
	public enum R_TYPE{
		/*This enum is used to declare the constants for the types of rooms*/
		Single_OnePerson, Double_TwoPersons, Deluxe, VIP
	}
	public enum R_Status{
		/*This enum is used to declare the status for the types of rooms*/
		Vacant_Free, Fixed, Booked, under_maintainnance
	}
	/*Variable for the room no, its type and status as type and status both are enum and publically 
	 * decclared above.*/
	private String r_no;
	private R_TYPE rm_Type;
	private R_Status rstatus;
	/*ArrayList is used to store dynamically sized collection of elements of rooms*/
	private ArrayList<String> roomdetaills =  new ArrayList<String>();
	
	/*this is the connsttructor of the class which sets the values accordingly*/
	public Room(String r_no, R_TYPE rm_Type, R_Status rstatus)
	{
		this.r_no = r_no;
		this.rm_Type = rm_Type;
		this.rstatus = rstatus;
	}
	/*constructor with arraylist*/
    public Room(String r_no, R_TYPE rm_Type, R_Status rstatus, ArrayList<String> roomdetaills)
    {
        this.r_no = r_no;
        this.rm_Type = rm_Type;
        this.rstatus = rstatus;
        this.roomdetaills = roomdetaills;
    }
	//this is a  class models
	public Room()
	{
	}
	
/*this will add dynamic detail of room*/
	public void add_roomdetaills(String detail)
	{
		roomdetaills.add(detail);
	}

	/*this will remove dynamic detail of room*/

	public void delete_roomdetaills(int index)
	{
		try
		{
			/*this will remove the previous stored value*/

			roomdetaills.remove(index-1);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
/*getters will get roomno, its type and status*/
	public String getRoomNo()
	{
		return this.r_no;
	}
	
	public R_TYPE getRoomType()
	{
		return this.rm_Type;
	}

	public R_Status getRoomStatus()
	{
		return this.rstatus;
	}

	//get the values of the array list.
	public ArrayList<String> getRoomDetail()
	{
		return roomdetaills;
	}
	
	/* these are the setters which will set roomno, its type,  its status and 
	 the value of the array list*/

    public void setRoomNo(String r_no)
    {
        this.r_no = r_no;
    }
	
	public void setRoomStatus(R_Status rstatus)
	{
		this.rstatus = rstatus;
	}
	
	public void setRoomType(R_TYPE rm_Type)
	{
		this.rm_Type = rm_Type;
	}

	public void setRoomDetail(ArrayList<String> roomdetaills)
	{
		this.roomdetaills = roomdetaills;
	}

	
}
