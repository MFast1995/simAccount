package simpleAccount.model;
import java.util.*;

//userAccnt is the user class that represents eachs persons account

public class userAccnt implements Comparable<userAccnt>{
	private String firstName;
	private String lastName;
	private double amount;
	private int id;
	
	public userAccnt(String firstName, String lastName, int id, float amount){
		this.firstName = firstName;
		this.lastName = lastName;
		this.amount = amount;
		this.id = id;
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public double getAmountEuro(){
		return	(amount * .92);
	}
	
	public double getAmountYuan(){
		return (amount * 6.23);
	}
	
	public String toString(){
		//return "User id: " + id + ", first= " + firstName + ", last= " + lastName + ", amount= " + amount;
		return firstName + "		" + lastName + "		" + id +"		"  + amount ;
	}
	
	@Override
	public int compareTo(userAccnt compareUser){
		int compareID = ((userAccnt)compareUser).getId();
		return this.id-compareID;
	}
	
	//combobox string
	public String toCBString(){
		//return "User id: " + id + ", first= " + firstName + ", last= " + lastName + ", amount= " + amount;
		return firstName + " " + lastName + " " + id;
	}
	
}
