package com.geico.emergencyroadassistantservice.api.db.repositories;


import org.springframework.data.repository.CrudRepository;

import com.geico.emergencyroadassistantservice.api.db.entities.Assistant;

public interface AssistantRepository extends CrudRepository<Assistant, Long> {

	Assistant findById(long id);
	
	Assistant findByGuid(String guid);
	
	Assistant findByFirstNameAndLastName(String firstName, String lastName);
}
