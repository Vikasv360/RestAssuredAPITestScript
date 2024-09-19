package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinitions extends Utils {

	RequestSpecification reqAuth;
	RequestSpecification orderReq;
	RequestSpecification deleteReq;
	Response CustResponse;
	Response getBookResponse;
	Response submitOrderRes;
	Response getOrderResponse;
	Response deleteOrderResponse;
	static Integer bookid;
	static String token;
	static String orderId;
	JsonPath js;
	String bookName = "The Vanishing Half";
	TestDataBuild data = new TestDataBuild();

	// Customer Authorization functionality
	@Given("User adds customerAuthorization payload with {string}, {string}")
	public void user_adds_customer_authorization_payload_with(String clientName, String clientEmail)
			throws IOException {

		reqAuth = given().log().all().spec(requestSpecifications()).header("Content-Type", "application/json")
				.body(data.custAuth(clientName, clientEmail));

	}

	@When("User calls CustomerAuthorizationAPI with post http request")
	public void user_calls_customer_authorization_api_with_post_http_request() {

		CustResponse = reqAuth.when().post("/api-clients/").then().log().all().assertThat().statusCode(201).extract()
				.response();

	}

	@Then("User is displayed with success status code {int} for customer authorization")
	public void user_is_displayed_with_success_status_code_for_customer_authorization(Integer int1) {
		Assert.assertEquals(CustResponse.getStatusCode(), 201);
	}

	@Then("User is displayed with accessToken in response body")
	public void user_is_displayed_with_access_token_in_response_body() {

		token = getJsonPath(CustResponse, "accessToken");
		System.out.println(token);
	}

	// Get Books functionality

	@When("User calls getBookAPI with get http request")
	public void user_calls_get_book_api_with_get_http_request() throws IOException {

		getBookResponse = given().log().all().spec(requestSpecifications()).header("Content-Type", "application/json")
				.when().get("/books").then().log().all().assertThat().statusCode(200).extract().response();
	}

	@Then("User is displayed with success status code {int} for getBookAPI")
	public void user_is_displayed_with_success_status_code_for_get_book_api(Integer int1) {

		Assert.assertEquals(getBookResponse.getStatusCode(), 200);

	}

	@Then("User is displayed with list of books in response body")
	public void user_is_displayed_with_list_of_books_in_response_body() {

		String listBookRes = getBookResponse.asString();

		js = new JsonPath(listBookRes);

	}

	@Then("User fetch the bookid from response body")
	public void user_fetch_the_bookid_from_response_body() {

		// Create a list to hold the IDs of available books
		ArrayList<Integer> bookIds = new ArrayList<>();
		// the specific book name

		// Get the size of the response array
		for (int i = 0; i < js.getList("$").size(); i++) {
			// Check if the 'name' field matches the target name
			String name = js.getString("[" + i + "].name");
			// Extract the 'id' and add it to the list
			if (name.equals(bookName)) {

				Integer id = js.getInt("[" + i + "].id");
				bookIds.add(id);

			}
		}

		bookid = bookIds.get(0);
		System.out.println(bookid);
	}

	// Submit order functionality

	@Given("User adds submitOrder payload with Bookid and {string}")
	public void user_adds_submit_order_payload_with_and(String clientName) throws IOException {

		orderReq = given().log().all().spec(requestSpecifications()).header("Content-Type", "application/json")
				.header("Authorization", token).body("{\r\n" + "  \"bookId\": " + bookid + ",\r\n"
						+ "  \"customerName\": \"" + clientName + "\"\r\n" + "}");

	}

	@When("User calls submitOrderAPI with post http request")
	public void user_calls_submit_order_api_with_post_http_request() {

		submitOrderRes = orderReq.when().post("/orders").then().log().all().assertThat().statusCode(201).extract()
				.response();

	}

	@Then("User is displayed with success status code {int} for submitOrder API")
	public void user_is_displayed_with_success_status_code_for_submit_order_api(Integer int1) {

		Assert.assertEquals(submitOrderRes.getStatusCode(), 201);
	}

	@Then("User is displayed with orderId in response body")
	public void user_is_displayed_with_order_id_in_response_body() {

		orderId = getJsonPath(submitOrderRes, "orderId");
		System.out.println(orderId);

	}

	// get submitted order details functionality

	@When("User calls getOrderAPI with get http request")
	public void user_calls_get_order_api_with_get_http_request() throws IOException {
		getOrderResponse = given().log().all().spec(requestSpecifications()).header("Content-Type", "application/json")
				.header("Authorization", token).pathParam("orderId", orderId).when().get("/orders/{orderId}").then()
				.log().all().assertThat().statusCode(200).extract().response();

	}

	@Then("User is displayed with success status code {int} for getOrderAPI")
	public void user_is_displayed_with_success_status_code_for_get_order_api(Integer int1) {

		Assert.assertEquals(getOrderResponse.getStatusCode(), 200);
	}

	@Then("User is displayed with the order submitted in response body")
	public void user_is_displayed_with_the_order_submitted_in_response_body() {

		String cust_name = getJsonPath(getOrderResponse, "customerName");
		String id = getJsonPath(getOrderResponse, "id");
		System.out.println(cust_name + "has order a book whose order id is : " + id);

	}

	// delete submitted order functionality

	@When("User calls deleteOrderAPI with delete http request")
	public void user_calls_delete_order_api_with_delete_http_request() throws IOException {

		deleteOrderResponse = given().log().all().spec(requestSpecifications()).header("Content-Type", "application/json").header("Authorization", token)
				.pathParam("orderId", orderId).when().delete("/orders/{orderId}").then().log().all().assertThat()
				.statusCode(204).extract().response();

	}
	
	@Then("User is displayed with success status code {int} for deleteOrderAPI")
	public void user_is_displayed_with_success_status_code_for_delete_order_api(Integer int1) {
		Assert.assertEquals(deleteOrderResponse.getStatusCode(), 204);
	}

	
	@Then("User is displayed with empty response body")
	public void user_is_displayed_with_empty_response_body() {

		System.out.println("Empty response body of delete request :" + deleteOrderResponse.asString());
	}

	
	

}
