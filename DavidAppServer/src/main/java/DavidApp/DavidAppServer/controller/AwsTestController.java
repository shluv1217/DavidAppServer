package DavidApp.DavidAppServer.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

@RestController
@SpringBootApplication
public class AwsTestController {

  @RequestMapping(value = "/available")
  public String available() {
	  
	  
  AWSCredentials credentials = null;
     try {
         credentials = new ProfileCredentialsProvider("default").getCredentials();
     } catch (Exception e) {
         throw new AmazonClientException(
                 "Cannot load the credentials from the credential profiles file. " +
                 "Please make sure that your credentials file is at the correct " +
                 "location (/Users/davidshin/.aws/credentials), and is in valid format.",
                 e);
     }  
	  
    return "Spring in Action";
  }

}
