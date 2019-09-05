package DavidApp.DavidAppServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import DavidApp.DavidAppServer.model.Population;
import DavidApp.DavidAppServer.model.RecogImage;

public interface PopulationRepository extends JpaRepository<Population, Long>{
	
	
}
