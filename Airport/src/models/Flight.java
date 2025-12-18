

public class Flight {
    private int flightNumber;
    private String origin;
    private String destination;
    private String schedule;
    private String status;
    private String type;

    public Flight(int flightNumber, String origin, String destination, String schedule, String status, String type) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.schedule = schedule;
        this.status = status;
        this.type = type;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }
}