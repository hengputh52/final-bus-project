package busbus;
public class BusVIP extends BusDetail {
    private String vipServices;

    public BusVIP(String source, String destination, String busType, int totalSeat, String departureTime, String arrivalTime, double fare, String vipServices) {
        super(source, destination, busType, totalSeat, departureTime, arrivalTime, fare); // Call the parent class constructor
        this.vipServices = vipServices;
    }

    protected String getVipServices() {
        return vipServices;
    }

    protected void setVipServices(String vipServices) {
        this.vipServices = vipServices;
    }

    @Override
    public String toString() {
        return "BusVIP [BusID=" + getBusID() + ", TotalSeat=" + getTotalSeat() + ", VIP Services=" + vipServices + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BusVIP) {
            return this.getBusID() == ((BusVIP) obj).getBusID();
        }
        return false;
    }
}
