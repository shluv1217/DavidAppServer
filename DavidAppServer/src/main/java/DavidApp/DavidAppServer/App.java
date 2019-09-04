package DavidApp.DavidAppServer;

import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import DavidApp.DavidAppServer.model.Hotel;
import DavidApp.DavidAppServer.model.ImageConf;
import DavidApp.DavidAppServer.model.Text;
import DavidApp.DavidAppServer.repository.HotelRepository;
import DavidApp.DavidAppServer.repository.ImageConfRepository;
import DavidApp.DavidAppServer.repository.TextRepository;
import DavidApp.DavidAppServer.App;
import DavidApp.DavidAppServer.controller.AwsRecogController;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageRequest;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageResult;
import com.amazonaws.services.comprehend.model.DetectKeyPhrasesRequest;
import com.amazonaws.services.comprehend.model.DetectKeyPhrasesResult;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;




@SpringBootApplication
public class App {

	public static void main(String[] args) {
		
//		String text = "It is raining today in Seattle";
//
//        // Create credentials using a provider chain. For more information, see
//        // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
//        AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();
// 
//        AmazonComprehend comprehendClient =
//            AmazonComprehendClientBuilder.standard()
//                                         .withCredentials(awsCreds)
//                                         .withRegion("us-east-1")
//                                         .build();
//                                         
//        // Call detectKeyPhrases API
//        System.out.println("Calling DetectKeyPhrases");
//        DetectKeyPhrasesRequest detectKeyPhrasesRequest = new DetectKeyPhrasesRequest().withText(text)
//                                                                                       .withLanguageCode("en");
//        DetectKeyPhrasesResult detectKeyPhrasesResult = comprehendClient.detectKeyPhrases(detectKeyPhrasesRequest);
//        detectKeyPhrasesResult.getKeyPhrases().forEach(System.out::println);
//        System.out.println("End of DetectKeyPhrases\n");
		
		SpringApplication.run(App.class, args);
	}
	
	
//	@Component
//	class CompCommandLineRunner implements CommandLineRunner{
//		 @Override
//		    public void run(String... strings)throws Exception{
//			 	String text = "It is raining today in Seattle";
//
//		        // Create credentials using a provider chain. For more information, see
//		        // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
//		        AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();
//
//		        AmazonComprehend comprehendClient =
//		            AmazonComprehendClientBuilder.standard()
//		                                         .withCredentials(awsCreds)
//		                                         .withRegion("us-east-1")
//		                                         .build();
//
//		        // Call detectKeyPhrases API
//		        System.out.println("Calling DetectKeyPhrases");
//		        DetectKeyPhrasesRequest detectKeyPhrasesRequest = new DetectKeyPhrasesRequest().withText(text)
//		                                                                                       .withLanguageCode("en");
//		        DetectKeyPhrasesResult detectKeyPhrasesResult = comprehendClient.detectKeyPhrases(detectKeyPhrasesRequest);
//		        detectKeyPhrasesResult.getKeyPhrases().forEach(System.out::println);
//		        System.out.println("End of DetectKeyPhrases\n");
//
//		    }
//	}
	
	
	@Component
	class HotelCommandLineRunner implements CommandLineRunner{

	    private final HotelRepository repository;

	    public HotelCommandLineRunner(HotelRepository repository){
	        this.repository = repository;
	    }

	    @Override
	    public void run(String... strings)throws Exception{

	        Stream.of("Conrad","Hilton","Shilla","Hayatt","Westin","Sangrila").forEach(name-> repository.save(new Hotel(name)));

	        repository.findAll().forEach(System.out::println);
	    }
	}



	@Component
	class ImageCommandLineRunner implements CommandLineRunner{

		private final ImageConfRepository imagerepository;
		

	    public ImageCommandLineRunner(ImageConfRepository repository){
	        this.imagerepository = repository;
	    }
	    

	    @Override
	    public void run(String... strings)throws Exception{
	    	

    	Logger logger = LoggerFactory.getLogger(AwsRecogController.class);
  	  
  	    String photo = "input.jpg";
        String bucket = "davidbucket1217";
        
        AWSCredentials credentials = null;
        Bucket bucket1 = null;
        
   	 
        try {
            credentials = new ProfileCredentialsProvider("default").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/Users/davidshin/.aws/credentials), and is in valid format.",
                    e);
        }  
        
         AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_1)
      		  .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
	     
         
         //Loading S3 buckets
         final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_1)
         		  .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
         List<Bucket> buckets = s3.listBuckets();
         System.out.println("Your Amazon S3 buckets are:");
         for (Bucket b : buckets) {
             System.out.println("* " + b.getName());
         }
         
         ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
         listObjectsRequest.setBucketName(bucket);
         
         ObjectListing objects = s3.listObjects(listObjectsRequest);
         
         
         
         	do {
             objects = s3.listObjects(listObjectsRequest);
             
             for(S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                 logger.info("keyname : " + objectSummary.getKey());
                 //employeeImageList.add(objectSummary.getKey());
                 
                 photo = objectSummary.getKey();
                 
                 DetectLabelsRequest request = new DetectLabelsRequest()
      	  	           .withImage(new Image()
      	  	           .withS3Object(new S3Object()
      	  	           .withName(photo).withBucket(bucket)))
      	  	           .withMaxLabels(10)
      	  	           .withMinConfidence(75F);
      	  	  
      	  	  try {
      	  	         DetectLabelsResult result = rekognitionClient.detectLabels(request);
      	  	         List <Label> labels = result.getLabels();

      	  	         System.out.println("Detected labels for " + photo);
      	  	         for (Label label: labels) {
      	  	            System.out.println(label.getName() + ": " + label.getConfidence().toString());
      	  	            
      	  	            String name = label.getName();
      	  	            String score = label.getConfidence().toString();

      	  	            imagerepository.save(new ImageConf(name, score));
      	  	         }
      	  	         
      	  	         //fetch all customers
      	  	       imagerepository.findAll().forEach(System.out::println);
//      	          	logger.info("Images found with findAll():");
//      	          	logger.info("-------------------------------");
//      	          	for (ImageConf imageconf : repository.findAll()) {
//      	          		logger.info(imageconf.toString());
//      	          	}
//      	          	
//      	          	logger.info("-------------------------------");       
      	  	      } catch(AmazonRekognitionException e) {
      	  	         e.printStackTrace();
      	  	      }
      	  	  
             }
             
             listObjectsRequest.setMarker(objects.getNextMarker());
         } while(objects.isTruncated());



         

	  	  	  	  
	  	  
	  	  
	    }
	}
	
	
//	@Bean
//	public CommandLineRunner demo(ImageConfRepository repository) {
//		return (args) -> {
//			Logger logger = LoggerFactory.getLogger(AwsRecogController.class);
//		  	  
//	  	    String photo = "input.jpg";
//	        String bucket = "davidbucket1217";
//	        
//	        AWSCredentials credentials = null;
//	        
//	   	 
//	        try {
//	            credentials = new ProfileCredentialsProvider("default").getCredentials();
//	        } catch (Exception e) {
//	            throw new AmazonClientException(
//	                    "Cannot load the credentials from the credential profiles file. " +
//	                    "Please make sure that your credentials file is at the correct " +
//	                    "location (/Users/davidshin/.aws/credentials), and is in valid format.",
//	                    e);
//	        }  
//	        
//	        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_1)
//	      		  .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
//	  	  
//	  	  	  	  
//	  	  DetectLabelsRequest request = new DetectLabelsRequest()
//	  	           .withImage(new Image()
//	  	           .withS3Object(new S3Object()
//	  	           .withName(photo).withBucket(bucket)))
//	  	           .withMaxLabels(10)
//	  	           .withMinConfidence(75F);
//	  	  
//	  	  try {
//	  	         DetectLabelsResult result = rekognitionClient.detectLabels(request);
//	  	         List <Label> labels = result.getLabels();
//
//	  	         System.out.println("Detected labels for " + photo);
//	  	         for (Label label: labels) {
//	  	            System.out.println(label.getName() + ": " + label.getConfidence().toString());
//	  	            
//	  	            String name = label.getName();
//	  	            String score = label.getConfidence().toString();
//
//	  	          repository.save(new ImageConf(name, score));
//	  	         }
//	  	         
//	  	         //fetch all customers
//	  	       repository.findAll().forEach(System.out::println);
////	          	logger.info("Images found with findAll():");
////	          	logger.info("-------------------------------");
////	          	for (ImageConf imageconf : repository.findAll()) {
////	          		logger.info(imageconf.toString());
////	          	}
////	          	
////	          	logger.info("-------------------------------");
//
//	  	         
//	  	         
//	  	      } catch(AmazonRekognitionException e) {
//	  	         e.printStackTrace();
//	  	      }
//		};
//	}
	
	
//	@Component
//	class TextCommandLineRunner implements CommandLineRunner{
//
//	    private final TextRepository repository;
//
//	    public TextCommandLineRunner(TextRepository repository){
//	        this.repository = repository;
//	    }
//
//	    @Override
//	    public void run(String... strings)throws Exception{
//
//	        Stream.of("Text1","Text2","Text3","Text4","Text5","Text6").forEach(text-> repository.save(new Text(text)));
//
//	        repository.findAll().forEach(System.out::println);
//	    }
//	}
	
	
}

