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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freenow.controller.mapper.CarMapper;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainvalue.EngineType;
import com.freenow.service.driver.CarService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Optional;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
@WithMockUser("user")
public class CarControllerTest {

	
	   @Autowired
	   private MockMvc mvc;	
	 
	   @MockBean
	   private CarService carService;
	   

	   @MockBean
	    private CarMapper carMapper;
	   
	    
		 // This object will be magically initialized by the initFields method below.
	    private JacksonTester<CarDTO> jsonDTO;
	    
	    @Before
	    public void setup() {
	        JacksonTester.initFields(this, new ObjectMapper());
	    }
	   
	    @WithMockUser("user")
	    @Test
		 public void getCarTest() throws Exception{
			 
			 CarDO carMocked = generateMockedCarDO(10l).get();

			  given(carService.find(10l)).willReturn(carMocked);
			  
			  // when
		        MockHttpServletResponse response = mvc.perform(
		                get("/v1/cars/" + 10)
		                        .accept(MediaType.APPLICATION_JSON))
		                .andReturn().getResponse();
		        
		        // then
		        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		        assertThat(response.getContentAsString())
	            .isEqualTo(jsonDTO.write(carMapper.makeCarDTO(carMocked)).getJson());
			 
		 }
	    
	    @WithMockUser("user")
	    @Test
		 public void createCarTest() throws Exception{
			 
	    	 CarDO carMocked = generateMockedCarDO(10l).get();

			  CarDTO carDTO = generateMockedCarDTO(carMocked);
  
			  given(carService.create(carMocked)).willReturn(carMocked);

		        
		        // when
		        MockHttpServletResponse response = mvc.perform(
		                post("/v1/cars/").contentType(MediaType.APPLICATION_JSON)
		                        .content(jsonDTO.write(carDTO).getJson()))
		                .andReturn().getResponse();
		        
		        
		        
		        // then
		        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		        assertThat(response.getContentAsString())
	            .isEqualTo(jsonDTO.write(carMapper.makeCarDTO(carMocked)).getJson());
			 
		 }
	    

	
	    @WithMockUser("user")
	    @Test
		 public void deleteCarTest() throws Exception{

			 // when
		        MockHttpServletResponse response = mvc.perform(
		                delete("/v1/cars/" + 10)
		                        .accept(MediaType.APPLICATION_JSON))
		                .andReturn().getResponse();        
		        // then
		        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
			 
		 }
	    
	    
	    
	    @WithMockUser("user")
		 @Test
		 public void updateCarStatusTest() throws Exception{
			 
			 // when
		        MockHttpServletResponse response = mvc.perform(
		                put("/v1/cars/"+10).param("carStatus", "FREE")
		                        .accept(MediaType.APPLICATION_JSON))
		                .andReturn().getResponse();        
		        // then
		        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
			 
		
		 }
		 
		 
		//DAO Data
		    private Optional<CarDO> generateMockedCarDO(long id)
		    {
		        CarDO car = new CarDO("MADRID-12345", 5, 9, false, EngineType.ELECTRIC, "TESLA");
		        car.setId(id);
		        return Optional.of(car);
		    }
	    
		    
		  //DAO Data
		    private CarDTO generateMockedCarDTO(CarDO mockedCar)
		    {
		    	CarDTO carDTO = CarMapper.makeCarDTO(mockedCar);
		    	 
		    	 return carDTO;
		    }
   
	   
	   
}
