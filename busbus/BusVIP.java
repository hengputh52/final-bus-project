package busbus;
public class BusVIP extends BusDetail {
    private String vipServices;

    public BusVIP(int totalSeat, String vipServices) {
        super(totalSeat);
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
