package DavidApp.DavidAppServer.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import DavidApp.DavidAppServer.model.Hotel;
import DavidApp.DavidAppServer.model.Images;
import DavidApp.DavidAppServer.repository.HotelRepository;
import DavidApp.DavidAppServer.service.ImageService;

@Controller
public class HomeController {

	private static final String BASE_PATH = "/images";
	private static final String FILENAME = "{filename:.+}";
	
    private ImageService imageService;  
    private HotelRepository repository;
    
    @Autowired
	public HomeController(ImageService imageService, HotelRepository repository){
		this.imageService = imageService;
		this.repository = repository;
	}
    
    @RequestMapping(value = "/")
    public String index(Model model, Pageable pageable){
    	final Page<Images> page = imageService.findPage(pageable);
    	model.addAttribute("page", page);
    	return "index";
    }
    
    @RequestMapping(value = "/main")
    public String main(){
    	//final Page<Images> page = imageService.findPage(pageable);
    	//model.addAttribute("page", page);
    	return "main";
    }
    
    
    @RequestMapping(method = RequestMethod.GET, value = BASE_PATH + "/" + FILENAME + "/raw")
    @ResponseBody
    public ResponseEntity<?> oneRawImage(@PathVariable String filename){
    	try {
    		Resource file = imageService.findOneImage(filename);
    		return ResponseEntity.ok()
    				.contentLength(file.contentLength())
    				.contentType(MediaType.IMAGE_JPEG)
    				.body(new InputStreamResource(file.getInputStream()));
    	}catch (IOException e){
    		return ResponseEntity.badRequest()
    				.body("Couldn't find" + filename + "=>" + e.getMessage());
    	}    	
    }
    
    
    @RequestMapping(method = RequestMethod.POST, value = "/images")
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
    public String createFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
    	try {
    		imageService.createImage(file);
    		redirectAttributes.addFlashAttribute("flash message", "Successfully uploaded" + file.getOriginalFilename());
    	}catch (IOException e){
    		redirectAttributes.addFlashAttribute("flash message", "Failed to upload" + file.getOriginalFilename() + "=>" + e.getMessage());
    	}    	
    	return "redirect:/";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/temp")
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
    Collection<Hotel> test(@RequestParam("file") MultipartFile file){
    	  	
    	return repository.findAll().stream().filter(this::isGreat).collect(Collectors.toList());
    }
    
    private boolean isGreat(Hotel hotel) {
        return !hotel.getName().equals("Ashai")&&
                !hotel.getName().equals("PaleAle")&&
                !hotel.getName().equals("Chingtao");
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/posttest")
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
    public String postmethod() {
      return "Spring Boot in Action";
    }
    
    
}








