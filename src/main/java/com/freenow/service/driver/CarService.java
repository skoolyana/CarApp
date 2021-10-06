package com.freenow.service.driver;

import java.util.List;

import com.freenow.domainobject.CarDO;
import com.freenow.domainvalue.CarStatus;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;

public interface CarService {
	
	CarDO find(Long carId) throws EntityNotFoundException;


    CarDO create(CarDO carDO) throws ConstraintsViolationException;


    void delete(Long carId) throws EntityNotFoundException;


    void updateCarStatus(Long carId, CarStatus carStatus) throws EntityNotFoundException;


    List<CarDO> find(CarStatus carStatus);


}
