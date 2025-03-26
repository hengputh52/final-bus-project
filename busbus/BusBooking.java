package busbus;


import java.util.ArrayList;
import java.util.Scanner;

public class BusBooking{
    private static int Booking = 0;  
    private int BookingID;
    private int CustomerID; 
    private int PickupStopID;
    private int DropStopID;
    private String TravelDate;
    private String ReturnDate;
    private int Total_Seat;

   static ArrayList<BusBooking> busBooking = new ArrayList<BusBooking>();
  
    public BusBooking(int CustomerID, int BusID, int RouteID, int PickupStopID, int DropStopID, String BookingTime, String TravelDate, String ReturnDate, int Total_Seat){
        this.BookingID = ++Booking;
        this.CustomerID = CustomerID;
        this.PickupStopID = PickupStopID;
        this.DropStopID = DropStopID;
        this.TravelDate = TravelDate;
        this.ReturnDate = ReturnDate;
        this.Total_Seat = Total_Seat;
        busBooking.add(this);
    }


    public BusBooking(int CustomerID, int BusID, int RouteID, int PickupStopID, int DropStopID, String BookingTime, String TravelDate, int Total_Seat) {
        this.BookingID = ++Booking;
        this.CustomerID = CustomerID;
        this.PickupStopID = PickupStopID;
        this.DropStopID = DropStopID;
        this.TravelDate = TravelDate;
        this.Total_Seat = Total_Seat;
        busBooking.add(this);
    
    }
  
    
    protected int getBookingID() {
        return BookingID;
    }

    
    protected int getCustomerID() {
        return CustomerID;
    }



    
    protected int getPickupStopID() {
        return PickupStopID;
    }

    
    protected int getDropStopID() {
        return DropStopID;
    }
    
    protected String getTravelDate() {
        return TravelDate;
    }

    
    protected String getReturnDate() {
        return ReturnDate;
    }


    
    protected int getTotal_Seat() {
        return Total_Seat;
    }

    protected static ArrayList<BusBooking> getBusBooking() {
        return busBooking;
    }
@Override
public void BookTicket() {
    Scanner input = new Scanner(System.in);

    try {
        System.out.print("Enter Customer ID: ");
        int customerID = input.nextInt();
        input.nextLine(); // Consume the newline character

        System.out.print("Enter Bus ID: ");
        int busID = input.nextInt();
        input.nextLine();

        System.out.print("Enter Route ID: ");
        int routeID = input.nextInt();
        input.nextLine();

        System.out.print("Enter Pickup Stop ID: ");
        int pickupStopID = input.nextInt();
        input.nextLine();

        System.out.print("Enter Drop Stop ID: ");
        int dropStopID = input.nextInt();
        input.nextLine();

        System.out.print("Enter Booking Time (e.g., 10:00 AM): ");
        String bookingTime = input.nextLine();

        System.out.print("Enter Travel Date (e.g., 2025-03-25): ");
        String travelDate = input.nextLine();

        System.out.print("Do you want to book a return ticket? (yes/no): ");
        String returnOption = input.nextLine().trim().toLowerCase();

        String returnDate = null;
        if (returnOption.equals("yes")) {
            System.out.print("Enter Return Date (e.g., 2025-03-30): ");
            returnDate = input.nextLine();
        }

        System.out.print("Enter Total Seats to Book: ");
        int totalSeats = input.nextInt();
        input.nextLine();

        // Create a new booking and add it to the list
        BusBooking newBooking = new BusBooking(customerID, busID, routeID, pickupStopID, dropStopID, bookingTime, travelDate, returnDate, totalSeats);
        busBooking.add(newBooking);

        System.out.println("Booking successful! Your Booking ID is: " + newBooking.getBookingID());
    } catch (Exception e) {
        System.out.println("Error: Invalid input. Please try again.");
        input.nextLine(); // Clear the invalid input
    }
}

    @Override
    public boolean CancelTicket(int bookingID, int customerID) {
        // TODO Auto-generated method stub
        for (BusBooking booking : busBooking) {
            if (booking.getBookingID() == bookingID && booking.getCustomerID() == customerID) {
                busBooking.remove(bookingID);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof BusBooking) {
            BusBooking busBooking = (BusBooking) obj;
            if (this.BookingID == busBooking.BookingID && this.CustomerID == busBooking.CustomerID) {
                return true;
            }
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "BusBooking \nBookingID=" + BookingID + ", \nCustomerID=" + CustomerID + ", \nRouteID="
                + ", \nPickupStopID=" + PickupStopID + ", \nDropStopID=" + DropStopID + ", \nBookingTime="
                + "\nTravelDate=" + TravelDate + ", \nReturnDate=" + ReturnDate + ", \nTotal_Seat=" + Total_Seat;
    }

    
    
}

