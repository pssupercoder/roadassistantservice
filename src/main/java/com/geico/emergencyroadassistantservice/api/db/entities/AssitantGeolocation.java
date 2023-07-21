
package com.geico.emergencyroadassistantservice.api.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
	
	@JsonIgnore
	@OneToOne
    @JoinColumn(name = "geolocation_id")
	private Geolocation geolocation;

	
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

	public Geolocation getGeolocation() {
		return geolocation;
	}

	public void setGeolocation(Geolocation geolocation) {
		this.geolocation = geolocation;
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
		return "AssitantGeolocation [id=" + id + ", assistant=" + assistant + ", geolocation=" + geolocation
				+ ", customer=" + customer + ", distance=" + distance + "]";
	}

	
	
}
	