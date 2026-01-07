import java.util.ArrayList;
import java.util.List;
public class User {
private String name;
private String cnic;
private String email;
private String contact;
private String passportNumber;
private String citizenship;
private String visa;
private String role;
private String encrypKey;
private String bankName;
private String bankAcc;
private double salary = 0.0;
private String profImgPath = "titleicon.png";
private String country;
private String city;
private String postCode;
// New DOB field
private String dob;
// New associations
private List<Flight> assignedFlights = new ArrayList<>();
private List<Duty> assignedDuties = new ArrayList<>();
public String getName() {
return name;
}
public void setName(String name) {
this.name = name;
}
public String getCnic() {
return cnic;
}
public void setCnic(String cnic) {
this.cnic = cnic;
}
public String getEmail() {
return email;
}
public void setEmail(String email) {
this.email = email;
}
public String getContact() {
return contact;
}
public void setContact(String contact) {
this.contact = contact;
}
public String getPassportNo() {
return passportNumber;
}
public void setPassportNo(String passportNumber) {
this.passportNumber = passportNumber;
}
public String getCitizenship() {
return citizenship;
}
public void setCitizenship(String citizenship) {
this.citizenship = citizenship;
}
public String getVisa() {
return visa;
}
public void setVisa(String visa) {
this.visa = visa;
}
public String getRole() {
return role;
}
public void setRole(String role) {
this.role = role;
}
public String getencrypKey() {
return encrypKey;
}
public void setencrypKey(String encrypKey) {
this.encrypKey = encrypKey;
}
public String getBankName() {
return bankName;
}
public void setBankName(String bankName) {
this.bankName = bankName;
}
public String getbankAcc() {
return bankAcc;
}
public void setbankAcc(String bankAcc) {
this.bankAcc = bankAcc;
}
public double getSalary() {
return salary;
}
public void setSalary(double salary) {
this.salary = salary;
}
public String getprofImgPath() {
return profImgPath;
}
public void setprofImgPath(String profImgPath) {
this.profImgPath = profImgPath;
}
public String getCountry() {
return country;
}
public void setCountry(String country) {
this.country = country;
}
public String getCity() {
return city;
}
public void setCity(String city) {
this.city = city;
}
public String getpostCode() {
return postCode;
}
public void setpostCode(String postCode) {
this.postCode = postCode;
}
// DOB getter and setter
public String getDob() {
    return dob;
}
public void setDob(String dob) {
    this.dob = dob;
}
public List<Flight> getAssignedFlights() {
return assignedFlights;
}
public List<Duty> getAssignedDuties() {
return assignedDuties;
}
}