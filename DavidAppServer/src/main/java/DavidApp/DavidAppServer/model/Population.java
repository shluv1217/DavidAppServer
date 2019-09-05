package DavidApp.DavidAppServer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Population {
	
	@Id @GeneratedValue
	private Long id;
	private String city;
	private String number;
	
	public Population(){
    }
	
	
	public Population(String city, String number){
		this.city = city;
		this.number = number;
	}
	
	

	@Override
    public String toString() {
        return String.format(
                "Population[id=%d, city='%s',  number='%s']",
                id, city, number);
    }


	
}
