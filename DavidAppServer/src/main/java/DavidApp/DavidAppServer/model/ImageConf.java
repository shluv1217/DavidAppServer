package DavidApp.DavidAppServer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ImageConf {
	
	@Id @GeneratedValue
	private Long id;
	private String filename;
	private String type;
	private String score;
	
	public ImageConf(){
    }
	
	
	public ImageConf(String filename, String type, String score){
		this.filename = filename;
		this.type = type;
		this.score = score;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public String getScore() {
		return score;
	}
	
	public String getType() {
		return type;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public void setName(String type) {
		this.type = type;
	}


	public void setScore(String score) {
		this.score = score;
	}

	@Override
    public String toString() {
        return String.format(
                "ImageConf[id=%d, filename = '%s', name='%s',  score='%s']",
                id, filename, type, score);
    }


	
}
