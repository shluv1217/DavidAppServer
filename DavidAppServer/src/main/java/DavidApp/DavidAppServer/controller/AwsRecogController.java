package DavidApp.DavidAppServer.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.util.IOUtils;

import DavidApp.DavidAppServer.model.Hotel;
import DavidApp.DavidAppServer.model.ImageConf;
import DavidApp.DavidAppServer.model.*;
import DavidApp.DavidAppServer.repository.HotelRepository;
import DavidApp.DavidAppServer.repository.ImageConfRepository;
import DavidApp.DavidAppServer.service.ImageService;
import DavidApp.DavidAppServer.service.aws.RecogService;


@RestController
@EnableAutoConfiguration
public class AwsRecogController {
	

  private ImageService imageService; 
  private RecogService recogService; 
  private ImageConfRepository imageConfRepository;
	
  
  Logger logger = LoggerFactory.getLogger(AwsRecogController.class);

  public AwsRecogController (ImageService imageService, RecogService recogService, ImageConfRepository imageConfRepository){
	  	this.imageService = imageService;
        this.recogService = recogService;
        this.imageConfRepository = imageConfRepository;
  }
 

  @RequestMapping(method = RequestMethod.POST, value = "/imageProcessing")
  @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
  Collection<ImageConf> checkedOut(@RequestParam("file") MultipartFile file){
	  
	   logger.info("File name : " + file);
	  

		try {
			imageService.createImage(file);
			recogService.recogImage(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
  
	  
		return imageConfRepository.findAll().stream().collect(Collectors.toList());
  }
  
  @RequestMapping(method = RequestMethod.GET, value = "/gridtest")
  @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
  Collection<ImageConf> gridtest(){

		return imageConfRepository.findAll().stream().collect(Collectors.toList());
  }
  
  @RequestMapping(method = RequestMethod.GET, value = "/gridtest1")
  @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
  public JSONObject gridtest1(){
	  

		JSONObject obj =new JSONObject();
		JSONObject obj1 =new JSONObject();
		JSONArray arr =new JSONArray();
		
		obj1.put("labels","Boston");
		arr.add(obj1);
		
		
		obj.put("chartData1", arr);



		return obj;
  }
	

  
//  @RequestMapping(value = "/awsRecogTest")
//  @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
//  //public String awsrecogtest() throws IOException {
//  Collection<ImageConf> awsrecogtest() throws IOException {
//	  
//	  Logger logger = LoggerFactory.getLogger(AwsRecogController.class);
//	  
//	  String photo = "input.jpg";
//      String bucket = "davidbucket1217";
//      
//      AWSCredentials credentials = null;
//      
// 	 
//      try {
//          credentials = new ProfileCredentialsProvider("default").getCredentials();
//      } catch (Exception e) {
//          throw new AmazonClientException(
//                  "Cannot load the credentials from the credential profiles file. " +
//                  "Please make sure that your credentials file is at the correct " +
//                  "location (/Users/davidshin/.aws/credentials), and is in valid format.",
//                  e);
//      }  
//      
//      AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_1)
//    		  .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
//	  
//	  
//	  //AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();
//	  
//	  DetectLabelsRequest request = new DetectLabelsRequest()
//	           .withImage(new Image()
//	           .withS3Object(new S3Object()
//	           .withName(photo).withBucket(bucket)))
//	           .withMaxLabels(10)
//	           .withMinConfidence(75F);
//	  
//	  try {
//	         DetectLabelsResult result = rekognitionClient.detectLabels(request);
//	         List <Label> labels = result.getLabels();
//
//	         System.out.println("Detected labels for " + photo);
//	         for (Label label: labels) {
//	            System.out.println(label.getName() + ": " + label.getConfidence().toString());
//	            
//	            String name = label.getName();
//	            String score = label.getConfidence().toString();
////	            
////	            repository.save(new ImageConf("test1", "test1"));
////	            
////	            repository.findByName(label.getName()).forEach(bauer -> {
////	            	System.out.println(bauer.toString());
////				});
//	            
//	            //Stream.of("Conrad","Hilton","Shilla","Hayatt","Westin","Sangrila").forEach(name-> repository.save(new ImageConf(name)));
//
//	            repository.save(new ImageConf(name, score));
//		        //repository.findAll().forEach(System.out::println);
//	            
//	         }
//	         
//	         //fetch all customers
//        	logger.info("Hotels found with findAll():");
//        	logger.info("-------------------------------");
//        	for (ImageConf imageconf : repository.findAll()) {
//        		logger.info(imageconf.toString());
//        	}
//        	
//        	logger.info("-------------------------------");
//
//	         
//	         
//	      } catch(AmazonRekognitionException e) {
//	         e.printStackTrace();
//	      }

	  
      //return "Spring in Action";
//	  return repository.findAll().stream().collect(Collectors.toList());
//  }
  
  

}
