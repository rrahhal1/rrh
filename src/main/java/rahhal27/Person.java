package rahhal27;

import java.io.Serializable;

public class Person implements Serializable {
	//this is the model class
	private static final long serialVersionUID = 1L;
	private  String firstName;
	private  String lastname;
	private String fullname;


	public Person(String fn,String ln,String fullname) {
		this.setFirstName(fn);
		this.setLastname(ln);
		this.setFullname(fullname);
	}

	public Person() {
	
	}
	public  String getFirstName() {
		return firstName;
	}
	public  String getFullName() {
		return fullname;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public  String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	
	
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	}
