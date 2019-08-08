package DavidApp.DavidAppServer.controller;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DavidApp.DavidAppServer.model.Hotel;
import DavidApp.DavidAppServer.repository.HotelRepository;
import DavidApp.DavidAppServer.model.Greeting;

@RestController
public class HotelController{
    private HotelRepository repository;

    public HotelController (HotelRepository repository){
        this.repository = repository;
    }

    @GetMapping("/good-hotels")
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000", "http://localhost:8080"})
    Collection<Hotel> goodhotels(){
    	
    	 Logger logger = LoggerFactory.getLogger("chapters.introduction.HelloWorld1");
    	 
	     logger.trace("Hello world.");
	     logger.debug("Hello world."); 
	     logger.info("Hello world.");
	     logger.warn("Hello world.");
	     logger.error("Hello world.");


        return repository.findAll().stream().filter(this::isGreat).collect(Collectors.toList());
    }

    private boolean isGreat(Hotel hotel) {
        return !hotel.getName().equals("Ashai")&&
                !hotel.getName().equals("PaleAle")&&
                !hotel.getName().equals("Chingtao");
    }

}
