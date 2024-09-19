package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import files.CustomerAuthorizationPojo;
import files.SubmitOrderRequestPojo;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.JsonPath;

public class APIValidations {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String clientName = "vikasvv43";
		String clientEmail = "vikasvv43@example.com";

		RestAssured.config = RestAssuredConfig.config().sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation());
		RestAssured.baseURI = "https://simple-books-api.glitch.me";

		// Authorization
		CustomerAuthorizationPojo cap = new CustomerAuthorizationPojo();
		cap.setClientName(clientName);
		cap.setClientEmail(clientEmail);

		String AuthResponse = given().log().all().header("Content-Type", "application/json").body(cap).when()
				.post("/api-clients/").then().log().all().assertThat().statusCode(201).extract().response().asString();

		JsonPath js = new JsonPath(AuthResponse);
		String token = js.get("accessToken");
		System.out.println(token);

		// Get book id's API
		String getBookResponse = given().log().all().header("Content-Type", "application/json").when().get("/books")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath js1 = new JsonPath(getBookResponse);

		// Create a list to hold the IDs of available books
		ArrayList<Integer> bookIds = new ArrayList<>();
		// the specific book name
		String bookName = "The Vanishing Half";

		// Get the size of the response array
		for (int i = 0; i < js1.getList("$").size(); i++) {
			// Check if the 'name' field matches the target name
			String name = js1.getString("[" + i + "].name");
			// Extract the 'id' and add it to the list
			if (name.equals(bookName)) {

				Integer id = js1.getInt("[" + i + "].id");
				bookIds.add(id);

			}
		}

		Integer bookid = bookIds.get(0);
		System.out.println(bookid);

		// Submit order API

		SubmitOrderRequestPojo so = new SubmitOrderRequestPojo();
		so.setBookId(bookid);
		so.setCustomerName(clientName);

		String submitOrderResponse = given().log().all().header("Content-Type", "application/json")
				.header("Authorization", token)
				.body(so)
				.when().post("/orders").then().log().all().assertThat()
				.statusCode(201).extract().response().asString();

		JsonPath js3 = new JsonPath(submitOrderResponse);
		String orderID = js3.get("orderId");
		System.out.println(orderID);
		
		//get order API
		String getOrderResponse = given().log().all().header("Content-Type", "application/json")
				.header("Authorization", token)
				.pathParam("orderId", orderID)
				.when().get("/orders/{orderId}")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath js4 = new JsonPath(getOrderResponse);
		int bookIdOrder =js4.get("bookId");
		String cust_name = js4.get("customerName");
		System.out.println(bookIdOrder);
		System.out.println(cust_name);
		
		//delete order API
		
		String deleteOrderResponse = given().log().all().header("Content-Type", "application/json")
				.header("Authorization", token)
				.pathParam("orderId", orderID)
				.when().delete("/orders/{orderId}")
				.then().log().all().assertThat().statusCode(204).extract().response().asString();
		
		System.out.println(deleteOrderResponse);

	}

}
