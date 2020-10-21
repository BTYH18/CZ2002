
package assignemnt;

import java.io.Serializable;

public class PaymentDetails implements Serializable {

	private String payType;
	private String nameOnCard;
	private int cardNum;
	private String billAddress;
	
	
	public PaymentDetails(String payType, String nameOnCard, int cardNum, String billAddress) {
		
		this.payType = payType;
		this.nameOnCard = nameOnCard;
		this.cardNum = cardNum;
		this.billAddress = billAddress;

	}
	
	public String getPayType()
	{
		return this.payType;
	}
	
	public String getNameOnCard()
	{
		return this.nameOnCard;
	}
	
	public int getCardNo()
	{
		return this.cardNum;
	}

	public String getBillAddress()
	{
		return this.billAddress;
	}
	
	
	
	//Setter
	public void setPayType(String pay_Type)
	{
		this.payType = payType;
	}
	
	public void setCardName(String nameOnCard)
	{
		this.nameOnCard = nameOnCard;
	}
	
	public void setCardNum(int cardNum)
	{
		this.cardNum = cardNum;
	}

	public void setBillAddress(String billAddress)
	{
		this.billAddress = billAddress;
	}
	
	
}