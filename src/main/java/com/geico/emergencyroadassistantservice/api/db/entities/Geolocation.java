
package com.geico.emergencyroadassistantservice.api.db.entities;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "geolocation")
@JsonInclude(Include. NON_NULL)
public class Geolocation {

	@JsonIgnore
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "latitude", columnDefinition="Numeric(10,6)" )
	private BigDecimal latitude;
	
	@Column(name = "longitude", columnDefinition="Numeric(10,6)")
	private BigDecimal longitude;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Geolocation [latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	
	
		
}
	