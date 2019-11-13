package DavidApp.DavidAppServer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Subscription {
	
	@Id @GeneratedValue
	private Long id;
	private String email;
	private String password;
	
	public Subscription(){
	}
		
	public Subscription(String email, String password){
		this.email = email;
		this.password = password;
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

}
