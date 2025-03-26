package busbus;


public class BookingException extends IllegalArgumentException {
    public BookingException(String message) {
        super(message);
    }
    public BookingException(int Total_Seat) throws BookingException{

        if(Total_Seat <= 0 || Total_Seat >40){
            throw new BookingException("Total seat cannot be less than or equal to 0 or more than 40");
        }

    }
    
}
