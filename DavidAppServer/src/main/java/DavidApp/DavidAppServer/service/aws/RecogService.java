package DavidApp.DavidAppServer.service.aws;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import com.amazonaws.util.IOUtils;

import DavidApp.DavidAppServer.model.ImageConf;
import DavidApp.DavidAppServer.repository.ImageConfRepository;

import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class RecogService {
	
	private ImageConfRepository imageConfRepository;
	
	Logger logger = LoggerFactory.getLogger(RecogService.class);
	
	@Autowired
	public RecogService (ImageConfRepository imageConfRepository){
	  	this.imageConfRepository = imageConfRepository;
	}

	
	public void recogImage(MultipartFile file) throws IOException {
		
		  
	      AWSCredentials credentials = null;
		  ByteBuffer imageBytes;
		  
 	 
	      try {
	          credentials = new ProfileCredentialsProvider("default").getCredentials();
	          imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(file.getInputStream()));  
	      } catch (Exception e) {
	          throw new AmazonClientException(
	                  "Cannot load the credentials from the credential profiles file. " +
	                  "Please make sure that your credentials file is at the correct " +
	                  "location (/Users/davidshin/.aws/credentials), and is in valid format.",
	                  e);
	      }  
	      
	      
		  
		  
	      AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_1)
		  .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
	      
	      
	      DetectLabelsRequest request = new DetectLabelsRequest()
	                .withImage(new Image()
	                        .withBytes(imageBytes))
	                .withMaxLabels(10)
	                .withMinConfidence(77F);
	      
	      try {

	            DetectLabelsResult result = rekognitionClient.detectLabels(request);
	            List <Label> labels = result.getLabels();

	
	            for (Label label: labels) {
	            	
	               System.out.println(label.getName() + ": " + label.getConfidence().toString());
	               imageConfRepository.save(new ImageConf(file.getOriginalFilename(),label.getName(), label.getConfidence().toString()));
	            }

	        } catch (AmazonRekognitionException e) {
	            e.printStackTrace();
	        }

	}
	
}
