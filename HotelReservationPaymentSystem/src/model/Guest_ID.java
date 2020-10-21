package model;

import java.io.Serializable;

/**
 * Created by Anton
 */
public class Guest_ID implements Serializable {
	
	private String id_type;
	private String num;
	
	public Guest_ID(String id_type, String x) {
		this.id_type = id_type;
		this.num = x;
	}
	
	public String getIDType() {
		return id_type;
	}
	
	public String getNum() {
		return num;
	}
		
	
	public void setID_Type(String id_type)
	{
		this.id_type = id_type;
	}
	
	public void setNum(String x)
	{
		this.num = x;
	}
}
