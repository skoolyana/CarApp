package com.freenow.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freenow.controller.mapper.DriverMapper;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.EngineType;
import com.freenow.service.driver.DriverService;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(DriverController.class)
@WithMockUser("user")
//Logic in Controller, Mock MVC is used to verify that

public class DriverControllerTest {

	
	   @Autowired
	   private MockMvc mvc;	
	 
	   @MockBean
	   private DriverService driverService;
	   
	   @MockBean
	    private DriverMapper driverMapper;
	   
  

	 
	 // This object will be magically initialized by the initFields method below.
	    private JacksonTester<DriverDTO> jsonDTO;
	 
		
	    	@Before
		    public void setup() {
		        JacksonTester.initFields(this, new ObjectMapper());
		    }
	 
	 @Test
	 public void getDriverTest() throws Exception{
		 
		 DriverDO driverMocked = generateMockedDriverDO(10l).get();

		  given(driverService.find(10l)).willReturn(driverMocked);
		  
		  // when
	        MockHttpServletResponse response = mvc.perform(
	                get("/v1/drivers/" + 10)
	                        .accept(MediaType.APPLICATION_JSON))
	                .andReturn().getResponse();
	        
	        // then
	        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		 
	 }
	 
	 @Test
	 public void createDriverTest() throws Exception{
		 
		  DriverDO driverMocked = generateMockedDriverDO(10l).get();
		  DriverDTO driverDTO = generateMockedDriverDTO(driverMocked);

		  
		  
		  
		  given(driverService.create(driverMocked)).willReturn(driverMocked);

	        
	        // when
	        MockHttpServletResponse response = mvc.perform(
	                post("/v1/drivers/").contentType(MediaType.APPLICATION_JSON)
	                        .content(jsonDTO.write(driverDTO).getJson()))
	                .andReturn().getResponse();
	        

	        
	        // then
	        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
	        		 
	 }
	 
	 @Test
	 public void deleteDriverTest() throws Exception{

		 // when
	        MockHttpServletResponse response = mvc.perform(
	                delete("/v1/drivers/" + 10)
	                        .accept(MediaType.APPLICATION_JSON))
	                .andReturn().getResponse();        
	        // then
	        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		 
	 }
	 
	 
	 @Test
	 public void updateDriverLocationTest() throws Exception{
		 
		 // when
	        MockHttpServletResponse response = mvc.perform(
	                put("/v1/drivers/"+10).param("longitude", "102.4").param("latitude", "101.3")
	                        .accept(MediaType.APPLICATION_JSON))
	                .andReturn().getResponse();        
	        // then
	        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		 
	
	 }
	 
	 
	//DAO Data
	    private Optional<DriverDO> generateMockedDriverDO(long id)
	    {
	        CarDO car = new CarDO("MADRID-12345", 5, 9, false, EngineType.ELECTRIC, "TESLA");
	        car.setId(10L);
	        
	        DriverDO driver = new DriverDO("sunil", "1982");
	        driver.setId(id);
	        driver.setCar(car);
	        return Optional.of(driver);
	    }
	    
	  //DAO Data
	    private DriverDTO generateMockedDriverDTO(DriverDO mockedDriver)
	    {
	    	 DriverDTO driverDTO = DriverMapper.makeDriverDTO(mockedDriver);
	    	 
	    	 return driverDTO;
	    }
	
}
