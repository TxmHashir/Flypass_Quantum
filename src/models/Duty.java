public class Duty {
private int id;
private String time;
private String location;
private int flightNo;
public Duty(int id, String time, String location, int flightNo) {
this.id = id;
this.time = time;
this.location = location;
this.flightNo = flightNo;
}
public int getId() {
return id;
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