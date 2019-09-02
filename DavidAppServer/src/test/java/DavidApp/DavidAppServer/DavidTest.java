package DavidApp.DavidAppServer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import DavidApp.DavidAppServer.controller.HotelController;
import DavidApp.DavidAppServer.model.ImageConf;
import DavidApp.DavidAppServer.repository.HotelRepository;


@RunWith(SpringRunner.class)
@WebMvcTest(HotelController.class)
public class DavidTest {
	
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private HotelRepository repository;   
    
    @MockBean
    private ImageConf imageconf;   
    
	
    @Test
    public void awsAPITest() throws Exception {

        mvc.perform(get("/good-hotels"))
            .andExpect(status().isOk());
    }

   
    
}



