public class Flight {
    private int flightNo;
    private String origin;
    private String dest;
    private String schedule;
    private String status;
    private String type;
    private double price;

    public Flight(int flightNo, String origin, String dest, String schedule, String status, String type, double price) {
        this.flightNo = flightNo;
        this.origin = origin;
        this.dest = dest;
        this.schedule = schedule;
        this.status = status;
        this.type = type;
        this.price = price;
    }

    public double getPrice() {  
        return price;
    }
    
    public void setPrice(double price) {  
        this.price = price;
    }

    public int getflightNo() {
        return flightNo;
    }

    public String getOrigin() {
        return origin;
    }

    public String getdest() {
        return dest;
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

    public void setflightNo(int flightNo) {
        this.flightNo = flightNo;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setdest(String dest) {
        this.dest = dest;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }
}