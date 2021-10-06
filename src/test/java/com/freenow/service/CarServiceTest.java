package com.freenow.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainvalue.CarStatus;
import com.freenow.domainvalue.EngineType;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.driver.CarService;
import com.freenow.service.driver.DefaultCarService;
import com.freenow.service.driver.DefaultDriverService;

public class CarServiceTest {
	
	private CarService carService;
	
	  private static Long carIdExisting;
	  private static Long carIdNonExisting;
	  private static Long carIdCreated;
	
		 
	  private static int seatCount;
	  private static int rating;
	  private static String manufacturer;
	  private static String licensePlate;
	  private static String licensePlateDuplicate;
	  private static EngineType engineType;
	  private static boolean convertible;
	
	 @Mock
	 CarRepository carRepository;
	 
	 
	 @BeforeClass
	    public static void init()
	    {
	        carIdExisting = 60l;
	        carIdNonExisting = 61l;
	        carIdCreated = 62l;
	        licensePlate = "Madrid-1982";
	        licensePlateDuplicate = "Madrid-2009";
	        seatCount = 5;
	        rating = 9;
	        manufacturer = "Tesla";
	        engineType = EngineType.ELECTRIC;
	        convertible = false;

	    }

	
	@Before
	 public void setUp() {
		 
		  // With this call to initMocks we tell Mockito to process the annotations
	        MockitoAnnotations.initMocks(this);	      
	        
	        carService = new DefaultCarService(carRepository);
	       
	        given(carRepository.findByIdAndDeleted(carIdExisting, false)).willReturn(generateMockCarDo(carIdExisting));
	        given(carRepository.findByIdAndDeleted(carIdNonExisting, false)).willReturn(Optional.ofNullable(null));

	}
	
	@Test
    public void whenFindCarByExistingId_thenCorrect() throws EntityNotFoundException
    {
		
		 CarDO carDO = carService.find(carIdExisting);
		 
	 	 // then
	     assertThat(carDO.getId()).isEqualTo(carIdExisting);
	     assertThat(carDO.getLicensePlate()).isEqualTo(licensePlate);
	     assertThat(carDO.getRating()).isEqualTo(rating);
	     assertThat(carDO.getSeatCount()).isEqualTo(seatCount);
	     assertThat(carDO.getLicensePlate()).isEqualTo(licensePlate);
	     assertThat(carDO.isConvertible()).isEqualTo(convertible);
	     assertThat(carDO.getEngineType()).isEqualTo(engineType);
	     assertThat(carDO.getManufacturer()).isEqualTo(manufacturer);


    }

	
	  @Test(expected = EntityNotFoundException.class)
	    public void whenFindCarByNonExistingId_thenExcption() throws EntityNotFoundException
	    {
	        CarDO carDO = carService.find(carIdNonExisting);

	    }
	  
	  
	    @Test
	    public void whenDeleteCarByExistingId_thenCorrect() throws EntityNotFoundException
	    {
	        carService.delete(carIdExisting);
	        Optional<CarDO> findByIdAndDeleted = carRepository.findByIdAndDeleted(carIdExisting, true);

	    }
	    
	    
	    @Test(expected = EntityNotFoundException.class)
	    public void whenDeleteCarByNonExistingId_thenException() throws EntityNotFoundException
	    {
	        carService.delete(carIdNonExisting);
	    }
	    
	    
	    
	    @Test
	    public void whenCreateCar_thenCorrectCreatedId() throws ConstraintsViolationException
	    {
	    	
	    	setUpCreateDao();
	    	
	    	CarDO car = new CarDO(licensePlate, seatCount, rating, convertible, engineType, manufacturer);
	        CarDO carCreated = carService.create(car);

	        assertThat(carCreated.getId()).isEqualTo(carIdCreated);
	    	
	    }
	    
	    
	
	    
	    
	    
	    //This covers all the DataIntegrityViolationException from DAO layer
	    @Test(expected = ConstraintsViolationException.class)
	    public void whenCreateCar_givenDuplicateLicensePlate_thenException() throws ConstraintsViolationException
	    {
	    	setUpCreateException();
	    	CarDO car = new CarDO(licensePlateDuplicate, seatCount, rating, convertible, engineType, manufacturer);
	        CarDO carCreated = carService.create(car);
	    	
	    }
	    
	    
	    private void setUpCreateDao()
	    {
	        CarDO car = new CarDO(licensePlate, seatCount, rating, convertible, engineType, manufacturer);
	        given(carRepository.save(car)).willReturn(generateMockCarDo(carIdCreated).get());

	    }
	    
	    private void setUpCreateException()
	    {
	        CarDO car = new CarDO(licensePlateDuplicate, seatCount, rating, convertible, engineType, manufacturer);
	        given(carRepository.save(car)).willThrow(new DataIntegrityViolationException("Invalid Data"));
	    
	    }
	    
	    
	    
	
	 private Optional<CarDO> generateMockCarDo(long id)
	 {
	     CarDO car = new CarDO(licensePlate, seatCount, rating, convertible, engineType, manufacturer);
	     car.setId(id);
	     return Optional.of(car);
	 }


}
