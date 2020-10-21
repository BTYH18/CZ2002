package model;

import java.io.Serializable;

/**
 * Created by Anton
 */
public class Guest implements Serializable{
	private String name;
	private String country;
	private String gender;
	private String address;
	private Guest_ID identity;
	private int contact;
	private PaymentDetails pay;
	
	public Guest(String name, String country, String gender, String address, String idType, String idNum, int contact, String payType, String nameOnCard, int cardNum, String billAddress) {
		this.name = name;
		this.country = country;
		this.gender = gender;
		this.address = address;
		this.identity = new Guest_ID(idType, idNum);
		this.contact = contact;
		this.pay = new PaymentDetails(payType, nameOnCard,cardNum, billAddress);
	}

	public Guest() {

	}
	
	// Get -------------------------------------------------------------------------------------
	
	public String getName() {
		return this.name;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public Guest_ID getID() {
		return this.identity;
	}
	
	public Guest_ID getGuestID() {
		return this.identity;
	}
	
	public String getIdentityType() {
		return this.identity.getIDType();
	}
	
	public String getIdentityNum() {
		return this.identity.getNum();
	}
	
	public String getGender() {
		return this.gender;
	}
	
	public PaymentDetails getPaymentDetails() {
		return this.pay;
	}
	
	public int getContact() {
		return this.contact;
	}
	
	// Set -------------------------------------------------------------------------------------
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public void setCountry(String newCountry) {
		this.country = newCountry;
	}
	
	public void setAddress(String newAddress) {
		this.address = newAddress;
	}
	
	public void setIdentity(String newIdType, String newIdNum) {
		this.identity.setID_Type(newIdType);
		this.identity.setNum(newIdNum);
	}
	
	public void setIdentity(Guest_ID newID) {
		this.identity = newID;
	}

	
	public void setGender(String newGender) {
		this.gender = newGender;
	}
	
	
	public void setContact(int newContact) {
		this.contact = newContact;
	}
	
	public void setPaymentDetails(PaymentDetails newPay) {
		this.pay = newPay;
	}
	
	public String toString() {
		return name + " " + country + " " + gender + " " + address + " " + identity + " " + contact + " " + pay;
	}
}
