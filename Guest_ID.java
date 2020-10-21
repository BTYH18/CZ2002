
package assignemnt;


import java.io.Serializable;

public class Guest_ID implements Serializable {
	
	private String id_type;
	private int num;
	
	public Guest_ID(String id_type, int x) {
		this.id_type = id_type;
		this.num = x;
	}
	
	public String getIDType() {
		return id_type;
	}
	
	public int getNum() {
		return num;
	}
		
	
	public void setID_Type(String id_type)
	{
		this.id_type = id_type;
	}
	
	public void setNum(int x)
	{
		this.num = x;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}