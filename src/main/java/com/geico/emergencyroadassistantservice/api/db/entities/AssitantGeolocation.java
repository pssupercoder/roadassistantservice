
package com.geico.emergencyroadassistantservice.api.db.entities;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table (name = "assitant_geolocation")
@JsonInclude(Include. NON_NULL)
public class AssitantGeolocation {

	@JsonIgnore
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne
    @JoinColumn(name = "assistant_id")
	private Assistant assistant;
	
	@Column(name = "latitude", columnDefinition="Numeric(10,6)" )
	private BigDecimal latitude;
	
	@Column(name = "longitude", columnDefinition="Numeric(10,6)")
	private BigDecimal longitude;
	
	@OneToOne
    @JoinColumn(name = "customer_id")
	private Customer customer;
	
	
	@Transient
	@JsonIgnore
	private Double distance=Double.valueOf(0.0);
	
	public Assistant getAssistant() {
		return assistant;
	}

	public void setAssistant(Assistant assistant) {
		this.assistant = assistant;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "AssitantGeolocation [id=" + id + ", assistant=" + assistant + ", latitude=" + latitude + ", longitude="
				+ longitude + ", customer=" + customer + ", distance=" + distance + "]";
	}

	
	
}
	