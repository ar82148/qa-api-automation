Feature: Reqres Users API

  Background:
    * url baseUrl

  Scenario: Get list of users returns 200
    Given path '/users'
    And param page = 1
    When method GET
    Then status 200
    And match response.data == '#[]'
    And match response.page == 1

  Scenario: Get single user returns correct data
    Given path '/users/2'
    When method GET
    Then status 200
    And match response.data.id == 2
    And match response.data.email == '#string'
    And match response.data.first_name == '#string'

  Scenario: Get non-existent user returns 404
    Given path '/users/999'
    When method GET
    Then status 404

  Scenario: Create user returns 201
    Given path '/users'
    And request { name: 'Jake', job: 'QA Engineer' }
    When method POST
    Then status 201
    And match response.name == 'Jake'
    And match response.id == '#string'

  Scenario: Update user returns 200
    Given path '/users/2'
    And request { name: 'Jake', job: 'SDET' }
    When method PUT
    Then status 200
    And match response.job == 'SDET'

  Scenario: Delete user returns 204
    Given path '/users/2'
    When method DELETE
    Then status 204