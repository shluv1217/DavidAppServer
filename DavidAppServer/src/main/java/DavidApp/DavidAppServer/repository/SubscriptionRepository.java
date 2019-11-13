package DavidApp.DavidAppServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import DavidApp.DavidAppServer.model.Subscription;


public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{
	
	
}
