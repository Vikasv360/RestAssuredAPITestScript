
Feature: Validating Simple book API's
 
 @Authorization
  Scenario Outline: Verify if customer is successfully authorized using CustomerAuthorization API
    Given User adds customerAuthorization payload with "<clientName>", "<clientEmail>"
    When User calls CustomerAuthorizationAPI with post http request
    Then User is displayed with success status code 201 for customer authorization
    And User is displayed with accessToken in response body

  Examples:
    | clientName   | clientEmail           | 
    | vikasve4     | vikasve02@example.com |


@getBook
Scenario: Verify the user is displayed with the list of books using getBooks API
   When User calls getBookAPI with get http request
   Then User is displayed with success status code 200 for getBookAPI 
   And User is displayed with list of books in response body
   And User fetch the bookid from response body
   
@submitOrder
Scenario Outline: Verify the user is able to submit the book order using submitOrder API
   Given User adds submitOrder payload with Bookid and "<clientName>"
    When User calls submitOrderAPI with post http request
    Then User is displayed with success status code 201 for submitOrder API
    And User is displayed with orderId in response body
    
    Examples:
    | clientName  |
    | vikasve4    |  
    
@getOrder
Scenario: Verify the user is displayed with the order submitted using getOrder API
   When User calls getOrderAPI with get http request
   Then User is displayed with success status code 200 for getOrderAPI 
   And User is displayed with the order submitted in response body
   
@deleteOrder
Scenario: Verify the user is able to delete the submitted order using deleteOrder API
   When User calls deleteOrderAPI with delete http request
   Then User is displayed with success status code 204 for deleteOrderAPI 
   And User is displayed with empty response body
  

   
 