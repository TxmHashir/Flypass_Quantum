

public class Duty {
    private String time;
    private String location;
    private int flightNumber;

    public Duty(String time, String location, int flightNumber) {
        this.time = time;
        this.location = location;
        this.flightNumber = flightNumber;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public int getFlightNumber() {
        return flightNumber;
    }
}