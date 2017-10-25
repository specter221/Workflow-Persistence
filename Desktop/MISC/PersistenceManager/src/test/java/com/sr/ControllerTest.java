package com.sr;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.sr.domain.PersistenceModel;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersistenceManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ControllerTest {
	String user1;
    @LocalServerPort
    private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    PersistenceModel persistencemodel;
    @Before
    public void setUp() throws Exception {
    	persistencemodel = new PersistenceModel(1, "1,2");
    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
    @After
    public void tearDown() throws Exception {
    }
   
    @Test
    public void testaddWorkflow() throws Exception {  
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PersistenceModel> entity = new HttpEntity<PersistenceModel>(persistencemodel, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/workflow/save"),
                HttpMethod.POST, entity, String.class); 
        assertNotNull(response);
        String actual = response.getBody();
        assertEquals("User Added",actual); 
    }
    
    @Test
    public void testgetWorkflow() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/workflow/get"),
                HttpMethod.GET, entity, String.class);
        assertNotNull(response);
    }
    
    @Test
    public void testupdateWorkflow() throws Exception {
        HttpEntity<PersistenceModel> entity = new HttpEntity<PersistenceModel>(persistencemodel, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/workflow/update"),
                HttpMethod.PUT, entity, String.class);
        assertNotNull(response);
        String actual = response.getBody();
        System.out.println(actual);
        assertEquals("user updated",actual);
    }
    
   @Test
    public void testdelete() throws Exception {
        HttpEntity<PersistenceModel> entity = new HttpEntity<PersistenceModel>(persistencemodel, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/workflow/delete/1"),
                HttpMethod.DELETE, entity, String.class);
        assertNotNull(response);
        String actual = response.getBody();
        System.out.println(actual);
        assertEquals("Deleted succesfully",actual);
    }

}




	    
	    
	    
	    
	    