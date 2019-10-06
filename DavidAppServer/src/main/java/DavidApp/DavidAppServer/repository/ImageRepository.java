package DavidApp.DavidAppServer.repository;

import org.springframework.data.repository.*;
import DavidApp.DavidAppServer.model.Images;

public interface ImageRepository extends PagingAndSortingRepository<Images, Long>{
	
	public Images findByName(String name);
	
}
