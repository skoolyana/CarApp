package com.freenow.controller.mapper;

import com.freenow.controller.CarController;
import com.freenow.controller.DriverController;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.exception.EntityNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class DriverMapper 
{
	
	private static final Logger LOG = LoggerFactory.getLogger(DriverMapper.class);
	
	private DriverMapper()
    {

    }
	
    public static DriverDO makeDriverDO(DriverDTO driverDTO)
    {
        return new DriverDO(driverDTO.getUsername(), driverDTO.getPassword());
    }


    public static DriverDTO makeDriverDTO(DriverDO driverDO)
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder = DriverDTO.newBuilder()
            .setId(driverDO.getId())
            .setPassword(driverDO.getPassword())
            .setUsername(driverDO.getUsername());

        GeoCoordinate coordinate = driverDO.getCoordinate();
        if (coordinate != null)
        {
            driverDTOBuilder.setCoordinate(coordinate);
        }
        
        try
        {
            CarDO carDO = driverDO.getCar();
        
            if (carDO != null)
            {
                driverDTOBuilder
                    .setCarLink(
                        linkTo(
                            methodOn(CarController.class)
                                .getcar(carDO.getId())).withRel("car"));

            }
            else
            {
                driverDTOBuilder.setCarLink(linkTo(methodOn(DriverController.class).getDriver(driverDO.getId())).withRel("self"));
            }
        }
        catch (EntityNotFoundException e)
        {
            LOG.error(String.format("Exception in DTO to DO coversion: %s", e.getMessage()), e);
        }

        return driverDTOBuilder.createDriverDTO();
    }


    public static List<DriverDTO> makeDriverDTOList(Collection<DriverDO> drivers)
    {
        return drivers.stream()
            .map(DriverMapper::makeDriverDTO)
            .collect(Collectors.toList());
    }
}
