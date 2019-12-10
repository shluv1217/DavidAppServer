package DavidApp.DavidAppServer.service.subscription;


import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import DavidApp.DavidAppServer.model.subcription.*;
import DavidApp.DavidAppServer.repository.subscription.*;


@Service
public class SubscriptionService {

	private final SubscriptionRepository repository;

	@Autowired
	public SubscriptionService(SubscriptionRepository repository){
		this.repository = repository;
	}
	
	public void createSubscription(Subscription subscription) throws IOException {
		   
		   repository.save(new Subscription(subscription.getEmail(), subscription.getPassword()));
	}
	
}
