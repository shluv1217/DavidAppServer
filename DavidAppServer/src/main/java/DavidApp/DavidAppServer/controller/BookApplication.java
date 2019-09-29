package DavidApp.DavidAppServer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@SpringBootApplication
public class BookApplication {
	
  Logger logger = LoggerFactory.getLogger(BookApplication.class);

  //@RequestMapping(value = "/available")
  public String available() {
    return "Spring in Action";
  }
  
//  @RequestMapping(method = RequestMethod.POST, value = "/imageProcessing")
//  @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
//  public String checkedOut(@RequestParam("file") MultipartFile file){
//	  
//	  logger.info("File name : " + file);
//	  
//    return "Spring Boot in Action";
//  }




//  public static void main(String[] args) {
//    SpringApplication.run(BookApplication.class, args);
//  }
}
