public class Duty {
private String time;
private String location;
private int flightNo;
public Duty(String time, String location, int flightNo) {
this.time = time;
this.location = location;
this.flightNo = flightNo;
}
public String getTime() {
return time;
}
public String getLocation() {
return location;
}
public int getflightNo() {
return flightNo;
}
}