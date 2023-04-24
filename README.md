# SpringBootBlogApiLatest
Build RestApi for simple Blog Application

	user should able to List, create, update and delete posts
	user should able to perform pagination and sorting on posts
	user should able to add, update, delete comments for the posts
	user should able to register to the blog app
	user should able toable to login to the blog app
Build RestApi for pagination and sorting
Build RestApi for login and singup
proper restapi exception or error handling
proper restApi request validation
secure restApi's(role based security)-admin,user
use token based authentication
Document all restApi's for consumers
deploy blog app production




----selecting the technology stack for blogApp
	
java platform: -- java 8+
frameworks: Spring frameworks and its protfolio projects like spring boot and security(JWT),Spring data JPA
Build tool: Maven
IDE: intellij IDEA
Server: tomcat as embedded server
Database: mySql
RestClient: postMan
RestApi document: swagger
production: AWS



--Identify Resources for blog domain
	we are building restApi for a blog application.
	the resources that can be identified in a blog domain are post, comment and user.



Springboot Application Architecture:::
	-----------------------------------

		  dto's		API layer	Business logic		Persistence logic
		   |		 /		    /			 /
		   |		/		   /			/
Postman client  <------>  controller <------->  Service <-------> DAO(Repository) <----------------> DB
		   |									JDBC driver,JDBC url,username,password
		   |
		  json







RestApi for Post Resource
------------------------------
Description				URL path						HTTP Method
----------------------------------------------------------------------------------------------------------------------------
Get All posts				/api/posts						GET


Get post by id				/api/posts/{id}						GET


create a new post			/api/posts						POST


update existing post with id		/api/posts/{id}						PUT


delete post by id			/api/posts/{id}						DELETE


paging and sorting posts		/api/posts?pageSize=5&pageNo=1&sortBy=FirstName		GET







Pagination Support for all get posts restApi
-------------------------------------------------
Pagination consists of two fields-page size and page number
Defining default values for pageNo is 0 and pageSize = 10
Changing existing get all posts REST API to provide pagination support..

ex: http://localhost:8080/api/posts/paging?pageNo=0&pageSize=10


private List<PostDto> content;
private int pageNo;
private int pageSize;
private long totalEle;
private int totalPages;
private boolean last;




one to many relationships
-------------------------


post Entity		comment entity
/			/			
post  <----------->  comment
	   |
	one to many







RestApi for Comment Resource
----------------------------------------------------------------------------------------------------------------------------
Description				URL path							HTTP Method
----------------------------------------------------------------------------------------------------------------------------
Get All Comment				/api/posts/{postId}/comments						GET
if it belongs to post 
with id =  postId

Get Comment by id			/api/posts/{postId}/comments/{id}					GET
if it belongs to post 
with id =  postId

create a new post			/api/posts/{postId}/comments						POST
for the new post 
with id =  postId

update existing post with id		/api/posts/{postId}/comments/{id}					PUT
if it belongs to post 
with id =  postId

delete post by id			/api/posts/{postId}/comments/{id}					DELETE


paging and sorting posts		/api/posts?pageSize=5&pageNo=1&sortBy=FirstName				GET
if it belongs to post 
with id =  postId



---------------------------------------------------------------------------------------------------------------------------------------


Using modelmapper for mapping some objects..

source: https://modelmapper.org/getting-started/


----------------------------------------------------------------------------




Spring boot restApi Exception Handling
--------------------------------------


		 |RestApi call				Throws Exception(
		 |					RESOURCE NOT FOUND EXCEPTION)
		 |						|
postmanclient  <-------------------> controller  <-------> service <---------------------
	/\										 |
	|										 |
	|										\|/
	------------------------------------------------------------------GlobalExceptionHandler--> this class hanldes exception specific and 
													global exception in a single place
															



default spring boot exception response:


{
	"timestamp": "",
	"status": 404,
	"error": "Not Found",
	"trace": "",
	"message": "",
	"path": ""
}


		 |
		\ /

customizing the default exception format


{
	"timestamp": "",
	"message": "",
	"details":""
		
}


Annotations for exception handling
---------------------------------
@ExceptionHandler
@ControllerAdvice



DevelopmentProcess
-------------------
Create ErrorDetails Class
create GlobalExceptionHandler class
Test using postman client


--------------------------------------------------------------------------------------------------------------------------------------------------------

Java bean validation basics
----------------------------
we validate a java bean with the standard framework-jsr 380, also known as bean validation 2.0

Hibernate Validator is the reference implementation of the validation API.

starting with spring boot 2.3, we need to explicitly add the spring boot starter-validation dependency


validating postDto

adding dependecy
using @notnull, @size ... on perticular postdto bean
then enabling validation in controller class by using @valid annotation
------------------------------------------------------------------------------------------------------------



springboot auto configuration for spring security.
--------------------------------------------------
spring-boot-starter-security starter that aggregates spring security dependencies together

Enables spring security's default configuration, which creates a servlet filter as a bean named springSecurityFilterChan. provides default login for u.

Creates a deault user with username as USER and a randemoly generated password that is logged to the console.

spring boot provides properties to customize default user's username and password

protect the password storage with Bcrypt algorithm

lets the user log out(default logout feature)

CSRF attack prevention.

if spring security is on classpath, spring boot automaticlly secure the all HTTP endpoints with basic authentication.



		
JWT Authentication development steps
----------------------------------

Add JWT related maven dependency

	need to add 3 maven dependencies
		jjwt-impl
		jjwt-api
		jjwt-jackson
	reference link
		https://mvnrepository.com/artifact/io.jsonwebtoken
	
create JWTAuthenticationEntryPoint
Add jwt properties in application properties file
create jwtTokenProvider- Utility class
create jwtAuthenticationFilter
create jwtAuthesponse DTO
configure JWT in spring security
change login or signup REST API to return JWT token



category management requirements -- secure category restApi using Roles
---------------------------------------
add category REST API  --  secure using admin role
get single category REST API -- provide a access for all the users
get all category REST API -- provide a access for all the users
update category REST API -- secure using admin role
delete category REST API --  secure using admin role	
get post by category REST API


-----------------------------------


Spring Doc

----------------------------------------------------------------------------------

spring-doc openapi java library helps to automate the generation of API documentation using springboot projects.
spring-doc openapi java library provides the integration between spring boot and swagger-api.

automatically generates documentation in JSON/YAML and HTML format APIs.

this library supports...
	OpenAI 3
	spring boot version 3(java17 & jakarta EE 9)
	jsr-303, specifically for @NotNull,@min,@max, and @size
	swagger-ui
	OAuth 2
this is community based project, not maintained by the spring framework contributers..

---------------------------------------------
development steps
---------------------
--> Adding springdoc-openApi maven dependency..
	
	https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui/2.0.4
	
	<!-- https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui -->
		<dependency>
    			<groupId>org.springdoc</groupId>
    			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    			<version>2.0.4</version>
		</dependency>

	
--> spring security supports for swagger urls(public access)

	  .requestMatchers("/swagger-ui/**").permitAll()
          .requestMatchers("/v3/api-docs").permitAll()

	add the above in security config file

--> defining general api information..

	@OpenApiDefinition
		@Info
		@Contact
		@License
		@ExternalDocumentation

--> Adding Authorization header in request using @SecurityRequirement and @SecurityScheme
	@SecurityScheme--> to add security scheme for JWT based authentication
	

--> Customizing swagger api docmentation with annotations
--> Customizing swagger models documentation with annotations

