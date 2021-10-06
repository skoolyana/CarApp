package com.freenow.datatransferobject;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.freenow.domainvalue.EngineType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {
	
	@JsonIgnore
    private Long id;

    @NotNull(message = "License Plate cannot be null!")
    private String licensePlate;

    @NotNull(message = "Seat Count cannot be null!")
    private Integer seatCount;

    private Boolean convertible;
    private Integer rating;
    private EngineType engineType;
    private String manufacturer;
    
    
    
    public static CarDTOBuilder newBuilder()
    {
        return new CarDTOBuilder();
    }
    
    
 


    public void setId(Long id)
    {
        this.id = id;
    }


    public Long getId()
    {
        return id;
    }




	public String getLicensePlate()
    {
        return licensePlate;
    }


    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }


    public Integer getSeatCount()
    {
        return seatCount;
    }


    public void setSeatCount(Integer seatCount)
    {
        this.seatCount = seatCount;
    }


    public Boolean isConvertible()
    {
        return convertible;
    }


    public void setConvertible(Boolean convertible)
    {
        this.convertible = convertible;
    }


    public Integer getRating()
    {
        return rating;
    }


    public void setRating(Integer rating)
    {
        this.rating = rating;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public void setEngineType(EngineType engineType)
    {
        this.engineType = engineType;
    }


    public String getManufacturer()
    {
        return manufacturer;
    }


    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }


     CarDTO()
    {

    }


     CarDTO(Long id, String licensePlate, Integer seatCount, Boolean convertible, Integer rating, EngineType engineType, String manufacturer)
    {
        this.id = id;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
    }
    
    public static class CarDTOBuilder
    {
    	private Long id;
        private String licenseplate;
        private Integer seatcount;
        private Boolean convertible;
        private Integer rating;
        private EngineType engineType;
        private String manufacturer;

        
        public CarDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public CarDTOBuilder setLicenseplate(String licenseplate)
        {
            this.licenseplate = licenseplate;
            return this;
        }


        public CarDTOBuilder setSeatcount(Integer seatcount)
        {
            this.seatcount = seatcount;
            return this;
        }


        public CarDTOBuilder setConvertible(Boolean convertible)
        {
            this.convertible = convertible;
            return this;
        }


        public CarDTOBuilder setRating(Integer rating)
        {
            this.rating = rating;
            return this;
        }


        public CarDTOBuilder setEngineType(EngineType engineType)
        {
            this.engineType = engineType;
            return this;
        }


        public CarDTOBuilder setManufacturer(String manufacturer)
        {
            this.manufacturer = manufacturer;
            return this;
        }
        
        public CarDTO createCarDTO()
        {
            return new CarDTO(id, licenseplate, seatcount, convertible, rating, engineType, manufacturer);
        }

        
        
        
    }

    
    
    

}
