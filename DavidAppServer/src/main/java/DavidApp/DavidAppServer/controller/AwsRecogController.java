package DavidApp.DavidAppServer.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;



@RestController
@SpringBootApplication
public class AwsRecogController {

  @RequestMapping(value = "/available")
  public String available() throws IOException {
	  
	  
	  AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

	  
    return "Spring in Action";
  }
  

}
