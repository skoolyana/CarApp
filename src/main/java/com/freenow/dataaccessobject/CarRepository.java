package com.freenow.dataaccessobject;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.freenow.domainobject.CarDO;
import com.freenow.domainvalue.CarStatus;

/**
 * DAO for Car table.
 * 
 *
 */
public interface CarRepository extends CrudRepository<CarDO, Long> {

	
	 List<CarDO> findByCarStatusAndDeleted(CarStatus carStatus, Boolean deleted);


	 Optional<CarDO> findByIdAndDeleted(Long id, Boolean deleted);
	
}
