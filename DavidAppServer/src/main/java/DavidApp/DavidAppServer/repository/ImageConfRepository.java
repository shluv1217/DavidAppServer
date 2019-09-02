package DavidApp.DavidAppServer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import DavidApp.DavidAppServer.model.Hotel;
import DavidApp.DavidAppServer.model.ImageConf;
import DavidApp.DavidAppServer.model.Text;


public interface ImageConfRepository extends JpaRepository<ImageConf, Long>{
	 List<ImageConf> findByName(String name);
}
