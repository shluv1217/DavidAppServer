package DavidApp.DavidAppServer.repository;

import org.springframework.data.repository.*;
import DavidApp.DavidAppServer.model.RecogImage;

public interface ImageRecogRepository extends PagingAndSortingRepository<RecogImage, Long>{
	
	
}
