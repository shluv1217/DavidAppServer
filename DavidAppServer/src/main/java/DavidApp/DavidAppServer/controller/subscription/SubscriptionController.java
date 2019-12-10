package DavidApp.DavidAppServer.controller.subscription;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import DavidApp.DavidAppServer.model.subcription.*;
import DavidApp.DavidAppServer.repository.subscription.*;
import DavidApp.DavidAppServer.service.subscription.SubscriptionService;


@Controller
@EnableAutoConfiguration
public class SubscriptionController{
	
	private SubscriptionService subscriptionService;
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	public SubscriptionController(SubscriptionService subscriptionService, SubscriptionRepository subscriptionRepository){
		this.subscriptionService = subscriptionService;
		this.subscriptionRepository = subscriptionRepository;
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/submit")
	//@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
	@CrossOrigin
    public  Map<String, String> createSubscription(@RequestBody Subscription subscription) throws Exception {
		
       // model.addAttribute("name", name);
	   Logger logger = LoggerFactory.getLogger(SubscriptionController.class);	
	   logger.info("email: " + subscription.getEmail() + ", password: "+ subscription.getPassword());
	   
       Map<String, String> resultMap = new HashMap<>();
       
       
       
       try {
    	   
    	   subscriptionService.createSubscription(subscription);

           resultMap.put("resultcode", "00");
           resultMap.put("resultmessage", "success");

       } catch (Exception e) {
    	   
    	   resultMap.put("resultcode", "01");
           resultMap.put("resultmessage", e.getMessage());

       } 

	
		
       return resultMap;
    }
	
}
