package com.freenow.domainobject;

import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.freenow.domainvalue.CarStatus;
import com.freenow.domainvalue.EngineType;

@Entity
@Table(name = "car",uniqueConstraints = @UniqueConstraint(name = "uc_license_plate", columnNames = {"license_plate"}))
public class CarDO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime date_created = ZonedDateTime.now();

  
    @Column(name = "license_plate", nullable = false)
    @NotNull(message = "License Plate can not be null!")
    private String licensePlate;


    @NotNull(message = "Seat Count can not be null!")
    @Column(name = "seat_count", nullable = false)
    private Integer seatCount;

    @Column
    private Boolean convertible;

    @Column
    private Integer rating;

    @Column(nullable = false)
    private String manufacturer;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "engine_type", nullable = false)
    private EngineType engineType;

    @Enumerated(EnumType.STRING)
    @Column(name = "car_status", nullable = false)
    private CarStatus carStatus;
    
    @Column(nullable = false)
    private Boolean deleted = false;

    @Version
    private long version = 0;
    

    private CarDO()
    {}
    
    public CarDO(String licensePlate, Integer seatCount, Integer rating, Boolean convertible, EngineType engineType, String manufacturer)
    {
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
        this.deleted = false;
        this.carStatus = CarStatus.FREE;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ZonedDateTime getDate_created() {
		return date_created;
	}

	public void setDate_created(ZonedDateTime date_created) {
		this.date_created = date_created;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Integer getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(Integer seatCount) {
		this.seatCount = seatCount;
	}

	public Boolean isConvertible() {
		return convertible;
	}

	public void setConvertible(Boolean convertible) {
		this.convertible = convertible;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public EngineType getEngineType() {
		return engineType;
	}

	public void setEngineType(EngineType engineType) {
		this.engineType = engineType;
	}

	public CarStatus getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(CarStatus carStatus) {
		this.carStatus = carStatus;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
        int result = 1;
        result = prime * result + ((carStatus == null) ? 0 : carStatus.hashCode());
        result = prime * result + ((convertible == null) ? 0 : convertible.hashCode());
        result = prime * result + ((date_created == null) ? 0 : date_created.hashCode());
        result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
        result = prime * result + ((engineType == null) ? 0 : engineType.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((licensePlate == null) ? 0 : licensePlate.hashCode());
        result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
        result = prime * result + ((rating == null) ? 0 : rating.hashCode());
        result = prime * result + ((seatCount == null) ? 0 : seatCount.hashCode());
        result = prime * result + (int) (version ^ (version >>> 32));
        return result;	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarDO other = (CarDO) obj;
			
		if (carStatus != other.carStatus)
            return false;
		
		if (convertible == null)
        {
            if (other.convertible != null)
                return false;
        }
        else if (!convertible.equals(other.convertible))
            return false;
		
		
		if (date_created == null)
	       {
	           if (other.date_created != null)
	               return false;
	       }
		 
		if (deleted == null)
	       {
	           if (other.deleted != null)
	               return false;
	       }
	       else if (!deleted.equals(other.deleted))
	           return false;
		 
		if (engineType != other.engineType)
	           return false;
		 
		if (id == null)
	       {
	           if (other.id != null)
	               return false;
	       }
		 
		if (licensePlate == null)
	       {
	           if (other.licensePlate != null)
	               return false;
	       }
	       else if (!licensePlate.equals(other.licensePlate))
	           return false;
	    if (manufacturer == null)
	      {
	          if (other.manufacturer != null)
	               return false;
	      }
	       else if (!manufacturer.equals(other.manufacturer))
	           return false;
		 
		
	        
	    if (rating == null)
	     {
	           if (other.rating != null)
	               return false;
	     }
	    else if (!rating.equals(other.rating))
	           return false;
	        
	        
	    if (seatCount == null)
	    {
	           if (other.seatCount != null)
	               return false;
	    }
	    else if (!seatCount.equals(other.seatCount))
	            return false;
	    if (version != other.version)
	           return false;
	      
	    return true;
	        
		
	}

}
