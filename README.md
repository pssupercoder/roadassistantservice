# Emergency RoadSide Assistant Service

Service helps Geico customers with a disabled vehicle, to get immediate roadside assistance, by connecting
them directly with emergency assistance service trucks that are available and operating at nearby locations

 Features 

 0. Allow Assistants, customers and geolocation additions in system and get information back
 1. Find nearby assistant based on geocodes
 2. Reverse & Release Assistants
 3. Check all reservations in system
 4. JWT token security
 5. Micorservice Rest APIs 
 6. H2 Database Durable Storage on file system (Test Phase), Browser Accessible
 7. Bad Requests Handling
 

# Pre-Requestities 

 1. Java 17
 2. maven
 3. eclipse
 4. git setup (eclipse or local)
 5. Postman tool 


# Assumptions 

 1. Service is handling geo location based on latitude and longitude values for now that can extend based on zip code and address along with within certain distance.
    Note: Follow Test execution steps 
 
 2. Assistant and Customer information required in database.
    Note: Follow Test execution steps 
 
 3. Later phase a goecode service required to translate geolocation by address 
 
 4. External Error logging service require for later phase with some http response fix.
 
 5. Execute Add Geocodes API request one time only as need further handling to avoid multiple entries in database.    
 

----------------------------------------------------------------------------------

# Service Configuration file - Exists in service base folder --> /src/main/resources

 1. Local Profile        -  application-local.yml
 2. Main Default Profile - application.yml
 

# Long lived access token used in api requests as Bearer Token used in postman collection
  eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkdlaWNvIFRlc3QiLCJpYXQiOjE1MTYyMzkwMjIsImV4cCI6MTkxMDgyNzE1M30.i0yLO8CFycE_Pa3cI1xq1MR7mU527JgOBhAKv--p1kE


# Run Service 

  From eclipse
 
    1. get Project from git location & convert in maven project 
    2. Right Click -> Clean Build in eclipse
    3. Right Click ->  Run As -> Run Configurations -- > Right Click "Java Application from left tree --> On right Pane add Main Class --> com.geico.emergencyroadassistantservice.api.EmergencyRoadAssistantApplication
       Click on "Arguments Tab" add vm argument -->  -Dspring.profiles.active=local --> Save and run
 
  Terminal - From service root folder
  
  1. mvn install

  2. mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=local"
       OR
    java -Dspring.profiles.active=local -jar target/emergency-road-assistant-service-0.0.1-SNAPSHOT.jar


# H2 Database Console to access database in Browser  

	1. Open  http://localhost:8090/h2-console  in Browser
	2. Check JDBC URL -- > jdbc:h2:file:./geicodb
	3. UserName: sa
	4. Password: sa
	5. Click --> Connect

  Note: you can see file "geicodb.mv.db" in service root folder once service will be up & running


    
# Rest APIs have been created to perform all required operation that can be used via Postman tool.
  Note: File "EmergencyAssistantService.postman_collection.json" exists in service root folder. Follow test steps for setup and run.      
  
   

 
----------------------------------------------------------------------------------------    


# Test Execution Steps

1. Download postman tool for API execution --> https://www.postman.com/downloads/

2. Install and Open postman tool

3. Click on import button to import postman API collection that exists in service codebase folder by Name : EmergencyAssistantService.postman_collection

4. Click on EmergencyAssistantService Dropdown

5. Click on Add Assistant  --> Click on Send 
   Note: 1. API response return Assistant Guid eg. 60e7b553-0b1e-42d4-8635-0ce3301a5c55 , copy this value as required later.
         2. To create more than one assistant as per requirement, update body of request by changing first name and last name for each request and re-execute.
          
6. Click on Add Customer --> Click on Send 
   Note: API response return Customer Guid eg. 525283e1-89cb-437f-a3de-dbafe4795002 , copy this value as required later.

7. Click on Update Assistant Location --> Replace default Assistant Guid id in url from one copied in step 5   --> Click Send
   1. eg http://localhost:8080/v1/assistants/60e7b553-0b1e-42d4-8635-0ce3301a5c55/location
    2. Update geo locations for more than one assistant if require, update body of request by using geocodes for each request and re-execute.

  Sample Geocodes :
   {
    "latitude": 33.032313,
    "longitude": -117.085730
   },
   {
    "latitude": 33.031256,
    "longitude": -117.085231
   },
   {
    "latitude": 33.033385,
    "longitude": -117.086430
  }
	 
9. Lookup nearby Assistant --> Click on Find NearBy Assistant With Limit Criteria --> Click Send
   Note: you will see assistant information in response body according to provided geo codes in request body, limit value can be modified in url

10. Click on Reserve Assistant For Customer --> Replace default Customer Guid id in url from one copied in step 6 --> Click Send
    eg. http://localhost:8080/v1/customers/525283e1-89cb-437f-a3de-dbafe4795002/reserveAssistant 
    Note: you will see 200 success response and nearby assistant if exits will be allocated to customer.

11. Click on Get ALL Reservations --> CLick send
    Note: you will see Assistant reserved for provided customer in Step 10.

12. Lookup nearby Assistant --> Click on Find NearBy Assistant With Limit Criteria --> Click Send
   Note: you will not see assistant information in response body as already assigned to customer

13. Click on Release Assigend Assistant For Customer --> Replace default Customer Guid id in url from one copied in step 6 , Replace default Assistant Guid id in url from one copied in step 5 --> Click Send
    eg. http://localhost:8080/v1/customers/525283e1-89cb-437f-a3de-dbafe4795002/releaseAssistant 
    Note: you will see 200 success response.

Rerun step 11. and 12 to see Assiatant is back for reservation.
 
Note: In case of any data issue, delete database file as per note in "H2 Database Console to access database in Browser" section and rerun steps as given
