package com.freenow.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.freenow.domainvalue.GeoCoordinate;
import javax.validation.constraints.NotNull;
import org.springframework.hateoas.Link;

import org.springframework.hateoas.RepresentationModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverDTO extends RepresentationModel
{
	 @JsonIgnore
    private Long driverid;

    @NotNull(message = "Username can not be null!")
    private String username;

    @NotNull(message = "Password can not be null!")
    private String password;

    private GeoCoordinate coordinate;


    private DriverDTO()
    {
    }


    private DriverDTO(Long driverid, String username, String password, GeoCoordinate coordinate, Link link)
    {
        this.driverid = driverid;
        this.username = username;
        this.password = password;
        this.coordinate = coordinate;
        add(link);
    }


    public static DriverDTOBuilder newBuilder()
    {
        return new DriverDTOBuilder();
    }


  
  

    public String getUsername()
    {
        return username;
    }


    public String getPassword()
    {
        return password;
    }


    public GeoCoordinate getCoordinate()
    {
        return coordinate;
    }


    public Long getDriverId()
    {
        return driverid;
    }
    
    public void setDriverId(Long driverId)
    {
        this.driverid = driverId;
    }


    public static class DriverDTOBuilder
    {
        private Long driverid;
        private String username;
        private String password;
        private GeoCoordinate coordinate;
        Link link;


        public DriverDTOBuilder setId(Long driverid)
        {
            this.driverid = driverid;
            return this;
        }


        public DriverDTOBuilder setUsername(String username)
        {
            this.username = username;
            return this;
        }


        public DriverDTOBuilder setPassword(String password)
        {
            this.password = password;
            return this;
        }


        public DriverDTOBuilder setCoordinate(GeoCoordinate coordinate)
        {
            this.coordinate = coordinate;
            return this;
        }
        
        public DriverDTOBuilder setCarLink(Link link)
        {
            this.link = link;
            return this;
        }


        public DriverDTO createDriverDTO()
        {
            return new DriverDTO(driverid, username, password, coordinate,link);
        }

    }
}
