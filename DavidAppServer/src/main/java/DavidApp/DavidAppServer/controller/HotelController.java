package DavidApp.DavidAppServer.controller;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import DavidApp.DavidAppServer.model.Hotel;
import DavidApp.DavidAppServer.repository.HotelRepository;

@RestController
public class HotelController{
    private HotelRepository repository;

    public HotelController (HotelRepository repository){
        this.repository = repository;
    }

    @GetMapping("/good-hotels")
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
    Collection<Hotel> goodhotels(){
    	
	Logger logger = LoggerFactory.getLogger(HotelController.class);
	 
	//fetch all customers
	logger.info("Hotels found with findAll():");
	logger.info("-------------------------------");
	for (Hotel hotel : repository.findAll()) {
		logger.info(hotel.toString());
	}
	
	logger.info("-------------------------------");
	
	

        return repository.findAll().stream().filter(this::isGreat).collect(Collectors.toList());
    }
    
    

    private boolean isGreat(Hotel hotel) {
        return !hotel.getName().equals("Ashai")&&
                !hotel.getName().equals("PaleAle")&&
                !hotel.getName().equals("Chingtao");
    }

}
