package com.freenow.service;


import org.springframework.dao.DataIntegrityViolationException;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.CarStatus;
import com.freenow.domainvalue.EngineType;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.driver.DefaultDriverService;
import com.freenow.service.driver.DriverService;

import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;



public class DriverServiceTest {
	
	private static Long driverIdExisting;
    private static Long driverIdNonExisting;
    private static Long driverIdCreated;
    private static String username;
    private static String password;
    private static Long carId;
    private static long carIdInUse;
    
    

    private DriverService driverService;
    
   
	
	 @Mock
	 DriverRepository driverRepository;
	 
	 @Mock
	 CarRepository carRepository;
	
	 
	 @BeforeClass
     public static void init()
     {
        driverIdExisting = 10l;
        driverIdNonExisting = 10000l;
        driverIdCreated = 11l;
        carId = 40l;
        username = "sunil";
        password = "freenow";
        carIdInUse = 41l;
     }
	 
	
	 @Before
	 public void setUp() {
		 
		  // With this call to initMocks we tell Mockito to process the annotations
	        MockitoAnnotations.initMocks(this);	      
	        
	        driverService = new DefaultDriverService(driverRepository,carRepository);
	       
	        given(driverRepository.findByIdAndDeleted(driverIdExisting, false)).willReturn(generateMockedDriverDO(driverIdExisting, OnlineStatus.OFFLINE, carId));
	 }
	 
	
	 
	    @Test
	    public void whenFindDriverByExistingId_thenCorrect() throws EntityNotFoundException
	    {

	    	DriverDO driverDO = driverService.find(driverIdExisting);
	    	
	    	 // then
	        assertThat(driverDO.getId()).isEqualTo(driverIdExisting);
	        assertThat(driverDO.getUsername()).isEqualTo(username);
	        assertThat(driverDO.getPassword()).isEqualTo(password);
	        
	    }
	    

	    @Test(expected = EntityNotFoundException.class)
	    public void whenFindDriverByNonExistingId_thenExcption() throws EntityNotFoundException
	    {

	        driverService.find(driverIdNonExisting);

	    }
	    
	    
	    @Test
	    public void whenCreateDriver_thenCorrectCreatedId() throws ConstraintsViolationException
	    {
	    	createSaveDO();

	        DriverDO driver = new DriverDO(username, password);
	        DriverDO driverCreated = driverService.create(driver);
	        
	        assertThat(driverCreated.getId()).isEqualTo(driverIdCreated);
	        	
	       
	    }
	    
	  
	    @Test(expected = ConstraintsViolationException.class)
	    public void whenCreateDriver_givenNullUsername_thenException() throws ConstraintsViolationException
	    {
	        setUpSaveException();

	        DriverDO driver = new DriverDO(null, password);
	        driverService.create(driver);

	    }
	    
	    
	    @Test
	    public void whenDeleteDriverByExistingId_thenCorrect() throws EntityNotFoundException
	    {
	        driverService.delete(driverIdExisting);
	        driverRepository.findByIdAndDeleted(driverIdExisting, true);

	    }
	    
	    
	    
	    @Test(expected = EntityNotFoundException.class)
	    public void whenDeleteDriverByNonExistingId_thenException() throws EntityNotFoundException
	    {
	        driverService.delete(driverIdNonExisting);
	    }
	    
	    
	    
	    // tests to select car
	    @Test(expected = EntityNotFoundException.class)
	    public void givenCarNotExists_WhenSelectCar_thenException() throws EntityNotFoundException, CarAlreadyInUseException,ConstraintsViolationException
	    {
	    	setupCarNotFoundDO();
	    	DriverDO driver = new DriverDO(username, password);
	        driver.setId(driverIdExisting);
	        driverService.selectCar(driver, carId);
	    	
	    }
	    
	    @Test(expected = CarAlreadyInUseException.class)
	    public void givenCarIsInUse_WhenSelectCar_thenException() throws ConstraintsViolationException, EntityNotFoundException, CarAlreadyInUseException
	    {

	        setUpCarInUseDO();

	        DriverDO driver = new DriverDO(username, password);
	        driver.setId(driverIdExisting);
	        driverService.selectCar(driver, carIdInUse);

	    }
	    
	    
	    private void setupCarNotFoundDO()
	    {
	    	
	    	given(carRepository.findByIdAndDeleted(carId, false)).willReturn((Optional.ofNullable(null)));
	    	
	    }
	    
	    private void setUpCarInUseDO()
	    {
	    	
	    	given(carRepository.findByIdAndDeleted(carIdInUse, false)).willReturn(generateMockedCarDO(carIdInUse, CarStatus.IN_USE));
	 
	    	
	    }
	    
	    private Optional<CarDO> generateMockedCarDO(long id, CarStatus carStatus)
	    {
	    	 CarDO car = new CarDO("MADRID-12345", 5, 9, false, EngineType.ELECTRIC, "TESLA");

	        car.setCarStatus(carStatus);
	        return Optional.of(car);
	    }
	    
	    
	    
	    private void createSaveDO()
	    {
	        DriverDO driver = new DriverDO(username, password);
	        given(driverRepository.save(driver)).willReturn(generateMockedDriverDO(driverIdCreated, OnlineStatus.OFFLINE, carId).get());
	        
	        
	    }
	    
	    private void setUpSaveException()
	    {
	        DriverDO driver = new DriverDO(null, password);
	        
	        given(driverRepository.save(driver)).willThrow(new DataIntegrityViolationException("Invalid Data"));
	    
	    
	    }
	
	 
	//DAO Data
	    private Optional<DriverDO> generateMockedDriverDO(long id, OnlineStatus onlineStatus, long carId)
	    {
	        CarDO car = new CarDO("MADRID-12345", 5, 9, false, EngineType.ELECTRIC, "TESLA");
	        
	        DriverDO driver = new DriverDO(username, password);
	        driver.setId(id);
	        driver.setCar(car);
	        driver.setOnlineStatus(onlineStatus);
	        return Optional.of(driver);
	    }
	
	

}
