package busbus;
public interface Booking {
    void BookTicket();
    boolean CancelTicket(int BookingID, int CustomerID);
    void ViewHistory();
}
