# SOLUTION NOTES

## RUNNING THE APP

 - Generate the jar file by performing mvn clean install; the jar file will be generated in the target directory.
 - Execute java -jar wcl-cake-manager.jar from the command line to run the Spring Boot Application.
 - In a browser, use the url http://localhost:8082/ to access the main page displaying a list of Cakes in tabular form.
 - The URLS available in the application are as follows :-
    - http://localhost:8082/ - displays list of cakes in tabular format.
    - http://localhost:8082/cakes - displays list of cakes in Json format.
    - http://localhost:8082/add - form for adding a new Cake.
    - 
## CI URL

https://app.circleci.com/pipelines/github/warnettconsultingltd/wcl-cake-manager

# DEVELOPMENT NOTES

## ISSUE 1 - Project Setup and Creation

The decision was made to uplift the codebase from the old Servlet / JSP style into a more
modern Spring Boot microservice style application.  The nature of the problem suggested a clear delineation 
between client and server.

As both client and server are resident within the same project, there is an issue that only
one SpringBootApplication can be present in a project as an entry point.

The solution was thus to make the client and server controllers with specific, distinct Spring profiles.
The produced jar file is thus invoked with wither client or server Spring profile and launches
the correct instance.

### TECH STACK

- Java 16
- Spring Boot
- Thymeleaf for client
- H2 local database

### IMPROVEMENTS

- Split project into separate client / server projects.  This would enable benefits such as
scalability, load balancing etc as found with microservices.
  
## ISSUE 2 - ADD CAKE ENTITY

First cut of an entity representing a Cake.

## ISSUE 3 - SERVICE CLASS FOR CAKES

Introduced a Service class, CakeService, on the server side to service the defined server
endpoint, "/" to return a human readable version of Cakes, "/cakes" to return the cake list in
JSON format.

Dummy data was returned from the service class at this point in time to satisfy tests - database
interaction to be determinCHANGESed at a later stage.

## ISSUE 4 - RETRIEVE EXISTING CAKE DATA

A gist URL was used in the original solution to retrieve seed Cake data.

A separate entity was introduced to model the seed data; the data contains title, desc and image
values which doesn't necessarily lend itself well to a natural primary key.

The JSON was converted to a list of SeedDataCake objects and is returned from the utility class.

## ISSUE 5 - DUPLICATES IN SEED DATA

The JSON seed data was observed to return duplicates; the assumption being made here is that the
intention was for one of each cake type to be contained - multiple cakes would be an odd way to model
number of cakes available, after all.

The solution is to return a Set rather than a List to strip out the duplicate entities; the
Java 16 records should handle equals correctly in this instance.

## ISSUE 6 - DEFINE IN MEMORY H2 DATABASE

A DDL file was created, data.sql, to create a Cakes table.

CREATE TABLE Cakes (
  id uuid default random_uuid() PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL,
  imageurl VARCHAR(250) NOT NULL
);

As the seed data contains no primary key value - there can be different lemon cheesecakes and Strings 
as primary keys are generally meh to say the least, the database will assign a random
UUID to be used as primary key.

The application-apiserver.properties file was amended to add in database properties.

### IMPROVEMENTS

data.sql is invoked by each Spring profile, so in this fake example, the client will also
have a table established - a table never used.  This is a consequence of client / server
being defined in the same project.  This can be resolved by implementing the client and
server as separate projects, as mentioned before.

## ISSUE 7 - POPULATING DB WITH SEED DATA

Populating the seed data needs to be a one-off action; this was achieved by creating a 
SeedDataService, autowired with the previously created SeedDataRetriever and a newly created
CakeRepository instance.

The CakeRepository is a JPA Repository, for simplicity.

The service uses a Java 8 stream to convert and persist the SeedDataCake entities.  Why convert?
After all, surely it would be simpler to have a single class rather than a class for seed data, a
class representing a cake and a class for the JPA entity?  For me, I prefer POJOs to be as free
as annotations as possible; after all, @Id only really has context within a persistence context.
Yes, it creates slightly more work in converting - and something like MapStruct could be potentially
used for that - but it means that areas of the code become cleaner to read.  

