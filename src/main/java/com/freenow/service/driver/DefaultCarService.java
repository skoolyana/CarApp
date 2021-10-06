package com.freenow.service.driver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainvalue.CarStatus;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;

@Service
public class DefaultCarService implements CarService {
	
	private static final Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);
	
	private final CarRepository carRepository;
	
	
	@Autowired
    public DefaultCarService(final CarRepository carRepository)
    {
        this.carRepository = carRepository;
    }

	
	

	@Override
	public CarDO find(Long carId) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		  return findCarChecked(carId);
	}

	
	@Override
	public CarDO create(CarDO carDO) throws ConstraintsViolationException {
		
		CarDO car;
        try
        {
            car = carRepository.save(carDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a car: {}", carDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return car;
	}
	
	  /**
     * Deletes an existing car by id.
     *
     * @param carId
     * @throws EntityNotFoundException if no car with the given id was found.
     */
	
	

	@Override
	@Transactional
	public void delete(Long carId) throws EntityNotFoundException {
		 CarDO carDO = findCarChecked(carId);
	     carDO.setDeleted(true);

	}

	
	/**
     * Update the location for a car.
     *
     * @param carId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
	public void updateCarStatus(Long carId, CarStatus carStatus) throws EntityNotFoundException {
		
    	CarDO carDO = findCarChecked(carId);
        carDO.setCarStatus(carStatus);

	}

	@Override
	public List<CarDO> find(CarStatus carStatus) {
		// TODO Auto-generated method stub
		 return carRepository.findByCarStatusAndDeleted(carStatus, false);
	}
	
	
	private CarDO findCarChecked(Long carId) throws EntityNotFoundException
    {
        return carRepository
            .findByIdAndDeleted(carId, false)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find entity with id: %s", carId)));
    }
	
	

}
