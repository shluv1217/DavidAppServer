package DavidApp.DavidAppServer.repository.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import DavidApp.DavidAppServer.model.subcription.*;


public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{
	
	
}
