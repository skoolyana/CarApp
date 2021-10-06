package com.freenow.controller;


import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.freenow.controller.mapper.CarMapper;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainvalue.CarStatus;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.driver.CarService;



@RestController
@RequestMapping("v1/cars")
public class CarController {

	
	 private final CarService carService;


	    @Autowired
	    public CarController(final CarService carService)
	    {
	        this.carService = carService;
	    }

	    
	    @GetMapping("/{carId}")
	    public CarDTO getcar(@PathVariable long carId) throws EntityNotFoundException
	    {
	        return CarMapper.makeCarDTO(carService.find(carId));
	    }

	   
	    
	    @DeleteMapping("/{carId}")
	    public void deleteCar(@PathVariable long carId) throws EntityNotFoundException
	    {
	    	carService.delete(carId);
	    }
	    
	    @PostMapping
	    @ResponseStatus(HttpStatus.CREATED)
	    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException
	    {
	        CarDO carDO = CarMapper.makeCarDO(carDTO);
	        return CarMapper.makeCarDTO(carService.create(carDO));
	    }
	    
	    
	    @PutMapping("/{carId}")
	    public void updateStatus(
	        @PathVariable long carId, @RequestParam CarStatus carStatus)
	        throws EntityNotFoundException
	    {
	        carService.updateCarStatus(carId, carStatus);
	    }


	    @GetMapping
	    public List<CarDTO> findcars(@RequestParam CarStatus carStatus)
	    {
	        return CarMapper.makeCarDTOList(carService.find(carStatus));
	    }
	    
}
