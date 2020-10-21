package model;

public class ReservationModel {

    public enum reservation_status {
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
    public ReservationModel(String guest_ID, String room_No, String date_Check_In, String date_Check_Out,String reservation_Date,
                       Integer num_Of_Adult, Integer num_Of_Children, reservation_status status) {
        this.guest_ID = guest_ID;
        this.room_No = room_No;
        this.date_Check_In = date_Check_In;
        this.date_Check_Out = date_Check_Out;
        this.reservation_Date = reservation_Date;
        this.num_Of_Adult = num_Of_Adult;
        this.num_Of_Children = num_Of_Children;
        this.status = status;
    }

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

}
