import java.io.*;

public class User implements Serializable{

	String cust_id;
	String firstName;
	String lastName;
	String email;
	String password;
	String street;
	String city;

	String zipcode;
	String datejoined;
	String usertype;
	String phonenumber;
	
	public User() {}
	
	public User(String cust_id, String firstName, String lastName, String email, String password, String street,
			String city, String zipcode, String datejoined, String usertype,String phonenumber) {
		this.cust_id = cust_id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.street = street;
		this.city = city;
		
		this.zipcode = zipcode;
		this.datejoined = datejoined;
		this.usertype = usertype;
		this.phonenumber = phonenumber;
	}
	
	
	
	//Getters and setters
	public String getUserType() {
		return usertype;
	}
	public void setUserType(String usertype) {
		this.usertype = usertype;
	}
	
	public String getPhoneNumber() {
		return phonenumber;
	}
	public void setPhoneNumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getDateJoined() {
		return datejoined;
	}
	public void setDateJoined(String datejoined) {
		this.datejoined = datejoined;
	}
	

}