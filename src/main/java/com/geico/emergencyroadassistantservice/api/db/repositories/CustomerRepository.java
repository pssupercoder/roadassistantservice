package com.geico.emergencyroadassistantservice.api.db.repositories;


import org.springframework.data.repository.CrudRepository;

import com.geico.emergencyroadassistantservice.api.db.entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	Customer findById(long id);
	
	Customer findByGuid(String guid);
	
	Customer findByFirstNameAndLastName(String firstName, String lastName);
	
}
