package ca.gbc.productservice;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

// Tells Spring Boot to look for a main configuration class(@SpringBootApplication)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	// This annotation is used in combination with TestContainers to automatically configure the connection to the Test MongoDbContainer.
	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

	@LocalServerPort
	private Integer port;

	//http://localhost:port/api/product
	// Run this method before each test
	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongoDBContainer.start();
	}

	// Add update and delete test

	@Test
	void createProductTest() {
		String requestBody = """
				{
					"name" : "Samsung TV",
					"description" : "Samsung TV - Model 2024",
					"price" : 2000
				}
				""";
		// RestAssured is used to perform HTTP requests and verify responses.
		// This test performs a POST request to the /api/product endpoint to create a new product.
		// Then it verifies that the response status is 201 (Created) and the response body contains the correct product details.
		// BDD -0 Behavioural Driven Development (Given, When, Then)
		RestAssured.given()
				.contentType("application/json") // Set the content type of the request to JSON
				.body(requestBody) // Pass the request body (the product data)
				.when()
				.post("api/product") // Perform the POST request to the /api/product endpoint
				.then()
				.log().all()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("Samsung TV"))
				.body("description", Matchers.equalTo("Samsung TV - Model 2024"))
				.body("price", Matchers.equalTo(2000));
	}

//	@Test
//	void createProductTest() {
//		String requestBody = """
//            {
//                "name" : "Samsung TV",
//                "description" : "Samsung TV - Model 2024",
//                "price" : 2000
//            }
//            """;
//		RestAssured.given()
//				.contentType("application/json") // Set the content type of the request to JSON
//				.body(requestBody) // Pass the request body (the product data)
//				.when()
//				.post("api/product") // Perform the POST request to the /api/product endpoint
//				.then()
//				.log().all()
//				.statusCode(201)
//				.body("id", Matchers.notNullValue())
//				.body("name", Matchers.equalTo("Samsung TV"))
//				.body("description", Matchers.equalTo("Samsung TV - Model 2024"))
//				.body("price", Matchers.equalTo(2000));
//	}

	@Test
	void getProductsTest() {
		String requestBody = """
				{
					"name" : "Samsung TV",
					"description" : "Samsung TV - Model 2024",
					"price" : 2000
				}
				""";

		//BDD -0 Behavioural Driven Development (Given, When, Then)
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("api/product")
				.then()
				.log().all()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("Samsung TV"))
				.body("description", Matchers.equalTo("Samsung TV - Model 2024"))
				.body("price", Matchers.equalTo(2000));

		RestAssured.given()
				.contentType("application/json")
				.when()
				.get("api/product")
				.then()
				.log().all()
				.statusCode(200)
				.body("size()", Matchers.greaterThan(0))
				.body("[0].name", Matchers.equalTo("Samsung TV"))
				.body("[0].description", Matchers.equalTo("Samsung TV - Model 2024"))
				.body("[0].price", Matchers.equalTo(2000));
	}
}
