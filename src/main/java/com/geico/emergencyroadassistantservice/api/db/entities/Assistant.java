package com.geico.emergencyroadassistantservice.api.db.entities;

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
@Table (name = "assistant")
@JsonInclude(Include. NON_NULL)
public class Assistant  implements Comparable<Assistant>{

	@JsonIgnore
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "guid")
	private String guid;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	/*
	 * @OneToOne
	 * 
	 * @JoinColumn(name = "address_id") private Address address;
	 * 
	 * @OneToOne
	 * 
	 * @JoinColumn(name = "geolocation_id") private Geolocation geolocation;
	 */

	@Transient
	@JsonIgnore
	private Double distance=Double.valueOf(0.0);
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Override
	public String toString() {
		return "Assistant [id=" + id + ", guid=" + guid + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
	
	// Override the compareTo() method
    public int compareTo(Assistant c)
    {
        if (distance > c.distance) {
            return 1;
        }
        else if (distance < c.distance) {
            return -1;
        }
        else {
            return 0;
        }
    }

	

}