If the project was split into two projects - client and server - then the project structure would be
better served by using a maven multi-module project, of which persistence would be one.  That would
enable the JPA annotations, dependencies to be entirely self contained within a persistence module 
as opposed to leaking to other modules.

A test was written to trigger the starting of the app server; the contents of the in memory db
are then evaluated.

## ISSUE 8 - ADDING CIRCLE CI

I use Circle CI - used to use Travis but ran out of free credits....SS

### IMPROVEMENTS

- Include quality metrics - coverage stats could be pushed to something like codecov.
- Include code analysis reports

## ISSUE 9 - CREATE INTIIAL UI

Built a simple Thymeleaf UI, with boxes for containing a list of Cakes and the cake list JSON.

Added a simple service on the uiclient side to retrieve data to populate the Thymeleaf model
correctly; for this git push, uses dummy data.

Added tests of the Thymeleaf page with regards to model population.

## ISSUE 10 - CONNECT UI AND SERVER

The UI data service was amended to issue calls to the Server REST API.

### PROBLEM

Tests should be written to ensure that the front end is correctly populated with the Seed Data
from the server - however, this requires server and client to be running at the same time.
Trying to think of a way to automate this - more proof that client and server should be in
separate projects...

This issue is mitigated by the fact that tests exist to check that the client model gets correctly
populated from the client data service and that the REST API has tests to confirm seed data is
retrieved and populated into the database.

As a workaround, a Collection of Postman tests has been created and are placed within the
src/main/resources directory.

### RESOLUTION

At this stage, given the numerous issues surrounding the split between the client and server, especially surrounding
testing, I've decided to put merge the two into a single project and remove the delineation.  The project will thus
become a single Spring Boot application providing the Thymeleaf UI over the seed data retriever and persistence layer.
A step back to go two steps forward...

## ISSUE 11 - Add Thymeleaf elements to facilitate adding a new Cake

Added a new add_cake_form page.  Added link to that page on the two existing pages.

## ISSUE 12 - Persist a new Cake.

The process of adding a new Cake should be :-
 - Click link to access new Cake form ( link should be on / and /cakes pages)
 - Enter new Cake details
 - Press button to add new Cake
 - HAPPY PATH
   - Cake persisted
   - App returns to page to display list of Cakes with new Cake added
 - OOPS PATH
   - A Cake already exists for the supplied Title
   - Error displayed on screen
   - Cake not persisted to database
  
Due to issues experienced with Mockvc and the integration test, it was decided to concentrate
on getting the code working due to timeboxing issues.  I'd say for the record this is more an 
issue with front end skills than anything else, the overwhelming majority of work undertaken has#
been back end.

A single validation occurs, namely checking the Title to avoid duplicate Cakes being entered; it
would need to be determined if that was sufficient validation.

## TO DO
- Things mentioned in original brief
  - OAuth2 authentication - not done that before, something else to learn.
  - Containerisation - should be a fairly simple task to define a Dockerfile copying the jar file.
    executing the jar file and exposing port 8082.  Would need to be integrated into maven build
    process, could be pushed to DockerHub.
- Improve validation
  - Photo links returning non 200 status, ie 404 or 403 as seen with seed data
  - Validate the data entered
    - Check for null values
    - Check for empty Strings
    - Check for nasty code trying to have the database ( any hacks imprioving this code are welcome...)
    - Check the format of the image url entered
- Fix failing integration tests!  
  - Adding a new Cake works however in the integration test, commented out, the data isn't
    being received by the endpoint.  Cogniscent of time, bearing this is the first time I've
    work with MockMvc and Thymeleaf, felt it was best to complete the code.
- Use Spring profiles for dev/prod/test style segregation of properties; using a separate
  database ( in-mem for dev/test, other for prod ) would remove need to mock integration tests
- Use CSS stylings on the front end.  It looks like utter crap.  
