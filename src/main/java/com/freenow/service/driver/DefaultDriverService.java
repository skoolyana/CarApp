package com.freenow.service.driver;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.CarStatus;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.query.search.NotDeletedSpecification;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.ConstraintViolationException;
import org.springframework.data.jpa.domain.Specification;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;
    private final CarRepository carRepository;

    
    private static final String ENTITY_NOT_FOUND_MESSAGE = "Could not find entity with id: %s";


    public DefaultDriverService(final DriverRepository driverRepository, final CarRepository carRepository)
    {
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException
    {
    	return driverRepository
    	            .findByIdAndDeleted(driverId, false)
    	            .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, driverId)));

    }

    /**
     * 
     * Using Optimistic Locking to ensure car gets only one update
     */
    
    @Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public void selectCar(DriverDO driver, Long carId) throws EntityNotFoundException, CarAlreadyInUseException {
		
    	
    	 try
         {
             CarDO car = carRepository.findByIdAndDeleted(carId, false).orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, carId)));
             if (car.getCarStatus() == CarStatus.IN_USE)
             {
                 throw new CarAlreadyInUseException(String.format("Car %s is already in use", carId));
             }
             car.setCarStatus(CarStatus.IN_USE);
             driver.setCar(car);
         }
    	 catch (ObjectOptimisticLockingFailureException ex)
         {
             String message = ex.getMessage();
             LOG.error(String.format(" Excption While Persisting data: %s", message), ex);
             if (message != null && message.contains("DriverDO"))
             {
                 throw new ConstraintViolationException(String.format("Driver: %s must not have a Car selected", driver.getId()), null);
             }

             throw new CarAlreadyInUseException(String.format("Car %s is already in use", carId));
         }
		
	}

    @Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public void deselectCar(DriverDO driver, Long carId) throws EntityNotFoundException, ConstraintsViolationException {
		
    	try
        {
            CarDO car = carRepository.findByIdAndDeleted(carId, false).orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, carId)));
            
        
    	if (car.getCarStatus() == CarStatus.FREE)
        {
            throw new ConstraintsViolationException(String.format("Car %s must be booked before deselecting", carId));
        }
    	car.setCarStatus(CarStatus.FREE);
        driver.setCar(null);
    	
        }
    	catch (ObjectOptimisticLockingFailureException ex)
        {
            LOG.error(String.format(" Excption While Persisting data: %s", ex.getMessage()), ex);
            throw new ConstraintViolationException(String.format("Car: %s must be booked or Driver: %s must have a Car selected", carId, driver.getId()), null);
        }
    	
	}
    
    
    @Override
    public List<DriverDO> findAll(Specification<DriverDO> specification)
    {
        //Handle Soft Deletes 
        specification.and(NotDeletedSpecification.isNotDeleted());
        return driverRepository.findAll(specification);
    }


}
