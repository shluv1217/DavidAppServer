package DavidApp.DavidAppServer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ImageConf {
	
	@Id @GeneratedValue
	private Long id;
	private String name;
	private String score;
	
	
	public ImageConf(String name, String score){
		this.name = name;
		this.score = score;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getScore() {
		return score;
	}
	
	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public void setName(String name) {
		this.name = name;
	}


	public void setScore(String score) {
		this.score = score;
	}

	@Override
    public String toString() {
        return String.format(
                "ImageConf[id=%d, name='%s',  score='%s']",
                id, name, score);
    }


	
}
