package DavidApp.DavidAppServer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import DavidApp.DavidAppServer.service.SampleService;

@RestController
public class SampleController {

    @Autowired
    private SampleService sampleService;

    @GetMapping("/hello")
    public String hello() {
        return "hello " + sampleService.getName();
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/posttest")
    //@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000", "http://localhost:8080"})
	@CrossOrigin
    public String posttest() {
        return "success ";
    }
}
