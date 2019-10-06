package DavidApp.DavidAppServer.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import DavidApp.DavidAppServer.model.Images;
import DavidApp.DavidAppServer.repository.ImageRepository;


@Service
public class ImageService {

	private static String UPLOAD_ROOT = "upload-dir";
	private final ImageRepository repository;
	private final ResourceLoader resourceLoader;

	@Autowired
	public ImageService(ImageRepository repository, ResourceLoader resourceLoader){
		this.repository = repository;
		this.resourceLoader = resourceLoader;
	}
	
	public Page<Images> findPage(Pageable pageable){
		return repository.findAll(pageable);
	}
	
	public Resource findOneImage(String filename){
		return resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + filename);
	}
	
	public void createImage(MultipartFile file) throws IOException {
		if(!file.isEmpty()){
		   Files.copy(file.getInputStream(), Paths.get(UPLOAD_ROOT, file.getOriginalFilename()));
		   repository.save(new Images(file.getOriginalFilename()));
		}
	}
	
	public void deleteImage(String filename)throws IOException {
		final Images byName = repository.findByName(filename);
		repository.delete(byName);
		Files.deleteIfExists(Paths.get(UPLOAD_ROOT,filename));
	}	
	
}
