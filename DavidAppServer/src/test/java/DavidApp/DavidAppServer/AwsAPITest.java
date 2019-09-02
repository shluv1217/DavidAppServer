package DavidApp.DavidAppServer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import DavidApp.DavidAppServer.controller.AwsRecogController;
import DavidApp.DavidAppServer.repository.HotelRepository;
import DavidApp.DavidAppServer.repository.ImageConfRepository;


//@RunWith(SpringRunner.class)
//@WebMvcTest(AwsRecogController.class)
//@DataJpaTest
public class AwsAPITest {
	
//    @Autowired
//    private MockMvc mvc;
//    
//    @MockBean
//    private HotelRepository hotelrepository;   
//    
//    @MockBean
//    private ImageConfRepository imagerecogrepository;   
//    
//    @Test
//    public void awsAPITest() throws Exception {
//
//        mvc.perform(get("/awsRecogTest"))
//            .andExpect(status().isOk()).andDo(print());
//    }

   
    
}



