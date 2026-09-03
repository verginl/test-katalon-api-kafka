Feature: REST API User Management

  @TC_REST_Producer
  Scenario: Create a new user through REST API
    Given the REST API service is available
    When I send a POST request to create a new user
    Then the response status code should be 201
    And the response should contain the created user information

  @TC_REST_Consumer
  Scenario: Retrieve the created user through REST API
    Given the REST API service is available
    When I request the created user
    Then the response status code should be 200
    And the response should contain the requested user information