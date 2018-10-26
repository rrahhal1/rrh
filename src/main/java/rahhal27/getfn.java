package rahhal27;

import java.io.Serializable;

import org.apache.spark.api.java.function.Function;

import com.google.gson.Gson;

public class getfn implements Function<String, String>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String call(String name) throws Exception {
		Gson gson = new Gson();
		Person person = new Gson().fromJson(name, Person.class);
	

String firstName=person.getFirstName();
String lastName =person.getLastname();
String fullname=person.getFirstName()+" "+person.getLastname();
Person pers=new Person(firstName,lastName,fullname);
String jsonInString = gson.toJson(pers);

		System.out.println(jsonInString);
		return jsonInString;
	}
}
