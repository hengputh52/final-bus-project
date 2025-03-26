package busbus;

public class CheckStringEmpty  extends IllegalArgumentException {
    public CheckStringEmpty(String message) {
        super(message);
    }
    public CheckStringEmpty(String BookingTime, String TravelDate, String ReturnDate) throws CheckStringEmpty{
        if(BookingTime.isEmpty() || TravelDate.isEmpty() || ReturnDate.isEmpty()){
            throw new CheckStringEmpty("Booking Time, Travel Date and Return Date cannot be empty");
        }
    }


   

    public CheckStringEmpty(Integer customerID, Integer busID, Integer RouteID, Integer PickupStopID, Integer DropStopID) throws CheckStringEmpty{
        if(customerID == null || busID == null || RouteID == null || PickupStopID == null || DropStopID == null)
        {
            throw new CheckStringEmpty("these cannot be empty");
        }
    }

   public CheckStringEmpty(int[] input)
   {
        for(int Int : input)
        {
            if(Int  == 0)
            {
                throw new CheckStringEmpty("Input is cannot be empty");
            }
        }
   }
    
    public CheckStringEmpty(String[] inpuStrings) {
        for(String str: inpuStrings){
            if(str.isEmpty()){
                throw new CheckStringEmpty("Booking Time, Travel Date and Return Date cannot be empty");
            }
        }
    }

}
