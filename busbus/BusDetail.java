package busbus;

import java.util.ArrayList;

public class BusDetail {
    public static ArrayList<BusDetail> busDetailList = new ArrayList<>();

    private String source;
    private String destination;
    private String busType;
    private int totalSeat;
    private int availableSeat;
    private String departureTime;
    private String arrivalTime;
    private double fare;
    private static int idCounter = 1;
    private int busID;

    public BusDetail(String source, String destination, String busType, int totalSeat, String departureTime, String arrivalTime, double fare) {
        this.source = source;
        this.destination = destination;
        this.busType = busType;
        this.totalSeat = totalSeat;
        this.availableSeat = totalSeat; // Initially, all seats are available
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.fare = fare;
        this.busID = idCounter++; // Assign a unique ID to each bus
        busDetailList.add(this); // Add the bus to the static list
    }

    // Getters
    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getBusType() {
        return busType;
    }

    public int getTotalSeat() {
        return totalSeat;
    }

    public int getAvailableSeat() {
        return availableSeat;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public double getFare() {
        return fare;
    }

    public int getBusID() {
        return busID;
    }

    // Booking logic
    public boolean bookSeat(int seats) {
        if (seats <= availableSeat) {
            availableSeat -= seats;
            return true;
        }
        return false;
    }
}
