package com.geico.emergencyroadassistantservice.api.services;

import java.util.List;
import java.util.Optional;

import com.geico.emergencyroadassistantservice.api.db.entities.Assistant;
import com.geico.emergencyroadassistantservice.api.db.entities.Geolocation;
import com.geico.emergencyroadassistantservice.api.exceptions.GeicoException;

public interface RoadsideAssistanceServiceOrch {

	public void updateAssistantLocation(String assistantGuid, Geolocation assistantLocation) throws GeicoException;

	
	public List<Assistant> findNearestAssistants(Geolocation geolocation, int limit) ;

	
	public Optional<Assistant> reserveAssistant(String customerGuid, Geolocation customerLocation) throws GeicoException;

	
	public void releaseAssistant(String customerGuid, String assistantGuid) throws GeicoException ;
	
}