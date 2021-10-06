package com.freenow.service.driver;

import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

public interface DriverService
{

    DriverDO find(Long driverId) throws EntityNotFoundException;

    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    void delete(Long driverId) throws EntityNotFoundException;

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);
    
    void selectCar(DriverDO driver, Long carId) throws EntityNotFoundException, CarAlreadyInUseException;
    
    void deselectCar(DriverDO driver, Long carId) throws EntityNotFoundException, ConstraintsViolationException;
    
    List<DriverDO> findAll( Specification<DriverDO> specification );

}
