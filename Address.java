
public class Address {
	String street;
	String city;
	String state;
	
	public Address(String street, String city, String state){
		this.street = street;
		this.city = city;
		this.state = state;
	}
	
	public String getStreet() {
		return street;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getState() {
		return state;
	}
	
	@Override
	public String toString() {
		return(this.getStreet() + " " + this.getCity() + " " + this.getState());
	}
	
}
